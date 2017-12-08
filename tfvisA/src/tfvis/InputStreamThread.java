package tfvis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
				if (line == null)
					break;
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