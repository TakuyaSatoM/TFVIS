#include "R_Input.h"
#include "R_LibCore.h"

RC_InputNest* RL_INPUT(){return NHLib->GetInputNest();}
 

bool IsMouseInBox(int x,int y,int w,int h){return NHLib->GetInputNest()->__IsMouseInBox(x,y,w,h);}