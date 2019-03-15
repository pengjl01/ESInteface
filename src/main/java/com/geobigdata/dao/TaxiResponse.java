package com.geobigdata.dao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.geobigdata.entity.threadpool.RHLC;
import com.geobigdata.utils.Constants.TaxiConstants;
import com.geobigdata.utils.Constants.GlobalConstants;
/*
 * @author pjl
 * @version 创建时间：2019年1月14日 下午3:19:41
 * 类说明
 */
public class TaxiResponse {
	/*
	 * taxi查询函数
	 */
	public static SearchResponse taxiESSearch(double top, double left, double bottom, double right, String time) {
		try {
			SimpleDateFormat SDF = new SimpleDateFormat(GlobalConstants.SDFSTR);
			Date time1 = SDF.parse(time);
			time1.setTime(time1.getTime() - TaxiConstants.TAXI_DELTA_SECOND * 1000);
			String timeBottom = SDF.format(time1);
			RangeQueryBuilder rqb = QueryBuilders.rangeQuery(TaxiConstants.TAXI_TIME).gte(timeBottom).lte(time);
			GeoBoundingBoxQueryBuilder geoqb = QueryBuilders.geoBoundingBoxQuery(TaxiConstants.TAXI_LOCATION)
					.setCorners(top, left, bottom, right);
			QueryBuilder qb = QueryBuilders.boolQuery().must(geoqb).must(rqb);
			// TermsAggregationBuilder aggregation =
			// AggregationBuilders.terms(Constants.BY_ID)
			// .field(Constants.TAXI_ID).order(BucketOrder.key(false));
			String[] includeFields = new String[] { TaxiConstants.TAXI_ID, TaxiConstants.TAXI_LOCATION,
					TaxiConstants.TAXI_DIRECTION };
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qb).size(TaxiConstants.TAXI_MAX_NUM)
					.fetchSource(includeFields, null)
					.sort(TaxiConstants.TAXI_TIME, SortOrder.DESC);
			SearchRequest searchRequest = new SearchRequest(TaxiConstants.TAXI);
			searchRequest.source(sourceBuilder);
			return RHLC.getInstance().getclient().search(searchRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 查询某辆车的某时段轨迹
	 */
	public static SearchResponse taxiESSearchByTime(String uid, String starttime, String endtime) {
		try {
			return RHLC.getInstance().getclient().search(taxiPathBuildRequest(uid, starttime, endtime));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 查询某辆车的某时段轨迹
	 */
	public static MultiSearchResponse taxiESSearchByTimeMulti(List<String> uids, String starttime, String endtime) {
		MultiSearchRequest searchRequest = new MultiSearchRequest();
		for (String uid : uids) {
			searchRequest.add(taxiPathBuildRequest(uid, starttime, endtime));
		}
		try {
			return RHLC.getInstance().getclient().multiSearch(searchRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// taxipath构建查询
	private static SearchRequest taxiPathBuildRequest(String uid, String starttime, String endtime) {
		try {
			RangeQueryBuilder rqb = QueryBuilders.rangeQuery(TaxiConstants.TAXI_TIME).gte(starttime).lte(endtime);
			MatchQueryBuilder mqb = QueryBuilders.matchQuery(TaxiConstants.TAXI_ID, uid);
			QueryBuilder qb = QueryBuilders.boolQuery().must(mqb).must(rqb);
			String[] includeFields = new String[] { TaxiConstants.TAXI_LOCATION, TaxiConstants.TAXI_DIRECTION,
					TaxiConstants.TAXI_TIME, TaxiConstants.TAXI_ID };
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qb).size(10000)
					.fetchSource(includeFields, null)
					.sort(TaxiConstants.TAXI_TIME, SortOrder.ASC);
			SearchRequest searchRequest = new SearchRequest(TaxiConstants.TAXI);
			searchRequest.source(sourceBuilder);
			return searchRequest;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * 查询区域内某时段所有轨迹
	 */
	public static SearchResponse taxiESSearchAreaTime(double top, double left, double bottom, double right,
			String starttime, String endtime) {
		try {
			RangeQueryBuilder rqb = QueryBuilders.rangeQuery(TaxiConstants.TAXI_TIME).gte(starttime).lte(endtime);
			GeoBoundingBoxQueryBuilder geoqb = QueryBuilders.geoBoundingBoxQuery(TaxiConstants.TAXI_LOCATION)
					.setCorners(top, left, bottom, right);
			QueryBuilder qb = QueryBuilders.boolQuery().must(geoqb).must(rqb);
			String[] includeFields = new String[] { TaxiConstants.TAXI_ID, TaxiConstants.TAXI_LOCATION,
					TaxiConstants.TAXI_DIRECTION, TaxiConstants.TAXI_TIME };
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qb).size(10000)
//					.timeout(new TimeValue(TaxiConstants.TIMEOUT, TimeUnit.SECONDS))
					.fetchSource(includeFields, null);// .sort(Constants.TAXI_TIME,
																													// SortOrder.ASC);
			SearchRequest searchRequest = new SearchRequest(TaxiConstants.TAXI);
			searchRequest.source(sourceBuilder);
			return RHLC.getInstance().getclient().search(searchRequest);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
