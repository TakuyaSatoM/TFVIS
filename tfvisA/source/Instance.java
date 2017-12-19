
import java.lang.reflect.Field;

public class Print {
	static String str;

	public Print() {
		str = "";
		System.out.println(this.hashCode());
	}

	public void set(String newStr) {
		str = newStr;
		System.out.println(str);

		return;
	}

}