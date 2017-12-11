#ifndef _GAMEDEFINE_h_
#define _GAMEDEFINE_h_
#include "NHLib.h"


#define DT_XWIDEMAX (20)

#define DEFAULT_FPS (30)

extern const int   WINDOWSIZEW;
extern const int   WINDOWSIZEH;
extern const int   WORLDSIZEW;
extern const int   WORLDSIZEH;


class C_Box
{
public:
 int x,y,w,h;

C_Box(){x=y=w=h=0;}
C_Box(int ix,int iy,int iw,int ih){x=ix;y=iy;w=iw;h=ih;}

};



string GetOddType(int type,int arr);
string ReleaseArray_OddName(string name);






#define SCREEN_SPACE (15)




class DTCom;
namespace drive{
	void dataTransitionsDiagram(DTCom* dt);
	void executionDiagram();
}

#endif


