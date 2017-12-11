
#ifndef _ExeReader_h_
#define _ExeReader_h_
#include "RL_Reader.h"



class Exe;
class ExeReader:public C_Reader
{
protected:


public:
ExeReader(char* filename)
:C_Reader(filename)
{

}
virtual ~ExeReader(){}

void read(Exe* ctop); 


};


#endif