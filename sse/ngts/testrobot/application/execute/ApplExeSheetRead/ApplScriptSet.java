package sse.ngts.testrobot.application.execute.ApplExeSheetRead;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;

public class ApplScriptSet {
	private ApplExecutCase steps;
	/**
	 * 函数功能:设置步骤中所有字段的值
	 * 函数输入：
	 * @param values
	 * @param cDetail
	 * 函数返回值：
	 *    空
	 */
    public void setApplSteps(Hashtable values)
    {
	        Set a = values.entrySet();
	        Iterator b = a.iterator();
	     
	        
	      
	        steps = new ApplExecutCase();
	        //设置脚本当前字段的值 
	        while (b.hasNext())
	        {
	            Entry c = (Entry) b.next();
	            String key = (String) c.getKey();
	            String value = (String) c.getValue();
	            setValue(key, value.trim());
	        }
	        
	        steps.setStepType();
	 }
	 public void setValue(String key, String value)
	 {
		 
		 if (key.equals("步骤编号"))
	     {
			this.steps.getFrmCase().setStepsId(value);
	     }
		 if (key.equals("脚本编号"))
	     {
			this.steps.getFrmCase().setScriptId(value);
	     }
	     else if (key.equals("脚本描述"))
	     {
	    	 this.steps.getFrmCase().setDescrip(value);
	     }
		 else if(key.equals("脚本执行内容"))
	     {
			 this.steps.getFrmCase().setTestContent(value);	 
			 if(value.indexOf(ApplExecuteConstValues.manual) == 0)
	    		 this.steps.setAttribute(ApplExecutCase.ATTR_AUTO_FLAG, false);
	    	 if(value.equalsIgnoreCase(ApplExecuteConstValues.skip))
	    		 this.steps.setAttribute(ApplExecutCase.ATTR_SKIP_FLAG, true);
	     }
	     else if (key.equals("交易日"))
	     {
	    	 this.steps.getFrmCase().setTestDate(value);  	
	     }
	     else if (key.equals("执行阶段"))
	     {
	    	 this.steps.getFrmCase().setTestPhase(value);   	
	     }
	     else if (key.equals("预期结果"))
	     {
	    	 this.steps.getFrmCase().setTestAntic(value);
	     }
	     else if (key.equals("主机"))
	     {
	    	 this.steps.getFrmCase().setTestHost(value);
	     }
	     else if (key.equals("批步骤"))
	     {   
	    	
	    	 this.steps.getFrmCase().setTestBatch(value);
	     }
	     else if (key.equals("优先级"))
	     {
	    	 this.steps.getFrmCase().setTestPrior(NumChg(value));
	     }
	     else if (key.equals("错误状态"))
	     {
	    	 this.steps.getFrmCase().setTestStatus(value);
	    	
	     }
	     else if (key.equals("备注"))
	     {
	    	 this.steps.getFrmCase().setTestMemo(value);
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
		
		public String NumChg(String a)
		{
			int b = Float.valueOf(a).intValue();
			return String.valueOf(b);
		}
		
		public ApplExecutCase getScript() {
			return steps;
		}
		public void setScript(ApplExecutCase script) {
			this.steps = script;
		}


}
