package sse.ngts.testrobot.application.execute.ApplExecuteProcess;

import sse.ngts.testrobot.engine.unit.ApplExecutCase;

public interface ApplExecuteProcess {
		
    /**
     * �жϲ����ִ����𣬵�����Ӧ�Ľӿ�ʵ��ִ�й��̣������ݷ���ֵ���ò�����������
     * @param step ����������������
     * @return   boolean  ��������ִ���Ƿ�ɹ�
     */
	public void stepProcess(ApplExecutCase step);
	
	public void setActioncontroller(ActionController actioncontroller);

}
