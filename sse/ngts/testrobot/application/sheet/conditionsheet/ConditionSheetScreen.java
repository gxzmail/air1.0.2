package sse.ngts.testrobot.application.sheet.conditionsheet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ActionController;
import sse.ngts.testrobot.application.interf.SheetScreen;
import sse.ngts.testrobot.application.management.ViewScreen;
import sse.ngts.testrobot.engine.app.ApplConfirmDialog;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;


@SuppressWarnings("serial")
public class ConditionSheetScreen
        extends JSplitPane implements SheetScreen
{
    private ConditionSheetTable tableUI;
	private ConditionSheetController c;
    private JComboBox viewList;
    private JComboBox phaseList;
    private JTextField caseTxt;
    private JComboBox stepResult;
    private JComboBox stepType;

    private JButton  buttonClear;

    private JButton  buttonStart;
    private JButton  buttonStop;
    private JButton  buttonResume;
    private JButton  buttonSuspend;
    private JButton  buttonSingleStepRun;
    private JButton  buttonStopScroll;
    private JButton  buttonCurrentRunStep;
    private JLabel costTimeLabel;

    private JLabel curruntStatus;
    private JLabel curruntStep;
    private JLabel runStatus;
    private JTextArea resutTxt = new JTextArea();
    
	private boolean buttonStartFlag = false;
	private boolean popStarEn = true;
	private int tablehight = 0;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "HH:mm:ss");
    {
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }
    public ConditionSheetScreen(ConditionSheetController c)
    {
        this.c = c;
        createComponent();
        createAction();

    }
    

    private void createComponent()
    {
    	 ImageIcon startIcon =
             new ImageIcon(ViewScreen.class.getResource("/pic/run_exc1.gif"));

         ImageIcon stopIcon =
             new ImageIcon(ViewScreen.class.getResource("/pic/stop.gif"));
        
         ImageIcon resumeIcon =
             new ImageIcon(ViewScreen.class.getResource("/pic/resume_co.gif"));
         
         ImageIcon suspendIcon =
             new ImageIcon(ViewScreen.class.getResource("/pic/suspend_co.gif"));
        tableUI = new ConditionSheetTable(c);

        JPanel mainFuncPanel = new JPanel(new BorderLayout());
        JPanel ViewPanel = new JPanel(new BorderLayout());
        costTimeLabel = new JLabel("总运行时间:00:00:00");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tableUI);

       
        JLabel txtLabel = new JLabel("选择日期:");
        String label[] = {"选择日期","T0","T1","T2"};

        JLabel phaseLabel = new JLabel("选择时段:");       
        Object[] label2=  c.getTradePhase().toArray();
        JLabel caseLabel = new JLabel("输入用例ID:");
        
        JLabel statusLabel = new JLabel("选择结果:");
        String label3[] = {"选择结果","等待执行","执行失败","执行成功","需要手动执行",
        		"手动执行完成","失败需手动"};
        JLabel stepTypeLabel = new JLabel("选择类型:");
        String label4[] = {"选择类型","自动执行","手动执行"};


        buttonClear = new JButton("清空");
        buttonStart = new JButton("");
        buttonStop = new JButton("");
        buttonResume = new JButton("");
        buttonCurrentRunStep = new JButton("");
        curruntStatus = new JLabel("当前处于滚屏状态");
        curruntStep = new JLabel("当前运行步骤：");
        buttonSuspend =  new JButton("");
        buttonSingleStepRun = new JButton("");
        buttonStopScroll = new JButton("");
        buttonStart.setIcon(startIcon);
        buttonStart.setToolTipText("开始执行脚本");
        buttonStop.setIcon(stopIcon);
        buttonStop.setToolTipText("停止脚本执行");
        buttonResume.setIcon(resumeIcon);
        buttonResume.setToolTipText("继续执行");
        buttonSuspend.setIcon(suspendIcon);
        buttonSuspend.setToolTipText("暂停脚本执行");
        buttonSingleStepRun.setText("单步执行");
        buttonSingleStepRun.setToolTipText("单步执行");
        buttonCurrentRunStep.setText("跳转至当前");
        buttonCurrentRunStep.setToolTipText("当前正在运行步骤");
        buttonStopScroll.setText("停止滚屏");
        buttonStopScroll.setToolTipText("停止滚屏");
        JPanel viewFuncPanel = new JPanel(new BorderLayout());
        viewList = new JComboBox(label);
        phaseList = new JComboBox(label2);
        caseTxt = new JTextField("");
        stepResult = new JComboBox(label3);
        caseTxt.setColumns(10);
        stepType = new JComboBox(label4);
        JPanel viewChoosePanel = new JPanel();
        JPanel viewChoosePane2 = new JPanel();
        setFuncStatusBegin();
        runStatus = new JLabel();
        runStatus.setText("错误数/已执行/手动完成/步骤数:0/0/0/0");
        /*控件栏*/

        viewChoosePane2.add(curruntStatus);
        viewChoosePane2.add(curruntStep);
        viewChoosePane2.add(costTimeLabel);
        viewChoosePane2.add(runStatus);
        viewChoosePanel.add(buttonStart);
        viewChoosePanel.add(buttonSuspend);
        viewChoosePanel.add(buttonResume);
        viewChoosePanel.add(buttonStop);
        viewChoosePanel.add(buttonSingleStepRun);    
        viewChoosePanel.add(buttonCurrentRunStep);    

        viewChoosePanel.add(buttonStopScroll);
        
        JPanel viewPanel1 = new JPanel();
        /*选择栏*/
        viewPanel1.add(txtLabel);
        viewPanel1.add(viewList);

        viewPanel1.add(phaseLabel);
        viewPanel1.add(phaseList);
        
        viewPanel1.add(caseLabel);
        viewPanel1.add(caseTxt);
        
        viewPanel1.add(stepTypeLabel);
        viewPanel1.add(stepType);

        viewPanel1.add(statusLabel);
        viewPanel1.add(stepResult);

        
        viewPanel1.add(buttonClear);
        
        
        ViewPanel.add(viewPanel1,BorderLayout.WEST);
        //viewFuncPanel.add(viewChoosePane2,BorderLayout.WEST);
        viewFuncPanel.add(viewChoosePanel,BorderLayout.EAST);
        
        mainFuncPanel.add(ViewPanel, BorderLayout.NORTH);
        mainFuncPanel.add(scrollPane, BorderLayout.CENTER);
        mainFuncPanel.add(viewFuncPanel,BorderLayout.SOUTH);
       
       
        /************* 结果窗口**********************/
        JPanel ResultPanel = new JPanel(new BorderLayout());
        JLabel resultlabel = new JLabel("当前运行结果");
       
        ResultPanel.add(resultlabel,BorderLayout.NORTH);
        ResultPanel.add(viewChoosePane2,BorderLayout.SOUTH);
        JScrollPane resultPane = new JScrollPane();
        //Dimension de = this.getToolkit().getScreenSize();
       // int sheight = new Double(de.getHeight()*0.4).intValue();

       // int swidth = new Double(de.getWidth()*0.8).intValue();
       // resutTxt.setSize(swidth, sheight);
        resultPane.setViewportView(resutTxt);
        ResultPanel.add(resultPane,BorderLayout.CENTER);
        
        Dimension de = this.getToolkit().getScreenSize();
        int sheight = new Double(de.getHeight()*0.75).intValue();

       
        this.setOrientation(0);
        this.setDividerLocation(sheight);
        this.setTopComponent(mainFuncPanel);
        this.setBottomComponent(ResultPanel);
    }
    public void changeScriptStep(int step,String str)
    {
    	  curruntStep.setText("      当前运行步骤："+(step+1));
    }

    public void changeScriptStatus(int row, String status)
    {
        tableUI.changeStatus(row, status);
      
    }

    public void changeCostTime( int row , long time )
    {
        String timeStr = dateFormatter.format(new Date(time));
        tableUI.changeCostTime(row,timeStr);
    }
    
    public void changeRunTime( int row , long starttime,long endtime )
    {
        String timestart = dateFormatter.format(new Date(starttime));
        String timeend = dateFormatter.format(new Date(endtime));

        tableUI.changeRunPhase(row,timestart,timeend);
    }
    public void changeCostTime( int row , String time )
    {
        if(time.length() == 6)
        {
            time = time.substring(0,2)+":"+
                   time.substring(2,4)+":"+time.substring(4,6);
        }
        tableUI.changeCostTime(row,time);
    }
    
    public void tableScroll( int row , Boolean isAllowScroll )
    {
    	int height = (int) (tableUI.getCellRect(2, 0, false).getY()-tableUI.getCellRect(1, 0, false).getY());
    	int rows = (int) (tableUI.getVisibleRect().getHeight()/height);
    	
    	//System.out.println("row num:"+row+tableUI.getCellRect(row, 0, false));
    	//System.out.println("height:"+tableUI.getVisibleRect());
        /*2012-09-11,修改刷新次数过多问题*/
    	if(isAllowScroll&&((row%(rows/2)==0&&row>=rows-1)))
        {
        	tableUI.scrollRectToVisible(tableUI.getCellRect(
        		row+rows/2+1, 0, true));
        }
    }



    private void createAction()
    {
    	buttonStart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
            	if(buttonStartFlag)
            	{           		            		
            		
            		/*2012-04-19 modified by wuli ,start*/
            		Object options[]={"取消","确认"};
            		int selection = JOptionPane.showOptionDialog(null, "重新开始会清除执行记录，要重新开始执行脚本吗？",
            				"警告",
            				JOptionPane.OK_CANCEL_OPTION,
            				JOptionPane.WARNING_MESSAGE,null,
            				options, options[0]);
            		/*
            		int selection = JOptionPane.showConfirmDialog
            		        (null, 
            				"重新开始会清除执行记录，要重新开始执行脚本吗？", 
            				"警告", 
            				JOptionPane.OK_CANCEL_OPTION,
            				JOptionPane.WARNING_MESSAGE);
            				*/
            		if(selection == 0)
            			return;
            		else if(selection == 1)
            		{
            		    c.reStartExec(false);  
            		    c.clearSheetInfo();
            		    buttonStartFlag = true;
            		}
            		/*2012-04-19 modified by wuli ,end*/

            	}
            	else
            	{
          		    buttonStartFlag = true;
            	}
    			c.setStop(false);
        		c.setSuspend(false);
                c.execAutoActions();               
                setFuncStatus(false);
                buttonStop.setEnabled(true);
              
            }

        });

    	buttonSuspend.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               c.setSuspend(true); 
               setFuncStatus(true);
            }

        });
    	buttonResume.addActionListener(new ActionListener()
        {
            
    		public void actionPerformed(ActionEvent e)
            {
               if(c.isApplSheetWorking())
               {
            	   JOptionPane.showMessageDialog(null, "当前步骤仍在执行中，不能执行选定步骤！", "warnning",JOptionPane.INFORMATION_MESSAGE);
            	   return;
               }
    		   c.setSuspend(false); 
               c.execAutoActions();             
               setFuncStatus(false);
            }

        });
    	buttonStop.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	   /*2012-04-19 modified by wuli ,start*/	
            	  /*
            	    int selection = JOptionPane.showConfirmDialog
            		        (null, 
            				"需要停止当前的执行吗？", 
            				"警告", 
            				JOptionPane.OK_CANCEL_OPTION,
            				JOptionPane.WARNING_MESSAGE);
            		*/
            		Object options[]={"取消","确认"};
            	  
            		int selection = JOptionPane.showOptionDialog(null, "需要停止当前的执行吗?",
            				"警告",
            				JOptionPane.OK_CANCEL_OPTION,
            				JOptionPane.WARNING_MESSAGE,null,
            				options, options[0]);
            	
            		if(selection == 0)
            			return;
            		else if(selection == 1)
            		{           			 
                        c.setSuspend(true);   
            			setFuncStatusStop();
            		}            	
            }
        });
    	
    	buttonSingleStepRun.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {             	   
                if(c.isApplSheetWorking())
                {
             	   JOptionPane.showMessageDialog(null, "当前步骤仍在执行中，不能执行选定步骤！", "warnning",JOptionPane.INFORMATION_MESSAGE);
             	   return;
                }
            	c.sigleStepProcess();               	  
                setFuncStatus(false);
            }

        });
    	buttonCurrentRunStep.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	tableUI.scrollRectToVisible(tableUI.getCellRect(
            			c.getCurrentRunRow()-1, 0, true));
            	 
            }

        });
        viewList.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Object item = ((JComboBox)e.getSource()).getSelectedItem();
                c.filterViewByExecDateAndPhaseAndCase(item.toString(),
                                               phaseList.getSelectedItem().toString(),
                                               caseTxt.getText().toString(),
                                               stepResult.getSelectedItem().toString(),
                                               stepType.getSelectedItem().toString());
            }

        });

        phaseList.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Object item = ((JComboBox)e.getSource()).getSelectedItem();
                c.filterViewByExecDateAndPhaseAndCase(viewList.getSelectedItem().toString(),
                		                        item.toString(),
                		                        caseTxt.getText().toString(),
                                               stepResult.getSelectedItem().toString(),
                                               stepType.getSelectedItem().toString());
            }

        });
        caseTxt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
            	 c.filterViewByExecDateAndPhaseAndCase(viewList.getSelectedItem().toString(),
                         phaseList.getSelectedItem().toString(),
                         caseTxt.getText().toString(),
                         stepResult.getSelectedItem().toString(),
                         stepType.getSelectedItem().toString());
            	 
            }

        });
        stepResult.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
            	 c.filterViewByExecDateAndPhaseAndCase(viewList.getSelectedItem().toString(),
                         phaseList.getSelectedItem().toString(),
                         caseTxt.getText().toString(),
                         stepResult.getSelectedItem().toString(),
                         stepType.getSelectedItem().toString());
            	 
            }

        });  
        stepType.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
            	 c.filterViewByExecDateAndPhaseAndCase(viewList.getSelectedItem().toString(),
                         phaseList.getSelectedItem().toString(),
                         caseTxt.getText().toString(),
                         stepResult.getSelectedItem().toString(),
                         stepType.getSelectedItem().toString());
            	 
            }

        });  
        buttonClear.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                
            	viewList.setSelectedItem("选择日期") ;
                phaseList.setSelectedItem("选择时段");
            	caseTxt.setText("");
            	stepResult.setSelectedItem("选择结果");
            	stepType.setSelectedItem("选择类型"); 
            	c.filterViewByExecDateAndPhaseAndCase(viewList.getSelectedItem().toString(),
                         phaseList.getSelectedItem().toString(),
                         caseTxt.getText().toString(),
                         stepResult.getSelectedItem().toString(),
                         stepType.getSelectedItem().toString());
            	 
            }

        });  
        tableUI.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(final MouseEvent e)
            {
                c.changeSelectedRows(tableUI.getSelectedRows());
                tableUI.repaint();
                if (e.getClickCount() == 2)
                {
                    c.handleOpenAction(null);               
                }
                if (e.getButton() == 3)
                {
                	ConditionSheetPopup popui = new ConditionSheetPopup(popStarEn,c);
                	popui.init(tableUI, c.findControllersBySelectedRows());
                	popui.show(e.getX(), e.getY());   
                }

            }
        });
        tableUI.addKeyListener(new KeyListener()
        {
            public void keyTyped(KeyEvent e)
            {
            	           	
            }         
            public void keyPressed(KeyEvent e)
            {
                c.changeSelectedRows(tableUI.getSelectedRows());
            	if(e.getKeyCode() == KeyEvent.VK_1)
            	{        	   
            	   
            	  new Thread()
                   {
                    public void run()
                    {
               		    if(!c.findControllersBySelectedRows().isEmpty())
                 	    {
               		     
               		    	int selection = JOptionPane.showConfirmDialog
               		    	(null, 
               		    			"需要设置所有选定的步骤为\"执行成功\"标记吗？", 
               		    			"警告", 
               		    			JOptionPane.OK_CANCEL_OPTION,
               		    			JOptionPane.WARNING_MESSAGE);
               		    	if(selection == JOptionPane.CANCEL_OPTION)
               		    		return;               		    	
               		    	else if(selection == JOptionPane.OK_OPTION)
               		    	{
       		            	     c.changeStepStatus(1);
               		    	}
     		             }
                    }
                }.start();
                repaint();           		
            	}
            	if(e.getKeyCode() == KeyEvent.VK_0)
            	{
            		new Thread()
                    {
                        public void run()
                        {
                   		    if(!c.findControllersBySelectedRows().isEmpty())
                     	    {int selection = JOptionPane.showConfirmDialog
         		            (null, 
         				    "需要设置所有选定的步骤为\"未执行\"标记吗？", 
         				    "警告", 
         				     JOptionPane.OK_CANCEL_OPTION,
         				     JOptionPane.WARNING_MESSAGE);
         		             if(selection == JOptionPane.CANCEL_OPTION)
         			             return;
         		             else if(selection == JOptionPane.OK_OPTION)
         		             {
       		            	     c.changeStepStatus(0);

         		             }
                        }
                        }
                    }.start();
                    repaint();           		

            	}
            	if(e.getKeyCode() == KeyEvent.VK_2)
            	{
            		new Thread()
                    {
                        public void run()
                        {
                  		    if(!c.findControllersBySelectedRows().isEmpty())
                  		    {int selection = JOptionPane.showConfirmDialog
                  		    	(null, 
                  		    			"需要设置所有选定的步骤为\"执行失败\"标记吗？", 
                  		    			"警告", 
                  		    			JOptionPane.OK_CANCEL_OPTION,
                  		    			JOptionPane.WARNING_MESSAGE);
                  		    if(selection == JOptionPane.CANCEL_OPTION)
                  		    	return;
                  		    else if(selection == JOptionPane.OK_OPTION)
                  		    {
      		            	     c.changeStepStatus(2);
                  		    }
                  		    }
                        }
                    }.start();
                    repaint();           		
            	}
            }
            public void keyReleased(KeyEvent e)
            {
            	
            }
        });
        buttonStopScroll.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	
            	if(c.getScrollAllowed())
            	{
            		c.setScrollAllowed(false);
            		buttonStopScroll.setText("恢复滚屏"); 
            		curruntStatus.setText("当前处于停止滚屏状态               ");
            	}
            	else
            	{
            		c.setScrollAllowed(true);
            		buttonStopScroll.setText("停止滚屏");
            		curruntStatus.setText("当前处于滚屏状态              ");

            	}
            		
            }
        });
    }


    public void setButtonStartFlag(boolean buttonStartFlag) {
		this.buttonStartFlag = buttonStartFlag;
	}


	public synchronized void setFuncStatus(final boolean f)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                buttonStart.setEnabled(f);
                buttonResume.setEnabled(f);
                buttonSingleStepRun.setEnabled(f);
                viewList.setEnabled(f);
                phaseList.setEnabled(f);
                caseTxt.setEnabled(f);
                stepResult.setEnabled(f); 
                stepType.setEnabled(f); 

                buttonSuspend.setEnabled(!f);
                popStarEn = f;
                if(c.isStop())
                {
                	 buttonSingleStepRun.setEnabled(false);
                	 buttonResume.setEnabled(false);
                	 buttonSuspend.setEnabled(false);
                	 popStarEn = false;
                }
               
            }
        });
    }
    
    /**
     * 初始情况下按键设置
     */
    public synchronized void setFuncStatusBegin()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                buttonStart.setEnabled(true);
                buttonResume.setEnabled(false);
                buttonSingleStepRun.setEnabled(true);
                viewList.setEnabled(true);
                phaseList.setEnabled(true);
                caseTxt.setEnabled(true);
                stepResult.setEnabled(true); 
                stepType.setEnabled(true); 
                buttonSuspend.setEnabled(false);
                buttonStop.setEnabled(false);
                popStarEn = true;
            }
        });
    }
    
    public synchronized void setFuncStatusReBegin()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                buttonStart.setEnabled(true);
                buttonResume.setEnabled(false);
                buttonSingleStepRun.setEnabled(true);
                viewList.setEnabled(true);
                phaseList.setEnabled(true);
                caseTxt.setEnabled(true);
                stepResult.setEnabled(true); 
                stepType.setEnabled(true); 
                buttonSuspend.setEnabled(false);
                buttonStop.setEnabled(true);
                popStarEn = true;
            }
        });
    }
    
    /**
     * 单击停止键后界面上控件设置
     */
    public synchronized void setFuncStatusStop()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	c.setStop(true); 
            	buttonStart.setEnabled(true);
                buttonResume.setEnabled(false);
                buttonSingleStepRun.setEnabled(false);
                viewList.setEnabled(true);
                phaseList.setEnabled(true);
                caseTxt.setEnabled(true);
                stepResult.setEnabled(true); 
                stepType.setEnabled(true); 
                buttonSuspend.setEnabled(false);
                buttonStop.setEnabled(false);              
                popStarEn = false;
            }
        });
    }
   
    public void changeTotalCostTime( long costTime )
    {
        costTimeLabel.setText("      运行时间:"+
                              dateFormatter.format(new Date(costTime)));
    }
    public void changeCurrentStatus()
    {
    	new Thread()
        {
            public void run()
            {   	        
            	runStatus.setText("      错误数/已执行/手动完成/步骤数:"+c.getStatus());   
            }
        }.start();
    }
    
    public void changePhase(Object[] label2)
    {
    	for(int i = 0;i<label2.length;i++)
    	phaseList.addItem(label2[i]);
    }
    
    public void addScriptRowUI(ActionController c)
    {
        if (c != null )
            tableUI.addRow(c);
    }

    public void updateView()
    {
        tableUI.clearAll();
    }
    
    public JTextArea getResutTxt() {
		return resutTxt;
	}


	public ConditionSheetTable getTableUI() {
		return tableUI;
	}
}
