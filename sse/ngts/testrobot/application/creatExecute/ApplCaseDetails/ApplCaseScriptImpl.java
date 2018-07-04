package sse.ngts.testrobot.application.creatExecute.ApplCaseDetails;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.functions.ApplCaseScriptSort;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplCase;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.exception.ApplFatalException;


/**from doc
1．	类名称：ApplCaseScript, ApplCaseScriptImpl  为 接口 ApplCaseScript的具体实现
2．	类功能：根据"用例列表"数组，得到该用例列表的"用例详情"数组  
                                    ，判断所有的用例的测试环境是否一致，如果不一致出错 
                                    ，测试中断。

***/


public class ApplCaseScriptImpl implements ApplCaseScript{
	
	private ArrayList<ApplCase> caseScript= new ArrayList<ApplCase>();
	
    /**************************************************
     * 函数功能：根据用例列表和详情列表获取用例列表详情数据
     * 函数输入：
     * String pathDirc          －－用例所在文件夹
     * Hashtable pathCfg        －－所有文件配置路径
     * HashSet<String>caseList  －－用例列表
     * HashSet<String>sceneList －－场景列表
     * 函数返回值：
     *      空
     ****************************************************/
	public Boolean readCaseDetails(String pathDirc,
			ApplConfig pathCfg,HashSet<String>caseList,
			HashSet<String>sceneList)
	{
	    Logger.getLogger(ApplConstValues.logName).
         entering("ApplCaseScriptImpl", "readCaseDetails");
		Iterator<String> b = sceneList.iterator();
	    String scene = new String();
	    
	    while(b.hasNext())
	    {
	    	scene = b.next();
	    	ApplCaseDetails caseSheet = new ApplCaseDetails();
/***xzguo
		 filePath 通过debug 为TestCases\综业平台ONLINE(ON)\NGTS_AM_AIR_D_ONXX_CV01.xls
***/
	    	String filePath=pathCfg.getCasePathDirc(scene)+ApplConstValues.prefix+scene+ApplConstValues.versions;
	    	File file = new File(filePath);
/***xzguo
		NGTS_AM_AIR_D_ONXX_CV01.xls 中用到的sheet 名为测试用例(ApplConstValues.sheetName)
		
***/
	    	if(!caseSheet.readCaseSheet(file, ApplConstValues.sheetName, 3, caseList,scene))  
				return false;
			
	    	this.caseScript.addAll(caseSheet.getCaseDetails());  /******xzguo 阶段总结
													 返回AIR_CYCLE.TXT 中每个CaseID在D_ONxx.xls所
													 在行的一行内容，
													全部放入caseScript数组中
													******/
/***xzguo
	caseDetailPath 的值为Output\AIR_ExecuteFile_result\ON05_detais.txt
***/		
	    	String caseDetailPath = pathCfg.getTestResultPath()+scene+ApplConstValues.postfix;
	    	this.outPutCaseDetails(caseSheet.getCaseDetails(),caseDetailPath);
	    }
	    if(this.caseScript.isEmpty())
	    {
	    	Logger.getLogger(ApplConstValues.logName).log(Level.SEVERE,
            "不存在用例或读取失败");
            Logger.getLogger(ApplConstValues.logName).
                log(Level.SEVERE, "执行手册生成失败 ");
            ApplExecuteResultDialog.viewError("执行失败，不存在用例或读取失败！！", "ERROR");
            return false;
	    }
/***xzguo
	deteTestEnvr(this.caseScript) 
***/
        if(!deteTestEnvr(this.caseScript))   		   
        {	
        	Logger.getLogger(ApplConstValues.logName).log(Level.SEVERE,
	    	               "用例环境不统一，出错！！");
        	Logger.getLogger(ApplConstValues.logName).
        	log(Level.SEVERE, "执行手册生成失败 ");
        	ApplExecuteResultDialog.viewError("执行失败，用例环境不统一，出错！！", "ERROR");
			return false;
        }
	    Collections.sort(caseScript,new ApplCaseScriptSort()); /*****对caseScript中的数组按照caseId排序****/
		return true;


	}
	
	 /*************************************************
	  * 函数功能：/********xzguo 把用例详情中的|用例编号|测试环境|主机|  testNecessDoc  
	  				   输出到Output\AIR_ExecuteFile_result\ONXX_detais.txt文件中
	  * 函数输入：
	  * ArrayList<ApplCase> in  －－circle.txt中所有对应D_ONXX.xls的一行记录
	  *     /*******xzguo filePath  - - ONXX_detais.txt的文件和路径， 举例:  Output\AIR_ExecuteFile_result\ON05_detais.txt
	  * 函数返回值：
	  *     空
	  ****************************************************/
	 public void outPutCaseDetails(ArrayList<ApplCase> in, String filePath)
	 {
		
	     Logger.getLogger(ApplConstValues.logName).
            entering("ApplCaseScriptImpl", "outPutCaseDetails");
	     File file = new File(filePath);
		 
		 try{
			 if (!file.exists())
				 file.createNewFile();
		     PrintWriter out = new PrintWriter(new FileWriter(filePath));
		     Iterator<ApplCase> iter = in.iterator();
		     ApplCase outPutString = new ApplCase();
		     int i = 0;
		     while(iter.hasNext())
		     {
		    	 outPutString = iter.next();
		    	 out.println(String.valueOf(i++)+"|"+outPutString.getCaseId()+"|"+outPutString.getTestEnvr()+
		    			 "|"+outPutString.getTesthost()+"|"+outPutString.getTestNecessDoc());
		     }  
		     out.close();
		 }
		 catch(IOException exception)
		 {
			 exception.printStackTrace();
		     Logger.getLogger(ApplConstValues.logName).log(Level.SEVERE,
	               "写文件{0}出错",file.getAbsolutePath());
	         //Logger.getLogger(ApplConstValues.logName).
		        // log(Level.SEVERE, "执行手册生成失败 ");
			//ApplExecuteResultDialog.viewError("执行失败，写文件"+filePath+"失败", "ERROR");
	       	   
		 }
	 }
	 
	 /**
	  * 函数功能：判断//***xzguo NGTS_AM_AIR_D_ONXX_CV01.xls 文件中*** "测试 环境" 是否一致
	  * 函数输入
	  * @param caseScript   －－用例详情文件
	  * 函数返回值
	  * @return  Boolean
	  */
	  /***xzguo
	  实际使用一律填C, 所以无需检查。该函数可以去掉
	  ***/
	 private Boolean deteTestEnvr(ArrayList<ApplCase> caseScript)
	 {
		 Logger.getLogger(ApplConstValues.logName).
	         entering("ApplCaseScriptImpl", "deteTestEnvr");
		 Iterator<ApplCase> c=caseScript.iterator();
		 HashSet<String> a= new HashSet<String>();
		 while(c.hasNext())
		 {
		    a.add(c.next().getTestEnvr().trim());
		 }
		 if(a.size()==1)
			 return true;
		 return false;
	 }
	 public ArrayList<ApplCase> getCaseScript() {
	     return caseScript;
	 }
}

