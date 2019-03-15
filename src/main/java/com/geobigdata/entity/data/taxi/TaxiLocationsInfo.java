package com.geobigdata.entity.data.taxi;

import java.util.ArrayList;
import java.util.List;

import com.geobigdata.entity.data.Returns;

/*
 * @author pjl
 * 一组出租车的位置信息列表
 */
public class TaxiLocationsInfo extends Returns {
	private List<TaxiLocationInfo> locations = new ArrayList<TaxiLocationInfo>();

	public List<TaxiLocationInfo> getLocations() {
		return locations;
	}

	public void setLocations(List<TaxiLocationInfo> locations) {
		this.locations = locations;
	}

	public void addTaxiLocation(TaxiLocationInfo taxiLocation) {
		locations.add(taxiLocation);
	}

	public void checkUp(double top, double left, double bottom, double right, String time) {

	}
}
