#include "R_LibCore.h"
#include "RL_FigureNest.h"
#include "RL_2DPorigon.h"
#include "RL_DownSampling.h"

 

RC_2DPorigon* CreateObject_2DPorigon(){return NHLib->GetFigureNest()->CreateObject<RC_2DPorigon>();}


RC_DownSampling* CreateObject_DownSampling(){return NHLib->GetFigureNest()->CreateObject<RC_DownSampling>();}



