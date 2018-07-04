package sse.ngts.testrobot.application.creatExecute.ApplCreateExeFile;

import java.util.ArrayList;

import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
import sse.ngts.testrobot.exception.ApplFatalException;

/***from doc
	1��	�����ƣ�ApplExeScriptCreate
	2��	�๦�ܣ�����ApplExeScriptCreateʵ������ȡ�ű��ֲ��ļ�


***/

public class ApplExeScriptCreate {
	
	private ArrayList<ApplFrmwkCase> steps= new ArrayList<ApplFrmwkCase>();
	private static ApplExeScriptCreate instance;
 	private static boolean hasCreat = false; 
	
	public static ApplExeScriptCreate getInstance(String filePath)
 	{
        if (instance == null||hasCreat ==false)
        {
            instance = new ApplExeScriptCreate();
        }

        return instance;
 	}
	public static boolean hasCreat()
 	{
        return hasCreat;
 	}
/***xzguo

		 ���� ����:  �ļ����ص�ʵ��ʵ��
		 
	       ʵ�ַ�ʽ: readScrip.stepsGet()����ʵ��
	
***/
	
	public ApplExeScriptCreate()
	{		
	    ApplReadScript readScrip = new ApplReadScript();	
	    if(!readScrip.stepsGet())
	    {
	    	instance = null;
    		hasCreat = false;
    		throw new ApplFatalException(null,null); 
	    }
	    steps = readScrip.getSteps();  
        hasCreat = true;
	}
	


	public ArrayList<ApplFrmwkCase> getSteps() {
		return steps;
	}
}
