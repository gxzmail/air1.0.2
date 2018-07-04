package sse.ngts.testrobot.engine.unit;

/***from doc
	1．	类名称：ApplCase
	2．	类功能：用例表(/***xzguo 这里的用例表是指NGTS_AM_AIR_D_ON05_CV01.xls)                      
						记录中一行对应该类的一个实例。

***/

public class ApplCase {
    private String sheetName;
    private String sceneType;
    private String sceneId;
    private String caseDetails;

	private String caseId;
    private String testEnvr;
    private String testDate;
    private String testhost;
    private String testPrior;
    private String testResult;
    private String testNecessDoc;

	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public String getSceneType() {
		return sceneType;
	}
	public void setSceneType(String sceneType) {
		this.sceneType = sceneType;
	}
	public String getSceneId() {
		return sceneId;
	}
	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getTestEnvr() {
		return testEnvr;
	}
	public void setTestEnvr(String testEnvr) {
		this.testEnvr = testEnvr;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getTesthost() {
		return testhost;
	}
	public void setTesthost(String testhost) {
		this.testhost = testhost;
	}
	public String getTestPrior() {
		return testPrior;
	}
	public void setTestPrior(String testPrior) {
		this.testPrior = testPrior;
	}

	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getTestNecessDoc() {
		return testNecessDoc;
	}
	public void setTestNecessDoc(String testNecessDoc) {
		this.testNecessDoc = testNecessDoc;
	}
	public String getCaseDetails() {
		return caseDetails;
	}
	public void setCaseDetails(String caseDetails) {
		this.caseDetails = caseDetails;
	}  

}
