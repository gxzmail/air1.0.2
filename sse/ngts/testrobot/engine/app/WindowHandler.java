package sse.ngts.testrobot.engine.app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WindowHandler extends StreamHandler{
	public WindowHandler()
	{
		dialog =new JDialog();
		dialog.setTitle("生成执行手册");
		final JTextArea output = new JTextArea("生成执行手册执行详情如下所示：\n");
		output.setEditable(false);
		dialog.setSize(500, 500);
		dialog.add(new JScrollPane(output),BorderLayout.CENTER);
	
		setOutputStream(new 
				OutputStream()
		        {
			        public void write(int b){};
			        public void write(byte[]b ,int off,int len)
			        {
			        	output.append(new String(b,off,len));
			        }
			        	
			        
		        });
		
    	dialog.setFocusableWindowState(true);
		dialog.setVisible(true);
	}
	public void publish(LogRecord record)
	{
		if(!dialog.isVisible())return;
		super.publish(record);
		flush();
	}
   
	public JDialog getDialog() {
		return dialog;
	}
	 private JDialog  dialog;
	 

}
