//package com.geobigdata.ESInterfaceAppTest;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.MultiSearchRequest;
//import org.elasticsearch.action.search.MultiSearchResponse;
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
//public class WaveMultiSearchTest {
//	public static void main(String[] args) {
//		RestHighLevelClient client = new RestHighLevelClient(RestClient
//				.builder(new HttpHost("172.18.20.217", 9200, "http"), new HttpHost("172.18.20.217", 9201, "http")));
//
//		MultiSearchRequest request = new MultiSearchRequest();
//		long[] locationIds = { 1601830, 1100271, 1100280 };
//		for (int i = 0; i < locationIds.length; ++i) {
//			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//			MatchQueryBuilder mqb = QueryBuilders.matchQuery("locationId", locationIds[i]);
//			RangeQueryBuilder rqb = QueryBuilders.rangeQuery("rectime").gte("20180302083500").lte("20180302084000");
//			QueryBuilder qb = QueryBuilders.boolQuery().must(rqb).must(mqb);
//			sourceBuilder.query(qb);
//			sourceBuilder.size(10);
//			sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//			SearchRequest searchRequest = new SearchRequest("wave");
//			searchRequest.source(sourceBuilder);
//			request.add(searchRequest);
//		}
//
////		SearchRequest firstSearchRequest = new SearchRequest();
////		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
////		searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
////		firstSearchRequest.source(searchSourceBuilder);
////		request.add(firstSearchRequest);
////		SearchRequest secondSearchRequest = new SearchRequest();
////		searchSourceBuilder = new SearchSourceBuilder();
////		searchSourceBuilder.query(QueryBuilders.matchQuery("user", "luca"));
////		secondSearchRequest.source(searchSourceBuilder);
////		request.add(secondSearchRequest);
//
//		try {
//			MultiSearchResponse response = client.multiSearch(request);
//			for(int i=0;i<locationIds.length;++i) {
//			MultiSearchResponse.Item firstResponse = response.getResponses()[i];
////			assertNull(firstResponse.getFailure());
//			SearchResponse searchResponse = firstResponse.getResponse();
////			assertEquals(4, searchResponse.getHits().getTotalHits());
////			MultiSearchResponse.Item secondResponse = response.getResponses()[1];
////			assertNull(secondResponse.getFailure());
////			searchResponse = secondResponse.getResponse();
////			assertEquals(2, searchResponse.getHits().getTotalHits());
//
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
//			System.out.println();
//			}
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
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
