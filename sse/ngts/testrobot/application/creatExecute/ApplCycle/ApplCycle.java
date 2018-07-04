package sse.ngts.testrobot.application.creatExecute.ApplCycle;

import java.util.HashSet;

public interface ApplCycle {


	public boolean readCycleFile(String filePath);	
	
	public HashSet<String> getCaseList() ;
	
	public HashSet<String> getSceneList();
	
	 public void outPutCaseListFile(String path);
	 
	 public void outPutSceneListFile(String path);
}