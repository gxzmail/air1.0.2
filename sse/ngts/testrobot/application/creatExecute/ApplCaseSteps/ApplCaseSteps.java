package sse.ngts.testrobot.application.creatExecute.ApplCaseSteps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sse.ngts.testrobot.application.functions.AppSheetRead;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplCase;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
import sse.ngts.testrobot.engine.unit.ApplFrmwkCase;
import sse.ngts.testrobot.exception.ApplFatalException;

/***from doc
	1�������ƣ�ApplCaseSteps
       2���๦�ܣ���������ID����ȡ�������ļ���
       				�õ��������е�"��������"������ļ���
       				��ȡ����ļ��������ܵ�����"��ܲ���"������ļ�


***/

public class ApplCaseSteps{
    private ArrayList<ApplFrmwkCase> steps;
	
	public ApplCaseSteps()
	{		
			
	}
	/*************************************
	 *��������: �����������ļ��л�ȡ���в���
	 *�������룺
	 *String sheetNm        �����ű�������     /***xzguo     ÿ��xls��sheetΪ���Խű�
	 *File file             �����ű�·��
	 *int num               �����ӵڼ��п�ʼ��
	 *ApplCase caseDetail   ������������     /***xzguo  D_ONxx.xls�е�һ��
	 *��������ֵ��
	 *��
	 *************************************/
	public Boolean readCaseStep(String sheetNm, File file,int num,ApplCase caseDetail)
    {
		
	 		 
		Logger.getLogger(ApplConstValues.logName).
		                 entering("ApplCaseSteps", "readCaseStep");
		try
        {
            Hashtable dupCaseChk = new Hashtable();
            FileInputStream in = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            HSSFSheet sheet = workbook.getSheet(sheetNm);
            if (sheet == null)
            {
            }
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            String titleDesc[] = null;
            steps = new ArrayList<ApplFrmwkCase>();

            for (int j = num; j < numberOfRows; j++)
            {
                HSSFRow row = sheet.getRow(j);
                
                if (row == null)
                {
                    continue;
                }
                if (j == num)
                {
/***xzguo
 *  titleDesc = AppSheetRead.getTitleDesc(row)�����ǻ�ȡ�б�ע�����е������б�ע(�ű���š��ű�������)
 *  ��ֵ��titleDesc
 */
                    titleDesc = AppSheetRead.getTitleDesc(row);
/***xzguo   ���ԣ�ò�Ʒ����Ĳ���
 * AppSheetRead.deteTilt(titleDesc,ApplConstValues.stepTitle, file.getAbsolutePath()) ������
 * ���   ��    �������ڵ��е�ֵ����ApplConstValues.stepTitle ��
 */
                    if(!AppSheetRead.deteTilt(titleDesc,ApplConstValues.stepTitle, file.getAbsolutePath()))
                		return false;                	
                }
                else
                {

                	Hashtable<String,String> values = AppSheetRead.wrapValues(titleDesc, row);   // �ò���������          
                																		//��NGTS_AM_AIR_ON05_001_001_CV01.xls �е�row + 1��
                																		//(��0��ʼ����) ��ֵ�Ͷ�Ӧ���б�ǩ
                																		//(NGTS_AM_AIR_ON05_001_001_CV01.xls�е�4�У�rowȡֵΪ 3 )
                																		//��������������
                																		//ͨ���б�ǩ�ҵ���Ӧ��ֵ
                    if(values.get("�ű����").toString().isEmpty())
                        continue;
                    if(values.get("������").toString().isEmpty())
                    {
                    	Logger.getLogger(ApplConstValues.logName).
                    	  log(Level.SEVERE, "����{0}�Ľ������ֶ�Ϊ��",caseDetail.getCaseId()+"_"+
               		         values.get("�ű����").toString());
                    	return false;
                    }
                    if(!values.get("������").toString().trim().toUpperCase().startsWith("T")||!values.get("������").toString().trim().substring(1).matches("[0-9]+"))
                    {
                    	Logger.getLogger(ApplConstValues.logName).
                    	  log(Level.SEVERE, "����{0}�Ľ������ֶ�ֵ��ʽ����",caseDetail.getCaseId()+"_"+
               		         values.get("�ű����").toString());
                    	
                    }
                    if(values.get("ִ�н׶�").toString().isEmpty())
                    {                    	
                    	Logger.getLogger(ApplConstValues.logName).
                  	       log(Level.SEVERE, "����{0}��ִ�н׶��ֶ�Ϊ��",caseDetail.getCaseId()+"_"+
                		         values.get("�ű����").toString());
                    	  return false;
                    }
                    if(values.get("����״̬").toString().isEmpty())
                    {
                    	Logger.getLogger(ApplConstValues.logName).
                        log(Level.SEVERE, "����{0}�Ĵ���״̬Ϊ��",caseDetail.getCaseId()+"_"+
                        		         values.get("�ű����").toString());
                    	Logger.getLogger(ApplConstValues.logName).
           	            log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
						ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ�����"+caseDetail.getCaseId()+"_"+
								 values.get("�ű����").toString()+"����״̬Ϊ��", "ERROR");
						return false;
                    }
                    if(-1=="ynrlYNRL".indexOf(values.get("����״̬").toString().trim()))
                    {
                    	ApplExecuteResultDialog.viewError("ִ��ʧ��,ԭ��"+
                    			caseDetail.getCaseId()+"_"+
               		         values.get("�ű����").toString()+
                    			"�Ĵ���״̬ ����", "ERROR");
                    	Logger.getLogger(ApplConstValues.logName).
                    	     log(Level.SEVERE, "����{0}�Ĵ���״̬����",caseDetail.getCaseId()+"_"+
               		         values.get("�ű����").toString());
           	            Logger.getLogger(ApplConstValues.logName).
  	                           log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
           	            return false;
                    }
                    if(values.get("�ű�ִ������").toString().isEmpty())
                    {                    	
                    	Logger.getLogger(ApplConstValues.logName).
                  	       log(Level.SEVERE, "����{0}�Ľű�ִ�������ֶ�Ϊ��",caseDetail.getCaseId()+"_"+
                		         values.get("�ű����").toString());
                    	  return false;
                    }
                    ApplStepsSet step = new ApplStepsSet();
/***
		step.setApplSteps(values,caseDetail) �ò���Ϊ��ÿһ��NGTS_AM_AIR_ON05_001_001_CV01.xls �е�ÿһ������(values)
		��NGTS_AM_AIR_D_ON05_CV01.xls   (caseDetail)   �е�ĳ�м�¼�� "��������" �ֶη���steps������
***/
                    step.setApplSteps(values,caseDetail);
                    steps.add(step.getSteps());
                }
            }
            Logger.getLogger(ApplConstValues.logName).
                log(Level.INFO, "��ȡ�ļ�{0}�ɹ�",file.getName());	
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
			Logger.getLogger(ApplConstValues.logName).
               log(Level.SEVERE, "�Ҳ����ļ�{0}",file.getName());
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
			ApplExecuteResultDialog.viewError("ִ��ʧ��,�Ҳ����ļ�"+file.getName(), "ERROR");
            return false;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            Logger.getLogger(ApplConstValues.logName).
                log(Level.SEVERE, "��ȡ�ļ�{0}ʧ��",file.getName());	
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
			ApplExecuteResultDialog.viewError("ִ��ʧ��,��ȡ"+file.getName()+"ʧ��", "ERROR");
       	    return false;
        }
        return true;
    }
	
	public ArrayList<ApplFrmwkCase> getSteps() {
		return steps;
	}

}
