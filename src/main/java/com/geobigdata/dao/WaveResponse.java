package com.geobigdata.dao;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import com.geobigdata.entity.data.wave.LaneInfo;
import com.geobigdata.entity.data.wave.MutliLaneInfo;
import com.geobigdata.entity.threadpool.RHLC;
import com.geobigdata.utils.Constants.GlobalConstants;
import com.geobigdata.utils.Constants.WaveConstants;

/*
 * @author pjl
 * @version 创建时间：2019年1月14日 下午3:19:41
 * Wave接口，ES查询相关
 */
public class WaveResponse {
	// wave构建查询
	private static SearchRequest waveBuildRequest(String locationID, String time) {
		try {
			Date time1;
			SimpleDateFormat SDF = new SimpleDateFormat(GlobalConstants.SDFSTR);
			time1 = SDF.parse(time);
			String year = time.substring(0, 4);
			String month = time.substring(4, 6);
			time1.setTime(time1.getTime() - WaveConstants.WAVE_DELTA_SECOND * 1000);
			String timeBottom = SDF.format(time1);
			MatchQueryBuilder mqb = QueryBuilders.matchQuery(WaveConstants.WAVE_LOCATION_ID, locationID);
			RangeQueryBuilder rqb = QueryBuilders.rangeQuery(WaveConstants.WAVE_TIME).gte(timeBottom).lte(time);
			QueryBuilder qb = QueryBuilders.boolQuery().must(mqb).must(rqb);
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qb).size(WaveConstants.WAVE_MAX_LANE);
//			 .sort(WaveConstants.WAVE_TIME, SortOrder.DESC);
			SearchRequest searchRequest = new SearchRequest(WaveConstants.WAVE + year + month);
			searchRequest.source(sourceBuilder);
			return searchRequest;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 逐个添加查询到MultiSearchResponse
	 */
	private static MultiSearchResponse waveSearchMulti(List<String> locationIDs, String time) {
		MultiSearchRequest searchRequest = new MultiSearchRequest();
		for (String locationID : locationIDs) {
			SearchRequest temp = waveBuildRequest(locationID, time);
			if (temp != null) {
				searchRequest.add(temp);
			}
		}
		try {
			return RHLC.getInstance().getclient().multiSearch(searchRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// return RHLCThreadPool.getInstance().submitTask(searchRequest);
	}
	/*
	 * 单个 查询
	 */
	private static SearchResponse waveSearch(String locationID, String time) {
		SearchRequest searchRequest = waveBuildRequest(locationID, time);
		if (searchRequest != null)
			try {
				return RHLC.getInstance().getclient().search(searchRequest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	/*
	 * locationIDs：locationID的List time：时间
	 * 
	 * return：map,key为locationID，value为各车道平均速度
	 */
	public static Map<String, String> getSpeedMap(List<String> locationIDs, String time) {
		MultiSearchResponse response = waveSearchMulti(locationIDs, time);
		Map<String, String> speedMap = new HashMap<String, String>();
		for (int i = 0; i < locationIDs.size(); ++i) {
			String locationID = locationIDs.get(i);
			SearchResponse searchResponse = response.getResponses()[i].getResponse();
			float speed = getAvgSpeed(searchResponse);
			if (speed != 0)
				speedMap.put(locationID, String.valueOf(speed));
		}
		return speedMap;
	}

//	/*
//	 * 查单条路的各车道平均车速
//	 */
//	public static RoadInfo getRoadInfo(String locationID, String time) {
//		SearchResponse searchResponse = waveSearch(locationID, time);
//		float speed = getAvgSpeed(searchResponse);
//		RoadInfo ri;
//		if (speed == 0) {
//			ri = new RoadInfo(locationID, "0");
//			ri.setStatus(1);
//		} else
//			ri = new RoadInfo(locationID, String.valueOf(speed));
//		return ri;
//	}
	/*
	 * 查单条路的各车道平均车速
	 */
	public static float getSpeed(String locationID, String time) {
		SearchResponse searchResponse = waveSearch(locationID, time);
		float speed = getAvgSpeed(searchResponse);
		return speed;
	}

	/*
	 * 从SearchResponse获取一条道路各车道的平均车速
	 */
	private static float getAvgSpeed(SearchResponse searchResponse) {
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		if (searchHits.length != 0) {
			float sum = 0, num = 0;
			for (SearchHit hit : searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				float tempspeed = Float.valueOf(sourceAsMap.get(WaveConstants.WAVE_LANE_SPEED).toString());
				if (tempspeed != 0) {
					sum += tempspeed;
					++num;
				}
			}
			return sum / num;
		} else
			return 0;
	}

	/*
	 * 查单条路每条车道的车速 都别排序了，Lane查询另想办法
	 */
	public static MutliLaneInfo getMutliLaneInfo(String locationID, String time) {
		SearchResponse searchResponse = waveSearch(locationID, time);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		MutliLaneInfo mli = new MutliLaneInfo();
		if (searchHits.length != 0) {
			String temptime = (String) searchHits[0].getSourceAsMap().get(WaveConstants.WAVE_TIME);
			for (SearchHit hit : searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				String thisTime=(String) sourceAsMap.get(WaveConstants.WAVE_TIME);
				int timeCompare=temptime.compareTo(thisTime);
				if (timeCompare==0){
					mli.addLane(new LaneInfo(sourceAsMap));
				}
				else if(timeCompare==-1) {
					mli.clearList();
					temptime=thisTime;
					mli.addLane(new LaneInfo(sourceAsMap));
				}
//				if (temptime.equals(sourceAsMap.get(WaveConstants.WAVE_TIME))) {
//					mli.addLane(new LaneInfo(sourceAsMap));
//				} else {
//					break;
//				}
			}
		} else {
			mli.setStatus(1);
		}
		return mli;
	}
	
	public static void main(String[]args) {
//		String a="20180101101020";
//		String b="20180202101001";
//		int c=b.compareTo(b);
//		System.out.println(c);
	}
}
