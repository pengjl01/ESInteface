package com.geobigdata.utils;

import java.text.SimpleDateFormat;

/*
 * @author pjl
 * @version 创建时间：2019年1月16日 下午4:44:16
 * 工具类
 */
public class MyUtil {

	public static boolean checkTime(String time) {
		try {
			SimpleDateFormat SDF = new SimpleDateFormat(com.geobigdata.utils.Constants.GlobalConstants.SDFSTR);
			SDF.parse(time);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
