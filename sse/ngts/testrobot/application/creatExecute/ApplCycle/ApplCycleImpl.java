package sse.ngts.testrobot.application.creatExecute.ApplCycle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;

/***from doc
	1．	类名称：ApplCycle类功能：读取AIR_CYCLE.TXT文件 ，
	                                   输出该文件的"用例列表"和"场景列表"数组或文件

***/


public class ApplCycleImpl implements ApplCycle
{
	 private String cfgFileName ;/*AIR_CYCLE.TXT文件路径*/

	 private HashSet<String> caseList = new HashSet<String>();;
	 private HashSet<String> sceneList= new HashSet<String>();

	 
    /**
     * 函数功能：获取场景列表和用例列表数组
     * 函数输入：
     * filePath  －－      AIR_CYCLE.TXT文件路径
     * 函数返回值：
     * 空
     */
	 public boolean readCycleFile(String filePath)
	 {
		 Logger.getLogger(ApplConstValues.logName).
           entering("ApplCycleImpl", "loadFile");
		 this.cfgFileName = filePath;

		 File file = new File(cfgFileName);

	     if (file.exists())
	     {
	    	 BufferedReader reader = null;
	         try{
	        	 reader = new BufferedReader(new FileReader(file));
	             String tempString = null;
	             String caseId = null;
	             String sceneId = null;
	             while ((tempString = reader.readLine())!=null) 
	             {
	            	 /*2012-06-27 modified by wuli start */
	            	 if(tempString.isEmpty()||!tempString.contains("|"))
	                /*2012-06-27 modified by wuli end */
	            		 continue; 
	            	 /*2012-4-18 add by wuli start*/
	            	 if(tempString.trim().startsWith("#"))
	            	     continue;
	            	 /*2012-4-18 add by wuli end*/
	            	 caseId = ApplFileProcess.getStringByToken(1,"|",tempString);
	            	 sceneId = ApplFileProcess.getStringByToken(1,"_",caseId);
	            	 if(caseId == null ||sceneId == null)
	            		 continue;

	            	 caseList.add(caseId);
	            	 sceneList.add(sceneId);
	             }     
	             if(caseList.isEmpty())
	             {
	            	 Logger.getLogger(ApplConstValues.logName).
	                    log(Level.INFO, "caseList为空，请查看测试文件");
	            	 System.exit(0);
	             }
	  		     Logger.getLogger(ApplConstValues.logName).
                    log(Level.INFO, "读文件{0}成功",file.getName());
	             return true;
	         }
	         catch (IOException e) {
	             e.printStackTrace();
	  		     Logger.getLogger(ApplConstValues.logName).
                   log(Level.SEVERE, "读文件{0}失败，IO读写错误",file.getAbsolutePath());
				 ApplExecuteResultDialog.viewError("执行失败,读文件"+file.getAbsolutePath()+"失败", "ERROR");
	             return false;
	         } 
	         catch(Exception e)
	         {
	        	 Logger.getLogger(ApplConstValues.logName).        	 	        	 
                  log(Level.SEVERE, "读文件{0}失败",file.getName());
				 ApplExecuteResultDialog.viewError("执行失败,读文件"+file.getAbsolutePath()+"失败", "ERROR");
	             return false;

	         }
	         finally {
	             if (reader != null) {
	                 try {
	                     reader.close();
	                 } catch (IOException e1) {
	                 }
	             }
	         }

	     }
	     else
	     {
	    	 Logger.getLogger(ApplConstValues.logName). 
	    	   log(Level.SEVERE, "文件{0}不存在",file.getName());
	        
	         Logger.getLogger(ApplConstValues.logName).
		         log(Level.SEVERE, "执行手册生成失败 ");
			 ApplExecuteResultDialog.viewError("执行失败,文件"+file.getAbsolutePath()+"不存在", "ERROR");
             return false;

	     }

	 }
	 /**
	  * 函数功能：输出用例列表文件
	  * 函数输出：
	  * filePath    －－用例列表文件路径
	  */
	 public void outPutCaseListFile(String filePath)
	 {
		 ApplFileProcess.fileOutPut(this.caseList,filePath);
	 }
	 
	 /**
	  * 函数功能：输出场景列表文件
	  * 函数输出：
	  * filePath    －－场景列表文件路径
	  */
	 public void outPutSceneListFile(String filePath)
	 {
		 ApplFileProcess.fileOutPut(this.sceneList,filePath);
	 }
     
	 public HashSet<String> getCaseList() {
		return caseList;
	 }

	 public HashSet<String> getSceneList() {
		return sceneList;
	 }


}
