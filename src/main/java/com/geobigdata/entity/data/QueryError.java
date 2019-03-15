package com.geobigdata.entity.data;

/*
 * @author pjl
 * @version 创建时间：2018年11月9日 下午2:26:52
 * 查询错误时的返回
 */
public class QueryError extends Returns{
	private String errorInfo;
	public QueryError(String errorInfo) {
		this.status=2;
		this.errorInfo=errorInfo;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo=errorInfo;
	}
}
