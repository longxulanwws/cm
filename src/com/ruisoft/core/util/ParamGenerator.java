package com.ruisoft.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ruisoft.cm.rbac.dao.BaseDAO;
import com.ruisoft.cm.rbac.entity.QueryEntity;

public class ParamGenerator {
	private static final Logger LOG = Logger.getLogger(ParamGenerator.class);
	
	private static final String DICT_ITEM_QUERY = "rbac.select.dict.item.query";
	
	private static final String PARAM_RETURN = "{\"param\":{p},\"tree\":\"{t}\"}";
	
	private Map<String, ParamEntity> config = null;

	public Map<String, ParamEntity> getConfig() {
		return config;
	}

	public void setConfig(Map<String, ParamEntity> config) {
		this.config = config;
	}
	
	@Resource
	private BaseDAO baseDAO = null;
	
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	private static final HashMap<String, String> cache = new HashMap<String, String>();
	
	public String generate(String id, JSONObject args) {
		if (cache.containsKey(id)) // 查找缓存
			return cache.get(id);
			
		String params = null, treeAttr = "";
		boolean needCache = true;
		try {
			if (!config.containsKey(id)) {
				// 参数字典表取值
				params = genFormDict(id, args);
			} else {
				ParamEntity entity = config.get(id);
			
				if (entity instanceof SQLParamEntity) {
					params = generate((SQLParamEntity) entity, args);
					treeAttr = ((SQLParamEntity) entity).getTreeAttr();
					needCache = ((SQLParamEntity) entity).isCache();
				} else if (entity instanceof ListParamEntity) {
					params = generate((ListParamEntity) entity);
					needCache = ((ListParamEntity) entity).isCache();
				}
			}
			
			params = PARAM_RETURN.replaceFirst("\"p\"", new JSONArray(params).toString())
					.replaceFirst("\"t\"", treeAttr);
			
			if (needCache)
				cache.put(id, params);
			
			return params;
		} catch (JSONException e) {
			LOG.error("生成字典项[".concat(id).concat("]发生错误"), e);
		}
		
		return null;
	}
		
	public String genFormDict(String id, JSONObject args) throws JSONException {
		List<JSONObject> items = baseDAO.query(args, DICT_ITEM_QUERY);
		return reorgParam(items);
	}
	
	public String generate(SQLParamEntity entity, JSONObject args) throws JSONException {
		if (args == null || args.isNull("d")) {
			LOG.error("未提供目标参数定义名称");
			return null;
		}
		
		QueryEntity qEntity = entity.getSql();
		List<JSONObject> items = baseDAO.query(args, qEntity);
		return reorgParam(items);
	}
	
	public String generate(ListParamEntity entity) {
		return null;
	}
	
	private String reorgParam(List<JSONObject> items) throws JSONException {
		JSONObject item = new JSONObject();
		for (JSONObject i : items) {
			item.put(i.getString("value"), i.toString());
		}

		String param = item.toString();
		LOG.debug(param);
		return param;
	}
}
