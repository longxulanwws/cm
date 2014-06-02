package com.ruisoft.cm.rbac.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ruisoft.cm.base.excel.util.PrduExcelConfig;
import com.ruisoft.cm.rbac.dao.BaseDAO;
import com.ruisoft.cm.rbac.dao.CmImpDAO;
import com.ruisoft.cm.rbac.util.SysConstants;
import com.ruisoft.test.poi.ProductRoutingEntiy;
import com.ruisoft.test.poi.ProductionTaskRouting;
import com.ruisoft.test.poi.ReadExcelTest;
import com.ruisoft.test.poi.RoutingEntity;
import com.ruisoft.test.poi.TaskRoutingEntity;
import com.sun.org.apache.bcel.internal.generic.NEW;

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
	
	private final String HOURS_SQL = "INSERT INTO  mrp_routing_hours SELECT DISTINCT a.task_routing_id, a.task_routing_code, a.task_routing_name, a.task_routing_type,"+
			" a.up_task_routing_code,a.task_routing_code, a.Product_ID, a.Product_Name,a.task_routing_qty "+
			" FROM   "+
			" (SELECT * FROM mrp_task_routing WHERE task_routing_type  IN ('1','2')  ) AS a "+
			" , (SELECT * FROM mrp_task_routing WHERE task_routing_type ='3') AS b  "+
			" WHERE    a.Product_ID=b.Product_ID AND b.up_task_routing_code=a.task_routing_code "+
			" AND  a.Product_ID=? ;";
	//���Ӳ�ƷID
	private String product_id = "";
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
				
				/*
				 * �ж��ļ����Ƿ���ڣ��������򴴽�
				 */
				File file =new File(uploadFileUrl);  
				//����ļ��в������򴴽�  
				if  (!file.exists() && !file.isDirectory()){     
				    //������ʱ,�Զ�����
				    file.mkdir();  
				} 
				
				File _apkFile = saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, fileName);  
				if (_apkFile.exists()) {
					//�ϴ��ɹ�
					FullfileName = _apkFile.getAbsolutePath();
				} else{
					//�ϴ�ʧ��
					FullfileName = "fasle";
				}
			}  
		}  
		//���ļ����ݵ������ݿ�洢
		Map reMap = ImportDbData(FullfileName,createuser);
		if("true".equals(reMap.get("result"))){
			out.print("success!");
			out.print("product_id");
			out.print(product_id);
		}else {
			out.print(reMap.get("message"));
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
	private Map ImportDbData(String FullfileName,String createuser){
		Map resultMap = new HashMap();
		boolean result = false;
		String message = "";
		//��ǰ�û�ID
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//���Է�����޸����ڸ�ʽ
		//��ǰ����
		String createdate = dateFormat.format( now ); 
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
			//��Ʒ�Ƿ����check
			resultMap = isExistProduct(productRoutingEntityList,productOrParts);
			if("true".equals(resultMap.get("result"))){
				resultMap = new HashMap();
				//��Ʒ�Ƿ���check
				resultMap = isExistProductByTree(productRoutingEntityList,productOrParts);
				if("true".equals(resultMap.get("result"))){
					resultMap = new HashMap();
					//���Ӳ�Ʒ�ĵ���
					if("parts".equals(productOrParts)){
						resultMap = checkProQty(taskRoutingEntityList);
						if("true".equals(resultMap.get("result"))){
							resultMap = new HashMap();
							resultMap = checkGongyiParts(taskRoutingEntityList);
							if("true".equals(resultMap.get("result"))){
								resultMap = new HashMap();
								resultMap = checkTaskLevel(taskRoutingEntityList);
								if("true".equals(resultMap.get("result"))){
									resultMap = new HashMap();
									result = partsImport(createuser,createdate,productRoutingEntityList,taskRoutingEntityList);
									if(!result){
										message = "����ʧ�ܣ�";
										resultMap.put("result", "false");
										//message����
										resultMap.put("message", message);
									}else{
										message = "����ɹ���";
										resultMap.put("result", "true");
										//message����
										resultMap.put("message", message);
									}
								}
							}
						}

					} else if ("products".equals(productOrParts)){
						resultMap = checkProductCode(productRoutingEntityList);
						if("true".equals(resultMap.get("result"))){
							resultMap = new HashMap();
							resultMap = checkGongyiProducts(productRoutingEntityList);
							if("true".equals(resultMap.get("result"))){
								resultMap = new HashMap();
								//��Ʒ����
								result = productImport(createuser,createdate,productRoutingEntityList);
								if(!result){
									message = "����ʧ�ܣ�";
									resultMap.put("result", "false");
									//message����
									resultMap.put("message", message);
								}else{
									message = "����ɹ���";
									resultMap.put("result", "true");
									//message����
									resultMap.put("message", message);
								}
							}
						}
					}
				}
			}
		}else{
			message = "�ļ��ϴ�ʧ�ܣ�";
			resultMap.put("result", "false");
			//message����
			resultMap.put("message", message);
		}
		return resultMap;
	}
	
//	1����Ʒ��������ʱ����ά����
//	1) excel ����У�飺
//	��֤��Ʒ���롢��Ʒ���ơ�����ͺ��Ƿ�����Ʒ���������Ƿ�һ�¡�
//	��֤����·���Ƿ�����ϵͳ�ж���
//	��Ʒ+�ಿ�� ʱ��������������Ϊ��,��Ϊ0��
//	�������Ҫ����ּ�����1-1-1���зּ�ʱ���ϼ��������
//	2��excel �������
//	�����ظ�����ͬһ��Ʒ�Ĺ�ʱ��������
	
	private Map checkTaskLevel(List<TaskRoutingEntity> taskRoutingEntityList){
		Map resultMap = new HashMap();
		Set set=new HashSet();
		List leveList = new ArrayList();
		String result = "true";
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < taskRoutingEntityList.size(); i++)
		{
			TaskRoutingEntity taskRoutingEntity = taskRoutingEntityList.get(i);
			if(taskRoutingEntity!=null){
				String task_routing_code = taskRoutingEntity.getTask_routing_code();
				int level = strLevel(task_routing_code);
				set.add(level);
			}
		}
		for(Iterator it=set.iterator();it.hasNext();)
		  {
			leveList.add(it.next());
		  }
		for(int i = 0; i < leveList.size(); i++){
			boolean resu = false;
			for(int j = 0; j < leveList.size(); j++){
				if(i == (Integer)leveList.get(j)){
					resu = true;
					break;
				}
			}
			if(!resu){
				result = "false";
				message.append((i+1)+"�㣬");
			}
		}
		//check���
		if("false".equals(result)){
			message.append("������Щ������ݲ����ڣ����鲿���Ĳ㼶��ϵ��");
			System.out.println("������Ϣ��"+message);
		}
		//check���
		resultMap.put("result", result);
		//message����
		resultMap.put("message", message);
		return resultMap;
	}
	/**
	 * ��֤��Ʒ�Ĺ���·���Ƿ�����ϵͳ�ж���
	 * @param taskRoutingEntityList
	 * @return
	 */
	private Map checkGongyiParts(List<TaskRoutingEntity> taskRoutingEntityList){
		Map resultMap = new HashMap();
		Set set=new HashSet();
		String result = "true";
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < taskRoutingEntityList.size(); i++)
		{
			TaskRoutingEntity taskRoutingEntity = taskRoutingEntityList.get(i);
			if(taskRoutingEntity!=null){
				List<RoutingEntity> routingEntityList = taskRoutingEntity.getRoutingEntityList();
				for (int j = 0; j < routingEntityList.size(); j++){
					RoutingEntity routingEntity = routingEntityList.get(j);
					if(routingEntity!=null){
						String routing_name = routingEntity.getName();
						int count = cmImpDAO.queryIsCode(routing_name);
						if(count <= 0){
							result = "false";
							set.add(routing_name);
						}
					}
				}
			}
		}
		//check���
		if("false".equals(result)){
			message.append("������Щ����·������δ���壬���鹤��·�����ƣ�");
			for(Iterator it=set.iterator();it.hasNext();)
			  {
				message.append(it.next()).append(",");
			  }
			System.out.println("������Ϣ��"+message);
		}
		//check���
		resultMap.put("result", result);
		//message����
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * ��֤���Ӳ�Ʒ�Ĺ���·���Ƿ�����ϵͳ�ж���
	 * @param taskRoutingEntityList
	 * @return
	 */
	private Map checkGongyiProducts(List<ProductRoutingEntiy> productRoutingEntityList ){
		Map resultMap = new HashMap();
		Set set=new HashSet();
		String result = "true";
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < productRoutingEntityList.size(); i++)
		{
			ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(i);
			if(productRoutingEntiy!=null){
				List<RoutingEntity> routingEntityList = productRoutingEntiy.getRoutingEntityList();
				for (int j = 0; j < routingEntityList.size(); j++){
					RoutingEntity routingEntity = routingEntityList.get(j);
					if(routingEntity!=null){
						String routing_name = routingEntity.getName();
						int count = cmImpDAO.queryIsCode(routing_name);
						if(count <= 0){
							result = "false";
							set.add(routing_name);
						}
					}
				}
			}
		}
		//check���
		if("false".equals(result)){
			message.append("������Щ����·������δ���壬���鹤��·�����ƣ�");
			for(Iterator it=set.iterator();it.hasNext();)
			  {
				message.append(it.next()).append(",");
			  }
			System.out.println("������Ϣ��"+message);
		}
		//check���
		resultMap.put("result", result);
		//message����
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * ��Ʒ+�ಿ�� ʱ��������������Ϊ��,��Ϊ0
	 * @param taskRoutingEntityList
	 * @return
	 */
	private Map checkProQty(List<TaskRoutingEntity> taskRoutingEntityList){
		Map resultMap = new HashMap();
		List repeatData = new ArrayList();
		String result = "true";
		StringBuffer message = new StringBuffer();
		for (int i = 0; i < taskRoutingEntityList.size(); i++)
		{
			TaskRoutingEntity taskRoutingEntity = taskRoutingEntityList.get(i);
			if(taskRoutingEntity!=null){
				//��������
				String task_routing_qty = taskRoutingEntity.getTask_routing_qty();
				//����code
				String task_routing_code = taskRoutingEntity.getTask_routing_code();
				if(StringUtils.isBlank(task_routing_qty) ||Float.parseFloat(task_routing_qty) <= 0){
					repeatData.add(task_routing_code);
					result = "false";
				}
			}
		}
		//check���
		if("false".equals(result)){
			message.append("������Щ�����������ڿ����ݣ��������²�����ŵ�������");
			for(int j = 0; j < repeatData.size()-1; j++){
				message.append(repeatData.get(j)).append(",");
			}
			message.append(repeatData.get(repeatData.size()-1));
			System.out.println("������Ϣ��"+message);
		}
		//check���
		resultMap.put("result", result);
		//message����
		resultMap.put("message", message);
		return resultMap;
	}
	/**
	 * ��Ʒexcel�ڲ������в�Ʒ����ظ�check
	 * @param productRoutingEntityList
	 * @return
	 */
	private Map checkProductCode(List<ProductRoutingEntiy>  productRoutingEntityList){
		Map resultMap = new HashMap();
		List repeatData = new ArrayList();
		String result = "true";
		StringBuffer message = new StringBuffer();
		//��Ʒcheck
		for (int i = 0; i < productRoutingEntityList.size() - 1; i++)
		{
			ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(i);
			//��Ʒ���
			String code = productRoutingEntiy.getCode();
			for (int j = i + 1; j < productRoutingEntityList.size(); j++)
			{
				ProductRoutingEntiy productRoutingTwo = productRoutingEntityList.get(j);
				//��Ʒ���
				String pro_code = productRoutingTwo.getCode();
				if (code.equals(pro_code))
				{
					repeatData.add(code);
					result = "false";
					
				}
			}
		}
		//check���
		if("false".equals(result)){
			message.append("EXCEL�в�Ʒ��������ظ����ݣ��������²�Ʒ��ţ�");
			for(int j = 0; j < repeatData.size()-1; j++){
				message.append(repeatData.get(j)).append(",");
			}
			message.append(repeatData.get(repeatData.size()-1));
			System.out.println("������Ϣ��"+message);
		}
		//check���
		resultMap.put("result", result);
		//message����
		resultMap.put("message", message);
		return resultMap;
	}
	
	/**
	 * ��Ʒ�жϲ�Ʒ����֯�����Ƿ��Ѿ�����
	 * @param productRoutingEntityList
	 * @return
	 */
	private Map isExistProductByTree(List<ProductRoutingEntiy>  productRoutingEntityList,String productOrParts){
		System.out.println("�жϲ�Ʒ�Ƿ��Ѿ����ڿ�ʼ");
		Map resultMap = new HashMap();
		List repeatData = new ArrayList();
		String result = "true";
		StringBuffer message = new StringBuffer();
		//��Ʒcheck
		if ("products".equals(productOrParts)){
			for (int i = 0; i < productRoutingEntityList.size(); i++)
			{
				ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(i);
				//��Ʒ���
				String code = productRoutingEntiy.getCode();
				System.out.println("��Ʒ���"+code);
				int count = cmImpDAO.queryProCode(code);
				//�����Ʒ�Ѿ�����
				if(count > 0){
					repeatData.add(code);
					result = "false";
				}
			}
		}else{
			//���Ӳ�Ʒcheck
			ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(0);
			//��Ʒ���
			String code = productRoutingEntiy.getCode();
			int count = cmImpDAO.queryProCode(code);
			//�����Ʒ�Ѿ�����
			if(count > 0){
				repeatData.add(code);
				result = "false";
			}
		}
		//check���
		if("false".equals(result)){
			message.append("������Щ��Ʒ�Ѿ������룡��Ʒ���Ϊ��");
			for(int j = 0; j < repeatData.size()-1; j++){
				message.append(repeatData.get(j)).append(",");
			}
			message.append(repeatData.get(repeatData.size()-1));
			System.out.println("������Ϣ��"+message);
		}
		//message����
		resultMap.put("message", message);
		//check���
		resultMap.put("result", result);
		return resultMap;
	}
	
	/**
	 * ��Ʒ�жϲ�Ʒ�Ƿ��Ѿ�����
	 * @param productRoutingEntityList
	 * @return
	 */
	private Map isExistProduct(List<ProductRoutingEntiy>  productRoutingEntityList,String productOrParts){
		Map resultMap = new HashMap();
		List repeatData = new ArrayList();
		String result = "true";
		StringBuffer message = new StringBuffer();
		if("products".equals(productOrParts)){
			for (int i = 0; i < productRoutingEntityList.size(); i++)
			{
				ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(i);
				//��Ʒ���
				String code = productRoutingEntiy.getCode();
				//��Ʒ����
				String name = productRoutingEntiy.getName();
				//��Ʒ���
				String specs = productRoutingEntiy.getSpecs();
				System.out.println("��Ʒ���"+code);
				int count = cmImpDAO.queryIsProCode(code,name,specs);
				//�����Ʒ�Ѿ�����
				if(count <= 0){
					repeatData.add(code);
					result = "false";
				}
			}
		}else{
			ProductRoutingEntiy productRoutingEntiy = productRoutingEntityList.get(0);
			//��Ʒ���
			String code = productRoutingEntiy.getCode();
			//��Ʒ����
			String name = productRoutingEntiy.getName();
			//��Ʒ���
			String specs = productRoutingEntiy.getSpecs();
			System.out.println("��Ʒ���"+code);
			int count = cmImpDAO.queryIsProCode(code,name,specs);
			//�����Ʒ�Ѿ�����
			if(count <= 0){
				repeatData.add(code);
				result = "false";
			}
		}
		//check���
		if("false".equals(result)){
			message.append("������Щ��Ʒ����Ʒ��Ϣ�в����ڣ�������Щ��Ʒ�Ĳ�Ʒ��ţ���Ʒ���ƣ���Ʒ��񣡲�Ʒ���Ϊ��");
			for(int j = 0; j < repeatData.size()-1; j++){
				message.append(repeatData.get(j)).append(",");
			}
			message.append(repeatData.get(repeatData.size()-1));
			System.out.println("������Ϣ��"+message);
		}
		//check���
		resultMap.put("result", result);
		resultMap.put("message", message);
		return resultMap;
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
				if(StringUtils.isNotBlank(code)&&StringUtils.isNotBlank(name)){
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
					pro_data[7] = 1;
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
								if(StringUtils.isNotBlank(routing_name)){
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
					if(result){
					Object hours_data[] = new Object[1];
					hours_data[0] = code;
					result = cmImpDAO.proAdd(hours_data,HOURS_SQL);
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
			if(StringUtils.isNotBlank(code)&&StringUtils.isNotBlank(name)){
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
					//�����ֶηǿ��ж�
					if(StringUtils.isBlank(task_routing_qty)){
						task_routing_qty="0";
					}
					if(StringUtils.isNotBlank(task_routing_code)&&StringUtils.isNotBlank(task_routing_name)){
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
									if(StringUtils.isNotBlank(routing_name)){
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
		if(result){
			product_id = code;
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
	
	/**
	 * ��ѯ����code�Ĳ㼶��
	 * @param task_code
	 * @return
	 */
	private int strLevel(String task_code){
		String str1 = "-";		
		String str2 = "��";	
		String str3 = "��";	
		int count = 0;
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int start1 = 0;
		int start2 = 0;
		int start3 = 0;
		while (task_code.indexOf(str1, start1) >= 0 && start1 < task_code.length()) {	
			count1++;			
			start1 = task_code.indexOf(str1, start1) + str1.length();
		}
		while (task_code.indexOf(str2, start2) >= 0 && start2 < task_code.length()) {	
			count2++;			
			start2 = task_code.indexOf(str2, start2) + str2.length();
		}
		while (task_code.indexOf(str3, start3) >= 0 && start3 < task_code.length()) {	
			count3++;			
			start3 = task_code.indexOf(str3, start3) + str3.length();
		}
		count = count1 + count2 + count3;
		return count;
	}
}
