package com.geobigdata.entity.data.taxi;

import java.util.Map;

import com.geobigdata.utils.Constants.TaxiConstants;
/*
 * @author pjl
 * 单个出租车的位置信息
 */
public class TaxiLocationInfo {
	private String uid;
	private double lat;
	private double lon;
	private int direction;
	
	public TaxiLocationInfo(Map<String, Object> map) {
		uid = map.get(TaxiConstants.TAXI_ID).toString();
		String[] locations = map.get(TaxiConstants.TAXI_LOCATION).toString().split(",");
		lat = Double.valueOf(locations[0]);
		lon = Double.valueOf(locations[1]);
		direction=Integer.valueOf(map.get(TaxiConstants.TAXI_DIRECTION).toString());
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid=uid;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat=lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon=lon;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction=direction;
	}
}
