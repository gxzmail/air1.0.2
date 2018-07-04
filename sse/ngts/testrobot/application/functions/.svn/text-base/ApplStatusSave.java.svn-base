package sse.ngts.testrobot.application.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ActionController;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;
import sse.ngts.testrobot.engine.unit.ApplExecuteConstValues;

public class ApplStatusSave {	
	private String statusFile = ApplExecuteConstValues.outStatusFileName;
	private String statusTmpFile = ApplExecuteConstValues.outStatusTmpFileName;
	private ArrayList<String> status;
	private static ApplStatusSave instance;
	
    public static ApplStatusSave getinstance()
    {
        if (instance == null)
        {
            instance = new ApplStatusSave();
        }
        return instance;
    }
    
	public  void writStatus(ArrayList<ActionController> actionsController)
	{
		System.out.println("print result!--------");
		try {
			
			File file = new File(statusFile);
			if (file.exists())
			{
				file.delete();					
			}
			file.createNewFile();
			PrintWriter out = new PrintWriter(new FileWriter(statusFile,false));
			String str = null;
            int i =0;
			for(i = 0;i<actionsController.size();i++)
			{
				Boolean refed =
					 actionsController.get(i).getCurrentScript().getAttribute(ApplExecutCase.ATTR_REFF_FLAG);
				Boolean result =
					 actionsController.get(i).getCurrentScript().getAttribute(ApplExecutCase.ATTR_SUCCESS_FLAG);
				if(actionsController.get(i).isCurrentControllerWorking())
					continue;
				int j = i+1;
				if(refed)
                {
                	if(result)
                	{
            			str = j+"|"+1;
                		out.println(str);
                	}
                	else
                	{            			
                		str = j+"|"+0;
            		    out.println(str);               		
                	}
                }
			}			
			out.close();
			writeTmp();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

    public void writeTmp()
    {
		File file = new File(statusTmpFile);
		if (file.exists())
		{
			file.delete();					
		}
		try {
			file.createNewFile();
			PrintWriter out = new PrintWriter(new FileWriter(statusTmpFile,false));
            String line = null;
			BufferedReader in = new BufferedReader(
					  new FileReader(statusFile));
			while((line = in.readLine())!=null)
			{
				out.println(line);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
	public synchronized void writStatus(int id, String result)
	{
			try {
			
				File file = new File(statusFile);
				if (file.exists())
				{
					file.delete();					
				}
				file.createNewFile();
				PrintWriter out = new PrintWriter(new FileWriter(statusFile,false));
				String str = null;
				str = id+"|"+result;
                int i =0;
				for(i = 0;i<status.size();i++)
				{
					String str1 = id+"|";
					if(status.get(i).trim().indexOf(str1)!= -1)
					{
						status.set(i, str);
						out.println(status.get(i));
						break;
					}
					out.println(status.get(i));

				}
				if(i>=status.size())
				{
					status.add(str);
					out.println(str);
				}
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			}									
	}
	
	public void readStatus()
	{
		try {
			File file = new File(statusFile);
            int i = 0;
            String line = null;
			if (file.exists())
			{
			BufferedReader in = new BufferedReader(
			  new FileReader(statusFile));
			
            status =  new ArrayList<String>();
            for(i = 0;;i++)
            {
            	if((line = in.readLine())!=null)
            	{
                	status.add(line);
            	}
            	else
            		break;
            }
            in.close();

			}
            if(i == 0)
            {
    			File file1 = new File(statusFile);
    			if (file1.exists())
    			{


            	BufferedReader in1 = new BufferedReader(
  					  new FileReader(statusTmpFile));
            	for(i = 0;;i++)
                {
                	if((line = in1.readLine())!=null)
                	{
                    	status.add(line);
                	}
                	else
                		break;
                }
                in1.close();

    			}

            }
 
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void clear()
	{
		status =null;
		File file = new File(statusFile);
		if (file.exists())
		{
			file.delete();					
		}
		
	}
	public ArrayList<String> getStatus() {
		readStatus();
		return status;
	}

}
