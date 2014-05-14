package com.ruisoft.test.poi;

import java.util.List;




public class ProductionTaskRouting {
  
    //是否是单品，还是复杂部件标识
	public String productOrParts =null;
	
	//产品对象List
	private List<ProductRoutingEntiy> productRoutingEntityList;
	//工艺路线对象List
	private List<TaskRoutingEntity> taskRoutingEntityList;


	public List<ProductRoutingEntiy> getProductRoutingEntityList() {
		return productRoutingEntityList;
	}
	public void setProductRoutingEntityList(List<ProductRoutingEntiy> productRoutingEntityList) {
		this.productRoutingEntityList = productRoutingEntityList;
	}
	public List<TaskRoutingEntity> getTaskRoutingEntityList() {
		return taskRoutingEntityList;
	}
	public void setTaskRoutingEntityList(List<TaskRoutingEntity> taskRoutingEntityList) {
		this.taskRoutingEntityList = taskRoutingEntityList;
	}
	
	public String getProductOrParts() {
		return productOrParts;
	}
	public void setProductOrParts(String productOrParts) {
		this.productOrParts = productOrParts;
	}
	


}
