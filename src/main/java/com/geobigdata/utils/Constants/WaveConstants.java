package com.geobigdata.utils.Constants;

/*
 * @author pjl
 * @version 创建时间：2019年1月16日 上午11:38:06
 * 类说明
 */
public class WaveConstants {
	// WAVE
		public static final String WAVE = "wave";
		public static final int WAVE_MAX_LANE = 20;// 最大的车道数
		public static final int WAVE_DELTA_SECOND = 1800;// 向前推的查询时间间隔
		// 缓存相关
		public static final String WAVE_LOCATIONID_LIST_FILE = "/usr/share/apache-tomcat-9.0.12/interfacedata/locationid.txt";
//		public static final String WAVE_LOCATIONID_LIST_FILE = "locationid.txt";
//		public static final List<String> WAVE_LOCATIONID_LIST=readLocationIDFile();
		// 以下为接口POST trafficstatus的參數
		public static final String WAVE_POST_TRAFFICSTATUS_MULTI_PARAM = "trafficstatus";
		public static final String WAVE_POST_TRAFFICSTATUS_MULTI_PARAM0 = "locationIDs";
		public static final String WAVE_POST_TRAFFICSTATUS_MULTI_PARAM1 = "time";
		// 以下为接口GET trafficstatus的參數
		public static final String WAVE_GET_TRAFFICSTATUS_PARAM0 = "locationID";
		public static final String WAVE_GET_TRAFFICSTATUS_PARAM1 = "time";
		public static final String WAVE_GET_TRAFFICSTATUS_PARAM = "trafficstatus/{" + WAVE_GET_TRAFFICSTATUS_PARAM0 + "}/{"
				+ WAVE_GET_TRAFFICSTATUS_PARAM1 + "}";
		// 以下为接口GET lane的參數
		public static final String WAVE_GET_LANE_PARAM0 = "locationID";
		public static final String WAVE_GET_LANE_PARAM1 = "time";
		public static final String WAVE_GET_LANE_PARAM = "lane/{" + WAVE_GET_TRAFFICSTATUS_PARAM0 + "}/{"
				+ WAVE_GET_TRAFFICSTATUS_PARAM1 + "}";
		// 以下为ES中的字段名
		public static final String WAVE_LOCATION_ID = "locationID";
		public static final String WAVE_TIME = "rectime";
		public static final String WAVE_AVG_PATHLINE_SPEED = "AvgSpeed";
		public static final String WAVE_LANE_SPEED = "speed";
		public static final String WAVE_LANE_NO = "laneno";
		public static final String WAVE_VOLUME = "volume";
		
		public static final String WAVE_GET_TRAFFICSTATUS_MULTI_PARAM0_ERROR = "Parameter "
				+ WaveConstants.WAVE_POST_TRAFFICSTATUS_MULTI_PARAM0
				+ " should be like this:[1601830,1601831].\nEnsure that every element is a Long.";
		public static final String WAVE_GET_TRAFFICSTATUS_MULTI_PARAM1_ERROR = "Parameter "
				+ WaveConstants.WAVE_POST_TRAFFICSTATUS_MULTI_PARAM1 + " should be like this:20180302084000.";
		public static final String WAVE_GET_LANE_PARAM1_ERROR = "Parameter " + WaveConstants.WAVE_GET_LANE_PARAM1
				+ " should be like this:20180302084000.";
		public static final String WAVE_GET_TRAFFICSTATUS_PARAM1_ERROR = "Parameter " + WaveConstants.WAVE_GET_LANE_PARAM1
				+ " should be like this:20180302084000.";
		
//		private static List<String> readLocationIDFile() {
//			List<String> locationList = new ArrayList<String>();
//			try {
//				BufferedReader br = new BufferedReader(new FileReader(WAVE_LOCATIONID_LIST_FILE));
//				String line = null;
//				while ((line = br.readLine()) != null) {
//					locationList.add(line);
//				}
//				br.close();
//			} catch (NumberFormatException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return locationList;
//		}
}
