//package com.geobigdata.entity.threadpool;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.Queue;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.MultiSearchRequest;
//import org.elasticsearch.action.search.MultiSearchResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//
//import com.geobigdata.utils.Constants;
//
///*
// * @author pjl
// * @version 创建时间：2019年1月2日 下午5:35:16
// * 客户端线程池
// */
//public class RHLCThreadPool {
//	private static volatile RHLCThreadPool rhlctp;
//	private Queue<RestHighLevelClient> RHLCqueue=new LinkedList<RestHighLevelClient>();
//	private RHLCThreadPool() {
//		for(int i=0;i<Constants.RHLCNUM;++i) {
//			RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
//					new HttpHost(Constants.ES_SERVER_ADDRESS, Constants.ES_SERVER_PORT, Constants.ES_SERVER_SCHEME),
//					new HttpHost(Constants.ES_SERVER_ADDRESS, Constants.ES_SERVER_PORT1, Constants.ES_SERVER_SCHEME)));
//			RHLCqueue.offer(client);
//		}
//	}
//
//	public static RHLCThreadPool getInstance() {
//		if (rhlctp == null) {
//			synchronized (RHLCThreadPool.class) {
//				if (rhlctp == null) {
//					rhlctp = new RHLCThreadPool();
//				}
//			}
//		}
//		return rhlctp;
//	}
//
//	public SearchResponse submitTask(SearchRequest searchRequest) {
//		RestHighLevelClient client=RHLCqueue.poll();
//		System.out.println("RHLC polled "+RHLCqueue.size());
//		try {
//			SearchResponse response=client.search(searchRequest);
//			RHLCqueue.offer(client);
////			System.out.println("RHLC offered "+RHLCqueue.size());
//			return response;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			RHLCqueue.offer(client);
////			System.out.println("RHLC offered "+RHLCqueue.size());
//			return null;
//		}
//	}
//	public MultiSearchResponse submitTask(MultiSearchRequest searchRequest) {
//		RestHighLevelClient client=RHLCqueue.poll();
//		System.out.println("RHLC polled "+RHLCqueue.size());
//		try {
//			MultiSearchResponse response=client.multiSearch(searchRequest);
//			RHLCqueue.offer(client);
////			System.out.println("RHLC offered "+RHLCqueue.size());
//			return response;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			RHLCqueue.offer(client);
////			System.out.println("RHLC offered "+RHLCqueue.size());
//			return null;
//		}
//	}
//}
//
