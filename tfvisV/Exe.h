#ifndef _Exe_h_
#define _Exe_h_
#include "Event.h"
#include <iostream>
#include <string>
#include <sstream>
#include <iomanip>

class Exe;

class ExeList:public C_Set
{
public:

  ExeList(){m_Ad=NULL;}
  ExeList(Exe* exe){m_Ad=exe;}
  ExeList* next(){return (ExeList*)C_Set::CHECK();}
  ExeList* back(){return (ExeList*)C_Set::CHECK_BACK();}

  Exe* m_Ad;

};




class UpdateVars:public C_Set
{
public:

  UpdateVars(){}
  UpdateVars(string name, int id){
	  m_Target=name;
	  instanceID = id;
  }

  virtual UpdateVars* next(){return (UpdateVars*)C_Set::CHECK();}
  virtual UpdateVars* back(){return (UpdateVars*)C_Set::CHECK_BACK();}

  string m_Target;
  int instanceID;

  virtual string getNewDataText(){return "";}
};
class UV_Int:public UpdateVars
{
public:

  UV_Int(){}
  UV_Int(string name, int instanceID, int newValue):UpdateVars(name, instanceID)
  {
	m_Value=newValue;
	m_Text = string(getNewDataText());
  }
  virtual UV_Int* next(){return (UV_Int*)C_Set::CHECK();}
  virtual UV_Int* back(){return (UV_Int*)C_Set::CHECK_BACK();}

  int m_Value;
  string m_Text;

  virtual string getNewDataText()
  {
	  static char text[128];
	  sprintf(text,"%d",m_Value);
	  return text;
  }
};
class UV_Double:public UpdateVars
{
public:

  UV_Double(){}
  UV_Double(string name,int instanceID, double newValue):UpdateVars(name, instanceID)
  {
	m_Value=newValue;
	ostringstream str;
	str << m_Value;
	m_Text = str.str(); 
	
  }
  virtual UV_Double* next(){return (UV_Double*)C_Set::CHECK();}
  virtual UV_Double* back(){return (UV_Double*)C_Set::CHECK_BACK();}

  double m_Value;
  string m_Text;

  virtual string getNewDataText() {
	  static char text[128];
	  sprintf(text,"%.2lf",m_Value);
	  return text;
  }
};
class UV_String:public UpdateVars
{
public:

  UV_String(){}
  UV_String(string name, int instanceID,string text):UpdateVars(name, instanceID)
  {
	  m_Text=text;
  }
  virtual UV_String* next(){return (UV_String*)C_Set::CHECK();}
  virtual UV_String* back(){return (UV_String*)C_Set::CHECK_BACK();}

  string m_Text;

  virtual string getNewDataText() {
	  static char text[128];
	  sprintf(text,"%s",m_Text.c_str());
	  return text;
  }
};


class DTCom;
class DTAItem
{
	public:
	Exe* m_Ad;
	int m_INumber;
	DTAItem* m_Child;
	DTAItem* m_Sibling;

	DTAItem()
	{
		m_INumber=0;
		m_Ad=NULL;
		m_Child=NULL;
		m_Sibling=NULL;
	}
	~DTAItem(){release();}

	void release();
	void drawDTA(RC_2DPorigon* po,DTCom* dt,DTAItem* parent);
};

class Event
{
	public:

	Event(){}
	virtual ~Event(){}
};

class E_Update:public Event
{
	public:
	UpdateVars m_Updates;
	C_String  m_Infs;
	boolean standard_Input;
	int instanceID;

	E_Update(int id){instanceID=id;}

	void SetInt(char* stock);
	void SetIntArray(char* stock);

	void SetDouble(char* stock);
	void SetDoubleArray(char* stock);

	void SetString(char* stock);
	void SetStringArray(char* stock);

	void setInputState(char* Input);
	bool getInputState();

	static int existRelation(E_Update* base,E_Update* inf);
};



class E_LoopStart:public Event
{
	public:
	Exe* m_Start;
	int m_Count;

	E_LoopStart(Exe* start);

};

class E_LoopNext:public Event
{
	public:
	Exe* m_Start;
	int m_Turn;

	E_LoopNext(Exe* start);

};
class E_LoopEnd:public Event
{
	public:
	Exe* m_Start;

	E_LoopEnd(Exe* start);

};
class E_Con:public Event
{
	public:

	bool m_True;
	E_Con();

};

class E_LifeLimit:public Event
{
	public:

	string m_Target;
	E_LifeLimit(string target);

};

class E_Catch:public Event
{
	public:
	Exe* m_Try;
	E_Catch(Exe* m_Try);
};

class E_Try:public Event
{
	public:
	Exe* m_Try;
	E_Try(Exe* m_Try);
};

class E_TryEnd:public Event
{
	public:
	Exe* m_Try;
	E_TryEnd(Exe* m_Try);
};

class E_Switch:public Event
{
	public:
	Exe* m_Case;
	E_Switch(Exe* m_Case);
};

class E_Case:public Event
{
	public:
	Exe* m_Switch;
	E_Case(Exe* m_Switch);
};


class Exe:public C_Set
{
public:

  Exe()
  {
	  m_EventType=0;
	  m_Number=-1;
	  m_Event=NULL;
	  m_DTXY=0;
	  m_InDTA=false;
	  m_DrawY=0;
	  m_Argu=false;
  }
  ~Exe()
  {
	  if(m_Event){delete m_Event;}
  }
  Exe* CHECK(){return (Exe*)C_Set::CHECK();}
  Exe* back(){return (Exe*)C_Set::CHECK_BACK();}

  INT2 m_DTXY;




  D3DXVECTOR2 m_LastPos;

  int m_Number;
  int m_EventType;
  int m_LineID;
  int m_InstanceID;
  int m_MethodID;
  int m_MethodExeID;
  bool m_InDTA;
  float m_DrawY;
  bool m_Argu;


    C_String  m_Ref;
  Event* m_Event;

  int GetMethodExeID(int methodID,int number);

  void createDTA(DTAItem* dta);

 

};


#endif 