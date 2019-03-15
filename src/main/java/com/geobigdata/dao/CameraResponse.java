package com.geobigdata.dao;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import com.geobigdata.entity.threadpool.RHLC;
import com.geobigdata.utils.Constants.GlobalConstants;

/*
 * @author pjl
 * @version 创建时间：2019年1月14日 下午3:19:41
 * 类说明
 */
public class CameraResponse {
	/*
	 * camera查询函数 不排序
	 */
	public static SearchResponse cameraESSearch(long locationID, String starttime, String endtime) {
		try {
			RangeQueryBuilder rqb = QueryBuilders.rangeQuery(GlobalConstants.CAMERA_TIME).gte(starttime).lte(endtime);
			MatchQueryBuilder mqb = QueryBuilders.matchQuery(GlobalConstants.CAMERA_LOCATION_ID, locationID);
			QueryBuilder qb = QueryBuilders.boolQuery().must(mqb).must(rqb);
			String[] includeFields = new String[] { GlobalConstants.CAMERA_ID, GlobalConstants.CAMERA_CPHM, GlobalConstants.CAMERA_TIME,
					GlobalConstants.CAMERA_ZFBH };
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qb).size(GlobalConstants.CAMERA_MAX_NUM)
//					.timeout(new TimeValue(GlobalConstants.TIMEOUT, TimeUnit.SECONDS))
					.fetchSource(includeFields, null);
			SearchRequest searchRequest = new SearchRequest(GlobalConstants.CAMERA);
			searchRequest.source(sourceBuilder);
			return RHLC.getInstance().getclient().search(searchRequest);
//			return RHLCThreadPool.getInstance().submitTask(searchRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
