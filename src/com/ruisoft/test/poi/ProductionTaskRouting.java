package com.ruisoft.test.poi;

import java.util.List;




public class ProductionTaskRouting {
  
    //�Ƿ��ǵ�Ʒ�����Ǹ��Ӳ�����ʶ
	public String productOrParts =null;
	
	//��Ʒ����List
	private List<ProductRoutingEntiy> productRoutingEntityList;
	//����·�߶���List
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
