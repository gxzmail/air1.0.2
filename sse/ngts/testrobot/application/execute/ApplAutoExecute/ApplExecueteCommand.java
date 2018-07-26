/*
 *  Created on: 2017-5-26
 *      Author: xzguo
 *  Time		SIR MAKR    		DESCRIPTION
 * 2017-05-26	sir 1		modify the air turns to be red although the  expect is the same with response
 *
 */




package sse.ngts.testrobot.application.execute.ApplAutoExecute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
public class ApplExecueteCommand {

	private String failedCause= new String();
	private Process pro;


/**
 * 调用command执行步骤
 * @param stepExecute  －－要执行的内容
 */
	public Boolean  execueteCommand(String stepExecute)  
    {
		failedCause = "";
		System.out.println("************************");
		//System.out.println("Result:");
		Object[] cmdarray = changstr(stepExecute).toArray();
		String[] strarr = new String[cmdarray.length];
		Boolean stepSuccess = false;
		for(int i =0;i<strarr.length;i++){
			strarr[i] = (String)cmdarray[i];
			//System.out.println(strarr[i]);

		}
		try
		{      
			/*
			File file = new File("temp.txt");
	        if(file.exists()){
	        	file.delete();
	            file.createNewFile();         
	        }
            FileOutputStream fos = new FileOutputStream("temp.txt"); 
            */
			Process pro = Runtime.getRuntime().exec(strarr);
			ApplStreamGobbler outputGobbler = new 
			ApplStreamGobbler(pro.getInputStream(), "OUTPUT"); 
			outputGobbler.start(); 
			int exitVal = pro.waitFor(); 
			System.out.println("ExitValue: " + exitVal);			
/*sir 1 begin*/
			outputGobbler.join(); //***xzguo 直接从air1.0.5迁移过来，没有测试和逻辑核对。应该没有问题
/*sir 1 end*/
			int count=3;
            while(exitVal==13&& count>0)
            {
    			pro = Runtime.getRuntime().exec(strarr);
    			outputGobbler = new 
    			ApplStreamGobbler(pro.getInputStream(), "OUTPUT"); 
    			outputGobbler.start(); 
    			exitVal = pro.waitFor(); 
    			System.out.println("ExitValue: " + exitVal); 
    			count--;
            }
			stepSuccess = outputGobbler.getRunflag();
			failedCause = outputGobbler.getStr().toString();       
			pro.destroy();
		} catch (IOException ex) {
			Logger.getLogger(ApplExecuteConstValues.logExecutName).
               log(Level.WARNING, "command执行失败，原因{0}",ex.getMessage());
			return stepSuccess;
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		    e.printStackTrace();
	        return stepSuccess;
	} 
     return stepSuccess;
  }

	public String getFailedCause() {
		return failedCause;
	}
	
	public static ArrayList<String>  changstr(String str)
	{
	    ArrayList<String> strarray  = new ArrayList<String>();
	 	StringTokenizer t = new StringTokenizer(str);
        int count = 0;
        String[] arr = new String[t.countTokens()];
        while(t.hasMoreTokens()){
        	arr[count++] = t.nextToken();
        	
        }
        int i=0,j=0;
        boolean iflag = false,jflag= false;
        for(count =0;count<arr.length;count++)
        {
        	if(arr[count].indexOf("\"")==0&&!iflag)
        	{
        		i = str.indexOf("\"",j);
        		if(i+1<str.length())j=i+1;
        		iflag = true;
        		if(arr[count].length()>1){
        			int k = str.indexOf("\"",j);
        			if(k-i+1 == arr[count].length()){
        				jflag = true;
        				j=k;
        			}
        		}
        	}    	
        	else if((arr[count].indexOf("\"")==arr[count].length()-1)&&iflag )
        	{
        		j = str.indexOf("\"",j);
        		jflag = true;
        	}
        	
        	if(!iflag)
        	{
        		strarray.add(arr[count]);
        		System.out.println(arr[count]);
        	}
        	if(iflag && jflag)
        	{
        		strarray.add(str.substring(i, j+1));
        		System.out.println(str.substring(i, j+1));
        		iflag = jflag =false;
        		if(j+1<str.length())
        			j=j+1;
        	}
        }
        return strarray;
	}
	
	
	
	
}
