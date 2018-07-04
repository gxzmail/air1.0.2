package sse.ngts.testrobot.application.execute.ApplAutoExecute;

import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ApplExecuteProcess;
import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ApplExecuteProcessImpl;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.exception.ApplPauseException;

public class ApplAutoExecuteImpl implements ApplAutoExecute {
	/**
	 * 自动执行步骤
	 * @param step －－步骤详情
	 * @return ：boolean--执行是否成功
	 */
	
	private ApplExecuteProcessImpl executeProcess;
    private boolean runStop;
    
	public boolean  autoExec(ApplExecutCase step)
	{
		String stepContent = step.getFrmCase().getTestContent();
		ApplExecueteCommand exeComd=  new ApplExecueteCommand();
		boolean flag = exeComd.execueteCommand(stepContent);
		int RNum = 1;
		int LNum = 1;
		if(flag)
		{
			step.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG,true);
		    step.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG,true);  
    		step.setAttribute(ApplExecutCase.ATTR_AUTOFIN_FLAG,true);

			//结果写入日志	
		    step.getTestResult().add(exeComd.getFailedCause());
		    Logger.getLogger(ApplExecuteConstValues.logExecutName).
	          log(Level.INFO, "步骤执行成功，步骤ID:{0} ",step.getFrmCase().getStepsId());
			
		}
		else if(!flag)
		{
			
			if(step.getStepType() == 2)
			{
				//结果写入日志
				Logger.getLogger(ApplExecuteConstValues.logExecutName).
		          log(Level.WARNING, "步骤{0}执行失败,错误状态为”Y“,继续执行!" 
		        		  ,step.getFrmCase().getStepsId());
				step.getTestResult().add(exeComd.getFailedCause());
				step.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG,true);
				step.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG,false);
			}
			else{
	
				if (step.getStepType() == 1)
			    {
				    Logger.getLogger(ApplExecuteConstValues.logExecutName).
		              log(Level.SEVERE, "步骤{0}执行失败,错误状态为”N“,进入手动执行!" 
		        	      ,step.getFrmCase().getStepsId());
					step.getTestResult().add(exeComd.getFailedCause());

		            return false;	  
			    }
			    else if (step.getStepType() == 6)
			    {				
				    while(!flag){
				        Logger.getLogger(ApplExecuteConstValues.logExecutName).
		                  log(Level.SEVERE, "步骤{0}执行失败,错误状态为”R“" ,
		                		  step.getFrmCase().getStepsId());
						step.getTestResult().add("第"+RNum+"次"+exeComd.getFailedCause());
						if(isRunStop())
						{
				            return false;	  
						}
						Pause(1.0);
						flag = exeComd.execueteCommand(stepContent);	
						RNum++;
			         }
				     Logger.getLogger(ApplExecuteConstValues.logExecutName).
			          log(Level.INFO, "步骤执行成功，步骤ID:{0} ",step.getFrmCase().getStepsId());
				     step.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG,true);
				     step.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG,true);     	 
					 step.getTestResult().add("第"+RNum+"次"+exeComd.getFailedCause());

				
			    }
			    else if (step.getStepType() == 3)
			    {
				    while(!flag)
				    {	 
					    Logger.getLogger(ApplExecuteConstValues.logExecutName).
		                  log(Level.SEVERE, "步骤{0}执行失败,错误状态为”L“" ,
		                		  step.getFrmCase().getStepsId());			
						step.getTestResult().add("第"+LNum+"次"+exeComd.getFailedCause());
						Pause(1.0);
						flag = exeComd.execueteCommand(stepContent);
					    if(++LNum == 4)
					    {
							 step.getTestResult().add("第"+LNum+"次"+exeComd.getFailedCause());
					         return false;	  
					    }
				    }
				     Logger.getLogger(ApplExecuteConstValues.logExecutName).
			          log(Level.INFO, "步骤执行成功，步骤ID:{0} ",step.getFrmCase().getStepsId());
				     step.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG,true);
					 step.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG,true);     	 
					 step.getTestResult().add("第"+LNum+"次"+exeComd.getFailedCause());

			    }
			    else
			    {
				    // 写入日志
				    Logger.getLogger(ApplExecuteConstValues.logExecutName).
		              log(Level.INFO, "执行失败,无法判断原因 ," +
		          		       "步骤ID:{0} ",step.getFrmCase().getStepsId());

			     }						
			
			}
		}
		return true;
	}
	
    private void Pause(double time)
    {
        try
        {
            Thread.sleep((int)(1000*time));
        }
        catch (InterruptedException ex)
        {
        }
    }

	public boolean isRunStop() {
		return executeProcess.isStoprun();
	}

	public void setExecuteProcess(ApplExecuteProcessImpl executeProcess) {
		this.executeProcess = executeProcess;
	}

}
