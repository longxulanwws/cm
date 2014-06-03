package com.ruisoft.test.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ruisoft.cm.base.excel.util.PrduExcelConfig;
import com.ruisoft.cm.base.excel.util.ProductConfig;
import com.ruisoft.cm.base.excel.util.ProductPartConfig;
import com.ruisoft.cm.base.excel.util.RoutingConfig;

public class ReadExcelTest {

	/*
	 * ����excelģ����ص����ö���
	 */
	// ��Ʒ
	protected static ProductConfig productConf = null;
	// ��Ʒ��������
	protected static ProductPartConfig productPartConf = null;
	// ����·��
	protected static RoutingConfig routingConf = null;

	/**
	 * ��excelģ����������excel�ļ��������excel���ݶ��󻯡�
	 * 
	 * @param fileName
	 *            �����excel�ļ�
	 * @return ProductionTaskRouting ������Ϣ����list
	 */
	public static ProductionTaskRouting readExcelData(String fileName,PrduExcelConfig prduExcelConfig) {

		// ���������ļ� �˲����� ���� Spring web ����ʱ������Ҫ
//		ApplicationContext context = new FileSystemXmlApplicationContext("src/cm.prdu.excel.config.xml");
//		PrduExcelConfig prduExcelConfig = (PrduExcelConfig) context.getBean("prduExcelConfig");

		//
		productConf = prduExcelConfig.getProduct();
		productPartConf = prduExcelConfig.getProductPart();
		routingConf = prduExcelConfig.getRouting();

		/*
		 * ����������ݶ���ʵ��
		 */
		// ��Ʒ����
		ProductRoutingEntiy productRoutingEntiy = new ProductRoutingEntiy();
		// ��Ʒ����List
		List<ProductRoutingEntiy> productRoutingEntityList = new ArrayList<ProductRoutingEntiy>();
		// ���������
		RoutingEntity RoutingEntity = new RoutingEntity();
		// �������List ����ʼ
		List<RoutingEntity> routingEntityList = new ArrayList<RoutingEntity>();
		// ���������
		TaskRoutingEntity taskRoutingEntity = new TaskRoutingEntity();
		// ��ʼ ��Ʒ���������List
		List<TaskRoutingEntity> taskRoutingEntityList = new ArrayList<TaskRoutingEntity>();

		// ��ʼ ��Ʒ���������
		ProductionTaskRouting productiontaskRouting = new ProductionTaskRouting();

		/*
		 * ��ʼ����excel�ļ�������ÿsheet ÿrow��ÿcell ����
		 */
		try {
			// Create the input stream from the xlsx/xls file
			FileInputStream fis = new FileInputStream(fileName);

			// Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			} else {
				throw new IllegalArgumentException("��֧�ֳ���xls/xlsx������ļ���ʽ!!!");
			}
			// �������һ��sheet ������
			Sheet sheet1 = workbook.getSheetAt(0);

			String cellValue = null;

			// ����excel ��ȫ���Ĺ����������ʱ map ��
			HashMap<String, String> routingNameMap = new HashMap<String, String>();
			
			String product_code = null;
			String product_name = null;
			// �������У�row������
			for (Row row : sheet1) {
				// ����ʹ�õĲ�Ʒ������Ϣ
				// �������
				String task_routing_code = "";
				// ��������
				String task_routing_name = "";
				// ��������
				String task_routing_qty = "";

				// ����ȫ��������
				String task_routing_routingName = "";

				// ������Ԫ��cell������
				
				routingEntityList = new ArrayList<RoutingEntity>();
				
				for (Cell cell : row) {

					// ��� ��Ԫ������� �� A1 ��B6��
					CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

					System.out.print(cellRef.formatAsString());
					System.out.print(" - ");

					// ��excel��Ԫ�����ͽ���ȡֵ
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						cellValue = cell.getRichStringCellValue().getString();
						// System.out.println(cell.getRichStringCellValue().getString());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							cellValue = cell.getDateCellValue().toString();
							// System.out.println(cell.getDateCellValue());
						} else {
							cellValue = String.valueOf(Math.round(cell.getNumericCellValue()));
							// System.out.println(cell.getNumericCellValue());
						}
						
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellValue = String.valueOf(cell.getBooleanCellValue());
						// System.out.println(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_FORMULA:
						cellValue = cell.getCellFormula();
						// System.out.println(cell.getCellFormula());
						break;
					default:
						cellValue = StringUtils.EMPTY;
						// System.out.println();
					}

					System.out.println("cellValue:" + cellValue);

					/*
					 * ��Ʒ�����Ϣ����
					 */
					
					String cellCoords = cellRef.formatAsString();
//					String cellCoordsX = cellCoords.substring(0, 1);
//					int cellCoordsY = Integer.valueOf(cellCoords.substring(1, cellCoords.length()));
					//row.getRowNum(), cell.getColumnIndex()
//					String cellCoordsX = String.valueOf(cell.getColumnIndex());
					int cellCoordsX = cell.getColumnIndex()+1;
					int cellCoordsY = row.getRowNum()+1;
					System.out.println("���꣺"+cellCoordsX+" : " +cellCoordsY);
					// ��excel�ж���ġ���Ʒ���ݡ�������
					if (productConf.getCodeY() <= cellCoordsY) {
						// �����õ��н��и�ֵ
						// ��Ʒ��Ÿ�ֵ
						//System.out.println("��excel�ж���ġ���Ʒ���ݡ�������"+cellValue);
						System.out.println("��excel�ж���ġ���Ʒ���ݡ��������Ʒ��Ÿ�ֵ"+product_code);
						if (productConf.getCodeX()==cellCoordsX && StringUtils.isNotBlank(cellValue)) {
							System.out.println("��excel�ж���ġ���Ʒ���ݡ�������?????"+cellValue);
							productRoutingEntiy.setCode(cellValue);
							//�ж����Ʒcode,˵���Ƕ����Ʒ
							if(StringUtils.isNotBlank(product_code)&&!StringUtils.equals(cellValue, product_code)){
								productiontaskRouting.setProductOrParts("products");
							}else{
								productiontaskRouting.setProductOrParts("parts");
							}
							
							product_code = cellValue;
 
						}

						// ��Ʒ���Ƹ�ֵ
						if (productConf.getNameX()==(cellCoordsX) && StringUtils.isNotBlank(cellValue)) {
							productRoutingEntiy.setName(cellValue);
							product_name = cellValue;
						}

						// ������
						if (productConf.getSpecsX()==(cellCoordsX))
							productRoutingEntiy.setSpecs(cellValue);

					}
					
					/*
					 * ���������Ϣ����
					 */

					// ���������Ϣ��ֵ

					// ��excel�ж���ġ���Ʒ�������ݡ�������
					if (productPartConf.getCodeY() <= cellCoordsY) {
						// �����õ��н��и�ֵ
						// ���������
						if (productPartConf.getCodeX()==(cellCoordsX))
							task_routing_code = cellValue;
						// ����������
						if (productPartConf.getNameX()==(cellCoordsX))
							task_routing_name = cellValue;
						// ����������
						if (productPartConf.getNumX()==(cellCoordsX))
							task_routing_qty = cellValue;
						// �ò�����Ҫ��ȫ�������� ��
						if (productPartConf.getRoutingNameX()==(cellCoordsX))
							task_routing_routingName = cellValue;

					}

					/*
					 * ������ظ�ֵ ���ݶԹ���·���Ƿ�ֵ�ж��Ƿ�˲�����Ҫ�����
					 */
					// ����������
					String routing_name = cellCoordsX >= routingConf.getNameX() && routingConf.getNameY() == cellCoordsY ? cellValue : "";
					// ��������excel�����õĹ������У�����map,ʹ���б�ǳ� �� 1
					if (StringUtils.isNotBlank(routing_name)) {
						System.out.println("routing_name:" + routing_name);
						routingNameMap.put(String.valueOf(cellCoordsX), routing_name);
					}
					// ����յĹ�ʱ����֣�����
					String routing_hours = "";
					// �� ���չ�ʱ��������ʼ����
					if (routingConf.getHoursY() <= cellCoordsY) {
						RoutingEntity = new RoutingEntity();
						// ͨ��excel�����õĹ�������map Ҫ����Щcell��ȡ��ʱ��������
						if (StringUtils.isNotBlank(routingNameMap.get(String.valueOf(cellCoordsX)))) {
							RoutingEntity.setName(routingNameMap.get(String.valueOf(cellCoordsX)));
							if (StringUtils.isNotBlank(cellValue))
								routing_hours = cellValue;
							// ֻ�������˹�ʱ����Ĺ�����Ų��� ÿ��������µĹ���·�������list��
							if (StringUtils.isNotBlank(routing_hours)) {
								RoutingEntity.setHours(routing_hours);
								// ���� ÿ��������µĹ���·�������list��
								routingEntityList.add(RoutingEntity);

								System.out.println("routing_name:" + RoutingEntity.getName());
								System.out.println("routing_hours:" + RoutingEntity.getHours());
							}
						}
					}
				}// end of rows
					// һ�д����������

				// ��������code\name��Ϊ�գ��򲿼�list���Ӳ�������
				if (StringUtils.isNotBlank(task_routing_code)&&StringUtils.isNotBlank(task_routing_name)) {
					// ��ÿ�У�row������һ����Ʒ�������ݶ�����
					if (StringUtils.isNotBlank(task_routing_code))
						taskRoutingEntity.setTask_routing_code(task_routing_code);

					if (StringUtils.isNotBlank(task_routing_name))
						taskRoutingEntity.setTask_routing_name(task_routing_name);

					if (StringUtils.isNotBlank(task_routing_qty))
						taskRoutingEntity.setTask_routing_qty(task_routing_qty);

					// ���õ��������Ĺ���·������list����
					taskRoutingEntity.setRoutingEntityList(routingEntityList);
					
					if (StringUtils.isNotBlank(product_code))
						taskRoutingEntity.setProduct_code(product_code);
					if (StringUtils.isNotBlank(product_name))
						taskRoutingEntity.setProduct_name(product_name);

					taskRoutingEntityList.add(taskRoutingEntity);
					
					//productiontaskRouting.setProductOrParts("parts");

					System.out.println("task_routing_code:" + task_routing_code);
					System.out.println("task_routing_name:" + task_routing_name);
					System.out.println("task_routing_qty:" + task_routing_qty);
					System.out.println("task_routing_routingName:" + task_routing_routingName);
					
					
				}
				
				if(StringUtils.isNotBlank(productRoutingEntiy.getCode())&&StringUtils.isNotBlank(productRoutingEntiy.getName())){
					// ���õ�����Ʒ�Ĺ���·������list����
					productRoutingEntiy.setRoutingEntityList(routingEntityList);
					// ��Ʒʱ����
					productRoutingEntityList.add(productRoutingEntiy);

				}
				
				// ��ʼ ����·������list���� �� ���ʵ��;
				taskRoutingEntity = new TaskRoutingEntity();
				productRoutingEntiy = new ProductRoutingEntiy();
				routingEntityList = new ArrayList<RoutingEntity>();



			}// end of row for loop
			productiontaskRouting.setProductRoutingEntityList(productRoutingEntityList);
			productiontaskRouting.setTaskRoutingEntityList(taskRoutingEntityList);

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return productiontaskRouting;
	}

//	public static void main(String args[]) {
//
//		// readExcelData("src/com/ruisoft/test/poi/Sample.xlsx");
//		//ProductionTaskRouting productionTaskRouting  = readExcelData("src/com/ruisoft/test/poi/XX��˾XX��Ʒ���չ�ʱ�����ģ�� .xls");
//		ProductionTaskRouting productionTaskRouting  = readExcelData("src/com/ruisoft/test/poi/2XX��˾XX��Ʒ���չ�ʱ�����ģ��.xls");
//		System.out.println("ProductionTaskRouting List\n" + productionTaskRouting);
//
//	}

}