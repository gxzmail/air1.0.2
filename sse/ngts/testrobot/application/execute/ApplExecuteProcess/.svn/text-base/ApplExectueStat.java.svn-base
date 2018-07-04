package sse.ngts.testrobot.application.execute.ApplExecuteProcess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;

public class ApplExectueStat {


	private  String errorNum ;
	private  String sumSteps ; 
	private  String sucessNum;
	private  String sheetName;
	private static ApplExectueStat instance;
    
	public static ApplExectueStat getInstance()
    {
        if (instance == null)
        {
            instance = new ApplExectueStat();
        }
        return instance;
    }
	
	public void  execResultWrite(String fileName,ActionController c)
	{
		
		try {
			File fi = new File(fileName);
			if(!fi.exists()||!fi.isFile())
			{
				ApplFileProcess.createDir(fileName, ApplExecuteConstValues.APPL_File);
				fi.createNewFile();
			}
			FileWriter file = new FileWriter(fileName,true);
			PrintWriter pwrite = new PrintWriter(file);
			pwrite.write("------------------------------------------------------");
			pwrite.println();
			if(!c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG))
			{
			}
			pwrite.write("步骤号  ："+c.getCurrentScript().getFrmCase().getStepsId());
			pwrite.println();
			pwrite.write("脚本ID  ："+c.getCurrentScript().getFrmCase().getScriptId());
			pwrite.println();
			pwrite.write("执行状态："+c.getCurrentScript().getTestResultDescr());
			/*
			if(c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
					c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG))
			    pwrite.write("执行状态：执行成功");
			else if(c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
					!c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
					!c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_FINISH_FLAG))					
			    pwrite.write("执行状态：需要手动执行"); 
			else if(c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
					!c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
					c.getCurrentScript().getAttribute(ApplExecutCase.ATTR_FINISH_FLAG))	
			    pwrite.write("执行状态：执行失败"); 
		*/
			pwrite.println();
			pwrite.write("执行结果：");
			pwrite.println();
			/*2012-09-11,修改只显示最新日志,by wuli*/
			/*
			while(i.hasNext())
			{
				pwrite.write("第"+j+"次执行--"+i.next());
				j++;
				pwrite.println();
			}
			*/
			int j = c.getCurrentScript().getTestResult().size();	
			if(j>0)
			{
			    pwrite.write(c.getCurrentScript().getTestResult().get(j-1));
			    pwrite.println();
			}
			/*2012-09-11,修改只显示最新日志,by wuli*/
			pwrite.write("------------------------------------------------------");
			pwrite.println();
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void  statProcess(String ExecFile,String OutFile,ArrayList<ActionController> actionscontroller)			
	{		
		this.sheetName = ExecFile;
		statResult(actionscontroller);	
		writeResultStat(OutFile,actionscontroller);		
		
	}
	public void  statResult(ArrayList<ActionController> steps)
	{
		
		if(steps==null)return;
		Iterator<ActionController> c = steps.iterator();
	    ApplExecutCase a;

	    int errorNum1 = 0;
	    int sumSteps1 = 0;
	    while(c.hasNext())
	    {	    	
	    	ActionController action = c.next();
	    	if(!action.getApplSheetUIController().getSheetName().equalsIgnoreCase(sheetName))
	    	    continue;
	    	a = action.getCurrentScript();
	    	if(!a.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
	    		continue;
	    	if(!a.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG))
	    	{	
	    		errorNum1++;
	    	}
	    	sumSteps1++;
	    }
	    sumSteps = String.valueOf(sumSteps1);
	    errorNum = String.valueOf(errorNum1);
	    sucessNum = String.valueOf(sumSteps1-errorNum1);
	}
	
	public void writeResultStat(String FilePath,ArrayList<ActionController> resultStat)
	{

		File file = new File(FilePath);
		try{
			
			ApplFileProcess.createDir(FilePath,ApplExecuteConstValues.APPL_File);
			if(!file.exists()||!file.isFile())
				file.createNewFile();
            FileOutputStream in = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            
            HSSFCellStyle titleStyle =setTitleStyle(workbook);
            HSSFCellStyle cellStyle =setCellStyle(workbook);
            HSSFSheet sheet = workbook.createSheet(ApplExecuteConstValues.statReuslt);       
            
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell= row.createCell((short)(0));  
		    cell.setCellValue("执行统计结果，总共执行步骤数为:"+sumSteps+
		    		";执行失败步骤为：" +errorNum+
		    		";成功步骤为："+sucessNum);
		    
		    row = sheet.createRow(1);
            cell= row.createCell((short)(0));  
		    cell.setCellValue("执行失败步骤如下：");
		    		    
		    Iterator<ActionController> c = resultStat.iterator();
		    int j = 2;
            row = sheet.createRow(j);
            
		    cell= row.createCell((short)(0));  
		    cell.setCellStyle(titleStyle);
		    cell.setCellValue("步骤ID");
		    
		    cell= row.createCell((short)(1));  
		    cell.setCellStyle(titleStyle);
		    cell.setCellValue("脚本编号");
		    
		    cell= row.createCell((short)(2));  
		    cell.setCellStyle(titleStyle);
		    cell.setCellValue("错误状态");
		    
		    cell= row.createCell((short)(3));  
		    cell.setCellStyle(titleStyle);
		    cell.setCellValue("错误原因");
        	j++;
		    while(c.hasNext())
            {			    	
		    	ActionController action = c.next();
		    	if(!action.getApplSheetUIController().getSheetName().equalsIgnoreCase(sheetName))
		    		continue;
		    	ApplExecutCase step = action.getCurrentScript();
		    	if(!step.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)||
		    			step.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG))
		    		continue;
                row = sheet.createRow(j);
                
                cell= row.createCell((short)(0));  
    		    cell.setCellStyle(cellStyle);
    		    cell.setCellValue(step.getFrmCase().getStepsId());
    		    
    		    cell= row.createCell((short)(1));  
    		    cell.setCellStyle(cellStyle);
    		    cell.setCellValue(step.getFrmCase().getScriptId());
    		    
    		    cell= row.createCell((short)(2));  
    		    cell.setCellStyle(cellStyle);
    		    cell.setCellValue(step.getFrmCase().getTestStatus());
    		    
    		    cell= row.createCell((short)(3));  
    		    cell.setCellStyle(cellStyle);
    			int i = step.getTestResult().size(); 
    			if(i>0)
    			{
    	    	    cell.setCellValue(step.getTestResult().get(i-1));   				
    		    //cell.setCellValue(step.getTestResult().toString());
    			}
    	    	j++;
    		    
            }  
		    workbook.write(in);
            in.close();
		}catch(Exception e)
	     {
	   		    Logger.getLogger(ApplExecuteConstValues.logExecutName).
	            log(Level.INFO, "写文件{0}失败",FilePath);
	        	Logger.getLogger(ApplExecuteConstValues.logExecutName).
		         log(Level.WARNING, "执行结果统计生成失败 ");
	         	
		 }
	}
	
	public  HSSFCellStyle  setTitleStyle(HSSFWorkbook workbook)
	{
		HSSFCellStyle titleStyle =workbook.createCellStyle();
        titleStyle.setBorderBottom((short)2);
        titleStyle.setBorderLeft((short)2);
        titleStyle.setBorderRight((short)2);
        titleStyle.setBorderTop((short)2);
        return titleStyle;
	}
	public   HSSFCellStyle setCellStyle(HSSFWorkbook workbook)
	{
        HSSFCellStyle cellStyle =setTitleStyle(workbook);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        cellStyle.setBorderTop((short)1);
        return cellStyle;
	}
	@SuppressWarnings("deprecation")
	public  HSSFRow writeCells(int stepId, HSSFRow row,ApplExecutCase step,HSSFCellStyle cellStyle)
	{

		int z= 0;
        HSSFCell cell= row.createCell((short)z++); 
        cell.setCellStyle(cellStyle);
        cell.setCellValue(String.valueOf(stepId));
        
        cell= row.createCell((short)z++); 
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getFrmCase().getScriptId());
        
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getFrmCase().getDescrip());
        
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getFrmCase().getTestContent());
        
        
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getFrmCase().getTestAntic());
        
        
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getFrmCase().getTestBatch());
        
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getFrmCase().getTestStatus());
        
        
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        if(step.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
        	cell.setCellValue("未执行");
        else if(step.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG))
            cell.setCellValue("执行成功");
        else
        	cell.setCellValue("执行失败");
       
        
        cell= row.createCell((short)z++);  
        cell.setCellStyle(cellStyle);
        cell.setCellValue(step.getFrmCase().getTestMemo());
        return row;
	}

}
