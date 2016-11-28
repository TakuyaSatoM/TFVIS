#include "Class.h"
#include "Draw.h"

namespace ot{


   //カラーパターン(オフセット)
   D3DXVECTOR4 colorPattern(int id){

		int max=10;
		id=id%max;

		D3DXVECTOR4 out;
		switch(id)
		{
			case 0:{out= D3DXVECTOR4(1.0,0.5,0.5,1)*0.7;break;}
			case 1:{out= D3DXVECTOR4(0.5,1.0,0.5,1)*0.7;break;}
			case 2:{out=D3DXVECTOR4(0.5,0.5,1.0,1)*0.7;break;}
			case 3:{out=D3DXVECTOR4(0.9,0.4,0.8,1)*0.7;break;}
			case 4:{out= D3DXVECTOR4(0.4,0.9,0.9,1)*0.7;break;}
			case 5:{out=D3DXVECTOR4(0.4,0.4,0.4,1)*0.7;break;}
			case 6:{out= D3DXVECTOR4(0.9,0.5,0.2,1)*0.7;break;}
			case 7:{out= D3DXVECTOR4(0.6,0.6,0.6,1)*0.7;break;}
			case 8:{out= D3DXVECTOR4(0.2,0.7,1.0,1)*0.7;break;}
			case 9:{out= D3DXVECTOR4(0.2,1.0,0.2,1)*0.7;break;}
			default:{out= D3DXVECTOR4(0.2,0.2,0.2,1)*0.7;break;}
		}
		out.w=1;
		return out;
   }


   void printTexture(RC_Texture* texture,C_Box area,INT2 tex,float z){

	   RC_DownSampling* po=draw::Down();

   		po->SetTexture(texture);
		po->f_Color=D3DXVECTOR4(1,1,1,1);
		po->f_Point=D3DXVECTOR3(area.x,area.y,z);
		po->f_Size=D3DXVECTOR2(area.w,area.h);
		po->f_TexUV=D3DXVECTOR4(tex.x*2,tex.y*2,area.w*2,area.h*2);
		po->Set();
		po->Draw();
   }


   void drawWindowFrame(C_Box mainArea,float bold){

	   RC_2DPorigon* po=draw::Basic();

		   po->f_Color=D3DXVECTOR4(0.2,0.2,0.2,1);
		   po->f_Point=D3DXVECTOR3(mainArea.x-bold,mainArea.y-bold,0);
		   po->f_Size=D3DXVECTOR2(bold,bold);
		   po->Set();	

		   po->f_Point=D3DXVECTOR3(mainArea.x,mainArea.y-bold,0);
		   po->f_Color=D3DXVECTOR4(0.8,0.8,0.8,1);
		   po->f_Size=D3DXVECTOR2(mainArea.w,bold);
		   po->Set();	

		   po->f_Point=D3DXVECTOR3(mainArea.x-bold,mainArea.y,0);
		   po->f_Size=D3DXVECTOR2(bold,mainArea.h);
		   po->Set();

		   po->f_Color=D3DXVECTOR4(0.2,0.2,0.2,1);
		   bold=2;
		   po->f_Point=D3DXVECTOR3(mainArea.x,mainArea.y,0);
		   po->f_Size=D3DXVECTOR2(bold,mainArea.h);
		   po->Set();
		   po->f_Size=D3DXVECTOR2(mainArea.w,bold);
		   po->Set();

		   po->f_Point=D3DXVECTOR3(mainArea.x,mainArea.y+mainArea.h-bold,0);
		   po->f_Size=D3DXVECTOR2(mainArea.w,bold);
		  
		   po->Set();

		   po->f_Point=D3DXVECTOR3(mainArea.x+mainArea.w-bold,mainArea.y,0);
		   po->f_Size=D3DXVECTOR2(bold,mainArea.h); 
		   po->Set();
		
		po->Draw();
   }


}