package sse.ngts.testrobot.engine.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;

import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.exception.ApplRunFailException;

/***xzguo
	读取D:\project\work\trunk\Cfg\air_config.txt
***/
public class ApplCfg
{
	public static final String cfgFileName = ApplConstValues.configFileName;
    public static final String TestFile_KEY = "appl.TestFile";
    public static final String TestResultPath_KEY = "appl.TestResultPath";
   
    public static final String CaseListFile_KEY= "appl.CaseListFile"; /*用例列表文件的输出路径*/
    public static final String sceneListFile_KEY= "appl.sceneListFile";/*场景列表文件的输出路径*/
    
    public static final String CaseScript_KEY= "appl.CaseScript";
    public static final String TestCaseStepsScriptPath_KEY= "appl.TestCaseStepsScriptPath"; /*用例步骤文件的输出路径*/
    
    public static final String ExecuteFile_KEY="appl.ExecuteFile";

    public static final String CasePath_KEY= "appl.CasePath";     /*用例所在的文件夹的输入路径*/
    public static final String FrmWorkPath_KEY="appl.FRMWORKFile";          /*框架文件的输入路径*/
   
    public static final String ExecuteOutResult_Key = "appl.ExecuteoutPutResult";


    public static final String FrmId_KEY="appl.FrmId";

	public static final String CASETYPE_KEY = "appl.path." ;

	public static final String ExecuteOutSTAT_Key = "appl.ExecuteoutPutStat";
    private static ApplCfg instance = null;
    public static Properties props;
    public static Hashtable<String,String> caseType = new Hashtable<String,String>();
    private ApplCfg()
    {
        props = new Properties();
         
        loadCfgFile();
        Enumeration<Object> keyEnum = props.keys();
        while(keyEnum.hasMoreElements())
        {	       	
        	String str = (String) keyEnum.nextElement();
        	if(str.indexOf(CASETYPE_KEY)!=-1)
        	{
        		
        		//System.out.println(getProperty(str));
        		caseType.put(str.substring(10),getProperty(str));
        	}
        }   
        
    }

      public static ApplCfg getInstance()
    {
        if (instance == null)
        {
            instance = new ApplCfg();
        }
        return instance;
    }

    public void modify(String key, String value)
    {
        try {
			props.setProperty(key, new String(value.getBytes("GBK"),"ISO_8859_1"));
	    	
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public String getProperty(String key)
    {
        String str = null;
    	try {
    		str = new String(props.getProperty(key).getBytes("ISO_8859_1"),"GBK");
		    if(str.isEmpty())
		    	throw new ApplRunFailException(key,null);
		    	
    	} 
    	catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		catch(Error ex)
		{
			throw new ApplRunFailException(key,null);
		}
		catch(Exception ex)
		{
			throw new ApplRunFailException(key,null);
		}
	
		return str;
		
        
    }


    public void loadCfgFile()
    {
        File file = new File(cfgFileName);
        if (file.exists())
        {
            FileInputStream in = null;
            try
            {
                in = new FileInputStream(file);
                props.load(in);
            }
            catch (FileNotFoundException ex)
            {
                ex.printStackTrace();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

        }

    }


    public void saveCfgFile()
    {

        try
        {
            File file = new File(cfgFileName);
            if (file.exists())
            {
                file.delete();
            }  
            file.createNewFile();
            PrintWriter outP = new PrintWriter(new FileWriter(cfgFileName));
            Enumeration<Object> keyEnum = props.keys();
            while(keyEnum.hasMoreElements())
            {	
            	
            	String str = (String) keyEnum.nextElement();
            	String str1 = new String(ApplCfg.getInstance().getProperty(str));           	
            	outP.println(str+"="+chgStr(str1));

            }   
            outP.close();
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
    
    private String chgStr(String str)
    {
    	StringBuilder str2 = new  StringBuilder();
    	if (str == null)
    		return str;
    	for(int i = 0;i<str.length();i++)
    	{
    		char c = str.charAt(i);
    		if(c == '\\')
    		{
    			str2.append("\\\\");
    		
    		}
    		else str2.append(c);
    	}
    	return str2.toString();
  		
    }
}
