package com.geobigdata.entity.threadpool;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.geobigdata.utils.Constants.GlobalConstants;

/*
 * @author pjl
 * @version 创建时间：2018年10月30日 下午12:13:22
 * ES RestHighLevelClient单例模式
 */
public class RHLC {
	private static RestHighLevelClient client;
	private static volatile RHLC rhlc;

	private RHLC() {
		client = new RestHighLevelClient(RestClient.builder(
				new HttpHost(GlobalConstants.ES_SERVER_ADDRESS, GlobalConstants.ES_SERVER_PORT, GlobalConstants.ES_SERVER_SCHEME),
				new HttpHost(GlobalConstants.ES_SERVER_ADDRESS, GlobalConstants.ES_SERVER_PORT1, GlobalConstants.ES_SERVER_SCHEME)));
	}

	public static RHLC getInstance() {
		if (rhlc == null) {
			synchronized (RHLC.class) {
				if (rhlc == null) {
					rhlc = new RHLC();
				}
			}
		}
		return rhlc;
	}

	public RestHighLevelClient getclient() {
		return client;
	}
}
