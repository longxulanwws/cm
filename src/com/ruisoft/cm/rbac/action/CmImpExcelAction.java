package com.ruisoft.cm.rbac.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mysql.jdbc.StringUtils;
import com.ruisoft.cm.base.excel.util.PrduExcelConfig;
import com.ruisoft.cm.rbac.dao.BaseDAO;
import com.ruisoft.cm.rbac.dao.CmImpDAO;
import com.ruisoft.cm.rbac.util.SysConstants;
import com.ruisoft.test.poi.ProductRoutingEntiy;
import com.ruisoft.test.poi.ProductionTaskRouting;
import com.ruisoft.test.poi.ReadExcelTest;
import com.ruisoft.test.poi.RoutingEntity;
import com.ruisoft.test.poi.TaskRoutingEntity;

@Controller
@RequestMapping("/rbac/impExcel.do")
public class CmImpExcelAction extends BaseAction{
	private final static Logger LOG = Logger.getLogger(CmImpExcelAction.class);
	
	//���Ӳ�Ʒ��ʶ
	private final String TASK_ROUTING_LEVEL_ZERO = "0";
	//�򵥲�Ʒ��ʶ
	private final String TASK_ROUTING_LEVEL_ONE = "1";
	//������SQLID
	private final String SQL = "INSERT INTO mrp_task_routing (task_routing_id, task_routing_code, task_routing_name, task_routing_type, up_task_routing_code, Product_ID, Product_Name, task_routing_qty, task_routing_standard_hour, task_routing_level, CreateUser, CreateDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	@Resource
	protected CmImpDAO cmImpDAO = null;
	
	public void setCmImpDAO(CmImpDAO cmImpDAO) {
		this.cmImpDAO = cmImpDAO;
	}
	
	/**
	 * excel����
	 * @param multipartRequest
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "method=impExcel")
	public void impExcel(MultipartHttpServletRequest multipartRequest,  
			HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=UTF-8"); 
		PrintWriter out = response.getWriter();  
		String createuser = ((JSONObject) session.getAttribute(SysConstants.USER_INFO.toString())).getString("uid");
		String FullfileName = "";
		//�����ϴ��ļ�
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {  
			String key = (String) it.next();  
			MultipartFile imgFile = multipartRequest.getFile(key);  
			if (imgFile.getOriginalFilename().length() > 0) {  
				String fileName = imgFile.getOriginalFilename();  
				String uploadFileUrl = multipartRequest.getSession().getServletContext().getRealPath("\\upload");  
				File _apkFile = saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, fileName);  
				if (_apkFile.exists()) {
					//�ϴ��ɹ�
					FullfileName = _apkFile.getAbsolutePath();
					//FileInputStream fis = new FileInputStream(_apkFile);  
				} else{
					//�ϴ�ʧ��
					FullfileName = "fasle";
				}
			}  
		}  
		//���ļ����ݵ������ݿ�洢
		boolean result = ImportDbData(FullfileName,createuser);
		if(result){
			out.print("����ɹ���");
		}else {
			out.print("����ʧ�ܣ�����ģ�����ݸ�ʽ��");
		}
	}

	/**
	 * �ϴ��ļ���������
	 * @param stream
	 * @param path
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	private File saveFileFromInputStream(InputStream stream, String path,  
			String filename) throws IOException {  
		File file = new File(path + "/" + filename);  
		FileOutputStream fs = new FileOutputStream(file);  
		byte[] buffer = new byte[1024 * 1024];  
		int bytesum = 0;  
		int byteread = 0;  
		while ((byteread = stream.read(buffer)) != -1) {  
			bytesum += byteread;  
			fs.write(buffer, 0, byteread);  
			fs.flush();  
		}  
		fs.close();  
		stream.close();  
		return file;  
	} 
	
	/**
	 * �ϴ�����������excel���뵽���ݿ�
	 * @param FullfileName
	 * @param createuser
	 * @return
	 */
	private boolean ImportDbData(String FullfileName,String createuser){
		boolean result = false;
		//��ǰ�û�ID
//		String createuser = (String) request.getSession().getAttribute(SysConstants.USER_INFO.toString());
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//���Է�����޸����ڸ�ʽ
		//��ǰ����
		String createdate = dateFormat.format( now ); 
		System.out.println(createdate); 
		//��ȡcontext����
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());  
		PrduExcelConfig prduExcelConfig = (PrduExcelConfig) ctx.getBean("prduExcelConfig"); 
		//�ϴ��ɹ�
		if (!"fasle".equals(FullfileName)){
			//��ȡ��Ʒ����
			ProductionTaskRouting productiontaskRouting = ReadExcelTest.readExcelData(FullfileName,prduExcelConfig);
			//�Ƿ��ǵ�Ʒ�����Ǹ��Ӳ�����ʶ
			String productOrParts = productiontaskRouting.getProductOrParts();
			//��Ʒ����List
			List<ProductRoutingEntiy> productRoutingEntityList = productiontaskRouting.getProductRoutingEntityList();
			//����·�߶���List
			List<TaskRoutingEntity> taskRoutingEntityList = productiontaskRouting.getTaskRoutingEntityList();
			//���Ӳ�Ʒ�ĵ���
			if("parts".equals(productOrParts)){
				result = partsImport(createuser,createdate,productRoutingEntityList,taskRoutingEntityList);
			} else if ("products".equals(productOrParts)){
				//��Ʒ����
				result = productImport(createuser,createdate,productRoutingEntityList);
			}
		}
		return result;
	}
	
	/**
	 * ��Ʒ����
	 * @param createuser
	 * @param createdate
	 * @param productRoutingEntityList
	 * @return
	 */
	private boolean productImport(String createuser,String createdate,List<ProductRoutingEntiy> productRoutingEntityList){
		boolean result = false;
		if(productRoutingEntityList.size()>0 && !productRoutingEntityList.isEmpty()){
			//��ȡ��Ʒ����
			for(int i = 0; i < productRoutingEntityList.size(); i++ ){
				ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(i);
				String code = productRoutingEntiy.getCode();
				String name = productRoutingEntiy.getName();
				if(!StringUtils.isNullOrEmpty(code)&&!StringUtils.isNullOrEmpty(name)){
					Object pro_data[] = new Object[12];
					//task_routing_id
					pro_data[0] = UUID.randomUUID().toString();
					//task_routing_code
					pro_data[1] = code;
					//task_routing_name
					pro_data[2] = name;
					//task_routing_type
					pro_data[3] = "1";
					//up_task_routing_code
					pro_data[4] = null;
					//Product_ID
					pro_data[5] = code;
					//Product_Name
					pro_data[6] = name;
					//task_routing_qty
					pro_data[7] = null;
					//task_routing_standard_hour
					pro_data[8] = null;
					//task_routing_level
					pro_data[9] = TASK_ROUTING_LEVEL_ONE;
					//CreateUser
					pro_data[10] = createuser;
					//CreateDate
					pro_data[11] = createdate;
					result = cmImpDAO.proAdd(pro_data,SQL);
					//����·�߶���List
					List<RoutingEntity> routingEntityList = productRoutingEntiy.getRoutingEntityList();
					if(routingEntityList.size()>0 && !routingEntityList.isEmpty() && result){
						//��ȡ��Ʒ����
						for(int j = 0; j < routingEntityList.size(); j++ ){
							RoutingEntity routingEntity = routingEntityList.get(j);
							if(routingEntity != null){
								String routing_name =  routingEntity.getName();
								if(!StringUtils.isNullOrEmpty(routing_name)){
									Object routing_data[] = new Object[12];
									//task_routing_id
									routing_data[0] = UUID.randomUUID().toString();
									//task_routing_code
									routing_data[1] = cmImpDAO.queryCode(routing_name);
									//task_routing_name
									routing_data[2] = routing_name;
									//task_routing_type
									routing_data[3] = "3";
									//up_task_routing_code
									routing_data[4] = code;
									//Product_ID
									routing_data[5] = code;
									//Product_Name
									routing_data[6] = name;
									//task_routing_qty
									routing_data[7] = null;
									//task_routing_standard_hour
									routing_data[8] = Float.parseFloat(routingEntity.getHours());
									//task_routing_level
									routing_data[9] = TASK_ROUTING_LEVEL_ONE;
									//CreateUser
									routing_data[10] = createuser;
									//CreateDate
									routing_data[11] = createdate;
									result = cmImpDAO.proAdd(routing_data,SQL);
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * ���Ӳ�Ʒ����
	 * @param createuser
	 * @param createdate
	 * @param productRoutingEntityList
	 * @param taskRoutingEntityList
	 * @return
	 */
	private boolean partsImport(String createuser,String createdate,List<ProductRoutingEntiy> productRoutingEntityList,
			List<TaskRoutingEntity> taskRoutingEntityList){
		boolean result = false;
		String code = "";
		String name = "";
		if(productRoutingEntityList.size()>0 && !productRoutingEntityList.isEmpty()){
			//��ȡ��Ʒ����
			ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(0);
			code = productRoutingEntiy.getCode();
			name = productRoutingEntiy.getName();
			if(!StringUtils.isNullOrEmpty(code)&&!StringUtils.isNullOrEmpty(name)){
				Object pro_data[] = new Object[12];
				//task_routing_id
				pro_data[0] = UUID.randomUUID().toString();
				//task_routing_code
				pro_data[1] = code;
				//task_routing_name
				pro_data[2] = name;
				//task_routing_type
				pro_data[3] = "1";
				//up_task_routing_code
				pro_data[4] = null;
				//Product_ID
				pro_data[5] = code;
				//Product_Name
				pro_data[6] = name;
				//task_routing_qty
				pro_data[7] = null;
				//task_routing_standard_hour
				pro_data[8] = null;
				//task_routing_level
				pro_data[9] = TASK_ROUTING_LEVEL_ZERO;
				//CreateUser
				pro_data[10] = createuser;
				//CreateDate
				pro_data[11] = createdate;
				result = cmImpDAO.proAdd(pro_data,SQL);
			}
		}
		if(taskRoutingEntityList.size()>0 && !taskRoutingEntityList.isEmpty()){
			//��ȡ��������
			for(int i = 0; i < taskRoutingEntityList.size(); i++){
				TaskRoutingEntity taskRoutingEntity = taskRoutingEntityList.get(i);
				if(taskRoutingEntity!=null){
					String task_routing_code = taskRoutingEntity.getTask_routing_code();
					String task_routing_name = taskRoutingEntity.getTask_routing_name();
					String task_routing_qty = taskRoutingEntity.getTask_routing_qty();
					if(!StringUtils.isNullOrEmpty(task_routing_code)&&!StringUtils.isNullOrEmpty(task_routing_name)){
						Object task_data[] = new Object[12];
						//task_routing_id
						task_data[0] = UUID.randomUUID().toString();
						//task_routing_code
						task_data[1] = task_routing_code;
						//task_routing_name
						task_data[2] = task_routing_name;
						//task_routing_type
						task_data[3] = "2";
						//up_task_routing_code
						task_data[4] = strPid(task_routing_code,code);
						//Product_ID
						task_data[5] = code;
						//Product_Name
						task_data[6] = name;
						//task_routing_qty
						task_data[7] = Float.parseFloat(task_routing_qty);;
						//task_routing_standard_hour
						task_data[8] = null;
						//task_routing_level
						task_data[9] = TASK_ROUTING_LEVEL_ZERO;
						//CreateUser
						task_data[10] = createuser;
						//CreateDate
						task_data[11] = createdate;
						result = cmImpDAO.proAdd(task_data,SQL);
						//����·�߶���List
						List<RoutingEntity> routingEntityList = taskRoutingEntity.getRoutingEntityList();
						if(routingEntityList.size()>0 && !routingEntityList.isEmpty() && result){
							//��ȡ��Ʒ����
							for(int j = 0; j < routingEntityList.size(); j++ ){
								RoutingEntity routingEntity = routingEntityList.get(j);
								if(routingEntity!=null){
									String routing_name =  routingEntity.getName();
									if(!StringUtils.isNullOrEmpty(routing_name)){
										Object routing_data[] = new Object[12];
										//task_routing_id
										routing_data[0] = UUID.randomUUID().toString();
										//task_routing_code
										routing_data[1] = cmImpDAO.queryCode(routing_name);
										//task_routing_name
										routing_data[2] = routing_name;
										//task_routing_type
										routing_data[3] = "3";
										//up_task_routing_code
										routing_data[4] = task_routing_code;
										//Product_ID
										routing_data[5] = code;
										//Product_Name
										routing_data[6] = name;
										//task_routing_qty
										routing_data[7] = null;
										//task_routing_standard_hour
										routing_data[8] = Float.parseFloat(routingEntity.getHours());
										//task_routing_level
										routing_data[9] = TASK_ROUTING_LEVEL_ZERO;
										//CreateUser
										routing_data[10] = createuser;
										//CreateDate
										routing_data[11] = createdate;
										result = cmImpDAO.proAdd(routing_data,SQL);
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * ��ȡ�ϼ�ID
	 * @param task_code ����code
	 * @param pro_code ��Ʒcode
	 * @return
	 */
	private String strPid(String task_code,String pro_code){
		String pid = "";
		int egNum = task_code.lastIndexOf("-");
		int cnNum = task_code.lastIndexOf("��");
		int cn2Num = task_code.lastIndexOf("��");
		if(egNum < 0 && cnNum < 0 && cn2Num < 0){
			pid = pro_code;
		} else if(egNum > cnNum && egNum > cn2Num && egNum > 0 ){
			pid = task_code.substring(0, egNum);
		} else if(cnNum > egNum && cnNum > cn2Num && cnNum > 0 ){
			pid = task_code.substring(0, cnNum);
		} else if(cn2Num > egNum && cn2Num > cnNum && cn2Num > 0 ){
			pid = task_code.substring(0, cn2Num);
		}
		return pid;
	}
}
