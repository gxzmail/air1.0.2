package sse.ngts.testrobot.application.execute.ApplAutoExecute;

import sse.ngts.testrobot.application.execute.ApplExecuteProcess.ApplExecuteProcessImpl;
import sse.ngts.testrobot.engine.unit.ApplExecutCase;

public interface ApplAutoExecute {

	/**
	 * �Զ�ִ�в���
	 * @param step ������������
	 * @return ��boolean--ִ���Ƿ�ɹ�
	 */
	public boolean  autoExec(ApplExecutCase step);
	public void setExecuteProcess(ApplExecuteProcessImpl executeProcess);

}
