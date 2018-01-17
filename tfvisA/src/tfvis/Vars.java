package tfvis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vars implements tfvisConstants {

	String m_Type;
	String m_Name;
	boolean m_Array;
	final static List<String> typeList = new ArrayList<>(Arrays.asList("int", "double", "String","byte","short", "long", "char", "float", "boolean"));
	
	Vars(String state, String name) {
		m_Type = state;
		m_Name = name;
		m_Array = false;
	}

	Vars(String state, String name, boolean array) {
		m_Type = state;
		m_Name = name;
		m_Array = array;
	}

	public int getUpdateTFVISID() {

		int tmp = 0;
		if (m_Array) {
			tmp = ArrayMode;
		}

		if (m_Type.equals("int")) {
			return Ev_IntUpdate + tmp;
		}
		if (m_Type.equals("double")) {
			return Ev_DoubleUpdate + tmp;
		}
		if (m_Type.equals("String")) {
			return Ev_StringUpdate + tmp;
		}
		if (m_Type.equals("byte")) {
			return Ev_ByteUpdate + tmp;
		}
		if (m_Type.equals("short")) {
			return Ev_ShortUpdate + tmp;
		}
		if (m_Type.equals("long")) {
			return Ev_LongUpdate + tmp;
		}
		if (m_Type.equals("char")) {
			return Ev_CharUpdate + tmp;
		}
		if (m_Type.equals("float")) {
			return Ev_FloatUpdate + tmp;
		}
		if (m_Type.equals("boolean")) {
			return Ev_BooleanUpdate + tmp;
		}

		return -1;

	}

	static boolean checkVarsDec(String state) {
		return typeList.contains(state);
	}

}
