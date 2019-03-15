package com.geobigdata.entity.data.wave;

import java.util.ArrayList;
import java.util.List;
import com.geobigdata.entity.data.Returns;
/*
 * @author pjl
 * 微波数据整条路的位置信息（含多个车道）
 */
public class MutliLaneInfo extends Returns{
	private List<LaneInfo> lanes=new ArrayList<LaneInfo>();
	
	public List<LaneInfo> getLanes(){
		return lanes;
	}
	public void setLanes(List<LaneInfo> lanes) {
		this.lanes=lanes;
	}
	public void addLane(LaneInfo waveLane) {
		lanes.add(waveLane);
	}
	public void clearList() {
		lanes.clear();
	}
}
