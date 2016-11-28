
#include "Game.h"

namespace db{


	int load(){

		//��{�\���f�[�^�Ǎ�
		  {
			StrReader read("Data\\SystemStr.txt");
			read.Read(getClass());
		  }

		  //���s���ʓǍ�
		  {
			  ExeReader read("Data\\Method.txt");
			  read.Read(getExe());
		  }

		  //�e���\�b�h�̌Ăяo���񐔂��v�Z�E���\�b�h���s��o�^
		  {
				Exe* index=getExe();
				int count=0;
				while(index=index->CHECK()){

					if(index->m_EventType == ev::METHOD_START){

						MethodExe* me;
						getMethodExe()->Add(me=new MethodExe);
						me->m_ID=count++;
						me->m_Method=db::searchMethod(index->m_MethodID);
						me->m_MethodExeNum=me->m_Method->m_CallNum;
						me->m_Method->m_CallNum++;
					}
				}		  

				//�e���\�b�h���s�̊J�n�ƏI����c��
				db::setRelation_MethodExe_andExe();
		  }
		  
		  //�f�[�^�J�ڐ}�쐬
		  if(createDTTable()){
			//�G���[����
			ERROR_EXIT();
		  }



		  return 0;
	}



}