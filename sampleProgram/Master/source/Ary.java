
public class Ary {

	
	int num;
	int maxSize;
	int[] values;
	

	public Ary(int isize){
		num=0; 		
		maxSize=isize;		
		values = new int[maxSize];
	}
	

	public int setValue(int val){
		

		if(num >= maxSize){return 0;}
		num++;
		values[num]=val;
		return 1;
	}
	
	public int getMin(){
			

		if(num <= 0){return 0;}	
		int min=values[0];
		

		for(int i=1;i<num;i++){
			if(min > values[i]){
				min=values[i];
			}
		}
		return min;
	}
	public int getMax(){

		if(num <= 0){return 0;}		
		int max=values[0];	

		for(int i=1;i<num;i++ ){	
			if(max < values[i]){
				max=values[i];
			}
		}
		return max;
	}
	
	public int getAve(){
		
		if(num <= 0){return 0;}		
		int sum=0;
		for(int i=0;i<num;i++){		
			sum+=values[i];
		}
		int ave=sum/num;
		return ave;		
	}	
	
	
}