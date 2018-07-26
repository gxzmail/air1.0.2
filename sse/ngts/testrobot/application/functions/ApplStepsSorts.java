/**
 *Created on: 2018-07-24
 *      Author: xzguo
 *  Time		SIR MAKR    		DESCRIPTION
 * 2018-07-24	sir 180724		    修复排序过程中乱序的问题
 *
 */



package sse.ngts.testrobot.application.functions;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;

public class ApplStepsSorts implements Comparator<ApplFrmwkCase>{
	private Hashtable<String,Integer> TradePhase = new Hashtable<String,Integer>();
//***
	private String frmId;
//***


	public ApplStepsSorts(Hashtable<String,Integer> tradePhase)
	{
		this.TradePhase = tradePhase;
	}

	public ApplStepsSorts(Hashtable<String,Integer> tradePhase, String frmId)
	{
		this.TradePhase = tradePhase;
		this.frmId = frmId;
	}


	/**
	 * 函数功能：按照交易日、交易阶段、优先级排序
	 * 函数输入：
	 * Object obj1   －－要比较的对象1
	 * Object obj2   －－要比较的对象2
	 * 返回值：
	 *   int
	 */
	public int compare( ApplFrmwkCase obj1, ApplFrmwkCase obj2)
	{


		ApplFrmwkCase step1 = obj1;
		ApplFrmwkCase step2 = obj2;

		int temp1 = compareTradDate(step1,step2); //***xzguo 比较交易日
		int temp2 = comparePhase(step1,step2);   //***xzguo 比较交易时段，其中借助TradePhase数组。该部分可以优化
		int temp3 = comparePriorty(step1.getTestPrior(),step2.getTestPrior()); //xzguo 比较优先级
		if(temp1>0)
			return 1;
		else if (temp1 < 0)
			return -1;

		else if(temp1==0)
		{
			if(temp2>0)
				return 1;
			else if (temp2 < 0)
				return -1;

			else if(temp2==0)
			{
				if(temp3 > 0)
				    return 1;
				else if (temp3 < 0)
					return -1;
           	 /*2012-08-22 modified by wuli start */

				else if(temp3== 0)/*&&step1.getScriptId().compareTo(step2.getScriptId())>0)*/
			    {
			    	String stepid1 = step1.getStepsId();
			    	String stepid2 = step2.getStepsId();

			    	String scenid1 = ApplFileProcess.getStringByToken(1,"_",step1.getScriptId())+"_"
			    	                  +ApplFileProcess.getStringByToken(2,"_",step1.getScriptId())+"_"
			    	                   +ApplFileProcess.getStringByToken(3,"_",step1.getScriptId());
			    	String scenid2 = ApplFileProcess.getStringByToken(1,"_",step2.getScriptId())+"_"
	                  +ApplFileProcess.getStringByToken(2,"_",step2.getScriptId())+"_"
	                   +ApplFileProcess.getStringByToken(3,"_",step2.getScriptId());
			    	if(scenid1.compareTo(scenid2)>0)
			    		return 1;
			    	else if (scenid1.compareTo(scenid2) < 0)
			    		return -1;
			    	else if (scenid1.compareTo(scenid2) == 0)
			    	{
			    		//***xzguo Integer.valueOf(stepid1)-Integer.valueOf(stepid2) > 0 ? 1: -1; 报错，可改为
			    		//***xzguo int val = Integer.valueOf(stepid1)-Integer.valueOf(stepid2) > 0 ? 1: -1;
			    		return (Integer.valueOf(stepid1)-Integer.valueOf(stepid2) > 0 ? 1: -1);
			    	}

			//    	else if(scenid1.compareTo(scenid2) == 0 &&(Integer.valueOf(stepid1)-Integer.valueOf(stepid2))>0)
			//    			return 1;
			    }
	           	 /*2012-08-22 modified by wuli end */

			}

		}
		return 0;
	}

	public int compareTradDate(ApplFrmwkCase a,ApplFrmwkCase b)
	{
		/***xzguo
		* substring(1,a.getTestDate().length())不包含首字符T
		*/
		String a1 = a.getTestDate().trim().substring(1,a.getTestDate().length());
		String b1 = b.getTestDate().trim().substring(1,b.getTestDate().length());
		return Integer.valueOf(a1)-Integer.valueOf(b1);
	}

	public int comparePhase(ApplFrmwkCase a1,ApplFrmwkCase b1)
	{
		String a = a1.getTestPhase();
		String b = b1.getTestPhase();
		if(!TradePhase.containsKey(a)||!TradePhase.containsKey(b))
		{
		    String c= a1.getScriptId().concat(" or ").concat(b1.getScriptId());
			Logger.getLogger(ApplConstValues.logName).
            log(Level.SEVERE, "脚本步骤{0}存在框架中没有的时段，请查看后进行修改再运行！！",c);
			ApplExecuteResultDialog.viewError("手册生成失败", "error");

		}
		int i = TradePhase.get(a).intValue();
		int j = TradePhase.get(b).intValue();
		return i-j;
	}
	public int comparePriorty(String a,String b)
	{
		int a1 = Float.valueOf(a).intValue();
		int b1 = Float.valueOf(b).intValue();
		return a1-b1;
	}

}
