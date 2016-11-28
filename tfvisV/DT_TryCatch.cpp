#include "DataTransitions.h"
#include "Game.h"

//try-catch•¶
void DtDiagram::tryCatch(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){


	E_Catch* ui=(E_Catch*)indexExe->m_Event;
	
	int sx,sy,sh,sw; 
	
	sx=dt->m_DS+EVENTW+ui->m_Try->m_DTXY.x*DTCELLW+3*2;
	sy=ui->m_Try->m_DTXY.y*LINEH+2*2;
	sw=DTCELLW-6*2;
	sh=LINEH-4*2;

	if(sx==box.x){
		po->Set_Arrow(D3DXVECTOR3(sx+sw/2,sy+sh,loopZ),D3DXVECTOR3(box.x+box.w/2,box.y+box.h/2,loopZ),50,40,30,0,catchColor);
	}else{
		po->Set_Arrow(D3DXVECTOR3(sx+sw/2,sy+sh,loopZ),D3DXVECTOR3(sx+box.w/2,box.y+box.h/2,loopZ),50,40,30,0,catchColor);
		po->Set_Arrow(D3DXVECTOR3(sx+box.w/2,box.y+box.h/2,loopZ),D3DXVECTOR3(box.x+box.w/2,box.y+box.h/2,loopZ),20,20,20,0,catchColor);
	}

	draw_box(po,sx,sy,sw,sh);

	DWordFormat(DT_CENTER | DT_VCENTER );
	DWordArea_W(sx,sy,sw,sh);

	DWordColor(D3DXCOLOR(1,0,0,1));
	DWordDrawText(G()->m_CommonFont  ,"Catch");
			
}

