package com.geobigdata.utils.Constants;

/*
 * @author pjl
 * @version 创建时间：2019年1月16日 上午11:38:06
 * 类说明
 */
public class TaxiConstants {
	// TAXI
		public static final String TAXI = "taxi";
		public static final int TAXI_MAX_NUM = 100;// 最大的区域内出租车数
		public static final int TAXI_DELTA_SECOND = 60;// 向前推的查询时间间隔
		// 以下为接口GET location的参数
		public static final String TAXI_GET_LOCATION_PARAM0 = "top";
		public static final String TAXI_GET_LOCATION_PARAM1 = "left";
		public static final String TAXI_GET_LOCATION_PARAM2 = "bottom";
		public static final String TAXI_GET_LOCATION_PARAM3 = "right";
		public static final String TAXI_GET_LOCATION_PARAM4 = "time";
		public static final String TAXI_GET_LOCATION_PARAM = "location/{" + TAXI_GET_LOCATION_PARAM0 + "}/{"
				+ TAXI_GET_LOCATION_PARAM1 + "}/{" + TAXI_GET_LOCATION_PARAM2 + "}/{" + TAXI_GET_LOCATION_PARAM3 + "}/{"
				+ TAXI_GET_LOCATION_PARAM4 + "}";
		// 以下为接口GET path的参数
		public static final String TAXI_GET_PATH_PARAM0 = "uid";
		public static final String TAXI_GET_PATH_PARAM1 = "starttime";
		public static final String TAXI_GET_PATH_PARAM2 = "endtime";
		public static final String TAXI_GET_PATH_PARAM = "path/{" + TAXI_GET_PATH_PARAM0 + "}/{" + TAXI_GET_PATH_PARAM1
				+ "}/{" + TAXI_GET_PATH_PARAM2 + "}";
		// 以下为接口GET areapathold的参数
		public static final String TAXI_GET_AREAPATH_PARAM0 = "top";
		public static final String TAXI_GET_AREAPATH_PARAM1 = "left";
		public static final String TAXI_GET_AREAPATH_PARAM2 = "bottom";
		public static final String TAXI_GET_AREAPATH_PARAM3 = "right";
		public static final String TAXI_GET_AREAPATH_PARAM4 = "starttime";
		public static final String TAXI_GET_AREAPATH_PARAM5 = "endtime";
		public static final String TAXI_GET_AREAPATH_PARAM = "areapathold/{" + TAXI_GET_AREAPATH_PARAM0 + "}/{"
				+ TAXI_GET_AREAPATH_PARAM1 + "}/{" + TAXI_GET_AREAPATH_PARAM2 + "}/{" + TAXI_GET_AREAPATH_PARAM3 + "}/{"
				+ TAXI_GET_AREAPATH_PARAM4 + "}/{" + TAXI_GET_AREAPATH_PARAM5 + "}";

		// 以下为接口GET areapath的参数
		public static final String TAXI_GET_AREAPATH2_PARAM0 = "top";
		public static final String TAXI_GET_AREAPATH2_PARAM1 = "left";
		public static final String TAXI_GET_AREAPATH2_PARAM2 = "bottom";
		public static final String TAXI_GET_AREAPATH2_PARAM3 = "right";
		public static final String TAXI_GET_AREAPATH2_PARAM4 = "time";
		public static final String TAXI_GET_AREAPATH2_PARAM5 = "starttime";
		public static final String TAXI_GET_AREAPATH2_PARAM6 = "endtime";
		public static final String TAXI_GET_AREAPATH2_PARAM = "areapath/{" + TAXI_GET_AREAPATH2_PARAM0 + "}/{"
				+ TAXI_GET_AREAPATH2_PARAM1 + "}/{" + TAXI_GET_AREAPATH2_PARAM2 + "}/{" + TAXI_GET_AREAPATH2_PARAM3 + "}/{"
				+ TAXI_GET_AREAPATH2_PARAM4 + "}/{" + TAXI_GET_AREAPATH2_PARAM5 + "}/{" + TAXI_GET_AREAPATH2_PARAM6 + "}";
		// 以下为ES中的字段名
		public static final String TAXI_TIME = "genTime";
		public static final String TAXI_LOCATION = "location";
		public static final String TAXI_ID = "uid";
		public static final String TAXI_DIRECTION = "direction";
		
		public static final String TAXI_GET_LOCATION_PARAM0_PARAM2_ERROR = "Parameter " + TAXI_GET_LOCATION_PARAM0
				+ " is below " + "Parameter " + TAXI_GET_LOCATION_PARAM2 + "corner";
		public static final String TAXI_GET_LOCATION_PARAM1_PARAM3_ERROR = "Parameter " + TAXI_GET_LOCATION_PARAM1
				+ " cannot be the same as " + "Parameter " + TAXI_GET_LOCATION_PARAM3;
		public static final String TAXI_GET_LOCATION_PARAM4_ERROR = "Parameter " + TAXI_GET_LOCATION_PARAM4
				+ " should be like this:20180302084000.";
}
