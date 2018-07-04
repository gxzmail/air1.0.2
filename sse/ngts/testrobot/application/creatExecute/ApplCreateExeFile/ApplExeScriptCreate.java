package sse.ngts.testrobot.application.creatExecute.ApplCreateExeFile;

import java.util.ArrayList;

import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
import sse.ngts.testrobot.exception.ApplFatalException;

/***from doc
	1．	类名称：ApplExeScriptCreate
	2．	类功能：创建ApplExeScriptCreate实例，获取脚本手册文件


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

		 函数 功能:  文件加载的实际实现
		 
	       实现方式: readScrip.stepsGet()函数实现
	
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
