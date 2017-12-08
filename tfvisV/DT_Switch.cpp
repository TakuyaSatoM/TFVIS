#include "DataTransitions.h"
#include "Game.h"


//Switch§Œä
void DtDiagram::Switch(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){


	E_Switch* ui=(E_Switch*)indexExe->m_Event;
				
	draw_box(po,box.x,box.y,box.w,box.h);

	DWordFormat(DT_CENTER | DT_VCENTER );
	DWordArea_W(box.x,box.y,box.w,box.h);		

	DWordColor(D3DXCOLOR(0,0.7,0,1));
	DWordDrawText(G()->m_CommonFont  ,"Switch");
			
}

void DtDiagram::Case(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){


	E_Case* ui=(E_Case*)indexExe->m_Event;
				
	draw_box(po,box.x,box.y,box.w,box.h);

	DWordFormat(DT_CENTER | DT_VCENTER );
	DWordArea_W(box.x,box.y,box.w,box.h);		

	DWordColor(D3DXCOLOR(0,0.7,0,1));
	DWordDrawText(G()->m_CommonFont  ,"Case");
			
}
