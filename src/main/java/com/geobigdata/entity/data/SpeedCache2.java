//package com.geobigdata.entity.data;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.geobigdata.utils.Constants;
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
//public class SpeedCache2 {
//	private static volatile SpeedCache2 speedcache;
////	private static Map<String, String> speedMap;
//	static Jedis jedis = RedisUtil.getJedis();
//	static Pipeline pipe = jedis.pipelined();
//	private List<String> locationList;
//	
//	private SpeedCache2() {
//		try {
////			BufferedReader br = new BufferedReader(new FileReader(Constants.WAVE_LOCATIONID_LIST));
//			BufferedReader br = new BufferedReader(new FileReader("locationid.txt"));
////			speedMap = new HashMap<String, String>(55000);
//			String line = null;
//			locationList=new ArrayList<String>();
//			while ((line = br.readLine()) != null) {
//				locationList.add(line);
//				float randomSpeed = (float) (5 + Math.random() * 65);
//				putSpeed(line, String.valueOf(randomSpeed));
//			}
//			pipe.sync();
//			br.close();
//		} catch (NumberFormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public static SpeedCache2 getInstance() {
//		if (speedcache == null) {
//			synchronized (SpeedCache2.class) {
//				if (speedcache == null) {
//					speedcache = new SpeedCache2();
//				}
//			}
//		}
//		return speedcache;
//	}
//
//	public String getSpeed(String locationID) {
//		String ans=jedis.get(locationID);
//		if(ans==null)
//			return "-1";
//		return ans;
//	}
////	看看返回实体类是不是接受map的，返回map需要2次转换，一次Map<String,Response<String>>，第二次Map<String,String>
//	public Map<String,String> getSpeed(List<String> locationIDs) {
//		Map<String, Response<String>> response= new HashMap<String, Response<String>>();
//		for(String temp:locationIDs) {
//			response.put(temp, pipe.get(temp));
//		}
//		pipe.sync();
//		Map<String, String> ans= new HashMap<String, String>();
//		for(String temp:locationIDs) {
//			ans.put(temp, response.get(temp).get());
//		}
//		return ans;
//	}
////	public String getSpeed(String locationID) {
////		if (speedMap.containsKey(locationID))
////			return speedMap.get(locationID);
////		else
////			return "-1";
////	}
//
//	private void putSpeed(String locationID, String speed) {
////		speedMap.put(locationID, speed);
//		pipe.set(locationID, speed);
//	}
//
//	public static void main(String[] args) {
//		SpeedCache2 speedCache = SpeedCache2.getInstance();
//		System.out.println(speedCache.getSpeed("1018487902"));
////		System.out.println(jedis.get("1018487902"));
//		System.out.println(speedCache.getSpeed("1014859802"));
//		System.out.println(speedCache.getSpeed("1013445263"));
//		System.out.println(speedCache.getSpeed("1011601452"));
//		System.out.println(speedCache.getSpeed("1016970142"));
//		System.out.println(speedCache.getSpeed("1016952742"));
//		System.out.println(speedCache.getSpeed("1013766652"));
//		System.out.println(speedCache.getSpeed("1017265052"));
//		System.out.println(speedCache.getSpeed("1014120922"));
//		System.out.println(speedCache.getSpeed("1010011712"));
//		System.out.println(speedCache.getSpeed("1010011712222L"));
//	}
//}
