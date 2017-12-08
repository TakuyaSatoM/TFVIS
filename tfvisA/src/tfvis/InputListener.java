package tfvis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

//入力監視用スレッド
public class InputListener extends Thread {

	private BufferedWriter bw;
	private BufferedReader br;
	private List<String> list = new ArrayList<String>();

	public InputListener(OutputStream os) {
		bw = new BufferedWriter(new OutputStreamWriter(os));
		br = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void run() {
		String line;
		System.out.println("入力");
		try {
			while (true) {
				line = br.readLine();
				bw.write(line);
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				br.close();
				bw.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

	/** 文字列取得 */
	public List<String> getStringList() {
		return list;
	}
}