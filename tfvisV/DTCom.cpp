#include "DTCom.h"
#include "DataTransitions.h"

//íœ—\–ñ—v‘f‚ğÁ‹
void DTCom::doBookingDelete(DTCom* com){

	DTCom* next=com->CHECK();
	DTCom* tmp;
	while(next){
		tmp=next;
		next=next->CHECK();
		if(tmp->m_BookDelete){tmp->Delete();}
	}	
}

//ƒf[ƒ^‘JˆÚ}‚ğæ‚èŠ·‚¦‚é
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


//ƒf[ƒ^‘JˆÚ}ã‚Éƒ}ƒEƒXƒJ[ƒ\ƒ‹‚ª‘¶İ‚·‚é‚©
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


//ƒf[ƒ^‘JˆÚ}’Ç‰Á
DTCom* DTCom::Open(DTCom* comTop,MethodExe* methodExe){

	//d•¡“WŠJ”»’è
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

//ƒtƒB[ƒ‹ƒh}‚Ì’Ç‰Á
DTCom* DTCom::Open(DTCom* comTop, E_Update* instanceUpdate){

	DTCom* add;
	comTop->Add(add=new );


   return add;
}