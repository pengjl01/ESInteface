package com.geobigdata.ESInterfaceApp;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geobigdata.dao.WaveResponse;
import com.geobigdata.entity.cache.wave.SpeedCache;
import com.geobigdata.entity.data.QueryError;
import com.geobigdata.entity.data.wave.MutliLaneInfo;
import com.geobigdata.entity.data.wave.RoadInfo;
import com.geobigdata.entity.data.wave.MutliRoadInfo;
import com.geobigdata.utils.Constants.WaveConstants;
import com.geobigdata.utils.MyUtil;

/*
 * @author pjl
 * @version 创建时间：2018年10月29日 下午15:21:45
 * wave接口
 */
@Path(WaveConstants.WAVE)
public class Wave {
	/*
	 * POST方法
	 * 
	 * 在BODY中以JSON方式发送待查询列表
	 * 
	 * @return 速度的JSON数组
	 */
	// 每个接口都需看看是否可能返回null，wave接口需先查缓存
	@POST
	@Path(WaveConstants.WAVE_POST_TRAFFICSTATUS_MULTI_PARAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String trafficStatusMulti(MultivaluedMap<String, String> formParams) {
		String lIDs = formParams.getFirst(WaveConstants.WAVE_POST_TRAFFICSTATUS_MULTI_PARAM0);
		String time = formParams.getFirst(WaveConstants.WAVE_POST_TRAFFICSTATUS_MULTI_PARAM1);
		List<String> locationIDs;
		try {
			locationIDs = JSONObject.parseArray(lIDs, String.class);
		} catch (Exception e) {
			return new QueryError(WaveConstants.WAVE_GET_TRAFFICSTATUS_MULTI_PARAM0_ERROR).toJSON();
		}
		if (!MyUtil.checkTime(time))
			return new QueryError(WaveConstants.WAVE_GET_TRAFFICSTATUS_MULTI_PARAM1_ERROR).toJSON();
		MutliRoadInfo mri;
		SpeedCache speedCache =SpeedCache.getInstance();
		mri = new MutliRoadInfo(speedCache.getSpeedMap(locationIDs, time));
		return JSON.toJSONString(mri);
	}

	/*
	 * GET方法
	 * 
	 * @param 路径参数，待查道路ID和时间
	 * 
	 * @return 速度的JSON
	 */
	@GET
	@Path(WaveConstants.WAVE_GET_TRAFFICSTATUS_PARAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String trafficstatus(@PathParam(WaveConstants.WAVE_GET_TRAFFICSTATUS_PARAM0) String locationID,
			@PathParam(WaveConstants.WAVE_GET_TRAFFICSTATUS_PARAM1) String time) {
		if (!MyUtil.checkTime(time))
			return new QueryError(WaveConstants.WAVE_GET_TRAFFICSTATUS_PARAM1_ERROR).toJSON();
		SpeedCache speedCache =SpeedCache.getInstance();
		String speed=speedCache.getSpeed(locationID, time);
		RoadInfo ri;
		if (speed.equals("0")) {
			ri = new RoadInfo(locationID, "0");
			ri.setStatus(1);
		} else
			ri = new RoadInfo(locationID, speed);
		return ri.toJSON();
	}

	/*
	 * GET方法
	 * 
	 * @param 路径参数，待查道路ID和时间
	 * 
	 * @return JSON数组，待查道路对应时间的各车道状况
	 */
	@GET
	@Path(WaveConstants.WAVE_GET_LANE_PARAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String lane(@PathParam(WaveConstants.WAVE_GET_LANE_PARAM0) String locationID,
			@PathParam(WaveConstants.WAVE_GET_LANE_PARAM1) String time) {
		if (!MyUtil.checkTime(time))
			return new QueryError(WaveConstants.WAVE_GET_LANE_PARAM1_ERROR).toJSON();
		MutliLaneInfo mli = WaveResponse.getMutliLaneInfo(locationID, time);
		return mli.toJSON();
	}
}
