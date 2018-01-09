#ifndef _DataTransitions_h_
#define _DataTransitions_h_
#include "DataBase.h"
#include "DTCom.h"

class C_SelectLineExe:public C_Set
{
public:

  C_SelectLineExe(){}
  C_SelectLineExe(Exe* exe){m_Exe=exe;}
  C_SelectLineExe* CHECK(){return (C_SelectLineExe*)C_Set::CHECK();}

  Exe* m_Exe;

};


class SelectLine{
public:
	MethodExe*      m_MethodExe;
	C_Line*         m_Line;
	C_SelectLineExe m_Exe;
	int             m_SelectVar;
	int             m_SelectTurn;
	Exe*            m_LastUpdate;

	SelectLine(){m_MethodExe=NULL;m_LastUpdate=NULL;}

	void init(){m_SelectVar=0;m_SelectTurn=-1;m_MethodExe=NULL;m_LastUpdate=NULL;}

	bool wasSelected(){return m_MethodExe!=NULL;}
	void refresh();
	void arrowDraw();
};



class DtDiagram
{
	public:

	static const float EVENTW;
	static const float DTCELLW;
	static const float LINEH;

	static const float valueZ;
	static const float boxZ;
	static const float loopZ;
	static const float loopTagZ;
	static const float methodZ;
	static const float dtaZ;

	static const D3DXVECTOR4 dtsColor;
	static const D3DXVECTOR4 dtaColor;
	static const D3DXVECTOR4 loopsTagColor;
	static const D3DXVECTOR4 loopsColor;
	static const D3DXVECTOR4 readColor;
	static const D3DXVECTOR4 methodColor;
	static const D3DXVECTOR4 mouseOnColor;
	static const D3DXVECTOR4 dtaItemColor;
	static const D3DXVECTOR4 catchColor;
	static const D3DXVECTOR4 InputColor;
	static const D3DXVECTOR2 DTCELL_SIZE;

	bool draw(DTCom* dt);
	bool drawTransitionsDiagram(DTCom* dt);
	bool drawInstanceDiagram(DTCom* dt);

	private:

	class Area{
		public:
			int x,y,w,h;

		void set(DTCom* dt,Exe* exe,int inum){

			x=dt->m_DS+EVENTW+exe->m_DTXY.x*DTCELLW+3*2;
			y=exe->m_DTXY.y*LINEH+2*2;
			w=DTCELLW-6*2;
			h=LINEH-4*2;	
			if(exe->m_EventType==ev::UPDATE_INTARRAY){
				x=dt->m_DS+EVENTW+exe->m_DTXY.x*DTCELLW+3*2+inum*12*2;
				w=12*2;
			}

		}
	};

	void createDTA(DTCom* dt,Exe* start);
	void draw_box(RC_2DPorigon* po,float x,float y,float w,float h,D3DXVECTOR4 bc,D3DXVECTOR4 fc);
	void draw_box(RC_2DPorigon* po,float x,float y,float w,float h,D3DXVECTOR4 bc);
	void draw_box(RC_2DPorigon* po,float x,float y,float w,float h);

	void drawCode(DTCom* dt);
	void drawFields(DTCom* dt);
	void drawTable(DTCom* dt);
	void drawFieldsTable(DTCom* dt);

	void variableUpdate(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void variableArrayUpdate(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void InstanceUpdate(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);

	void methodStart(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po,int& changeTargetMethodExe);
	void methodEnd(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void condition(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void methodCall(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void loopNext(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void loopEnd(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void tryCatch(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void Switch(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
	void Case(DTCom* dt,Exe* indexExe,C_Box box,RC_2DPorigon* po);
};


#endif 