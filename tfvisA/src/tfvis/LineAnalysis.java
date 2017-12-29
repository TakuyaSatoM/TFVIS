package tfvis;

import java.util.ArrayList;

public class LineAnalysis implements tfvisConstants {

	/**
	 * �g�[�N�����X�g���\�[�X�R�[�h�ɐ��`�E�R�[�h�̍\�������(�N���X��)
	 * 
	 * @param pro
	 *            �v���O�����S�̂̃f�[�^�N���X
	 * @param m_Token
	 *            �N���X�̃g�[�N�����X�g(�v)
	 * @param m_Line
	 *            �s�f�[�^�����郊�X�g(��)
	 */
	static public void exe(Program pro, ArrayList<Token> m_Token, ArrayList<Line> m_Line) {

		// �R�[�h�o�^
		int indent = 0;
		boolean lineHead = true;
		Line nowLine = new Line();
		Line beforeLine = nowLine;
		// �ŏ��̍s�Ƀ��\�b�h�J�n�C�x���g��o�^
		nowLine.addEvent(Ev_MethodStart);
		boolean varRefer = false;
		ArrayList<String> referVars = new ArrayList<String>();
		int m_NowMethodID = -1;
		String className = "";

		for (int k = 0; k < m_Token.size(); k++) {

			boolean changeLine = false;

			int nowID = m_Token.get(k).m_ID;
			String state = m_Token.get(k).m_State;

			if (nowID == CoiDec) {
				className = m_Token.get(k + 1).m_State;
			}

			if (nowID == Method + Identifier || nowID == Constructor + Identifier) {
				m_NowMethodID = pro.getMethodID(state, className);
				nowLine.addEvent(Ev_MethodStart);
			}

			// ��͏I��
			if (nowID == CoiDec + End) {
				break;
			}

			// ���\�b�h�I��
			if (nowID == Method + End || nowID == Constructor + End) {
				m_NowMethodID = -1;
				beforeLine.addEvent(Ev_MethodEnd);
				continue;
			}

			// ���[�v�I��
			if (nowID == For + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_ForEnd);
				continue;
			}

			// �C�x���g�o�^
			if (nowID == Return) {
				nowLine.addEvent(Ev_Return);
			}

			if (nowID == For) {
				nowLine.addEvent(Ev_For);
			}

			if (nowID == If) {
				nowLine.addEvent(Ev_Route);
			}

			if (nowID == Else + End) {
				continue;
			}
			if (nowID == If + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_IfEnd);
				continue;
			}
			if (nowID == Branch + End) {
				/* m_Line.get(m_Line.size()-1).addEvent(Ev_BranchEnd) */;
				continue;
			}

			if (nowID == While) {
				nowLine.addEvent(Ev_While);
			}
			if (nowID == While + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_WhileEnd);
				continue;
			}

			if (nowID == Try) {
				nowLine.addEvent(Ev_Try);
			}
			if (nowID == Try + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_TryEnd);
				continue;
			}
			if (nowID == Catch) {
				nowLine.addEvent(Ev_Catch);
			}
			if (nowID == Catch + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_CatchEnd);
				continue;
			}
			if (nowID == Finally) {
				nowLine.addEvent(Ev_Finally);
			}
			if (nowID == Finally + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_FinallyEnd);
				continue;
			}

			if (nowID == Switch) {
				nowLine.addEvent(Ev_Switch);
			}
			if (nowID == Switch + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_SwitchEnd);
				continue;
			}
			if (nowID == Case) {
				nowLine.addEvent(Ev_Case);
			}
			if (nowID == Case + End) {
				continue;
			}
			if (nowID == Default) {
				nowLine.addEvent(Ev_Case);
			}
			if (nowID == Default + End) {
				continue;
			}

			if (nowID == ObjectExp) {
				nowLine.addEvent(Ev_GenerateInstance);
				nowLine.setTarget(m_Token.get(k - 2).m_State);
				nowLine.deleteUse(m_Token.get(k - 2).m_State);
			}

			if (nowID == Do) {
				nowLine.addEvent(Ev_Do);
			}
			if (nowID == Do + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_DoEnd);
			}
			if (nowID == Do_While + End) {
				m_Line.get(m_Line.size() - 1).addEvent(Ev_DoWhileEnd);
				continue;
			}

			if (nowID == ExplicitCons + Super) {
				nowLine.addEvent(Ev_Super);
			}

			if (state.equals("readLine")) {
				nowLine.addEventTop(Ev_Input);
			}

			// =��p�����X�V
			if (nowLine.hasUpdateEvent() == false) {

				// for�������������̓o�^
				if (state.equals("=") && nowLine.checkHasEvent(Ev_For) && nowLine.getLoopInitTargetName().equals("")) {
					boolean tmp = false;
					if (Vars.checkVarsDec(m_Token.get(k - 2).m_State)) {
						tmp = true;
					}
					nowLine.setLoopInitTarget(m_Token.get(k - 1).m_State, tmp);
				}

				// =�ɂ��X�V
				if (state.equals("=")) {

					int tID = pro.checkVarsUpdate_TFVISID(new VarsQue(m_Token.get(k - 1).m_State));
					if (tID >= 0) {
						nowLine.setTarget(m_Token.get(k - 1).m_State);
						nowLine.deleteUse(m_Token.get(k - 1).m_State);
						nowLine.addEvent(tID);
					} else if (m_Token.get(k - 1).m_State.equals("]")) {

						String varfName = "";
						String varsName = "";
						for (int j = k - 1; j >= 0; j--) {
							varfName = m_Token.get(j).m_State + varfName;
							if (m_Token.get(j).m_State.equals("[")) {
								varsName = m_Token.get(j - 1).m_State;
								varfName = varsName + varfName;
								break;
							}
						}
						int uID = pro.checkVarsUpdate_TFVISID(new VarsQue(varsName));
						if (uID >= 0) {
							nowLine.setTarget(varfName);
							nowLine.deleteUse(varfName);
							nowLine.addEvent(uID - ArrayMode);
						}

					}
				}
				// += -= *= /=�ɂ��X�V
				if (state.equals("+=") || state.equals("-=") || state.equals("*=") || state.equals("/=")) {

					int tID = pro.checkVarsUpdate_TFVISID(new VarsQue(m_Token.get(k - 1).m_State));
					if (tID >= 0) {
						nowLine.setTarget(m_Token.get(k - 1).m_State);
						nowLine.addEvent(tID);
					} else if (m_Token.get(k - 1).m_State.equals("]")) {

						String varfName = "";
						String varsName = "";
						for (int j = k - 1; j >= 0; j--) {
							varfName = m_Token.get(j).m_State + varfName;
							if (m_Token.get(j).m_State.equals("[")) {
								varsName = m_Token.get(j - 1).m_State;
								varfName = varsName + varfName;
								break;
							}
						}
						int uID = pro.checkVarsUpdate_TFVISID(new VarsQue(varsName));
						if (uID >= 0) {
							nowLine.setTarget(varfName);

							nowLine.addEvent(uID - ArrayMode);
						}

					}
				}

				// �C���N�������g�A�f�N�������g�ɂ��X�V
				if (state.equals("++") || state.equals("--")) {
					int tID = pro.checkVarsUpdate_TFVISID(new VarsQue(m_Token.get(k - 1).m_State));
					if (tID >= 0) {
						nowLine.setTarget(m_Token.get(k - 1).m_State);
						nowLine.addUse(m_Token.get(k - 1).m_State);
						nowLine.addEvent(tID);
					}
				}
				// �Q�Ƃ̌��o
				if (pro.checkVarsUpdate_TFVISID(new VarsQue(m_Token.get(k).m_State)) >= 0) {
					varRefer = true;
					referVars.add(m_Token.get(k).m_State);
				}
			} else if (nowLine.checkHasEvent(Ev_For)) {// for�C�x���g�s��

				if (state.equals("++") || state.equals("--")) {// �C���N�������g�A�f�N�������g
					int tID = pro.checkVarsUpdate_TFVISID(new VarsQue(m_Token.get(k - 1).m_State));
					if (tID >= 0) {
						nowLine.addUse(m_Token.get(k - 1).m_State);
					}
				}
			}

			// ���\�b�h�Ăяo���̌��o//func();
			eventCheck_CallMethod(pro, m_Token.get(k), nowLine);

			// state���ϐ��̌^�ł���ꍇ(int or double�E�E�E)
			if (Vars.checkVarsDec(state)) {

				// �ϐ��̓o�^Identifier
				if (!m_Token.get(k + 1).m_State.equals("[") && m_Token.get(k + 1).m_ID != Identifier + Method) {
					pro.setVar(new Vars(state, m_Token.get(k + 1).m_State));
				}
				// �ϐ��̓o�^Identifier�z��
				if (m_Token.get(k + 1).m_State.equals("[") && m_Token.get(k + 2).m_State.equals("]")
						&& m_Token.get(k + 1).m_ID != Identifier + Method) {
					pro.setVar(new Vars(state, m_Token.get(k + 3).m_State, true));
				}

				// �����̏ꍇ
				if (nowLine.checkHasEvent(Ev_MethodStart)) {

					if (!m_Token.get(k + 1).m_State.equals("[")) {
						int tID = pro.checkVarsUpdate_TFVISID(new VarsQue(m_Token.get(k + 1).m_State));
						if (tID >= 0) {
							nowLine.setTarget(m_Token.get(k + 1).m_State);
							nowLine.addEvent(tID);
						}
					} else {
						int tID = pro.checkVarsUpdate_TFVISID(new VarsQue(m_Token.get(k + 3).m_State));
						if (tID >= 0) {
							nowLine.setTarget(m_Token.get(k + 3).m_State);
							nowLine.addEvent(tID);
						}

					}
				}
			}

			if (k < m_Token.size() - 1 && m_Token.get(k + 1).m_State.equals("{") == true
					&& !m_Token.get(k).m_State.equals("=")) {

				nowLine.addCode(/* getIndetStr(indent)+ */state + "{");
				k++;
				indent++;
				lineHead = true;

				changeLine = true;
			} else if (state.equals("}") == true) {
				indent--;
				lineHead = true;
				nowLine.setIndent(indent);
				nowLine.addCode("}");

				changeLine = true;
			} else {
				if (lineHead) {
					nowLine.setIndent(indent);
					;
					lineHead = false;
				}

				// *extends //*�̏ꏊ�ɋ�
				if (m_Token.get(k).m_State.equals("extends")) {
					nowLine.addCode(" ");
				}

				// *implements //*�̏ꏊ�ɋ�
				if (m_Token.get(k).m_State.equals("implements")) {
					nowLine.addCode(" ");
				}

				nowLine.addCode(state);

				if (Tools.checkNeedSpace(m_Token, k)) {
					nowLine.addCode(" ");
				}

				for (int endID : CodeEndList) {
					if (endID == nowID) {
						lineHead = true;
						changeLine = true;
						break;
					}
				}
			}

			// �Q�ƕϐ��̒��o
			if (!nowLine.checkHasEvent(Ev_MethodStart) && !nowLine.checkHasEvent(Ev_For)) {

				int uID = pro.checkVarsUpdate_TFVISID(new VarsQue(state));

				if (uID >= 0) {
					String use = state;
					if (m_Token.get(k + 1).m_State.equals("[")) {
						for (int j = k + 1; j < m_Token.size(); j++) {
							use += m_Token.get(j).m_State;
							if (m_Token.get(j).m_State.equals("]")) {
								break;
							}
						}
					}
					nowLine.addUse(use);
				}
			}

			// �s�̐ؑ�
			if (changeLine) {
				m_Line.add(nowLine);
				nowLine.setMethodID(m_NowMethodID);

				beforeLine = nowLine;
				nowLine = new Line();
				varRefer = false;
				referVars.clear();
			}

		}

	}

	/**
	 * ���\�b�h�Ăяo���C�x���g���m�E�o�^
	 * 
	 * @param token
	 *            �Y���̃g�[�N��
	 * @param line
	 *            �Ώۂ̍s
	 */
	static public void eventCheck_CallMethod(Program pro, Token token, Line line) {

		if (token.m_ID == CoiType1 + Identifier) {
			return;
		}

		if (line.checkHasEvent(Ev_MethodStart) == true) {
			return;
		}

		for (FileData file : pro.m_Files) {
			for (StrClass indexClass : file.getClassData()) {

				if (indexClass.hasMethod(token.m_State)) {
					line.addEvent(Ev_CallMethod);
					return;
				}
				for (StrClass subClass : indexClass.getSubClass()) {
					if (subClass.hasMethod(token.m_State)) {
						line.addEvent(Ev_CallMethod);
						return;
					}
				}
			}
		}

	}

}
