package sse.ngts.testrobot.application.creatExecute.ApplCaseSteps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sse.ngts.testrobot.application.functions.AppSheetRead;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplCase;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
import sse.ngts.testrobot.exception.ApplFatalException;

/***from doc
	1．类名称：ApplCaseSteps
       2．类功能：根据用例ID，读取该用例文件，
       				得到该用例中的"用例步骤"数组或文件；
       				读取框架文件，输出框架的所有"框架步骤"数组和文件


***/

public class ApplCaseSteps{
    private ArrayList<ApplFrmwkCase> steps;
	
	public ApplCaseSteps()
	{		
			
	}
	/*************************************
	 *函数功能: 从用例或框架文件中获取所有步骤
	 *函数输入：
	 *String sheetNm        －－脚本表单名称     /***xzguo     每个xls的sheet为测试脚本
	 *File file             －－脚本路径
	 *int num               －－从第几行开始读
	 *ApplCase caseDetail   －－用例详情     /***xzguo  D_ONxx.xls中的一行
	 *函数返回值：
	 *空
	 *************************************/
	public Boolean readCaseStep(String sheetNm, File file,int num,ApplCase caseDetail)
    {
		
	 		 
		Logger.getLogger(ApplConstValues.logName).
		                 entering("ApplCaseSteps", "readCaseStep");
		try
        {
            Hashtable dupCaseChk = new Hashtable();
            FileInputStream in = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            HSSFSheet sheet = workbook.getSheet(sheetNm);
            if (sheet == null)
            {
            }
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            String titleDesc[] = null;
            steps = new ArrayList<ApplFrmwkCase>();

            for (int j = num; j < numberOfRows; j++)
            {
                HSSFRow row = sheet.getRow(j);
                
                if (row == null)
                {
                    continue;
                }
                if (j == num)
                {
/***xzguo
 *  titleDesc = AppSheetRead.getTitleDesc(row)作用是获取列标注所在行的所有列标注(脚本编号、脚本描述等)
 *  赋值给titleDesc
 */
                    titleDesc = AppSheetRead.getTitleDesc(row);
/***xzguo   不对，貌似分析的不对
 * AppSheetRead.deteTilt(titleDesc,ApplConstValues.stepTitle, file.getAbsolutePath()) 作用是
 * 检查   列    描述所在的行的值都在ApplConstValues.stepTitle 中
 */
                    if(!AppSheetRead.deteTilt(titleDesc,ApplConstValues.stepTitle, file.getAbsolutePath()))
                		return false;                	
                }
                else
                {

                	Hashtable<String,String> values = AppSheetRead.wrapValues(titleDesc, row);   // 该步的作用是          
                																		//把NGTS_AM_AIR_ON05_001_001_CV01.xls 中第row + 1行
                																		//(从0开始计数) 的值和对应的列标签
                																		//(NGTS_AM_AIR_ON05_001_001_CV01.xls中第4行，row取值为 3 )
                																		//关联起来，可以
                																		//通过列标签找到对应的值
                    if(values.get("脚本编号").toString().isEmpty())
                        continue;
                    if(values.get("交易日").toString().isEmpty())
                    {
                    	Logger.getLogger(ApplConstValues.logName).
                    	  log(Level.SEVERE, "步骤{0}的交易日字段为空",caseDetail.getCaseId()+"_"+
               		         values.get("脚本编号").toString());
                    	return false;
                    }
                    if(!values.get("交易日").toString().trim().toUpperCase().startsWith("T")||!values.get("交易日").toString().trim().substring(1).matches("[0-9]+"))
                    {
                    	Logger.getLogger(ApplConstValues.logName).
                    	  log(Level.SEVERE, "步骤{0}的交易日字段值格式错误！",caseDetail.getCaseId()+"_"+
               		         values.get("脚本编号").toString());
                    	
                    }
                    if(values.get("执行阶段").toString().isEmpty())
                    {                    	
                    	Logger.getLogger(ApplConstValues.logName).
                  	       log(Level.SEVERE, "步骤{0}的执行阶段字段为空",caseDetail.getCaseId()+"_"+
                		         values.get("脚本编号").toString());
                    	  return false;
                    }
                    if(values.get("错误状态").toString().isEmpty())
                    {
                    	Logger.getLogger(ApplConstValues.logName).
                        log(Level.SEVERE, "步骤{0}的错误状态为空",caseDetail.getCaseId()+"_"+
                        		         values.get("脚本编号").toString());
                    	Logger.getLogger(ApplConstValues.logName).
           	            log(Level.SEVERE, "执行手册生成失败 ");
						ApplExecuteResultDialog.viewError("执行失败，步骤"+caseDetail.getCaseId()+"_"+
								 values.get("脚本编号").toString()+"错误状态为空", "ERROR");
						return false;
                    }
                    if(-1=="ynrlYNRL".indexOf(values.get("错误状态").toString().trim()))
                    {
                    	ApplExecuteResultDialog.viewError("执行失败,原因"+
                    			caseDetail.getCaseId()+"_"+
               		         values.get("脚本编号").toString()+
                    			"的错误状态 不对", "ERROR");
                    	Logger.getLogger(ApplConstValues.logName).
                    	     log(Level.SEVERE, "步骤{0}的错误状态不对",caseDetail.getCaseId()+"_"+
               		         values.get("脚本编号").toString());
           	            Logger.getLogger(ApplConstValues.logName).
  	                           log(Level.SEVERE, "执行手册生成失败 ");
           	            return false;
                    }
                    if(values.get("脚本执行内容").toString().isEmpty())
                    {                    	
                    	Logger.getLogger(ApplConstValues.logName).
                  	       log(Level.SEVERE, "步骤{0}的脚本执行内容字段为空",caseDetail.getCaseId()+"_"+
                		         values.get("脚本编号").toString());
                    	  return false;
                    }
                    ApplStepsSet step = new ApplStepsSet();
/***
		step.setApplSteps(values,caseDetail) 该步骤为把每一个NGTS_AM_AIR_ON05_001_001_CV01.xls 中的每一行内容(values)
		和NGTS_AM_AIR_D_ON05_CV01.xls   (caseDetail)   中的某行记录中 "用例描述" 字段放入steps数组中
***/
                    step.setApplSteps(values,caseDetail);
                    steps.add(step.getSteps());
                }
            }
            Logger.getLogger(ApplConstValues.logName).
                log(Level.INFO, "读取文件{0}成功",file.getName());	
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
			Logger.getLogger(ApplConstValues.logName).
               log(Level.SEVERE, "找不到文件{0}",file.getName());
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "执行手册生成失败 ");
			ApplExecuteResultDialog.viewError("执行失败,找不到文件"+file.getName(), "ERROR");
            return false;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            Logger.getLogger(ApplConstValues.logName).
                log(Level.SEVERE, "读取文件{0}失败",file.getName());	
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "执行手册生成失败 ");
			ApplExecuteResultDialog.viewError("执行失败,读取"+file.getName()+"失败", "ERROR");
       	    return false;
        }
        return true;
    }
	
	public ArrayList<ApplFrmwkCase> getSteps() {
		return steps;
	}

}
