#ifndef _ExecutionDiagram_h_
#define _ExecutionDiagram_h_
#include "DataBase.h"
#include "Game.h"


class ExecutionDiagram{

	public:

	void draw();

	private:

	D3DXVECTOR4 getNestLvColor(int id);

	static const int classTopY;
	static const int methodTopY;
	static const int xStart;
	static const int cellH;
	static const int cellW;
	static const int space;
	static const float cellZ;
	static const int methodSpace;
	static const int startY;

	static const int wide;
	static const int Aheight;
	static const int height;

	int m_ClassNamesRightX;

	void drawMethodNameTab(Method* method,int number,RC_2DPorigon* po,D3DXVECTOR4 mainColor);

	void drawMethodFigure(RC_2DPorigon* po);


	void drawExecutionSpecification(Exe* indexExe,float zlv);
	void drawCallArrow(Exe* beforeExe,Exe* nowExe);

	public:
	ExecutionDiagram(){m_ClassNamesRightX=0;}
};


#endif 