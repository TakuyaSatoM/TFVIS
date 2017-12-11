#include "R_MathVector.h"


float TransRadian(float angle)
{
return (angle)*3.14/180.;
}

D3DXVECTOR3 TransRadian(D3DXVECTOR3* angleV)
{
 return D3DXVECTOR3(TransRadian(angleV->x),TransRadian(angleV->y),TransRadian(angleV->z));
} 

float TransAngle(float radian)
{
 return (radian)*180/3.14;
}

D3DXVECTOR3 TransAngle(D3DXVECTOR3* radianV)
{
	return D3DXVECTOR3(TransAngle(radianV->x),TransAngle(radianV->y),TransAngle(radianV->z));
}