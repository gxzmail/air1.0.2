package sse.ngts.testrobot.engine.app;

import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.exception.ApplRunFailException;

public class ApplConfig {


	private static ApplConfig instance;
	private String testFile =null; /*AIR_CYCLE.TXT文件的输入路径*/
	private String caseListFile=null; /*用例列表文件的输出路径*/
	private String sceneListFile=null;/*场景列表文件的输出路径*/
	private String casePath=null;     /*用例所在的文件夹的输入路径*/
	private String frmWorkPath=null;          /*框架文件的输入路径*/

	private String frmStepPath=null;           /*框架步骤文件的输出路径*/
	private String frmId=null;
	private String TestCaseStepsScriptPath=null; /*用例步骤文件的输出路径*/
	private String TestResultPath = null;
	private String TestCaseScript = null;

	private String IFDir = null ;
    private String RTDir = null ;
    private String ARDir = null ;
    private String DTDir = null ;
    private String CTDir = null ;
    private String OSDir = null ;
    
    private String executeFile = null;  /*执行执行手册的输入路径*/
    private String exectueOutPutStat = null;
    private String exectueOutPutResult = null;


	
    public static ApplConfig getInstance( )
    {
        if (instance == null)
        {
            instance = new ApplConfig();
        }
        return instance;
    }
	
	public ApplConfig()
	{
	    try{
		  this.TestResultPath = ApplCfg.getInstance().getProperty(ApplCfg.TestResultPath_KEY);
	      ApplFileProcess.createDir(this.TestResultPath,ApplExecuteConstValues.APPL_DIRC );	    
	      this.frmId  = ApplCfg.getInstance().getProperty(ApplCfg.FrmId_KEY);
	      this.caseListFile  = this.TestResultPath+
	         ApplCfg.getInstance().getProperty(ApplCfg.CaseListFile_KEY);
	      this.sceneListFile = this.TestResultPath+
	        ApplCfg.getInstance().getProperty(ApplCfg.sceneListFile_KEY);
	      this.TestCaseStepsScriptPath =  this.TestResultPath+
	        ApplCfg.getInstance().getProperty(ApplCfg.TestCaseStepsScriptPath_KEY);
	      this.TestCaseScript =  this.TestResultPath+
	        ApplCfg.getInstance().getProperty(ApplCfg.CaseScript_KEY);
	      this.frmStepPath    = this.TestResultPath+
                             frmId+ApplConstValues.stepPostfix;
	    	    
	      this.testFile  = ApplCfg.getInstance().getProperty(ApplCfg.TestFile_KEY);
	      this.frmWorkPath   = this.casePath+
	        ApplCfg.getInstance().getProperty(ApplCfg.FrmWorkPath_KEY);
	    

	      this.exectueOutPutStat = ApplCfg.getInstance().getProperty(ApplCfg.ExecuteOutSTAT_Key);
	      this.executeFile = ApplCfg.getInstance().getProperty(ApplCfg.ExecuteFile_KEY);
	      this.exectueOutPutResult = ApplCfg.getInstance().getProperty(ApplCfg.ExecuteOutResult_Key);
      }
	  catch(ApplRunFailException ex)
	  {
			ApplExecuteResultDialog.viewError("配置文件["+ex.getAct()+"]项不存在或为空值，错误！", "ERROR");
		    return;
	  }
	}
	
	public String getExectueOutPutResult() {
		return exectueOutPutResult;
	}

	public String getExectueOutPutStat() {
		return this.exectueOutPutStat = ApplCfg.getInstance().getProperty(ApplCfg.ExecuteOutSTAT_Key);
	}

	
	public String getTestFile() {
		return this.testFile = ApplCfg.getInstance().getProperty(ApplCfg.TestFile_KEY);
	}


	public String getCaseListFile() {
		
		 return this.caseListFile ;
	}


	public String getSceneListFile() {
		return this.sceneListFile ;
	}
	
	public String getFrmStepPath() {
		 return frmStepPath;
	}

	public String getCasePath() {
	    this.casePath      = ApplCfg.getInstance().getProperty(ApplCfg.CasePath_KEY);
		return casePath;
	}


	public String getFrmWorkPath() {
		this.casePath      = ApplCfg.getInstance().getProperty(ApplCfg.CasePath_KEY);
	    this.frmWorkPath   = this.casePath+
	        ApplCfg.getInstance().getProperty(ApplCfg.FrmWorkPath_KEY);
		return frmWorkPath;
	}



	public String getFrmId() {
		return this.frmId ;
	}


	public String getTestCaseStepsScriptPath() {
	
		return TestCaseStepsScriptPath;
	}
	
	public String getTestResultPath() {
	     return  this.TestResultPath;
	}
	
	public String getTestCaseScript() {
		
		return TestCaseScript;
	}
	
	public String getExecuteFile() {
		
		this.executeFile = ApplCfg.getInstance().getProperty(ApplCfg.ExecuteFile_KEY);
		return executeFile;
	}
	
	/*@param caseId  ONXX 
	 * 
	 *@return if scene equals ON, then return TestCases\\综业平台ONLINE(ON)\\
	 *		  if scene equals OS, then return TestCases\\周边系统实时接口(OS)\\ --从来就没用到
	 *		  if scene equals RT, then return TestCases\\撮合交易和非交易(RT)\\ --从来就没用到 
	 */
	public String getCasePathDirc(String caseId)
    {
    	String scene = null;
    	String pathDirc = null;
    	scene = ApplFileProcess.getStringByToken(1, "_",caseId).trim().substring(0, 2);
    	pathDirc = this.casePath+ApplCfg.caseType.get(scene);
    	
    	return pathDirc;
    }
}
