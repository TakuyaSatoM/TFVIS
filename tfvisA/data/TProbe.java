
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

public class TProbe {

	static File m_MethodFile;

	static final int START_CLASS = 0;
	static final int END_CLASS = 1;
	static final int START_METHOD = 100;
	static final int END_METHOD = 101;

	static final int RETURN = 150;

	static final int ROUTE = 170;

	static final int UPDATE_INT = 200;
	static final int UPDATE_INTARRAY = 201;

	static final int UPDATE_DOUBLE = 210;
	static final int UPDATE_DOUBLEARRAY = 211;

	static final int UPDATE_STRING = 220;
	static final int UPDATE_STRINGARRAY = 221;

	static final int INPUT = 230;
	static final int CALL_METHOD = 250;

	static final int GENERATE_INSTANCE = 260;

	static final int LOOP = 300;
	static final int LOOPNEXT = 301;
	static final int LOOPEND = 302;

	static final int Try = 380;
	static final int Catch = 390;

	static final int Switch = 160;
	static final int Case = 162;

	static final int DATAREAD = 500;

	static final int LIFELIMIT = 680;

	static final int OTHER = 999;

	static final String DELIM = "#";
	static int m_ExeCount = 0;

	static public int GetExe() {
		return m_ExeCount++;
	}

	static public void Init() {
		m_MethodFile = new File("tData\\Method.txt");
		// ファイルを初期化
		if (m_MethodFile.exists()) {
			m_MethodFile.delete();
		}
		try {
			m_MethodFile.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// メソッドの開始
	static private void input_common(int type, int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(type + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	static public void Input_Route(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(ROUTE + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// メソッドの開始
	static public void Input_StartMethod(int insID, int methodid, int methodexe, int line) {
		input_common(START_METHOD, insID, methodid, methodexe, line);
	}

	// メソッドの終了
	static public void Input_EndMethod(int insID, int methodid, int methodexe, int line) {
		input_common(END_METHOD, insID, methodid, methodexe, line);
	}

	static public void Input_MethodCall(int insID, int methodid, int methodexe, int line) {
		input_common(CALL_METHOD, insID, methodid, methodexe, line);
	}

	static public void Input_DataRead(int insID, int methodid, int methodexe, int line) {
		input_common(DATAREAD, insID, methodid, methodexe, line);
	}

	// 更新-int配列
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, int[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_INTARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
			pw.print(line + DELIM + name + "," + array.length);
			for (int i = 0; i < array.length; i++) {
				pw.print("," + array[i]);
			}
			pw.println("," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 更新-String配列
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, String[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_STRINGARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
			pw.print(line + DELIM + name + "," + array.length);
			for (int i = 0; i < array.length; i++) {
				pw.print("," + array[i]);
			}
			pw.println("," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 更新-double配列
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, double[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_DOUBLEARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
			pw.print(line + DELIM + name + "," + array.length);
			for (int i = 0; i < array.length; i++) {
				pw.print("," + array[i]);
			}
			pw.println("," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 更新-int単体
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, int var, String infs,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_INT + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + name
					+ "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 更新-String単体
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, String var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_STRING + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM
					+ name + "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 更新-double単体
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, double var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_DOUBLE + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM
					+ name + "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 生存限界-int
	static public void Input_LifeLimit(int insID, int methodid, int methodexe, int line, String name) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(LIFELIMIT + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + name
					+ DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// 更新-int単体
	static public void Input_Read(int insID, int methodid, int methodexe, int line, String infs) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(DATAREAD + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + infs
					+ "," + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// その他
	static public void Input_Other(int insID, int methodid, int methodexe, int line) {
		input_common(OTHER, insID, methodid, methodexe, line);
	}

	// ループ開始
	static public void Input_Loop(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(LOOP + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// ループ周回
	static public void Input_LoopNext(int insID, int methodid, int methodexe, int line, int object) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(LOOPNEXT + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + object
					+ DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// ループ終了
	static public void Input_LoopEnd(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(LOOPEND + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// try処理
	static public void Input_Try(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Try + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// catch処理
	static public void Input_Catch(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Catch + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// switch文
	static public void Input_Switch(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Switch + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// case文
	static public void Input_Case(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Case + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// インスタンス生成
	static public void Generate_Instance(int insID, int methodid, int methodexe, int line, String name,
			Object instance) {
		String str = "";

		if (instance != null) {
			str += name + ",";
			str += instance.getClass().getName() + ",";
			str += instance.hashCode() + ",";
			int fieldCountNum = instance.getClass().getDeclaredFields().length;
			str += fieldCountNum + ",";
			for (Field field : instance.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				str += field.getName() + ",";
			}
		}

		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(GENERATE_INSTANCE + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM
					+ str + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}