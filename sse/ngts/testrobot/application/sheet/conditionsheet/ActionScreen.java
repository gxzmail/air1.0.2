package sse.ngts.testrobot.application.sheet.conditionsheet;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ActionController;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;


public class ActionScreen
        extends JDialog implements Runnable
{
    private ActionController controller;
    JLabel caseDetial = new JLabel();
    JLabel caseContent = new JLabel();
    JLabel testResult= new JLabel();
    JLabel failedCause = new JLabel();
    JLabel caseDescrip  = new JLabel();
    JTextArea txtCaseDetial= new JTextArea();
    JTextArea txtCaseContent= new JTextArea();
    JTextArea txtTestResult= new JTextArea();
    JTextArea txtFailedCause= new JTextArea();
    JTextArea txtCaseDescrip= new JTextArea();
    JScrollPane sCaseDetial = new JScrollPane();
    JScrollPane sCaseContent = new JScrollPane();
    JScrollPane sFailedCause = new JScrollPane();
    JScrollPane sCaseDescrip = new JScrollPane();
    
    JPanel jPanel1 = new JPanel();
    public ActionScreen(ActionController controller)
    {
        this.controller = controller;
		
    }




    public void createUI()
    {
        this.setTitle("步骤详情窗口");
        
        initComponents();
 
        setSize(600, 450);
        setLocation(100, 100);
        
        this.toFront();
        this.setResizable(false);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

    }
    
    private void  initComponents()
    {
        
        this.setTitle("步骤详情窗口");
        caseDetial.setText("用例详情");
        caseDescrip.setText("脚本描述");
        caseContent.setText("脚本内容");
        testResult.setText("测试结果");
        failedCause.setText("测试日志");
       
        txtCaseContent.setLineWrap(true);
        txtCaseDetial.setLineWrap(true);
        txtTestResult.setLineWrap(true);
        txtFailedCause.setLineWrap(true);
        txtCaseDescrip.setLineWrap(true);
       
        setComponentValues(); 
        sCaseDetial.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sCaseDescrip.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sCaseContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sFailedCause.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        sCaseDetial.getViewport().add(txtCaseDetial);
        sCaseDescrip.getViewport().add(txtCaseDescrip);
        sCaseContent.getViewport().add(txtCaseContent);    
        sFailedCause.getViewport().add(txtFailedCause);
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
         jPanel1.setLayout(jPanel1Layout);
         jPanel1Layout.setHorizontalGroup(
             jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addGroup(jPanel1Layout.createSequentialGroup()
                 .addContainerGap()
                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     
                     .addGroup(jPanel1Layout.createSequentialGroup()
                         .addComponent(caseDetial)
                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                         .addComponent(sCaseDetial, 500, 500, 500))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                         .addComponent(caseDescrip)
                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                         .addComponent(sCaseDescrip, 500, 500, 500))
                     .addGroup(jPanel1Layout.createSequentialGroup()
                         .addComponent(caseContent)
                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                         .addComponent(sCaseContent, 500, 500, 500))
                     .addGroup(jPanel1Layout.createSequentialGroup()
                         .addComponent(testResult)
                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                         .addComponent(txtTestResult, 500, 500, 500))
                     .addGroup(jPanel1Layout.createSequentialGroup()
                         .addComponent(failedCause)
                         .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                         .addComponent(sFailedCause, 500, 500, 500)))
                 .addGap(111, 111, 111))
         );
         jPanel1Layout.setVerticalGroup(
             jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addGroup(jPanel1Layout.createSequentialGroup()
                 .addContainerGap()    
                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(caseDetial)
                     .addComponent(sCaseDetial, 40, 40, 40))
                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(caseDescrip)
                     .addComponent(sCaseDescrip, 40, 40, 40))
                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                 .addComponent(caseContent)
                     .addComponent(sCaseContent, 60, 60, 60))
                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(testResult)
                     .addComponent(txtTestResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                 .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                     .addComponent(failedCause)
                     .addComponent(sFailedCause, 120, 120, 120))
                 .addContainerGap())
         );
         javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
         getContentPane().setLayout(layout);
         layout.setHorizontalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addGroup(layout.createSequentialGroup()
                 .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addGroup(layout.createSequentialGroup()
                         .addGap(0, 0, 0)
                         .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                             .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                 .addContainerGap(26, Short.MAX_VALUE)
         ))));
         layout.setVerticalGroup(
             layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
             .addGroup(layout.createSequentialGroup()
                 .addGap(10, 10, 10)
                 .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                 .addGap(18, 18, 18)
                 .addContainerGap(37, Short.MAX_VALUE))
         );
         pack();
        
    }
    
    private void setComponentValues()
    {
        if(controller.getCurrentScript() != null)
        {
        	String strCaseDetails = controller.getCurrentScript().getFrmCase().getCaseDetials();
        	txtCaseDetial.setText((strCaseDetails == null)? "":strCaseDetails);
        	
        	String strCaseDescrip = controller.getCurrentScript().getFrmCase().getDescrip();
            txtCaseDescrip.setText((strCaseDescrip == null)? "":strCaseDescrip);         
        	txtCaseContent.setText(controller.getCurrentScript().getFrmCase().getTestContent());
        	txtTestResult.setText(controller.getCurrentScript().getTestResultDescr());
        	
        	txtFailedCause.setWrapStyleWord(true);
        	if(controller.getCurrentScript().getTestResult() == null)
        		txtFailedCause.setText("");
        	else
        	{
        	    //for(int i = 0;i<controller.getCurrentScript().getTestResult().size();i++)
        	    //{
        		    int i = controller.getCurrentScript().getTestResult().size();
            	    if(i>0)
            	    {	
            	    	if(!controller.getCurrentScript().getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG))
            	    	{
            	    		txtFailedCause.setForeground(Color.red);
            	    	}
        		        txtFailedCause.append(controller.getCurrentScript().getTestResult().get(i-1));
            	    }
              //}
            }
        }
    }

	public void run() {	
		createUI();
        this.setVisible(true);
    	this.toFront();
	}



   
}
