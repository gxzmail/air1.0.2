package sse.ngts.testrobot.application.creatExecute.ApplCaseSteps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.creatExecute.ApplCreateExeFile.ApplExeSheetWrite;
import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.application.functions.ApplXmlParse;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplCase;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
/***from doc 
	类功能：
	根据"用例详情"数组，得到该数组对应的所有用例的"步骤列表"数组

***/
public class ApplCaseStepsScriptImpl implements ApplCaseStepsScript{
	
    private ArrayList<ApplFrmwkCase> caseSteps = new ArrayList<ApplFrmwkCase>();  
	private ArrayList<ApplFrmwkCase> frmWorkSteps= new ArrayList<ApplFrmwkCase>();
    private HashSet<String> tradeDate = new HashSet<String>();


	private Hashtable<String,Integer> tradePhase = new Hashtable<String,Integer>(); 
	private ApplXmlParse  xmlParse = ApplXmlParse.getinstance();		

	/***from doc
		函数功能：根据"用例详情"数组，读取用例脚本文件（文件格式见4.10.6），
						生成"用例步骤"数组，读取框架文件，生成框架步骤数组。

	***/
 
	public  Boolean readCaseSheet(ArrayList<ApplCase>caseList)
    {
    		if(!readCaseListSteps(caseList))
				return false;
			ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.OutPutStepsSheetName,
    			ApplConfig.getInstance().getTestCaseStepsScriptPath(),
                caseSteps);
			if(!readFrmSheet(ApplConfig.getInstance().getFrmWorkPath(), ApplConfig.getInstance().getFrmId()))
				return false;
			ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.OutPutStepsSheetName,
    			ApplConfig.getInstance().getFrmStepPath(),
    			frmWorkSteps);
						
		/****************************************************
		 * 根据配置文件更新步骤
		 ****************************************************/
			if(!xmlParse.readxml())
			{
				Logger.getLogger(ApplConstValues.logName).
     	        log(Level.SEVERE,"打开配置文件\"cfg\\AIR_Config.xml\"出错");
				return false;
			}
			replaceCaseSteps(caseSteps);
			replaceFrameSteps(frmWorkSteps);					
    	/*****************************************************
         * 查看框架步骤中的所有步骤的优先级是否高于脚本中的所有步骤的
         * 优先级
         ******************************************************/
    	if(!comparePriority(caseSteps,frmWorkSteps))
    	{
    		Logger.getLogger(ApplConstValues.logName).
     	        log(Level.SEVERE, "程序执行失败，框架中步骤的优先级低于用例脚本中步骤的优先级！！");
     	   
     	    return false;
     	    
    	}
    	caseSteps.addAll(frmWorkSteps);
    	return true;
    }
	
	private void replaceFrameSteps(ArrayList<ApplFrmwkCase> steps)
	{
		Iterator<ApplFrmwkCase> iter = steps.iterator();
		while(iter.hasNext())
		{
			ApplFrmwkCase elem = iter.next();
			String s = null;
			for(int i = 1;i< 6;i++)
			{
				if(elem.getTestDate().trim().equals("T"+i))
				{
					for(int j = 6;j>= 0;j--)
					{
						int k = j+i;
						s = elem.getTestContent().replaceAll("%T"+j+"%","%T"+k+"%");
						elem.setTestContent(s);
					}
				}
			}

		}	
		replaceCaseSteps(steps);
	}
	
	private void replaceCaseSteps(ArrayList<ApplFrmwkCase> steps)
	{		
		Iterator<ApplFrmwkCase> iter = steps.iterator();
		Hashtable<String,String> xmlP = new Hashtable<String,String>();
		xmlP = xmlParse.getXmlParse();
		while(iter.hasNext())
		{
			ApplFrmwkCase elem = iter.next();
			String s = null;
			
            for(String key:xmlP.keySet())
			{
				if(elem.getTestContent().indexOf(key)!= -1)
				{
					s = elem.getTestContent().replaceAll("%"+key+"%", (String)xmlP.get(key));
					elem.setTestContent(s);
				}
			}
			
			
		}
	}
	
	private Boolean comparePriority(ArrayList<ApplFrmwkCase> steps1,ArrayList<ApplFrmwkCase> steps2)
	{
		String str1 = getMaxPriority(steps1);
		String str2 = getMaxPriority(steps2);
		if(str1.compareToIgnoreCase(str2)>0)
			return true;
		else
			return false;
	}
	
	private String getMaxPriority(ArrayList<ApplFrmwkCase> steps)
	{
		Iterator<ApplFrmwkCase> c = steps.iterator();
		String testPrior = null;
		String maxPrior = null;
		while(c.hasNext())
		{
			testPrior = c.next().getTestPrior();
		
			if(maxPrior == null)
				maxPrior = testPrior;
			else if(maxPrior.compareToIgnoreCase(testPrior)>0) 
				maxPrior = testPrior;
		}
		return maxPrior;
	}
    /*********************************************
    * 函数功能：获取用例列表中所有的步骤
    * 函数输入：
    * String path                   －－文件路径
    * ArrayList<ApplCase>caseList   －－用例列表
    * Hashtable pathCfg             －－用例文件配置路径
    * 函数输出：
    *     空
    * 函数返回值：
    *    空
    *********************************************/
	public Boolean readCaseListSteps(ArrayList<ApplCase>caseList)
    {
    	
		Logger.getLogger(ApplConstValues.logName).
             entering("ApplCaseStepsScriptImpl", "readCaseListSteps");
		Iterator<ApplCase> c = caseList.iterator();
    	
        String pathDirc = null;
        String filePath = null;
    	while(c.hasNext())
    	{
    		ApplCase caseDescrip = new ApplCase();
    		caseDescrip = c.next();
    		String sceneId = null;
    		
    		pathDirc = ApplConfig.getInstance().getCasePathDirc(caseDescrip.getCaseId());
    		/*场景ID*/
    		sceneId = ApplFileProcess.getStringByToken(1, "_",caseDescrip.getCaseId());
    		/***xzguo
    		 	filePath 值 为 TestCases\综业平台ONLINE(ON)\脚本\ON05\NGTS_AM_AIR_ON05_001_001_CV01.xls
    		 */
    		/*用例脚本的路径*/
    		filePath = pathDirc+ApplConstValues.stepDircPathName+"\\"+sceneId+"\\"+ApplConstValues.stepPrefix+
    		                  caseDescrip.getCaseId()+ApplConstValues.stepVersions;
    		try{
    			File file = new File(filePath);
    			ApplCaseSteps step = new ApplCaseSteps();
    		/***xzguo
    		 *  
    		 */
    			if(!step.readCaseStep(ApplConstValues.stepSheetName, file, 3, caseDescrip))
    				return false;
    		    /*每个用例文件的步骤文件路径*/
    		    String caseStepsTxtPath = ApplConfig.getInstance().getTestResultPath()+
	    		                    caseDescrip.getCaseId()+ApplConstValues.stepPostfix;
    		    ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.OutPutStepsSheetName,caseStepsTxtPath,step.getSteps());
    		    caseSteps.addAll(step.getSteps());	
    		}
    		catch(Exception e){
    			Logger.getLogger(ApplConstValues.logName).
                    log(Level.SEVERE, "读文件{0}失败",filePath);	
    			
    			Logger.getLogger(ApplConstValues.logName).
                    log(Level.SEVERE, "执行手册生成失败 ");
				ApplExecuteResultDialog.viewError("执行失败,读取文件"+filePath+"失败", "ERROR");   			
				return false;
    		}


    	} 
    	setTradeDate(this.caseSteps);
		return true;

    	
    }
    
	
	public Boolean readFrmSheet(String frmWorkPath,String frameWorkId)
	{
		Logger.getLogger(ApplConstValues.logName).
             entering("ApplCaseFramworkScript", "initApplFrmStep");
		try{
			
			File file = new File(frmWorkPath);
			ApplCase a = new ApplCase();
			a.setCaseId(frameWorkId);
			
			ApplCaseSteps frmSteps = new ApplCaseSteps();		
			if(!frmSteps.readCaseStep(ApplConstValues.caseSheetName, file, 3,a))
				return false;
			setTradePhase(frmSteps.getSteps());
			Iterator<String> c = tradeDate.iterator();
			int i = 0;
			int j = 0;
			String date;
			Iterator<ApplFrmwkCase> b = frmSteps.getSteps().iterator();
			while(b.hasNext())
			{ 
				b.next();
				j++;
			}
			while(c.hasNext())
			{
				date = c.next().toString();
				this.frmWorkSteps.addAll(chgSteps(frmSteps.getSteps(),date));
				i++;
			}
		}
		catch(Exception e)
		{
			Logger.getLogger(ApplConstValues.logName).
              log(Level.SEVERE, "读文件{0}失败",frmWorkPath);	
			ApplExecuteResultDialog.viewError("执行失败，读文件"+frmWorkPath+"失败", "ERROR");
			return false;
		}
		return true;	
	}
	
	/*
	 *函数功能：更改步骤中的交易日
	 *函数输入：
	 *ArrayList<ApplFrmwkCase> frmWorkSteps   －－步骤数组
	 *String tradeDate                        －－ 交易日
	 *函数返回类型：
	 *   ArrayList<ApplFrmwkCase>
	 */
	private ArrayList<ApplFrmwkCase> chgSteps(ArrayList<ApplFrmwkCase> frmWorkSteps,String tradeDate)
	{
		
		
		ArrayList<ApplFrmwkCase> fSteps = new ArrayList<ApplFrmwkCase>();
		Iterator<ApplFrmwkCase> c = frmWorkSteps.iterator();
	  	while(c.hasNext())
	  	{
	  		
	  		ApplFrmwkCase d = new ApplFrmwkCase();
	  		d = c.next().clone();
	  		d.setTestDate(tradeDate);
	  		fSteps.add(d);
	  	}
	  	return fSteps;
	}
	
    /***********************************************
     * 函数功能：根据用例步骤数组获取交易日
     * 函数输入：
     * @param Steps     －－交易日数组
     * 函数返回值：
     * HashSet<String>  －－交易日数组
     *************************************************/
	private void setTradeDate(ArrayList<ApplFrmwkCase> Steps)
    {
    	Iterator<ApplFrmwkCase> c = Steps.iterator();
    	
        while(c.hasNext())
        {
        	this.tradeDate.add(c.next().getTestDate());
        }
    }

	public void setTradePhase(ArrayList<ApplFrmwkCase> e) {
		Iterator<ApplFrmwkCase> c = e.iterator();
		String str = new String();
		ArrayList<String> str3 = new ArrayList<String>();
	   
		int i = 0;
		while(c.hasNext())
		{
			str = c.next().getTestPhase().trim();
			if(str3.contains((String)str))
				continue;
			else
			{   
				str3.add(str);
				tradePhase.put(str,(Integer)i);
				i++;
			}
		}
	}
    
	public ArrayList<ApplFrmwkCase> getCaseSteps() {
			return caseSteps;
	}

	public Hashtable<String, Integer> getTradePhase() {
		return tradePhase;
	}
	


}
