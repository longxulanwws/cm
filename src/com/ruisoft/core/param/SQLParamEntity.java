package com.ruisoft.core.param;

import com.ruisoft.cm.rbac.entity.QueryEntity;

public class SQLParamEntity implements ParamEntity {
	/** ��ѯSQL */
	private QueryEntity sql = null;

	public QueryEntity getSql() {
		return sql;
	}

	public void setSql(QueryEntity sql) {
		this.sql = sql;
	}
	
	/** �������ͽṹ����������  */
	private String treeAttr = "";

	public String getTreeAttr() {
		return treeAttr;
	}

	public void setTreeAttr(String treeAttr) {
		this.treeAttr = treeAttr;
	}
	
	/** �Ƿ���Ҫ���� */
	private boolean cache = false;

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}
}
