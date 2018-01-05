package tfvis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Method implements tfvisConstants {

	/**
	 * �\����̓g�[�N���N���X
	 * 
	 * @author HN
	 *
	 */

	private static int uniqueID = 0;

	private int m_ID;
	private int m_Type;
	private String m_FullName;
	private String m_ShortName;

	public void setID(int id) {
		m_ID = id;
	}

	public int getID() {
		return m_ID;
	}

	public String getShortName() {
		return m_ShortName;
	}

	public boolean inputState;

	/**
	 * �ʏ�̃��\�b�h�̐����p�R���X�g���N�^
	 * 
	 * @param id
	 * @param data
	 * @param stID
	 * @param type
	 */
	Method(AnalysisData data, int stID, int type) {
		m_Type = type;

		m_ID = -1;
		m_ShortName = data.getState(stID);

		if (m_Type == Mt_Constructor) {
			stID++;
		}
		m_FullName = m_ShortName;

		inputState = false;
	}

	/**
	 * �\�����o��
	 */
	public void outputStr(File file) {
		try {
			// System.out.println(METHODID+DLM+m_ShortName+DLM+m_FullName+DLM+(/*methodIDOffset+*/m_ID)+DLM);
			PrintWriter fout = new PrintWriter(new FileWriter(file, true));
			fout.println(METHODID + DLM + m_ShortName + DLM + m_FullName + DLM + (m_ID) + DLM);
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outputLineFile(ArrayList<Line> lineList) {

		File ssfile = new File(outDir + m_FullName + Exten_Lines);
		// �t�@�C����������
		if (ssfile.exists()) {
			ssfile.delete();
		}
		try {
			ssfile.createNewFile();
		} catch (IOException e) {
			System.out.println(e);
		}

		try {

			PrintWriter fout = new PrintWriter(new FileWriter(ssfile, true));

			for (Line line : lineList) {
				if (line.getMethodID() != m_ID) {
					continue;
				}
				line.fileOutput(fout);

			}
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �v���[�u��}��
	 */

	public void fileOutputProbe(ArrayList<Line> lineList, PrintWriter fout) {

		boolean first = false;
		boolean isTry = false;

		int CatchLoopEnd = 0;
		int LoopEnd = 0;
		int lineID = 0;
		boolean isLoop = false;
		int count = 0;

		Line beforeLine = lineList.get(0);
		ArrayList<Integer> loopStart = new ArrayList<Integer>();
		for (Line line : lineList) {
			count++;

			if (line.getMethodID() != m_ID) {
				continue;
			}

			boolean insertedProbe = false;
			boolean cancelCodeIn = false;
			String indent = line.getIndentText() + " ";

			// before�v���[�u
			for (Integer event : line.getEventList()) {

				if (event == Ev_Route) {
					if (line.getUseNum() > 0) {

						readProbe(line, lineID, fout);
					}
				}

				if (event == Ev_Return) {// ���\�b�h�I��
					readProbe(line, lineID, fout);
					try {
						loopStart.get(loopStart.size() - 1);
						fout.println(indent + "TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
								+ ");");
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					fout.println(indent + "TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
							+ "  );");
					insertedProbe = true;
				}
				if (event == Ev_MethodEnd && beforeLine.checkHasEvent(Ev_Return) == false) {// ���\�b�h�I��
					fout.println(indent + "TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
							+ "  );");
					insertedProbe = true;
				} else if (event == Ev_CallMethod) {// ���\�b�h�Ăяo���v���[�u
					fout.println(
							indent + "TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ");");
					insertedProbe = true;
				} else if (event == Ev_For || event == Ev_While || event == Ev_Do) {// Loop�J�n�v���[�u
					isLoop = true;
					fout.println(indent + "TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ");");

					if (line.getLoopInitTargetName().length() > 0) {
						fout.println(indent + "boolean tvisLoopInit" + uniqueID + "=false;");
						fout.println(indent + "TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
								+ ",\"" + line.getLoopInitTargetName() + "\");");

						fout.print(line.getIndentText() + line.getCode().substring(0, line.getCode().length() - 2));
						fout.println(",TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ", \""
								+ line.getLoopInitTargetName() + "\"," + line.getLoopInitTargetName() + ",\""
								+ line.getLoopInitTargetName() + "\"," + inputState + ")){");
						cancelCodeIn = true;
						insertedProbe = true;
					}
					insertedProbe = true;
					loopStart.add(lineID);
				} else if (event == Ev_ForEnd || event == Ev_WhileEnd || event == Ev_DoEnd) {// Loop�I���v���[�u
					isLoop = false;
					fout.println(indent + "TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ","
							+ loopStart.get(loopStart.size() - 1) + ");");
					fout.println(indent + "isLoop=false;");
					LoopEnd = lineID;
					insertedProbe = true;
				} else if (event == Ev_Switch) {
					fout.println(
							indent + "TProbe.Input_Switch(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ");");
					insertedProbe = true;
				}

				if (event == Ev_CatchEnd && !isLoop) {
					fout.println(indent + "if(isLoop){");
					fout.println(indent + "TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + CatchLoopEnd
							+ "," + CatchLoopEnd + ");");
					fout.println(
							indent + "TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + LoopEnd + ");}");
					insertedProbe = true;
				}
			}

			if (cancelCodeIn == false && !lineList.get(count - 1).checkHasEvent(Ev_Super)) {
				fout.println(line.getIndentText() + line.getCode());
			}

			if (first == false) {
				first = true;
				if (lineList.get(count).checkHasEvent(Ev_Super)) {
					fout.println(lineList.get(count).getIndentText() + lineList.get(count).getCode());
				}
				fout.println(indent + "final int TP_METHODID = " + m_ID + ";");
				fout.println(indent + "int TP_METHODEXE=TProbe.GetExe();	");

				// main���\�b�h
				if (m_Type == Mt_Main) {
					fout.println(indent + "TProbe.Init();");
					fout.println(indent + "int TP_INSTANCEID = -1;");
				} else {
					fout.println(indent + "int TP_INSTANCEID = this.hashCode();");
				}
				fout.println(
						indent + "TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ");");
				fout.println(indent + "boolean isLoop=false;");
				insertedProbe = true;
			}

			// after�v���[�u

			for (Integer event : line.getEventList()) {

				// �ϐ��X�V�n�v���[�u
				if (line.checkHasEvent(Ev_For) == false) {// for���̏����Ƃ��Ă̕ϐ��X�V(�J�E���^)�͕ʏ���(for)
					if (event == Ev_IntUpdate || event == Ev_DoubleUpdate || event == Ev_StringUpdate) {// int�^�X�V

						ArrayList<String> targetList = line.getTargetList();
						String target;
						String target2 = targetList.get(0);
						targetList.remove(0);
						{
							if (line.getTarget().length() > 0) {
								target = "\"" + Tools.changeProbeVars(target2);
								target += "\"";
							} else {
								target = "\"\"";
							}

						}
						String use = "\"";
						{
							for (int i = 0; i < line.getUseNum(); i++) {
								if (i > 0) {
									use += ",";
								}

								use += Tools.changeProbeVars(line.getUse(i));
							}
							if (line.getUseNum() == 0) {
								use = "\"\"";
							} else {
								use += "\"";
							}
						}
						fout.println(indent + "TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
								+ ", " + target + "," + target2 + "," + use + "," + inputState + ");");
						insertedProbe = true;
						inputState = false;
					}
					if (event == Ev_IntUpdate + ArrayMode || event == Ev_DoubleUpdate + ArrayMode
							|| event == Ev_StringUpdate + ArrayMode) {// int�^�z��X�V

						String target = line.getTarget();
						String use = "";

						if (line.getUseNum() > 0) {
							use = line.getUse(0);
						}

						fout.println(indent + "TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
								+ ", \"" + target + "\"," + target + "," + inputState + ");");
						insertedProbe = true;
						inputState = false;
					}
				}

				// �v���[�u[for�J�n
				if (event == Ev_For && line.getLoopInitTargetName().length() > 0) {
					isLoop = true;
					fout.println(indent + "isLoop=true;");
					fout.println(indent + "if(tvisLoopInit" + uniqueID + "==false){tvisLoopInit" + uniqueID
							+ "=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ",\""
							+ line.getLoopInitTargetName() + "\"," + line.getLoopInitTargetName() + ",\"\","
							+ inputState + ");}");
					insertedProbe = true;
					uniqueID++;
				}

				// �v���[�u[While�J�n
				if (event == Ev_While) {
					isLoop = true;
					fout.println(indent + "isLoop=true;");
					insertedProbe = true;
				}

				// �v���[�u[Loop�I��
				if (event == Ev_ForEnd || event == Ev_WhileEnd || event == Ev_DoWhileEnd) {// ���[�v�I��
					isLoop = false;
					fout.println(indent + "TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,"
							+ loopStart.get(loopStart.size() - 1) + "," + loopStart.get(loopStart.size() - 1) + ");");
					CatchLoopEnd = loopStart.get(loopStart.size() - 1);
					loopStart.remove(loopStart.size() - 1);
					fout.println(
							indent + "TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + ");");
					insertedProbe = true;
				}

				// �v���[�u[if��������
				if (event == Ev_Route) {
					fout.println(
							indent + "TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + "  );");
					insertedProbe = true;
				}

				// �v���[�u[Catch�J�n
				if (event == Ev_Catch) {
					fout.println(
							indent + "TProbe.Input_Catch(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + "  );");
					insertedProbe = true;
				}
				if (event == Ev_Case) {
					fout.println(
							indent + "TProbe.Input_Case(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + "  );");
					insertedProbe = true;
				}

				// �C���X�^���X�����v���[�u
				if (event == Ev_UpdateInstance) {
					fout.println(indent + "TProbe.Generate_Instance(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
							+ ",\"" + line.getTarget() + "\"," + line.getTarget() + ");");
				}

				if (event == Ev_Input) {
					inputState = true;
				}
			}

			// �v���[�u[try
			if (line.checkHasEvent(Ev_Try) == true) {
				isTry = true;
			}
			if (line.checkHasEvent(Ev_TryEnd) == true) {
				isTry = false;
			}

			//
			if (beforeLine.checkHasEvent(Ev_Route)) {
				// fout.println(indent+"}");
			}
			if (insertedProbe == false && line.getUseNum() > 0) {
				readProbe(line, lineID, fout);
			}

			lineID++;
			beforeLine = line;

			if (isTry && !(line.checkHasEvent(Ev_IfEnd))) {
				fout.println(indent + "TProbe.Input_Try(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID + "  );");
			}
		}
		fout.println();
	}

	void readProbe(Line line, int lineID, PrintWriter fout) {

		String use = "\"";
		{
			for (int i = 0; i < line.getUseNum(); i++) {
				if (i > 0) {
					use += ",";
				}

				use += Tools.changeProbeVars(line.getUse(i));
			}
			if (line.getUseNum() == 0) {
				use = "\"\"";
			} else {
				use += "\"";
			}
		}
		fout.println(line.getIndentText() + " " + "TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE," + lineID
				+ ", " + use + "  );");

	}

}
