package com.geobigdata.entity.data.wave;

import com.geobigdata.entity.data.Returns;

/*
 * @author pjl
 * 单条道路的速度信息
 */
//public class RoadInfo extends Returns{
//	private String speed;
//	private String locationID;
//	
//	public RoadInfo(String locationID,String speed) {
//		this.locationID=locationID;
//		this.speed=speed;
//	}
//	public String getSpeed() {
//		return speed;
//	}
//	public void setSpeed(String speed) {
//		this.speed=speed;
//	}
//	public String getLocationID() {
//		return locationID;
//	}
//	public void setLocationID(String locationID) {
//		this.locationID=locationID;
//	}
//}
//旧版兼容
public class RoadInfo extends Returns{
	private float speed;
	private int locationID;
	
	public RoadInfo(String locationID,String speed) {
		this.locationID=Integer.valueOf(locationID);
		this.speed=Float.valueOf(speed);
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed=speed;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID=locationID;
	}
}
