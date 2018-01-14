
public class InstanceTest {
	public static void main(String[] args) {		
		InstanceItem item = new InstanceItem();
		item.setID(1);
		InstanceItem newItem = item;
		
		int id = newItem.getID();
		System.out.println(id);
	}
}

