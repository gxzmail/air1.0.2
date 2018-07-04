package sse.ngts.testrobot.engine.app;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.exception.ApplFatalException;

public class ApplEnvDete {
    private  static ApplEnvDete instance;
    public static ApplEnvDete getinstance()
    {
        if (instance == null)
        {
            instance = new ApplEnvDete();
        }
        return instance;
    }
   
	public  ApplEnvDete()
    {
		
        String fileDirc =  System.getProperty("user.dir");
    	
        File dir = new File("log");
        if(!dir.isDirectory()||!dir.exists())
        {
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.INFO, "{0}不存在",dir.getAbsolutePath());
        	
        	dir.mkdir();
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.INFO, "新建log 文件夹");

        }
        else
        {
        	Logger.getLogger(ApplConstValues.logName).
     	         log(Level.INFO, "{0}已经存在",dir.getAbsolutePath());
        	
        }
       
        ApplLogWrite.writeLog(fileDirc+"\\log\\"+ApplConstValues.logFileName,ApplConstValues.logName);
        File file = new File(fileDirc+"\\"+ApplConstValues.configFileName);
        try{
        	if(!file.exists())
            {
        	    Logger.getLogger(ApplConstValues.logName).
	                log(Level.SEVERE, "配置文件{0}不存在",file.getAbsolutePath());       	        	
			    ApplExecuteResultDialog.viewError("配置文件"+file.getAbsolutePath()+"不存在", "ERROR");
			    throw new ApplFatalException(null,null);
      	    }
        }
        catch(ApplFatalException ex)
        {
        	throw new ApplFatalException(null,null);
        }
	    ApplFileProcess.createDir(ApplConfig.getInstance().getTestResultPath(),ApplExecuteConstValues.APPL_DIRC );	    

    }
}
