package com.ruisoft.cm.base.excel.util;

public class RoutingConfig {
	private String codeX = null;
	private String nameX = null;

	// <!-- 工艺路线 列数，即有多少种工艺 -->
	private String columnNum = null;

	// 工艺工时配额（分）
	private String hoursX = null;

	private int codeY = 0;
	private int nameY = 0;
	// 工艺工时配额（分）
	private int hoursY = 0;

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

	public String getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(String columnNum) {
		this.columnNum = columnNum;
	}

	public String getHoursX() {
		return hoursX;
	}

	public void setHoursX(String hoursX) {
		this.hoursX = hoursX;
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
