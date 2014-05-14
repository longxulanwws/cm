package com.ruisoft.test.poi;

import java.util.List;

public class ProductRoutingEntiy {
	private String id;
	private String code;
	private String name;
	private String specs;
	private String uom;
	
	//工艺路线对象List
	private List<RoutingEntity> routingEntityList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecs() {
		return specs;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public List<RoutingEntity> getRoutingEntityList() {
		return routingEntityList;
	}
	public void setRoutingEntityList(List<RoutingEntity> routingEntityList) {
		this.routingEntityList = routingEntityList;
	}
	

}
