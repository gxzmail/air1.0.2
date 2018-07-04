package sse.ngts.testrobot.engine.unit;

import java.util.ArrayList;
import java.util.BitSet;

public class ApplExecutCase  {
    
	/**
	 * ����������
	 */
	public ApplFrmwkCase frmCase;
    //��ǰ�������е�״̬
    public static final int PROCESS_STATUS_RUN = 1;
    public static final int PROCESS_STATUS_STOP = 2;

    //��ǰ�ű�����������
    /* �Զ�ִ�еĽű���*/
    public static final int ATTR_AUTO_FLAG = 1;

    public static final int ATTR_ENABLE_FLAG = 2;

    public static final int ATTR_SHOW_FLAG = 3;

    public static final int ATTR_FINISH_FLAG = 4;

    public static final int ATTR_SUCCESS_FLAG = 5;
    /*�Ƿ���ʹ�*/
    public static final int ATTR_REFF_FLAG = 6;
    
    public static final int ATTR_SKIP_FLAG = 7;
    public static final int ATTR_AUTOFIN_FLAG = 8;
    private int stepType = 0;
    private boolean faileflag = false;
    private BitSet attributeSets = new BitSet();

    //��ǰ�ű�������״̬
    private int processStatus = PROCESS_STATUS_STOP;

    private String stepTypeDescr = null;
    private String testResultDescr = null;
    private Boolean faileMannul = false;
	private ArrayList<String>  testResult = new ArrayList<String>();

	public ApplExecutCase()
    {

        setAttribute(ATTR_ENABLE_FLAG, true);
        setAttribute(ATTR_SHOW_FLAG, true);
        setAttribute(ATTR_AUTO_FLAG, true);
        setAttribute(ATTR_SKIP_FLAG, false);
        setAttribute(ATTR_REFF_FLAG, false);
        setAttribute(ATTR_AUTOFIN_FLAG, false);
        testResultDescr = ApplExecuteConstValues.result1;
        setStepType();
        setMannul();

    }
    
    public ApplExecutCase(ApplFrmwkCase step)
    {
        super();
        frmCase = step;
        
        setAttribute(ATTR_ENABLE_FLAG, true);
        setAttribute(ATTR_SHOW_FLAG, true);
        setAttribute(ATTR_AUTO_FLAG, true);
        setAttribute(ATTR_SKIP_FLAG, false);
        setAttribute(ATTR_AUTOFIN_FLAG, false);
        if(this.frmCase.getTestContent().indexOf(ApplExecuteConstValues.manual) == 0)
        	setAttribute(ATTR_AUTO_FLAG, false);
        if(this.frmCase.getTestContent().equalsIgnoreCase(ApplExecuteConstValues.skip))
       	    setAttribute(ATTR_SKIP_FLAG, true);
        setAttribute(ATTR_REFF_FLAG, false);
        setStepType();
        testResultDescr = ApplExecuteConstValues.result1;
        setMannul();
    }
    


    /**
     * ��õ�ǰ�ű�������
     * @return ��ǰ�ű�������
     */
    public boolean getAttribute(int flag)
    {
        return attributeSets.get(flag);
    }

    
    private void setMannul()
    {
    	if((this.stepType ==1 )||(this.stepType ==3)||(this.stepType ==6))
    	{
    		faileMannul =  true;
    		return;
    	}
    	faileMannul =  false;
    }
    
    public boolean getMannul()
    {
    	return faileMannul;
    }
    /**
     * ���õ�ǰ�ű�������
     * @param attribute ���õ�ǰ�ű�������
     */
    public void setAttribute(int flag, boolean f)
    {
        attributeSets.set(flag, f);
    }



    public int getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}

	public int getStepType() {
		return stepType;
	}

	public void setStepType() {
		if(this.getAttribute(ATTR_SKIP_FLAG))
		{
			this.stepType =5;
			stepTypeDescr = "����ִ��";
		}
		else if(this.getAttribute(ATTR_AUTO_FLAG)&&this.frmCase.getTestStatus().equalsIgnoreCase("N"))
		{	
			this.stepType =1;
			stepTypeDescr = "������ֶ�";
		}
		else if(this.getAttribute(ATTR_AUTO_FLAG)&&this.frmCase.getTestStatus().equalsIgnoreCase("Y"))
		{
			this.stepType =2;
			stepTypeDescr = "�������";
		}
		else if(this.getAttribute(ATTR_AUTO_FLAG)&&this.frmCase.getTestStatus().equalsIgnoreCase("L"))
		{
			this.stepType =3;
			stepTypeDescr = "�����Ĵκ�ֹͣ";
		}
		else if(this.getAttribute(ATTR_AUTO_FLAG)&&this.frmCase.getTestStatus().equalsIgnoreCase("R"))
		{
		     this.stepType =6; 
		     stepTypeDescr = "�����ظ�����ͣ";
		}
		else if(!this.getAttribute(ATTR_AUTO_FLAG))
		{
			this.stepType =4;
			stepTypeDescr = "�ֶ�ִ��";
		}
		
			
	}
	
	 public void setReuslt()
	 {
		   if( processStatus ==PROCESS_STATUS_RUN)
	    		this.setTestResultDescr (ApplExecuteConstValues.result2);	
		    else if(!this.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
	    		this.setTestResultDescr (ApplExecuteConstValues.result1);
		    else if(this.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
		    		this.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
		    		this.faileMannul&&
		    		this.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
		    		!faileflag)
	    		this.setTestResultDescr (ApplExecuteConstValues.result3);
	    	else if(this.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
	    			this.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
	    			this.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
	    			(!this.faileMannul))
	    		this.setTestResultDescr(ApplExecuteConstValues.result3);
	    	else if (this.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
	                this.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
	                (!this.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)||
	            	this.faileMannul&&this.faileflag))
	    		this.setTestResultDescr(ApplExecuteConstValues.result7);
	    	else  if (!this.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
	                this.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
	                this.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
	                !this.faileMannul)
	    		this.setTestResultDescr(ApplExecuteConstValues.result4);
	    	else  if( !this.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
	        		!this.getAttribute(ApplExecutCase.ATTR_SKIP_FLAG)&&
	        		!this.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
	        		this.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
	    		this.setTestResultDescr(ApplExecuteConstValues.result6);
	    	else  if( this.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
	        		!this.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
	        		this.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
	        		this.faileMannul)
	    	{
	    		this.setTestResultDescr(ApplExecuteConstValues.result5);
	    		this.faileflag = true;
	    	}
	    	
	    		
	    }

	public void setFaileflag(boolean faileflag) {
		this.faileflag = faileflag;
	}

	public boolean isFaileflag() {
		return faileflag;
	}

	public ApplFrmwkCase getFrmCase() {
		return frmCase;
	}

	public String getStepTypeDescr() {
		return stepTypeDescr;
	}
	
	public String getTestResultDescr() {
		setReuslt();
		return testResultDescr;
	}

	public void setTestResultDescr(String testResultDescr) {
		this.testResultDescr = testResultDescr;
	}
	public ArrayList<String> getTestResult() {
		return testResult;
	}
}
