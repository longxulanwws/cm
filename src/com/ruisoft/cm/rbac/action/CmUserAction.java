package com.ruisoft.cm.rbac.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruisoft.cm.rbac.dao.CmUserDAO;
import com.ruisoft.cm.rbac.util.SysConstants;

@Controller
@RequestMapping("/rbac/cmUser.do")
public class CmUserAction extends BaseAction {
	private final static Logger LOG = Logger.getLogger(CmUserAction.class);
	
	@Resource
	private CmUserDAO cmUserDAO = null;
	
	public void setCmUserDAO(CmUserDAO cmUserDAO) {
		this.cmUserDAO = cmUserDAO;
	}
	
	@RequestMapping(params = "method=login")
	public String login() {
		try {
			JSONObject json = getReqData();
			
			if (json == null) {
				response("[{\"status\":\"1\",\"valid\":\"0\"}]");
				return null;
			}
			
			JSONObject user = cmUserDAO.login(json);
			int valid = 1;
			if (user == null) // ��¼ʧ��
				valid = 0;
			else if (!"1".equals(user.getString("status")))
				valid = 2;
			else { // ��¼�ɹ�
				// ������Ϣ
				// ����û���Ϣ
				// �û���Ϣ
				session.setAttribute(SysConstants.USER_INFO.toString(), user);
				// �˵���
				// Ȩ����Ϣ
				
			}
			response("{\"status\":\"1\",\"valid\":\"" + valid + "\"}");
		} catch (Exception e) {
			LOG.error("��¼������������", e);
		}
		
		return null;
	}
	
	@RequestMapping(params = "method=getUserInfo")
	public String getUserInfo() {
		JSONObject user = (JSONObject) session.getAttribute(SysConstants.USER_INFO.toString());
		
		try {
			if (user == null) {
				user = new JSONObject();
				user.put("status", "0");
			} else {
				user.put("status", "1");
				user.put("date",
						new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
		
			response(user.toString());
		} catch (Exception e) {
			LOG.error("��ȡ����Ա��Ϣʱ��������", e);
		}
		
		return null;
	}
	
	@RequestMapping(params = "method=logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		session.removeAttribute(SysConstants.USER_INFO.toString());
		try {
			response.sendRedirect(request.getContextPath() + "/index.html");
		} catch (IOException e) {
			LOG.error(e);
		}
		return null;
	}
}