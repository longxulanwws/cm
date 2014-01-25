package com.ruisoft.cm.rbac.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruisoft.cm.rbac.dao.BaseDAO;
import com.ruisoft.cm.rbac.util.JSONMap;
import com.ruisoft.cm.rbac.util.SysCache;

@Controller
@RequestMapping("/rbac/cm.do")
public class CommonAction extends BaseAction {
	private static final Logger LOG = Logger.getLogger(CommonAction.class);
	
	@Resource
	protected BaseDAO baseDAO = null;
	
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	private static final String RESPONSE_STR = "{\"Rows\":{rows},\"Total\":\"{total}\"}";
	
	private JSONObject reqData = null;
	
	@RequestMapping(params = "m=c")
	public void dml() {
		try {
			reqData = getReqData();
			String type = reqData.getString("t");
			if ("q".equals(type))
				query();
			else if ("a".equals(type))
				add();
			else if ("u".equals(type))
				update();
			else if ("d".equals(type))
				delete();
		} catch (Exception e) {
			LOG.error("Common DML Error!", e);
		}
	}
	
	@RequestMapping(params = "m=q")
	public void query() {
		JSONMap<String, Object> returnJson = new JSONMap<String, Object>(JSONMap.TYPE.OBJECT);
		try {
			if (reqData == null)
				reqData = getReqData();
			// ��ѯʵ������
			String entityName = reqData.getString("en");
			if (entityName == null || SysCache.get(entityName) == null) {
				returnJson.put("status", "-1");
				returnJson.put("msg", "û��ָ����ѯʵ������");
			} else {
				// l-list ���Խṹ��Ĭ�ϣ�t-tree ���ͽṹ
				String returnType = reqData.getString("rt");
				// �Ƿ��ҳ
				boolean isPager = reqData.getBoolean("p");
				// ��ǰҳ��
				int currentPage = reqData.isNull("cp") ? 1 : reqData.getInt("cp");
				// ÿҳ��¼��
				int pageSize = reqData.isNull("ps") ? 20 : reqData.getInt("ps");
				
				List<JSONObject> result = null;
				String rows = null, total = "0";
				if (isPager) {
					result = baseDAO.queryForPage(entityName,
							reqData.getString("data"), currentPage, pageSize);
					total = result.get(0).getString("total");
					result.remove(0);
				} else {
					result = baseDAO.query(reqData.getString("data"),
							entityName);
				}
				
				if ("t".equalsIgnoreCase(returnType)) {
					rows = treeData(result);
				} else {
					rows = new JSONArray(result).toString();
				}
				
				returnJson.put("status", "1");
				returnJson.put("data",
						RESPONSE_STR.replaceFirst("\\{total\\}", total)
							.replaceFirst("\\{rows\\}", rows));
			}
		} catch (Exception e) {
			LOG.error("ִ�в�ѯ������������", e);
			returnJson.put("status", "-2");
		} finally {
			try {
				LOG.debug(returnJson);
				response(returnJson);
			} catch (IOException e) {
				LOG.error("��ѯ������Ϣʱ��������", e);;
			}
			reqData = null;
		}
	}
	
	protected String treeData(List<JSONObject> data) {
		return new JSONMap<String, String>(JSONMap.TYPE.ARRAY).toString();
	}
	
	@RequestMapping(params = "m=a")
	public void add() {
		JSONMap<String, Object> returnJson = new JSONMap<String, Object>(JSONMap.TYPE.OBJECT);
		
		try {
			if (reqData == null)
				reqData = getReqData();
			// ��ѯʵ������
			String entityName = reqData.getString("en");
			if (entityName == null || SysCache.get(entityName) == null) {
				returnJson.put("status", "-1");
				returnJson.put("msg", "û��ָ������ʵ������");
			} else {
				int r = baseDAO.add(reqData.getString("data"), entityName);
				returnJson.put("status", r);
			}
		} catch (Exception e) {
			LOG.error("ִ�в��������������", e);
			returnJson.put("status", "-2");
		} finally {
			try {
				response(returnJson);
			} catch (IOException e) {
				LOG.error("���뷵����Ϣʱ��������", e);
			}
			reqData = null;
		}
	}
	
	@RequestMapping(params = "m=u")
	public void update() {
		JSONMap<String, Object> returnJson = new JSONMap<String, Object>(JSONMap.TYPE.OBJECT);
		
		try {
			if (reqData == null)
				reqData = getReqData();
			// ��ѯʵ������
			String entityName = reqData.getString("en");
			if (entityName == null || SysCache.get(entityName) == null) {
				returnJson.put("status", "-1");
				returnJson.put("msg", "û��ָ������ʵ������");
			} else {
				int r = baseDAO.update(reqData.getString("data"), entityName);
				returnJson.put("status", r);
			}
		} catch (Exception e) {
			LOG.error("ִ�и��²�����������", e);
			returnJson.put("status", "-2");
		} finally {
			try {
				response(returnJson);
			} catch (IOException e) {
				LOG.error("���·�����Ϣʱ��������", e);
			}
			reqData = null;
		}
	}
	
	@RequestMapping(params = "m=d")
	public void delete() {
		JSONMap<String, Object> returnJson = new JSONMap<String, Object>(JSONMap.TYPE.OBJECT);
		
		try {
			if (reqData == null)
				reqData = getReqData();
			// ��ѯʵ������
			String entityName = reqData.getString("en");
			if (entityName == null || SysCache.get(entityName) == null) {
				returnJson.put("status", "-1");
				returnJson.put("msg", "û��ָ��ɾ��ʵ������");
			} else {
				int r = baseDAO.delete(reqData.getString("data"), entityName);
				returnJson.put("status", r);
			}
		} catch (Exception e) {
			LOG.error("ִ��ɾ��������������", e);
			returnJson.put("status", "-2");
		} finally {
			try {
				response(returnJson);
			} catch (IOException e) {
				LOG.error("ɾ��������Ϣʱ��������", e);
			}
			reqData = null;
		}
	}
}
