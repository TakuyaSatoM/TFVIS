#ifndef _RMath_h_
#define _RMath_h_
#include "Common.h"
#include <string>
 
#include <sstream>
#include <vector>
#include <string>
#include <exception>

#include <Windows.h>

using namespace std;

bool InBox(float x,float y,float sx,float sy,float sw,float sh);
//ç≈è¨íl0
int Cut0(int a);
float Cut0f(float a);



float Big(float a,float b);

//è„å¿â∫å¿
char BetWeenC(char x,char l,char h);
int BetWeenI(int x,int l,int h);

float Math_BetWeen(float x,float d,float t);
float AbsSum(float i,float p);

//p1Ç©ÇÁp2Ç÷ÇÃäpìx
double Sum_FlatAngle360(double x1,double z1,double x2,double z2);
double GetDistance(double x1,double z1,double x2,double z2);


int GetNumber(int i,int x);
int GetNumber100(int x);
int GetNumber10(int x);
int GetNumber1(int x);

double FlatAngle_Simply(double x);
double Angle_Difference(double x,double y);
double Angle_Difference_plus(double x,double y);


string NumberPlusMinus(int a);

int GetRandom(int min,int max);

int GetRandom_TO(int min,int max);


void FileNameAnalysis(char* text,char* fileWhere,char* fileName);
string CutExtension(string name);

char IsPlus(double a);
double WayTarns(double x,double y,double maxturn);


 float ShortParcent(short p);

 class C_Int;
 class C_String;
namespace TEXT
{
	void SplitInt(char* text,char delim,C_Int* result);
	void Seek(char* text,char delim,int* seek,char* get);
	bool headCut(string ori,char delim,string& head,string& rest);
	void SplitString(char* text,char delim,C_String* result);
}

namespace FILES
{
	vector<string> GetFiles(const string& dir_path, const string& filter);
}
#endif