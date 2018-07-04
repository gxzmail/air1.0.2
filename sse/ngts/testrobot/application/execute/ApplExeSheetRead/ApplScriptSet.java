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
	 * ��������:���ò����������ֶε�ֵ
	 * �������룺
	 * @param values
	 * @param cDetail
	 * ��������ֵ��
	 *    ��
	 */
    public void setApplSteps(Hashtable values)
    {
	        Set a = values.entrySet();
	        Iterator b = a.iterator();
	     
	        
	      
	        steps = new ApplExecutCase();
	        //���ýű���ǰ�ֶε�ֵ 
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
		 
		 if (key.equals("������"))
	     {
			this.steps.getFrmCase().setStepsId(value);
	     }
		 if (key.equals("�ű����"))
	     {
			this.steps.getFrmCase().setScriptId(value);
	     }
	     else if (key.equals("�ű�����"))
	     {
	    	 this.steps.getFrmCase().setDescrip(value);
	     }
		 else if(key.equals("�ű�ִ������"))
	     {
			 this.steps.getFrmCase().setTestContent(value);	 
			 if(value.indexOf(ApplExecuteConstValues.manual) == 0)
	    		 this.steps.setAttribute(ApplExecutCase.ATTR_AUTO_FLAG, false);
	    	 if(value.equalsIgnoreCase(ApplExecuteConstValues.skip))
	    		 this.steps.setAttribute(ApplExecutCase.ATTR_SKIP_FLAG, true);
	     }
	     else if (key.equals("������"))
	     {
	    	 this.steps.getFrmCase().setTestDate(value);  	
	     }
	     else if (key.equals("ִ�н׶�"))
	     {
	    	 this.steps.getFrmCase().setTestPhase(value);   	
	     }
	     else if (key.equals("Ԥ�ڽ��"))
	     {
	    	 this.steps.getFrmCase().setTestAntic(value);
	     }
	     else if (key.equals("����"))
	     {
	    	 this.steps.getFrmCase().setTestHost(value);
	     }
	     else if (key.equals("������"))
	     {   
	    	
	    	 this.steps.getFrmCase().setTestBatch(value);
	     }
	     else if (key.equals("���ȼ�"))
	     {
	    	 this.steps.getFrmCase().setTestPrior(NumChg(value));
	     }
	     else if (key.equals("����״̬"))
	     {
	    	 this.steps.getFrmCase().setTestStatus(value);
	    	
	     }
	     else if (key.equals("��ע"))
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
