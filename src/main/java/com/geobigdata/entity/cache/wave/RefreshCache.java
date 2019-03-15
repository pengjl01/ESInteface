//package com.geobigdata.entity.cache.wave;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import com.geobigdata.dao.WaveResponse;
//import com.geobigdata.utils.RedisUtil;
//import com.geobigdata.utils.Constants.WaveConstants;
//
//import redis.clients.jedis.Jedis;
//
///*
// * @author pjl
// * @version 创建时间：2019年1月10日 下午2:11:13
// * 缓存更新
// */
////把此类池化，控制同时更新缓存的数量
//public class RefreshCache implements Runnable {
//	private static int POOL_SIZE = 6;
//	private static int LOCATIONID_SLICE = 1000;
//	String time;
//	final CountDownLatch latch;
//	public List<List<String>> locationList2;
//	private Map<String, String> speedMap;
//	private ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
//
//	public RefreshCache(String time) {
//		// 此处需改，判断是否存在最近一次缓存，存在则以缓存值作为map初始值，不存在则随机初始化
////		修改查询往回退30分钟，然后随机初始化或许是不错选择？首先将随机数放至redis，随后查得数据进行更新
////		任务队列？
//		speedMap = new HashMap<String, String>();
//		this.time = time;
//		locationList2 = new ArrayList<List<String>>();
//		List<String> tempList = new ArrayList<String>();
//		int i = 0;
//		for (String location : WaveConstants.WAVE_LOCATIONID_LIST) {
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
//	// * 是否可以将ES查询做成异步管道形式
//	private void refresh(final List<String> locationList) {
//		executor.submit(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					// Thread.sleep(1000);
//					System.out.println("thread running");
//					Map<String, String> temp = WaveResponse.getSpeedMap(locationList, time);
//					speedMap.putAll(temp);
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					latch.countDown();
//				}
//			}
//		});
//	}
//
////	先传一组随机数到redis，然后更新数值
//	// 缓存全部更新
//	@Override
//	public void run() {
//		System.out.println(time+"RefreshCache Started");
//		long startTime = System.currentTimeMillis();
//		try {
//			for (List<String> locationList : locationList2) {
//				refresh(locationList);
//			}
//			latch.await();
//			Jedis jedis = null;
//			try {
//				jedis = RedisUtil.getJedis();
//				jedis.hmset(time, speedMap);
//				System.out.println("RefreshRedis Done");
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				RedisUtil.returnResource(jedis);
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		long endTime = System.currentTimeMillis();
//		long usedTime = endTime - startTime;
//		System.out.println("silce=" + LOCATIONID_SLICE + " threads=" + POOL_SIZE + " time=" + usedTime);
//		System.out
//				.println("after: 1010027082 " + speedMap.get("1010027082") + ";1010027072 " + speedMap.get("1010027072")
//						+ ";1010074742 " + speedMap.get("1010074742") + ";1014188952 " + speedMap.get("1014188952"));
//	}
//
//	public static void main(String[] args) {
//		new Thread(new RefreshCache("20180218154100")).start();
//	}
//}
