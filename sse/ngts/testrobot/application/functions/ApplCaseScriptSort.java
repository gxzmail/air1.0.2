package sse.ngts.testrobot.application.functions;

import java.util.Comparator;

import sse.ngts.testrobot.engine.unit.ApplCase;

public class ApplCaseScriptSort implements Comparator<ApplCase>{
	public int compare( ApplCase obj1, ApplCase obj2)
	{
	
		if(obj1.getCaseId().compareToIgnoreCase(obj2.getCaseId())>=1)
			return 1;
		else 
			return 0;
		
	}

}
