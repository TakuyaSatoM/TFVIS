#include "DataTransitions.h"
#include "Game.h"
#include <iostream>

bool DtDiagram::draw(DTCom* dt)
{
   HideBackBuffer();
   dt->m_Render->Set(1);

   drawCode(dt);
   drawTable(dt);
	
   RevaivalBackBuffer();


   //ウィンドウ部
   {
	   
		RC_2DPorigon* po=draw::Basic();
   
		int scrollSpace=10;
		int titleSpace=20;
		int edgeSpace=4;

		int dx,dy,dw,dh;


		dt->m_RealDrawArea.x=dx=dt->m_DrawArea.x-edgeSpace;
		dt->m_RealDrawArea.y=dy=dt->m_DrawArea.y-titleSpace;
		dt->m_RealDrawArea.w=dw=dt->m_DrawArea.w+edgeSpace*2+scrollSpace;
		dt->m_RealDrawArea.h=dh=dt->m_DrawArea.h+titleSpace+edgeSpace+scrollSpace;

		po->f_Point=D3DXVECTOR3(dx,dy,1);
		po->f_Size=D3DXVECTOR2(dw,dh);
		po->f_Color=D3DXVECTOR4(0.9,0.9,0.9,1);
		po->Set();

		po->f_Color=D3DXVECTOR4(0.7,0.7,0.7,1);
		po->Set_Box(dx,dy,dw,dh,1);

		po->Draw();

		//転写
		ot::printTexture(dt->m_Render->GetTexture(),dt->m_DrawArea,dt->m_TexXY,0.9);



		po->f_Point.z=0.8;
		po->f_Color=D3DXVECTOR4(0.5,0.5,0.5,0.3);
		po->Set_Box(dt->m_DrawArea.x,dt->m_DrawArea.y,dt->m_DrawArea.w,dt->m_DrawArea.h,1);

		po->f_Point=D3DXVECTOR3(dx+dw-scrollSpace-edgeSpace,dy+dh-scrollSpace-edgeSpace,0.8);
		po->f_Size=D3DXVECTOR2(scrollSpace+edgeSpace,scrollSpace+edgeSpace);
		po->Set();
		po->Draw();

		DWordDrawStart();
		DWordFormat(DT_VCENTER);
		DWordColor(D3DXCOLOR(0,0,0,1));

		DWordArea_W(dx+10,dy,9999,titleSpace);
		sprintf(DWordBuffer(),"class %s/%s",dt->getMethod()->m_MyClass->m_Name.c_str(),dt->getMethod()->m_FullName.c_str());
		DWordDrawText(G()->m_CommonFont_S  ,DWordBuffer());	
		DWordDrawEnd();

		if(!RL_INPUT()->m_MouseL.Push()){dt->m_DragStart=false;}
		if(dt->m_DragStart){

			dt->m_DrawArea.x+=RL_INPUT()->MOUSEX()-dt->m_BeforeXY.x;
			dt->m_DrawArea.y+=RL_INPUT()->MOUSEY()-dt->m_BeforeXY.y;

			dt->m_BeforeXY.x=RL_INPUT()->MOUSEX();
			dt->m_BeforeXY.y=RL_INPUT()->MOUSEY();
		}

		if(IsMouseInBox(dx,dy,dw,titleSpace) && DTCom::whatMousePointerCovered(&G()->m_DtCom,true,true)==dt){

			if(dt->m_DragStart==false && RL_INPUT()->m_MouseL.NowPush() ){
				dt->m_BeforeXY.x=RL_INPUT()->MOUSEX();
				dt->m_BeforeXY.y=RL_INPUT()->MOUSEY();
				dt->m_DragStart=true;
			}

			if( RL_INPUT()->m_MouseR.NowPush()){
				dt->m_BookDelete=true;
				return true;
			}
		}
   }

   return false;
}