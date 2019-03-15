package com.geobigdata.entity.cache.wave;
//package com.geobigdata.entity.cache;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import com.geobigdata.ESInterfaceApp.Wave;
//import com.geobigdata.dao.WaveResponse;
//import com.geobigdata.utils.RedisUtil;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Pipeline;
//import redis.clients.jedis.Response;
//
///*
// * @author pjl
// * @version 创建时间：2019年1月9日 下午5:57:23
// * 类说明
// */
//public class SpeedCache {
//	private static volatile SpeedCache speedcache;
//	private static Map<String, String> speedMap;
//	static Jedis jedis = RedisUtil.getJedis();
//	static Pipeline pipe = jedis.pipelined();
//	public List<String> locationList;
//	public List<List<String>> locationList2;
//	private ExecutorService executor = Executors.newFixedThreadPool(1);
//
//	private SpeedCache() {
//		try {
//			// BufferedReader br = new BufferedReader(new
//			// FileReader(Constants.WAVE_LOCATIONID_LIST));
//			BufferedReader br = new BufferedReader(new FileReader("locationid.txt"));
//			speedMap = new HashMap<String, String>(55000);
//			String line = null;
//			locationList = new ArrayList<String>();
//			while ((line = br.readLine()) != null) {
//				locationList.add(line);
//				float randomSpeed = (float) (5 + Math.random() * 65);
//				speedMap.put(line, String.valueOf(randomSpeed));
//			}
//			br.close();
//		} catch (NumberFormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 妥协
//		locationList2 = new ArrayList<List<String>>();
//		List<String> tempList = new ArrayList<String>();
//		int i = 0;
//		for (String location : locationList) {
//			if (i < 10000) {
//				tempList.add(location);
//				++i;
//			} else {
//				i = 0;
//				locationList2.add(tempList);
//				tempList = new ArrayList<String>();
//			}
//		}
//		locationList2.add(tempList);
//	}
//
//	public static SpeedCache getInstance() {
//		if (speedcache == null) {
//			synchronized (SpeedCache.class) {
//				if (speedcache == null) {
//					speedcache = new SpeedCache();
//				}
//			}
//		}
//		return speedcache;
//	}
//
//	public String getSpeed(String time, String locationID) {
//		Jedis jedis = null;
//		try {
//			jedis = RedisUtil.getJedis();
//			String ans = jedis.hget(time, locationID);
//			return ans;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			RedisUtil.returnResource(jedis);
//		}
//		return "-1";
//	}
//
//	// 看看返回实体类是不是接受map的，返回map需要2次转换，一次Map<String,Response<String>>，第二次Map<String,String>
//	public Map<String, String> getSpeed(String time, List<String> locationIDs) {
//		Jedis jedis = null;
//		Map<String, String> ans = new HashMap<String, String>();
//		try {
//			jedis = RedisUtil.getJedis();
//			Pipeline pipe = jedis.pipelined();
//			Map<String, Response<String>> response = new HashMap<String, Response<String>>();
//			for (String temp : locationIDs) {
//				response.put(temp, pipe.hget(time, temp));
//			}
//			pipe.sync();
//			for (String temp : locationIDs) {
//				String tempspeed = response.get(temp).get();
//				if (tempspeed == null)
//					ans.put(temp, "-1");
//				else
//					ans.put(temp, tempspeed);
//			}
//			return ans;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			RedisUtil.returnResource(jedis);
//		}
//		return ans;
//	}
//	// public String getSpeed(String locationID) {
//	// if (speedMap.containsKey(locationID))
//	// return speedMap.get(locationID);
//	// else
//	// return "-1";
//	// }
//
//	// public void refresh(String time) {
//	// for (List<String> locationList : locationList2) {
//	// RefreshCache a=new RefreshCache();
//	// a.refresh(locationList, time);
//	// }
//	// }
//	public void refreshCache(final List<String> locationList, final String time) {
//		executor.execute(new Runnable() {
//			@Override
//			public synchronized void run() {
//				System.out.println("Refresh Started");
//				long startTime = System.currentTimeMillis();
//				
//				
////				try {
////					Thread.sleep(1000);
////				} catch (InterruptedException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}				
//				Map<String,String> temp=WaveResponse.getSpeedMap(locationList, time);
//				speedMap.putAll(temp);
//
//
//				long endTime = System.currentTimeMillis();
//				long usedTime = endTime - startTime;
//				System.out.println("Time " + usedTime);
//				System.out.println("after: 1010027082 " + speedMap.get("1010027082") + ";1010027072 " + speedMap.get("1010027072") + ";1010074742 "
//						+ speedMap.get("1010074742") + ";1014188952 " + speedMap.get("1014188952"));
//			}
//		});
//	}
////	public void refreshCache(final List<String> locationList, final String time) {
////		Thread t1 =new Thread(new Runnable() {
////			@Override
////			public synchronized void run() {
////				System.out.println("Refresh Started");
////				long startTime = System.currentTimeMillis();
////				
////				
//////				try {
//////					Thread.sleep(1000);
//////				} catch (InterruptedException e) {
//////					// TODO Auto-generated catch block
//////					e.printStackTrace();
//////				}				
////				Map<String,String> temp=GetESResponse.getSpeedMap(locationList, time);
////				speedMap.putAll(temp);
////
////
////				long endTime = System.currentTimeMillis();
////				long usedTime = endTime - startTime;
////				System.out.println("Time " + usedTime);
////				System.out.println("after: 1010027082 " + speedMap.get("1010027082") + ";1010027072 " + speedMap.get("1010027072") + ";1010074742 "
////						+ speedMap.get("1010074742") + ";1014188952 " + speedMap.get("1014188952"));
////			}
////		});
////		t1.start();
////	}
//
//	// 缓存全部更新
//	public void refresh(String time) {
//		for (List<String> locationList : locationList2) {
//			refreshCache(locationList, time);
//		}
//		refreshRedis(time);
//	}
//
//	private boolean refreshRedis(final String time) {
//		executor.submit(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("RefreshRedis Start" );
//				jedis.hmset(time, speedMap);
//				System.out.println("RefreshRedis Done" );
//			}
//		});	
//		return true;
//	}
//
//	public static void main(String[] args) {
//
//	}
//}
