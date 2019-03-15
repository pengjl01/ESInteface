package com.geobigdata.entity.data.taxi;

import java.util.ArrayList;
import java.util.List;

import com.geobigdata.entity.data.Returns;

/*
 * @author pjl
 * @version 创建时间：2018年11月13日 下午12:00:38
 * 出租车轨迹（点集）
 */
public class TaxiPath extends Returns {
	private long uid;
	private List<TaxiPoint> path=new ArrayList<TaxiPoint>();
	
	public TaxiPath(long uid) {
		this.uid=uid;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid=uid;
	}
	public List<TaxiPoint> getLocations(){
		return path;
	}
	public void setLocations(List<TaxiPoint> locations) {
		this.path=locations;
	}
	public void addTaxiPoint(TaxiPoint taxiLocation) {
		path.add(taxiLocation);
	}
}
