//package com.geobigdata.ESInterfaceAppTest;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.GeoBoundingBoxQueryBuilder;
//import org.elasticsearch.index.query.MatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//public class TaxiSearchTest {
//	public static void main(String[] args) {
//		RestHighLevelClient client = new RestHighLevelClient(RestClient
//				.builder(new HttpHost("172.18.20.217", 9200, "http"), new HttpHost("172.18.20.217", 9201, "http")));
//
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		// 构建查询
//		RangeQueryBuilder rqb = QueryBuilders.rangeQuery("genTime").gte("20121017235500").lte("20121018000000");
//		GeoBoundingBoxQueryBuilder geoqb = QueryBuilders.geoBoundingBoxQuery("location").setCorners(39.8908000,
//				116.3345300, 39.8907800, 116.3345100);
//		QueryBuilder qb = QueryBuilders.boolQuery().must(geoqb).must(rqb);
//		sourceBuilder.query(qb);
//		sourceBuilder.size(10);
//		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//		SearchRequest searchRequest = new SearchRequest("taxi");
//		searchRequest.source(sourceBuilder);
//		// 排序
//		// sourceBuilder.sort("wjID.keyword", SortOrder.DESC);
//		// 筛选字段
//		// String[] includeFields = new String[] {"dataID", "camID", "cphm"};
//		// String[] excludeFields = new String[] {"_type"};
//		// sourceBuilder.fetchSource(includeFields, excludeFields);
//		SearchResponse searchResponse;
//		try {
//			searchResponse = client.search(searchRequest);
//			SearchHits hits = searchResponse.getHits();
//			SearchHit[] searchHits = hits.getHits();
//			for (SearchHit hit : searchHits) {
//				String sourceAsString = hit.getSourceAsString();
//				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//				String documentTitle = (String) sourceAsMap.get("title");
//				List<Object> users = (List<Object>) sourceAsMap.get("user");
//				Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
//				System.out.println(sourceAsString);
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		try {
//			client.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
