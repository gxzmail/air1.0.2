package sse.ngts.testrobot.factory;

import sse.ngts.testrobot.application.execute.ApplAutoExecute.ApplAutoExecute;
import sse.ngts.testrobot.application.execute.ApplAutoExecute.ApplAutoExecuteImpl;
import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ApplExecuteProcess;
import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ApplExecuteProcessImpl;

public class EXECUFactory {

	
	public static ApplAutoExecute getApplAutoExecuteInstance()
	{
		return new ApplAutoExecuteImpl();
	}

	public static ApplExecuteProcess getApplExecuteProcess()
	{
		return new ApplExecuteProcessImpl();
	}
	
}
