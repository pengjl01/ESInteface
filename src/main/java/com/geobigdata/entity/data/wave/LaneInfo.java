package com.geobigdata.entity.data.wave;

import java.util.Map;

import com.geobigdata.utils.Constants.WaveConstants;
/*
 * @author pjl
 * 微波数据某一个车道的信息
 */
public class LaneInfo {
	private byte laneno;
	private int volume;
	private float speed;

	public LaneInfo(Map<String, Object> map) {
		laneno = Byte.valueOf(map.get(WaveConstants.WAVE_LANE_NO).toString());
		volume = Integer.valueOf(map.get(WaveConstants.WAVE_VOLUME).toString());
		speed = Float.valueOf(map.get(WaveConstants.WAVE_LANE_SPEED).toString());
	}

	public byte getLaneno() {
		return laneno;
	}

	public void setLaneno(byte laneno) {
		this.laneno = laneno;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
}
