package com.geobigdata.entity.data.taxi;

import java.util.ArrayList;
import java.util.List;

import com.geobigdata.entity.data.Returns;

/*
 * @author pjl
 * @version 创建时间：2018年12月4日 下午8:51:41
 * 区域内的所有路径
 */
public class TaxiAreaPath extends Returns {
	private List<TaxiPath> paths=new ArrayList<TaxiPath>();
	
	public List<TaxiPath> getPaths(){
		return paths;
	}
	public void setPaths(List<TaxiPath> paths) {
		this.paths=paths;
	}
	public void addTaxiPath(TaxiPath taxiPath) {
		paths.add(taxiPath);
	}
}
