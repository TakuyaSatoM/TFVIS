package tfvis;
import java.io.*;
import java.util.*;

public class InputStreamThread extends Thread {

	private BufferedReader br;

	private List<String> list = new ArrayList<String>();

	/** �R���X�g���N�^�[ */
	public InputStreamThread(InputStream is) {
		br = new BufferedReader(new InputStreamReader(is));
	}

	/** �R���X�g���N�^�[ */
	public InputStreamThread(InputStream is, String charset) {
		try {
			br = new BufferedReader(new InputStreamReader(is, charset));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			for (;;) {
				String line = br.readLine();
				if (line == null) 	break;
				list.add(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
		}
	}

	/** ������擾 */
	public List<String> getStringList() {
		return list;
	}
}