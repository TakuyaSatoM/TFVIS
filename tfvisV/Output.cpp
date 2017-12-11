#include "S_Base.h"
#include "Game.h"
#include <typeinfo.h>

//画面下部のデータ遷移線の注釈
void dtIcon(DTAItem* dIt,RC_2DPorigon* po,D3DXVECTOR2 center,int depth){
			enum Variables {m_Int=0, m_Double=1, m_Str=2} m_Variables=m_Int;

			if(dIt->m_Ad==NULL){return;}

			/*memo*************************************
			tar->m_Value 注釈に出力する値

			tar eup->m_UPdates.CHECKをUV_Int型にキャストしたもの
			m_Updates E_Updateクラスで生成される、UpdateVars型
			UpdateVars C_Setを継承、m_Targetにnameを格納
			C_Setクラス f_Nextを辿るCHECK()やf_Backを辿るCHECK_BACK()など C_IntとC_StrがあるがC_Doubleはない？

			eup dIt->m_Ad->m_EventをE_Update型にキャストしたもの
			DTAItemクラス Exe*型のm_Ad、m_Inumber、m_Child、m_Sibling(兄弟ノード？)の定義と初期化
			m_Ad 多分関係リスト

			*******************************************/
			E_Update* eup=(E_Update*)dIt->m_Ad->m_Event;
			UV_Int* tar_Int=(UV_Int*)eup->m_Updates.CHECK();
			UV_Double* tar_Double=(UV_Double*)eup->m_Updates.CHECK();
			UV_String* tar_Str=(UV_String*)eup->m_Updates.CHECK();
			int nexts=dIt->m_INumber;

			switch(dIt->m_Ad->m_EventType){
			case ev::UPDATE_INT:
				for(int i=0;i<dIt->m_INumber;i++)
				{
					tar_Int=(UV_Int*)tar_Int->CHECK();
				}
				m_Variables = m_Int;
				break;
				
			case ev::UPDATE_DOUBLE:
				for(int i=0;i<dIt->m_INumber;i++)
				{
					tar_Double=(UV_Double*)tar_Double->CHECK();
				}
				m_Variables = m_Double;
				break;
				
			case ev::UPDATE_STRING:
				for(int i=0;i<dIt->m_INumber;i++)
				{
					tar_Str=(UV_String*)tar_Str->CHECK();
				}
				m_Variables = m_Str;
				break;

			default:
				break;
			}

			int x,y,z,w;
			z=80*2;
			w=30*2;
			x=center.x-z/2;
			y=center.y-w/2;

			po->f_Color=D3DXVECTOR4(0.0,0.0,0.0,0.5);
			po->Set_Box(x,y,z,w,2*2);
			
			DWordColor(D3DXCOLOR(0.3,0,0,1));
			DWordFormat(DT_NOCLIP);	
			DWordArea_W(x,y-20*2,0,0);
			switch(m_Variables){
			case m_Int:
				sprintf(DWordBuffer(),"%s", tar_Int->m_Target.c_str());
				break;
				
			case m_Double:
				sprintf(DWordBuffer(),"%s", tar_Double->m_Target.c_str());
				break;
				
			case m_Str:
				sprintf(DWordBuffer(),"%s", tar_Str->m_Target.c_str());
				break;

			default:
				break;
			}
			DWordDrawText(G()->m_CommonFont  ,DWordBuffer());		

			DWordColor(D3DXCOLOR(0,0,0,1)); 
			DWordFormat(DT_CENTER | DT_VCENTER);
			DWordArea_W(x,y,z,w);
			
		
			switch(m_Variables){
				case m_Int:
				sprintf(DWordBuffer(),"%s", tar_Int->m_Text.c_str());
				break;
				
			case m_Double:
				sprintf(DWordBuffer(),"%s", tar_Double->m_Text.c_str());
				break;
				
			case m_Str:
				sprintf(DWordBuffer(),"%s", tar_Str->m_Text.c_str());
				break;
			default:
				break;
			}
			DWordDrawText(G()->m_CommonFont  ,DWordBuffer());
			

			depth--;
			if(depth < 0){return;}

			Method* method=db::searchMethod(dIt->m_Ad->m_MethodID);
			C_Line* line=db::searchLine(method,dIt->m_Ad->m_LineID);

			DWordColor(D3DXCOLOR(0.0,0.4,0.2,1));
			DWordFormat(DT_NOCLIP);	
			DWordArea_W(x+90*2,y-10*2,0,0);
			sprintf(DWordBuffer(),"%s",line->m_Text.c_str());
			DWordDrawText(G()->m_CommonFont  ,DWordBuffer());

			int xcount=0;
			int xdif=150*2;


			DTAItem* dIndex=dIt->m_Child;
			while(dIndex){

				D3DXVECTOR2 newPos=center+D3DXVECTOR2(xdif*(xcount++),60*2);
				po->Set_Arrow(D3DXVECTOR3( newPos.x, newPos.y,0.5),D3DXVECTOR3(center.x,center.y,0.5),10,10,20,0,D3DXVECTOR4(1,0,0,0.3));
				dtIcon(dIndex,po,newPos,depth);

				dIndex=dIndex->m_Sibling;
			}
}

//データ遷移検索条件指定
void refMode(){


			DWordColor(D3DXCOLOR(0.3,0,0,1));
			DWordFormat(DT_NOCLIP);	
			DWordArea_W(700*2,5*2,0,0);
			DWordDrawText(G()->m_CommonFont  ,"データ遷移検索");

			DWordFormat(DT_CENTER | DT_VCENTER);	

			RC_2DPorigon* po=draw::Basic();
			po->SetTexture(NULL);	
			po->f_Color=D3DXVECTOR4(0.7,0.7,0.7,1);
			po->Set_Box(650*2,1*2,500*2,180*2,1*2);

			if(RL_INPUT()->m_MouseR.NowPush()){G()->m_SelectLine.init();}




			D3DXVECTOR4 rect;
			{
				C_String* index=&G()->m_SelectLine.m_Line->m_Ref;
				int count=0;
				while(index=index->CHECK()){

					rect.x=700*2+100*2*count;rect.y=30*2;
					rect.z=80*2;rect.w=30*2;

					if(G()->m_SelectLine.m_SelectVar==count){//選択中
						po->f_Point=D3DXVECTOR3(rect.x,rect.y,0);
						po->f_Size=D3DXVECTOR2(rect.z,rect.w);
						po->f_Color=D3DXVECTOR4(1,0,0,0.5);
						po->Set();
					}
					if(IsMouseInBox(G()->mDrawArea_Output.x+rect.x/2,G()->mDrawArea_Output.y+rect.y/2,rect.z/2,rect.w/2)){//マウス上
						po->f_Point=D3DXVECTOR3(rect.x,rect.y,0);
						po->f_Size=D3DXVECTOR2(rect.z,rect.w);
						po->f_Color=D3DXVECTOR4(1,1,1,0.5);
						po->Set();		

						if(RL_INPUT()->m_MouseL.NowPush()){G()->m_SelectLine.m_SelectVar=count;G()->m_SelectLine.refresh();}
					}

					DWordArea_W(rect.x,rect.y,rect.z,rect.w);
					sprintf(DWordBuffer(),"%s",index->m_Text.c_str());
					DWordDrawText(G()->m_CommonFont  ,DWordBuffer());

					count++;
				}
			}
			{
				C_SelectLineExe* index=&G()->m_SelectLine.m_Exe;
				int count=0;
				while(index=index->CHECK()){

					rect.x=700*2+50*2*count;rect.y=70*2;
					rect.z=30*2;rect.w=30*2;

					if(G()->m_SelectLine.m_SelectTurn==count){//選択中
						po->f_Point=D3DXVECTOR3(rect.x,rect.y,0);
						po->f_Size=D3DXVECTOR2(rect.z,rect.w);
						po->f_Color=D3DXVECTOR4(1,0,0,0.5);
						po->Set();
					}
					if(IsMouseInBox(G()->mDrawArea_Output.x+rect.x/2,G()->mDrawArea_Output.y+rect.y/2,rect.z/2,rect.w/2)){//マウス上
						po->f_Point=D3DXVECTOR3(rect.x,rect.y,0);
						po->f_Size=D3DXVECTOR2(rect.z,rect.w);
						po->f_Color=D3DXVECTOR4(1,1,1,0.5);
						po->Set();		

						if(RL_INPUT()->m_MouseL.NowPush()){G()->m_SelectLine.m_SelectTurn=count;G()->m_SelectLine.refresh();}
					}

					DWordArea_W(rect.x,rect.y,rect.z,rect.w);
					sprintf(DWordBuffer(),"%d",count);
					DWordDrawText(G()->m_CommonFont  ,DWordBuffer());

					count++;
				}
			}

			po->Draw();
}

void S_Base::Output()
{
	HideBackBuffer();
	G()->mRT_Output->Set(1);

	DWordDrawStart();
	DWordFormat(DT_NOCLIP);


	{
		RC_2DPorigon* po=draw::Basic();
		draw::Basic()->SetTexture(NULL);	
		DTCom* next=&G()->m_DtCom;
		DTCom* tmp;
		int drawY=5*2;

		while(next=next->CHECK()){
			
			tmp=next;
			DTAItem* dIt=&tmp->m_DTA;
			if(dIt->m_Ad==NULL){continue;}
			dtIcon(dIt,po,D3DXVECTOR2(150*2,50*2),1);
			break;
		}	
		
		po->Draw();
	}
	
	if(G()->m_SelectLine.wasSelected()){
		refMode();
	}
	
	DWordDrawEnd();

   RevaivalBackBuffer();

   //バッファ転写
   ot::printTexture(G()->mRT_Output->GetTexture(),G()->mDrawArea_Output,G()->mTexXY_Output,G()->GetInsZ());


   //枠表示
   ot::drawWindowFrame(G()->mDrawArea_Output,SCREEN_SPACE);



  return;
 
}