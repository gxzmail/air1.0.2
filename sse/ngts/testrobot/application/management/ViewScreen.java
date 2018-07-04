package sse.ngts.testrobot.application.management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import sse.ngts.testrobot.application.component.XFButton;
import sse.ngts.testrobot.application.interf.SheetController;
import sse.ngts.testrobot.application.sheet.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.application.sheet.conditionsheet.ConditionSheetScreen;
import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.app.ApplConfirmDialog;

public class ViewScreen
        extends JFrame
{
 	private ViewController controller;

   
    private JButton buttonExecute;
	private JButton buttonConfig;
    private JButton buttonLog;
    private JButton buttonTest;

   
    private JPanel configPanel;
  
    
    private JTabbedPane xlsSheetPane;



    public static void installSkin()
    {
        try
        {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

            /*�趨��ʾ����*/
            FontUIResource font = new FontUIResource("����", Font.PLAIN, 12);

            Enumeration<Object> enu = UIManager.getDefaults().keys();

            Object key, value;
            while (enu.hasMoreElements())
            {
                key = enu.nextElement();
                value = UIManager.get(key);
                if (value instanceof FontUIResource)
                {
                    UIManager.put(key, font);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Fail to set look and feel: " + e.getMessage());
        }
    }



    protected ViewScreen(final ViewController controller)
    {
        installSkin();            
      
        JPanel titlePanel = new JPanel(new BorderLayout());
        this.controller = controller;
        /************************* �趨���� **********************/
        JPanel buttonPanel = new JPanel();
      
        buttonExecute = new XFButton("��ִ���ֲ�", 5);
        buttonConfig = new XFButton("����", 5);
        buttonLog = new XFButton("��־", 5);
    
        buttonTest = new XFButton("���Ա���",5);
       
        
        buttonPanel.add(buttonConfig);
      
        buttonPanel.add(buttonExecute);
        buttonPanel.add(buttonTest);
        buttonPanel.add(buttonLog);
     
      
        
        titlePanel.add(buttonPanel, BorderLayout.WEST);

        this.add(titlePanel, BorderLayout.NORTH);


        /************************* �ű��� ***********************/
        JPanel instrucPanel = new JPanel(new BorderLayout());
        ImageIcon instrucIcon =
            new ImageIcon(ViewScreen.class.getResource("/pic/air����ͼ.gif"));
        JLabel instruclabel = new JLabel();
        instruclabel.setIcon(instrucIcon);
        
        JPanel descScrollPane = new JPanel();
        descScrollPane.setLayout(new BorderLayout());
        descScrollPane.add(instruclabel,BorderLayout.NORTH);
        instrucPanel.add(descScrollPane,BorderLayout.CENTER);
        instrucPanel.setBorder(BorderFactory.createTitledBorder("ʹ��˵�� "));
        
        xlsSheetPane = new JTabbedPane();      
        xlsSheetPane.setBackground(Color.WHITE);
        JSplitPane funcSplitPane = new JSplitPane();
        funcSplitPane.setOrientation(0);
        funcSplitPane.setDividerLocation(400);
        funcSplitPane.setTopComponent(instrucPanel);
        /*************���ô���**********************/
        JLabel label = new JLabel("��ǰ��������");
        configPanel = new JPanel();
        configPanel.add(label);
        funcSplitPane.setBottomComponent(configPanel);

        xlsSheetPane.add("ָ��",funcSplitPane);
        xlsSheetPane.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    if (xlsSheetPane.getSelectedComponent() instanceof ConditionSheetScreen)
                    {
    		        	if(controller.isApplEngineWorking())
    		        	{
    		        		JOptionPane.showMessageDialog(null, "�ű�����ִ�У����ܹرգ�", "INFO", JOptionPane.INFORMATION_MESSAGE);
    		        		return;
    		        	}
                    	int selection = JOptionPane.showConfirmDialog
        		        (null, 
        				"ȷ����Ҫ�ر�ѡ�����", 
        				"����", 
        				JOptionPane.OK_CANCEL_OPTION,
        				JOptionPane.WARNING_MESSAGE);
        		        if(selection == JOptionPane.CANCEL_OPTION)
        			       return;
        		        else if(selection == JOptionPane.OK_OPTION)
        		        {
        		        	controller.handleTestResult();        		        	
        		        	controller.handleRemoveSheetView(
                              (ConditionSheetScreen) xlsSheetPane.getSelectedComponent());
                            xlsSheetPane.remove(xlsSheetPane.getSelectedComponent());
        		        }
                    }
                }
            }
        });


        handleAction();

        this.add(xlsSheetPane, BorderLayout.CENTER);
        this.setTitle("AIRִ���ֲ������");
        Dimension de = this.getToolkit().getScreenSize();
        int sheight = new Double(de.getHeight()*0.9).intValue();

        int swidth = new Double(de.getWidth()*0.9).intValue();
        this.setMinimumSize(new Dimension(600, 400));
        setSize(swidth, sheight);
        setLocation(10, 10);

        this.setVisible(true);
     

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }


    public void addNewTabSheetView(SheetController c, String sheetName)
    {
        xlsSheetPane.add(sheetName, (Component)c.getUI());
        xlsSheetPane.setSelectedComponent((Component)c.getUI());
    }

/***xzguo
 * ���"��ִ���ֲ�"��Ĳ������
 * @param ---��
 * @return -- ��
 */

    private void handleAction()
    {
    	buttonExecute.addActionListener(new ActionListener()
    	{
			public void actionPerformed(ActionEvent e) {
				if(!controller.conditionSheetViewDete())
				{		        	
					int selection = JOptionPane.showConfirmDialog
    		        (null, 
    				"�Ƿ�����ִ�У�����ִ�л�ɾ������ִ�м�¼��", 
    				"����", 
    				JOptionPane.OK_CANCEL_OPTION,
    				JOptionPane.WARNING_MESSAGE);
    	
    		        if(selection == JOptionPane.OK_OPTION)
    		        {   		        	
   				        controller.restartConditionSheet(false); 		        	
    		        }	  		        
					
				}
			    controller.handleAddConditionSheet(ApplConfig.getInstance().getExecuteFile());					
			}
    		
    	});

       	
    	buttonConfig.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ConfScreen.getInstance().showUI();
            }
        });

        buttonLog.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
         	   controller.handleOpenTestlog(null);
            }
        });

       
        buttonTest.addActionListener(new ActionListener()
       {
            
           public void actionPerformed(ActionEvent e)
           {     	   
        	   controller.handleTestResult();
        	   controller.handleOpenTestResultSheet(null);       	   
           }
       });

    }
    
    public void selectedTabSheeView(ConditionSheetController c)
    {
        xlsSheetPane.setSelectedComponent(c.getUI());
    }

}