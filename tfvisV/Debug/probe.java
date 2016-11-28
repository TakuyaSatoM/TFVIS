 /*TVIS*/import java.io.FileWriter;
 /*TVIS*/import java.io.IOException;
 /*TVIS*/import java.io.PrintWriter;
 public class main {
	 public static void main ( String [ ] args ) {
	 /*TVIS*/try{ 
 PrintWriter TVIS_FILES = new PrintWriter(new FileWriter("C:/workspace/Avis5/data/result.tv_r"));
		 int [ ] data = { 4 , 7 , 10 , 2 , 5 } ;
		 /*TVIS*/TVIS_FILES.println("2#1#"+"data"+"#"+" 4 , 7 , 10 , 2 , 5 ,"+"#");
			 for ( int i = 0 ; i < data . length - 1 ; i ++ )
			 {
			 /*TVIS*/TVIS_FILES.println("3#3#0#");
			 /*TVIS*/TVIS_FILES.print("3#2#"+"i"+"/"+"#"+i+"#");
			 /*TVIS*/TVIS_FILES.println("i"+"/"+"#");
					 for ( int j = 0 ; j < data . length - i - 1 ; j ++ )
					 {
					 /*TVIS*/TVIS_FILES.println("5#3#0#");
					 /*TVIS*/TVIS_FILES.print("5#2#"+"j"+"/"+"#"+j+"#");
					 /*TVIS*/TVIS_FILES.println("j"+"/"+"#");
						 if ( data [ j ] > data [ j + 1 ] )
						 {
								 int asc =  data [ j ] ;
								 /*TVIS*/TVIS_FILES.print("9#2#"+"asc"+"/"+"#"+asc+"#");
								 /*TVIS*/TVIS_FILES.println("data"+"["+(j)+"]"+"/"+"#");
								 data [ j ] =  data [ j + 1 ] ;
								 /*TVIS*/TVIS_FILES.print("10#2#"+"data"+"["+(j)+"]"+"/"+"#"+data [ j ]+"#");
								 /*TVIS*/TVIS_FILES.println("data"+"["+(j+1)+"]"+"/"+"#");
								 data [ j + 1 ] = asc ;
								 /*TVIS*/TVIS_FILES.print("11#2#"+"data"+"["+(j+1)+"]"+"/"+"#"+data [ j + 1 ]+"#");
								 /*TVIS*/TVIS_FILES.println("asc"+"/"+"#");
							 }
					 /*TVIS*/TVIS_FILES.println("5#3#1#");
					 }
			 /*TVIS*/TVIS_FILES.println("3#3#1#");
			 }
			 for ( int i = 0 ; i < data . length ; i ++ )
			 {
			 /*TVIS*/TVIS_FILES.println("15#3#0#");
			 /*TVIS*/TVIS_FILES.print("15#2#"+"i"+"/"+"#"+i+"#");
			 /*TVIS*/TVIS_FILES.println("i"+"/"+"#");
				 System . out . print ( data [ i ] + "," ) ;
			 /*TVIS*/TVIS_FILES.println("15#3#1#");
			 }
	 /*TVIS*/TVIS_FILES.close();
	 /*TVIS*/TVIS_FILES = null;
}catch(IOException e) {e.printStackTrace();}
	 }
 }
