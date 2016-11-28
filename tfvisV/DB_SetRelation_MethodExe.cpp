
#include "Game.h"

namespace{

	class ReData{
		public:
	    bool m_Ended;
		Exe* m_Start;
		Exe* m_End;

		ReData(){m_Ended=false;m_Start=m_End=NULL;}
	};
}

namespace db{

	void setRelation_MethodExe_andExe(){
			ReData* datas=new ReData[MethodExe::ms_ExistNum];

			{
				Exe* index=getExe();
				while(index=index->CHECK()){
					if(index->m_EventType == ev::METHOD_START){
						datas[index->m_MethodExeID].m_Start=index;
					}
					if(index->m_EventType == ev::METHOD_END){
						datas[index->m_MethodExeID].m_Ended=true;
						datas[index->m_MethodExeID].m_End=index;
					}
					for(int i=0;i<MethodExe::ms_ExistNum;i++){
						if(datas[i].m_Ended==false){datas[i].m_End=index;}
					}
				}	
			}
			{
				MethodExe* index=getMethodExe();
				while(index=index->next()){
					index->m_StartExe=datas[index->m_ID].m_Start;
					index->m_EndExe=datas[index->m_ID].m_End;
				}	
			}

			delete[] datas;
	}

}