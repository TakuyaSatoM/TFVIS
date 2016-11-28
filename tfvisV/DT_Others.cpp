#include "DataTransitions.h"
#include "Game.h"

const float DtDiagram::EVENTW =80*2;
const float DtDiagram::DTCELLW =50*2;
const float DtDiagram::LINEH =20*2;

const float DtDiagram::valueZ=0.8;
const float DtDiagram::boxZ=0.9;
const float DtDiagram::loopZ=0.95;
const float DtDiagram::loopTagZ=0.97;
const float DtDiagram::methodZ=0.99;
const float DtDiagram::dtaZ=0.0;


const D3DXVECTOR4 DtDiagram::dtsColor=D3DXVECTOR4(0,0,1.00,0.7);
const D3DXVECTOR4 DtDiagram::dtaColor=D3DXVECTOR4(1.0,0,0,0.7);
const D3DXVECTOR4 DtDiagram::loopsTagColor=D3DXVECTOR4(0.2,0.4,0.2,1);
const D3DXVECTOR4 DtDiagram::loopsColor=D3DXVECTOR4(0.4,0.6,0.4,1);
const D3DXVECTOR4 DtDiagram::readColor=D3DXVECTOR4(1,1,1.0,1);
const D3DXVECTOR4 DtDiagram::methodColor=D3DXVECTOR4(0.5,0.5,0.5,0.3);
const D3DXVECTOR4 DtDiagram::mouseOnColor=D3DXVECTOR4(0.5,0.5,1.0,1);
const D3DXVECTOR4 DtDiagram::dtaItemColor=D3DXVECTOR4(1.0,0.7,0.7,1);
const D3DXVECTOR2 DtDiagram::DTCELL_SIZE=D3DXVECTOR2(80,20)*2;
const D3DXVECTOR4 DtDiagram::catchColor=D3DXVECTOR4(1,0,0,1);
const D3DXVECTOR4 DtDiagram::InputColor=D3DXVECTOR4(1,1,0,0.7);




void DtDiagram::createDTA(DTCom* dt,Exe* start)
{
	{
		Exe* index=db::getExe();
		while(index=index->back())
		{
			index->m_InDTA=false;
		}
	}
	E_Update* u=(E_Update*)start->m_Event;


	DTAItem* dta=&dt->m_DTA;
	dta->release();
	
	start->createDTA(dta);
}

void DtDiagram::draw_box(RC_2DPorigon* po,float x,float y,float w,float h,D3DXVECTOR4 bc,D3DXVECTOR4 fc)
{
	po->f_Point=D3DXVECTOR3(x,y,boxZ);
	po->f_Size=D3DXVECTOR2(w,h);
	po->f_Color=D3DXVECTOR4(bc.x,bc.y,bc.z,bc.w);

	po->Set();

	po->f_Color=D3DXVECTOR4(1-bc.x,1-bc.y,1-bc.z,bc.w);

		po->f_Color=D3DXVECTOR4(fc.x,fc.y,fc.z,fc.w);

	po->Set_Box(x,y,w,h,1*2);
}
void DtDiagram::draw_box(RC_2DPorigon* po,float x,float y,float w,float h,D3DXVECTOR4 bc){draw_box(po,x,y,w,h,bc,D3DXVECTOR4(1-bc.x,1-bc.y,1-bc.z,bc.w));}
void DtDiagram::draw_box(RC_2DPorigon* po,float x,float y,float w,float h){draw_box(po,x,y,w,h,D3DXVECTOR4(1,1,1,0.5));}






void  DTAItem::drawDTA(RC_2DPorigon* po,DTCom* dt,DTAItem* parent)
{
	D3DXVECTOR2 st=parent->m_Ad->m_LastPos;
	D3DXVECTOR2 be=m_Ad->m_LastPos;

	D3DXVECTOR4 tailColor=DtDiagram::dtaColor;
	{
		MethodExe* mExe=db::searchMethodExe(m_Ad->m_MethodExeID);
		if(mExe->m_NowOpen==false)
		{
			be.x=mExe->m_EDsRect.x+mExe->m_EDsRect.z/2;
			be.y=mExe->m_EDsRect.y+mExe->m_EDsRect.w/2;

			tailColor=D3DXVECTOR4(0.5,0.5,0.5,0.5);
		}
		else
		{
			if(m_Ad->m_EventType==ev::UPDATE_INTARRAY)
			{
				be.x+=-DtDiagram::DTCELLW/4+m_INumber*12+5;
			}		
		}
	}

	D3DXVECTOR2 nor=st-be;
	float len=D3DXVec2Length(&nor);
	D3DXVec2Normalize(&nor,&nor);
	len=max(0,len-10);
	st=be+nor*len;


	
	po->Set_Arrow(D3DXVECTOR3(be.x,be.y,DtDiagram::dtaZ),D3DXVECTOR3(st.x,st.y,DtDiagram::dtaZ),4,6,16,0,DtDiagram::dtaColor,tailColor);

	
	//if(m_Child){m_Child->drawDTA(po,dt,this);}
	if(m_Sibling){m_Sibling->drawDTA(po,dt,parent);}
}



void Exe::createDTA(DTAItem* dta)
{
	dta->m_Ad=this;
	m_InDTA=true;


	C_String* infIndex=&((E_Update*)this->m_Event)->m_Infs;

	//影響後に、最終更新を検索
	while(infIndex=infIndex->CHECK()){

		Exe* index=this;
		while(index=index->back()){

			//スコープ範囲を超過
			if(index->m_EventType == ev::LIFELIMIT){

				E_LifeLimit* liL=(E_LifeLimit*)index->m_Event;
				if(liL->m_Target==infIndex->m_Text){break;}
			}

			//更新ではない
			if(ev::isUpdate(index->m_EventType )==false){continue;}
			if(index->m_Argu){continue;}


			bool existRelation=false;
			int count=0;
			UpdateVars* uin=&((E_Update*)index->m_Event)->m_Updates;
			while(uin=uin->next())
			{
				if(uin->m_Target == infIndex->m_Text)
				{
						existRelation=true;
						break;
				}
				count++;
			}

			if(existRelation)
			{
				DTAItem* newItem=new DTAItem;
				newItem->m_INumber=count;

				if(dta->m_Child==NULL){dta->m_Child=newItem;}
				else{

					DTAItem* sibIn=dta->m_Child;
					while(sibIn->m_Sibling!=NULL){sibIn=sibIn->m_Sibling;}
					sibIn->m_Sibling=newItem;
				}
					


				index->createDTA(newItem);	
				break;
			}

		}

	}



}