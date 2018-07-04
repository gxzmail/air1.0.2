package sse.ngts.testrobot.factory;

import sse.ngts.testrobot.application.creatExecute.ApplCaseDetails.ApplCaseScript;
import sse.ngts.testrobot.application.creatExecute.ApplCaseDetails.ApplCaseScriptImpl;
import sse.ngts.testrobot.application.creatExecute.ApplCaseSteps.ApplCaseStepsScript;
import sse.ngts.testrobot.application.creatExecute.ApplCaseSteps.ApplCaseStepsScriptImpl;
import sse.ngts.testrobot.application.creatExecute.ApplCycle.ApplCycle;
import sse.ngts.testrobot.application.creatExecute.ApplCycle.ApplCycleImpl;

public class DAOFactory {

	public static ApplCycle getCycleInstance()
	{
		return new ApplCycleImpl();
	}
	
	public static ApplCaseStepsScript getApplCaseStepsInstance()
	{
		return new ApplCaseStepsScriptImpl();
	}
	public static ApplCaseScript getApplCaseScriptInstance()
	{
		return new ApplCaseScriptImpl();
	}
	
}