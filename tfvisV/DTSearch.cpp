#include "DataTransitions.h"
#include "Game.h"


void SelectLine::arrowDraw(){

//void  DTAItem::drawDTA(RC_2DPorigon* po,DTCom* dt,DTAItem* parent)


	D3DXVECTOR2 st=D3DXVECTOR2(RL_INPUT()->MOUSEX(),RL_INPUT()->MOUSEY());
	D3DXVECTOR2 be=m_LastUpdate->m_LastPos;

	D3DXVECTOR4 tailColor=DtDiagram::dtaColor;
	{
		MethodExe* mExe=db::searchMethodExe(m_LastUpdate->m_MethodExeID);
		if(mExe->m_NowOpen==false)
		{
			be.x=mExe->m_EDsRect.x+mExe->m_EDsRect.z/2;
			be.y=mExe->m_EDsRect.y+mExe->m_EDsRect.w/2;

			tailColor=D3DXVECTOR4(0.5,0.5,0.5,0.5);
		}
		else
		{
			if(m_LastUpdate->m_EventType==ev::UPDATE_INTARRAY)
			{
				be.x+=-DtDiagram::DTCELLW/2;
			}		
		}
	}

	D3DXVECTOR2 nor=st-be;
	float len=D3DXVec2Length(&nor);
	D3DXVec2Normalize(&nor,&nor);
	len=max(0,len-10);
	st=be+nor*len;


	
	draw::Basic()->Set_Arrow(D3DXVECTOR3(st.x,st.y,DtDiagram::dtaZ),D3DXVECTOR3(be.x,be.y,DtDiagram::dtaZ),4,6,16,0,DtDiagram::dtsColor,DtDiagram::dtsColor);


}

void SelectLine::refresh(){

	SelectLine* sl=&G()->m_SelectLine;
	int num=sl->m_Exe.NUM();

	DTCom* next=&G()->m_DtCom;
	while(next=next->CHECK())
	{next->m_DTA.release();}
	
	//ŠY“–Exe‚ÌŒŸõ
	Exe* index=NULL;
	{
		C_SelectLineExe* inLE=sl->m_Exe.CHECK();
		for(int i=0;i<sl->m_SelectTurn;i++){
			inLE=inLE->CHECK();
		}
		if(inLE==NULL){return;}
		index=inLE->m_Exe;
	}
	if(index==NULL){return;}

	//ŠY“–•Ï”‚ÌŒŸõ
	string varName = "";
	{
		if(index->m_Ref.NUM()==0){
			return;
		}

		C_String* inSt=index->m_Ref.CHECK();

		for(int i=0;i<sl->m_SelectVar;i++){
			inSt=inSt->CHECK();
		}

		varName=inSt->m_Text;
	}

	Exe* TargetIndex = index;
	while(index){

		if(ev::isUpdate(index->m_EventType) ){

			bool existRelation=false;
			int count=0;
			UpdateVars* uin=&((E_Update*)index->m_Event)->m_Updates;
			while(uin=uin->next()){
				if(uin->m_Target == varName && uin->instanceID == TargetIndex->m_InstanceID){
					existRelation=true;
					break;
				}
				count++;
			}

			if(existRelation)
			{break;}
		}
		index=index->back();
	}

	m_LastUpdate=index;


}