package sse.ngts.testrobot.application.creatExecute.ApplCreateExeFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.creatExecute.ApplCaseDetails.ApplCaseScript;
import sse.ngts.testrobot.application.creatExecute.ApplCaseSteps.ApplCaseStepsScript;
import sse.ngts.testrobot.application.creatExecute.ApplCycle.ApplCycle;
import sse.ngts.testrobot.application.functions.ApplStepsSorts;
import sse.ngts.testrobot.application.functions.ApplXmlParse;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
import sse.ngts.testrobot.factory.DAOFactory;

public class ApplReadScript {
	private ArrayList<ApplFrmwkCase> steps;

    public ApplReadScript()
    {
    }
	
	public Boolean stepsGet()
	{
    	/***********************************
    	 * 读取配置文件
    	***********************************/
 		ApplConfig A = ApplConfig.getInstance();

    	/****************************************************
    	 * 获取场景列表与用例列表数组，输出场景列表和用例列表文件
    	 * **************************************************/
    	ApplCycle B = DAOFactory.getCycleInstance();
	/***xzguo
		A.getTestFile通过debug为testCase/circle.txt
	***/
    	if(!B.readCycleFile(A.getTestFile()))
    		return false;
	/***xzguo
		A.getCaseListFile 通过debug为 Output\AIR_ExecuteFile_result\Case_File.txt, 
		内容举例如下:
		0|IF01_001_001
		1|IF01_001_002
		2|RT01_001_001

		A.getSceneListFile  通过debug为Output\AIR_ExecuteFile_result\Scene_File.txt
		内容举例如下:
		0|IF01
		1|IF02
		
	***/
    	B.outPutCaseListFile(A.getCaseListFile());
    	B.outPutSceneListFile(A.getSceneListFile());
    	
    	/******************************************************
    	* 根据用例列表与场景列表，获取用例详情,并把详情输出到文件中
    	******************************************************/
    	
    	ApplCaseScript caseScrpit = DAOFactory.getApplCaseScriptInstance();
    	ApplCaseStepsScript caseSteps = DAOFactory.getApplCaseStepsInstance();

	    if(!caseScrpit.readCaseDetails(A.getCasePath(), A, B.getCaseList(), B.getSceneList()))
	    	return false;

	    caseScrpit.outPutCaseDetails(caseScrpit.getCaseScript(),
			                     A.getTestCaseScript());
    
	    
	 	/******************************************************
    	* 读取配置文件"cfg\\air_configs.txt"
    	******************************************************/ 
    	ApplXmlParse airConfig = ApplXmlParse.getinstance();
    	if(!airConfig.readxml())
    	{
        	ApplExecuteResultDialog.viewError("读取配置文件\"cfg\\air_configs.txt\"出错！","failed" );	
    	    return false;
    	}                      

		/***xzguo

				总结: 从     	ApplCaseScript caseScrpit = DAOFactory.getApplCaseScriptInstance(); 
				该行开始到
				ApplExecuteResultDialog.viewError("读取配置文件\"cfg\\air_configs.txt\"出错！","failed" );	
				return false;
				结束，完成将circle.txt中的记录从D_ONXX中取出，  ???  放入caseScript数组中???
				
				             
				
    	****/
		
	   /***************************************
       * 获取用例步骤,并把所有的步骤输出到文件中
       * *************************************/
	   if(!caseSteps.readCaseSheet(caseScrpit.getCaseScript()))  	  	
	   {
		   ApplExecuteResultDialog.viewError("手册生成失败", "error");
		   return false;
	   }
	   this.steps = caseSteps.getCaseSteps();   	

    	/**********************************************************
         * 把所有的步骤按照交易日、交易阶段、优先级排序，并输出到文件中
         * ********************************************************/
    	Collections.sort(this.steps,new ApplStepsSorts(caseSteps.getTradePhase()));    	
    	ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.excuteSheetName,
    			                        A.getExecuteFile(),
    			                        this.steps);
    	Logger.getLogger(ApplConstValues.logName).
 	             log(Level.INFO, "程序执行完成！！");
 	   

    	ApplExecuteResultDialog.viewSuccess("手册生成成功", "success");
	    return true;
	}
	

	
	


	public ArrayList<ApplFrmwkCase> getSteps() {
		return steps;
	}
	


}
