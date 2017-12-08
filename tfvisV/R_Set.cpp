#include "R_Set.h"

//�g�b�v�v�f �R���X�g���N�^
C_Set::C_Set()
{
	f_Next=f_Back=f_Top=this;
}
 
void C_Set::CHANGE()
{
	C_Set* im=this;
	C_Set* target=im->f_Next;
	C_Set* start=this->f_Back;
	C_Set* end=target->f_Next;

	start->f_Next=target;
	target->f_Back=start;
	target->f_Next=im;
	im->f_Back=target;
	im->f_Next=end;
	end->f_Back=im;
}

int C_Set::NUM()
{
	C_Set* index=f_Top->f_Next;
	int count=0;
	while(index != f_Top)
	{
		index=index->f_Next;
		count++;
	}
	return count;
}

void C_Set::Reset()
{
	if(this!=this->f_Top)
	{
		_ASSERT_EXPR( 0, TEXT("C_Set Add�s���ȃg�b�v����̃��Z�b�g") );
		return;
	}

	C_Set* index=f_Top->f_Next;
	while(index != f_Top)
	{
		C_Set* tmp=index;
		index=index->f_Next;
		tmp->Delete();
	}

	this->f_Next=this;
	this->f_Back=this;
}

void C_Set::Add(C_Set* in)
{
	if(this!=this->f_Top)
	{
		_ASSERT_EXPR( 0, TEXT("C_Set Add�s���ȃg�b�v�ւ̒ǉ�") );
		return;
	}

   in->f_Top=this;
   in->f_Next=this;
   in->f_Back=this->f_Back;
   this->f_Back->f_Next=in;
   this->f_Back=in;

}
void C_Set::Out()
{
   if(f_Top != this)
   {
		C_Set *index= this;
 
		index->f_Back->f_Next=index->f_Next;
		index->f_Next->f_Back=index->f_Back;

		this->f_Next=this->f_Back=NULL;
   }
 
}
void C_Set::Delete()
{
   if(f_Top != this)
   {
		C_Set *index= this;
 
		if(index->f_Back){index->f_Back->f_Next=index->f_Next;}
		if(index->f_Next){index->f_Next->f_Back=index->f_Back;}
   }
   delete this;
 
}
         
C_Set::~C_Set()
{
	if(f_Top != this){return;}


	C_Set* index=f_Next;
	C_Set* tmp;
	while(index != this)
	{
		tmp=index->f_Next;
		index->Delete();
		index=tmp;
	}

}
        
