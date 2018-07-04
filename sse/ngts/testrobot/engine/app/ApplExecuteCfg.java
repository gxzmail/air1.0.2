package sse.ngts.testrobot.engine.app;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;

public class ApplExecuteCfg {
	private static ApplExecuteCfg instance;
   
	public static ApplExecuteCfg getInstance()
    {
        if (instance == null)
        {
            instance = new ApplExecuteCfg();
        }
        return instance;
    }
   /**
    * 环境配置，建立log文件夹
    */
	public  ApplExecuteCfg()
    {
       
		String fileDirc =  System.getProperty("user.dir");
	
        File dir = new File("log");
        if(!dir.isDirectory()||!dir.exists())
        {
        	Logger.getLogger(ApplExecuteConstValues.logExecutName).
	         log(Level.INFO, "{0}不存在",dir.getAbsolutePath());
        	
        	dir.mkdir();
        	Logger.getLogger(ApplExecuteConstValues.logExecutName).
	         log(Level.INFO, "新建log 文件夹");

        }
        else
        {
        	Logger.getLogger(ApplExecuteConstValues.logExecutName).
     	         log(Level.INFO, "{0}已经存在",dir.getAbsolutePath());
        	
        }
        ApplLogWrite.writeLog(fileDirc+"\\log\\"+ApplExecuteConstValues.logExecuteFileName,ApplExecuteConstValues.logExecutName);       
        
        File exeResult = new File(ApplConfig.getInstance().getExectueOutPutResult());
        if(exeResult.exists()&&exeResult.isFile())
        	exeResult.delete();
        
    }
	

}
