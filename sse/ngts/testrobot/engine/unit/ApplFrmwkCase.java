package sse.ngts.testrobot.engine.unit;

/***xzguo
1．	类名称：ApplFrmwkCase
2．	类功能：NGTS_AM_AIR_ONXX_XXX_XXX_CV01.xls       文件中的
					 每行记录对应该类的一个实例。
***/


public class ApplFrmwkCase implements Cloneable{

    private String scriptId;
    private String descrip;
    private String testContent;
    private String testDate;
    private String testPhase;
    private String testAntic;
    private String testHost;
    private String testBatch;
    private String testStatus;
    private String testPrior;
    private String testMemo;
    private String stepsId;
    private String caseDetials;

	private final String title[]=
    {"步骤编号","脚本编号","脚本描述","脚本执行内容","交易日",
     "执行阶段","预期结果","主机","批处理","错误状态",
     "优先级","备注"
    };


	public ApplFrmwkCase clone()
    {
    	try{
    	    ApplFrmwkCase cloned = (ApplFrmwkCase)super.clone();
    	    return cloned;
    	}catch(CloneNotSupportedException e)
    	{
    		return null;
    	}
    	
    }

	public String getScriptId() {
		return scriptId;
	}
	public void setScriptId(String scriptId) {
		this.scriptId = scriptId;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getTestContent() {
		return testContent;
	}
	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	public String getTestPhase() {
		return testPhase;
	}
	public void setTestPhase(String testPhase) {
		this.testPhase = testPhase;
	}

	public String getTestHost() {
		return testHost;
	}
	public void setTestHost(String testHost) {
		this.testHost = testHost;
	}
	public String getTestBatch() {
		return testBatch;
	}
	public void setTestBatch(String testBatch) {
		this.testBatch = testBatch;
	}
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	public String getTestPrior() {
		return testPrior;
	}
	public void setTestPrior(String testPrior) {
		this.testPrior = testPrior;
	}
	public String getTestMemo() {
		return testMemo;
	}
	public void setTestMemo(String testMemo) {
		this.testMemo = testMemo;
	}
	
    public String[] getTitle() {
		return title;
	}
    public String getStepsId() {
		return stepsId;
	}
	public void setStepsId(String stepsId) {
		this.stepsId = stepsId;
	}

	public void setTestAntic(String testAntic) {
		this.testAntic = testAntic;
	}

	public String getTestAntic() {
		return testAntic;
	}

	public String getCaseDetials() {
		return caseDetials;
	}

	public void setCaseDetials(String caseDetials) {
		this.caseDetials = caseDetials;
	}
}
