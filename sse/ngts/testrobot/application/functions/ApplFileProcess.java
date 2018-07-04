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
	 * ����˵����line�ַ�����token��Ϊ�ָ�����ȡ���еĵ�n���ֶ�
	 * ���������
	 * int n       ���� ��Ҫȡ���ֶε�λ��, ��1��ʼȡֵ
	 * String token�����ָ���
	 * String line ���� �ֶ����ڵ��ַ���
	 * ��  ��  ֵ��
	 *    �ַ���
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
	  * �������ܣ������������ָ�����ļ���
	  * �������룺
	  * @param in            ����Ҫ���������
	  * ���������
	  * @param filePath      ��������ļ�·��
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
		     /*����*/
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
                log(Level.INFO, "д�ļ�{0}�ɹ�",filePath);

		 }
		 catch(IOException exception)
		 {
			 exception.printStackTrace();
		     Logger.getLogger(ApplConstValues.logName).
               log(Level.WARNING, "д�ļ�{0}ʧ��",filePath);
		     //Logger.getLogger(ApplConstValues.logName).
		     //  log(Level.WARNING, "����ִ���ļ�ʧ��");
			//  ApplExecuteResultDialog.viewError("ִ��ʧ��,д�ļ�"+filePath+"ʧ��", "ERROR");
		    
 
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
	 
	 /*****����Ŀ¼***********/
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
	                log(Level.INFO, "�ļ���{0}������",fileDirc.getAbsolutePath());
		    	    fileDirc.mkdir();
		    	    Logger.getLogger(ApplConstValues.logName).
	                log(Level.INFO, "�½��ļ���{0}",fileDirc.getAbsolutePath());
		    
		        }	        	
	        }	
	    }
}
