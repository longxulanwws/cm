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
	 * 定义excel模板相关的配置对象
	 */
	// 产品
	protected static ProductConfig productConf = null;
	// 产品生产部件
	protected static ProductPartConfig productPartConf = null;
	// 工艺路线
	protected static RoutingConfig routingConf = null;

	/**
	 * 按excel模板解析导入的excel文件，将相关excel数据对象化。
	 * 
	 * @param fileName
	 *            导入的excel文件
	 * @return ProductionTaskRouting 部件信息对象list
	 */
	public static List<ProductionTaskRouting> readExcelData(String fileName) {

		// 解析配置文件 此部分在 并入 Spring web 容器时，不需要
		ApplicationContext context = new FileSystemXmlApplicationContext("src/cm.prdu.excel.config.xml");
		PrduExcelConfig prduExcelConfig = (PrduExcelConfig) context.getBean("prduExcelConfig");

		//
		productConf = prduExcelConfig.getProduct();
		productPartConf = prduExcelConfig.getProductPart();
		routingConf = prduExcelConfig.getRouting();

		/*
		 * 定义相关数据对象实体
		 */
		// 产品对象
		ProductEntiy pruductEntiy = new ProductEntiy();
		// 工艺项对象
		RoutingEntity RoutingEntity = new RoutingEntity();
		// 工艺项的List 并初始
		List<RoutingEntity> routingEntityList = new ArrayList<RoutingEntity>();

		// 初始 产品部件项对象
		ProductionTaskRouting taskRouting = new ProductionTaskRouting();
		// 初始 产品部件项对象List
		List<ProductionTaskRouting> taskRoutingList = new ArrayList<ProductionTaskRouting>();

		/*
		 * 开始解析excel文件，进行每sheet 每row，每cell 处理
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
				throw new IllegalArgumentException("不支持除：xls/xlsx以外的文件格式!!!");
			}
			// 仅处理第一个sheet 的内容
			Sheet sheet1 = workbook.getSheetAt(0);

			String cellValue = null;

			// 将此excel 中全部的工艺项放入临时 map 中
			HashMap<String, String> routingNameMap = new HashMap<String, String>();
			// 进行逐行（row）处理
			for (Row row : sheet1) {
				// 定义使用的产品部件信息
				// 部件编号
				String task_routing_code = "";
				// 部件名称
				String task_routing_name = "";
				// 部件数量
				String task_routing_qty = "";

				// 部件全部工艺项
				String task_routing_routingName = "";

				// 进行逐单元格（cell）处理
				for (Cell cell : row) {

					// 获得 单元格的坐标 如 A1 ，B6等
					CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

					 System.out.print(cellRef.formatAsString());
					 System.out.print(" - ");

					// 按excel单元格类型进行取值
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
					 * 产品相关信息处理
					 */
					// 产品编号赋值
					String product_code = productConf.getCode().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";

					// 非空时给产品对象赋值
					if (StringUtils.isNotBlank(product_code))
						pruductEntiy.setCode(product_code);

					// 产品名称赋值
					String product_name = productConf.getName().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";
					// 非空时给产品对象赋值
					if (StringUtils.isNotBlank(product_name))
						pruductEntiy.setName(product_name);

					// 产品规格型号赋值
					String product_specs = productConf.getSpecs().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";
					// 非空时给产品对象赋值
					if (StringUtils.isNotBlank(product_specs))
						pruductEntiy.setSpecs(product_specs);

					// 产品单位赋值
					String product_uom = productConf.getUom().equalsIgnoreCase(cellRef.formatAsString()) ? cellValue : "";
					// 非空时给产品对象赋值
					if (StringUtils.isNotBlank(product_uom))
						pruductEntiy.setUom(product_uom);

					/*
					 * 部件相关信息处理
					 */

					// 部件相关信息赋值
					String cellCoords = cellRef.formatAsString();
					String cellCoordsX = cellCoords.substring(0, 1);
					int cellCoordsY = Integer.valueOf(cellCoords.substring(cellCoords.length() - 1, cellCoords.length()));

					// 对excel中定义的“产品部件数据”区域处理
					if (productPartConf.getCodeY() <= cellCoordsY) {
						// 按设置的列进行赋值
						// 部件编号列
						if (productPartConf.getCodeX().equalsIgnoreCase(cellCoordsX))
							task_routing_code = cellValue;
						// 部件名称列
						if (productPartConf.getNameX().equalsIgnoreCase(cellCoordsX))
							task_routing_name = cellValue;
						// 部件数量列
						if (productPartConf.getNumX().equalsIgnoreCase(cellCoordsX))
							task_routing_qty = cellValue;
						// 该部件需要的全部工艺项 列
						if (productPartConf.getRoutingNameX().equalsIgnoreCase(cellCoordsX))
							task_routing_routingName = cellValue;

					}

					/*
					 * 工艺相关赋值 根据对工艺路线是否赋值判断是否此部件需要哪项工艺
					 */
					// 工艺项名称
					String routing_name = cellCoordsX.compareToIgnoreCase(routingConf.getNameX()) >= 0 && routingConf.getNameY() == cellCoordsY ? cellValue : "";
					// 将解析的excel中设置的工艺项列，生成map,使用列标记出 如 A
					if (StringUtils.isNotBlank(routing_name)) {
						System.out.println("routing_name:" + routing_name);
						routingNameMap.put(cellCoordsX, routing_name);
					}
					// 各项工艺的工时定额（分）处理
					String routing_hours = "";
					// 从 工艺工时数据区域开始处理
					if (routingConf.getHoursY() <= cellCoordsY) {
						// 通过excel中设置的工艺项列map 要从那些cell中取工时定额数据
						if (StringUtils.isNotBlank(routingNameMap.get(cellCoordsX))) {
							RoutingEntity.setName(routingNameMap.get(cellCoordsX));
							if (StringUtils.isNotBlank(cellValue))
								routing_hours = cellValue;
							// 只有设置了工时定额的工艺项才插入 每项部件对象下的工艺路线项对象list中
							if (StringUtils.isNotBlank(routing_hours)) {
								RoutingEntity.setHours(routing_hours);
								// 插入 每项部件对象下的工艺路线项对象list中
								routingEntityList.add(RoutingEntity);

								System.out.println("routing_name:" + RoutingEntity.getName());
								System.out.println("routing_hours:" + RoutingEntity.getHours());
							}
						}
					}
				}// end of rows
					// 一行处理结束后处理
					// 按每行（row）生成一个产品部件数据对象处理
				if (StringUtils.isNotBlank(task_routing_code))
					taskRouting.setTask_routing_code(task_routing_code);

				if (StringUtils.isNotBlank(task_routing_name))
					taskRouting.setTask_routing_name(task_routing_name);

				if (StringUtils.isNotBlank(task_routing_qty))
					taskRouting.setTask_routing_qty(task_routing_qty);

				if (StringUtils.isNotBlank(task_routing_routingName))
					taskRouting.setTask_routing_qty(task_routing_routingName);

				// 设置产品对象
				taskRouting.setPruductEntiy(pruductEntiy);
				// 设置单个部件的工艺路线数据list对象
				taskRouting.setRoutingEntityList(routingEntityList);
				// 清空 工艺路线数据list对象
				routingEntityList.clear();

				// 若部件项code\name不为空，则部件list增加部件对象
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
		List<ProductionTaskRouting> list = readExcelData("src/com/ruisoft/test/poi/XX公司XX产品工艺工时定额导入模板 .xls");
		System.out.println("ProductionTaskRouting List\n" + list);

	}

}