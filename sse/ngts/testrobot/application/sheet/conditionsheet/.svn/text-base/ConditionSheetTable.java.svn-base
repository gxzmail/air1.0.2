package sse.ngts.testrobot.application.sheet.conditionsheet;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

import sse.ngts.testrobot.application.component.XFTable;
import sse.ngts.testrobot.application.component.XFTableBO;
import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ActionController;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;


public class ConditionSheetTable
        extends XFTable
{
    private static String columns[] =
            {
            "序列","脚本编号","用例描述","脚本描述","脚本执行内容", "交易日","执行时段", "错误状态", "结果","运行时间","开始/结束时间"};

    private int currID = 1;


	private ConditionSheetRender render;

    public ConditionSheetRender getRender() {
		return render;
	}

	public ConditionSheetTable(ConditionSheetController controller)
    {
        super(columns);
        int width[] =
                {
                50,100,200,180,250,50,60,100,100,80,110};
        this.setWidth(width);
        render =  new ConditionSheetRender(controller);
        this.getTableHeader().setReorderingAllowed(false);
        this.setDefaultRenderer(Object.class, render);

    }

    public void clearAll()
    {
        super.clearAll();
        currID = 1;
    }

    public int getCurrID() {
		return currID;
	}


    public void addRow(ActionController c )
    {
        ApplTableBO bo = new ApplTableBO(currID, c.getCurrentScript());    
        super.addRow(bo);
        currID++;
    }

    public void changeResult(final int rowID, final String result)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                ApplTableBO bo = (ApplTableBO) getBO(rowID);
                bo.setResult(result);
                modifyRow(rowID, bo);
            }
        });
    }

    public void changeStatus(final int rowID, final String result)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
        	public void run()
            {
                ApplTableBO bo = (ApplTableBO) getBO(rowID);         
                bo.setResult(result);              
                ConditionSheetTable.this.getModel().setValueAt(result,rowID,8);
            }
        });
    }

    public void changeCostTime(final int rowID, final String costTime)
   {
       SwingUtilities.invokeLater(new Runnable()
       {
           public void run()
           {
               ApplTableBO bo = (ApplTableBO) getBO(rowID);
               bo.setCostTime(costTime);
               ConditionSheetTable.this.getModel().setValueAt(costTime,rowID,9);
           }
       });
   }
    public void changeRunPhase(final int rowID, final String starttime,final String endtime)
    {
    	SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                ApplTableBO bo = (ApplTableBO) getBO(rowID);
                bo.setRunTime(starttime,endtime);
                String str  = starttime+"/"+endtime;
                ConditionSheetTable.this.getModel().setValueAt(str,rowID,10);
            }
        });
    }

    public ApplExecutCase getApplExecutCase(int row)
    {
        return ((ApplTableBO)super.getBO(row)).getApplExecutCase();
    }


    class ApplTableBO
            extends XFTableBO
    {
       // private int id ;
        private ApplExecutCase script;

        private String result = "";
        private String costTime = "";
        private String runTime = "";
        public ApplTableBO( int id , ApplExecutCase script )
        {
            //this.id = id;
            this.script = script;
        }

        public ApplExecutCase getApplExecutCase()
        {
            return script;
        }


        public String getResult()
        {
            return (result == null) ? "" : script.getTestResultDescr();
        }


        public Object[] getItems()
        {
        	return new Object[]
                    {
                    script.getFrmCase().getStepsId(),
                    script.getFrmCase().getScriptId(),changstr(script.getFrmCase().getCaseDetials(),22),changstr(script.getFrmCase().getDescrip(),17),
                    changstr(script.getFrmCase().getTestContent(),41),script.getFrmCase().getTestDate(),               
                    script.getFrmCase().getTestPhase(),script.getStepTypeDescr(),script.getTestResultDescr(),costTime,runTime};
        }
        public void setResult(String result)
        {
            this.result = result;
        }

       
        public void setCostTime( String time )
        {
            this.costTime = time;
        }
        
        public void setRunTime( String starttime,String endtime )
        {
            this.runTime = starttime+"/"+endtime;
        }
        public String getTableField(int col)
        {
            return "";
        }
    }

    public String changstr(String str,int len)
    {
    	if(str==null||str.equals(""))
    		return "";
    	if(str.length()>len){
    		str = str.substring(0, len-3);
    		str = str.concat("...");
    	}
    	return str;
    }
}
