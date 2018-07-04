package sse.ngts.testrobot.engine.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;

public class ApplFunctions {
	
	public static String strGet(ApplExecutCase step)
	{
		String outStr = null;
		if(step.getFrmCase().getTestContent().trim().equalsIgnoreCase("Manual"))
		{
			outStr = "    手动执行如下步骤，执行完后单击“确认”完成！！";
			Logger.getLogger(ApplExecuteConstValues.logExecutName).
	            log(Level.INFO, "手动执行步骤，步骤ID:{0} ",step.getFrmCase().getStepsId());
		}
		else{
		    switch(step.getFrmCase().getTestStatus().trim().codePointAt(0))
		    {
		        case 'l':
		        case 'L':
		    	    outStr = "    步骤执行失败，请单击“确认”重新执行！！";
		         	break;
		        case 'r':
		        case 'R':
		    	    outStr = "    步骤执行失败，请手动修复配置环境，单击“确认”后系统重新执行该步骤！！";
		    	    break;
		        case 'N':
		        case 'n':
		    	    outStr = "    步骤执行失败，请手动执行该步骤，单击“确认”继续执行下一步骤！！";
		    	    break;
		    }
		    outStr = outStr +"(当前错误状态为"+step.getFrmCase().getTestStatus()+")";
		    Logger.getLogger(ApplExecuteConstValues.logExecutName).
	          log(Level.SEVERE, "步骤执行失败 ，并且需要手动执行，步骤ID:{0} ",step.getFrmCase().getStepsId());
		}
		return outStr;
	}

}
