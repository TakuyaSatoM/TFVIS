#include "ExecutionDiagram.h"

const int ExecutionDiagram::classTopY=0*2;
const int ExecutionDiagram::methodTopY=20*2+2*2;
const int ExecutionDiagram::xStart=20*2;
const int ExecutionDiagram::cellH=20*2;
const int ExecutionDiagram::cellW=90*2;
const int ExecutionDiagram::space=2*2;
const float ExecutionDiagram::cellZ=0.3;
const int ExecutionDiagram::methodSpace=4*2;
const int ExecutionDiagram::startY=methodTopY+cellH*3+10*2;
const int ExecutionDiagram::wide=10*2;
const int ExecutionDiagram::Aheight=8*2;
const int ExecutionDiagram::height=2*2;


//���s�t���[�}�`��
void ExecutionDiagram::draw(){

	static int yoffset=0;
	yoffset+=50*RL_INPUT()->V_MOUSEWHEEL();

	{
		Exe* indexExe=db::getExe();
		int nowY=0;
		while( (indexExe=indexExe->CHECK()) ){
			indexExe->m_DrawY=nowY+yoffset;
			nowY+=ev::getEventWeight(indexExe->m_EventType);
		}
	}


	HideBackBuffer();
	G()->mRT_ExeFlow->Set(1);

	DWordDrawStart();
	DWordFormat(DT_CENTER | DT_VCENTER);
	DWordColor(D3DXCOLOR(1,1,1,1));

	draw::Basic()->SetTexture(NULL);
	

	RC_2DPorigon* po=draw::Basic();

	po->f_Point=D3DXVECTOR3(0,0,1);
	po->f_Color=D3DXVECTOR4(0.7,0.7,0.7,1);
	po->f_Size=D3DXVECTOR2(20*2,G()->mDrawArea_ExeFlow.h*2);
	po->Set();
	po->f_Point=D3DXVECTOR3(1*2,startY+1*2,1);
	po->f_Color=D3DXVECTOR4(0.8,0.8,0.8,1);
	po->f_Size=D3DXVECTOR2(20*2-2*2,G()->mDrawArea_ExeFlow.h*2-startY-2*2);
	po->Set();




	{
		C_Class* indexClass=db::getClass();
		while(indexClass=indexClass->next()){
			Method* indexMethod=&indexClass->m_Method;
			while(indexMethod=indexMethod->next()){
				indexMethod->m_Recursion=0;
			}
		}
	}

	//�O���t
	{
		float lineZ=0.6;
		Exe* indexExe=db::getExe();
		Exe* beforeExe=NULL;

		while( (indexExe=indexExe->CHECK()) )
		{
				lineZ-=0.00001;

				//�Y�����\�b�h���s����
				Method* method=db::searchMethod(indexExe->m_MethodID); 

				//�F�̐���
				if(indexExe->m_EventType==ev::METHOD_START){method->m_Recursion++;}
				if(indexExe->m_EventType==ev::METHOD_END){method->m_Recursion--;}
			
				//������ԕ`��
				if(indexExe->m_EventType==ev::METHOD_START ){drawExecutionSpecification(indexExe,lineZ);}
	
				//�Ăяo�����
				if(beforeExe && beforeExe->m_MethodExeID != indexExe->m_MethodExeID){drawCallArrow(beforeExe,indexExe);}
				beforeExe=indexExe;
				
		}


		//�ُ�I��
		if(beforeExe->m_EventType!=ev::METHOD_END){


			po->f_Point=D3DXVECTOR3(20*2,startY+beforeExe->m_DrawY,1);
			po->f_Color=D3DXVECTOR4(1,0,0,0.5);
			po->f_Size=D3DXVECTOR2(this->m_ClassNamesRightX-20*2,4*2);
			po->Set();

			D3DXVECTOR3 pos=po->f_Point+D3DXVECTOR3(po->f_Size.x,po->f_Size.y/2,0);
			float len=10*2;
			po->Set_Line(pos+D3DXVECTOR3(-len,-len,0),pos+D3DXVECTOR3(len,len,0),4*2,po->f_Color);
			po->Set_Line(pos+D3DXVECTOR3(len,-len,0),pos+D3DXVECTOR3(-len,len,0),4*2,po->f_Color);
		}
	}

	//���\�b�h��\���}�`��`��
	po->f_Point=D3DXVECTOR3(2*20,0,0);
	po->f_Color=D3DXVECTOR4(0.6,0.6,0.6,1);
	po->f_Size=D3DXVECTOR2(G()->mDrawArea_ExeFlow.w*2,startY);
	po->Set();
	draw::Basic()->Draw();

	G()->mRT_ExeFlow->BufferClear(D3DCLEAR_ZBUFFER);



	drawMethodFigure(po);

  
	draw::Basic()->Draw();
	DWordDrawEnd();
	RevaivalBackBuffer();
 

	//�o�b�t�@�]��
	ot::printTexture(G()->mRT_ExeFlow->GetTexture(),G()->mDrawArea_ExeFlow,G()->mTexXY_ExeFlow,G()->GetInsZ());

	//�g�\��
	ot::drawWindowFrame(G()->mDrawArea_ExeFlow,SCREEN_SPACE);

	return;
 
}


