#include "ExecutionDiagram.h"
#include "Draw.h"


//ƒƒ\ƒbƒhŽÀs‘JˆÚ–îˆó•`‰æ
void ExecutionDiagram::drawCallArrow(Exe* beforeExe,Exe* nowExe){
	float multiA=1;
	float multiB=1;

	MethodExe* mA=db::searchMethodExe(beforeExe->m_MethodExeID);
	MethodExe* mB=db::searchMethodExe(nowExe->m_MethodExeID);

	D3DXVECTOR4 acolor=D3DXVECTOR4(0,0,0,0);	

	if(G()->m_SelectExe){
		if(G()->m_SelectExe == nowExe && G()->m_SelectExe->BACK() == beforeExe){
			acolor+=D3DXVECTOR4(0.4,0.4,0.4,0)*G()->FlashDecimal(); multiA=6;multiB=2;
		}
	}

	
	if(beforeExe->m_MethodID != nowExe->m_MethodID){

		if(nowExe->m_EventType==ev::METHOD_START ){
			acolor+=D3DXVECTOR4(0,0,1,1);
			int ay=nowExe->m_DrawY+startY+Aheight/2;
			draw::Basic()->Set_Arrow(D3DXVECTOR3(mA->m_CenterXPos,ay,0),D3DXVECTOR3(mB->m_CenterXPos,ay,0),2*multiA,8*multiB,30,0,acolor);

		}
		else{
			acolor+=D3DXVECTOR4(1,0,0,1);
			int ay=beforeExe->m_DrawY+startY+Aheight/2;
			draw::Basic()->Set_Arrow(D3DXVECTOR3(mA->m_CenterXPos,ay,0),D3DXVECTOR3(mB->m_CenterXPos,ay,0),2*multiA,8*multiB,30,0,acolor);
		}
	}
}


//Šˆ«‹æŠÔ•`‰æ
void ExecutionDiagram::drawExecutionSpecification(Exe* indexExe,float zlv){

	Method* method=db::searchMethod(indexExe->m_MethodID);


	int recLv=db::searchMethod(indexExe->m_MethodID)->m_Recursion;
	float recX=wide/2*(recLv-1);
	D3DXVECTOR4 mcolor=getNestLvColor(recLv);

	int dx,dy;
	dx=method->m_EDsXPos;
	dy=startY+indexExe->m_DrawY;




	MethodExe* mExe=db::searchMethodExe(indexExe->m_MethodExeID);
	Exe* endExe=mExe->m_EndExe;

	D3DXVECTOR4 gcolor=D3DXVECTOR4(mcolor.x,mcolor.y,mcolor.z,1);
	
	if(mExe->m_NowOpen){gcolor+=D3DXVECTOR4(0.1,0.1,0.3,0);}

	if(IsMouseInBox(G()->mDrawArea_ExeFlow.x+(dx-wide/2+recX)/2,G()->mDrawArea_ExeFlow.y+dy/2,wide/2,(startY+endExe->m_DrawY+Aheight-dy)/2)){
		gcolor+=D3DXVECTOR4(0.1,0.1,0.3,0);
		if(RL_INPUT()->m_MouseL.NowPush()){DTCom::Open(&G()->m_DtCom,mExe);}
	}

	D3DXVECTOR3 top,bottom;
	draw::Basic()->Set_Tri(top=D3DXVECTOR3(dx-wide/2+recX,dy,zlv),D3DXVECTOR2(wide,Aheight),true,gcolor);

	if(endExe->m_EventType==ev::METHOD_END){
		draw::Basic()->Set_Tri(bottom=D3DXVECTOR3(dx-wide/2+recX,endExe->m_DrawY+startY,zlv),D3DXVECTOR2(wide,Aheight),false,gcolor);
	}
	draw::Basic()->f_Color=gcolor;
	draw::Basic()->f_Point=D3DXVECTOR3(dx-wide/2+recX,dy+Aheight,zlv);		  
	draw::Basic()->f_Size=D3DXVECTOR2(wide,((endExe->m_DrawY+startY)-(dy+Aheight)));
	draw::Basic()->Set();
			

	mExe->m_CenterXPos=draw::Basic()->f_Point.x+draw::Basic()->f_Size.x/2;

	//Šˆ«‹æŠÔ—Ìˆæ‚Ì‹L‰¯
	mExe->m_EDsRect.x=top.x/2+G()->mDrawArea_ExeFlow.x;
	mExe->m_EDsRect.y=top.y/2+G()->mDrawArea_ExeFlow.y;
	mExe->m_EDsRect.z=(bottom.x-top.x)/2;
	mExe->m_EDsRect.w=(bottom.y-top.y)/2;
}