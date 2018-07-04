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
	1��	�����ƣ�ApplCycle�๦�ܣ���ȡAIR_CYCLE.TXT�ļ� ��
	                                   ������ļ���"�����б�"��"�����б�"������ļ�

***/


public class ApplCycleImpl implements ApplCycle
{
	 private String cfgFileName ;/*AIR_CYCLE.TXT�ļ�·��*/

	 private HashSet<String> caseList = new HashSet<String>();;
	 private HashSet<String> sceneList= new HashSet<String>();

	 
    /**
     * �������ܣ���ȡ�����б��������б�����
     * �������룺
     * filePath  ����      AIR_CYCLE.TXT�ļ�·��
     * ��������ֵ��
     * ��
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
	                    log(Level.INFO, "caseListΪ�գ���鿴�����ļ�");
	            	 System.exit(0);
	             }
	  		     Logger.getLogger(ApplConstValues.logName).
                    log(Level.INFO, "���ļ�{0}�ɹ�",file.getName());
	             return true;
	         }
	         catch (IOException e) {
	             e.printStackTrace();
	  		     Logger.getLogger(ApplConstValues.logName).
                   log(Level.SEVERE, "���ļ�{0}ʧ�ܣ�IO��д����",file.getAbsolutePath());
				 ApplExecuteResultDialog.viewError("ִ��ʧ��,���ļ�"+file.getAbsolutePath()+"ʧ��", "ERROR");
	             return false;
	         } 
	         catch(Exception e)
	         {
	        	 Logger.getLogger(ApplConstValues.logName).        	 	        	 
                  log(Level.SEVERE, "���ļ�{0}ʧ��",file.getName());
				 ApplExecuteResultDialog.viewError("ִ��ʧ��,���ļ�"+file.getAbsolutePath()+"ʧ��", "ERROR");
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
	    	   log(Level.SEVERE, "�ļ�{0}������",file.getName());
	        
	         Logger.getLogger(ApplConstValues.logName).
		         log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
			 ApplExecuteResultDialog.viewError("ִ��ʧ��,�ļ�"+file.getAbsolutePath()+"������", "ERROR");
             return false;

	     }

	 }
	 /**
	  * �������ܣ���������б��ļ�
	  * ���������
	  * filePath    ���������б��ļ�·��
	  */
	 public void outPutCaseListFile(String filePath)
	 {
		 ApplFileProcess.fileOutPut(this.caseList,filePath);
	 }
	 
	 /**
	  * �������ܣ���������б��ļ�
	  * ���������
	  * filePath    ���������б��ļ�·��
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