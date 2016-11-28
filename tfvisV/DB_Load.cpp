
#include "Game.h"

namespace db{


	int load(){

		//基本構造データ読込
		  {
			StrReader read("Data\\SystemStr.txt");
			read.Read(getClass());
		  }

		  //実行結果読込
		  {
			  ExeReader read("Data\\Method.txt");
			  read.Read(getExe());
		  }

		  //各メソッドの呼び出し回数を計算・メソッド実行を登録
		  {
				Exe* index=getExe();
				int count=0;
				while(index=index->CHECK()){

					if(index->m_EventType == ev::METHOD_START){

						MethodExe* me;
						getMethodExe()->Add(me=new MethodExe);
						me->m_ID=count++;
						me->m_Method=db::searchMethod(index->m_MethodID);
						me->m_MethodExeNum=me->m_Method->m_CallNum;
						me->m_Method->m_CallNum++;
					}
				}		  

				//各メソッド実行の開始と終了を把握
				db::setRelation_MethodExe_andExe();
		  }
		  
		  //データ遷移図作成
		  if(createDTTable()){
			//エラー処理
			ERROR_EXIT();
		  }



		  return 0;
	}



}