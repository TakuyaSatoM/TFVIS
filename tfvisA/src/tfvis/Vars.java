package tfvis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Vars implements tfvisConstants {

	String m_State;
	String m_Name;
	boolean m_Array;
	static List<String> typeList = new ArrayList<>(Arrays.asList("int", "double", "String"));

	Vars(String state, String name) {
		m_State = state;
		m_Name = name;
		m_Array = false;
	}

	Vars(String state, String name, boolean array) {
		m_State = state;
		m_Name = name;
		m_Array = array;
	}

	public int getUpdateTFVISID() {

		int tmp = 0;
		if (m_Array) {
			tmp = ArrayMode;
		}

		if (m_State.equals("int")) {
			return Ev_IntUpdate + tmp;
		}
		if (m_State.equals("double")) {
			return Ev_DoubleUpdate + tmp;
		}
		if (m_State.equals("String")) {
			return Ev_StringUpdate + tmp;
		}
		if (typeList.contains(m_State)) {
			return Ev_UpdateInstance;
		}

		return -1;

	}

	static void addType(String state) {
		typeList.add(state);
	}

	static boolean checkVarsDec(String state) {
		return typeList.contains(state);
	}
}
