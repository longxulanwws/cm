package com.ruisoft.cm.rbac.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ruisoft.cm.rbac.entity.AddEntity;
import com.ruisoft.cm.rbac.util.SysCache;


public class CmImpDAO extends BaseDAO  {

	private final static Logger LOG = Logger.getLogger(CmImpDAO.class);

	private AddEntity aEntity = null;

	/**
	 * 增加产品结构
	 * @param value
	 * @param sql
	 * @return
	 */
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
	
	/**
	 * 查询工艺路线编码
	 * @param name
	 * @return
	 */
	public String queryCode(String name){
		String sql = "SELECT CODE FROM cm_dict_item WHERE DICT_DEF_ID = '0061' AND NAME = ? ";
		Map reMap = tpl.queryForMap(sql, name);
		return (String)reMap.get("CODE");
	}
	
	/**
	 * 查询工艺路线编码
	 * @param name
	 * @return
	 */
	public int queryIsCode(String name){
		int count = 0;
		String sql = "SELECT count(*) FROM cm_dict_item WHERE DICT_DEF_ID = '0061' AND NAME = ? ";
		count = tpl.queryForInt(sql, name);
		return count;
	}
	
	/**
	 * 查询产品是否存在编码
	 * @param product_id
	 * @return
	 */
	public int queryProCode(String product_id){
		int count = 0;
		String sql = "SELECT count(*) FROM mrp_task_routing WHERE Product_ID = ?";
		count = tpl.queryForInt(sql, product_id);
		return count;
	}
	
	/**
	 * 查询产品是否存在编码
	 * @param product_id
	 * @return
	 */
	public int queryIsProCode(String code,String name,String specs){
		int count = 0;
		String sql = "SELECT COUNT(*) FROM product_product WHERE Code = ? AND Name = ? AND Specs = ?";
		count = tpl.queryForInt(sql, code, name, specs);
		return count;
	}
}
