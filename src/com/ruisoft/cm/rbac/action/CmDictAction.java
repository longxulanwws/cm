package com.ruisoft.cm.rbac.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruisoft.cm.rbac.dao.BaseDAO;
import com.ruisoft.cm.rbac.entity.DMLEntity;
import com.ruisoft.cm.rbac.util.JSONMap;
import com.ruisoft.cm.rbac.util.SysCache;
import com.ruisoft.core.param.ParamGenerator;

@Controller
@RequestMapping("/rbac/cmDict.do")
public class CmDictAction extends BaseAction {
	private final static Logger LOG = Logger.getLogger(CmDictAction.class);
	
	private static final String SQL_DEF_QUERY = "rbac.select.dict.def.query";
	
	private static final String SQL_DEF_ADD = "rbac.add.dict.def.add";
	
	private static final String SQL_DEF_UPDATE = "rbac.update.dict.def.update";
	
	private static final String SQL_DEF_DELETE = "rbac.delete.dict.def.delete";
	
	private static final String SQL_ITEM_DELETE = "rbac.delete.dict.item.delete";
	
	private static final String RESPONSE_STR = "{\"Rows\":{dict},\"Total\":\"{total}\"}";
	
	@Resource
	private BaseDAO baseDAO = null;
	
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	@Resource
	private ParamGenerator paramGenerator = null;

	public void setParamGenerator(ParamGenerator paramGenerator) {
		this.paramGenerator = paramGenerator;
	}

	@RequestMapping(params = "m=q")
	public String query() {
		LOG.debug("��ѯ�ֵ䶨��");
		LOG.debug(request.getQueryString());
		try {
			int page = Integer.valueOf(request.getParameter("p"));
			int pageSize = Integer.valueOf(request.getParameter("cp"));

			JSONObject cond = getReqData();
			LOG.debug("��ѯ������" + cond);

			List<JSONObject> dictDefs = baseDAO.queryForPage(SQL_DEF_QUERY,
					cond, page, pageSize);

			String c = dictDefs.get(0).getString("total");
			dictDefs.remove(0);

			String dicts = RESPONSE_STR.replaceFirst("\\{dict\\}",
					new JSONArray(dictDefs).toString()).replaceFirst(
					"\\{total\\}", c);
			LOG.debug(dicts);
			response(dicts);
		} catch (Exception e) {
			LOG.error("��ѯ�ֵ䶨�巢������", e);
		}
		return null;
	}
	
	/**
	 * �ֵ���������ػ���
	 */
	@RequestMapping(params = "m=gi")
	public void getDictItem() {
		try {
			// ����JSON����
			JSONObject reqObj = getReqData();
			// Ҫת�����ֵ���������
			String dictName = reqObj.getString("d");
			response(paramGenerator.generate(dictName, reqObj));
		} catch (Exception e) {
			LOG.error("��ȡ�ֵ���巢������", e);
		}
	}
	
	@RequestMapping(params = "m=a")
	public String addDictDef() {
		try {
			JSONObject reqData = getReqData();
			JSONMap<String, Object> json = new JSONMap<String, Object>(
					JSONMap.TYPE.OBJECT);
			int ex = baseDAO.count(((DMLEntity) SysCache.get(SQL_DEF_QUERY)).getSql()
					.concat(" WHERE CODE='").concat(reqData.getString("code"))
					.concat("'"));
			int r = 0xFF;
			if (ex > 0) {
				r = -1;
			} else {
				r = baseDAO.add(reqData, SQL_DEF_ADD);
			}
			json.put("result", r);
			response(json);
		} catch (Exception e) {
			LOG.error("�����ֵ䶨�巢������", e);
		}
		return null;
	}
	
	@RequestMapping(params = "m=u")
	public String updateDictDef() {
		try {
			int r = baseDAO.update(getReqData(), SQL_DEF_UPDATE);
			JSONMap<String, Object> json = new JSONMap<String, Object>(
					JSONMap.TYPE.OBJECT);
			json.put("result", r);
			response(json);
		} catch (Exception e) {
			LOG.error("�����ֵ䶨�巢������", e);
		}
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@RequestMapping(params = "m=d")
	public String deleteDictDef() {
		try {
			JSONObject cond = getReqData();
			int r = baseDAO.delete("{\"def_id\":\"" + cond.getString("id") + "\"}", SQL_ITEM_DELETE);
			r = baseDAO.delete(cond, SQL_DEF_DELETE);
			JSONMap<String, Object> json = new JSONMap<String, Object>(
					JSONMap.TYPE.OBJECT);
			json.put("result", r);
			response(json);
		} catch (Exception e) {
			LOG.error("ɾ���ֵ䶨�巢������", e);
		}
		return null;
	}
	
	public String queryDictItemByTree(HttpServletRequest request,
			HttpServletResponse response) {
		
		return null;
	}
}
