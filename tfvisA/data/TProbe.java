
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
	static final int CALL_METHOD = 110;

	static final int RETURN = 150;

	static final int ROUTE = 170;

	static final int GENERATE_INSTANCE = 180;
	static final int UPDATE_INSTANCE = 190;

	static final int UPDATE_INT = 200;
	static final int UPDATE_INTARRAY = 201;

	static final int UPDATE_DOUBLE = 210;
	static final int UPDATE_DOUBLEARRAY = 211;

	static final int UPDATE_STRING = 220;
	static final int UPDATE_STRINGARRAY = 221;

	static final int UPDATE_BYTE = 230;
	static final int UPDATE_BYTEARRAY = 231;

	static final int UPDATE_SHORT = 240;
	static final int UPDATE_SHORTARRAY = 241;

	static final int UPDATE_LONG = 250;
	static final int UPDATE_LONGARRAY = 251;

	static final int UPDATE_CHAR = 260;
	static final int UPDATE_CHARARRAY = 261;

	static final int UPDATE_FLOAT = 270;
	static final int UPDATE_FLOATARRAY = 271;

	static final int UPDATE_BOOLEAN = 280;
	static final int UPDATE_BOOLEANARRAY = 281;

	static final int LOOP = 300;
	static final int LOOPNEXT = 301;
	static final int LOOPEND = 302;

	static final int Try = 380;
	static final int Catch = 390;

	static final int Switch = 160;
	static final int Case = 162;

	static final int INPUT = 460;

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
		// �t�@�C����������
		if (m_MethodFile.exists()) {
			m_MethodFile.delete();
		}
		try {
			m_MethodFile.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// ���\�b�h�̊J�n
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

	// ���\�b�h�̊J�n
	static public void Input_StartMethod(int insID, int methodid, int methodexe, int line) {
		input_common(START_METHOD, insID, methodid, methodexe, line);
	}

	// ���\�b�h�̏I��
	static public void Input_EndMethod(int insID, int methodid, int methodexe, int line) {
		input_common(END_METHOD, insID, methodid, methodexe, line);
	}

	static public void Input_MethodCall(int insID, int methodid, int methodexe, int line) {
		input_common(CALL_METHOD, insID, methodid, methodexe, line);
	}

	static public void Input_DataRead(int insID, int methodid, int methodexe, int line) {
		input_common(DATAREAD, insID, methodid, methodexe, line);
	}

	// �X�V-int�z��
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

	// �X�V-String�z��
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

	// �X�V-double�z��
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

	// �X�V-byte�z��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, byte[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_BYTEARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
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

	// �X�V-short�z��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, short[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_SHORTARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
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

	// �X�V-long�z��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, long[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_LONGARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
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

	// �X�V-char�z��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, char[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_CHARARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
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

	// �X�V-float�z��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, float[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_FLOATARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
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

	// �X�V-boolean�z��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, boolean[] array,
			boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.print(UPDATE_BOOLEANARRAY + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM);
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

	// �X�V-int�P��
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

	// �X�V-String�P��
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

	// �X�V-double�P��
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

	// �X�V-byte�P��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, byte var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_BYTE + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + name
					+ "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// �X�V-short�P��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, short var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_SHORT + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + name
					+ "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// �X�V-long�P��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, long var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_LONG + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + name
					+ "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// �X�V-char�P��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, char var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_CHAR + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + name
					+ "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// �X�V-float�P��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, float var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_FLOAT + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM + name
					+ "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// �X�V-boolean�P��
	static public void Input_Update(int insID, int methodid, int methodexe, int line, String name, boolean var,
			String infs, boolean inputState) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(UPDATE_BOOLEAN + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM
					+ name + "," + var + "," + infs + "," + DELIM + inputState + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// �������E-int
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

	// �Q��
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

	// ���̑�
	static public void Input_Other(int insID, int methodid, int methodexe, int line) {
		input_common(OTHER, insID, methodid, methodexe, line);
	}

	// ���[�v�J�n
	static public void Input_Loop(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(LOOP + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// ���[�v����
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

	// ���[�v�I��
	static public void Input_LoopEnd(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(LOOPEND + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// try����
	static public void Input_Try(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Try + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// catch����
	static public void Input_Catch(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Catch + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// switch��
	static public void Input_Switch(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Switch + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// case��
	static public void Input_Case(int insID, int methodid, int methodexe, int line) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(m_MethodFile, true));
			pw.println(Case + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// �C���X�^���X�X�V
	static public void Update_Instance(int insID, int methodid, int methodexe, int line, String name, Object instance) {
		String str = "";

		str += name + ",";
		if (instance != null) {
			str += instance.getClass().getSimpleName() + ",";
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
			pw.println(UPDATE_INSTANCE + DELIM + insID + DELIM + methodid + DELIM + methodexe + DELIM + line + DELIM
					+ str + DELIM);
			pw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	 //�C���X�^���X�X�V
	/*
	static public void Generate_Instance(int insID, int methodid, int methodexe, int line, String name,
			Object instance) {
		String str = "";

		str += name + ",";

		str += instance.getClass().getSimpleName() + ",";
		str += instance.hashCode() + ",";
		int fieldCountNum = instance.getClass().getDeclaredFields().length;
		str += fieldCountNum + ",";
		for (Field field : instance.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				str += field.getType() + ",";
				str += field.getName() + ",";
				str += field.get(instance) + ",";
			} catch (NullPointerException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
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
*/
}