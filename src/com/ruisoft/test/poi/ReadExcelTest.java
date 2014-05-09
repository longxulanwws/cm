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
	public static List<ProductionTaskRouting> readExcelData(String fileName) {

		// ���������ļ� �˲����� ���� Spring web ����ʱ������Ҫ
		ApplicationContext context = new FileSystemXmlApplicationContext("src/cm.prdu.excel.config.xml");
		PrduExcelConfig prduExcelConfig = (PrduExcelConfig) context.getBean("prduExcelConfig");

		//
		productConf = prduExcelConfig.getProduct();
		productPartConf = prduExcelConfig.getProductPart();
		routingConf = prduExcelConfig.getRouting();

		/*
		 * ����������ݶ���ʵ��
		 */
		// ��Ʒ����
		ProductEntiy pruductEntiy = new ProductEntiy();
		// ���������
		RoutingEntity RoutingEntity = new RoutingEntity();
		// �������List ����ʼ
		List<RoutingEntity> routingEntityList = new ArrayList<RoutingEntity>();

		// ��ʼ ��Ʒ���������
		ProductionTaskRouting taskRouting = new ProductionTaskRouting();
		// ��ʼ ��Ʒ���������List
		List<ProductionTaskRouting> taskRoutingList = new ArrayList<ProductionTaskRouting>();

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
							cellValue = String.valueOf(cell.getNumericCellValue());
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
					// ��Ʒ��Ÿ�ֵ
					String product_code = productConf.getCode().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";

					// �ǿ�ʱ����Ʒ����ֵ
					if (StringUtils.isNotBlank(product_code))
						pruductEntiy.setCode(product_code);

					// ��Ʒ���Ƹ�ֵ
					String product_name = productConf.getName().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";
					// �ǿ�ʱ����Ʒ����ֵ
					if (StringUtils.isNotBlank(product_name))
						pruductEntiy.setName(product_name);

					// ��Ʒ����ͺŸ�ֵ
					String product_specs = productConf.getSpecs().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";
					// �ǿ�ʱ����Ʒ����ֵ
					if (StringUtils.isNotBlank(product_specs))
						pruductEntiy.setSpecs(product_specs);

					// ��Ʒ��λ��ֵ
					String product_uom = productConf.getUom().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";
					// �ǿ�ʱ����Ʒ����ֵ
					if (StringUtils.isNotBlank(product_uom))
						pruductEntiy.setUom(product_uom);

					/*
					 * ���������Ϣ����
					 */

					// ���������Ϣ��ֵ
					String cellCoords = cellRef.formatAsString();
					String cellCoordsX = cellCoords.substring(0, 1);
					int cellCoordsY = Integer.valueOf(cellCoords.substring(cellCoords.length() - 1, cellCoords.length()));

					// ��excel�ж���ġ���Ʒ�������ݡ�������
					if (productPartConf.getCodeY() <= cellCoordsY) {
						// �����õ��н��и�ֵ
						// ���������
						if (productPartConf.getCodeX().equalsIgnoreCase(cellCoordsX))
							task_routing_code = cellValue;
						// ����������
						if (productPartConf.getNameX().equalsIgnoreCase(cellCoordsX))
							task_routing_name = cellValue;
						// ����������
						if (productPartConf.getNumX().equalsIgnoreCase(cellCoordsX))
							task_routing_qty = cellValue;
						// �ò�����Ҫ��ȫ�������� ��
						if (productPartConf.getRoutingNameX().equalsIgnoreCase(cellCoordsX))
							task_routing_routingName = cellValue;

					}

					/*
					 * ������ظ�ֵ ���ݶԹ���·���Ƿ�ֵ�ж��Ƿ�˲�����Ҫ�����
					 */
					// ����������
					String routing_name = cellCoordsX.compareToIgnoreCase(routingConf.getNameX()) >= 0 && routingConf.getNameY() == cellCoordsY ? cellValue : "";
					// ��������excel�����õĹ������У�����map,ʹ���б�ǳ� �� A
					if (StringUtils.isNotBlank(routing_name)) {
						System.out.println("routing_name:" + routing_name);
						routingNameMap.put(cellCoordsX, routing_name);
					}
					// ����յĹ�ʱ����֣�����
					String routing_hours = "";
					// �� ���չ�ʱ��������ʼ����
					if (routingConf.getHoursY() <= cellCoordsY) {
						// ͨ��excel�����õĹ�������map Ҫ����Щcell��ȡ��ʱ��������
						if (StringUtils.isNotBlank(routingNameMap.get(cellCoordsX))) {
							RoutingEntity.setName(routingNameMap.get(cellCoordsX));
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
					// ��ÿ�У�row������һ����Ʒ�������ݶ�����
				if (StringUtils.isNotBlank(task_routing_code))
					taskRouting.setTask_routing_code(task_routing_code);

				if (StringUtils.isNotBlank(task_routing_name))
					taskRouting.setTask_routing_name(task_routing_name);

				if (StringUtils.isNotBlank(task_routing_qty))
					taskRouting.setTask_routing_qty(task_routing_qty);

				if (StringUtils.isNotBlank(task_routing_routingName))
					taskRouting.setTask_routing_qty(task_routing_routingName);

				// ���ò�Ʒ����
				taskRouting.setPruductEntiy(pruductEntiy);
				// ���õ��������Ĺ���·������list����
				taskRouting.setRoutingEntityList(routingEntityList);
				// ��� ����·������list����
				routingEntityList.clear();

				// ��������code\name��Ϊ�գ��򲿼�list���Ӳ�������
				if (StringUtils.isNotBlank(task_routing_name)) {
					taskRoutingList.add(taskRouting);

					System.out.println("task_routing_code:" + task_routing_code);
					System.out.println("task_routing_name:" + task_routing_name);
					System.out.println("task_routing_qty:" + task_routing_qty);
					System.out.println("task_routing_routingName:" + task_routing_routingName);
				}

			}// end of sheets for loop

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return taskRoutingList;
	}

	public static void main(String args[]) {

		// readExcelData("src/com/ruisoft/test/poi/Sample.xlsx");
		List<ProductionTaskRouting> list = readExcelData("src/com/ruisoft/test/poi/XX��˾XX��Ʒ���չ�ʱ�����ģ�� .xls");
		System.out.println("ProductionTaskRouting List\n" + list);

	}

}