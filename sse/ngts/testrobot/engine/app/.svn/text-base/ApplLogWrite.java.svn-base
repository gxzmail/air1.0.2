package sse.ngts.testrobot.engine.app;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import sse.ngts.testrobot.engine.unit.ApplConstValues;

public class ApplLogWrite {
	
	public static void writeScreenLog(String logFileName)
	{
		try
		{
			Logger.getLogger("").setLevel(Level.INFO);
			Handler handler = new FileHandler(logFileName,false);
			handler.setFormatter(new SimpleFormatter());
			Logger.getLogger("").addHandler(handler);
			
			
		}
		catch (SecurityException e)
		{
			e.printStackTrace();	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		    Logger.getLogger(ApplConstValues.logName).
            log(Level.SEVERE,"cann't create log handler");	
		}
	}
	
	public static void writeLog(String logFileName,String logExecutName)
	{
		try
		{
			Logger.getLogger("").setLevel(Level.INFO);
			Handler handler = new FileHandler(logFileName,false);
			handler.setFormatter(new SimpleFormatter());
			
			Logger.getLogger(logExecutName).addHandler(handler);
		}
		catch (SecurityException e)
		{
			e.printStackTrace();	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		    Logger.getLogger(logExecutName).
                  log(Level.SEVERE,"cann't create log handler");	
		}
	   
	}
}
