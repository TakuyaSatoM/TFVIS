



#ifndef _StrReader_h_
#define _StrReader_h_
#include "RL_Reader.h"



class C_Class;
class StrReader:public C_Reader
{
protected:


public:
StrReader(char* filename)
:C_Reader(filename)
{

}
virtual ~StrReader(){}

void read(C_Class* ctop); 

C_Class* m_NowClass;
void _ClassRead(C_Class* top);
void _MethodRead();

};


#endif