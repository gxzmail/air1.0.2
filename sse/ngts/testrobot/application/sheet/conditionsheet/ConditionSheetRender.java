
package sse.ngts.testrobot.application.sheet.conditionsheet;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;

import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;


public class ConditionSheetRender
        extends JTextArea implements TableCellRenderer
{
    Color successColor = new Color(211, 255, 168);
    Color failedColor = new Color(255, 200, 200);
    Color autoRunColor = Color.yellow.brighter();
    Color manualColor = Color.gray.brighter();
    Font fontBold = getFont().deriveFont(Font.BOLD);
    
    private ConditionSheetController controller;


    ConditionSheetRender(ConditionSheetController controller)
    {
        this.controller = controller;
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }

    private int setRowHeight(JTable table, int row )
    {
        //���㵱���е���Ѹ߶�
        int maxPreferredHeight = 0;

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            JTextArea c = (JTextArea)(table.getCellRenderer(row,i));
            c.setSize(table.getColumnModel().getColumn(i).getWidth(),22);
            c.setText("" + table.getValueAt(row, i));

           // maxPreferredHeight = Math.max(maxPreferredHeight,
                                         // c.getPreferredSize().height);
            maxPreferredHeight = 22;
        }
        table.setRowHeight(row, maxPreferredHeight);
        return maxPreferredHeight;

    }



    public static Color getCalculatedColor(double part, Color c1, Color c2)
    {
        double r3 = (c1.getRed() * part) + (c2.getRed() * (1 - part));
        double g3 = (c1.getGreen() * part) + (c2.getGreen() * (1 - part));
        double b3 = (c1.getBlue() * part) + (c2.getBlue() * (1 - part));

        return new Color((int) r3, (int) g3, (int) b3);
    }

    public static Color getCalculatedColor(Color c1, Color c2)
    {
        return getCalculatedColor(0.25, c1, c2);
    }


    /**************************************************************************
     * ����Render����
     **************************************************************************/
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {

        if(column>10)return this;
        setText(value==null?"":value.toString());
        setBackground(Color.white);
        setForeground(Color.black);
        setBorder(new MatteBorder(new Insets(2,1,2,1),Color.white));
        
        ApplExecutCase script  =
                ((ConditionSheetTable) table).getApplExecutCase(row);

  
        if (script == null)
        {
            System.out.println("row number " + row);
        }
        
        if (!script.getAttribute(ApplExecutCase.ATTR_ENABLE_FLAG))
        {
            setEnabled(false);
        }
     
        if (isSelected)
        {
            setBorder(new MatteBorder(new Insets(2,0,2,0),Color.orange));
        }


        if( !script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
        {
            if (column == 8)
            {
                setText(script.getTestResultDescr());
            }
        }
        
        if( controller.getCurrentWorkingController()!=null)
        {
            if(controller.getCurrentWorkingController().getViewRow()-1 == row)
            {
                setBackground(autoRunColor);   
                if (column == 8)
                {
                    setText("����ִ��");
                }
            }              
        }
        

        if(row == controller.getCurrentRunRow()-1)
        {
        	setBorder(new MatteBorder(new Insets(2,0,2,0),Color.BLUE));
        }
        
        if(row == controller.getStopRunRow()-1)
        {
        	setBackground(Color.CYAN);
        }
        
        if(script.getProcessStatus() == ApplExecutCase.PROCESS_STATUS_RUN)
        	return this;
        
        if (script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
            script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
        {

            setBackground(successColor);

            if (column == 8)
            {                          	    
            	setText(script.getTestResultDescr());               
            }

        }
        
        if( !script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
        		!script.getAttribute(ApplExecutCase.ATTR_SKIP_FLAG)&&
        		!script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
        		script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
        {
        	setBackground(manualColor);
        	if (column == 8)
            {
                setText(script.getTestResultDescr());
                            	    	              	              
            }
        }
        if( script.getAttribute(ApplExecutCase.ATTR_AUTO_FLAG)&&
        		!script.getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG)&&
        		script.getAttribute(ApplExecutCase.ATTR_REFF_FLAG))
        {
        	setBackground(failedColor);
        	if (column == 8)
            {
                setText(script.getTestResultDescr());
            }
        }


        if (controller.getPreferredRowHeight(row) == -1  )
        {
            //���㵱���е���Ѹ߶�
        	setRowHeight(table, row);
            controller.setPreferredRowHeight(row,25);
        }

        return this;
    }
}
