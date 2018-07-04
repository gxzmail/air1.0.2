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
	 * �������ܣ�����caseDescript �������ֶε�ֵ�� /***��
	 				   �� NGTS_AM_AIR_D_ON05_CV01.xls �е�һ�м�¼ֵ��ֵ
	 				   ��caseDescript��xzguo NGTS_AM_AIR_D_ON05_CV01.xls�е�
	 			          һ�ж�Ӧһ��ApplCase ��
	 					
	 * �������룺
	 * @param values   ����ֵ//***xzguo NGTS_AM_AIR_D_ON05_CV01.xls�е�һ�м�¼����key=value
	 							��ʽ����������Ի���= C ��
	 * @param scene    �����������ڵĳ������
	 * ��������ֵ��
	 * ��
	 *************************************/
	public void setDetails(Hashtable values,String scene)
	{
	        Set a = values.entrySet();
	        Iterator b = a.iterator();
	        this.scene = scene;
	        caseDescript = new ApplCase();
	        //���ýű���ǰ�ֶε�ֵ 
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
	setValue������setCaseId ��   ���ڲ���������Ӧ�ö��Ⱪ¶ 
	getcaseDescript ���ⲿ����

***/
	 public void setValue(String key, String value)
	 {
		 if (key.equals("�������"))
	     {
			this.sceneType = strChg(value,3);
			this.caseDescript.setSceneType(sceneType);
	     }
	     else if (key.equals("����ID"))
	     {
	         this.sceneId = strChg(value,3);
	         this.caseDescript.setSceneId(sceneId);
	     }
	     else if (key.equals("��������"))
	     {
	         this.caseDescript.setCaseDetails(value);
	     }
	      else if (key.equals("���Ի���"))
	      {
	           this.caseDescript.setTestEnvr(value);
	      }
	      else if (key.equals("����"))
	      {
	           this.caseDescript.setTesthost(value);
	      }
	      else if (key.equals("��Ӧ�����ĵ�"))
	      {
	        	this.caseDescript.setTestNecessDoc(value);
	      }
	      else if (key.equals("���ȼ�"))
	      {
	        	this.caseDescript.setTestPrior(value);
	      }
	      else if (key.equals("����/�쳣"))
	      {
	        	this.caseDescript.setTestResult(value);
	      }
	   }

/***xzguo
	��һ������ǰ��0�� ���ָ�����ȵ��ַ���
	@param  c  һ������
	@param charLength  ָ���ĳ���
	@return ָ�����ȵ��ַ���
		    for example, strChg(2,3)���򷵻�002 
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
	����������������D_ON05.xlsx�е����������һ����丳ֵ��
	caseDescript
	@param scene  ������ ����ON05
	
***/
	    
	 public void setCaseId(String scene)
	 {
		 caseDescript.setCaseId(scene+"_"+this.sceneType+"_"+this.sceneId);
	 }
	 
	 public ApplCase getcaseDescript() {
	     return caseDescript;
	 }

}