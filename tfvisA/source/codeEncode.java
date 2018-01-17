
public class codeEncode {
	public static void main(String[] args) {
		String[] input = {"1 qwertyuiopasdfghjklzxcvbnm", "hqomq gfsoft iqeaqzigf"};

		String[] line = input[0].split(" ");
		int encodeNum = Integer.parseInt(line[0]);
		char[] state = line[1].toCharArray();

		char[] ciphertext = input[1].toCharArray();

		for (int i = 0; i < encodeNum; i++) {
			for (int j = 0; j < ciphertext.length; j++) {
				if (ciphertext[j] == ' ') {
					continue;
				}
				int index = String.valueOf(state).indexOf(String.valueOf(ciphertext[j]));
				char start = 'a';
				char convertedText = Character.valueOf((char) (start + index));
				ciphertext[j] = convertedText;
			}
		}

		System.out.println(ciphertext);
	}
}
