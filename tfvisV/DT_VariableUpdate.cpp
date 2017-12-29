#include "DataTransitions.h"
#include "Game.h"

void DtDiagram::variableUpdate(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){

	E_Update* ui=(E_Update*)indexExe->m_Event;

	if(dt->m_DTA.m_Ad!=indexExe){

		if(IsMouseInBox(dt->m_DrawArea.x+box.x/2,dt->m_DrawArea.y+box.y/2,box.w/2,box.h/2)){

			draw_box(po,box.x,box.y,box.w,box.h,mouseOnColor);
			G()->m_NameExe=indexExe;
			if(RL_INPUT()->m_MouseL.NowPush()){

				DTCom* next=&G()->m_DtCom;
				while(next=next->CHECK())
				{next->m_DTA.release();}
				createDTA(dt,indexExe);
				G()->m_SelectLine.init();
			}
		}
		else if(ui->getInputState()){
			//•Ï”XV‚ªStandard Input‚ÌŽž
			draw_box(po,box.x,box.y,box.w,box.h,InputColor);
		}
		else{draw_box(po,box.x,box.y,box.w,box.h);}

	}
	else{
					
		if(IsMouseInBox(dt->m_DrawArea.x+box.x/2,dt->m_DrawArea.y+box.y/2,box.w/2,box.h/2))
		{G()->m_NameExe=indexExe;}

		draw_box(po,box.x,box.y,box.w,box.h,D3DXVECTOR4(1,1,1,1),D3DXVECTOR4(1,0,0,1));
	}

	DWordFormat(DT_CENTER | DT_VCENTER);
	DWordColor(D3DXCOLOR(0,0,0,1));
	DWordColor(D3DXCOLOR(0,0,0,1));
	DWordArea_W(box.x,box.y,box.w,box.h);	
	DWordDrawText(G()->m_CommonFont  ,ui->m_Updates.next()->getNewDataText().c_str());	

}

void DtDiagram::variableArrayUpdate(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){
				
		E_Update* ui=(E_Update*)indexExe->m_Event;

		string out="";
		UpdateVars* index=&ui->m_Updates;
		while(index=index->next()){
			out+=index->getNewDataText();
			out+=",";
		}
		sprintf(DWordBuffer(),"%s", out.c_str());	

		DWordFormat(DT_LEFT | DT_VCENTER | DT_CALCRECT);
		DWordColor(D3DXCOLOR(0,0,0,1));
		DWordArea_W(box.x,box.y,box.w,box.h);		
		DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	

		draw_box(po,box.x,box.y,DWordNowSize().right-DWordNowSize().left,box.h);

		DWordFormat(DT_LEFT | DT_VCENTER );
		DWordDrawText(G()->m_CommonFont  ,DWordBuffer());
				
}

void DtDiagram::InstanceUpdate(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){
				
		E_Update* ui=(E_Update*)indexExe->m_Event;
		UpdateVars* index=&ui->m_Updates;

		sprintf(DWordBuffer(),"%s", index->getNewDataText());	

		DWordFormat(DT_LEFT | DT_VCENTER | DT_CALCRECT);
		DWordColor(D3DXCOLOR(0,0,0,1));
		DWordArea_W(box.x,box.y,box.w,box.h);		
		DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	

		draw_box(po,box.x,box.y,DWordNowSize().right-DWordNowSize().left,box.h);

		DWordFormat(DT_LEFT | DT_VCENTER );
		DWordDrawText(G()->m_CommonFont  ,DWordBuffer());
				
}


