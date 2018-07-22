package sse.ngts.testrobot.application.creatExecute.ApplCaseSteps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import sse.ngts.testrobot.application.creatExecute.ApplCreateExeFile.ApplExeSheetWrite;
import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.application.functions.ApplXmlParse;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplCase;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
/***from doc
	�๦�ܣ�
	����"��������"���飬�õ��������Ӧ������������"�����б�"����

***/
public class ApplCaseStepsScriptImpl implements ApplCaseStepsScript{

    private ArrayList<ApplFrmwkCase> caseSteps = new ArrayList<ApplFrmwkCase>();
	private ArrayList<ApplFrmwkCase> frmWorkSteps= new ArrayList<ApplFrmwkCase>();
    private HashSet<String> tradeDate = new HashSet<String>();


	private Hashtable<String,Integer> tradePhase = new Hashtable<String,Integer>();
	private ApplXmlParse  xmlParse = ApplXmlParse.getinstance();

	/***from doc
		readCaseSheet()
		�������ܣ�����"��������"���飬��ȡ�����ű��ļ����ļ���ʽ��4.10.6����
						����"��������"���飬��ȡ����ļ������ɿ�ܲ������顣
		���룺  ArrayList<ApplCase>caseList    ���������б�
		�����"��������"�ļ����ļ���ʽ��4.9.7���ļ���Ϊ��TestCaseStepsScript.xls��
		�����"��������"���� ArrayList< ApplFrmwkCase > caseList

	***/



	
	public  Boolean readCaseSheet(ArrayList<ApplCase>caseList)
    {
  /***xzguo
  *	readCaseListSteps(caseList)������ɽ�TestCases\��ҵƽ̨ONLINE(ON)\�ű�\ON05\NGTS_AM_AIR_ON05_001_001_CV01.xls
  *	д�� Output\AIR_ExecuteFile_result\ON05_001_001_Steps.xls�У�ͬʱ��NGTS_AM_AIR_ON05_001_001_CV01.xls�е�����
  * ���� caseSteps��������
  *
  ****/
    		if(!readCaseListSteps(caseList))
				return false;
 /***xzguo
 * �˴��� ApplExeSheetWrite.writeExcuteSheet �ǽ� ����NGTS_AM_AIR_ONXX_XXX_XXX_CV01.xls(caseSteps)������д��
 * Output\AIR_ExecuteFile_result\TestCaseStepsScript.xls��(ApplConfig.getInstance().getTestCaseStepsScriptPath()).
 * ��readCaseListSteps�������е���ApplExeSheetWrite.writeExcuteSheet������NGTS_AM_AIR_ONXX_XXX_XXX_CV01.xls�е�
 * ����д��ONXX_XXX_XXX_Steps.xls�в�ͬ�����ǽ�����NGTS_AM_AIR_ONXX_XXX_XXX_CV01.xls������д��TestCaseStepsScript.xls
 *��
 ***/
			ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.OutPutStepsSheetName,
    			ApplConfig.getInstance().getTestCaseStepsScriptPath(),
                caseSteps);
/***xzguo
 * �м�ϸ��û��ϸ��
 * readFrmSheet() ����  �� ApplExeSheetWrite.writeExcuteSheet()����
 * 	�����ǽ�TestCases\AIR��ܽű�\NGTS_AM_AIR_�ۺ�ҵ����Կ��_CV01.xls
 *	(ApplConfig.getInstance().getFrmWorkPath())������д�� Output\AIR_ExecuteFile_result\FW01_001_001_Steps.xls��
 *
 */
			if(!readFrmSheet(ApplConfig.getInstance().getFrmWorkPath(), ApplConfig.getInstance().getFrmId()))
				return false;
			ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.OutPutStepsSheetName,
    			ApplConfig.getInstance().getFrmStepPath(),
    			frmWorkSteps);

		/****************************************************
		 * ���������ļ����²���
		 ****************************************************/
			if(!xmlParse.readxml())
			{
				Logger.getLogger(ApplConstValues.logName).
/***xzguo modify
	log(Level.SEVERE,"�������ļ�\"cfg\\AIR_Config.xml\"����"); ��ø�Ϊ
	log(Level.SEVERE,"�������ļ�\"cfg\\AIR_Config.txt\"����");
***/
     	        log(Level.SEVERE,"�������ļ�\"cfg\\AIR_Config.xml\"����");
				return false;
			}
/***xzguo
	�滻�������%var%�ı���Ϊʵ��ֵ
***/
			replaceCaseSteps(caseSteps);
/***xzguo
	�滻
***/
			replaceFrameSteps(frmWorkSteps);
    	/*****************************************************
         * �鿴��ܲ����е����в�������ȼ��Ƿ���ڽű��е����в����
         * ���ȼ�
       ******************************************************/
/***xzguo
*	�����ע��"�鿴��ܲ����е����в�������ȼ��Ƿ���ڽű��е����в�������ȼ�"��׼ȷ��
*	Ӧ���ǲ鿴��������в����������ȼ��Ƿ���ڽű����������в����������ȼ�
*
***/

    	if(!comparePriority(caseSteps,frmWorkSteps))
    	{
    		Logger.getLogger(ApplConstValues.logName).
     	        log(Level.SEVERE, "����ִ��ʧ�ܣ�����в�������ȼ����������ű��в�������ȼ�����");

     	    return false;

    	}
    	caseSteps.addAll(frmWorkSteps);
    	return true;
    }

	private void replaceFrameSteps(ArrayList<ApplFrmwkCase> steps)
	{
		Iterator<ApplFrmwkCase> iter = steps.iterator();
		while(iter.hasNext())
		{
			ApplFrmwkCase elem = iter.next();
			String s = null;
			for(int i = 1;i< 6;i++)
			{
				if(elem.getTestDate().trim().equals("T"+i))
				{
					for(int j = 6;j>= 0;j--)
					{
						int k = j+i;
						s = elem.getTestContent().replaceAll("%T"+j+"%","%T"+k+"%");
						elem.setTestContent(s);
					}
				}
			}

		}
		replaceCaseSteps(steps);
	}

	private void replaceCaseSteps(ArrayList<ApplFrmwkCase> steps)
	{
		Iterator<ApplFrmwkCase> iter = steps.iterator();
		Hashtable<String,String> xmlP = new Hashtable<String,String>();
		xmlP = xmlParse.getXmlParse();
		while(iter.hasNext())
		{
			ApplFrmwkCase elem = iter.next();
			String s = null;

            for(String key:xmlP.keySet())
			{
				if(elem.getTestContent().indexOf(key)!= -1)
				{
					s = elem.getTestContent().replaceAll("%"+key+"%", (String)xmlP.get(key));
					elem.setTestContent(s);
				}
			}


		}
	}

	private Boolean comparePriority(ArrayList<ApplFrmwkCase> steps1,ArrayList<ApplFrmwkCase> steps2)
	{
		String str1 = getMaxPriority(steps1);
		String str2 = getMaxPriority(steps2);
		if(str1.compareToIgnoreCase(str2)>0)
			return true;
		else
			return false;
	}

	private String getMaxPriority(ArrayList<ApplFrmwkCase> steps)
	{
		Iterator<ApplFrmwkCase> c = steps.iterator();
		String testPrior = null;
		String maxPrior = null;
		while(c.hasNext())
		{
			testPrior = c.next().getTestPrior();

			if(maxPrior == null)
				maxPrior = testPrior;
			else if(maxPrior.compareToIgnoreCase(testPrior)>0)
				maxPrior = testPrior;
		}
		return maxPrior;
	}
    /*********************************************
    * �������ܣ���ȡ�����б������еĲ���
    * �������룺
    * String path                   �����ļ�·��
    * ArrayList<ApplCase>caseList   ���������б�
    * Hashtable pathCfg             ���������ļ�����·��
    * ���������
    *     ��
    * ��������ֵ��
    *    ��
    *********************************************/
	public Boolean readCaseListSteps(ArrayList<ApplCase>caseList)
    {

		Logger.getLogger(ApplConstValues.logName).
             entering("ApplCaseStepsScriptImpl", "readCaseListSteps");
		Iterator<ApplCase> c = caseList.iterator();

        String pathDirc = null;
        String filePath = null;
    	while(c.hasNext())
    	{
    		ApplCase caseDescrip = new ApplCase();
    		caseDescrip = c.next();
    		String sceneId = null;

    		pathDirc = ApplConfig.getInstance().getCasePathDirc(caseDescrip.getCaseId());
    		/*����ID*/
    		sceneId = ApplFileProcess.getStringByToken(1, "_",caseDescrip.getCaseId());
    		/***xzguo
    		 	filePath ֵ Ϊ TestCases\��ҵƽ̨ONLINE(ON)\�ű�\ON05\NGTS_AM_AIR_ON05_001_001_CV01.xls
    		 */
    		/*�����ű���·��*/
    		filePath = pathDirc+ApplConstValues.stepDircPathName+"\\"+sceneId+"\\"+ApplConstValues.stepPrefix+
    		                  caseDescrip.getCaseId()+ApplConstValues.stepVersions;
    		try{
    			File file = new File(filePath);
    			ApplCaseSteps step = new ApplCaseSteps();
    		/***xzguo
    		 *
    		 */
    			if(!step.readCaseStep(ApplConstValues.stepSheetName, file, 3, caseDescrip))
    				return false;
    		    /*ÿ�������ļ��Ĳ����ļ�·��*/
    		/***xzguo
    		 * caseStepsTxtPath ֵΪ  Output\AIR_ExecuteFile_result\ON05_001_001_Steps.xls
    		 *
    		 */
    		    String caseStepsTxtPath = ApplConfig.getInstance().getTestResultPath()+
	    		                    caseDescrip.getCaseId()+ApplConstValues.stepPostfix;
			/***xzguo
		     *	ApplExeSheetWrite.writeExcuteSheet������NGTS_AM_AIR_ON05_001_001_CV01.xls������(step.getSteps())
		     *	д�� Output\AIR_ExecuteFile_result\ON05_001_001_Steps.xls��(caseStepsTxtPath)��sheet��Ϊ����(ApplConstValues.OutPutStepsSheetName)
		     *	�ú�����ʵ�ֵ���NGTS_AM_AIR_ONXX_001_001_CV01.xlsд�뵥��ONXX_001_001_Steps.xls
			 */
    		    ApplExeSheetWrite.writeExcuteSheet(ApplConstValues.OutPutStepsSheetName,caseStepsTxtPath,step.getSteps());
			/***xzguo
			*	caseSteps.addAll(step.getSteps())�����ǽ�step.getSteps����caseSteps�б��ĩβ
			*
			*/
    		    caseSteps.addAll(step.getSteps());
    		}
    		catch(Exception e){
    			Logger.getLogger(ApplConstValues.logName).
                    log(Level.SEVERE, "���ļ�{0}ʧ��",filePath);

    			Logger.getLogger(ApplConstValues.logName).
                    log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
				ApplExecuteResultDialog.viewError("ִ��ʧ��,��ȡ�ļ�"+filePath+"ʧ��", "ERROR");
				return false;
    		}


    	}
    	setTradeDate(this.caseSteps);
		return true;


    }


	public Boolean readFrmSheet(String frmWorkPath,String frameWorkId)
	{
		Logger.getLogger(ApplConstValues.logName).
             entering("ApplCaseFramworkScript", "initApplFrmStep");
		try{

			File file = new File(frmWorkPath);
			ApplCase a = new ApplCase();
			a.setCaseId(frameWorkId);

			ApplCaseSteps frmSteps = new ApplCaseSteps();
			if(!frmSteps.readCaseStep(ApplConstValues.caseSheetName, file, 3,a))
				return false;
			setTradePhase(frmSteps.getSteps());
			Iterator<String> c = tradeDate.iterator();
			int i = 0;
			int j = 0;
			String date;
			Iterator<ApplFrmwkCase> b = frmSteps.getSteps().iterator();
			while(b.hasNext())
			{
				b.next();
				j++;
			}
			while(c.hasNext())
			{
				date = c.next().toString();
				this.frmWorkSteps.addAll(chgSteps(frmSteps.getSteps(),date));
				i++;
			}
		}
		catch(Exception e)
		{
			Logger.getLogger(ApplConstValues.logName).
              log(Level.SEVERE, "���ļ�{0}ʧ��",frmWorkPath);
			ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ����ļ�"+frmWorkPath+"ʧ��", "ERROR");
			return false;
		}
		return true;
	}

	/*
	 *�������ܣ����Ĳ����еĽ�����
	 *�������룺
	 *ArrayList<ApplFrmwkCase> frmWorkSteps   ������������
	 *String tradeDate                        ���� ������
	 *�����������ͣ�
	 *   ArrayList<ApplFrmwkCase>
	 */
	private ArrayList<ApplFrmwkCase> chgSteps(ArrayList<ApplFrmwkCase> frmWorkSteps,String tradeDate)
	{


		ArrayList<ApplFrmwkCase> fSteps = new ArrayList<ApplFrmwkCase>();
		Iterator<ApplFrmwkCase> c = frmWorkSteps.iterator();
	  	while(c.hasNext())
	  	{

	  		ApplFrmwkCase d = new ApplFrmwkCase();
	  		d = c.next().clone();
	  		d.setTestDate(tradeDate);
	  		fSteps.add(d);
	  	}
	  	return fSteps;
	}

    /***********************************************
     * �������ܣ������������������ȡ������
     * �������룺
     * @param Steps     ��������������
     * ��������ֵ��
     * HashSet<String>  ��������������
     *************************************************/
	private void setTradeDate(ArrayList<ApplFrmwkCase> Steps)
    {
    	Iterator<ApplFrmwkCase> c = Steps.iterator();

        while(c.hasNext())
        {
        	this.tradeDate.add(c.next().getTestDate());
        }
    }

/***xzguo
*	setTradePhase()��������Ϊ��ApplFrmwkCase���һ��ʵ��(����ӦNGTS_AM_AIR_ONXX_XXX_XXX.CV01.xls��һ�м�¼)�е�
*	testPhase(��Ӧһ�м�¼�е�ִ�н׶�)����(���ظ�����)����tradePhase��ϣ����
*
***/
	public void setTradePhase(ArrayList<ApplFrmwkCase> e) {
		Iterator<ApplFrmwkCase> c = e.iterator();
		String str = new String();
		ArrayList<String> str3 = new ArrayList<String>();

		int i = 0;
		while(c.hasNext())
		{
			str = c.next().getTestPhase().trim();
			if(str3.contains((String)str))
				continue;
			else
			{
				str3.add(str);
				tradePhase.put(str,(Integer)i);
				i++;
			}
		}
	}

	public ArrayList<ApplFrmwkCase> getCaseSteps() {
			return caseSteps;
	}

	public Hashtable<String, Integer> getTradePhase() {
		return tradePhase;
	}



}
