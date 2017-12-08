#include "Draw.h"
#include "Game.h"

namespace{

	RC_DownSampling* p_Down;
	RC_2DPorigon* p_2DCommon;
}


namespace draw{


	bool stanby(){

		 p_2DCommon=CreateObject_2DPorigon();
		 p_2DCommon->Build(1000,true,true,NULL);


		 p_Down=CreateObject_DownSampling();
		 p_Down->Build(1,true,false,NULL);

		 return false;
	}



	RC_DownSampling* Down(){return p_Down;}
	RC_2DPorigon* Basic(){return p_2DCommon;}

}