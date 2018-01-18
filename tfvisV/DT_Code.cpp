#include "DataTransitions.h"
#include "Game.h"

//小ウィンドウでのコード表示
void DtDiagram::drawCode(DTCom* dt){

	RC_2DPorigon* po=draw::Basic();
	DWordDrawStart();
	draw::Basic()->SetTexture(NULL);

	DWordFormat(DT_NOCLIP);
	DWordColor(D3DXCOLOR(0,0,0,1));

	int bold=20*2;
	C_Line* indexLine=&dt->getMethod()->m_Line;
	bool shadow=true;
	while(indexLine=indexLine->CHECK()){
		

		if(indexLine->m_Top){

			DWordFormat(DT_NOCLIP);
			DWordArea_W(10*2+indexLine->m_Indent*20*2,indexLine->m_ID*bold,0,0);
			sprintf(DWordBuffer(),"%s",indexLine->m_Text.c_str());
			if(indexLine->m_EventType==ev::MEMO){//コメント用文字色
				DWordColor(D3DXCOLOR(63/255.,127/255.,95/255.,1));
				DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	
			}
			else{//通常文字色
				DWordColor(D3DXCOLOR(0,0,0,1));
				DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	
			}
			shadow=!shadow;
		}


		po->f_Point=D3DXVECTOR3(0,indexLine->m_ID*bold,G()->GetInsZ());
		po->f_Size=D3DXVECTOR2(dt->m_DrawArea.w*2,bold);
		//縞模様
		if(shadow){
			po->f_Color=D3DXVECTOR4(0,0,0,0.05);
			po->Set();
		}

		//データ遷移検索対象行ハイライト
		po->f_Size=D3DXVECTOR2(dt->m_DS,bold);
		if(G()->m_SelectLine.m_Line==indexLine){
			po->f_Color=D3DXVECTOR4(1.0,0.4,0.4,0.35);
			po->Set();
		}

		//データ遷移検索候補か(主行である、マウスカーソルの位置にある)
		if(indexLine->m_Top  && IsMouseInBox(dt->m_DrawArea.x+po->f_Point.x/2,dt->m_DrawArea.y+po->f_Point.y/2,po->f_Size.x/2,po->f_Size.y/2)){
			
			//対象行のDataReadイベントがあるか
			bool existRead=false;
			{
				Exe* indexExe=db::getExe();
				while(indexExe=indexExe->CHECK()){

					if(indexExe->m_LineID != indexLine->m_BaseID || indexExe->m_MethodExeID != dt->m_MethodExe->m_ID){continue;}

					if(indexExe->m_EventType==ev::DATAREAD){existRead=true;}
				}
			}
			
			if(existRead){//ある場合
				po->f_Color=D3DXVECTOR4(0.5,0.5,1,0.5);
				po->Set();

				if(RL_INPUT()->m_MouseL.NowPush()){
					G()->m_SelectLine.init();


					G()->m_SelectLine.m_MethodExe=dt->m_MethodExe;
					G()->m_SelectLine.m_Line=indexLine;
					G()->m_SelectLine.m_Exe.Reset();

					G()->m_SelectLine.m_SelectTurn=0;
					G()->m_SelectLine.m_SelectVar=0;

					Exe* indexExe=db::getExe();
					Exe* tmpExe=NULL;
					int before=-1;
					while(indexExe=indexExe->CHECK()){

						if(indexExe->m_MethodExeID!=dt->m_MethodExe->m_ID){continue;}

						int now=indexExe->m_LineID;				
						if(before!=now){
							if(now == indexLine->m_BaseID){
								G()->m_SelectLine.m_Exe.Add(new C_SelectLineExe(indexExe));
							}
						}
						before=now;
					}
					G()->m_SelectLine.refresh();
				}
			}
		}



	}

	draw::Basic()->Draw();
	DWordDrawEnd();
	
	return;
 
}

// オブジェクト図のフィールドの描画
void DtDiagram::drawFields(DTCom* dt){

	RC_2DPorigon* po=draw::Basic();
	DWordDrawStart();
	draw::Basic()->SetTexture(NULL);

	DWordFormat(DT_NOCLIP);
	DWordColor(D3DXCOLOR(0,0,0,1));

	int bold=20*2;
	E_Update* updateEvent = (E_Update*)dt->m_Exe->m_Event;
	UV_Instance* instance = (UV_Instance*)updateEvent->m_Updates.CHECK();
	bool shadow=true;

	Exe* indexExe = &instance->fieldExe;

	for(int i=0; i<instance->fieldNum; i++){
		indexExe = indexExe->CHECK();

		UpdateVars* field = (UpdateVars*)((E_Update*)indexExe->m_Event)->m_Updates.next();
		string targetVariableName = field->m_Target.c_str();
		string targetVariableType = field->m_Type;

		DWordFormat(DT_NOCLIP);
		DWordArea_W(10*2,i*bold,0,0);
		sprintf(DWordBuffer(),"%s %s",targetVariableType.c_str(),targetVariableName.c_str());
		DWordColor(D3DXCOLOR(0,0,0,1));
		DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	

		shadow = !shadow;
		
		po->f_Point=D3DXVECTOR3(0,i*bold,G()->GetInsZ());
		po->f_Size=D3DXVECTOR2(dt->m_DrawArea.w*2,bold);
		//縞模様
		if(shadow){
			po->f_Color=D3DXVECTOR4(0,0,0,0.05);
			po->Set();
		}

		field = (UpdateVars*)field->next();

	}


	draw::Basic()->Draw();
	DWordDrawEnd();

	return;
}