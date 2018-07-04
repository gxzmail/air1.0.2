package sse.ngts.testrobot.application.management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import sse.ngts.testrobot.application.interf.ApplReceiver;
import sse.ngts.testrobot.application.interf.ApplSender;
import sse.ngts.testrobot.application.interf.SheetController;
import sse.ngts.testrobot.application.interf.SheetScreen;
import sse.ngts.testrobot.application.sheet.conditionsheet.ConditionSheetController;
import sse.ngts.testrobot.engine.ApplEvt;
import sse.ngts.testrobot.engine.app.ApplConfig;

public class ViewController
        implements ApplSender, ApplReceiver
{
    private static ViewController instance = null;
    private ArrayList<SheetController> sheetControllers;

	private ViewScreen ui;

	private ViewController()
    {
        ui = new ViewScreen(this);
        sheetControllers = new ArrayList<SheetController>();
    }

    public ViewScreen getUI()
    {
        return ui;
    }

    public void showWarnMessage()
    {
        JOptionPane.showMessageDialog(ui,"打开执行手册失败!");
    }

    public static ViewController getInstance()
    {
        if (instance == null)
        {
            instance = new ViewController();
        }
        return instance;
    }
 
    /**
     * 
     * 输入：String fileName――要打开的表单名
     * ArrayList<ApplFrmwkCase> steps ―― 表中的步骤详情信息
     * 输出：空
     * 功能：新建一个新增一个ConditionSheet的文件视图
     */
    
    public boolean conditionSheetViewDete()
    {
    	 for (SheetController c : sheetControllers)
         {
             if(c instanceof ConditionSheetController&&!((ConditionSheetController)c).isViewflag())
             {
                 return false;
             }
         }
    	 return true;
    	
    }
    
    public void restartConditionSheet(Boolean closeFlag)
    {
    	 for (SheetController c : sheetControllers)
         {
             if(c instanceof ConditionSheetController&&!((ConditionSheetController)c).isViewflag())
             {
                 ((ConditionSheetController)c).reStartExec(closeFlag);
             }
         }
    	
    }
    public void handleAddConditionSheet(String fileName)
    {
        for (SheetController c : sheetControllers)
        {
            if (c instanceof ConditionSheetController &&
                ((ConditionSheetController) c).getSheetName().equalsIgnoreCase(fileName)&&
                ((ConditionSheetController)c).isViewflag())
            {
                ui.selectedTabSheeView((ConditionSheetController) c);
                return;
            }
            else if(c instanceof ConditionSheetController&&!((ConditionSheetController)c).isViewflag())
            {
                ui.addNewTabSheetView(c, fileName);	
                return;
            }
        }

        ConditionSheetController c = new ConditionSheetController();
        boolean loadFlag = c.handleLoadSheet(fileName);
        c.loadSheetInfo();

        ui.addNewTabSheetView(c, fileName);
        c.getUI().changeCurrentStatus();
        sheetControllers.add(c);
        c.setViewflag(true);
        if (!loadFlag)
        {
            ViewController.getInstance().showWarnMessage();
        }
  
    }

  
    public void handleRemoveSheetView( SheetScreen removeUI )
    {
        for (SheetController c : sheetControllers)
        {
            if (c.getUI() == removeUI)
            {
                //sheetControllers.remove(c);
               ((ConditionSheetController)c).deleSheetController();
                return;
            }
        }

    }


    public boolean isApplEngineWorking()
    {
        for (SheetController c : sheetControllers)
        {
            if (c.isApplSheetWorking())
            {
                return true;
            }
        }
        return false;
    }
    



	 public void handleOpenTestResultSheet(ApplEvt evt)
	 {
	    	
		   String fileDirc =  System.getProperty("user.dir");
		   String filePath =
			   fileDirc + "\\"+ApplConfig.getInstance().getExectueOutPutStat();

	        try
	        {
	           //String[] exec = {
	           // "\"C:\\Program Files\\Microsoft Office\\OFFICE11\\excel.exe\"",
	            //"\""+filePath+"\"" };
	            Runtime.getRuntime().exec("cmd /c start "+filePath);
	        }
	        catch (IOException ex)
	        {
	            ex.printStackTrace();
	        }
	       
	    }
	 
	 public void handleOpenTestlog(ApplEvt evt)
	 {
	    	
		   String fileDirc =  System.getProperty("user.dir");
		   String filePath =
			   fileDirc + "\\"+ApplConfig.getInstance().getExectueOutPutResult();
           File file = new File(filePath);
           if(!file.exists())
        	   return;
	        try
	        {
	            //String[] exec = {
	            // "\"C:\\Program Files\\Windows NT\\Accessories\\wordpad.exe\"",

	            Runtime.getRuntime().exec("cmd /c start "+filePath);
	        }
	        catch (IOException ex)
	        {
	            ex.printStackTrace();
	        }
	       
	    }
	 public void handleTestResult()
	 {
		 new Thread()
         {
             public void run()
             {
                 for (SheetController c : sheetControllers)
                 {
                     if (c instanceof ConditionSheetController &&
                         ((ConditionSheetController) c).getSheetName().equalsIgnoreCase(ApplConfig.getInstance().getExecuteFile()))
                     {                   	    
                    	 ((ConditionSheetController)c).testResultFileCreate(ApplConfig.getInstance().getExectueOutPutStat());           
                     }
                 }
          	     
             }
         }.start();
	 }


}
