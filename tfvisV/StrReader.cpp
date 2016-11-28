#include "StrReader.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;
#include "Class.h"

#define TYPE_CLASS (0)
#define TYPE_METHOD (1)

void StrReader::_ClassRead(C_Class* top)
{
	top->Add(m_NowClass=new C_Class());
	//�N���X��
	if(SeekDel('#')){return;}
	m_NowClass->m_Name=StockText;

	if(SeekDel('#')){return;}
	m_NowClass->m_ClassID = Read_Int();
}

void StrReader::_MethodRead()
{
	if(m_NowClass==NULL){_ASSERT_EXPR( 0, TEXT("�N���X������܂���") );}

	Method* method;
	m_NowClass->m_Method.Add(method=new Method());

	//���\�b�h��
	if(SeekDel('#')){return;}
	method->m_Name=StockText;

	if(SeekDel('#',false)){return;}
	method->m_FullName=StockText;

	if(SeekDel('#')){return;}
	method->m_MethodID = Read_Int();


	method->m_MyClass=m_NowClass;

	method->loadLine();
}

/*-------------------------------------------------------------
�A�j���f�[�^�̓ǂݍ���
return �A�j���f�[�^
*///-----------------------------------------------------------
void StrReader::Read(C_Class* ctop)
{
 if(ctop == NULL){return;}

 m_NowClass=NULL;
 while(true)
 {
	if(SeekDel('#')){return;}
	int id= Read_Int();
	switch(id)
	{
		case TYPE_CLASS:{_ClassRead(ctop);}break;
		case TYPE_METHOD:{_MethodRead();}break;
		default:{ _ASSERT_EXPR( 0, TEXT("�s����id") );}
	}
 }

 return;
} 
