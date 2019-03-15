package com.geobigdata.ESInterfaceApp;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.geobigdata.dao.CameraResponse;
import com.geobigdata.entity.data.QueryError;
import com.geobigdata.entity.data.camera.CameraCamInfo;
import com.geobigdata.entity.data.camera.CameraLocationInfo;
import com.geobigdata.entity.data.camera.CameraPeccancyInfo;
import com.geobigdata.utils.Errors;
import com.geobigdata.utils.Constants.GlobalConstants;

/*
 * @author pjl
 * @version 创建时间：2018年11月1日 下午12:46:37
 * camera接口
 */
@Path(GlobalConstants.CAMERA)
public class Camera {
	/*
	 * GET方法
	 * 
	 * @param 路径参数，待查矩形边界坐标，待查时间
	 * 
	 * @return taxi的坐标JSON数组
	 */
	@GET
	@Path(GlobalConstants.CAMERA_GET_PECCANCY_PRAM)
	@Produces(MediaType.APPLICATION_JSON)
	public String taxiLocation(@PathParam(GlobalConstants.CAMERA_GET_PECCANCY_PRAM0) long locationId,
			@PathParam(GlobalConstants.CAMERA_GET_PECCANCY_PRAM1) String starttime,
			@PathParam(GlobalConstants.CAMERA_GET_PECCANCY_PRAM2) String endtime) {
		try {
			SimpleDateFormat SDF = new SimpleDateFormat(GlobalConstants.SDFSTR);
			SDF.parse(starttime);
			SDF.parse(endtime);
		} catch (Exception e) {
			return new QueryError(Errors.CAMERA_GET_PECCANCY_PRAM1_PRAM2_ERROR).toJSON();
		}
		SearchResponse searchResponse = CameraResponse.cameraESSearch(locationId, starttime, endtime);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		CameraLocationInfo cli = new CameraLocationInfo();
		if (searchHits.length != 0) {
			Map<String, CameraCamInfo> map = new HashMap<>();
			for (SearchHit hit : searchHits) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				String tempid = sourceAsMap.get(GlobalConstants.CAMERA_ID).toString();
				if (!map.containsKey(tempid)) {
					CameraCamInfo temp = new CameraCamInfo(tempid);
					temp.addCameraPeccancy(new CameraPeccancyInfo(sourceAsMap));
					map.put(tempid, temp);
				} else {
					map.get(tempid).addCameraPeccancy(new CameraPeccancyInfo(sourceAsMap));
				}
			}
			for (CameraCamInfo temp : map.values()) {
				cli.addCameraCam(temp);
			}
		}else {
			cli.setStatus(1);
		}
		return cli.toJSON();
	}

}
