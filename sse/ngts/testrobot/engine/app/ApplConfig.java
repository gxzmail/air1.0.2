package sse.ngts.testrobot.engine.app;

import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;
import sse.ngts.testrobot.exception.ApplRunFailException;

public class ApplConfig {


	private static ApplConfig instance;
	private String testFile =null; /*AIR_CYCLE.TXT�ļ�������·��*/
	private String caseListFile=null; /*�����б��ļ������·��*/
	private String sceneListFile=null;/*�����б��ļ������·��*/
	private String casePath=null;     /*�������ڵ��ļ��е�����·��*/
	private String frmWorkPath=null;          /*����ļ�������·��*/

	private String frmStepPath=null;           /*��ܲ����ļ������·��*/
	private String frmId=null;
	private String TestCaseStepsScriptPath=null; /*���������ļ������·��*/
	private String TestResultPath = null;
	private String TestCaseScript = null;

	private String IFDir = null ;
    private String RTDir = null ;
    private String ARDir = null ;
    private String DTDir = null ;
    private String CTDir = null ;
    private String OSDir = null ;
    
    private String executeFile = null;  /*ִ��ִ���ֲ������·��*/
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
			ApplExecuteResultDialog.viewError("�����ļ�["+ex.getAct()+"]����ڻ�Ϊ��ֵ������", "ERROR");
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
	 *@return if scene equals ON, then return TestCases\\��ҵƽ̨ONLINE(ON)\\
	 *		  if scene equals OS, then return TestCases\\�ܱ�ϵͳʵʱ�ӿ�(OS)\\ --������û�õ�
	 *		  if scene equals RT, then return TestCases\\��Ͻ��׺ͷǽ���(RT)\\ --������û�õ� 
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
