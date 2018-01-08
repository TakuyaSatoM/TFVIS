#include "DTCom.h"
#include "DataTransitions.h"

//削除予約要素を消去
void DTCom::doBookingDelete(DTCom* com){

	DTCom* next=com->CHECK();
	DTCom* tmp;
	while(next){
		tmp=next;
		next=next->CHECK();
		if(tmp->m_BookDelete){tmp->Delete();}
	}	
}

//データ遷移図を取り換える
DTCom* DTCom::transDTDiagram(DTCom* top,DTCom* oldOne,MethodExe* newOne_MethodExe){

		D3DXVECTOR2 memoryPos;
		memoryPos.x=oldOne->m_DrawArea.x;
		memoryPos.y=oldOne->m_DrawArea.y;
		oldOne->m_BookDelete=true;

		DTCom* newOne=DTCom::Open(top,newOne_MethodExe);
		if(newOne){
			newOne->m_DrawArea.x=memoryPos.x;
			newOne->m_DrawArea.y=memoryPos.y;
		}

		return newOne;
}


//データ遷移図上にマウスカーソルが存在するか
DTCom* DTCom::whatMousePointerCovered(DTCom* comTop,bool mostFront,bool onlyTitle){

		DTCom* out=NULL;
		DTCom* index=comTop;
		
		while(index=index->CHECK()){

			float h=index->m_RealDrawArea.h;
			if(onlyTitle){h=20;}

			if(IsMouseInBox(index->m_RealDrawArea.x,index->m_RealDrawArea.y,index->m_RealDrawArea.w,h)){

				out=index;
				if(mostFront==false){break;}
			}
		}

		return out;
}


//データ遷移図追加
DTCom* DTCom::Open(DTCom* comTop,MethodExe* methodExe){

	//重複展開判定
	if(methodExe->m_NowOpen){return NULL;}
	methodExe->m_NowOpen=true;

	DTCom* add;
	comTop->Add(add=new DTCom);

	add->m_MethodExe=methodExe;
	add->m_TexXY=INT2(0,0);
	int w=comTop->m_DS+DtDiagram::DTCELLW*methodExe->m_XWide/2;
	add->m_DrawArea=C_Box(40,80,w,add->getMethod()->m_UseYArea);
	add->m_Size=INT2(w,add->getMethod()->m_UseYArea);
	add->m_Render=CreateNewRenderingTexture( add->m_Size.x*2, add->m_Size.y*2);
	add->m_Render->SetColor( &D3DXCOLOR(0.9,0.9,0.9,1) );


   return add;
}

//オブジェクト図の追加
DTCom* DTCom::Open(DTCom* comTop, Exe* indexExe){

	DTCom* add;
	comTop->Add(add=new DTCom);

	add->m_MethodExe=NULL;
	add->m_Exe=indexExe;
	add->m_TexXY=INT2(0,0);
	int w=comTop->m_DS;

	E_Update* updateEvent = (E_Update*)indexExe->m_Event;
	UV_Instance* instance = (UV_Instance*)updateEvent->m_Updates.CHECK();
	add->m_DrawArea=C_Box(40,80,w,20*instance->fieldNum);
	add->m_Size=INT2(w,20*2*instance->fieldNum);
	add->m_Render=CreateNewRenderingTexture( add->m_Size.x*2, add->m_Size.y*2);
	add->m_Render->SetColor( &D3DXCOLOR(0.9,0.9,0.9,1) );

   return add;
}