package sse.ngts.testrobot.application.creatExecute.ApplCaseDetails;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import sse.ngts.testrobot.engine.unit.ApplCase;



public class ApplDetails {
	
	private ApplCase caseDescript;
	private String sceneType;
	private String sceneId;
	private String scene;
	
	/************************************
	 * 函数功能：设置caseDescript 中所有字段的值， /***即
	 				   将 NGTS_AM_AIR_D_ON05_CV01.xls 中的一行记录值赋值
	 				   给caseDescript。xzguo NGTS_AM_AIR_D_ON05_CV01.xls中的
	 			          一行对应一个ApplCase 类
	 					
	 * 函数输入：
	 * @param values   －－值//***xzguo NGTS_AM_AIR_D_ON05_CV01.xls中的一行记录，以key=value
	 							形式给出，如测试环境= C 等
	 * @param scene    －－用例所在的场景编号
	 * 函数返回值：
	 * 空
	 *************************************/
	public void setDetails(Hashtable values,String scene)
	{
	        Set a = values.entrySet();
	        Iterator b = a.iterator();
	        this.scene = scene;
	        caseDescript = new ApplCase();
	        //设置脚本当前字段的值 
	        while (b.hasNext())
	        {
	            Entry c = (Entry) b.next();
	            String key = (String) c.getKey();
	            String value = (String) c.getValue();
	            setValue(key, value.trim());
	        }
	        setCaseId(scene);
	        
	 }

/***xzguo
	setValue函数，setCaseId ，   是内部函数，不应该对外暴露 
	getcaseDescript 是外部函数

***/
	 public void setValue(String key, String value)
	 {
		 if (key.equals("场景类别"))
	     {
			this.sceneType = strChg(value,3);
			this.caseDescript.setSceneType(sceneType);
	     }
	     else if (key.equals("场景ID"))
	     {
	         this.sceneId = strChg(value,3);
	         this.caseDescript.setSceneId(sceneId);
	     }
	     else if (key.equals("用例描述"))
	     {
	         this.caseDescript.setCaseDetails(value);
	     }
	      else if (key.equals("测试环境"))
	      {
	           this.caseDescript.setTestEnvr(value);
	      }
	      else if (key.equals("主机"))
	      {
	           this.caseDescript.setTesthost(value);
	      }
	      else if (key.equals("对应需求文档"))
	      {
	        	this.caseDescript.setTestNecessDoc(value);
	      }
	      else if (key.equals("优先级"))
	      {
	        	this.caseDescript.setTestPrior(value);
	      }
	      else if (key.equals("正常/异常"))
	      {
	        	this.caseDescript.setTestResult(value);
	      }
	   }

/***xzguo
	将一个数字前加0， 变成指定长度的字符串
	@param  c  一个数字
	@param charLength  指定的长度
	@return 指定长度的字符串
		    for example, strChg(2,3)，则返回002 
***/
	    public  String strChg(String c,int charLength)
	    {

	    	Float  caseDescript = Float.valueOf(c);
	    	String  a = String.valueOf(caseDescript.intValue());
	    	while (a.length() < charLength)
	            a = "0" + a;
	        return a;
	    }
/***xzguo
	函数做作用是生成D_ON05.xlsx中的用例编号这一项，将其赋值给
	caseDescript
	@param scene  场景号 ，如ON05
	
***/
	    
	 public void setCaseId(String scene)
	 {
		 caseDescript.setCaseId(scene+"_"+this.sceneType+"_"+this.sceneId);
	 }
	 
	 public ApplCase getcaseDescript() {
	     return caseDescript;
	 }

}
