#include "DataTransitions.h"
#include "Game.h"

//メソッド開始
void DtDiagram::methodStart(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po,int& changeTargetMethodExe){

		for(int i=0;i<dt->getMethod()->m_CallNum;i++){

			if(dt->m_MethodExe->m_MethodExeNum !=i){//表示していないメソッド実行

				if(IsMouseInBox(dt->m_DrawArea.x+box.x/2,dt->m_DrawArea.y+box.y/2,box.w/2,box.h/2)){//マウスオン
	
					draw_box(po,box.x,box.y,box.w,box.h,D3DXVECTOR4(0.6,0.6,0.6,1));
					DWordColor(D3DXCOLOR(1,1,1.0,1));			
					if(RL_INPUT()->m_MouseL.NowPush()){//マウスクリック
						changeTargetMethodExe=i;

					}
				}
				else{//マウスノットオン
					draw_box(po,box.x,box.y,box.w,box.h,D3DXVECTOR4(0.2,0.2,0.2,1));
					DWordColor(D3DXCOLOR(1,1,1.0,1));						
				}

			}
			else{//表示中のメソッド実行
				draw_box(po,box.x,box.y,box.w,box.h,D3DXVECTOR4(0.5,0.5,1.0,1));
				DWordColor(D3DXCOLOR(1,1,1,1));
			}
			
			DWordFormat(DT_CENTER | DT_VCENTER);
			DWordArea_W(box.x,box.y,box.w,box.h);
			sprintf(DWordBuffer(),"%d",i+1);		
			DWordDrawText(G()->m_CommonFont  ,DWordBuffer());
			
			box.x+=DTCELLW;
		}
		
		po->f_Point=D3DXVECTOR3(dt->m_DS+EVENTW,0,loopTagZ);
		po->f_Size=D3DXVECTOR2(DTCELLW*(dt->getMethod()->m_CallNum),LINEH);
		po->f_Color=methodColor;
		po->Set();
		

}

//メソッド終了
void DtDiagram::methodEnd(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){
			
			
			if(IsMouseInBox(dt->m_DrawArea.x+box.x/2,dt->m_DrawArea.y+box.y/2,box.w/2,box.h/2))
			{
				draw_box(po,box.x,box.y,box.w,box.h,mouseOnColor);


				Exe* tmp=indexExe;
				while(tmp=tmp->CHECK()){if(tmp->m_MethodExeID!=indexExe->m_MethodExeID){break;}}
				G()->m_SelectExe=tmp;
					
				if(tmp&&RL_INPUT()->m_MouseL.NowPush())
				{
					DTCom::Open(&G()->m_DtCom,db::searchMethodExe(tmp->m_MethodExeID));
				}
			}
			else{draw_box(po,box.x,box.y,box.w,box.h);}

			po->Set_Arrow(D3DXVECTOR3(box.x+box.w*0.9,box.y+box.h/2,valueZ),D3DXVECTOR3(box.x+box.w*0.1,box.y+box.h/2,valueZ),4,10,30,0,D3DXVECTOR4(1.0,0,0,1));

			{
					
				po->Set_Arrow(D3DXVECTOR3(dt->m_DS+EVENTW+box.w/2+3*2,box.h+box.h/2,methodZ),D3DXVECTOR3(box.x+box.w/2,box.y+box.h,methodZ),30,40,60,0,methodColor);

			}
			
}

//メソッド呼び出し
void DtDiagram::methodCall(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po){

		
			if(IsMouseInBox(dt->m_DrawArea.x+box.x/2,dt->m_DrawArea.y+box.y/2,box.w/2,box.h/2))
			{
				draw_box(po,box.x,box.y,box.w,box.h,mouseOnColor);
					
				Exe* tmp=indexExe;
				while(tmp=tmp->CHECK()){if(tmp->m_MethodExeID!=indexExe->m_MethodExeID){break;}}
				G()->m_SelectExe=tmp;

				if(RL_INPUT()->m_MouseL.NowPush())
				{
					DTCom::Open(&G()->m_DtCom,db::searchMethodExe(tmp->m_MethodExeID));
				}

			}
			else{draw_box(po,box.x,box.y,box.w,box.h);}
			
			po->Set_Arrow(D3DXVECTOR3(box.x+box.w*0.1,box.y+box.h/2,valueZ),D3DXVECTOR3(box.x+box.w*0.9,box.y+box.h/2,valueZ),4,10,30,0,D3DXVECTOR4(.0,0,1,1));
			
			
}
