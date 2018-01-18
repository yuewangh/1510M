package com.wy.utils.poi;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class ExcelUtils {
	/**
	 * 读取excel 导入时，首先上传，读取文件。导入数据，一般都有固定模板；
	 * @param filepath 文件路径
	 * @param startrow 读取的开始行
	 * @Result 返回一个二维数组（第一维放的是行，第二维放的是列表）
	 * @throws Exception
	 */
	public static String[][] readexcell(String filepath,int startrow) throws Exception{
		// 判断文件是否存在
        File file = new File(filepath);
        if (!file.exists()) {
            throw new IOException("文件" + filepath + "W不存在！");
        }
        Sheet sheet = getSheet(filepath);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		// 根据第一行获取列数
		Row row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		
		String[][] content = new String[rowNum][colNum];
		String[] rows = null;
		for (int i = startrow; i <= rowNum- startrow + 1; i++) {
			row = sheet.getRow(i);
			rows = new String[colNum];
			for (int j = 0; j < colNum; j++) {
				rows[j] = getCellValue(row.getCell(j));
				content[i - startrow][j] = getCellValue(row.getCell(j));
			}
		}
		return content;
	}
	

	/**
	 * 根据表名获取第一个sheet
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	private static Sheet getSheet(String file) throws Exception {
		String extension = file.lastIndexOf(".") == -1 ? "" : file.substring(file.lastIndexOf("."));
		InputStream is = new FileInputStream(file);
		if (".xls".equals(extension)) {//2003
			POIFSFileSystem fs = new POIFSFileSystem(is);
			return new HSSFWorkbook(fs).getSheetAt(0);
		} else if (".xlsx".equals(extension) || ".xlsm".equals(extension)) {
			return new XSSFWorkbook(is).getSheetAt(0);
		} else {
			throw new IOException("文件（" + file + "）,无法识别！");
		}
	}
	  /**
     * 功能:获取单元格的值
     */
    private static String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                result = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
             // 在excel里,日期也是数字,在此要进行判断 
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 Date date = cell.getDateCellValue();  
					 result =  sdf.format(date); 
				}else{
					DecimalFormat df=new DecimalFormat("#"); 
					result=df.format(cell.getNumericCellValue());
				}
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                result = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_ERROR:
                result = cell.getErrorCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            default:
                break;
            }
        }
        return result.toString();
    }
    /**
     * 导出
     * 根据传入List数据集合导出Excel表格 生成本地excel
     * @param file （输出流路径）
     * @param list 任何对象类型的list（数据库直接查询出的）User（id，name，age，sex)
     * @param columnNames（表头名称）(姓名、性别、年龄)
     * @param columns （表头对应的列名）（name,sex,age）注意顺序
     * @param sheetName（sheet名称）
     */
 	@SuppressWarnings("rawtypes")
 	public static void exportExcelByList(String file, List list,String[] columnNames, String[] columns, String sheetName) {
 		try {
 			OutputStream fos = new FileOutputStream(file);
 			HSSFWorkbook wb = new HSSFWorkbook();
 			HSSFSheet sheet = wb.createSheet(sheetName);
 			HSSFCellStyle style = wb.createCellStyle(); // 样式对象
 			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
 			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
 			HSSFRow row = sheet.createRow(0);
 			for (int i = 0; i < columnNames.length; i++) {
 				HSSFCell cell = row.createCell(i);
 				cell.setCellValue(columnNames[i]);
 				cell.setCellStyle(style);
 			}
 			for (int i = 0; i < list.size(); i++) {
 				HSSFRow listRow = sheet.createRow(i + 1);
 				HashMap map = (HashMap) list.get(i);
 				for (int j = 0; j < columns.length; j++) {
 					HSSFCell listCell = listRow.createCell(j);
 					if (map.get(columns[j]) != null) {
 						listCell.setCellValue(map.get(columns[j]) + "");
 						listCell.setCellStyle(style);
 					} else {
 						listCell.setCellValue("");
 						listCell.setCellStyle(style);
 					}
 				}
 			}
 			wb.write(fos);
 			fos.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}

 	/**
 	 * 根据传入List数据集合导出Excel表格 返回页面选择保存路径的excel
 	 * @param response （响应页面）
 	 * @param list 数据列表
 	 * @param columnNames 表头
 	 * @param columns 对应列名
 	 * @param sheetName 
 	 * @param filename
 	 */
 	@SuppressWarnings("rawtypes")
 	public static void exportExcel(HttpServletResponse response,List list, String[] columnNames, String[] columns,String sheetName, String filename) {
 		try {
 			response.setCharacterEncoding("UTF-8");
 			response.reset();//清除缓存
 			response.setContentType("octets/stream");
 			response.addHeader("Content-Disposition", "attachment;filename="+ new String((filename).getBytes("UTF-8"), "iso8859-1")+ ".xls");
 			OutputStream fos = response.getOutputStream();
 			HSSFWorkbook wb = new HSSFWorkbook();
 			HSSFSheet sheet = wb.createSheet(sheetName);
 			HSSFCellStyle style = wb.createCellStyle(); // 样式对象
 			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
 			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平

 			HSSFRow row = sheet.createRow(0);
 			for (int i = 0; i < columnNames.length; i++) {
 				HSSFCell cell = row.createCell(i);
 				cell.setCellValue(columnNames[i]);
 				cell.setCellStyle(style);
 			}
 			for (int i = 0; i < list.size(); i++) {
 				HSSFRow listRow = sheet.createRow(i + 1);
 				Object o = list.get(i);
 				for (int j = 0; j < columns.length; j++) {
 					HSSFCell listCell = listRow.createCell(j);
 					Method m = o.getClass().getMethod("get" + upperStr(columns[j]));
 					String value = (String) m.invoke(o);
 					if (value != null) {
 						listCell.setCellValue(value + "");
 						listCell.setCellStyle(style);
 					} else {
 						listCell.setCellValue("");
 						listCell.setCellStyle(style);
 					}
 				}
 			}
 			wb.write(fos);
 			fos.close();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	/**
 	 * 把输入字符串的首字母改成大写
 	 * 
 	 * @param str
 	 * @return
 	 */
 	private static String upperStr(String str) {
 		char[] ch = str.toCharArray();
 		if (ch[0] >= 'a' && ch[0] <= 'z') {
 			ch[0] = (char) (ch[0] - 32);
 		}
 		return new String(ch);
 	}

}
