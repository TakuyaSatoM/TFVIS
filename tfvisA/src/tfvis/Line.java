package tfvis;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Line implements tfvisConstants {

	private int m_Indent;
	private ArrayList<Integer> m_Event;// イベントリスト
	private ArrayList<String> m_TargetList;
	private String m_Target;// 更新する変数リスト
	private ArrayList<String> m_Uses;// 参照する変数リスト
	private String m_Code;// ｿｰｽｺｰﾄﾞ
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
	 * 行が、特定のイベントを持つかを判定する
	 * 
	 * @param eID
	 *            イベントID
	 * @return trueならイベントを持つ
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
	 * 更新する変数の追加
	 * 
	 * @param text
	 *            変数名
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
	 * ソースコードを追加
	 * 
	 * @param text
	 *            追加するコード
	 */
	void addCode(String text) {
		m_Code += text;
	}

	/**
	 * イベントを登録
	 * 
	 * @param id
	 *            イベントのID
	 */
	void addEvent(int id) {
		m_Event.add(id);
	}

	void addEventTop(int id) {
		m_Event.add(0, id);
	}

	/**
	 * 参照変数を追加
	 * 
	 * @param text
	 *            変数名
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
	 * 行データを出力// イベント番号#更新する変数#参照する変数#行別コード#
	 */
	void fileOutput(PrintWriter fout) {

		// イベント番号リストを出力
		for (int event : m_Event) {
			fout.print(event + ",");
		}
		if (m_Event.size() == 0) {
			fout.print(Ev_Other + ",");
		}

		fout.print(DLM);

		// ターゲット変数を出力
		if (m_Target.length() > 0) {
			for (String target : m_TargetList) {
				fout.print(target + ",");
			}
		}
		fout.print(DLM);

		// 参照変数を出力
		for (String target : m_Uses) {
			fout.print(target + ",");
		}
		fout.print(DLM);

		// コード出力
		fout.println(Tools.getIndetStr(m_Indent) + m_Code + DLM);

	}
}
