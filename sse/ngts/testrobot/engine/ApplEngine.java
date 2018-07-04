package sse.ngts.testrobot.engine;

import sse.ngts.testrobot.application.management.ViewController;
import sse.ngts.testrobot.engine.app.ApplEnvDete;
import sse.ngts.testrobot.engine.app.ApplExecuteCfg;
import sse.ngts.testrobot.exception.ApplFatalException;

public class ApplEngine {
	private static ApplEngine instance;
	
    public static ApplEngine getinstance()
    {
        if (instance == null)
        {
            instance = new ApplEngine();
        }
        return instance;
    }
	/**
	 * ��ȡִ���ļ���ִ���ļ���ÿ�У�������ִ�н��дͳ���ļ�
	 */
    public ApplEngine()
	{
    	try{
    		ApplEnvDete.getinstance();
    		ApplExecuteCfg.getInstance();		
    		ViewController.getInstance();
    	}
    	catch(ApplFatalException ex)
    	{
    		System.exit(0);
    		return;
    	}
    	
       
	}

}
