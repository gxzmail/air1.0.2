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
	类功能:
	(二)
根据场景ID号，读取场景文件，
如果某个用例ID(二：ID是指D_ONXX_CV01.xls中的“
用例编号”列的内容)在“用例列表”数组中，则读取该用例详细信息，
放入“用例详情”数组中(二：class ApplCaseDetails 中的caseDetails变量)
	
***/

public class ApplCaseDetails 
{
    private ArrayList<ApplCase> caseDetails;



	/***************************************************************************
     *函数功能：读取场景文件，获取需要的用例的详细信息
     //***xzguo 用例的详细信息即指用例列表中的用例在场景文件中的所在行的一
     行信息。举例:
     	场景类别	场景ID	用例编号	用例描述	测试环境	需要的交易日	主机	脚本链接	优先级	正常/异常
	1	1	ON05_001_001	基础冒烟用例	C	T0	AC		1	
	1	2	ON05_001_002	报价出库申报校验	C	T0	AC		1	
	若用例列表中有ON05_001_002, 则将
	1	2	ON05_001_002	报价出库申报校验	C	T0	AC		1	这一行的内容赋值给
	ApplCase 类中的一个实例  ，然后将该实例放入caseDetails 的数组中
	***

     
     *函数输入：
     *File file                         －－场景文件 //*** D_ON05_CV01.xls
     *String sheetName                  －－场景表单名
     *int num,HashSet<String>filterStr  －－用例列表
     *String scene                      －－场景ID
     *函数返回值：
     *       空
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
	shhet.getRow(3)是从sheet中的第四行开始
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
		以NGTS_AM_AIR_D_ON05_CV01.xls 为例，
		cc 值为 ON05_001_001
***/
                    cc = scene +"_"+ cc;
                  //  System.out.println(cc);
                    if(!isInStr(cc,filterStr))   //判断邋CC是否在filerStr列表中，不在则跳过
                    	continue;
                    ApplDetails script = new ApplDetails();
/***
 		values值为
 		{测试环境=C, 场景ID=1.0, 正常/异常=, 主机=AC, 脚本链接=, 用例描述=基础冒烟用例, 优先级=1.0, 场景类别=1.0, 需要的交易日=T0, 用例编号=}
 ***/
                    script.setDetails(values,scene);
/***xzguo
 		script.getcaseDescript() 返回 NGTS_AM_AIR_D_ON05_CV01.xls 中一行的记录信息
 		将该一行信息放入script数组中
 ***/
                    scripts.add(script.getcaseDescript());
                   
                }
            }
            this.caseDetails = scripts;
			
            Collections.sort(caseDetails,new ApplCaseScriptSort()); //***xzguo 按照caseID 字段大小对caseDetails数组 
            												 //***中记录进行排序
            Logger.getLogger(ApplConstValues.logName).
               log(Level.INFO, "读取文件{0}成功",file.getAbsolutePath());
 
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Logger.getLogger(ApplConstValues.logName).
              log(Level.SEVERE, "找不到文件{0}",file.getAbsolutePath());
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "执行手册生成失败 ");
			ApplExecuteResultDialog.viewError("执行失败，找不到文件"+file.getAbsolutePath(), "ERROR");
            return false;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.getLogger(ApplConstValues.logName).
               log(Level.SEVERE, "读取文件{0}失败",file.getName());
        	Logger.getLogger(ApplConstValues.logName).
	         log(Level.SEVERE, "执行手册生成失败 ");
			ApplExecuteResultDialog.viewError("执行失败，读文件"+file.getAbsolutePath() +"失败", "ERROR");
            return false;
           
        }
        return true;


    }
   /*******************************************
    * 函数功能：判断指定      用例是否在 用例列表数组中
    * 输入：
    * @param cc                －－要判断的用例
    * @param filterStr         －－用例列表数组
    * 函数返回值：
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
