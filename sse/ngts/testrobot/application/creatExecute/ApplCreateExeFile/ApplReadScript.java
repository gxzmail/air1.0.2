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
    	 * ��ȡ�����ļ�
    	***********************************/
 		ApplConfig A = ApplConfig.getInstance();

    	/****************************************************
    	 * ��ȡ�����б��������б����飬��������б�������б��ļ�
    	 * **************************************************/
    	ApplCycle B = DAOFactory.getCycleInstance();
	/***xzguo
		A.getTestFileͨ��debugΪtestCase/circle.txt
	***/
    	if(!B.readCycleFile(A.getTestFile()))
    		return false;
	/***xzguo
		A.getCaseListFile ͨ��debugΪ Output\AIR_ExecuteFile_result\Case_File.txt, 
		���ݾ�������:
		0|IF01_001_001
		1|IF01_001_002
		2|RT01_001_001

		A.getSceneListFile  ͨ��debugΪOutput\AIR_ExecuteFile_result\Scene_File.txt
		���ݾ�������:
		0|IF01
		1|IF02
		
	***/
    	B.outPutCaseListFile(A.getCaseListFile());
    	B.outPutSceneListFile(A.getSceneListFile());
    	
    	/******************************************************
    	* ���������б��볡���б���ȡ��������,��������������ļ���
    	******************************************************/
    	
    	ApplCaseScript caseScrpit = DAOFactory.getApplCaseScriptInstance();
    	ApplCaseStepsScript caseSteps = DAOFactory.getApplCaseStepsInstance();

	    if(!caseScrpit.readCaseDetails(A.getCasePath(), A, B.getCaseList(), B.getSceneList()))
	    	return false;

	    caseScrpit.outPutCaseDetails(caseScrpit.getCaseScript(),
			                     A.getTestCaseScript());
    
	    
	 	/******************************************************
    	* ��ȡ�����ļ�"cfg\\air_configs.txt"
    	******************************************************/ 
    	ApplXmlParse airConfig = ApplXmlParse.getinstance();
    	if(!airConfig.readxml())
    	{
        	ApplExecuteResultDialog.viewError("��ȡ�����ļ�\"cfg\\air_configs.txt\"����","failed" );	
    	    return false;
    	}                      

		/***xzguo

				�ܽ�: ��     	ApplCaseScript caseScrpit = DAOFactory.getApplCaseScriptInstance(); 
				���п�ʼ��
				ApplExecuteResultDialog.viewError("��ȡ�����ļ�\"cfg\\air_configs.txt\"����","failed" );	
				return false;
				��������ɽ�circle.txt�еļ�¼��D_ONXX��ȡ����  ???  ����caseScript������???
				
				             
				
    	****/
		
	   /***************************************
       * ��ȡ��������,�������еĲ���������ļ���
       * *************************************/
	   if(!caseSteps.readCaseSheet(caseScrpit.getCaseScript()))  	  	
	   {
		   ApplExecuteResultDialog.viewError("�ֲ�����ʧ��", "error");
		   return false;
	   }
	   this.steps = caseSteps.getCaseSteps();   	

    	/**********************************************************
         * �����еĲ��谴�ս����ա����׽׶Ρ����ȼ����򣬲�������ļ���
         * ********************************************************/
    	Collections.sort(this.steps,new ApplStepsSorts(caseSteps.getTradePhase()));    	
    	ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.excuteSheetName,
    			                        A.getExecuteFile(),
    			                        this.steps);
    	Logger.getLogger(ApplConstValues.logName).
 	             log(Level.INFO, "����ִ����ɣ���");
 	   

    	ApplExecuteResultDialog.viewSuccess("�ֲ����ɳɹ�", "success");
	    return true;
	}
	

	
	


	public ArrayList<ApplFrmwkCase> getSteps() {
		return steps;
	}
	


}
