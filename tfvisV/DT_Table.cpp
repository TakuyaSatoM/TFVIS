#include "DataTransitions.h"
#include "Game.h"



//データ遷移図のメイン処理
void DtDiagram::drawTable(DTCom* dt)
{
	int command_ChangeMethodExe=-1;

	if(RL_INPUT()->m_MouseR.NowPush() && IsMouseInBox(dt->m_DrawArea.x,dt->m_DrawArea.y,dt->m_DrawArea.w,dt->m_DrawArea.h)){dt->m_DTA.release();}

	RC_2DPorigon* po=draw::Basic();
	draw::Basic()->SetTexture(NULL);
	
	DWordDrawStart();
	DWordFormat(DT_NOCLIP);
	DWordColor(D3DXCOLOR(0,0,0,1));
	
	int ds=dt->m_DS;
	
	po->f_Point=D3DXVECTOR3(ds,0,G()->GetInsZ());
	po->f_Size=D3DXVECTOR2(EVENTW,dt->m_DrawArea.h*2);
	po->f_Color=D3DXVECTOR4(0.0,0.0,0.0,0.3);
	po->Set();
	
	po->f_Point=D3DXVECTOR3(ds,0,G()->GetInsZ());
	po->f_Size=D3DXVECTOR2(2*2,dt->m_DrawArea.h*2);
	po->f_Color=D3DXVECTOR4(0.0,0.0,0.0,0.3);
	po->Set();
	
	po->f_Point=D3DXVECTOR3(ds+EVENTW-2*2,0,G()->GetInsZ());
	po->f_Size=D3DXVECTOR2(2*2,dt->m_DrawArea.h*2);
	po->f_Color=D3DXVECTOR4(0.0,0.0,0.0,0.3);
	po->Set();
	
	//表横線
	for(int i=0;i<dt->m_MethodExe->m_XWide+1;i++){

		po->f_Point=D3DXVECTOR3(ds+EVENTW+i*DTCELLW,0,G()->GetInsZ());
		po->f_Size=D3DXVECTOR2(1*2,dt->m_DrawArea.h*2);
		po->f_Color=D3DXVECTOR4(0.50,0.50,0.50,1);
		po->Set();	
	}
	
	//行別イベント名
	{
			C_Line* indexLine=&dt->getMethod()->m_Line;
			while(indexLine=indexLine->CHECK()){

					DWordFormat(DT_CENTER | DT_VCENTER);
					DWordArea_W(ds,indexLine->m_ID*LINEH,EVENTW,LINEH);
					po->f_Point=D3DXVECTOR3(ds+6*2,indexLine->m_ID*LINEH+LINEH-5*2,G()->GetInsZ());
					po->f_Size=D3DXVECTOR2(EVENTW-12*2,4*2);
					po->f_Color=D3DXVECTOR4(0.00,0,1.00,1);

					if(indexLine->m_EventType == ev::METHOD_END){

						C_Line* line=(C_Line*)indexLine->CHECK_BACK();
						if(line && line->m_EventType == ev::RETURN){
							continue;
						}
					}


					if(ev::getEventViewText(indexLine->m_EventType)!="u"){
						DWordColor(D3DXCOLOR(0.85,0.85,0.85,1));
						sprintf(DWordBuffer(),"%s",ev::getEventViewText(indexLine->m_EventType).c_str());

					}
					else{
						DWordColor(D3DXCOLOR(1,1,1,1));
						sprintf(DWordBuffer(),"%s",indexLine->m_Target.c_str());	
						po->Set();
					}

			
					DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	

			}
	}

	//データ遷移表上描画
	{
			Exe* indexExe=db::getExe();
			int mexeID=db::getExe()->GetMethodExeID(dt->getMethod()->m_MethodID,dt->m_MethodExe->m_MethodExeNum);
			while(indexExe=indexExe->CHECK()){

					if(indexExe->m_MethodExeID!=mexeID){continue;}
					C_Box cellArea;
					cellArea.x=ds+EVENTW+indexExe->m_DTXY.x*DTCELLW+3*2;
					cellArea.y=indexExe->m_DTXY.y*LINEH+2*2;
					cellArea.w=DTCELLW-6*2;
					cellArea.h=LINEH-4*2;
					indexExe->m_LastPos=D3DXVECTOR2(cellArea.x+cellArea.w/2,cellArea.y+cellArea.h/2)/2+D3DXVECTOR2(dt->m_DrawArea.x,dt->m_DrawArea.y);


					if(ev::isUpdate(indexExe->m_EventType)){//変数更新

						if(ev::isArrayUpdate(indexExe->m_EventType)==true)
						{
							// 配列の更新
							variableArrayUpdate(dt,indexExe,cellArea,po);
						}else if(ev::isInstanceUpdate(indexExe->m_EventType) == true){
							//インスタンスの更新
							InstanceUpdate(dt,indexExe,cellArea,po);
						}else{
							variableUpdate(dt,indexExe,cellArea,po);
						}
					}

					{//メソッド系
						//メソッド呼び出し
						if(indexExe->m_EventType==ev::METHOD_CALL){
							methodCall(dt,indexExe,cellArea,po);
						}
						//メソッド開始
						if(indexExe->m_EventType==ev::METHOD_START){
							methodStart(dt,indexExe,cellArea,po,command_ChangeMethodExe);
						}
						//メソッド終了
						if(indexExe->m_EventType==ev::METHOD_END){
							methodEnd(dt,indexExe,cellArea,po);
						}
					}

					//条件分岐成立
					if(indexExe->m_EventType==ev::ROUTE){
						condition(dt,indexExe,cellArea,po);
					}

					{//ループ系
						//ループ周回変更
						if(indexExe->m_EventType==ev::LOOP_NEXT){
							loopNext(dt,indexExe,cellArea,po);
						}
						//ループ終了
						if(indexExe->m_EventType==ev::LOOP_END){
							loopEnd(dt,indexExe,cellArea,po);
						}
					}

					{//try-catch
						if(indexExe->m_EventType==ev::CATCH){
							tryCatch(dt,indexExe,cellArea,po);
						}
					}

					{//Switch文
						if(indexExe->m_EventType==ev::SWITCH){
							Switch(dt,indexExe,cellArea,po);
						}
						if(indexExe->m_EventType==ev::CASE){
							Case(dt,indexExe,cellArea,po);
						}
					}

			}
	}



	po->Draw();
	DWordDrawEnd();



	//対応メソッド実行変更
	if(command_ChangeMethodExe >= 0){//変更有
		DTCom::transDTDiagram(&G()->m_DtCom,dt,db::searchMethodExe(dt->m_MethodExe->m_Method,command_ChangeMethodExe));
	}

}

void DtDiagram::drawFieldsTable(DTCom* dt){

	int command_ChangeMethodExe=-1;

	if(RL_INPUT()->m_MouseR.NowPush() && IsMouseInBox(dt->m_DrawArea.x,dt->m_DrawArea.y,dt->m_DrawArea.w,dt->m_DrawArea.h)){dt->m_DTA.release();}

	RC_2DPorigon* po=draw::Basic();
	draw::Basic()->SetTexture(NULL);
	
	DWordDrawStart();
	DWordFormat(DT_NOCLIP);
	DWordColor(D3DXCOLOR(0,0,0,1));
	
	int ds=dt->m_DS;
	
	po->f_Point=D3DXVECTOR3(ds,0,G()->GetInsZ());
	po->f_Size=D3DXVECTOR2(EVENTW,dt->m_DrawArea.h*2);
	po->f_Color=D3DXVECTOR4(0.0,0.0,0.0,0.3);
	po->Set();
	
	po->f_Point=D3DXVECTOR3(ds,0,G()->GetInsZ());
	po->f_Size=D3DXVECTOR2(2*2,dt->m_DrawArea.h*2);
	po->f_Color=D3DXVECTOR4(0.0,0.0,0.0,0.3);
	po->Set();
	
	po->f_Point=D3DXVECTOR3(ds+EVENTW-2*2,0,G()->GetInsZ());
	po->f_Size=D3DXVECTOR2(2*2,dt->m_DrawArea.h*2);
	po->f_Color=D3DXVECTOR4(0.0,0.0,0.0,0.3);
	po->Set();
	
	po->f_Point=D3DXVECTOR3(ds+EVENTW+DTCELLW,0,G()->GetInsZ());
	po->f_Size=D3DXVECTOR2(1*2,dt->m_DrawArea.h*2);
	po->f_Color=D3DXVECTOR4(0.50,0.50,0.50,1);
	po->Set();	

	E_Update* updateEvent = (E_Update*)dt->m_Exe->m_Event;
	UV_Instance* instance = (UV_Instance*)updateEvent->m_Updates.CHECK();

	//行別イベント名
	{
		for(int i=0; i<instance->fieldNum; i++){

					DWordFormat(DT_CENTER | DT_VCENTER);
					DWordArea_W(ds,i*LINEH,EVENTW,LINEH);
					po->f_Point=D3DXVECTOR3(ds+6*2,i*LINEH+LINEH-5*2,G()->GetInsZ());
					po->f_Size=D3DXVECTOR2(EVENTW-12*2,4*2);
					po->f_Color=D3DXVECTOR4(0.00,0,1.00,1);

					DWordColor(D3DXCOLOR(1,1,1,1));
					sprintf(DWordBuffer(),"%s",instance->m_Target.c_str());	
					po->Set();

					DWordDrawText(G()->m_CommonFont  ,DWordBuffer());	

			}
	}


	//データ遷移表上描画
	{
			Exe* exe  = dt->m_Exe;
			
			for(int i=0; i<instance->fieldNum; i++){
					C_Box cellArea;
					cellArea.x=ds+EVENTW+3*2;
					cellArea.y=i*LINEH+2*2;
					cellArea.w=DTCELLW-6*2;
					cellArea.h=LINEH-4*2;
					//indexExe->m_LastPos=D3DXVECTOR2(cellArea.x+cellArea.w/2,cellArea.y+cellArea.h/2)/2+D3DXVECTOR2(dt->m_DrawArea.x,dt->m_DrawArea.y);

					if(ev::isArrayUpdate(instance->m_Type == "")==true)
					{
							// 配列の更新
							variableArrayUpdate(dt,indexExe,cellArea,po);
					}else if(ev::isInstanceUpdate(indexExe->m_EventType) == true){
							//インスタンスの更新
							InstanceUpdate(dt,indexExe,cellArea,po);
					}else{
							variableUpdate(dt,indexExe,cellArea,po);
					}
			}


					
					
	}
			

	po->Draw();
	DWordDrawEnd();
}


