#include "DataTransitions.h"
#include "Game.h"

//ループ周回変更
void DtDiagram::loopNext(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){


			E_LoopNext* ui=(E_LoopNext*)indexExe->m_Event;
			if(ui->m_Start){
				int sx,sy,sw,sh;
				sx=dt->m_DS+LINEH+ui->m_Start->m_DTXY.x*DTCELLW+3*2;
				sy=ui->m_Start->m_DTXY.y*LINEH+2*2;
				sw=DTCELLW-6*2;
				sh=LINEH-4*2;
				po->Set_Arrow(D3DXVECTOR3(box.x+box.w/2,sy+0.1*sh,loopZ),D3DXVECTOR3(box.x+box.w/2,box.y+box.h,loopZ),50,40,30,0,loopsColor);

				DWordFormat(DT_CENTER | DT_VCENTER );
				DWordColor(D3DXCOLOR(1,1,1,1));
				DWordArea_W(box.x,sy+2*2,sw,sh);	
				sprintf(DWordBuffer(),"%d", ui->m_Turn);	
				DWordDrawText(G()->m_CommonFont  ,DWordBuffer());
			}
			
}

//ループ終了
void DtDiagram::loopEnd(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){

			
			E_LoopEnd* ui=(E_LoopEnd*)indexExe->m_Event;
			if(ui->m_Start)
			{
				int sx,sy,sw,sh;
				sx=dt->m_DS+EVENTW+(ui->m_Start->m_DTXY.x)*DTCELLW+3*2;
				sy=ui->m_Start->m_DTXY.y*LINEH+2*2;
				sw=DTCELLW-6*2;
				sh=LINEH-4*2;
				po->f_Point=D3DXVECTOR3(sx+sw*0.5,sy+sh*0.1,loopTagZ);
				po->f_Size=D3DXVECTOR2((box.x+box.w*0.5-box.w)-(sx+sw*0.5),sh*0.8);
				po->f_Color=loopsTagColor;
				po->Set();
			}
			
}
