package com.ruisoft.cm.base.excel.util;

public class ProductPartConfig {
	private String codeX = null;
	private String nameX = null;
	// 部件数量
	private String numX = null;

	private int codeY = 0;
	private int nameY = 0;
	// 部件数量
	private int numY = 0;

	private String routingNameX = null;
	private int routingNameY = 0;

	public String getCodeX() {
		return codeX;
	}

	public void setCodeX(String codeX) {
		this.codeX = codeX;
	}

	public String getNameX() {
		return nameX;
	}

	public void setNameX(String nameX) {
		this.nameX = nameX;
	}

	public String getNumX() {
		return numX;
	}

	public void setNumX(String numX) {
		this.numX = numX;
	}

	public int getCodeY() {
		return codeY;
	}

	public void setCodeY(int codeY) {
		this.codeY = codeY;
	}

	public int getNameY() {
		return nameY;
	}

	public void setNameY(int nameY) {
		this.nameY = nameY;
	}

	public int getNumY() {
		return numY;
	}

	public void setNumY(int numY) {
		this.numY = numY;
	}

	public String getRoutingNameX() {
		return routingNameX;
	}

	public void setRoutingNameX(String routingNameX) {
		this.routingNameX = routingNameX;
	}

	public int getRoutingNameY() {
		return routingNameY;
	}

	public void setRoutingNameY(int routingNameY) {
		this.routingNameY = routingNameY;
	}

}
