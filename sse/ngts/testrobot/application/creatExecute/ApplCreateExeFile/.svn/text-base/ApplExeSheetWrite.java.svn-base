package sse.ngts.testrobot.application.creatExecute.ApplCreateExeFile;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;

public class ApplExeSheetWrite {

	/**
	 * 函数功能：把步骤数组写入到执行手册中
	 * 函数输入：
	 * @param FilePath   －－执行手册路径
	 * @param steps      －－需要输出的步骤数组
	 * 函数返回值：
	 * 空
	 */
	@SuppressWarnings("deprecation")
	public   static Boolean writeExcuteSheet(String sheetName,String FilePath,ArrayList<ApplFrmwkCase> steps)
	{
		Logger.getLogger(ApplConstValues.logName).
          entering("ApplSheetWrite", "writeExcuteSheet");
	
		Iterator<ApplFrmwkCase> c = steps.iterator();
		ApplFrmwkCase a =new ApplFrmwkCase();
		String title[] = a.getTitle();
		int len= title.length;
		File file = new File(FilePath);
		try{
			if(!file.exists())
				file.createNewFile();
            FileOutputStream in = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            
            HSSFCellStyle titleStyle =setTitleStyle(workbook);
            HSSFCellStyle cellStyle =setCellStyle(workbook);
            HSSFSheet sheet = workbook.createSheet(sheetName);       
            
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell= row.createCell((short)0);  
            int j = 0;
            while(c.hasNext())
            {
                if(j ==0)
                {   
                	for(int z=0;z<len;z++)
        	        {
        		        cell= row.createCell((short)(z));  
        		        cell.setCellStyle(titleStyle);
        		        cell.setCellValue(title[z]);
        		    } 
                	j++;
                 }
                 else
                 {
                	 ApplFrmwkCase step = c.next();
                	 step.setStepsId(String.valueOf(j));
                	 row = sheet.createRow(j);
                	 row = writeCells(row,step,cellStyle);
    		         j++;
                 }
        
             }
            workbook.write(in);
            in.close();
   		    Logger.getLogger(ApplConstValues.logName).
                log(Level.INFO, "写文件{0}成功",file.getName());
		}
		catch(Exception e)
		{
   		    Logger.getLogger(ApplConstValues.logName).
            log(Level.INFO, "写文件{0}失败",FilePath);
        	//Logger.getLogger(ApplConstValues.logName).
	      //   log(Level.SEVERE, "执行手册生成失败 ");
		//	ApplExecuteResultDialog.viewError("执行失败,写文件"+file.getName()+"失败", "ERROR");
         	return false;
		}
		finally
		{
		}
     	return true;

	}
	
	public static HSSFCellStyle  setTitleStyle(HSSFWorkbook workbook)
	{
		HSSFCellStyle titleStyle =workbook.createCellStyle();
        titleStyle.setBorderBottom((short)2);
        titleStyle.setBorderLeft((short)2);
        titleStyle.setBorderRight((short)2);
        titleStyle.setBorderTop((short)2);
        return titleStyle;
	}
	public static  HSSFCellStyle setCellStyle(HSSFWorkbook workbook)
	{
        HSSFCellStyle cellStyle =setTitleStyle(workbook);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setDataFormat((short)0);   
        return cellStyle;
	}
	@SuppressWarnings("deprecation")
	public static HSSFRow writeCells(HSSFRow row,ApplFrmwkCase step,HSSFCellStyle cellStyle)
	{
        int z= 0;
        HSSFCell cell= row.createCell((short)z++); 
        cell.setCellStyle(cellStyle);
        cell.setCellValue(String.valueOf(step.getStepsId()));
        cell= row.createCell((short)z++); 
        cell.setCellStyle(cellStyle);
        cell.setCellValue(String.valueOf(step.getScriptId()));
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getDescrip());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestContent());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestDate());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestPhase());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestAntic());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestHost());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestBatch());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestStatus());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestPrior());
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getTestMemo());
        return row;
	}
}
