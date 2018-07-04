package sse.ngts.testrobot.application.execute.ApplExecuteProcess;

import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.execute.ApplAutoExecute.ApplAutoExecute;
import sse.ngts.testrobot.application.execute.ApplAutoExecute.ApplAutoExecuteImpl;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.exception.ApplPauseException;
import sse.ngts.testrobot.factory.EXECUFactory;

public class ApplExecuteProcessImpl implements ApplExecuteProcess{
	
	
    /**
     * 判断步骤的执行类别，调用相应的接口实现执行过程，并根据返回值设置步骤详情数组
     * @param step －－步骤详情数组
     * @return   boolean  －－步骤执行是否成功
     */
    private ActionController actioncontroller;

	public void stepProcess(ApplExecutCase step)
	{

		String[] str = {step.getFrmCase().getStepsId(),step.getFrmCase().getScriptId()};
		Logger.getLogger(ApplExecuteConstValues.logExecutName).
          log(Level.INFO, "步骤{0}开始执行步骤， 脚本号码：{1}",str);		
		if(handlePreSteps(step))
		{
			step.setReuslt();
			return;
		}
		/*设置步骤被访问过*/
		step.setAttribute(ApplExecutCase.ATTR_REFF_FLAG,true);
		
		try{
			/*手动执行抛出异常，需要手动执行*/
            if(step.getStepType() == 4)
            {
            	Logger.getLogger(ApplExecuteConstValues.logExecutName).
	              log(Level.INFO, "步骤{0}为手动执行类型",str);
            	step.getTestResult().add("手动类型，需要手动执行");
            	throw new ApplPauseException();
            }
		    else if(step.getStepType() == 5){
			    //写入日志，步骤不执行
			    step.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG,true);
			    step.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG,true);     	    	 
			    step.getTestResult().add("N/A类型，跳过");
        		step.setAttribute(ApplExecutCase.ATTR_AUTOFIN_FLAG,true);
			    Logger.getLogger(ApplExecuteConstValues.logExecutName).
	              log(Level.INFO, "步骤{0}执行完成，脚本号码：{1}",str);
			
			
		    }
		    else{
			     //写入日志，步骤自动执行
		    	ApplAutoExecute autoExecute= 
		    		EXECUFactory.getApplAutoExecuteInstance();
		    	autoExecute.setExecuteProcess(this);
	            if(!autoExecute.autoExec(step))
	            {
	    			throw new ApplPauseException();
	            }
	       
		    }
			step.setReuslt();
	    }
		catch( ApplPauseException ex)
        {
			step.setReuslt(); 
			ex.printStackTrace(); 
			throw new ApplPauseException();

        }
		
	}

	/*当步骤已经执行一次，但因为需要手动执行而停止执行，则设置步骤状态为执行成功*/
    private boolean handlePreSteps(ApplExecutCase step)
    {
        if (!step.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
        	return false;
        if (step.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
        		step.getStepType()!= 2 ||step.getStepType() == 4)
        {
        	step.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, true);
        	step.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG, true);      	
        	Logger.getLogger(ApplExecuteConstValues.logExecutName).
              log(Level.INFO, "步骤{0}手动执行成功",step.getFrmCase().getStepsId());
		    step.getTestResult().add("步骤"+step.getFrmCase().getStepsId()+"手动执行成功");
		    return true;
        }  
        else if(step.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&step.getStepType()== 2)
        {
        	step.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, true);
        	return false;
        }
        return false;
    }

	public boolean isStoprun() {
		return actioncontroller.isStopRun();
	}

	public void setActioncontroller(ActionController actioncontroller) {
		this.actioncontroller = actioncontroller;
	}
   
}
