



#ifndef _LineReader_h_
#define _LineReader_h_
#include "RL_Reader.h"



class Method;
class LineReader:public C_Reader
{
protected:


public:
LineReader(char* filename)
:C_Reader(filename)
{

}
virtual ~LineReader(){}

void Read(Method* cl); 

Method* m_Method;


};


#endif