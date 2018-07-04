package sse.ngts.testrobot.application.creatExecute.ApplCaseDetails;

import java.util.ArrayList;
import java.util.HashSet;

import sse.ngts.testrobot.engine.app.ApplConfig;
import sse.ngts.testrobot.engine.unit.ApplCase;



public interface ApplCaseScript {
	
	/*
	 * �������ܣ�
	 * ���ݳ����б�������ȡ�����ļ����ѳ����ļ��������������б��д��ڵ���������ϸ��Ϣȡ�������ŵ�������
	 * �������룺
	 * String pathDirc          ���������ļ����·��
	 * Hashtable pathCfg        ����������Ӧ��Ŀ¼
	 * HashSet<String>caseList  ���������б�
	 * HashSet<String>sceneList ���������б�
	 * ��������ֵ��
	 *     ��
	 */
	
	public Boolean readCaseDetails(String pathDirc,ApplConfig pathCfg,HashSet<String>caseList,HashSet<String>sceneList);
	
	/*
	 * �������ܣ��������е�����������ĵ���
	 * �������룺
	 * ArrayList<ApplCase> in  ����Ҫ���������
	 * �����
	 * String filePath         ��������ļ�·��
	 * ��������ֵ��
	 *     ��
	 * 
	 */
	public void outPutCaseDetails(ArrayList<ApplCase> in, String filePath);

	public ArrayList<ApplCase> getCaseScript();
}