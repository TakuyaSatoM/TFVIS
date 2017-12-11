#include "ExecutionDiagram.h"


//実行フロー図上でのメソッドアイコン描画
void ExecutionDiagram::drawMethodFigure(RC_2DPorigon* po){

		int xOffset=0*2;

		po->f_Point=D3DXVECTOR3(xStart,classTopY,cellZ);
		po->f_Color=D3DXVECTOR4(0.3,0.3,0.3,1);
		po->f_Size=D3DXVECTOR2(WINDOWSIZEW*2,cellH);
		po->Set();




		{
			C_Class* indexClass=db::getClass();
			int classID=0;
			int methodCount=0;
			while(indexClass=indexClass->next()){

				int methodNum=indexClass->m_Method.NUM();
				if(methodNum==0){xOffset+=cellW/2;classID++;continue;}


				int classW=1.0*(methodCount+methodNum+0.5)*cellW/2.0-xOffset;
		   
				if(indexClass->next()==NULL){classW+=cellW/4.0;}


				po->f_Point=D3DXVECTOR3(xStart+xOffset,classTopY+space,cellZ);
				po->f_Color=ot::colorPattern(classID);
				po->f_Size=D3DXVECTOR2(classW,cellH-space*2);
				po->Set();

				m_ClassNamesRightX=xStart+xOffset+classW;

				DWordArea_W(po->f_Point.x,po->f_Point.y,po->f_Size.x,po->f_Size.y);
				sprintf(DWordBuffer(),indexClass->m_Name.c_str());
				DWordDrawText(G()->m_CommonFont  ,DWordBuffer());


				//メソッド
				Method* indexMethod=&indexClass->m_Method;
				while(indexMethod=indexMethod->next()){
					drawMethodNameTab(indexMethod,methodCount,po,ot::colorPattern(classID));
					methodCount++;
				}			

				xOffset+=classW;
				classID++;
			}
		}

}


//メソッドネームタグ描画
void ExecutionDiagram::drawMethodNameTab(Method* method,int number,RC_2DPorigon* po,D3DXVECTOR4 mainColor){

				int mx,my;
				mx=xStart+number*cellW/2+methodSpace;
				my=methodTopY+(number%2)*(cellH+10*2);

				po->Set_Tri(D3DXVECTOR3(mx,my+cellH,cellZ),D3DXVECTOR2(cellW-methodSpace*2,8*2),false,D3DXVECTOR4(0.3,0.3,0.3,1));
				po->Set();
				//中心線
				method->m_EDsXPos=mx+(cellW-methodSpace*2)/2;
				draw::Basic()->f_Point=D3DXVECTOR3(mx+(cellW-methodSpace*2)/2,my+cellH+8*2,1);
				draw::Basic()->f_Color=D3DXVECTOR4(0.4,0.4,0.4,1);
				draw::Basic()->f_Size=D3DXVECTOR2(2*2,720*2);
				draw::Basic()->Set();

				po->f_Point=D3DXVECTOR3(mx,my,cellZ);
				po->f_Color=D3DXVECTOR4(0.25,0.25,0.25,1);
				po->f_Size=D3DXVECTOR2(cellW-methodSpace*2,cellH);
				po->Set();

				po->f_Point=D3DXVECTOR3(po->f_Point.x+space,po->f_Point.y+space,cellZ);
				po->f_Color=mainColor;
				po->f_Size=D3DXVECTOR2(po->f_Size.x-space*2,po->f_Size.y-space*2);
				if(IsMouseInBox(G()->mDrawArea_ExeFlow.x+(po->f_Point.x)/2,G()->mDrawArea_ExeFlow.y+po->f_Point.y/2,po->f_Size.x/2,po->f_Size.y/2) &&  DTCom::whatMousePointerCovered(&G()->m_DtCom,false,false)==NULL)
				{
					po->f_Color+=D3DXVECTOR4(0.25,0.25,0.25,0);

					if(RL_INPUT()->m_MouseL.NowPush()){DTCom::Open(&G()->m_DtCom,db::searchMethodExe(method,0));}
				}
				po->Set();

				DWordArea_W(po->f_Point.x,po->f_Point.y,po->f_Size.x,po->f_Size.y);
				sprintf(DWordBuffer(),method->m_Name.c_str());
				DWordDrawText(G()->m_CommonFont  ,DWordBuffer());
}

