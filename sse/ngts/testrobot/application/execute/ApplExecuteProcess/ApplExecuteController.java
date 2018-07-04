package sse.ngts.testrobot.application.execute.ApplExecuteProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.creatExecute.ApplCreateExeFile.ApplExeScriptCreate;
import sse.ngts.testrobot.application.interf.ApplReceiver;
import sse.ngts.testrobot.application.interf.ApplSender;
import sse.ngts.testrobot.application.sheet.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.ApplEvtQueue;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.WindowHandler;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
import sse.ngts.testrobot.exception.ApplFatalException;

/***from doc
	表格自动执行实例

***/


public class ApplExecuteController 
implements ApplSender, ApplReceiver{
	private ConditionSheetController sheetController;
	private String exeTxt; 	
	protected ArrayList<ActionController> actionsController;
	private ArrayList<String>  tradePhase ;
    private String fileName;
	private int currentStep = 0;
    private int stopRunStep = -1;
    private boolean isSuspend = false;
    private boolean isStop = false;
    private ArrayList<ApplFrmwkCase> framCase;
	public ApplExecuteController(ConditionSheetController sheetController,String fileName)
	{
		actionsController = new ArrayList<ActionController>();
		tradePhase = new ArrayList<String>();
		this.sheetController = sheetController;	
		fileName = sheetController.getSheetName();		
	    if(!handleLoadSheet())
	    	throw new ApplFatalException(null,null);
	}
	

    public Boolean readScriptSheet()
    {
        String fileDirc =  System.getProperty("user.dir");		        			        
		
        if(!ApplExeScriptCreate.hasCreat())
        {
            WindowHandler windowHandler = new WindowHandler();
		    windowHandler.setLevel(Level.ALL);
		    Logger.getLogger(ApplConstValues.logName).addHandler(windowHandler);
        }
        try{
        	framCase = ApplExeScriptCreate.getInstance(fileDirc+"\\"+ApplConstValues.configFileName).getSteps();
       
        }
        catch(ApplFatalException ex)
        {
        	return false;
        }
        return true;
     }
    
    /**************************************************************************
     * 读取ApplFrmwkCase数组中的对象,并对用一个ActionController去封装
     * node : 文件接点
     * needLoadActions 是否需要加载用例的脚本
     *************************************************************************/
    public boolean handleLoadSheet()
    {     
    	actionsController.clear();
        /*读取手册*/
        if(!readScriptSheet())
        	return false;
        /*更新界面上的交易时段*/
        setTradePhase(framCase);  
        for (int i = 0; i < framCase.size(); i++)
        {
        	ApplExecutCase script = new ApplExecutCase(framCase.get(i));

			
        	ActionController actionController = 
            	              new  ActionController(sheetController);

            actionController.initScript(script);
            actionController.setViewRow(i + 1);
            actionsController.add(actionController);
        }
        File file =new File(ApplConfig.getInstance().getExectueOutPutResult());
        if(file.exists()&&file.isFile())
        	file.delete();
        return true;
    }
	
	  /**************************************************************************
     * 执行单个步骤
     *************************************************************************/
    public boolean handleStep(ActionController c)
    {   	 
    	 ApplExecutCase script = c.getCurrentScript();
       	 if (!script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG))
         {
       		
       		exeTxt = "开始执行步骤"+script.getFrmCase().getStepsId()+
        				",脚本ID为："+script.getFrmCase().getScriptId();
       		sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            c.handleStartScript(null);
            while (c.isCurrentControllerWorking())
            {
               Pause(2.0);
            }
            if (!script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG))
            {
                exeTxt = "需要手动完成!"; 
                sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
           	    ApplExectueStat.getInstance().execResultWrite(ApplConfig.getInstance().getExectueOutPutResult(),c);
           	    return  false;
            }
            if(script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG))
            {
            	exeTxt = "步骤"+script.getFrmCase().getStepsId()+":执行成功";                       
            }
            else 
            {
            	exeTxt = "步骤"+script.getFrmCase().getStepsId()+":执行失败";
            	exeTxt = "错误原因："+script.getTestResult().toString();
            }   
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
       	    ApplExectueStat.getInstance().execResultWrite(ApplConfig.getInstance().getExectueOutPutResult(), c);
        }
       	return true;
    }
    
    
    
    /**************************************************************************
     * 自动执行当前用例中的所有脚本,并保存脚本执行后的状态
     *************************************************************************/
    public void execActions( )
    {
  
    	if(currentStep == -1 ||currentStep>actionsController.size()-1)
    		return;
    	for (int i = currentStep; i < actionsController.size(); i++)
        {
            ActionController c = actionsController.get(i);
            
            if(isSuspend||isStop) 
            {            	                    
                //if(stopRunStep == currentStep)
               // {         
            	//    stopRunStep = -1;
                //    sendNotifyEvent(ApplEvt.EVT_ID_CHANGE_STOPRUNSTEP);
               // }
                return; 
            }           	
                       
            ApplExecutCase script = c.getCurrentScript();
          
            if(script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG)&&
            		script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
            {
            	if(currentStep == actionsController.size()-1)
            		break;
            	else
            	    continue;            
            }
            
            currentStep = i;    
            if(!handleStep(c))
            	return; 
            if(stopRunStep == currentStep)
            {         
        	    stopRunStep = -1;
                sendNotifyEvent(ApplEvt.EVT_ID_CHANGE_STOPRUNSTEP);
                return; 
            }

        }
    }
    
    
    public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}


	/**************************************************************************
     * 重新开始执行，置所有步骤为未执行状态
     *************************************************************************/
    public void reStartExec()
    {
    	for(int i = 0;i<actionsController.size();i++)
    	{
    		 ApplExecutCase script = actionsController.get(i).getCurrentScript();
    		 script.setAttribute(ApplExecutCase.ATTR_REFF_FLAG, false);
    		 script.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, false);
    		 script.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG, false); 
    		 script.getTestResult().clear();
    		 script.setReuslt();
    		 script.setFaileflag(false);
             

    	}
		 currentStep = 0;
		 stopRunStep = -1;
		 isSuspend = false;
		 isStop = false;
    	
    }
    

    /**************************************************************************
     * 单步执行
     *************************************************************************/
    public void sigleStepProcess()
    {
    	if(currentStep <0 ||currentStep>actionsController.size()-1)
    		return;
    	ActionController currentAc = actionsController.get(currentStep);           
        ApplExecutCase script =  currentAc.getCurrentScript();  
       	
       	while(script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG)&&
       			script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
       	{
       		if(currentStep ==actionsController.size()-1)
       		{
       			return;
       		}
       		if(currentStep+1<actionsController.size())
       		{
       			currentStep++;
       			currentAc = actionsController.get(currentStep);           
       	        script =  currentAc.getCurrentScript();  
       		}

       	}
        handleStep(currentAc);      	
    }
    
    
    /**************************************************************************
     * 执行选好的步骤
     *************************************************************************/
    public void executSelectStep(int stepId)
    {
    	if(stepId == -1 ||stepId>actionsController.size()-1)
    		return;
    	ActionController currentAc = actionsController.get(stepId);           
        ApplExecutCase script =  currentAc.getCurrentScript();  
     
       	if(script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG)&&
       			script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
       	{
       		exeTxt =  "步骤"+script.getFrmCase().getStepsId()+"已经执行完成!";
            sendNotifyEvent(ApplEvt.EVT_ID_APPENDTXT_ACTION);
            return;  
       	}
       
        handleStep(currentAc); 
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
    

	
	public void setTradePhase(ArrayList<ApplFrmwkCase> steps) {
		Iterator<ApplFrmwkCase> c = steps.iterator();
		tradePhase.add("选择时段");
		while(c.hasNext())
		{
			String phase = c.next().getTestPhase();
			if(tradePhase.contains(phase))
				continue;
			tradePhase.add(phase);
		}

	}
	
    
    public void testResultFileCreate(String fileName)
    {
    	ApplExectueStat executeStat = ApplExectueStat.getInstance();
    	executeStat.statProcess(ApplConfig.getInstance().getExecuteFile(),fileName,actionsController);	
    }
	
    
    private void sendNotifyEvent(String messgeID)
    {      
    	Object params[] =
                {
    			currentStep,exeTxt,stopRunStep};
        ApplEvt evt1 = new ApplEvt(messgeID, params);
        evt1.setReceiver(sheetController);      
        postEvt(evt1);
      
    }
    

    public void postEvt(ApplEvt evt)
    {
        evt.setSender(this);
        if (evt.getReceiver() == null)
        {
            evt.setReceiver(this);
        }
        ApplEvtQueue.getInstance().post(evt);
    }
    
    public void handlePauseScript(ApplEvt evt)
    {
    	Object[] c = (Object[]) evt.getArgs();
    	isSuspend = (Boolean)c[0] ;
        
    }
    public void handleStopAction(ApplEvt evt)
    {
    	Object[] c = (Object[]) evt.getArgs();
    	isStop = (Boolean)c[1] ;
        
    }
    public void handleStopId(ApplEvt evt)
    {
    	Object[] c = (Object[]) evt.getArgs();
    	stopRunStep = (Integer)c[2] ;

    }  
    
	public ArrayList<String> getTradePhase() {
		return tradePhase;
	}		

	public ArrayList<ActionController> getActionsController() {
		return actionsController;
	}


}
