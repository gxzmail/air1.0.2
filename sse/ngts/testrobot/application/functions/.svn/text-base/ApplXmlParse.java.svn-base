package sse.ngts.testrobot.application.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;

public class ApplXmlParse {

	private Hashtable<String,String> xmlParse = new Hashtable<String,String>();

	private static ApplXmlParse instance;
	
    public static ApplXmlParse getinstance()
    {
        if (instance == null)
        {
            instance = new ApplXmlParse();
        }
        return instance;
    }
    
    /*2012-04-18 modified by wuli,start*/
	/**
    public  Boolean readxml()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
			DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.parse(ApplConstValues.airconfigFileName);
		    Element root = doc.getDocumentElement();
		    NodeList nodelist = root.getChildNodes();
		    handlenodelist(nodelist);
	    } catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    return false;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    return false;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    return false;

		}
	    return true;
	}
	**/
     		
	 public boolean readxml()
	 {
		 Logger.getLogger(ApplConstValues.logName).
           entering("ApplCycleImpl", "loadFile");

		 File airconfig = new File(ApplConstValues.airconfigFileName);

	     if (airconfig.exists())
	     {
	    	 BufferedReader reader = null;
	         try{
	        	 reader = new BufferedReader(new FileReader(airconfig));
	             String tempString = null;
	             String keys = null;
	             String values = null;
	             while ((tempString = reader.readLine())!=null) 
	             {
	            	 if(tempString.isEmpty())
	            		 continue; 
	            	 if(tempString.trim().contains("#"))
	            		 continue; 
	            	 if(tempString.trim().contains("="))
	            	 {
		            	 keys = ApplFileProcess.getStringByToken(1,"=",tempString);
		            	 values = ApplFileProcess.getStringByToken(2,"=",tempString);
		            	 xmlParse.put(keys.trim(), values);
	            	 }
	            	 
	             }     

	  		     Logger.getLogger(ApplConstValues.logName).
                    log(Level.INFO, "读文件{0}成功",airconfig.getName());
	             return true;
	         }
	         catch (IOException e) {
	             e.printStackTrace();
	  		     Logger.getLogger(ApplConstValues.logName).
                   log(Level.SEVERE, "读文件{0}失败，IO读写错误",airconfig.getAbsolutePath());
				 ApplExecuteResultDialog.viewError("执行失败,读文件"+airconfig.getAbsolutePath()+"失败", "ERROR");
	             return false;
	         } 
	         catch(Exception e)
	         {
	        	 Logger.getLogger(ApplConstValues.logName).        	 	        	 
                  log(Level.SEVERE, "读文件{0}失败",airconfig.getName());
				 ApplExecuteResultDialog.viewError("执行失败,读文件"+airconfig.getAbsolutePath()+"失败", "ERROR");
	             return false;

	         }
	         finally {
	             if (reader != null) {
	                 try {
	                     reader.close();
	                 } catch (IOException e1) {
	                 }
	             }
	         }

	     }
	     else
	     {
	    	 Logger.getLogger(ApplConstValues.logName). 
	    	   log(Level.SEVERE, "文件{0}不存在",airconfig.getName());
	        
	         Logger.getLogger(ApplConstValues.logName).
		         log(Level.SEVERE, "执行手册生成失败 ");
			 ApplExecuteResultDialog.viewError("执行失败,文件"+airconfig.getAbsolutePath()+"不存在", "ERROR");
             return false;

	     }

	 }
    
	/*2012-04-18 modified by wuli,end*/
	public void handlenodelist(NodeList nodelist)
	{
	    for(int i = 0; i<nodelist.getLength();i++)
		{
		    Node child = nodelist.item(i);
		    if(child instanceof Element)
		    {
		        Element childelment = (Element)child;
				NodeList nodelist1 = childelment.getChildNodes();
			    if(nodelist1.getLength() == 1)
                {
		            Text textNode = (Text)childelment.getFirstChild();
		    		String text = textNode.getData().trim();
				    NamedNodeMap t = child.getAttributes();

		    		if(t.getLength()>=1)
		    		{
				    for(int k =0;k<t.getLength();k++)
		    		{
			    		Node tnode= t.item(k);
			    		xmlParse.put(tnode.getNodeValue().trim(),text);
			    		
		    		}
		    		}
		    		else
		    		{
			    		xmlParse.put(childelment.getTagName().trim(),text);

		    		}
		    		
                 }
                 else if(nodelist1.getLength() > 1)
                 {
             	    	handlenodelist(nodelist1);
                 	
                 }
		    }
		}
	}
	
	
	public Hashtable<String, String> getXmlParse() {
		return xmlParse;
	}
	
}
