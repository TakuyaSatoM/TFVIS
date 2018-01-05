

public class instanceTest {
	public static void main(String[] args) {

		Print print = new Print();
		Print print2 = new Print();
		Print print3 = print2;

		print.set("aaa");
		print2.set("bbb");
		print.set("ccc");

		System.out.println(print.str);
		System.out.println(print2.str);
		System.out.println(print3.str);

	}

}