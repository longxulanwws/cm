package com.ruisoft.cm.base.excel.util;

public class RoutingConfig {
	private int codeX = 0;
	private int nameX = 0;

	// <!-- 工艺路线 列数，即有多少种工艺 -->
	private String columnNum = null;

	// 工艺工时配额（分）
	private int hoursX = 0;

	private int codeY = 0;
	private int nameY = 0;
	// 工艺工时配额（分）
	private int hoursY = 0;

	public int getCodeX() {
		return codeX;
	}

	public void setCodeX(int codeX) {
		this.codeX = codeX;
	}

	public int getNameX() {
		return nameX;
	}

	public void setNameX(int nameX) {
		this.nameX = nameX;
	}

	public int getHoursX() {
		return hoursX;
	}

	public void setHoursX(int hoursX) {
		this.hoursX = hoursX;
	}

	public String getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(String columnNum) {
		this.columnNum = columnNum;
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

	public int getHoursY() {
		return hoursY;
	}

	public void setHoursY(int hoursY) {
		this.hoursY = hoursY;
	}

}
