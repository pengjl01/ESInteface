package com.geobigdata.entity.data;

import com.alibaba.fastjson.JSON;

/*
 * @author pjl
 * @version 创建时间：2018年11月9日 下午2:28:16
 * 返回类
 */
public abstract class Returns {
	protected int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status=status;
	}
	public String toJSON() {
		return JSON.toJSONString(this);
	}
}
