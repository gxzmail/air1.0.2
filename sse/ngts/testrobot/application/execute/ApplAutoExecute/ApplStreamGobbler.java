package sse.ngts.testrobot.application.execute.ApplAutoExecute;

	import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;

public	class ApplStreamGobbler extends Thread 
{ 
	InputStream is; 
	String type; 
	//OutputStream os; 
    StringBuilder str = new StringBuilder();
    Boolean runflag = false;

	ApplStreamGobbler(InputStream is, String type) 
	{ 
		this.is = is; 
		this.type = type; 
		//this.os = redirect; 
	} 

	public void run() 
	{ 
		try 
		{ 
			/*
			PrintWriter pw = null; 
			if (os != null) 
				pw = new PrintWriter(os); 
            */
			InputStreamReader isr = new InputStreamReader(is); 
			BufferedReader br = new BufferedReader(isr); 
			String line=null; 
			while ( (line = br.readLine()) != null) 
			{ 
					if(-1 != line.indexOf(ApplExecuteConstValues.executeSuccess))
					{
						runflag = true;
	          
					}
					//pw.println(line);
					str.append(line+"\n");
				//}
				System.out.println(type + ">" + line); 
			} 
			//if (pw != null) 
			//	pw.flush(); 
			
		} catch (IOException ioe) 
		{ 
			ioe.printStackTrace(); 
		} 
	} 
	public Boolean getRunflag() {
		return runflag;
	}

	public StringBuilder getStr() {
		return str;
	}

} 

