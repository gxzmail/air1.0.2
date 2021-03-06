package sse.ngts.testrobot.application.creatExecute.ApplCaseSteps;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import sse.ngts.testrobot.engine.unit.ApplCase;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;

public class ApplStepsSet {
	private ApplFrmwkCase steps;
	private ApplCase caseDetail;
	
	/**
	 * 函数功能:设置步骤中所有字段的值
	 * 函数输入：
	 * @param values
	 * @param cDetail
	 * 函数返回值：
	 *    空
	 */
    public void setApplSteps(Hashtable values, ApplCase cDetail)
    {
	        Set a = values.entrySet();
	        Iterator b = a.iterator();
	        caseDetail = new ApplCase();
	        
	        this.caseDetail = cDetail;
	        steps = new ApplFrmwkCase();
	      
	        //设置脚本当前字段的值 
	        while (b.hasNext())
	        {
	            Entry c = (Entry) b.next();
	            String key = (String) c.getKey();
	            String value = (String) c.getValue();
/***xzguo

	setValue(key, value.trim());该步骤为把每一个NGTS_AM_AIR_ON05_001_001_CV01.xls 中的每一行内容(values)	
	放入this.steps中
***/
	            setValue(key, value.trim());
	        }
/***
	   steps.setCaseDetials(cDetail.getCaseDetails()); 该步骤为把NGTS_AM_AIR_D_ON05_CV01.xls   (caseDetail)   中的某行记录中的
	   "用例描述" 字段放入this.steps中
***/
	        steps.setCaseDetials(cDetail.getCaseDetails());
	 }
	 public void setValue(String key, String value)
	 {
		 if (key.equals("脚本编号"))
	     {
			this.steps.setScriptId(this.caseDetail.getCaseId()+"_"+strChg(NumChg(value),3));
	     }
	     else if (key.equals("脚本描述"))
	     {
	    	 this.steps.setDescrip(value);
	     }
		 else if(key.equals("脚本执行内容"))
	     {
			 this.steps.setTestContent(value);	   
	     }
	     else if (key.equals("交易日"))
	     {
	    	 this.steps.setTestDate(value);  	
	     }
	     else if (key.equals("执行阶段"))
	     {
	    	 this.steps.setTestPhase(value);   	
	     }
	     else if (key.equals("预期结果"))
	     {
	    	 this.steps.setTestAntic(value);
	     }
	     else if (key.equals("主机"))
	     {
	    	 this.steps.setTestHost(value);
	     }
	     else if (key.equals("批步骤"))
	     {   
	    	 if(value.isEmpty())
	    		 this.steps.setTestBatch("");
	    	 else
	    	     this.steps.setTestBatch(NumChg(value));
	     }
	     else if (key.equals("优先级"))
	     {
	    	 if(value.isEmpty())
	    	 {
	    	 /***xzguo !caseDetail.getTestPrior().isEmpty()含义 :
	    	  * NGTS_AM_AIR_ON05_001_001_CV01.xls中优先级没填，则取NGTS_AM_AIR_D_ON05_CV01.xls记录的优先级值
	    	  */
	    		 if(!caseDetail.getTestPrior().isEmpty()) 
	    			 this.steps.setTestPrior(NumChg(caseDetail.getTestPrior().trim()));
	    		 else
	    			 this.steps.setTestPrior(NumChg("5"));
	    	 }
	    	 else
	    	     this.steps.setTestPrior(NumChg(value));
	     }
	     else if (key.equals("错误状态"))
	     {
	    	 this.steps.setTestStatus(value);
	     }
	     else if (key.equals("备注"))
	     {
	    	 this.steps.setTestMemo(value);
	     }
	 }
	    public  String strChg(String c,int charLength)
	    {

	    	Float  caseDescript = Float.valueOf(c);
	    	String  a = String.valueOf(caseDescript.intValue());
	    	while (a.length() < charLength)
	            a = "0" + a;
	        return a;
	    }
		public ApplFrmwkCase getSteps() {
			return steps;
		}
		public void setSteps(ApplFrmwkCase steps) {
			this.steps = steps;
		}
		
		public String NumChg(String a)
		{
			int b = Float.valueOf(a).intValue();
			return String.valueOf(b);
		}
}
