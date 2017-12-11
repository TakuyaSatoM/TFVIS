#include "DataTransitions.h"
#include "Game.h"


//ðŒŽ®¬—§ƒAƒCƒRƒ“
void DtDiagram::condition(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){

	E_Con* ui=(E_Con*)indexExe->m_Event;

	draw_box(po,box.x,box.y,box.w,box.h);

	DWordFormat(DT_CENTER | DT_VCENTER );
	DWordArea_W(box.x,box.y,box.w,box.h);		

	DWordColor(D3DXCOLOR(0,0,1,1));
	DWordDrawText(G()->m_CommonFont  ,"True");
			
}

