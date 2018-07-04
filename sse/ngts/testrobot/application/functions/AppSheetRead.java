package sse.ngts.testrobot.application.functions;

import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;

public class AppSheetRead {
	
	/**
	 * 函数功能：获取一行    中的所有字段
	 * 函数输入：
	 * @param row   －－xls表单中的一行
	 * 返回值： 
	 * String[]
	 */
    public static String[] getTitleDesc(HSSFRow row)
    {
        int numberOfCells = row.getPhysicalNumberOfCells();
        String title[] = new String[numberOfCells];

        for (int k = 0; k < numberOfCells; k++)
        {
            HSSFCell cell = row.getCell((short) k);
            String value = cell.getRichStringCellValue().getString().trim();

            title[k] = value;
        }
        return title;

    }
    
    public static boolean deteTilt(String[] titleDesc,String[] tile,String fileName)
    {
    	for(int i = 0;i<titleDesc.length;i++)
        {
            if(titleDesc[i]==null||titleDesc[i].isEmpty())
            	continue;
    		int k = 0;
        	for(k = 0;k<tile.length;k++)
        	{
                if(tile[k].equalsIgnoreCase(titleDesc[i]))
                	break;
        	}

/***xzguo
		这里可以写成 k ==tile.length更好, 减少歧义
****/
        	if(k>=tile.length)
        	{
        		ApplExecuteResultDialog.viewError("表格列名:["+titleDesc[i]+"]错误，请检查xls文件"+fileName, "ERROR");
        		return false;

        	}
        }
    	return true;
    }
/***xzguo
	   wrapValues  函数功能：NGTS_AM_AIR_ON05_001_001_CV01.xls中的 dataRow 所在行的值和第4 行标签
	   							放入hashtable中，建立映射关系
	 * 函数输入：String[] titleDesc  ---NGTS_AM_AIR_ON05_001_001_CV01.xls中的 dataRow 所在行的值和第4 行标签
	 									即          "   脚本编号	脚本描述	脚本执行内容	交易日	执行阶段	
	 									预期结果	主机	批步骤	错误状态	优先级	备注"
	 * 函数输入：HSSFRow dataRow---第dataRow行所对应上述标签的值
	 * 返回值：Hashtable<String,String> 将dataRow行所对应的值和对应的标签建立哈希关系，即通过标签就
	 									可以取到对应的值
***/
    public static Hashtable<String,String> wrapValues(String[] titleDesc, HSSFRow dataRow)
    {
        //int numberOfCells = dataRow.getPhysicalNumberOfCells();
        Hashtable<String,String> values = new Hashtable<String,String>();

        for (int k = 0; k < titleDesc.length; k++)
        {
 /***xzguo	
 	getCell() is 0 based column number

 ***/
            HSSFCell cell = dataRow.getCell((short) k);
            String value = getValue(cell);
            String desc = (String) titleDesc[k];
            values.put(desc, value);

        }

        return values;
    }
    
    public static String getValue(HSSFCell cell)
    {
        String value = "";
        if (cell != null)
        {
            switch (cell.getCellType())
            {

            case HSSFCell.CELL_TYPE_NUMERIC:
                value = "" + cell.getNumericCellValue();
                break;

            case HSSFCell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue()
                        .getString();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                ;
                break;
            default:
            }
        }
        return value;
    }

}
