package com.geobigdata.entity.data.taxi;
/*
 * @author pjl
 * @version 创建时间：2018年11月13日 上午11:59:30
 * 单个出租车的位置点
 */

import java.util.Map;

import com.geobigdata.utils.Constants.TaxiConstants;

public class TaxiPoint {
	private double lat;
	private double lon;
	private int direction;
	private String time;
	
	public TaxiPoint(Map<String, Object> map) {
		String[] locations = map.get(TaxiConstants.TAXI_LOCATION).toString().split(",");
		lat = Double.valueOf(locations[0]);
		lon = Double.valueOf(locations[1]);
		direction=Integer.valueOf(map.get(TaxiConstants.TAXI_DIRECTION).toString());
		time=map.get(TaxiConstants.TAXI_TIME).toString();
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
	public String getTime() {
		return time;
	}
	public void settime(String time) {
		this.time=time;
	}
}
