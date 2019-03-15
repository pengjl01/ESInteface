//package com.geobigdata.ESInterfaceAppTest;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchRequestBuilder;
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
//import org.elasticsearch.search.aggregations.Aggregation;
//import org.elasticsearch.search.aggregations.AggregationBuilder;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.BucketOrder;
//import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
//import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.avg.Avg;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//
//public class WaveSearchTest2 {
//	public static void main(String[] args) {
//		RestHighLevelClient client = new RestHighLevelClient(RestClient
//				.builder(new HttpHost("172.18.20.217", 9200, "http"), new HttpHost("172.18.20.217", 9201, "http")));
//
//		// 构建查询
//		MatchQueryBuilder mqb1 = QueryBuilders.matchQuery("locationId", 1601830);
//		RangeQueryBuilder rqb = QueryBuilders.rangeQuery("rectime").gte("20180302083000").lte("20180302084000");
//		QueryBuilder qb = QueryBuilders.boolQuery().must(mqb1).must(rqb);
//		TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_time").field("rectime");
//		aggregation.subAggregation(AggregationBuilders.avg("avg_speed").field("speed")).order(BucketOrder.key(false));
//
//		String[] includeFields = new String[] { "dataID", "camID", "cphm" };
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qb).aggregation(aggregation).size(10)
//				.timeout(new TimeValue(60, TimeUnit.SECONDS));
//		SearchRequest searchRequest = new SearchRequest("wave");
//		searchRequest.source(sourceBuilder);
//		// 排序
//		sourceBuilder.sort("rectime", SortOrder.DESC);
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
//			Aggregations aggregations = searchResponse.getAggregations();
//			Terms byTimeAggregation = aggregations.get("by_time");
//			// Bucket elasticBucket = byTimeAggregation.getBucketByKey("20180302083830");
//			Bucket elasticBucket = byTimeAggregation.getBuckets().get(0);
//			Avg averageAge = elasticBucket.getAggregations().get("avg_speed");
//			String timeee = elasticBucket.getKeyAsString();
//			double avg = averageAge.getValue();
//			System.out.println(timeee);
//			System.out.println(avg);
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
