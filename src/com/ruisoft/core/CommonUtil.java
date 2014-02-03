package com.ruisoft.core;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;


public final class CommonUtil {
	private static final Logger LOG = Logger.getLogger(CommonUtil.class);
	
	/**
	 * <p>���POJO���е��������Լ���ֵ</p>
	 * �����ʽ<code>[attr=value,...]</code>
	 * <p>POJO������ֵͨ����Ӧ��<code>getter</code>������ȡ��
	 * boolean��ֵҲ����ͨ������<code>isXXX</code>�ķ�����ȡ</p>
	 * 
	 * @param pojo POJO�����
	 * 
	 * @return ��ʽ���������ֵ�ַ���
	 */
	public static <T> String pojoString(T pojo) {
		if (pojo == null)
			return "";
		
		String mName = null;
		StringBuffer str = new StringBuffer("[");
		Object[] params = null;
		
		for (Method md : pojo.getClass().getMethods()) {
			mName = md.getName();
			if (mName.startsWith("get") || mName.startsWith("is")) {
				if (mName.equals("getClass"))
					continue;
				if (md.getParameterTypes().length > 0)
					continue;
				
				try {
					str.append(mName.substring(3, 4).toLowerCase().concat(mName.substring(4)))
						.append('=').append(md.invoke(pojo, params));
				} catch (Exception e) {
					LOG.info(e);
				} finally {
					str.append(',');
				}
			}
		}
		if (str.length() > 1) {
			str.deleteCharAt(str.length() - 1);
		}
		str.append(']');
		
		return str.toString();
	}
}
