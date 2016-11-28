#include "S_Base.h"
#include "Game.h"
#include <iostream>

typedef struct _Menubar{
	D3DXVECTOR2 Set;
	D3DXVECTOR2 Refresh;
	D3DXVECTOR2 Search;
	D3DXVECTOR2 Result;
	D3DXVECTOR2 Option;

}Menubar;

bool MenuBottons(D3DXVECTOR2 start,D3DXVECTOR2 size,string text)
{
   draw::Basic()->f_Color=D3DXVECTOR4(1,1,1,1);
   draw::Basic()->f_Size=D3DXVECTOR2(size.x-4,size.y-4);
   draw::Basic()->f_Point=D3DXVECTOR3(start.x+2,start.y+2,G()->GetInsZ());
   draw::Basic()->Set();

   draw::Basic()->f_Color=D3DXVECTOR4(0.6,0.6,0.6,1);

   if(IsMouseInBox(draw::Basic()->f_Point.x,draw::Basic()->f_Point.y,draw::Basic()->f_Size.x,draw::Basic()->f_Size.y))
   {
		draw::Basic()->f_Color=D3DXVECTOR4(0.9,0.8,0.7,1);
   }
   

   draw::Basic()->f_Size=D3DXVECTOR2(size.x-4-1,size.y-4-1);
   draw::Basic()->f_Point=D3DXVECTOR3(start.x+2,start.y+2,G()->GetInsZ());
   draw::Basic()->Set();

   DWordColor(D3DXCOLOR(1,1,1,1));
	DWordArea_W(start.x,start.y,size.x,size.y);
	sprintf(DWordBuffer(),text.c_str());
	DWordDrawText(G()->m_CommonFont_S  ,DWordBuffer());


	return false;
}


void MenuSwitch(D3DXVECTOR2 start,D3DXVECTOR2 size,string text)
{

	static bool state=false;
   //
   	DWordColor(D3DXCOLOR(1,1,1,1)); 
   if(state==false)
   {
		draw::Basic()->f_Color=D3DXVECTOR4(0.6,0.6,0.6,1);
   }
   else
   {
 		draw::Basic()->f_Color=D3DXVECTOR4(0.3,0.3,0.3,1); 
		DWordColor(D3DXCOLOR(0.6,0.6,1,1)); 
			
   }
   

   draw::Basic()->f_Size=D3DXVECTOR2(size.x-4,size.y-4);
   draw::Basic()->f_Point=D3DXVECTOR3(start.x+2,start.y+2,G()->GetInsZ());
   if(IsMouseInBox(draw::Basic()->f_Point.x,draw::Basic()->f_Point.y,draw::Basic()->f_Size.x,draw::Basic()->f_Size.y) || state == true)
   {
		draw::Basic()->f_Color+=D3DXVECTOR4(0.3,0.3,0.3,0);
		if(RL_INPUT()->m_MouseL.NowPush() || state == true)
			{
				state=true;
				//ウィンドウ生成

				HideBackBuffer();
				RevaivalBackBuffer();

				RC_2DPorigon* po=draw::Basic();

				static int dx=100,dy=100,dw=100,dh=100;
				int scrollSpace=10;
				int titleSpace=20;
				int edgeSpace=4;
				bool m_DragStart=true;
				static int BeforeX=100,BeforeY=100;


		
				po->f_Point=D3DXVECTOR3(dx,dy,1);
				po->f_Size=D3DXVECTOR2(dw,dh);
				po->f_Color=D3DXVECTOR4(0.9,0.9,0.9,1);
				po->Set();
		
				po->f_Color=D3DXVECTOR4(0.7,0.7,0.7,1);
				po->Set_Box(dx,dy,dw,dh,1);

				po->Draw();
		
				DWordDrawStart();
				DWordFormat(DT_VCENTER);
				DWordColor(D3DXCOLOR(0,0,0,1));

				DWordArea_W(dx+10,dy,9999,titleSpace);
				sprintf(DWordBuffer(),"class");
				DWordDrawText(G()->m_CommonFont_S  ,DWordBuffer());	
				DWordDrawEnd();
		
				if(!RL_INPUT()->m_MouseL.Push()){m_DragStart=false;}
				if(m_DragStart){

					dx+=RL_INPUT()->MOUSEX()-BeforeX;
					dy+=RL_INPUT()->MOUSEY()-BeforeY;

					BeforeX=RL_INPUT()->MOUSEX();
					BeforeY=RL_INPUT()->MOUSEY();
					std::cout<<BeforeX<<endl;
				}
				
		}
   }
	draw::Basic()->Set();

	DWordArea_W(start.x,start.y,size.x,size.y);
	sprintf(DWordBuffer(),text.c_str());
	DWordDrawText(G()->m_CommonFont_S  ,DWordBuffer());

}

void S_Base::Menu()
{
	DWordDrawStart();

	DWordFormat(DT_CENTER | DT_VCENTER);
	DWordColor(D3DXCOLOR(1,1,1,1));
	

   draw::Basic()->f_Color=D3DXVECTOR4(0.7,0.7,0.7,1);
   draw::Basic()->f_Size=D3DXVECTOR2(WINDOWSIZEW,20);
   draw::Basic()->f_Point=D3DXVECTOR3(0,0,G()->GetInsZ());
   draw::Basic()->Set();

   draw::Basic()->f_Color=D3DXVECTOR4(0,0,0,0.2);
   draw::Basic()->f_Size=D3DXVECTOR2(WINDOWSIZEW,2);
   draw::Basic()->f_Point=D3DXVECTOR3(0,18,G()->GetInsZ());
   draw::Basic()->Set();

   draw::Basic()->f_Color=D3DXVECTOR4(0.9,0.9,0.9,1);
   draw::Basic()->f_Size=D3DXVECTOR2(490,16);
   draw::Basic()->f_Point=D3DXVECTOR3(WINDOWSIZEW-500,2,G()->GetInsZ());
   draw::Basic()->Set();

    DWordColor(D3DXCOLOR(0.1,0.1,0,1)); 
	DWordArea_W(WINDOWSIZEW-500+10,0,490,20);
	sprintf(DWordBuffer(),"Test Version");
	DWordDrawText(G()->m_CommonFont_S  ,DWordBuffer());


   int itW=80;
   int count=0;
   
   Menubar* m_Menubar = new Menubar();
   m_Menubar->Set=D3DXVECTOR2(itW*(count++),0);
   m_Menubar->Refresh=D3DXVECTOR2(itW*(count++),0);
   m_Menubar->Search=D3DXVECTOR2(itW*(count++),0);
   m_Menubar->Result=D3DXVECTOR2(itW*(count++),0);
   m_Menubar->Option=D3DXVECTOR2(itW*(count++),0);

   MenuBottons(m_Menubar->Set,D3DXVECTOR2(itW,20),"Set*");
   MenuBottons(m_Menubar->Refresh,D3DXVECTOR2(itW,20),"Refresh*");
   MenuBottons(m_Menubar->Search,D3DXVECTOR2(itW,20),"Search*");
   MenuSwitch(m_Menubar->Search,D3DXVECTOR2(itW,20),"Search*");
   MenuBottons(m_Menubar->Result,D3DXVECTOR2(itW,20),"Result*");
   MenuBottons(m_Menubar->Option,D3DXVECTOR2(itW,20),"Option*");

   draw::Basic()->Draw();
   DWordDrawEnd();
}