import java.util.ArrayList;
import java.util.List;

public class StockControler {
	private int ItemID;
	private List<Item> itemList;

	public StockControler() {
		itemList = new ArrayList<Item>();
		ItemID = 0;
	}

	public void addStock(String name, int weight, int price, int tax) {
		Item item = searchItem(name);

		if (item == null) {
			item = new Item(ItemID, name, weight, price, tax);
			itemList.add(item);
			ItemID++;
			System.out.println(name + "Çìoò^ÇµÇ‹ÇµÇΩÅB");
			return;
		}

		item.addWeight(weight);
		item.addPrice(price);
		System.out.println(name + "Ç" + weight + "kgí«â¡ÇµÇ‹ÇµÇΩÅB");
	}

	public Item searchItem(String SearchName) {
		String name = "";
		Item result = null;

		for (int i = 0; i < ItemID; i++) {
			Item item = (Item) itemList.get(i);
			name = item.getName();

			if (name.equals(SearchName)) {
				result = item;
			}
		}

		return result;
	}

	public void changeTax(String name, int tax) {
		Item item = searchItem(name);
		item.setTax(tax);
	}

	public int getTotalPrice() {
		int sum = 0;
		for (int i = 0; i < ItemID; i++) {
			Item item = itemList.get(i);
			sum += item.getTaxPrice();
		}
		return sum;
	}

}
