package sse.ngts.testrobot.application.functions;

import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import sse.ngts.testrobot.engine.app.ApplExecuteResultDialog;
import sse.ngts.testrobot.engine.unit.ApplConstValues;

public class AppSheetRead {
	
	/**
	 * �������ܣ���ȡһ��    �е������ֶ�
	 * �������룺
	 * @param row   ����xls�����е�һ��
	 * ����ֵ�� 
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
		�������д�� k ==tile.length����, ��������
****/
        	if(k>=tile.length)
        	{
        		ApplExecuteResultDialog.viewError("��������:["+titleDesc[i]+"]��������xls�ļ�"+fileName, "ERROR");
        		return false;

        	}
        }
    	return true;
    }
/***xzguo
	   wrapValues  �������ܣ�NGTS_AM_AIR_ON05_001_001_CV01.xls�е� dataRow �����е�ֵ�͵�4 �б�ǩ
	   							����hashtable�У�����ӳ���ϵ
	 * �������룺String[] titleDesc  ---NGTS_AM_AIR_ON05_001_001_CV01.xls�е� dataRow �����е�ֵ�͵�4 �б�ǩ
	 									��          "   �ű����	�ű�����	�ű�ִ������	������	ִ�н׶�	
	 									Ԥ�ڽ��	����	������	����״̬	���ȼ�	��ע"
	 * �������룺HSSFRow dataRow---��dataRow������Ӧ������ǩ��ֵ
	 * ����ֵ��Hashtable<String,String> ��dataRow������Ӧ��ֵ�Ͷ�Ӧ�ı�ǩ������ϣ��ϵ����ͨ����ǩ��
	 									����ȡ����Ӧ��ֵ
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