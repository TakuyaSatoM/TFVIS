#include "Class.h"
#include "LineReader.h"


int MethodExe::ms_ExistNum=0;



void Method::loadLine(){

	sprintf(DWordBuffer(),"data\\%s$%s.str",m_MyClass->m_Name.c_str(), m_FullName.c_str());
	LineReader reader(DWordBuffer());

	if(reader.GetFIle() == false){return;}
	reader.Read(this);
}





