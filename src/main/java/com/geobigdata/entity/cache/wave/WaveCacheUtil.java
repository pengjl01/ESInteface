package com.geobigdata.entity.cache.wave;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.geobigdata.utils.RedisUtil;
import com.geobigdata.utils.Constants.GlobalConstants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/*
 * @author pjl
 * @version 创建时间：2019年2月20日 上午11:50:01
 * 类说明
 */
public class WaveCacheUtil {

	public static String getCache(String locationID, String time) {
		Jedis jedis = null;
		try {
			jedis = RedisUtil.getJedis();
			String ans = jedis.hget(time, locationID);
			if (ans == null)
				return "0";
			return ans;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisUtil.returnResource(jedis);
		}
		return null;
	}

	// 看看返回实体类是不是接受map的，返回map需要2次转换，一次Map<String,Response<String>>，第二次Map<String,String>
	public static Map<String, String> getCache(List<String> locationIDs, String time) {
		Jedis jedis = null;
		Map<String, String> ans = new HashMap<String, String>();
		try {
			jedis = RedisUtil.getJedis();
			Pipeline pipe = jedis.pipelined();
			Map<String, Response<String>> response = new HashMap<String, Response<String>>();
			for (String temp : locationIDs) {
				response.put(temp, pipe.hget(time, temp));
			}
			pipe.sync();
			for (String temp : locationIDs) {
				String tempspeed = response.get(temp).get();
				if (tempspeed == null)
					ans.put(temp, "0");
				else
					ans.put(temp, tempspeed);
			}
			return ans;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisUtil.returnResource(jedis);
		}
		return ans;
	}

	public static Map<String, String> getAllCache(String time) {
		Jedis jedis = RedisUtil.getJedis();
		Map<String, String> ans = jedis.hgetAll(time);
		RedisUtil.returnResource(jedis);
		return ans;
	}

	// 将时间向上取整至半小时yyyymmddhhmmss
	// 时间已经通过转换测试
	public static String timeTrans(String time) {
		String hour = time.substring(0, 10);
		int minutes = Integer.valueOf(time.substring(10, 12));
		if (minutes < 30)
			return hour + "3000";
		else {
			Date time1;
			SimpleDateFormat SDF = new SimpleDateFormat(GlobalConstants.SDFSTR);
			try {
				time1 = SDF.parse(hour + "0000");
				time1.setTime(time1.getTime() + 3600000);
				return SDF.format(time1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	// 半小时前的时间
	public static String preTime(String time) {
		Date time1;
		SimpleDateFormat SDF = new SimpleDateFormat(GlobalConstants.SDFSTR);
		try {
			time1 = SDF.parse(time);
			time1.setTime(time1.getTime() - 1800000);
			return SDF.format(time1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 半小时后的时间
	public static String afterTime(String time) {
		Date time1;
		SimpleDateFormat SDF = new SimpleDateFormat(GlobalConstants.SDFSTR);
		try {
			time1 = SDF.parse(time);
			time1.setTime(time1.getTime() + 1800000);
			return SDF.format(time1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 判定是否有缓存
	public static boolean haveCache(String time) {
		Jedis jedis = RedisUtil.getJedis();
		boolean ans = jedis.exists(time);
		RedisUtil.returnResource(jedis);
		return ans;
	}

	// 计算进入未缓存区域的时间
	public static long deltaTime(String timeOrigin) {
		String time = WaveCacheUtil.timeTrans(timeOrigin);
		SimpleDateFormat SDF = new SimpleDateFormat(GlobalConstants.SDFSTR);
		try {
			Date t0 = SDF.parse(timeOrigin);
			Date t1 = SDF.parse(time);
			return (t1.getTime() - t0.getTime()) / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 10000;
	}

	public static void main(String[] args) {
//		// String time="20180205144000";
//		// System.out.println(timeTrans(time));
//		String time = "20180204133000";
//		Jedis jedis = RedisUtil.getJedis();
//		System.out.println(jedis.exists(time));
//		Map<String, String> ans = new HashMap<String, String>();
//		Map<String, String> ans1 = new HashMap<String, String>();
//		Map<String, String> ans2 = new HashMap<String, String>();
//		List<String> a = new ArrayList<>();
//		a.add("1010353932");
//		a.add("1010027082");
//		a.add("1014188952");
//		a.add("1010353932");
//
//		long startTime1 = System.currentTimeMillis();
//		ans2 = getCache(a, time);
//		long endTime1 = System.currentTimeMillis();
//		long usedTime1 = endTime1 - startTime1;
//		System.out.println("get2 " + usedTime1);
//		long startTime2 = System.currentTimeMillis();
//		ans1 = getAllCache(time);
//		long endTime2 = System.currentTimeMillis();
//		long usedTime2 = endTime2 - startTime2;
//		System.out.println("getAll " + usedTime2);
//		long startTime = System.currentTimeMillis();
//		ans = getCache(SpeedCache.getInstance().fullList, time);
//		long endTime = System.currentTimeMillis();
//		long usedTime = endTime - startTime;
//		System.out.println("get " + usedTime);
//		RedisUtil.returnResource(jedis);
	}

}
