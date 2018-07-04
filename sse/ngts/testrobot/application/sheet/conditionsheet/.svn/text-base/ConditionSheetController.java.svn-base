package sse.ngts.testrobot.application.sheet.conditionsheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ActionController;
import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ApplExecuteController;
import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.application.functions.ApplStatusSave;
import sse.ngts.testrobot.application.interf.ApplReceiver;
import sse.ngts.testrobot.application.interf.ApplSender;
import sse.ngts.testrobot.application.interf.SheetController;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.ApplEvtQueue;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.exception.ApplFatalException;


public class ConditionSheetController
        implements SheetController, ApplReceiver, ApplSender
{
    private ActionController currentChooseActionController = null;
    private ActionController currentWorkingController = null;

    private int currentSelectedRow;
    private int[] currentSelectedRows;
    protected ConditionSheetScreen ui;
    private ActionScreen actionScreen ;
    protected ArrayList<ActionController> actionsController;
  
    private String SheetName;
    private HashMap preferredRowHeight;
    private Thread actionThread;
	private long costTime;
	
    private boolean isSuspend = false;
    private boolean isStop = false;
    private int stopRunStep = -1;
    private Boolean scrollAllowed = true;/*是否允许滚屏*/ 
    private ApplExecuteController  applExec;
    private ArrayList<String>  tradePhase ;
    private int  currentRunStepId;
    private int currentRunRow;
    private int stopRunRow = -1;
    private   Thread aThread;
    private boolean viewflag = false;
	public ConditionSheetController()
    {
        tradePhase = new ArrayList<String>();
        ui = new ConditionSheetScreen(this);    
        preferredRowHeight = new HashMap();
    }

    public ArrayList<String> getTradePhase() {
		return tradePhase;
	}


	public void setCurrentWorkingController( ActionController c)
    {
        currentWorkingController = c;
    }

    public ActionController getCurrentWorkingController()
    {
        return currentWorkingController;
    }



    public void setPreferredRowHeight(int row ,int height)
    {
        preferredRowHeight.put(row,height);
    }

    public int getPreferredRowHeight(int row)
    {
        Integer a = (Integer) preferredRowHeight.get(row);
        if (a != null)
        {
            return a;
        }
        return -1;
    }

    public ConditionSheetScreen getUI()
    {
        return ui;
    }


    public int getCurrentSelectedRow()
    {
        return currentSelectedRow;
    }


    /***************************************************************************
     * 获得当前Sheet的脚本用例的属性列表,用来存储到XML中
     **************************************************************************/
    public String getCurrentSheetInfo()
    {
        StringBuffer actionInfo = new StringBuffer("");

        for( int i = 0 ; i < actionsController.size() ; i++ )
        {
            ActionController c = actionsController.get(i);
            actionInfo.append(c.getCurrentScript());
            if( i!=actionsController.size()-1)
            {
                actionInfo.append(",");
            }
        }
        return actionInfo.toString();
    }

    private void updateCostTime()
    {
        for (ActionController c : actionsController)
        {
            this.costTime+= c.getCostTime();
        }
    }

    public ArrayList<ActionController> findControllersBySelectedRows()
    {
        ArrayList<ActionController> selectedControllers =
                new ArrayList<ActionController>();

        if (currentSelectedRows == null || currentSelectedRows.length == 0)
            return selectedControllers;

        for (int j : currentSelectedRows)
        {
            for (ActionController c : actionsController)
            {
                if (c.getViewRow()-1 == j)
                    selectedControllers.add(c);
            }
        }

        return selectedControllers;
    }

    /*
     * 通过选定的行来得到对应的控制器
     */
    public ActionController findControllerBySSelectedRow()
    {
        for( ActionController c : actionsController)
        {
            if(c.getViewRow() == getCurrentSelectedRow() )
            {
                return c;
            }
        }
        return null;
    }


    /**************************************************************************
     * 自动执行当前用例中的所有脚本,并保存脚本执行后的状态
     *************************************************************************/
    public void execAutoActions( )
    {        	  		
    	new Thread(){
    	    public void run()
    	    {
    	        applExec.execActions();
    	        ui.setFuncStatus(true);  
    	    }
        }.start();
    }
    
    
	/**************************************************************************
     * 从指定的地方开始执行脚本,并保存脚本执行后的状态
     *************************************************************************/
    public void execAutoActions(int stepId)
    {
    	
    	applExec.setCurrentStep(stepId);
    	execAutoActions();
    }

    
    /**************************************************************************
     * 重新开始执行，置所有步骤为未执行状态
     *************************************************************************/
    public void reStartExec(Boolean stopFlag)
    {
    	if(applExec == null)
    		return;

        ui.setButtonStartFlag(stopFlag);
        isSuspend = false;
        isStop = false;
        stopRunStep = -1;
        scrollAllowed = true;/*是否允许滚屏*/ 
        currentRunStepId = 0;
    	applExec.reStartExec();
    	ui.getResutTxt().setText("");
    	ui.repaint(); 
    	
    }
    

    /**************************************************************************
     * 单步执行
     *************************************************************************/
    public void sigleStepProcess()
    {
        new Thread(){
    	    public void run()
    	    {
    	    	applExec.sigleStepProcess();
    	        ui.setFuncStatus(true);
    	    }
    	}.start();  	
       	
    }
    
    
    /**************************************************************************
     * 执行选好的步骤
     *************************************************************************/
    public void executSelectStep(int stepId)
    {
    	currentRunStepId = stepId;
    	new Thread(){
    	    public void run()
    	    {
    	        applExec.executSelectStep(currentRunStepId);
    	        ui.setFuncStatus(true);
    	    }
    	}.start();
    }


	/*打开步骤详情对话框*/
    public void handleOpenAction(ApplEvt evt)
    {
       
    	if(aThread!=null && aThread.isAlive())
    		aThread.stop();
    	if (currentChooseActionController != null)
        {
        	if(actionScreen!=null)
        	    actionScreen.dispose();
        	
        }
       
        
        ArrayList<ActionController> c = findControllersBySelectedRows();

        if (c.size() != 0)
        {
            currentChooseActionController = c.get(0);
            
            actionScreen = new ActionScreen(currentChooseActionController);
            aThread = new Thread(actionScreen);
            aThread.start();
        }
    }
    
    public Boolean getScrollAllowed() {
		return scrollAllowed;
	}


	public void setScrollAllowed(Boolean scrollAllowed) {
		this.scrollAllowed = scrollAllowed;
	}


	public int getStopRunStep() {
		return stopRunStep;
	}


	public void setStopRunStep(int stopRunStep) {		
		this.stopRunStep = stopRunStep;
		if(stopRunStep < 0)
		{
			this.stopRunRow = -1;
		}
		else
		{
		    this.stopRunRow = actionsController.get(stopRunStep).getViewRow();
		}
	    sendNotifyEvent(ApplEvt.EVT_ID_SETSTOPID_ACTION);	
	}
	
    public boolean isSuspend() {

		return isSuspend;
	}


	public void setSuspend(boolean isSuspend) {
		
		this.isSuspend = isSuspend;
		sendNotifyEvent(ApplEvt.EVT_ID_PAUSE_SCRIPT);	   
	}


	public boolean isStop() {
		return isStop;
	}

	
    
	public void setStop(boolean isStop) {	
		this.isStop = isStop;
		sendNotifyEvent(ApplEvt.EVT_ID_STOP_ACTION);
	}



	/**************************************************************************
     * 根据视图筛选来重新生成视图
     * ***********************************************************************/
    public void filterViewByExecDateAndPhaseAndCase( String date , String phase,String caseId,String result ,String stepType)
    {
        ui.updateView();
        preferredRowHeight.clear();

        int j = 1;

        for (ActionController c : actionsController)
        {
            c.setFilterViewShow(false);
            c.setViewRow(-1);
        }

        for (int i = 0 ; i < actionsController.size() ; i++ )
        {
            ActionController c = actionsController.get(i);
            ApplExecutCase script = c.getCurrentScript();
            if ((script.getFrmCase().getTestDate().trim().equals(date) || date.equals("选择日期")) &&
                (script.getFrmCase().getTestPhase().trim().equals(phase) || phase.equals("选择时段"))&&
                (script.getFrmCase().getScriptId().indexOf(caseId)!=-1 || caseId.equals("输入用例ID"))&&
                (script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&stepType.equals("自动执行")||
                !script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&stepType.equals("手动执行")||stepType.equals("选择类型")))
            {
                
            	if((result.equals(ApplExecuteConstValues.result1)&&!script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))||
            		(result.equals(ApplExecuteConstValues.result2)&&script.getProcessStatus() == ApplExecutCase.PROCESS_STATUS_RUN)||
            		(result.equals(ApplExecuteConstValues.result3)&&script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG) &&
            	            script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
            	            script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
            	            script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
            	            (!script.getMannul()||script.getMannul()&&!script.isFaileflag()))||
            	    (result.equals(ApplExecuteConstValues.result7)&&script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG) &&
                    	            script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
                    	            script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
                    	            (!script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)||
                    	            script.getMannul()&&script.isFaileflag()))||
            	    (result.equals(ApplExecuteConstValues.result4)&&
            	    		script.getAttribute(ApplExecutCase.ATTR_FINISH_FLAG) &&
            	            !script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
            	            script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
            	            script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
            	            !script.getMannul())||
                    (result.equals(ApplExecuteConstValues.result5)&&
                    		!script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
                    		script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
                    		script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
            	            script.getMannul())||
                    (result.equals(ApplExecuteConstValues.result6)&&
                    		!script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
                    		!script.getAttribute(ApplExecutCase.ATTR_SKIP_FLAG)&&
                    		!script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
                    		script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))||
                    result.equals("选择结果"))
            	{
            	    c.setFilterViewShow(true);
                    c.setViewRow(j);
                    ui.addScriptRowUI(c);
                    ui.changeCostTime(c.getViewRow()-1,c.getCostTime());
                    j++;
            	}
            }
        }
    }
    public boolean handleLoadSheet(String fileName)
    {    
        this.SheetName  = fileName;
    	try{
    		applExec = new ApplExecuteController(this,fileName);
        	actionsController = applExec.getActionsController();
        	tradePhase  = applExec.getTradePhase();

    	}
        catch(ApplFatalException ex)
        {
        	return false;
        }
    	handleChangPhase(null);
        ui.updateView();	
        for (int i = 0; i < actionsController.size(); i++)
        {
            if(ui!=null)
            {
                //显示自动脚本的参数
                ui.addScriptRowUI(actionsController.get(i));
            }
           
        }
        return true;
    }
   
    
    public void addCostTime( long time )
    {
        this.costTime+=time;
    }


    public boolean isApplSheetWorking()
    {
        for (ActionController c : actionsController)
        {
            if (c.isCurrentControllerWorking())
            {
                return true;
            }
        }
        return false;
    }


    
    public void deleSheetController()
    {
         this.getUI().setFuncStatus(true);
         this.setViewflag(false);
    }
    

    public int getCurrentViewSize()
    {
        if(actionsController ==null)
        	return 0;
    	return actionsController.size();
    }


	public synchronized void saveSheetInfo() {
		
		new Thread()
		{
		    public void run()
		    {			
			    ApplStatusSave.getinstance().writStatus(actionsController);			
		    }
		}.start();
	}

	public String getSheetName() {
		return this.SheetName;
	}

	public void clearSheetInfo()
	{
		ApplStatusSave.getinstance().clear();
	}
	
	public void loadSheetInfo() {
		File file = new File(ApplExecuteConstValues.outStatusFileName);
		try{
			if(!file.exists())
			{
				ApplFileProcess.createDir(ApplExecuteConstValues.outStatusFileName, ApplExecuteConstValues.APPL_File);
				file.createNewFile();
				return;
			}
			else 
			{
				ArrayList<String> status=new  ArrayList<String>();
				status = ApplStatusSave.getinstance().getStatus();
				
				ArrayList<String> failed = new ArrayList<String>();
				ArrayList<String> success = new ArrayList<String>();

				for(int i = 0; i<status.size();i++)
				{
					StringTokenizer  t= new  StringTokenizer(status.get(i),"|");
					String id = t.nextToken();	
					String result  = t.nextToken();
					if(result.equals("1"))
						success.add(id);
					else
						failed.add(id);
				}
				for(int i = 0;i<actionsController.size();i++)
				{
					String id = String.valueOf(i);
					ApplExecutCase  c = actionsController.get(i).getCurrentScript();
                    String id1 = String.valueOf((Integer.valueOf(id)+1));
					if(success.contains(id1))
					{
						c.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, true);
						c.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG, true);
						c.setAttribute(ApplExecutCase.ATTR_REFF_FLAG, true);
				    }
					else if(failed.contains(id1))
					{
						c.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG, false);
						c.setAttribute(ApplExecutCase.ATTR_REFF_FLAG, true);
						if(c.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&!c.getMannul())
						{
							c.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, true);
						}
				    }
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    public void testResultFileCreate(String fileName)
    {
    	applExec.testResultFileCreate(fileName);
    }
    
    public void handleTableScroll(ApplEvt evt)
    {
    	Object[] c = (Object[]) evt.getArgs();
    	currentRunRow = (Integer)c[0];
    	
    	ui.tableScroll(currentRunRow, scrollAllowed);
    }
    
    public void handleAppendTxt(ApplEvt evt)
    {
    	Object[] c = (Object[]) evt.getArgs();
    	currentRunStepId = (Integer)c[0];
    	currentRunRow = actionsController.get(currentRunStepId).getViewRow();

    	ui.changeScriptStep((Integer)c[0], null);
    	ui.getResutTxt().append((String)c[1]);
    	ui.getResutTxt().append("\n");
    }
    public void handleChangeStopRunStep(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();

    	stopRunRow = (Integer)c[2];

    }
    public synchronized void changeSelectedRow(int row)
    {
        currentSelectedRow = row;
    }

    public synchronized void changeSelectedRows(int rows[])
    {
        this.currentSelectedRows = rows;
    }

    public void handleChangPhase(ApplEvt evt)
    {     
        ui.changePhase(tradePhase.toArray());    
    }
    
    public void handleWarnNotify(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();
        addCostTime((Long)c[1]); 
        ui.changeCostTime( (Integer)c[0] - 1, (Long)c[1] );
        ui.changeScriptStatus((Integer)c[0] - 1,(String)c[3]);
        ui.changeTotalCostTime(costTime);
        ui.changeCurrentStatus();
        ui.changeRunTime((Integer)c[0] - 1,(Long)c[5],(Long)c[6]);
        String id = (String)c[4];
        saveSheetInfo();
    }

    public void handleActiveNotify(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();
        
        ui.changeScriptStatus((Integer)c[0] - 1,(String)c[3]);
        ui.changeTotalCostTime(costTime);

    }
    
    public void changeStepStatus(int type)
    {
	    
    	if(type == 2)
    	{
    	    for( ActionController a : findControllersBySelectedRows())
		    {
                if(a.isCurrentControllerWorking())
                {
             	   JOptionPane.showMessageDialog(null, "步骤"+a.getViewRow()+"仍在执行中，不能设置该步骤的状态！", "warnning",JOptionPane.INFORMATION_MESSAGE);
             	   continue;
                }
    	    	ApplExecutCase script = a.getCurrentScript();
	    		script.setAttribute(ApplExecutCase.ATTR_REFF_FLAG, true);
	    		script.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG, false);
	    		if(!script.getMannul()&&script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG))
	    		{
  		    		script.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, true);
	    		}
	    	    else
	    	    {
 		    		script.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, false);
    	    	}
		    	script.setReuslt();
    			ui.changeScriptStatus(a.getViewRow()-1, "");

	    	}
    	}
    	else if(type == 1)
    	{
    		for( ActionController a : findControllersBySelectedRows())
    		{
                if(a.isCurrentControllerWorking())
                {
             	   JOptionPane.showMessageDialog(null, "步骤"+a.getViewRow()+"仍在执行中，不能设置该步骤的状态！", "warnning",JOptionPane.INFORMATION_MESSAGE);
             	   continue;
                }
    			ApplExecutCase script = a.getCurrentScript();
    			script.setAttribute(ApplExecutCase.ATTR_REFF_FLAG, true);
    			script.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG, true);
    			script.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, true);
    			script.setReuslt();
    			ui.changeScriptStatus(a.getViewRow()-1, "");
	    	}
    		
    	}
    	else if(type == 0)
    	{
    		 for( ActionController a :findControllersBySelectedRows())
         	 {
                 if(a.isCurrentControllerWorking())
                 {
              	     JOptionPane.showMessageDialog(null, "步骤"+a.getViewRow()+"仍在执行中，不能设置该步骤的状态！", "warnning",JOptionPane.INFORMATION_MESSAGE);
              	     continue;
                 }
    			 ApplExecutCase script = a.getCurrentScript();
         		 script.setAttribute(ApplExecutCase.ATTR_REFF_FLAG, false);
         		 script.setAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG, false);
	    		 script.setAttribute(ApplExecutCase.ATTR_FINISH_FLAG, false);
         		 script.setReuslt();
     			ui.changeScriptStatus(a.getViewRow()-1, "未执行");

		        
         	 }
    		
    	}
    	else
    		return;
	 saveSheetInfo();
        ui.changeCurrentStatus();

    }
    public void handleStopNotify(ApplEvt evt)
    {
        Object[] c = (Object[]) evt.getArgs();
        addCostTime((Long)c[1]); 
        ui.changeScriptStatus((Integer)c[0] - 1,(String)c[3]);
        ui.changeCostTime( (Integer)c[0] - 1, (Long)c[1] );
        ui.changeTotalCostTime(costTime);
        
        ui.changeCurrentStatus();
        ui.changeRunTime((Integer)c[0] - 1,(Long)c[5],(Long)c[6]);
        String id = (String)c[4];
        saveSheetInfo();
    }

    public String getStatus()
    {
    	int wrongNum = 0;
    	int hasRunNum = 0;
    	int MannulNum = 0;
    	for(int i =0; i<actionsController.size();i++)
    	{
    		ApplExecutCase  c = actionsController.get(i).getCurrentScript();
    		if(c.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&!(c.getProcessStatus()==ApplExecutCase.PROCESS_STATUS_RUN ))
    			hasRunNum++;
    		if(c.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
    		  c.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
    		  (!c.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)||c.getMannul()))
    		    MannulNum++;
    		if(c.getAttribute(ApplExecutCase.ATTR_REFF_FLAG)&&
    	    	!c.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&!(c.getProcessStatus()==ApplExecutCase.PROCESS_STATUS_RUN ))
    			wrongNum++;    		
    	}
    	if(hasRunNum == actionsController.size())
    	{
    		testResultFileCreate(ApplConfig.getInstance().getExectueOutPutStat());
    	}
    	return wrongNum+"/"+hasRunNum+"/"+MannulNum+"/"+actionsController.size();
    }
    
    
    private void sendNotifyEvent(String messgeID)
    {      
    	Object params[] =
                {
    			isSuspend,isStop,stopRunStep};
        ApplEvt evt1 = new ApplEvt(messgeID, params);
        evt1.setReceiver(applExec);      
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

	public int getCurrentRunStepId() {
		return currentRunStepId;
	}


	public int getCurrentRunRow() {
		return currentRunRow;
	}


	public int getStopRunRow() {
		return stopRunRow;
	}

	public boolean is() {
		return viewflag;
	}

	public void setViewflag(boolean viewflag) {
		this.viewflag = viewflag;
	}

	public boolean isViewflag() {
		return false;
	}


}
