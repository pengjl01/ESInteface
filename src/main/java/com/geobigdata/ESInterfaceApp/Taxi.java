package com.geobigdata.ESInterfaceApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import com.geobigdata.dao.TaxiResponse;
import com.geobigdata.entity.data.QueryError;
import com.geobigdata.entity.data.taxi.TaxiAreaPath;
import com.geobigdata.entity.data.taxi.TaxiLocationInfo;
import com.geobigdata.entity.data.taxi.TaxiLocationsInfo;
import com.geobigdata.entity.data.taxi.TaxiPath;
import com.geobigdata.entity.data.taxi.TaxiPoint;
import com.geobigdata.utils.MyUtil;
import com.geobigdata.utils.Constants.TaxiConstants;

/*
 * @author pjl
 * @version 创建时间：2018年10月31日 下午14:40:12
 * taxi接口
 */
@Path(TaxiConstants.TAXI)
public class Taxi {

	/*
	 * GET方法
	 * 
	 * @param 路径参数，待查矩形边界坐标，待查时间
	 * 
	 * @return taxi的坐标JSON数组
	 */
	@GET
	@Path(TaxiConstants.TAXI_GET_LOCATION_PARAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String taxiLocation(@PathParam(TaxiConstants.TAXI_GET_LOCATION_PARAM0) double top,
			@PathParam(TaxiConstants.TAXI_GET_LOCATION_PARAM1) double left,
			@PathParam(TaxiConstants.TAXI_GET_LOCATION_PARAM2) double bottom,
			@PathParam(TaxiConstants.TAXI_GET_LOCATION_PARAM3) double right,
			@PathParam(TaxiConstants.TAXI_GET_LOCATION_PARAM4) String time) {
		if (top <= bottom)
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM0_PARAM2_ERROR).toJSON();
		if (left == right)
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM1_PARAM3_ERROR).toJSON();
		if (!MyUtil.checkTime(time))
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM4_ERROR).toJSON();
		SearchResponse searchResponse = TaxiResponse.taxiESSearch(top, left, bottom, right, time);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		TaxiLocationsInfo tlis = new TaxiLocationsInfo();
		if (searchHits.length != 0) {
			Map<String, Map<String, Object>> map = new HashMap<>();
			for (SearchHit hit : searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				String tempuid = sourceAsMap.get(TaxiConstants.TAXI_ID).toString();
				if (!map.containsKey(tempuid)) {
					map.put(tempuid, sourceAsMap);
				}
			}
			for (Map<String, Object> temp : map.values()) {
				TaxiLocationInfo tli = new TaxiLocationInfo(temp);
				tlis.addTaxiLocation(tli);
			}
			// TaxiLocationsInfo tlis=new TaxiLocationsInfo();
			// Terms byIdAggregation =
			// searchResponse.getAggregations().get(Constants.BY_TIME);
			// List elasticBuckets = byIdAggregation.g;
			// for(Bucket elasticBucket :elasticBuckets) {
			// System.out.println(elasticBucket.toString());
			// //TaxiLocationInfo tli=new TaxiLocationInfo(elasticBucket);
			// }
		} else {
			tlis.setStatus(1);
		}
		return tlis.toJSON();
	}

	/*
	 * GET方法
	 * 
	 * @param uid，待查时间
	 * 
	 * @return taxi的path
	 */
	@GET
	@Path(TaxiConstants.TAXI_GET_PATH_PARAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String taxiPath(@PathParam(TaxiConstants.TAXI_GET_PATH_PARAM0) String uid,
			@PathParam(TaxiConstants.TAXI_GET_PATH_PARAM1) String starttime,
			@PathParam(TaxiConstants.TAXI_GET_PATH_PARAM2) String endtime) {
		if (!MyUtil.checkTime(starttime) || !MyUtil.checkTime(endtime))
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM4_ERROR).toJSON();
		SearchResponse searchResponse = TaxiResponse.taxiESSearchByTime(uid, starttime, endtime);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		TaxiPath tp = new TaxiPath(Long.valueOf(uid));
		if (searchHits.length != 0) {
			for (SearchHit hit : searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				TaxiPoint tli = new TaxiPoint(sourceAsMap);
				tp.addTaxiPoint(tli);
			}
		} else {
			tp.setStatus(1);
		}
		return tp.toJSON();
	}

	/*
	 * GET方法
	 * 
	 * @param 待查矩形边界坐标，待查时间，待查时间
	 * 
	 * @return 区域内taxi的path
	 */
	@GET
	@Path(TaxiConstants.TAXI_GET_AREAPATH_PARAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String taxiAreaPath(@PathParam(TaxiConstants.TAXI_GET_AREAPATH_PARAM0) double top,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH_PARAM1) double left,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH_PARAM2) double bottom,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH_PARAM3) double right,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH_PARAM4) String starttime,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH_PARAM5) String endtime) {
		if (top <= bottom)
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM0_PARAM2_ERROR).toJSON();
		if (left == right)
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM1_PARAM3_ERROR).toJSON();
		if (!MyUtil.checkTime(starttime) || !MyUtil.checkTime(endtime))
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM4_ERROR).toJSON();
		SearchResponse searchResponse = TaxiResponse.taxiESSearchAreaTime(top, left, bottom, right, starttime, endtime);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		TaxiAreaPath tap = new TaxiAreaPath();
		if (searchHits.length != 0) {
			Map<Long, TaxiPath> map = new HashMap<>();
			for (SearchHit hit : searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				long tempid = Long.valueOf(sourceAsMap.get(TaxiConstants.TAXI_ID).toString());
				if (!map.containsKey(tempid)) {
					TaxiPath tp = new TaxiPath(tempid);
					tp.addTaxiPoint(new TaxiPoint(sourceAsMap));
					map.put(tempid, tp);
				} else {
					map.get(tempid).addTaxiPoint(new TaxiPoint(sourceAsMap));
				}
			}
			for (TaxiPath temp : map.values()) {
				tap.addTaxiPath(temp);
			}
		} else {
			tap.setStatus(1);
		}
		return tap.toJSON();
	}

	/*
	 * GET方法
	 * 
	 * @param 待查矩形边界坐标，待查时间，待查时间
	 * 
	 * @return 区域内taxi的path
	 */
	@GET
	@Path(TaxiConstants.TAXI_GET_AREAPATH2_PARAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String taxiAreaPath(@PathParam(TaxiConstants.TAXI_GET_AREAPATH2_PARAM0) double top,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH2_PARAM1) double left,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH2_PARAM2) double bottom,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH2_PARAM3) double right,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH2_PARAM4) String time,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH2_PARAM5) String starttime,
			@PathParam(TaxiConstants.TAXI_GET_AREAPATH2_PARAM6) String endtime) {
		if (top <= bottom)
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM0_PARAM2_ERROR).toJSON();
		if (left == right)
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM1_PARAM3_ERROR).toJSON();
		if (!MyUtil.checkTime(time) ||!MyUtil.checkTime(starttime) || !MyUtil.checkTime(endtime))
			return new QueryError(TaxiConstants.TAXI_GET_LOCATION_PARAM4_ERROR).toJSON();
		SearchResponse searchResponse = TaxiResponse.taxiESSearch(top, left, bottom, right, time);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		TaxiAreaPath tap = new TaxiAreaPath();
		List<String> taxiList = new ArrayList<>();
		if (searchHits.length != 0) {
			for (SearchHit hit : searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				String tempuid = sourceAsMap.get(TaxiConstants.TAXI_ID).toString();
				taxiList.add(tempuid);
			}
			LinkedHashSet<String> set = new LinkedHashSet<String>(taxiList.size());
			set.addAll(taxiList);
			taxiList.clear();
			taxiList.addAll(set);
			MultiSearchResponse searchResponses = TaxiResponse.taxiESSearchByTimeMulti(taxiList, starttime, endtime);
			for (int i = 0; i < taxiList.size(); ++i) {
				searchResponse = searchResponses.getResponses()[i].getResponse();
				hits = searchResponse.getHits();
				searchHits = hits.getHits();
				TaxiPath tp = new TaxiPath(0);
				if (searchHits.length != 0) {
					String a = searchHits[0].getSourceAsMap().get(TaxiConstants.TAXI_ID).toString();
					tp.setUid(Long.valueOf(a));
					for (SearchHit hit : searchHits) {
						Map<String, Object> sourceAsMap = hit.getSourceAsMap();
						TaxiPoint tli = new TaxiPoint(sourceAsMap);
						tp.addTaxiPoint(tli);
					}
				} else {
					tp.setStatus(1);
				}
				tap.addTaxiPath(tp);
			}

		}
		return tap.toJSON();
	}
}
