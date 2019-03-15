package com.geobigdata.entity.data.wave;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.geobigdata.entity.data.Returns;

/*
 * @author pjl
 * 多条道路的速度信息数组
 */
public class MutliRoadInfo {
	private List<Returns> speeds = new ArrayList<Returns>();

	public MutliRoadInfo(Map<String, String> speedMap) {
		Iterator<Entry<String, String>> iter = speedMap.entrySet().iterator();
		while (iter.hasNext()) {
		Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
		RoadInfo wsi=new RoadInfo(entry.getKey(),entry.getValue());
		speeds.add(wsi);
		}
	}

	public List<Returns> getSpeeds() {
		return speeds;
	}

	public void setSpeeds(List<Returns> speeds) {
		this.speeds = speeds;
	}

	public void addWaveSpeed(Returns waveSpeed) {
		speeds.add(waveSpeed);
	}

	public String toJSON() {
		return JSON.toJSONString(this);
	}
}
