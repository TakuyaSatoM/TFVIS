#include "S_Base.h"
#include "Game.h"

// 画面左部のクラスやメソッドの一覧の描画
void S_Base::SelectList()
{
	HideBackBuffer();
	G()->mRT_SelectList->Set(1);
	{

		RC_2DPorigon* po=draw::Basic();
		DWordDrawStart();


		draw::Basic()->SetTexture(NULL);

		DWordFormat(DT_NOCLIP);
		DWordColor(D3DXCOLOR(0,0,0,1));


		int bold=20*2;
		int yoffset=-bold+5*2;

		// クラスデータの取得
		C_Class* indexClass=db::getClass();

		// 各クラスのクラス名の取り出し
		while(indexClass=indexClass->next())
		{
			yoffset+=bold;
			DWordArea_W(5*2,yoffset,0,0);
			
			string fileName=indexClass->m_Name+"/class";
			

			sprintf(DWordBuffer(),fileName.c_str());
			DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	

			yoffset+=bold;

			// クラスが持つメソッドデータの取得
			Method* indexMethod=&indexClass->m_Method;
			

			while(indexMethod=indexMethod->next())
			{
				DWordColor(D3DXCOLOR(0,0,0,1));
				if(indexMethod->m_Line.NUM()==0){DWordColor(D3DXCOLOR(0.4,0.4,0.4,1));}

				DWordArea_W(5*2,yoffset,0,0);
				sprintf(DWordBuffer(),"・%s",indexMethod->m_FullName.c_str());
				DWordDrawText(G()->m_CommonFont  ,DWordBuffer());		
				   

				po->f_Color=D3DXVECTOR4(0.8,0.8,1.0,1);
				po->f_Point=D3DXVECTOR3(5*2,yoffset,0);
				po->f_Size=D3DXVECTOR2((G()->mDrawArea_SelectList.w-20)*2,bold);
//				   po->Set();				   
				if( IsMouseInBox(G()->mDrawArea_SelectList.x+po->f_Point.x/2,G()->mDrawArea_SelectList.y+po->f_Point.y/2,po->f_Size.x/2,po->f_Size.y/2)
					&& DTCom::whatMousePointerCovered(&G()->m_DtCom,false,false)==NULL)
				{
					po->Set();		
					if(RL_INPUT()->m_MouseL.NowPush() ){DTCom::Open(&G()->m_DtCom,db::searchMethodExe(indexMethod,0));}
				}

				yoffset+=bold;
			}

		}



	   draw::Basic()->Draw();
	   DWordDrawEnd();
	}
   RevaivalBackBuffer();


   //バッファ転写
    ot::printTexture(G()->mRT_SelectList->GetTexture(),G()->mDrawArea_SelectList,G()->mTexXY_SelectList,G()->GetInsZ());


	//枠表示
	ot::drawWindowFrame(G()->mDrawArea_SelectList,SCREEN_SPACE);


  return;
 
}