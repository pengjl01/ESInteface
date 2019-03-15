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
//import org.elasticsearch.index.query.MatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
///*
// * @author pjl
// * @version 创建时间：2018年11月1日 下午1:56:22
// * 类说明
// */
//public class CameraSearchTest {
//	public static void main(String[] args) {
//		RestHighLevelClient client = new RestHighLevelClient(RestClient
//				.builder(new HttpHost("172.18.20.217", 9200, "http"), new HttpHost("172.18.20.217", 9201, "http")));
//
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		MatchQueryBuilder mqb = QueryBuilders.matchQuery("camID", "CAM04213121");
//		RangeQueryBuilder rqb = QueryBuilders.rangeQuery("wjID").gte("20121017010227").lte("20121017010327");
//		QueryBuilder qb = QueryBuilders.boolQuery().must(rqb).must(mqb);
//		sourceBuilder.query(qb);
//		sourceBuilder.size(10);
//		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//		SearchRequest searchRequest = new SearchRequest("camera");
//		searchRequest.source(sourceBuilder);
//		// 排序
//		//sourceBuilder.sort("rectime", SortOrder.DESC);
//		// 筛选字段
////		String[] includeFields = new String[] { "locationId", "laneno", "rectime", "speed" };
////		String[] excludeFields = new String[] { "_type" };
////		sourceBuilder.fetchSource(includeFields, excludeFields);
//		SearchResponse searchResponse;
//		try {
//			searchResponse = client.search(searchRequest);
//			SearchHits hits = searchResponse.getHits();
//			SearchHit[] searchHits = hits.getHits();
//			for (SearchHit hit : searchHits) {
//				String sourceAsString = hit.getSourceAsString();
//				System.out.println(sourceAsString);
//				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//				String documentTitle = (String) sourceAsMap.get("title");
//				List<Object> users = (List<Object>) sourceAsMap.get("user");
//				Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
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
//
//		// MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
//		// .add("twitter", "_doc", "1")
//		// .add("twitter", "_doc", "2", "3", "4")
//		// .add("another", "_doc", "foo")
//		// .get();
//		//
//		// for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
//		// GetResponse response = itemResponse.getResponse();
//		// if (response.isExists()) {
//		// String json = response.getSourceAsString();
//		// }
//		// }
//	}
//}
