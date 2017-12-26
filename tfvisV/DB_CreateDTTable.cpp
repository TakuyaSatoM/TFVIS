
#include "Game.h"


namespace db{


	Exe* setExe(Exe* exe){
		
		Method* method=db::searchMethod(exe->m_MethodID);
		MethodExe* meExe=db::searchMethodExe(exe->m_MethodExeID);

		// ���\�b�h���ł̍s�ԍ��̎擾
		int lineNum=method->m_Line.back()->m_BaseID+1;

		// �e�s�̃��[�v���������ɕ��ׂ鎞�ɗp����l��ێ�����z��
		int* lvL=new int[lineNum];
		for(int i=0;i<lineNum;i++){lvL[i]=0;}

		Exe* indexExe=exe;
		Exe* beforeExe=exe;
		C_Line* iline=NULL;

		// �`�掞�ɕK�v�ȏ����C�x���g�N���X�Ƃ��ĕێ����A�C�x���g�O���t�ɕR�t��
		while( indexExe != NULL){
		
			method=db::searchMethod(indexExe->m_MethodID);
			meExe=db::searchMethodExe(indexExe->m_MethodExeID);

			{
				// �Y�����\�b�h��m_LineID�s�ڂ��擾
				C_Line* tmpline=iline;
				iline=db::searchLine(method,indexExe->m_LineID);

				
				if(tmpline != NULL){
					if(tmpline->CHECK()){
						if(((C_Line*)tmpline->CHECK())->m_BaseID == tmpline->m_BaseID){
							iline=tmpline->CHECK();
						}
					}

				}
				

				if(iline->m_EventNum>1){
					int num=iline->m_EventNum;
					while(iline && num > 0){
						if( ev::checkEventEqual(iline->m_EventType,indexExe->m_EventType) ){break;}

						iline=iline->CHECK();
						num--;
					}
					if(num==0){
						iline=db::searchLine(method,indexExe->m_LineID);
					}
				}
			}

			

			//���[�v�J�n
			if(indexExe->m_EventType == ev::LOOP_START){
					LooptMemo* newItem;
					meExe->m_LtMemory.Add(newItem= new LooptMemo(indexExe,lvL[indexExe->m_LineID]));
			}

			// ���s�C�x���g�̕`��ӏ��̎Z�o(���[�v�����ȊO)
			indexExe->m_DTXY=INT2(lvL[indexExe->m_LineID],iline->m_ID);
			meExe->m_XWide=max(meExe->m_XWide,indexExe->m_DTXY.x);

			// ���[�v�������̕`��ӏ��̎Z�o
			if(indexExe->m_EventType == ev::LOOP_NEXT){

				E_LoopNext* ev=(E_LoopNext*)indexExe->m_Event;
				LooptMemo* lt=meExe->m_LtMemory.back();
				ev->m_Start=lt->m_Start;
				indexExe->m_DTXY.x=lt->m_StartX;

				
				E_LoopStart* st=(E_LoopStart*)ev->m_Start->m_Event;
				st->m_Count++;
				ev->m_Turn=st->m_Count;
				
				int lmax=-1;
				for(int i=lt->m_Start->m_LineID;i<=indexExe->m_LineID;i++){
					lmax=max(lvL[i],lmax);
				}
				for(int i=lt->m_Start->m_LineID;i<=indexExe->m_LineID;i++){
					lvL[i]=lmax+1;
				}
				meExe->m_LtMemory.Add(new LooptMemo(lt->m_Start,lvL[lt->m_Start->m_LineID]));
				lt->Delete();
			}
			
			if(indexExe->m_EventType == ev::LOOP_END)
			{
				E_LoopEnd* ev=(E_LoopEnd*)indexExe->m_Event;
				LooptMemo* lt=meExe->m_LtMemory.back();
				ev->m_Start=lt->m_Start;
				for(int i=lt->m_Start->m_LineID;i<=indexExe->m_LineID;i++){
					lvL[i]--;
				}
				indexExe->m_DTXY.x++;
				lt->Delete();
			}

			//try-catch���̃C�x���g�N���X�̕R�Â�
			if(indexExe->m_EventType == ev::CATCH){
				E_Catch* ev=(E_Catch*)indexExe->m_Event;
				ev->m_Try = beforeExe;
			}

			beforeExe=indexExe;
			indexExe=indexExe->CHECK();

			if(indexExe->m_EventType == ev::METHOD_END){break;}

			if(indexExe->m_EventType == ev::METHOD_START && indexExe != exe){indexExe=setExe(indexExe);}
			
		}

		delete[] lvL;


		meExe->m_XWide=min(meExe->m_XWide,DT_XWIDEMAX);
		meExe->m_XWide=max(meExe->m_XWide,method->m_CallNum);

		return indexExe;
	}

	//�f�[�^�J�ڐ}���쐬(������0��Ԃ�)
	int  createDTTable(){


		Exe* exe = setExe(getExe()->CHECK());

		// ���C�����\�b�h�I���ʒu�̎w��
		Method* method=db::searchMethod(exe->m_MethodID);
		MethodExe* meExe=db::searchMethodExe(exe->m_MethodExeID);

		C_Line* iline=db::searchLine(method,exe->m_LineID);

		exe->m_DTXY = INT2(0,iline->m_ID);
		meExe->m_XWide = max(meExe->m_XWide,exe->m_DTXY.x);

		return 0;
	}

}