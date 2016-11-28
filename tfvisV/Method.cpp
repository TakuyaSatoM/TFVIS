#include "Class.h"
#include "LineReader.h"


int MethodExe::ms_ExistNum=0;



void Method::loadLine(){

	sprintf(DWordBuffer(),"data\\%s.str",m_FullName.c_str());
	LineReader reader(DWordBuffer());

	if(reader.GetFIle() == false){return;}
	reader.Read(this);
}





