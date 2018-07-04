package sse.ngts.testrobot.application.execute.ApplAutoExecute;

import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ApplExecuteProcessImpl;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;

public interface ApplAutoExecute {

	/**
	 * 自动执行步骤
	 * @param step －－步骤详情
	 * @return ：boolean--执行是否成功
	 */
	public boolean  autoExec(ApplExecutCase step);
	public void setExecuteProcess(ApplExecuteProcessImpl executeProcess);

}
