package sse.ngts.testrobot.application.creatExecute.ApplCaseDetails;

import java.util.ArrayList;
import java.util.HashSet;

import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.unit.ApplCase;



public interface ApplCaseScript {
	
	/*
	 * 函数功能：
	 * 根据场景列表挨个读取场景文件，把场景文件中所有在用例列表中存在的用例的详细信息取出来，放到数组中
	 * 函数输入：
	 * String pathDirc          －－场景文件存放路径
	 * Hashtable pathCfg        －－场景对应的目录
	 * HashSet<String>caseList  －－用例列表
	 * HashSet<String>sceneList －－场景列表
	 * 函数返回值：
	 *     空
	 */
	
	public Boolean readCaseDetails(String pathDirc,ApplConfig pathCfg,HashSet<String>caseList,HashSet<String>sceneList);
	
	/*
	 * 函数功能：把链表中的数据输出到文档中
	 * 函数输入：
	 * ArrayList<ApplCase> in  －－要输出的数据
	 * 输出：
	 * String filePath         －－输出文件路径
	 * 函数返回值：
	 *     空
	 * 
	 */
	public void outPutCaseDetails(ArrayList<ApplCase> in, String filePath);

	public ArrayList<ApplCase> getCaseScript();
}
