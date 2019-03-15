package com.geobigdata.entity.data.camera;

import java.util.ArrayList;
import java.util.List;
/*
 * @author pjl
 * 某一个摄像头的记录
 */
public class CameraCamInfo {
	private String camID;
	private List<CameraPeccancyInfo> peccancys=new ArrayList<CameraPeccancyInfo>();
	
	public CameraCamInfo(String camID) {
		this.camID=camID;
	}
	public String getCamID() {
		return camID;
	}
	public void setCamID(String camID) {
		this.camID=camID;
	}
	public List<CameraPeccancyInfo> getPeccancys(){
		return peccancys;
	}
	public void setPeccancys(List<CameraPeccancyInfo> peccancys) {
		this.peccancys=peccancys;
	}
	public void addCameraPeccancy(CameraPeccancyInfo cameraPeccancy) {
		peccancys.add(cameraPeccancy);
	}
}
