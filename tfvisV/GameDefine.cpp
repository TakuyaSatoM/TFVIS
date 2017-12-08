#include "GameDefine.h"
#include "DataTransitions.h"
#include "ExecutionDiagram.h"



const int   WINDOWSIZEW=1280;
const int   WINDOWSIZEH=720;
const int   WORLDSIZEW=1024;
const int   WORLDSIZEH=400;



string GetOddType(int type,int arr){

	switch(type)
	{
	case 95:{if(arr){return "int[]";}return "int";}
	}

	return "undefined";
}

string ReleaseArray_OddName(string name){

	string out="";
	for(int i=0;i<name.size();i++)
	{
		if(name[i]=='['){break;}

		out+=name[i];
	}
	return out;
}

namespace drive{

	void dataTransitionsDiagram(DTCom* dt){
		static DtDiagram dia;
		dia.draw(dt);
	}
	void executionDiagram(){
		static ExecutionDiagram dia;
		dia.draw();
	}

}