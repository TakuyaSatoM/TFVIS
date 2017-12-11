#ifndef _RL_MATHVECTOR_h_
#define _RL_MATHVECTOR_h_

#include "Common.h"
#include <d3d9.h>
#include <d3dx9.h>
 
float TransRadian(float angle);
D3DXVECTOR3 TransRadian(D3DXVECTOR3* angleV);

float TransAngle(float radian);
D3DXVECTOR3 TransAngle(D3DXVECTOR3* radianV);

D3DXVECTOR3 HSVtoRGB( D3DXVECTOR3 hsv );

double GetDistance(D3DXVECTOR2* a,D3DXVECTOR2* b);
double GetDistance(D3DXVECTOR3* a,D3DXVECTOR3* b);

double Sum_FlatAngle360(D3DXVECTOR2* a);
double Sum_FlatAngle360(D3DXVECTOR2* a,D3DXVECTOR2* b);
double Sum_FlatAngle360(D3DXVECTOR3* a,D3DXVECTOR3* b);

D3DXVECTOR3 MovePosition(D3DXVECTOR3* start,float way,float power);
D3DXVECTOR2 MovePosition(D3DXVECTOR2* start,float way,float power);
bool VectorMove(D3DXVECTOR2* now,D3DXVECTOR2* end,float* power);

bool InBox(D3DXVECTOR2* pos,D3DXVECTOR2* squareP,D3DXVECTOR2* size);



#endif