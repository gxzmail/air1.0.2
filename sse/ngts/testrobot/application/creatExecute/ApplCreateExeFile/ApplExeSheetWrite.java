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
	 * �������ܣ��Ѳ�������д�뵽ִ���ֲ���
	 *
	 * @param FilePath   ����ִ���ֲ�·�� /***xzguo �����ȡֵ����ΪOutput\AIR_ExecuteFile_result\ON05_001_001_Steps.xls 
	 * @param steps      ��������Ĳ������� /***xzguo ���룬 ȡֵ����Ϊ NGTS_AM_AIR_ON05_001_001_CV01.xls������
	 * ��������ֵ��
	 * ��
	 *
	 * /***xzguo
	 *	����: NGTS_AM_AIR_ON05_001_001_CV01.xls����������
	 *  
	 *	 �ű����	 �ű�����	 �ű�ִ������	 ������  ִ�н׶�	 Ԥ�ڽ��	 ����	 ������  ����״̬	 ���ȼ�  ��ע
	 *  	001 ���۳��⣨QBW���걨���걨�ɹ��� perl air_feeder_ezstep.pl "exec STEP_FEED 'QBW','0501010001','010107','80086','R','9=11135=D11=050101000148=01010738=1453=3448=A800860001452=5448=80086452=1448=QWESD452=400158=13912345678','reqresp_80086'" ""	 T0  ONLINE 			 Y		 
	 *
	 * ��ӦOutput\AIR_ExecuteFile_result\ON05_001_001_Steps.xls����������:
	 *
	 *	������	 �ű����	 �ű�����	 �ű�ִ������	 ������  ִ�н׶�	 Ԥ�ڽ��	 ����	 ������  ����״̬	 ���ȼ�  ��ע
	 *
	 *		1	 ON05_001_001_001	 ���۳��⣨QBW���걨���걨�ɹ���Xzguo	 perl air_feeder_ezstep.pl "exec STEP_FEED 'QBW','0501010001','010107','80086','R','9=11135=D11=050101000148=01010738=1453=3448=A800860001452=5448=80086452=1448=QWESD452=400158=13912345678','reqresp_80086'" ""	 T0  ONLINE 			 Y	 1	 
	 * 
	 *	
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
                log(Level.INFO, "д�ļ�{0}�ɹ�",file.getName());
		}
		catch(Exception e)
		{
   		    Logger.getLogger(ApplConstValues.logName).
            log(Level.INFO, "д�ļ�{0}ʧ��",FilePath);
        	//Logger.getLogger(ApplConstValues.logName).
	      //   log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
		//	ApplExecuteResultDialog.viewError("ִ��ʧ��,д�ļ�"+file.getName()+"ʧ��", "ERROR");
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
