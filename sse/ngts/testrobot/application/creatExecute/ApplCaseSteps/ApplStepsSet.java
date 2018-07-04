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
	 * ��������:���ò����������ֶε�ֵ
	 * �������룺
	 * @param values
	 * @param cDetail
	 * ��������ֵ��
	 *    ��
	 */
    public void setApplSteps(Hashtable values, ApplCase cDetail)
    {
	        Set a = values.entrySet();
	        Iterator b = a.iterator();
	        caseDetail = new ApplCase();
	        
	        this.caseDetail = cDetail;
	        steps = new ApplFrmwkCase();
	      
	        //���ýű���ǰ�ֶε�ֵ 
	        while (b.hasNext())
	        {
	            Entry c = (Entry) b.next();
	            String key = (String) c.getKey();
	            String value = (String) c.getValue();
/***xzguo

	setValue(key, value.trim());�ò���Ϊ��ÿһ��NGTS_AM_AIR_ON05_001_001_CV01.xls �е�ÿһ������(values)	
	����this.steps��
***/
	            setValue(key, value.trim());
	        }
/***
	   steps.setCaseDetials(cDetail.getCaseDetails()); �ò���Ϊ��NGTS_AM_AIR_D_ON05_CV01.xls   (caseDetail)   �е�ĳ�м�¼�е�
	   "��������" �ֶη���this.steps��
***/
	        steps.setCaseDetials(cDetail.getCaseDetails());
	 }
	 public void setValue(String key, String value)
	 {
		 if (key.equals("�ű����"))
	     {
			this.steps.setScriptId(this.caseDetail.getCaseId()+"_"+strChg(NumChg(value),3));
	     }
	     else if (key.equals("�ű�����"))
	     {
	    	 this.steps.setDescrip(value);
	     }
		 else if(key.equals("�ű�ִ������"))
	     {
			 this.steps.setTestContent(value);	   
	     }
	     else if (key.equals("������"))
	     {
	    	 this.steps.setTestDate(value);  	
	     }
	     else if (key.equals("ִ�н׶�"))
	     {
	    	 this.steps.setTestPhase(value);   	
	     }
	     else if (key.equals("Ԥ�ڽ��"))
	     {
	    	 this.steps.setTestAntic(value);
	     }
	     else if (key.equals("����"))
	     {
	    	 this.steps.setTestHost(value);
	     }
	     else if (key.equals("������"))
	     {   
	    	 if(value.isEmpty())
	    		 this.steps.setTestBatch("");
	    	 else
	    	     this.steps.setTestBatch(NumChg(value));
	     }
	     else if (key.equals("���ȼ�"))
	     {
	    	 if(value.isEmpty())
	    	 {
	    		 if(!caseDetail.getTestPrior().isEmpty())
	    			 this.steps.setTestPrior(NumChg(caseDetail.getTestPrior().trim()));
	    		 else
	    			 this.steps.setTestPrior(NumChg("5"));
	    	 }
	    	 else
	    	     this.steps.setTestPrior(NumChg(value));
	     }
	     else if (key.equals("����״̬"))
	     {
	    	 this.steps.setTestStatus(value);
	     }
	     else if (key.equals("��ע"))
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