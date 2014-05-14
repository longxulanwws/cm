package com.ruisoft.test.poi;

import java.util.List;

public class TaskRoutingEntity {
	
	private String product_code;
	private String product_name;
	
	//�������
	private String task_routing_code;
	//��������
	private String task_routing_name;
	//��������
	private String task_routing_qty;
	
	//��������·��
	private String routingToString;
	//����·�߶���List
	private List<RoutingEntity> routingEntityList;
	
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
	public void setTask_routing_qty(String task_routing_qty) {
		this.task_routing_qty = task_routing_qty;
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
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
}
