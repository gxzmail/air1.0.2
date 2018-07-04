package sse.ngts.testrobot.engine.unit;

public class ApplExecuteConstValues {
	
	/***执行部分常量**/
	public final static String  outStatusFileName ="Output\\AIR_AutoExecute_result\\result.txt";
	public final static String  outStatusTmpFileName ="Output\\AIR_AutoExecute_result\\result_tmp.txt";

	//public final static String executeFileName= "Input\\AIR_执行手册.xls";
	
	/*生成的执行结果文件名称*/
	//public static final String resultFile = "Output\\AIR_AutoExecute_result\\生成结果.xls";
	/*生成的执行结果表单名称*/
	public final static String statsheetName = "执行结果";
	public final static String statReuslt = "统计结果";
	

	public final static String  logExecuteFileName ="TestRobotExecute.log";
	public final static String logExecutName = "sse.ngts.testrobot.Execute";

	
	/* 执行结果成功失败的值*/
	public final static String executeSuccess = "--AIR--SUCCESS--";
	public final static String executeFailed = "--AIR--FAILURE--";
	public final static String executeManual = "手动执行";
	public final static String executeOmit = "不执行";
	
	public final static String stepsId = "步骤编号";
	public final static String scriptId = "脚本编号";
	public final static String tradeData = "交易日";
	public final static String testPhase= "执行阶段";
	public final static String testStatus = "错误状态";
	
	public final static String manual = "Manual";
	public final static String skip = "N/A";
	public final static int testMaxNum = 4;
	
	public final static String result1 = "等待执行";
	public final static String result2 = "正在执行";
	public final static String result3 = "执行成功";
	public final static String result4 = "执行失败";
	public final static String result5 = "失败需手动";
	public final static String result6 = "需要手动执行";
	public final static String result7 = "手动执行完成";
	public final static String[] title ={"步骤编号","脚本编号","脚本描述",
        "脚本执行内容","预期结果","批处理","错误状态","执行结果","备注"};
	public final static int APPL_File = 1;
	public final static int APPL_DIRC = 2;
}
