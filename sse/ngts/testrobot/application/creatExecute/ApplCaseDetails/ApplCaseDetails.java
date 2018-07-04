package sse.ngts.testrobot.application.creatExecute.ApplCaseDetails;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import sse.ngts.testrobot.application.functions.AppSheetRead;
import sse.ngts.testrobot.application.functions.ApplCaseScriptSort;
import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplCase;
import sse.ngts.testrobot.engine.unit.ApplConstValues;
/***xzguo
	�๦��:
	(��)
���ݳ���ID�ţ���ȡ�����ļ���
���ĳ������ID(����ID��ָD_ONXX_CV01.xls�еġ�
������š��е�����)�ڡ������б��������У����ȡ��������ϸ��Ϣ��
���롰�������顱������(����class ApplCaseDetails �е�caseDetails����)
	
***/

public class ApplCaseDetails 
{
    private ArrayList<ApplCase> caseDetails;



	/***************************************************************************
     *�������ܣ���ȡ�����ļ�����ȡ��Ҫ����������ϸ��Ϣ
     //***xzguo ��������ϸ��Ϣ��ָ�����б��е������ڳ����ļ��е������е�һ
     ����Ϣ������:
     	�������	����ID	�������	��������	���Ի���	��Ҫ�Ľ�����	����	�ű�����	���ȼ�	����/�쳣
	1	1	ON05_001_001	����ð������	C	T0	AC		1	
	1	2	ON05_001_002	���۳����걨У��	C	T0	AC		1	
	�������б�����ON05_001_002, ��
	1	2	ON05_001_002	���۳����걨У��	C	T0	AC		1	��һ�е����ݸ�ֵ��
	ApplCase ���е�һ��ʵ��  ��Ȼ�󽫸�ʵ������caseDetails ��������
	***

     
     *�������룺
     *File file                         ���������ļ� //*** D_ON05_CV01.xls
     *String sheetName                  ��������������
     *int num,HashSet<String>filterStr  ���������б�
     *String scene                      ��������ID
     *��������ֵ��
     *       ��
     **************************************************************************/
    public Boolean readCaseSheet(File file,String sheetName,int num,HashSet<String>filterStr,String scene)
    {
    	Logger.getLogger(ApplConstValues.logName).
            entering("ApplCaseDetails", "readCaseSheet");
    	try
        {
            Hashtable dupCaseChk = new Hashtable();
            FileInputStream in = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            HSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null)
            {

            }

            int numberOfRows = sheet.getPhysicalNumberOfRows();
            String titleDesc[] = null;
            ArrayList<ApplCase> scripts = new ArrayList<ApplCase>();
/***xzguo
	shhet.getRow(3)�Ǵ�sheet�еĵ����п�ʼ
***/

            for (int j = num; j < numberOfRows; j++)
            {
                HSSFRow row = sheet.getRow(j);
                if (row == null)
                {
                    continue;
                }
                if (j == num)
                {
                    titleDesc = AppSheetRead.getTitleDesc(row);
                    if(!AppSheetRead.deteTilt(titleDesc,ApplConstValues.caseTitle, file.getAbsolutePath()))
                		return false;   
                }
                else
                {
                    Hashtable values = AppSheetRead.wrapValues(titleDesc, row);
                    if(values.get(ApplConstValues.sceneType).toString().isEmpty())
                    	continue;
                    String cc = strChg(values.get(ApplConstValues.sceneType).toString(),3);
                    if(values.get(ApplConstValues.sceneId).toString().isEmpty())
                    	continue;
                    cc = cc+"_"+strChg(values.get(ApplConstValues.sceneId).toString(),3);
/***xzguo
		��NGTS_AM_AIR_D_ON05_CV01.xls Ϊ����
		cc ֵΪ ON05_001_001
***/
                    cc = scene +"_"+ cc;
                  //  System.out.println(cc);
                    if(!isInStr(cc,filterStr))   //�ж���CC�Ƿ���filerStr�б��У�����������
                    	continue;
                    ApplDetails script = new ApplDetails();
/***
 		valuesֵΪ
 		{���Ի���=C, ����ID=1.0, ����/�쳣=, ����=AC, �ű�����=, ��������=����ð������, ���ȼ�=1.0, �������=1.0, ��Ҫ�Ľ�����=T0, �������=}
 ***/
                    script.setDetails(values,scene);
/***xzguo
 		script.getcaseDescript() ���� NGTS_AM_AIR_D_ON05_CV01.xls ��һ�еļ�¼��Ϣ
 		����һ����Ϣ����script������
 ***/
                    scripts.add(script.getcaseDescript());
                   
                }
            }
            this.caseDetails = scripts;
			
            Collections.sort(caseDetails,new ApplCaseScriptSort()); //***xzguo ����caseID �ֶδ�С��caseDetails���� 
            												 //***�м�¼��������
            Logger.getLogger(ApplConstValues.logName).
               log(Level.INFO, "��ȡ�ļ�{0}�ɹ�",file.getAbsolutePath());
 
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Logger.getLogger(ApplConstValues.logName).
              log(Level.SEVERE, "�Ҳ����ļ�{0}",file.getAbsolutePath());
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
			ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ��Ҳ����ļ�"+file.getAbsolutePath(), "ERROR");
            return false;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.getLogger(ApplConstValues.logName).
               log(Level.SEVERE, "��ȡ�ļ�{0}ʧ��",file.getName());
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "ִ���ֲ�����ʧ�� ");
			ApplExecuteResultDialog.viewError("ִ��ʧ�ܣ����ļ�"+file.getAbsolutePath() +"ʧ��", "ERROR");
            return false;
           
        }
        return true;


    }
   /*******************************************
    * �������ܣ��ж�ָ��      �����Ƿ��� �����б�������
    * ���룺
    * @param cc                ����Ҫ�жϵ�����
    * @param filterStr         ���������б�����
    * ��������ֵ��
    * boolean
    *********************************************/
    public boolean isInStr(String cc,HashSet<String>filterStr)
    {
    	if(filterStr == null)
    		return true;
	    Iterator<String> b = filterStr.iterator();
	    while (b.hasNext())
	    {
	        String c = b.next();
	        if(cc.equalsIgnoreCase(c))
	        	return true;
	    }
    	return false;
    	
    }
    
    public  String strChg(String c,int charLength)
    {

    	Float  cc = Float.valueOf(c);
    	String  a = String.valueOf(cc.intValue());
    	while (a.length() < charLength)
            a = "0" + a;
        return a;
    }


    public ArrayList<ApplCase> getCaseDetails() {
		return caseDetails;
	}


}