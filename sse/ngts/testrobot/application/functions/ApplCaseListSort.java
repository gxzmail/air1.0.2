package sse.ngts.testrobot.application.functions;

import java.util.Comparator;

public class ApplCaseListSort implements Comparator<String>{
	public int compare( String obj1, String obj2)
	{
	
		if(obj1.compareToIgnoreCase(obj2)>=1)
			return 1;
		else 
			return 0;
		
	}

}
