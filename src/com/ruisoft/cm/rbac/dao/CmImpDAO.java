package com.ruisoft.cm.rbac.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ruisoft.cm.rbac.entity.AddEntity;
import com.ruisoft.cm.rbac.util.SysCache;


public class CmImpDAO extends BaseDAO  {

	private final static Logger LOG = Logger.getLogger(CmImpDAO.class);

	private AddEntity aEntity = null;

	public boolean proAdd(Object[] value,String sql){
		boolean result = false;
		if (tpl == null)
			tpl = new JdbcTemplate(getDataSource());
		int r = tpl.update(sql, value);
		if (r > 0){
			result = true;
		}
		return result;
	}
	
	public String queryCode(String name){
		String sql = "SELECT CODE FROM cm_dict_item WHERE DICT_DEF_ID = '0061' AND NAME = ? ";
		Map reMap = tpl.queryForMap(sql, name);
		return (String)reMap.get("CODE");
	}
}
