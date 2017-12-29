#include "S_Base.h"
#include "Game.h"




S_Base::S_Base()
{
	m_ArrowsEndOK=m_ArrowsStartOK=false;
}

S_Base::~S_Base()
{

}


char S_Base::_Task()
{
	G()->FrameDealing();

	drive::executionDiagram();
	SelectList();
	Output();


	G()->m_SelectExe=NULL;
	G()->m_NameExe=NULL;
	//データ遷移図
	{
		DTCom* next=&G()->m_DtCom;
		while(next=next->CHECK()){
			ZBufferClear();

			drive::dataTransitionsDiagram(next);
		}	
	}

	//不要なデータ遷移図を削除
	DTCom::doBookingDelete(&G()->m_DtCom);

	//データ遷移線
	{
		ZBufferClear();
		RC_2DPorigon* po=draw::Basic();
		draw::Basic()->SetTexture(NULL);

		DTCom* next=G()->m_DtCom.CHECK();
		DTCom* tmp;
		while(next)
		{
			tmp=next;
			next=next->CHECK();

			DTAItem* dIt=&tmp->m_DTA;
			if(dIt->m_Child)
			{
				dIt->m_Child->drawDTA(po,tmp,dIt);
			}
		}	

		if(G()->m_SelectLine.m_LastUpdate){
			G()->m_SelectLine.arrowDraw();
		}

		po->Draw();
	}

	//ターゲット名称表示
	if(G()->m_NameExe)
	{
		E_Update* update=(E_Update*)G()->m_NameExe->m_Event;
		UpdateVars* vars=(UpdateVars*)update->m_Updates.next();

		DWordDrawStart();
		DWordFormat(DT_NOCLIP);
		DWordColor(D3DXCOLOR(0,0,0,1));	
		DWordArea_W(RL_INPUT()->MOUSEX()+60,RL_INPUT()->MOUSEY()+20,0,0);
		DWordDrawText(G()->m_CommonFont+1  ,vars->m_Target.c_str());	
		DWordDrawEnd();
	
	}
	

	Menu();

	 
	return 0;
}