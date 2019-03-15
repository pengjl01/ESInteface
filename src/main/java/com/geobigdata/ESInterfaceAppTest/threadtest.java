//package com.geobigdata.ESInterfaceAppTest;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//
//import com.geobigdata.dao.WaveResponse;
//import com.geobigdata.utils.RedisUtil;
//import com.geobigdata.utils.Constants.GlobalConstants;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Pipeline;
//import redis.clients.jedis.Response;
//
///*
// * @author pjl
// * @version 创建时间：2019年1月14日 下午9:19:38
// * 类说明
// */
//public class threadtest implements Runnable {
//	public List<List<String>> locationList2;
//	private static int LOCATIONID_SLICE = 10000;
//	String time;
//	final CountDownLatch latch;
//
//	public threadtest(List<String> locationIDs, String time) {
//		locationList2 = new ArrayList<List<String>>();
//		List<String> tempList = new ArrayList<String>();
//		int i = 0;
//		for (String location : locationIDs) {
//			if (i < LOCATIONID_SLICE) {
//				tempList.add(location);
//				++i;
//			} else {
//				i = 0;
//				locationList2.add(tempList);
//				tempList = new ArrayList<String>();
//			}
//		}
//		locationList2.add(tempList);
//		latch = new CountDownLatch(locationList2.size());
//	}
//
//	public void refresh(final List<String> locationList, final String time) {
//		Thread a = new Thread(new Runnable() {
//			@Override
//			public synchronized void run() {
//				System.out.println("Refresh Started");
//				long startTime = System.currentTimeMillis();
//
//				try {
//					Thread.sleep(1000);
//					latch.countDown();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				long endTime = System.currentTimeMillis();
//				long usedTime = endTime - startTime;
//				System.out.println("Time " + usedTime);
//			}
//		});
//		a.start();
//	}
//
//	// 缓存全部更新
//	@Override
//	public void run() {
//		try {
//			for (List<String> locationList : locationList2) {
//				refresh(locationList, time);
//			}
//			latch.await();
//			System.out.println("RefreshRedis Start");
//			Thread.sleep(100);
//			System.out.println("RefreshRedis Done");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void refreshRedis() {
//
//	}
//
//	public static void main(String[] args) {
////		new Thread(new threadtest(com.geobigdata.utils.Constants.WaveConstants.WAVE_LOCATIONID_LIST, "20180101100000")).start();
//	}
//}
