package com.geobigdata.utils.Constants;

/*
 * @author pjl
 * 常数类
 */
public class GlobalConstants {
	// ES服务器相关
	public static final String ES_SERVER_ADDRESS = "172.18.20.218";
	public static final int ES_SERVER_PORT = 9200;
	public static final int ES_SERVER_PORT1 = 9201;
	public static final String ES_SERVER_SCHEME = "http";
	public static final int RHLCNUM=8;

	// 查询全局相关
	public static final String SDFSTR = "yyyyMMddHHmmss";
	public static final String BY_TIME = "bytime";
	public static final String BY_ID = "byid";

	// CAMERA
	public static final String CAMERA = "camera";
	public static final int CAMERA_MAX_NUM = 100;// 单次查询
	// 以下为接口GET peccancy的参数
	public static final String CAMERA_GET_PECCANCY_PRAM0 = "locationID";
	public static final String CAMERA_GET_PECCANCY_PRAM1 = "starttime";
	public static final String CAMERA_GET_PECCANCY_PRAM2 = "endtime";
	public static final String CAMERA_GET_PECCANCY_PRAM = "peccancy/{" + CAMERA_GET_PECCANCY_PRAM0 + "}/{"
			+ CAMERA_GET_PECCANCY_PRAM1 + "}/{" + CAMERA_GET_PECCANCY_PRAM2 + "}";
	// 以下为ES中的字段名
	public static final String CAMERA_LOCATION_ID = "locationID";
	public static final String CAMERA_ID = "camID";
	public static final String CAMERA_TIME = "wjID";
	public static final String CAMERA_CPHM = "cphm";
	public static final String CAMERA_ZFBH = "zfbh";

}
