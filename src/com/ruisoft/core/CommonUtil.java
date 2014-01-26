package com.ruisoft.core;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;


public final class CommonUtil {
	private static final Logger LOG = Logger.getLogger(CommonUtil.class);
	
	public static <T> String pojoString(T obj) {
		if (obj == null)
			return "";
		
		String mName = null;
		StringBuffer str = new StringBuffer("[");
		Object[] params = null;
		
		for (Method md : obj.getClass().getMethods()) {
			mName = md.getName();
			if (mName.startsWith("get")) {
				if (mName.equals("getClass"))
					continue;
				if (md.getParameterTypes().length > 0)
					continue;
				
				try {
					str.append(mName.substring(3, 4).toLowerCase().concat(mName.substring(4)))
						.append('=').append(md.invoke(obj, params));
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
