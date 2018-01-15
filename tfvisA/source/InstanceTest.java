
public class InstanceTest {
	public static void main(String[] args) {
		
		InstanceItem item = new InstanceItem();
		System.out.println(item.getDynamicID());
		item.setID(1);
		InstanceItem newItem = item;
	
		int staticID = newItem.getStaticID();
		int dynamicID = newItem.getDynamicID();
		
		System.out.println(staticID+","+dynamicID);
	}
}

