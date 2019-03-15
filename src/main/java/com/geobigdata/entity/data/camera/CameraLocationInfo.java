package com.geobigdata.entity.data.camera;

import java.util.ArrayList;
import java.util.List;

import com.geobigdata.entity.data.Returns;
/*
 * @author pjl
 * 一个locationId下所有摄像头的记录
 */
public class CameraLocationInfo extends Returns{
	private List<CameraCamInfo> cams=new ArrayList<CameraCamInfo>();
	
	public List<CameraCamInfo> getCams(){
		return cams;
	}
	public void setCams(List<CameraCamInfo> cams) {
		this.cams=cams;
	}
	public void addCameraCam(CameraCamInfo cameraCam) {
		cams.add(cameraCam);
	}
}
