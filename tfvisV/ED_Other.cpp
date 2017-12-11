#include "ExecutionDiagram.h"


//再帰処理時のネストによる活性化区間の描画色
D3DXVECTOR4 ExecutionDiagram::getNestLvColor(int id){
	id--;
	int max=5;
	id=id%max;

	switch(id)
	{
		case 0:{return D3DXVECTOR4(0.5,0.5,0.5,1);}
		case 1:{return D3DXVECTOR4(0.2,0.2,0.2,1);}
		case 2:{return D3DXVECTOR4(0.7,0.7,0.7,1);}
		case 3:{return D3DXVECTOR4(0.35,0.35,0.35,1);}
		case 4:{return D3DXVECTOR4(0.4,0.4,0.4,1);}
	}
	return D3DXVECTOR4(0,0,0,1);
}

