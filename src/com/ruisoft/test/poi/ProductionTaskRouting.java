package com.ruisoft.test.poi;

import java.util.List;



public class ProductionTaskRouting {
	//产品编号
	private String Product_ID;
	//产品对象
	private ProductEntiy pruductEntiy=null;	
	//部件编号
	private String task_routing_code;
	//部件名称
	private String task_routing_name;
	//部件数量
	private String task_routing_qty;
	
	//生产工艺路线
	private String routingToString;
	//工艺路线对象
	private List<RoutingEntity> routingEntityList;
	public String getProduct_ID() {
		return Product_ID;
	}
	public void setProduct_ID(String product_ID) {
		Product_ID = product_ID;
	}


	public String getTask_routing_code() {
		return task_routing_code;
	}
	public void setTask_routing_code(String task_routing_code) {
		this.task_routing_code = task_routing_code;
	}
	public String getTask_routing_name() {
		return task_routing_name;
	}
	public void setTask_routing_name(String task_routing_name) {
		this.task_routing_name = task_routing_name;
	}
	public String getTask_routing_qty() {
		return task_routing_qty;
	}
	public void setTask_routing_qty(String task_routing_qty2) {
		this.task_routing_qty = task_routing_qty2;
	}
	public String getRoutingToString() {
		return routingToString;
	}
	public void setRoutingToString(String routingToString) {
		this.routingToString = routingToString;
	}

	public List<RoutingEntity> getRoutingEntityList() {
		return routingEntityList;
	}
	public void setRoutingEntityList(List<RoutingEntity> routingEntityList) {
		this.routingEntityList = routingEntityList;
	}
	public ProductEntiy getPruductEntiy() {
		return pruductEntiy;
	}
	public void setPruductEntiy(ProductEntiy pruductEntiy) {
		this.pruductEntiy = pruductEntiy;
	}
	

}
