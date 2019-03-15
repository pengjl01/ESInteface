package com.geobigdata.entity.data.camera;

import java.util.Map;

import com.geobigdata.utils.Constants.GlobalConstants;

/*
 * @author pjl
 * 摄像头记录的某一次违章信息
 */
public class CameraPeccancyInfo {
	private String cphm;
	private String time;
	private String zfbh;
	
	public CameraPeccancyInfo(Map<String, Object> map) {
		cphm = map.get(GlobalConstants.CAMERA_CPHM).toString();
		time = map.get(GlobalConstants.CAMERA_TIME).toString();
		zfbh = map.get(GlobalConstants.CAMERA_ZFBH).toString();
	}
	public String getCphm() {
		return cphm;
	}
	public void setCphm(String cphm) {
		this.cphm=cphm;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time=time;
	}
	public String getZfbh() {
		return zfbh;
	}
	public void setZfbh(String zfbh) {
		this.zfbh=zfbh;
	}
}
