package sse.ngts.testrobot.application.execute.ApplExecuteProcess;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import sse.ngts.testrobot.application.interf.ApplReceiver;
import sse.ngts.testrobot.application.interf.ApplSender;
import sse.ngts.testrobot.application.sheet.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.ApplEvtQueue;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.exception.ApplPauseException;
import sse.ngts.testrobot.factory.EXECUFactory;


public class ActionController
        implements ApplSender, ApplReceiver
{
    private ApplExecutCase currentScript;

    private ConditionSheetController sheetController;

    private boolean filterViewShow = true;
    private boolean isCurrentControllerWorking = false;

    private long beginTime;
    private long endTime;
    private long startTime = 0;
	private int viewRow;
    private static Color shingColor = Color.yellow;

    private long cumulatedExecTime = 0;

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "HHmmss");
    {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }


    public ActionController( ConditionSheetController c)
    {
        this.sheetController = c;  
        
    }


    /* 设置脚本所属于的用例表格 */
    public void setApplSheetUIController( ConditionSheetController c)
    {
        this.sheetController = c;
    }
    /* 获得脚本所属于的用例表格 */
    public ConditionSheetController getApplSheetUIController()
    {
        return sheetController;
    }


    /**/
    public void setViewRow(int viewRow)
    {
        this.viewRow = viewRow;
    }

    public int getViewRow()
    {
        return viewRow;
    }

    public void setFilterViewShow(boolean t )
    {
        filterViewShow = t;
    }
    public boolean getFilterViewShow()
    {
        return filterViewShow;
    }

    
    public void initScript(ApplExecutCase script)
    {
        currentScript = script;
      
       
    }

    public long getCostTime()
    {
        return cumulatedExecTime;
    }
    
    public void setCostTime(long time)
    {
    	cumulatedExecTime = time;
    }

     public ApplExecutCase getCurrentScript()
     {
        return currentScript;
     }

   
    public synchronized boolean isCurrentControllerWorking()
    {
        return isCurrentControllerWorking;
    }
    
    public void handleStartScript(ApplEvt evt)
    {      
    	
    	if (isCurrentControllerWorking)
        {
    	    return;
        }
        isCurrentControllerWorking = true;
    	sendNotifyEvent(ApplEvt.EVT_ID_ACTIVE_NOTIFY); 
   		sendNotifyEvent(ApplEvt.EVT_ID_SCROLL_ACTION);
   		currentScript.setProcessStatus(ApplExecutCase.PROCESS_STATUS_RUN);
	    playScriptCore();
    }
    
    
    private void sendNotifyEvent(String messgeID)
    {      
    	if(viewRow <= 0)
    		return;
    	String result = currentScript.getTestResultDescr();
    	String id = this.currentScript.getFrmCase().getStepsId();
    	//System.out.println(id);
    	Object params[] =
                {
                viewRow,cumulatedExecTime, isCurrentControllerWorking,result,id,startTime,endTime};
    	
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
    
    private void playScriptCore()
    {
        currentScript.setProcessStatus(ApplExecutCase.PROCESS_STATUS_RUN);  	
    	sheetController.setCurrentWorkingController(this);
        beginTime = System.currentTimeMillis();
        if(startTime== 0)
        {
        	startTime = System.currentTimeMillis();
        }
        try
        {        	 
        	ApplExecuteProcess executProcess = EXECUFactory.getApplExecuteProcess();        		
        	executProcess.setActioncontroller(this);  
        	executProcess.stepProcess(currentScript);
            currentScript.setProcessStatus(ApplExecutCase.PROCESS_STATUS_STOP);
            endTime = System.currentTimeMillis();
            this.cumulatedExecTime += (endTime - beginTime);
            getApplSheetUIController().addCostTime(endTime - beginTime);
            sendNotifyEvent(ApplEvt.EVT_ID_STOP_NOTIFY);

        }
        catch (ApplPauseException ex)
        {
            endTime = System.currentTimeMillis();
            this.cumulatedExecTime += (endTime - beginTime);
            getApplSheetUIController().addCostTime(endTime - beginTime);            
            ex.printStackTrace();
            currentScript.setProcessStatus(ApplExecutCase.PROCESS_STATUS_STOP);
            sendNotifyEvent(ApplEvt.EVT_ID_WARN_NOTIFY);
        }
        sheetController.setCurrentWorkingController(null);
        isCurrentControllerWorking = false;
    }
    
    public void changRowTime()
    {
    	 endTime = System.currentTimeMillis();
         this.cumulatedExecTime += (endTime - beginTime);
         getApplSheetUIController().addCostTime(endTime - beginTime);
         sendNotifyEvent(ApplEvt.EVT_ID_STOP_NOTIFY);
    }

	public boolean isStopRun() {
		return sheetController.isSuspend();
	}


	public void setCurrentControllerWorking(boolean isCurrentControllerWorking) {
		this.isCurrentControllerWorking = isCurrentControllerWorking;
	} 
    public long getBeginTime() {
		return beginTime;
	}


}
