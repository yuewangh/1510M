package com.wy.utils.poi;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIexcel {
	public static void main(String[] args) throws Exception {
		exportExcel();
		//readexcell("D:\\test.xls");
	}
	/**
	 * 生成excel
	 * @throws Exception
	 */
	public static void exportExcel() throws Exception{
		//创建一个输出流
		FileOutputStream out = new FileOutputStream(new File("D:\\test1.xls"));
		try {
			//创建excel工作薄
			HSSFWorkbook wb = new HSSFWorkbook();
			//创建sheet
			HSSFSheet sheet = wb.createSheet("测试");
			
			//创建样式对象
			HSSFCellStyle style = wb.createCellStyle(); // 样式对象
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			
			//创建第一行
			HSSFRow row = sheet.createRow(0);
			//表头
			HSSFCell cell = row.createCell(0);
			cell.setCellValue("列1");
			cell.setCellStyle(style);
			
			cell = row.createCell(1);
			cell.setCellValue("列2");
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue("列3");
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue("列4");
			cell.setCellStyle(style);
			//表数据
			for(int i=0;i<10;i++){
				HSSFRow listRow = sheet.createRow(i+1);
				HSSFCell cell1 = listRow.createCell(0);
				cell1.setCellValue(i+"_1");
				cell1.setCellStyle(style);
				
				cell1 = listRow.createCell(1);
				cell1.setCellValue(2);
				cell1.setCellStyle(style);
				
				cell1 = listRow.createCell(2);
				cell1.setCellValue("2016-12-12");
				cell1.setCellStyle(style);
				
				cell1 = listRow.createCell(3);
				cell1.setCellValue("1234567890987654321");
				cell1.setCellStyle(style);
				
			}
			//设置第3列自适应
			sheet.autoSizeColumn(2); 
			//设置第4列自适应
			sheet.autoSizeColumn(3); 
			//把工作薄写入输出流
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取excel
	 * @param args
	 * @throws Exception 
	 * @throws Exception
	 */
	public static void readexcell(String fileName) throws Exception{
		
		// 判断文件是否存在
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }
        //获取excel的第一个sheet
        Sheet sheet = getSheet(fileName);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		// 根据第一行获取列数
		Row row = sheet.getRow(0);
		//获取第一行的列数
		int colNum = row.getPhysicalNumberOfCells();
		//循环所有行
		for (int i = 0; i <= rowNum; i++) {
			row = sheet.getRow(i);
			if (row != null) {
				//循环所有列
				for (int j = 0; j < colNum; j++) {
					System.out.println("第"+(i+0)+"行，第"+j+1+"列数据:"+getCellValue(row.getCell(j)));
				}
			}
		}
	}

	/**
	 * 根据表名获取第一个sheet
	 * @param path
	 * @return
	 * @throws Exception 
	 */
	public static Sheet getSheet(String fileName) throws Exception {
		//获取文件的后缀
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf("."));
		//把文件读入成一个输入流
		InputStream is = new FileInputStream(fileName);
		if (".xls".equals(extension)) {//2003
			POIFSFileSystem fs = new POIFSFileSystem(is);
			return new HSSFWorkbook(fs).getSheetAt(0);//获取第一个sheet
		} else if (".xlsx".equals(extension) || ".xlsm".equals(extension)) {
			return new XSSFWorkbook(is).getSheetAt(0);//获取第一个sheet
		} else {
			throw new IOException("文件名为" + fileName + "的文件,无法识别！");
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
                //在excel里,日期也是数字,在此要进行判断 
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 Date date = cell.getDateCellValue();  
					 result =  sdf.format(date); 
				}else{
					//Decimal金额类型
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
    
}

