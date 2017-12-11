#include "R_Math.h"
#include <math.h>
#include <time.h>



 
float Big(float a,float b)
{
 if(a > b){return a;}
 return b;
}

bool InBox(float x,float y,float sx,float sy,float sw,float sh)
{
 if(x >= sx && x < sx+sw && y >= sy && y < sy+sh) 
 {return 1;}

 return 0;
}


float ShortParcent(short p)
{
 return p/100.0;
}

double GetDistance(double x1,double z1,double x2,double z2)
{
 
 return sqrt((x1-x2)*(x1-x2)+(z1-z2)*(z1-z2)  );

}

char BetWeenC(char x,char l,char h)
{
 if(x < l)
 {return l;}

 if(x > h)
 {return h;}

 return x;
}

int BetWeenI(int x,int l,int h)
{
 if(x < l)
 {return l;}

 if(x > h)
 {return h;}

 return x;
}

float Math_BetWeen(float x,float d,float t)
{
	if(x > t){return t;}
	if(x < d){return d;}

	return x;

}

float AbsSum(float i,float p)
{
 float a=abs(i);
 a+=p;
 if(a < 0){a=0;}

 float plus=1;
 if(i < 0){plus=-1;}

 return a*plus;
}

SYSTEMTIME baseTime;

int GetRandom_TO(int min,int max)
{
 GetLocalTime(&baseTime);

 srand(rand()+baseTime.wMilliseconds*baseTime.wSecond*baseTime.wMinute);

	return GetRandom(min,max);
}

int GetRandom(int min,int max)
{
	return min + (int)(rand()*(max-min+1.0)/(1.0+RAND_MAX));
}


int Cut0(int a)
{
	if(a < 0){a = 0;}
 return a;
}
float Cut0f(float a)
{
	if(a < 0){a = 0;}
 return a;
}

double Sum_FlatAngle360(double x1,double z1,double x2,double z2)
{
 double  theta;

 if(x1==x2 && z1 == z2){return 0;}
 if(z1 == z2){z1+=0.0001;}

 theta =  atan((x1 - x2) / ( z1-z2)  ) * 180.0f / 3.14f ;
 

 if( z1 - z2 < 0){theta += 180;}
  if(theta  < 0){theta += 360;}

  if(theta < 0 || theta > 360){theta = 0;}

 
 return FlatAngle_Simply(theta+180);

}

int GetNumber(int i,int x)
{
 switch(i-1)
 {
  case 2:return GetNumber100(x);
  case 1:return GetNumber10(x);
  case 0:return GetNumber1(x);
 }


return -1;
}

int GetNumber100(int x)
{
 if(x < 100)
 {return -1;}

 int a = x/1000;
 x -= a*1000;

 

 return x/100;

}

int GetNumber10(int x)
{
 if(x < 10)
 {return -1;}


 int a = x/100;
 x -= a*100;

 return x/10;

}

int GetNumber1(int x)
{
 int a = x/10;
 x -= a*10;

 return x;

}

char IsPlus(double a)
{
 if(a < 0){return -1;}
 return 1;
}



double WayTarns(double x,double y,double maxturn)
{
 x=FlatAngle_Simply(x);
 y=FlatAngle_Simply(y);


 double r = abs(360-abs(x-y));
 double l = abs(x-y);
 double back;

 if(r >= l)
 {
  back = l;
  if(back > maxturn){back=maxturn;}

  return back * IsPlus(y-x);
 }

 back = r;
 if(back > maxturn){back=maxturn;}

 //340 20
 //x=50 y=280

 return back * IsPlus(x-y);
}


//Šp“x‚Ìâ‘Î·
double Angle_Difference(double x,double y)
{
 double z,tmp;
 if(x > y)
 {
  tmp = x;
  x = y;
  y = tmp;
 }
 
 if( y - x < 180)
 {
 
 z = (y -x);
  
 }
 else
 {
  z = (360 - y) + x;
 }
 
 return z;
}

//Šp“x·‚ÌŒü‚«
double Angle_Difference_plus(double x,double y)
{
 double tmp;
 double p = 1.0;
 
 if(x > y)
 {
  tmp = x;
  x = y;
  y = tmp;
  p= -1.0;
 
 }
 
 
 
 if( y - x >= 180)
 {
  p *= -1;
 
 }


 return p;
}

double FlatAngle_Simply(double x)
{
	if(x >= 0 && x < 360 ){return x;}

 x =x - (int)(x / 360)*360;
 if(x  < 0){x += 360;}


 return x;
}