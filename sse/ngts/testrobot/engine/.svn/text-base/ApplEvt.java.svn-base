package sse.ngts.testrobot.engine;

import java.lang.reflect.Method;

import sse.ngts.testrobot.application.interf.ApplReceiver;
import sse.ngts.testrobot.application.interf.ApplSender;


public class ApplEvt
{

    public static final String EVT_ID_OPEN_SCRIPT = "OpenScript";

    public static final String EVT_ID_START_SCRIPT = "StartScript";

    public static final String EVT_ID_CONTINUE_SCRIPT = "ContinueScript";

    public static final String EVT_ID_PAUSE_SCRIPT = "PauseScript";

    public static final String EVT_ID_OPEN_ACTION = "OpenAction";
    public static final String EVT_ID_STOP_ACTION = "StopAction";
    public static final String EVT_ID_LOAD_ACTION = "LoadScript";

    public static final String EVT_ID_ACTIVE_NOTIFY = "ActiveNotify";

    public static final String EVT_ID_STOP_NOTIFY = "StopNotify";
    public static final String EVT_ID_WARN_NOTIFY = "WarnNotify";

    public static final String EVT_ID_PAUSE_ACTION = "PauseAction";

    public static final String EVT_ID_SCROLL_ACTION = "TableScroll";
   
    public static final String EVT_ID_APPENDTXT_ACTION = "AppendTxt";
    
    public static final String EVT_ID_CHANGEPHASE_ACTION = "ChangPhase";
    public static final String EVT_ID_SETSTOPID_ACTION = "StopId";
    public static final String EVT_ID_CHANGE_STOPRUNSTEP = "ChangeStopRunStep";
    private String handleMethod;
    private Object args;
    private ApplSender sender;
    private ApplReceiver receiver;
    public ApplEvt(String handleMethod)
    {
        this.handleMethod = handleMethod;
    }

    public ApplEvt(String handleMethod, Object args)
    {
        this.handleMethod = handleMethod;
        this.args = args;
    }

    public void setSender(ApplSender sender)
    {
        this.sender = sender;
    }

    public void setReceiver(ApplReceiver receiver)
    {
        this.receiver = receiver;
    }

    public String getHandleMethod()
    {
        return handleMethod;
    }

    public Object getArgs()
    {
        return args;
    }

    public ApplSender getSender()
    {
        return sender;
    }

    public ApplReceiver getReceiver()
    {
        return receiver;
    }

    public void dispatch()
    {
        String methodName = "handle" + handleMethod;

        try
        {
            Method t = receiver.getClass().getMethod(methodName, ApplEvt.class);
            t.invoke(receiver, this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


}
