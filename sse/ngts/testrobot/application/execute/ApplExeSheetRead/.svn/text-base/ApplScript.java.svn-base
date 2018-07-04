package sse.ngts.testrobot.application.execute.ApplExeSheetRead;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sse.ngts.testrobot.application.functions.AppSheetRead;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;

public class ApplScript {
private ArrayList<ApplExecutCase> stepList= new  ArrayList<ApplExecutCase>(); 

	
	/**
	 * 读取执行手册，执行每一步骤
	 * @param executeFileName －－ 执行手册文件名
	 */
	public ApplScript(File executeFileName)
	{
		sheetRead(executeFileName);
	}
	
    private void sheetRead(File executeFileName)
	{

    	try
        {
    	
            FileInputStream in = new FileInputStream(executeFileName);
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            HSSFSheet sheet = workbook.getSheet(ApplConstValues.excuteSheetName);
            if (sheet == null)
            {

            }

            int numberOfRows = sheet.getPhysicalNumberOfRows();
            String titleDesc[] = null;
           
			for (int j = 0; j < numberOfRows; j++)
            {
                HSSFRow row = sheet.getRow(j);
                if (row == null)
                {
                    continue;
                }
                if (j == 0)
                {
                    titleDesc = AppSheetRead.getTitleDesc(row);
                }
                else
                {
                    Hashtable values = AppSheetRead.wrapValues(titleDesc, row);
                    if(values.get(ApplExecuteConstValues.stepsId).toString().isEmpty())
                        continue;
                    if(values.get(ApplExecuteConstValues.scriptId).toString().isEmpty())
                        continue;
                    if(values.get(ApplExecuteConstValues.tradeData).toString().isEmpty())
                        continue;
                    if(values.get(ApplExecuteConstValues.testPhase).toString().isEmpty())
                        continue;
                    if(values.get(ApplExecuteConstValues.testStatus).toString().isEmpty())
                    {
                    	ApplExecuteResultDialog.viewError("执行失败,原因"+
                    			values.get("步骤编号").toString().isEmpty()+
                    			"的错误状态 为空", "ERROR");
                    	System.exit(0);
                    }
                    if("ynrlYNRL".indexOf(values.get(ApplExecuteConstValues.testStatus).toString().trim())==-1)
                    {
                    	ApplExecuteResultDialog.viewError("执行失败,原因"+
                    			values.get("步骤编号").toString()+
                    			"的错误状态 不对", "ERROR");
                    	System.exit(0);
                    }             
                    ApplScriptSet step = new ApplScriptSet();
                    step.setApplSteps(values);
                    this.stepList.add(step.getScript());                    
                }
            }
        }
        catch (FileNotFoundException e)
        {
        	ApplExecuteResultDialog.viewError("执行失败,执行手册不存在,请生成手册", "ERROR");
        }
        catch (Exception e)
        {
        	ApplExecuteResultDialog.viewError("执行失败,读取执行手册失败", "ERROR");
        	
        }


    }

	/**
	 * 获取执行完后生成的新的步骤详情数组
	 * @return ArrayList<ApplExecutCase> －－步骤详情数组
	 */
	public ArrayList<ApplExecutCase> getStepList() {
		return stepList;
	}

}
