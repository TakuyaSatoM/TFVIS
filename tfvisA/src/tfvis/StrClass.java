package tfvis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class StrClass implements tfvisConstants {

	private boolean m_HasMainMethod;
	private int m_ID;
	private String m_Name;
	private ArrayList<Method> m_Methods;
	private ArrayList<Token> m_Token;
	private ArrayList<Line> m_Line;
	private ArrayList<StrClass> m_SubClasses;

	public boolean doesHasMainMethod() {
		return m_HasMainMethod;
	}

	public void setMyID(int id) {
		m_ID = id;
	}

	public int getHasMethodNum() {
		return m_Methods.size();
	}

	public String getName() {
		return m_Name;
	}

	public void setSubClass(StrClass classData) {
		m_SubClasses.add(classData);
	}

	public ArrayList<StrClass> getSubClass() {
		return m_SubClasses;
	}

	public StrClass getSubClasse(int i) {
		return m_SubClasses.get(i);
	}

	public boolean hasSubClass() {
		return !m_SubClasses.isEmpty();
	}

	public int getSubClassNum() {
		return m_SubClasses.size();
	}

	public int getMethodID(String methodName) {

		for (Method index : m_Methods) {

			if (index.getShortName().equals(methodName)) {
				return index.getID();
			}
		}
		return -1;
	}

	public void setMethodID(int offset) {

		for (Method index : m_Methods) {
			index.setID(offset++);
		}

	}

	/**
	 * nameという名前のメソッドを持っているか
	 */
	public boolean hasMethod(String name) {

		for (Method index : m_Methods) {
			if (index.getShortName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * クラスデータにメソッドデータを登録する
	 * 
	 * @param data
	 * @param offset
	 */
	private void registMethod(AnalysisData data, int offset, int type) {
		m_Methods.add(new Method(data, offset, type));
	}

	public void outputLineFile(Program pro) {

		LineAnalysis.exe(pro, m_Token, m_Line);

		for (Method index : m_Methods) {
			index.outputLineFile(m_Line);
		}
	}

	public StrClass(AnalysisData data) {
		m_Methods = new ArrayList<Method>();
		m_HasMainMethod = false;
		m_ID = -1;

		m_Line = new ArrayList<Line>();
		m_SubClasses = new ArrayList<StrClass>();

		m_Token = new ArrayList<Token>();
		for (int i = 0; i < data.size(); i++) {
			m_Token.add(new Token(data.getId(i), data.getState(i)));
		}

		// クラス名検索
		for (int i = 0; i < data.size(); i++) {
			if (data.getId(i) == CoiDec + Static) {
				m_Name = data.getState(i);
				break;
			}
		}

		// mainメソッド検索
		for (int i = 0; i < data.size(); i++) {
			if (data.getId(i) == Method + Static && data.getState(i).equals("main")) {
				m_HasMainMethod = true;
				// mainメソッド登録
				registMethod(data, i, Mt_Main);
				break;
			}
		}
		// メソッド検索
		for (int i = 0; i < data.size(); i++) {

			// メソッドの登録
			if (data.getId(i) == Method + Static && !data.getState(i).equals("main")) {
				registMethod(data, i, Mt_Normal);
			}

			// コンストラクタの登録
			if (data.getId(i) == Constructor + Static) {
				registMethod(data, i, Mt_Constructor);
			}

		}

	}

	public void fileOutputProbe(PrintWriter fout) {

		for (int i = 0; i < m_Line.size() - 1; i++) {
			if (m_Line.get(i).getMethodID() >= 0) {
				continue;
			}
			fout.println(m_Line.get(i).getCode());
		}

		fout.println("  static final int TP_CLASSID = " + m_ID + ";");
		fout.println("  static int TP_INSTANCEID = -1;");
		fout.println();

		if (this.hasSubClass()) {
			for (StrClass subClass : m_SubClasses) {
				for (int i = 0; i < subClass.m_Line.size() - 1; i++) {
					if (subClass.m_Line.get(i).getMethodID() >= 0) {
						continue;
					}
					fout.println(subClass.m_Line.get(i).getCode());
				}

				for (int i = 0; i < subClass.m_Methods.size(); i++) {
					subClass.m_Methods.get(i).fileOutputProbe(subClass.m_Line, fout);
				}
				fout.println("}");
			}
		}
		for (int i = 0; i < m_Methods.size(); i++) {
			m_Methods.get(i).fileOutputProbe(m_Line, fout);
		}

		fout.println("}");
	}

	public void outputStr(File file) {

		try {
			PrintWriter fout = new PrintWriter(new FileWriter(file, true));
			fout.println(CLASSID + DLM + m_Name + DLM + m_ID + DLM);
			fout.close();

			for (int i = 0; i < m_Methods.size(); i++) {
				m_Methods.get(i).outputStr(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
