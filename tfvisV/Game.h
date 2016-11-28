#ifndef _Game_h_
#define _Game_h_
#include "Common.h"
#include "GameDefine.h"
#include "Time.h"
#include "StrReader.h"
#include "ExeReader.h"
#include "DataBase.h"
#include "DTCom.h"
#include "DataTransitions.h"
#include "Draw.h"


class S_Base;



class C_GameNest
{
 
friend float GAMESPEED();
friend void SceneDrive();


public:
RC_RenderingTexture* mRT_ExeFlow;
RC_RenderingTexture* mRT_Spe;
C_Box mDrawArea_ExeFlow;
INT2  mSize_ExeFlow;
INT2  mTexXY_ExeFlow;

RC_RenderingTexture* mRT_SelectList;
C_Box mDrawArea_SelectList;
INT2  mTexXY_SelectList;
INT2  mSize_SelectList;

RC_RenderingTexture* mRT_Output;
C_Box mDrawArea_Output;
INT2  mTexXY_Output;
INT2  mSize_Output;



DTCom m_DtCom;

SelectLine m_SelectLine;

Exe* m_SelectExe;


Exe* m_NameExe;


char Text[256];


bool m_DeviceLost;
void SystemRecover();


C_GameNest();
~C_GameNest();

bool Create();
bool Release();


private:
S_Base* m_MainTask;


public:

void doMainTask();


float m_FlashCounter;
float FlashDecimal();

void FrameDealing();


int m_CommonFont;
int m_CommonFont_S;

float m_InsZ;
float GetInsZ(){m_InsZ+=0.00001;return 1-m_InsZ;}


};

C_GameNest* G();




#endif