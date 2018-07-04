package sse.ngts.testrobot.application.functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;

public class ApplFileProcess {
	
	 
	 /***
	 * 函数说明：line字符串用token作为分隔符，取其中的第n个字段
	 * 输入参数：
	 * int n       －－ 需要取的字段的位置, 从1开始取值
	 * String token－－分隔符
	 * String line －－ 字段所在的字符串
	 * 返  回  值：
	 *    字符串
	 */
	 public static  String getStringByToken(int n,String token,String line)
	 {
         String Id = null;
		 StringTokenizer t = new StringTokenizer(line,token);
		 if(n > t.countTokens())
			 return null;
		 while(n > 1)
		 {
		     t.nextToken();
		     n-- ;
		     
		 }
		 Id = t.nextToken(); 
		 return Id;
	 }
	 
	 /***
	  * 函数功能：把数组输出到指定的文件中
	  * 函数输入：
	  * @param in            －－要输出的数组
	  * 函数输出：
	  * @param filePath      －－输出文件路径
	 * @throws IOException 
	  */
	 public static void fileOutPut(HashSet<String> in, String filePath)
	 {
		 try
		 {
		     File file = new File(filePath);
		     if(!file.exists())
		    	 file.createNewFile();
			 PrintWriter out = new PrintWriter(new FileWriter(filePath));
		     
		     String outPutString = null;
		     int i = 0;
		     /*排序*/
		     ArrayList<String> d = new ArrayList<String>();
		     d= CaseListSort(in);
		     
		     Iterator<String> iter = d.iterator();
		     while(iter.hasNext())
		     {
		    	 
		    	 outPutString = iter.next();
		    	 out.println(String.valueOf(i++)+"|"+outPutString);
		     }  
		     out.close();
		     Logger.getLogger(ApplConstValues.logName).
                log(Level.INFO, "写文件{0}成功",filePath);

		 }
		 catch(IOException exception)
		 {
			 exception.printStackTrace();
		     Logger.getLogger(ApplConstValues.logName).
               log(Level.WARNING, "写文件{0}失败",filePath);
		     //Logger.getLogger(ApplConstValues.logName).
		     //  log(Level.WARNING, "生成执行文件失败");
			//  ApplExecuteResultDialog.viewError("执行失败,写文件"+filePath+"失败", "ERROR");
		    
 
		 }
	 }
	 public static  ArrayList<String> CaseListSort(HashSet<String> in)
	 {
   
	     ArrayList<String> d = new ArrayList<String>();
	     Iterator<String> c = in.iterator();
	     String str = null;
	     while(c.hasNext())
	     {
	    	 str = c.next();
	    	 if(str.isEmpty())
	    		 continue;
	    	 d.add(str);
	     }
	     Collections.sort(d, new ApplCaseListSort());
	     return d;
	 }
	 
	 public static Object[] dircToStringArray(String path)
	 {
         ArrayList<String> str = new ArrayList<String>();
		 String Id = null;
		 StringTokenizer t = new StringTokenizer(path,"\\");		 
		 while(t.hasMoreTokens())
		 {
		    str.add(t.nextToken());
		     
		 }
		 return str.toArray();
		 
	 }
	 public static String getFileName(String path)
	 {
		 Object[] fileArray = dircToStringArray(path);
		 int len = fileArray.length;
		 if(len == 0)
			 return null;			 
		 return (String)fileArray[len-1];
	 }
	 
	 /*****创建目录***********/
	 public static void createDir(String path,int type)
	 {
	        if(path == null)
	        	return;
	        Object[] dir = dircToStringArray(path);
	        int len = 0;
	        if(type == ApplExecuteConstValues.APPL_File)
	        	len = dir.length-1;
	        else if(type == ApplExecuteConstValues.APPL_DIRC)
	        	len = dir.length;
	        String dirc = null;
	        for(int i=0;i<len;i++)
	        {      	
	        	if(dirc!=null)
	        	{
	        	    dirc = dirc+"\\"+(String)dir[i];
	        	}
	        	else 
	        		dirc = (String)dir[i];
	        	File fileDirc = new File(dirc);
	        	if (!fileDirc.exists()||! fileDirc.isDirectory())
		        {
		    	    Logger.getLogger(ApplConstValues.logName).
	                log(Level.INFO, "文件夹{0}不存在",fileDirc.getAbsolutePath());
		    	    fileDirc.mkdir();
		    	    Logger.getLogger(ApplConstValues.logName).
	                log(Level.INFO, "新建文件夹{0}",fileDirc.getAbsolutePath());
		    
		        }	        	
	        }	
	    }
}
