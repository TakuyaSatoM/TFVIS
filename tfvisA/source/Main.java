
public class Main {

	public static void main(String[] args) {
		Ary ary = new Ary(7);

		ary.setValue(3);
		ary.setValue(0);
		ary.setValue(4);
		ary.setValue(7);
		ary.setValue(2);

		int min = ary.getMin();
		System.out.println("Å¬>>" + min);

		int max = ary.getMax();
		System.out.println("Å‘å>>" + max);

		int ave = ary.getAve();
		System.out.println("•½‹Ï>>" + ave);

	}

}
