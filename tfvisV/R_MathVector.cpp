#include "R_MathVector.h"
#include "R_LibCore.h"
#include "R_Screen.h"
#include "R_Math.h"



float Fraction( float v )
{
	return v - floor(v);
} 


D3DXVECTOR3 HSVtoRGB( D3DXVECTOR3 hsv )
{
	const float h = Fraction(hsv.x);
	const float s = hsv.y;
	const float v = hsv.z;
	const float hueF = h * 6.0f;
	const int hueI = static_cast<int>(hueF);
	const float fr = hueF - hueI;
	const float m = v * (1.0f-s);
	const float n = v * (1.0f-s*fr);
	const float p = v * (1.0f-s*(1.0f-fr));

	D3DXVECTOR3 rgb;

	switch(hueI)
	{
		case 0: rgb.x = v; rgb.y = p; rgb.z = m; break;
		case 1: rgb.x = n; rgb.y = v; rgb.z = m; break;
		case 2: rgb.x = m; rgb.y = v; rgb.z = p; break;
		case 3: rgb.x = m; rgb.y = n; rgb.z = v; break;
		case 4: rgb.x = p; rgb.y = m; rgb.z = v; break;
		default: rgb.x = v; rgb.y = m; rgb.z = n; break;
	}

	return rgb;
}

bool InBox(D3DXVECTOR2* pos,D3DXVECTOR2* squareP,D3DXVECTOR2* size)
{
 return InBox(pos->x,pos->y,squareP->x,squareP->y,size->x,size->y);
}

bool VectorMove(D3DXVECTOR2* now,D3DXVECTOR2* end,float *power)
{
  D3DXVECTOR2 moves=*end-*now;
  float dis = D3DXVec2Length(&moves);
  D3DXVec2Normalize(&moves,&moves);

  if(dis <= *power)
  {
    *now=*end;
	*power -= dis;
	return 1;
  }

  *now+=moves*(*power);
  *power=0;
  return 0;
}

double Sum_FlatAngle360(D3DXVECTOR2* a)
{
 return Sum_FlatAngle360(&D3DXVECTOR2(0,0),a);
}

D3DXVECTOR2 WayToVector(float way)
{
 D3DXVECTOR2 vec;

	 vec.x=cos(D3DXToRadian(way));
	 vec.y=sin(D3DXToRadian(way));

	 D3DXVec2Normalize(&vec,&vec);

 return vec;
}

D3DXVECTOR2 MovePosition(D3DXVECTOR2* start,float way,float power)
{
 D3DXVECTOR2 vec=*start;

	 vec.x+=power*cos(D3DXToRadian(way));
	 vec.y+=power*sin(D3DXToRadian(way));

 return vec;
}
D3DXVECTOR3 MovePosition(D3DXVECTOR3* start,float way,float power)
{
 D3DXVECTOR3 vec=*start;

	 vec.x+=power*cos(D3DXToRadian(way));
	 vec.z+=power*sin(D3DXToRadian(way));

 return vec;
}



double Sum_FlatAngle360(D3DXVECTOR2* a,D3DXVECTOR2* b)
{
 return Sum_FlatAngle360(a->x,a->y,b->x,b->y);
}
double Sum_FlatAngle360(D3DXVECTOR3* a,D3DXVECTOR3* b)
{
 return Sum_FlatAngle360(a->x,a->z,b->x,b->z);
}


double GetDistance(D3DXVECTOR2* a,D3DXVECTOR2* b)
{
 return GetDistance(a->x,a->y,b->x,b->y);
}
double GetDistance(D3DXVECTOR3* a,D3DXVECTOR3* b)
{
 return sqrt((a->x-b->x)*(a->x-b->x) + (a->y-b->y)*(a->y-b->y)+(a->z-b->z)*(a->z-b->z));
}
