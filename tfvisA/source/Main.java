
public class Main {
	public static void main(String[] args) {

		StockControler controler = new StockControler();
		System.out.println("�݌ɊǗ��V�X�e��");

		controler.addStock("apple", 50, 7500, 0);
		controler.addStock("wine", 40, 40000, 20);
		controler.addStock("cigarette", 30, 30000, 60);

		controler.addStock("cigarette", 30, 30000, 60);

		controler.changeTax("cigarette", 70);

		int sum = controler.getTotalPrice();
		System.out.println("���v:" + sum);
		System.out.println("�݌ɊǗ��V�X�e���I��");

	}
}