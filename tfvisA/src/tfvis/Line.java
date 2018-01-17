package tfvis;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Line implements tfvisConstants {

	private int m_Indent;
	private ArrayList<Integer> m_Event;// �C�x���g���X�g
	private ArrayList<String> m_TargetList;
	private String m_Target;// �X�V����ϐ����X�g
	private ArrayList<String> m_Uses;// �Q�Ƃ���ϐ����X�g
	private String m_Code;// �������
	private int m_MethodID;
	private int m_TotalLineID;

	private String m_LoopInitTargetName;
	private boolean m_LoopInitTargetIsTmp;

	public Line() {
		m_Indent = 0;
		m_Event = new ArrayList<Integer>();
		m_TargetList = new ArrayList<String>();
		m_Uses = new ArrayList<String>();
		m_Code = "";
		m_Target = "";
		m_MethodID = -1;
		m_LoopInitTargetName = "";
		m_LoopInitTargetIsTmp = false;
		m_TotalLineID = 0;
	}

	void setLoopInitTarget(String var, boolean tmp) {
		m_LoopInitTargetName = var;
		m_LoopInitTargetIsTmp = tmp;
	}

	String getLoopInitTargetName() {
		return m_LoopInitTargetName;
	}

	boolean isLoopInitTargetTmp() {
		return m_LoopInitTargetIsTmp;
	}

	void addTotalLineID(int nowLineID) {
		m_TotalLineID += nowLineID;
		return;
	}

	int getTotalLineID() {
		return m_TotalLineID;
	}

	void setMethodID(int id) {
		m_MethodID = id;
	}

	int getMethodID() {
		return m_MethodID;
	}

	ArrayList<Integer> getEventList() {
		return m_Event;
	}

	void setIndent(int in) {
		m_Indent = in;
	}

	int getIndent() {
		return m_Indent;
	}

	String getIndentText() {
		return Tools.getIndetStr(m_Indent);
	}

	String getCode() {
		return m_Code;
	}

	/**
	 * �s���A����̃C�x���g�������𔻒肷��
	 * 
	 * @param eID
	 *            �C�x���gID
	 * @return true�Ȃ�C�x���g������
	 */
	boolean checkHasEvent(int eID) {

		for (int indexID : m_Event) {

			if (indexID == eID) {
				return true;
			}
		}

		return false;
	}

	boolean hasUpdateEvent() {
		for (int indexID : m_Event) {
			if (Tools.isUpdateEvent(indexID)) {
				return true;
			}
		}

		return false;
	}

	boolean hasUpdateEvent_NotArray() {
		for (int indexID : m_Event) {
			if(!Tools.isPrimitiveUpdateEvent(indexID)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * �X�V����ϐ��̒ǉ�
	 * 
	 * @param text
	 *            �ϐ���
	 */
	void setTarget(String text) {
		m_Target = text;
		m_TargetList.add(m_Target);
	}

	String getTarget() {
		return m_Target;
	}

	ArrayList<String> getTargetList() {
		return m_TargetList;
	}

	/**
	 * �\�[�X�R�[�h��ǉ�
	 * 
	 * @param text
	 *            �ǉ�����R�[�h
	 */
	void addCode(String text) {
		m_Code += text;
	}

	/**
	 * �C�x���g��o�^
	 * 
	 * @param id
	 *            �C�x���g��ID
	 */
	void addEvent(int id) {
		m_Event.add(id);
	}

	void addEventTop(int id) {
		m_Event.add(0, id);
	}

	/**
	 * �Q�ƕϐ���ǉ�
	 * 
	 * @param text
	 *            �ϐ���
	 */
	void addUse(String text) {

		for (String use : m_Uses) {
			if (use.equals(text)) {
				return;
			}
		}

		m_Uses.add(text);
	}

	boolean deleteUse(String text) {

		for (int i = 0; i < m_Uses.size(); i++) {
			if (m_Uses.get(i).equals(text)) {
				m_Uses.remove(i);
				return true;
			}
		}
		return false;
	}

	String getUse(int num) {
		return m_Uses.get(num);
	}

	int getUseNum() {
		return m_Uses.size();
	}

	/**
	 * �s�f�[�^���o��// �C�x���g�ԍ�#�X�V����ϐ�#�Q�Ƃ���ϐ�#�s�ʃR�[�h#
	 */
	void fileOutput(PrintWriter fout) {

		// �C�x���g�ԍ����X�g���o��
		for (int event : m_Event) {
			fout.print(event + ",");
		}
		if (m_Event.size() == 0) {
			fout.print(Ev_Other + ",");
		}

		fout.print(DLM);

		// �^�[�Q�b�g�ϐ����o��
		if (m_Target.length() > 0) {
			for (String target : m_TargetList) {
				fout.print(target + ",");
			}
		}
		fout.print(DLM);

		// �Q�ƕϐ����o��
		for (String target : m_Uses) {
			fout.print(target + ",");
		}
		fout.print(DLM);

		// �R�[�h�o��
		fout.println(Tools.getIndetStr(m_Indent) + m_Code + DLM);

	}
}
