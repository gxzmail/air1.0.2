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
1��	�����ƣ�ApplCaseScript, ApplCaseScriptImpl  Ϊ �ӿ� ApplCaseScript�ľ���ʵ��
2��	�๦�ܣ�����"�����б�"���飬�õ��������б��"��������"����  
                                    ���ж����е������Ĳ��Ի����Ƿ�һ�£������һ�³��� 
                                    �������жϡ�

***/


public class ApplCaseScriptImpl implements ApplCaseScript{
	
	private ArrayList<ApplCase> caseScript= new ArrayList<ApplCase>();
	
    /**************************************************
     * �������ܣ����������б�������б��ȡ�����б���������
     * �������룺
     * String pathDirc          �������������ļ���
     * Hashtable pathCfg        ���������ļ�����·��
     * HashSet<String>caseList  ���������б�
     * HashSet<String>sceneList ���������б�
     * ��������ֵ��
     *      ��
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
		 filePath ͨ��debug ΪTestCases\��ҵƽ̨ONLINE(ON)\NGTS_AM_AIR_D_ONXX_CV01.xls
***/
	    	String filePath=pathCfg.getCasePathDirc(scene)+ApplConstValues.prefix+scene+ApplConstValues.versions;
	    	File file = new File(filePath);
/***xzguo
		NGTS_AM_AIR_D_ONXX_CV01.xls ���õ���sheet ��Ϊ��������(ApplConstValues.sheetName)
		
***/
	    	if(!caseSheet.readCaseSheet(file, ApplConstValues.sheetName, 3, caseList,scene))  
				return false;
			
	    	this.caseScript.addAll(caseSheet.getCaseDetails());  /******xzguo �׶��ܽ�
													 ����AIR_CYCLE.TXT ��ÿ��CaseID��D_ONxx.xls��
													 ���е�һ�����ݣ�
													ȫ������caseScript������
													******/
/***xzguo
	caseDetailPath ��ֵΪOutput\AIR_ExecuteFile_result\ON05_detais.txt
***/		
	    	String caseDetailPath = pathCfg.getTestResultPath()+scene+ApplConstValues.postfix;
	    	this.outPutCaseDetails(caseSheet.getCaseDetails(),caseDetailPath);
	    }
	    if(this.caseScript.isEmpty())
	    {
	    	Logger.getLogger(ApplConstValues.logName).log(Level.SEVERE,
            "�������������ȡʧ��");
            Logger.getLogger(ApplConstValues.logName).
                log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
            ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ��������������ȡʧ�ܣ���", "ERROR");
            return false;
	    }
/***xzguo
	deteTestEnvr(this.caseScript) 
***/
        if(!deteTestEnvr(this.caseScript))   		   
        {	
        	Logger.getLogger(ApplConstValues.logName).log(Level.SEVERE,
	    	               "����������ͳһ��������");
        	Logger.getLogger(ApplConstValues.logName).
        	log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
        	ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ�����������ͳһ��������", "ERROR");
			return false;
        }
	    Collections.sort(caseScript,new ApplCaseScriptSort()); /*****��caseScript�е����鰴��caseId����****/
		return true;


	}
	
	 /*************************************************
	  * �������ܣ�/********xzguo �����������е�|�������|���Ի���|����|  testNecessDoc  
	  				   �����Output\AIR_ExecuteFile_result\ONXX_detais.txt�ļ���
	  * �������룺
	  * ArrayList<ApplCase> in  ����circle.txt�����ж�ӦD_ONXX.xls��һ�м�¼
	  *     /*******xzguo filePath  - - ONXX_detais.txt���ļ���·���� ����:  Output\AIR_ExecuteFile_result\ON05_detais.txt
	  * ��������ֵ��
	  *     ��
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
	               "д�ļ�{0}����",file.getAbsolutePath());
	         //Logger.getLogger(ApplConstValues.logName).
		        // log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
			//ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ�д�ļ�"+filePath+"ʧ��", "ERROR");
	       	   
		 }
	 }
	 
	 /**
	  * �������ܣ��ж�//***xzguo NGTS_AM_AIR_D_ONXX_CV01.xls �ļ���*** "���� ����" �Ƿ�һ��
	  * ��������
	  * @param caseScript   �������������ļ�
	  * ��������ֵ
	  * @return  Boolean
	  */
	  /***xzguo
	  ʵ��ʹ��һ����C, ���������顣�ú�������ȥ��
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

