
public class Item {
	private int id;
	private String name;
	private int weight;
	private int price;
	private int tax;

	public Item(int id, String name, int weight, int price, int tax) {
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.tax = tax;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public int getTaxPrice() {
		int total = price + tax / 100;
		return total;
	}

	public void addWeight(int weight) {
		this.weight += weight;
	}

	public void addPrice(int price) {
		this.price += price;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public void print() {
		System.out.println("ID:" + this.id);
		System.out.println("Name:" + this.name);
		System.out.println("Stock:" + this.weight);
		System.out.println("Unit Price:" + this.price);

		return;
	}

}