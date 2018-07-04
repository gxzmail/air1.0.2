package sse.ngts.testrobot.application.management;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sse.ngts.testrobot.application.component.XFButton;
import sse.ngts.testrobot.application.functions.ApplFileProcess;
import sse.ngts.testrobot.engine.app.ApplCfg;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;

	/**
	 * <p>Title: </p>
	 *
	 * <p>Description: </p>
	 *
	 * <p>Copyright: Copyright (c) 2009</p>
	 *
	 * <p>Company: </p>
	 *
	 * @author not attributable
	 * @version 1.0
	 */


	@SuppressWarnings("serial")
	public class ConfScreen
	        extends JDialog
	{
	    JPanel jPanel1 = new JPanel();
	 
	    JLabel l1 = new JLabel();
	    JLabel l2 = new JLabel();	  
	    JLabel l5 = new JLabel();
	    JLabel l4 = new JLabel();
	    JLabel l6 = new JLabel();
	
	    
	      
	    JTextField txtTestFilePath = new JTextField();	    
	  
	    JTextField txtCasePath = new JTextField();	
	    JTextField txtTestResultPath = new JTextField();
	    JTextField txtFRMWORK = new JTextField();
	    
	    JPanel jPanel2 = new JPanel();
	    JLabel execuFilepath = new JLabel("执行手册文件");
	    JTextField txtExecuFile = new JTextField();
	    JLabel execuOutFilepath = new JLabel("测试结果文件");
	    JTextField txtExecuOutFilepath = new JTextField();
	    
	   
	    JButton  ConformButton =new XFButton("确认",5);
	    JButton  ApplButton = new XFButton("应用",5);
	    JButton  CancleButton = new XFButton("取消",5);
	    
	    String txtTestFilePath1 = null;
	    String txtCasePath1 = null;
	    String txtFRMWORK1 = null;
	    String txtExecuOutFilepath1= null;
	    String txtExecuteFile1 = null;
	    private static ConfScreen instance = null;

	    public static ConfScreen getInstance()
	    {
	        if (instance == null)
	        {
	            instance = new ConfScreen();
	        }
	        return instance;
	    }
	    
	    

	    private ConfScreen()
	    {
	        initComponents();
	        setCfgValue();
	        setAction();
	         this.setResizable(false);

	    }
	    
	    private void  initComponents()
	    {
	        
	        this.setTitle("配置文件窗口");
	    	l1.setText("测试配置文件");
	        l2.setText("测试用例路径");
	       
	        l4.setText("测试框架文件");
	    
	       
	        
	        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("脚本执行配置"));
	        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("脚本生成配置"));
	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	         jPanel1.setLayout(jPanel1Layout);
	         jPanel1Layout.setHorizontalGroup(
	             jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	             .addGroup(jPanel1Layout.createSequentialGroup()
	                 .addContainerGap()
	                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                     
	                     .addGroup(jPanel1Layout.createSequentialGroup()
	                         .addComponent(execuFilepath)
	                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                         .addComponent(txtExecuFile, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
	                     .addGroup(jPanel1Layout.createSequentialGroup()
	                         .addComponent(execuOutFilepath)
	                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                         .addComponent(txtExecuOutFilepath, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))
	                 .addGap(111, 111, 111))
	         );
	         jPanel1Layout.setVerticalGroup(
	             jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	             .addGroup(jPanel1Layout.createSequentialGroup()
	                 .addContainerGap()
	     
	                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                     .addComponent(execuFilepath)
	                     .addComponent(txtExecuFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
	                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                     .addComponent(execuOutFilepath)
	                     .addComponent(txtExecuOutFilepath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                 .addContainerGap())
	         );
		   
	         javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
	         jPanel2.setLayout(jPanel2Layout);
	         jPanel2Layout.setHorizontalGroup(
	        		 jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	             .addGroup(jPanel2Layout.createSequentialGroup()
	                 .addContainerGap()
	                 .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                     
	                     .addGroup(jPanel2Layout.createSequentialGroup()
	                         .addComponent(l1)
	                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                         .addComponent(txtTestFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
	                     .addGroup(jPanel2Layout.createSequentialGroup()
	                         .addComponent(l2)
	                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                         .addComponent(txtCasePath, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
	                  
	                     .addGroup(jPanel2Layout.createSequentialGroup()
	                         .addComponent(l4)
	                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                         .addComponent(txtFRMWORK, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))
	                   
	                 .addGap(111, 111, 111))
	         );
	         jPanel2Layout.setVerticalGroup(
	             jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	             .addGroup(jPanel2Layout.createSequentialGroup()
	                 .addContainerGap()
	     
	                 .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                     .addComponent(l1)
	                     .addComponent(txtTestFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
	                 .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                     .addComponent(l2)
	                     .addComponent(txtCasePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	               
	                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
	                 .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                     .addComponent(l4)
	                     .addComponent(txtFRMWORK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
	    
	                 .addContainerGap())
	         );
	         
	       
	         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	         getContentPane().setLayout(layout);
	         layout.setHorizontalGroup(
	             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	             .addGroup(layout.createSequentialGroup()
	                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                     .addGroup(layout.createSequentialGroup()
	                         .addGap(90, 90, 90)
	                         .addComponent(ConformButton)
	                         .addGap(70, 70, 70)
	                         .addComponent(ApplButton)
	                         .addGap(70, 70, 70)
	                         .addComponent(CancleButton))
	                     .addGroup(layout.createSequentialGroup()
	                         .addGap(36, 36, 36)
	                         .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                             .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                             .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
	                 .addContainerGap(29, Short.MAX_VALUE))
	         );
	         layout.setVerticalGroup(
	             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	             .addGroup(layout.createSequentialGroup()
	                 .addGap(30, 30, 30)
	                 .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                 .addGap(18, 18, 18)
	                 .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                 .addGap(18, 18, 18)
	                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                     .addComponent(ConformButton)
	                     .addComponent(ApplButton)
                         .addComponent(CancleButton))
	                 .addContainerGap(37, Short.MAX_VALUE))
	         );
	         pack();
	        
	    }
	    

	  
	    
	 
	    private void setCfgValue()
	    {

	        txtTestFilePath1 = ApplCfg.getInstance().getProperty(ApplCfg.TestFile_KEY);
	        txtTestFilePath.setText((txtTestFilePath1 == null) ? DEFAULT_txtTestFilePath : txtTestFilePath1);

	        txtCasePath1 = ApplCfg.getInstance().getProperty(ApplCfg.CasePath_KEY);
	        txtCasePath.setText((txtCasePath1 == null) ? DEFAULT_txtCasePath : txtCasePath1);

	        txtFRMWORK1 = ApplCfg.getInstance().getProperty(ApplCfg.FrmWorkPath_KEY);
	        txtFRMWORK.setText((txtFRMWORK1 == null) ? DEFAULT_txtFRMWORK : txtFRMWORK1);
        
	       
	        txtExecuteFile1 = ApplCfg.getInstance().getProperty(ApplCfg.ExecuteFile_KEY);
	        txtExecuFile.setText((txtExecuteFile1 == null) ? DEFAULT_txtExecuteFile : txtExecuteFile1);

	        txtExecuOutFilepath1 = ApplCfg.getInstance().getProperty(ApplCfg.ExecuteOutSTAT_Key);
	        txtExecuOutFilepath.setText((txtExecuOutFilepath1 == null) ? DEFAULT_txtExecuOutFilepath :
	        	txtExecuOutFilepath1);

	    }
	

	
	    private void setAction()
	    {
	    	CancleButton.addActionListener(
	                new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	            	setCfgValue();
	            	ConfScreen.this.setVisible(false);
	            }
	        });

	    	ConformButton.addActionListener(
	                new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                saveCfgValue();
	                JOptionPane.showMessageDialog(ConfScreen.this, "保存成功!");
	                ConfScreen.this.setVisible(false);
	            }
	        });
	        ApplButton.addActionListener(
	                new ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                saveCfgValue();
	                JOptionPane.showMessageDialog(ConfScreen.this, "保存成功!");
	                
	            }
	        });
	    }
	    public void showUI()
	    {
	        this.setVisible(true);
	    }

	    public void hideUI()
	    {
	        this.setVisible(false);
	    }
	    
	    private void saveCfgValue()
	    {
	    	if(!txtTestFilePath.getText().isEmpty())
	    	    ApplCfg.getInstance().modify(ApplCfg.TestFile_KEY, txtTestFilePath.getText());		
	    	else
	    		txtTestFilePath.setText(txtTestFilePath1);
	    	if(!txtCasePath.getText().isEmpty())
	    	    ApplCfg.getInstance().modify(ApplCfg.CasePath_KEY, txtCasePath.getText());	         
	    	else
	    		txtCasePath.setText(txtCasePath1);
	    	if(!txtFRMWORK.getText().isEmpty())
	    	    ApplCfg.getInstance().modify(ApplCfg.FrmWorkPath_KEY, txtFRMWORK.getText());	        
	    	else
	    		txtFRMWORK.setText(txtFRMWORK1);
	    	if(!txtExecuFile.getText().isEmpty())
	    	    ApplCfg.getInstance().modify(ApplCfg.ExecuteFile_KEY, txtExecuFile.getText());
	    	else
	    		txtExecuFile.setText(txtExecuteFile1);
	    	if(!txtExecuOutFilepath.getText().isEmpty())
	    	    ApplCfg.getInstance().modify(ApplCfg.ExecuteOutSTAT_Key,txtExecuOutFilepath.getText());
	    	else
	    		txtExecuOutFilepath.setText(txtExecuOutFilepath1);
	    	ApplCfg.getInstance().saveCfgFile();
	    }

	    private static final String DEFAULT_txtTestFilePath =
	            "Input\\AIR_ExecuteFile\\AIR_CYCLE.txt";
	    private static final String DEFAULT_txtCasePath= "Input\\AIR_ExecuteFile\\测试用例\\";

	    private static final String DEFAULT_txtFRMWORK = "AIR框架脚本\\NGTS_AM_AIR_D_03_03_AIR测试框架_CV02.xls";

	    private static final String DEFAULT_txtExecuteFile = "Input\\AIR_执行手册.xls";

	    private static final String DEFAULT_txtExecuOutFilepath = "output\\AIR_AutoExecute_result\\脚本测试统计.xls";


	}

