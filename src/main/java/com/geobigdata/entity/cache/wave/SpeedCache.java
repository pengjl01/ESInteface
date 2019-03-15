package com.geobigdata.entity.cache.wave;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.geobigdata.dao.WaveResponse;
import com.geobigdata.utils.RedisUtil;
import com.geobigdata.utils.Constants.WaveConstants;

import redis.clients.jedis.Jedis;

/*
 * @author pjl
 * @version 创建时间：2019年1月9日 下午5:57:23
 * 类说明
 */
public class SpeedCache {
	private int POOL_SIZE = 6;
	private static int LOCATIONID_SLICE = 1000;
	private ExecutorService executor;
	private static volatile SpeedCache speedCache;
	private long PRE_REFRESH = 600;// 预刷新时间
	private int EXPIRE = 1800;// redis过期时间

	public final List<String> fullList;
	public final List<List<String>> locationIDList;

	private SpeedCache() {
		executor = Executors.newFixedThreadPool(POOL_SIZE);
		fullList = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(WaveConstants.WAVE_LOCATIONID_LIST_FILE));
			String line = null;
			while ((line = br.readLine()) != null) {
				fullList.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(System.getProperty("user.dir"));
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		locationIDList = new ArrayList<List<String>>();
		List<String> tempList = new ArrayList<String>();
		int i = 0;
		for (String location : fullList) {
			if (i < LOCATIONID_SLICE) {
				tempList.add(location);
				++i;
			} else {
				i = 0;
				locationIDList.add(tempList);
				tempList = new ArrayList<String>();
			}
		}
		locationIDList.add(tempList);
	}

	public static SpeedCache getInstance() {
		if (speedCache == null) {
			synchronized (SpeedCache.class) {
				if (speedCache == null) {
					speedCache = new SpeedCache();
				}
			}
		}
		return speedCache;
	}

	// 查询方法
	public Map<String, String> getSpeedMap(List<String> locationIDs, String timeOrigin) {
		String time = WaveCacheUtil.timeTrans(timeOrigin);
		Map<String, String> ans;
		if (WaveCacheUtil.haveCache(time)) {
			ans=WaveCacheUtil.getCache(locationIDs, time);
		}
		else {
			ans = WaveResponse.getSpeedMap(locationIDs, time);
			refresh(time, ans);
		}
		if (WaveCacheUtil.deltaTime(timeOrigin) < PRE_REFRESH) {
			String nextTime = WaveCacheUtil.afterTime(time);
			if (!WaveCacheUtil.haveCache(nextTime))
				refresh(nextTime, null);
		}
		return ans;
	}
	
	// 查询方法
	public String getSpeed(String locationID, String timeOrigin) {
		String time = WaveCacheUtil.timeTrans(timeOrigin);
		String ans;
		if (WaveCacheUtil.haveCache(time)) {
			ans=WaveCacheUtil.getCache(locationID, time);
		}
		else {
			ans = String.valueOf(WaveResponse.getSpeed(locationID, time));
			Map<String, String> temp = new HashMap<>();
			temp.put(locationID, ans);
			refresh(time, temp);
		}
		if (WaveCacheUtil.deltaTime(timeOrigin) < PRE_REFRESH) {
			String nextTime = WaveCacheUtil.afterTime(time);
			if (!WaveCacheUtil.haveCache(nextTime))
				refresh(nextTime, null);
		}
		return ans;
	}

	private void refresh(final String time, final Map<String, String> ans) {
		Thread t=new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, String> randomMap = new HashMap<>();
				for (String ID : fullList) {
					float randomSpeed = (float) (5 + Math.random() * 65);
					randomMap.put(ID, String.valueOf(randomSpeed));
				}
				if (ans != null)
					randomMap.putAll(ans);
				redisRefresh(time, randomMap);
				for (final List<String> locationList : locationIDList) {
					executor.submit(new Runnable() {
						@Override
						public void run() {
							try {
								Map<String, String> esData = WaveResponse.getSpeedMap(locationList, time);
								redisRefresh(time, esData);
								System.out.println(executor.toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		t.start();
	}

	private void redisRefresh(String time, Map<String, String> data) {
		if (data!=null&&!data.isEmpty()) {
			Jedis jedis = RedisUtil.getJedis();
			jedis.hmset(time, data);
			jedis.expire(time, EXPIRE);
			RedisUtil.returnResource(jedis);
		}

	}

	public static void main(String[] args) {
		 String time="20180204115100";
		 System.out.println(WaveCacheUtil.deltaTime(time));
		 if (WaveCacheUtil.deltaTime(time) < 600) {
			 System.out.println("true");
		 }
//		Map<String, String> a = new HashMap<>();
//		a.put("1", "1");
//		a.put("2", "2");
//		a.put("3", "3");
//		a.put("4", "4");
//		a.put("5", "5");
//		a.put("6", "6");
//		Map<String, String> b = new HashMap<>();
//		b.put("2", "3");
//		b.put("3", "4");
//		b.put("6", "7");
//		Jedis jedis = RedisUtil.getJedis();
//		jedis.hmset("test", a);
//		// jedis = RedisUtil.getJedis();
//		jedis.hmset("test", b);
//		b = jedis.hgetAll("test");
//		System.out.println(jedis.exists("20180205114100"));
//		RedisUtil.returnResource(jedis);
	}
}

// public class SpeedCache {
// private int POOL_SIZE = 2;
// private ExecutorService executor;
// private static volatile SpeedCache speedCache;
//
// private SpeedCache() {
// executor = Executors.newFixedThreadPool(POOL_SIZE);
// }
//
// public static SpeedCache getInstance() {
// if (speedCache == null) {
// synchronized (SpeedCache.class) {
// if (speedCache == null) {
// speedCache = new SpeedCache();
// }
// }
// }
// return speedCache;
// }
//
//// 查询方法
// public Map<String, String> getSpeedMap(List<String> locationIDs, String time)
// {
// String time2 = WaveCacheUtil.timeTrans(time);
// if (WaveCacheUtil.haveTime(time2))
// return WaveCacheUtil.getCache(locationIDs, time2);
// else {
// executor.submit(new RefreshCache(time2));
// return WaveResponse.getSpeedMap(locationIDs, time2);
// }
// }
//
// public static void main(String[] args) {
// // String time="19880415184256";
// // System.out.println(timeTrans(time));
// Jedis jedis = RedisUtil.getJedis();
// System.out.println(jedis.exists("20180205114100"));
// }
// }
