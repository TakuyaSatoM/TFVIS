
public class InstanceItem {
		static int staticID;
		int dynamicID;
		public InstanceItem() {
			staticID =0;
			dynamicID = this.hashCode();
		}
		
		public void setID(int num) {
			dynamicID = num;
		}
		
		int getDynamicID() {
			return dynamicID;
		}
		
		static int getStaticID() {
			return staticID;
		}
	
}
