package com.ruisoft.cm.rbac.entity;

public class KeyEntity {
	/** ���ɹ��� */
	private String rule = null;
	
	/** ����������� */
	private int step = 1;
	
	/** �̶�λ����Ų�λ�ַ� */
	private String fillChar = "0";
	
	/**
	 * ��ż������� �����µ����ڿ�ʼʱ������ֵ����0<br/>
	 * year:����<br/>
	 * month:����<br/>
	 * day:����<br/>
	 * def:���Զ�����<br/>
	 * none:��
	 */
	private String circle = "none";
	
	/** �������  */
	private boolean group = false;
	
	/** ��ʼ����ֵ */
	private int init = 1;
	
	private String description = "";
	
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getFillChar() {
		return fillChar;
	}

	public void setFillChar(String fillChar) {
		this.fillChar = fillChar;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	public int getInit() {
		return init;
	}

	public void setInit(int init) {
		this.init = init;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}