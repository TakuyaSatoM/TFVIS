package Dentaku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DentakuFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	JPanel contentPane = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JTextField result = new JTextField(""); //計算結果を表示するテキストフィールド
	double stackedValue = 0.0; //演算子ボタンを押す前にテキストフィールドに合った値
	boolean isStacked = false; //stackedValueに数値を入力したかどうか
	boolean afterCalc = false; //演算子ボタンを押した後かどうか
	String currentOp = ""; //押された演算子ボタンの名前

	//レイアウト用
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	//金種枚数計算用
	JLabel label[]=new JLabel[9];

	//フレームのビルド
	public DentakuFrame() {
		contentPane.setLayout(borderLayout1);
		//メインフレームの定義
		//this.getContentPane().setPreferredSize(new Dimension(680, 400));//ウィンドウの大きさ
		this.setTitle("電子式卓上計算機");//ウィンドウのタイトル
		this.setContentPane(contentPane);//contentPaneを表示

		result.setPreferredSize(new Dimension(200,100));
		result.setFont(new Font("ＭＳ　ゴシック",Font.PLAIN,50));
		result.setEditable(false);
		result.setBackground(Color.WHITE);
		contentPane.add(result, BorderLayout.NORTH); //テキストフィールドを配置

		JPanel keyPanel = new JPanel(gbl); //ボタンを配置するパネルを用意
		//keyPanel.setLayout(new GridLayout(4, 8)); //4行4列のGridLayoutにする

		contentPane.add(keyPanel, BorderLayout.CENTER);

		setMoneyLabel();

		int x=0;
		int y=0;
		//コンポーネントの配置
		addConponent(keyPanel, new NumberButton("７"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("８"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("９"), x++, y, 1, 1);
		addConponent(keyPanel, new ClearButton(), x++, y, 2, 1);
		addConponent(keyPanel, new MoneyButton(), ++x, y, 3, 1);

	    x=0;
		y++;
		addConponent(keyPanel, new NumberButton("４"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("５"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("６"), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("÷"), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("×"), x++, y, 1, 1);
		addConponent(keyPanel, label[2], x++, y, 1, 1);
		addConponent(keyPanel, label[1], x++, y, 1, 1);
		addConponent(keyPanel, label[0], x++, y, 1, 1);

	    x=0;
		y++;
		addConponent(keyPanel, new NumberButton("１"), x++, y, 1, 1);
	    addConponent(keyPanel, new NumberButton("２"), x++, y, 1, 1);
	    addConponent(keyPanel, new NumberButton("３"), x++, y, 1, 1);
	    addConponent(keyPanel, new CalcButton("＋"), x++, y, 1, 1);
	    addConponent(keyPanel, new CalcButton("－"), x++, y, 1, 1);
	    addConponent(keyPanel, label[5], x++, y, 1, 1);
	    addConponent(keyPanel, label[4], x++, y, 1, 1);
	    addConponent(keyPanel, label[3], x++, y, 1, 1);

	    x=0;
		y++;
		addConponent(keyPanel, new NumberButton("０"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("00"), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("."), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("＝"), x++, y, 2, 1);
		addConponent(keyPanel, label[8], ++x, y, 1, 1);
		addConponent(keyPanel, label[7], ++x, y, 1, 1);
		addConponent(keyPanel, label[6], ++x, y, 1, 1);

	    this.pack();//コンポーネントに合わせてサイズを自動調整する
		this.setVisible(true);
	}

	/*テキストフィールドに引数の文字列をつなげる*/
	public void appendResult(String c) {
		if (!afterCalc){ //演算子ボタンを押した直後でないなら
			result.setText(result.getText() + c); //押したボタンの名前をつなげる
		}else {
			result.setText(c); //押したボタンの文字列だけを設定する（いったんクリアしたかに見える）
			afterCalc = false;
		}
	}

	/*コンポーネント追加メソッド*/
	void addConponent(JComponent parent, JComponent c, int x, int y, int w, int h) {
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridx = x;
	    gbc.gridy = y;
	    gbc.gridwidth = w;
	    gbc.gridheight = h;
	    gbl.setConstraints(c, gbc);
	    parent.add(c);
	  }

	/*金種ラベルの定義*/
	public void setMoneyLabel(){

		ImageIcon icon[] = new ImageIcon[9];
		icon[0] = new ImageIcon("./image/s_money_10000.png");
		icon[1] = new ImageIcon("./image/s_money_5000.png");
		icon[2] = new ImageIcon("./image/s_money_1000.png");
		icon[3] = new ImageIcon("./image/s_money_500.png");
		icon[4] = new ImageIcon("./image/s_money_100.png");
		icon[5] = new ImageIcon("./image/s_money_50.png");
		icon[6] = new ImageIcon("./image/s_money_10.png");
		icon[7] = new ImageIcon("./image/s_money_5.png");
		icon[8] = new ImageIcon("./image/s_money_1.png");

		for(int i=0;i<9;i++){
		label[i]=new JLabel("×0",icon[i],JLabel.CENTER);
		label[i].setOpaque(true);
		label[i].setBackground(Color.WHITE);
		//label[i].setBorder(new LineBorder(Color.GRAY, 1, false));
		label[i].setFont(new Font("ＭＳ　ゴシック",Font.PLAIN,40));
		}
	}

	/*数字を入力するボタンの定義*/
	public class NumberButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		public NumberButton(String keyTop) {
			super(keyTop); //JButtonクラスのコンストラクタを呼び出す
			this.setFont(new Font("ＭＳ　ゴシック",Font.PLAIN,40));
			this.addActionListener(this); //このボタンにアクションイベントのリスナを設定
		}

		public void actionPerformed(ActionEvent evt) {
			String[] s={"0","1","2","3","4","5","6","7","8","9"};
			String[] S={"０","１","２","３","４","５","６","７","８","９"};
			String keyNumber="";
			if(this.getText()=="00"){
				keyNumber="00";
			}
			else{
			for(int z=0;z<10;z++){
				if(this.getText()==S[z]){
					keyNumber=s[z];
					break;
				}
			}
			}
			appendResult(keyNumber); //ボタンの名前をテキストフィールドにつなげる
		}
	}

	/*演算子ボタンを定義*/
	public class CalcButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		public CalcButton(String op) {
			super(op);
			this.setFont(new Font("ＭＳ　ゴシック",Font.PLAIN,40));
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if (isStacked) { //以前に演算子ボタンが押されたのなら計算結果を出す。
				double resultValue = (Double.valueOf(result.getText()))
						.doubleValue();
				if (currentOp.equals("＋")) //演算子に応じて計算する
					stackedValue += resultValue;
				else if (currentOp.equals("－"))
					stackedValue -= resultValue;
				else if (currentOp.equals("×"))
					stackedValue *= resultValue;
				else if (currentOp.equals("÷"))
					stackedValue /= resultValue;
				result.setText(String.valueOf(stackedValue)); //計算結果をテキストフィールドに設定
			}
			currentOp = this.getText(); //ボタン名から押されたボタンの演算子を取り出す
			stackedValue = (Double.valueOf(result.getText())).doubleValue();
			afterCalc = true;
			if (currentOp.equals("＝"))
				isStacked = false;
			else
				isStacked = true;
		}
	}

	/*クリアボタンの定義*/
	public class ClearButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public ClearButton() {
			super("C");
			this.setFont(new Font("ＭＳ　ゴシック",Font.PLAIN,40));
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent evt) {
			result.setFont(new Font("ＭＳ　ゴシック",Font.PLAIN,50));
			stackedValue = 0.0;
			result.setText("");
			for(int i=0;i<9;i++){
				label[i].setText("×0");
				}
			afterCalc = false;
			isStacked = false;
			DentakuFrame.this.pack();
		}
	}

	/*無効ボタンの定義*/
	public class NoneButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public NoneButton() {
			super("");
			this.addActionListener(this);
			this.setEnabled(false);
			this.setBorderPainted(false);
		}

		public void actionPerformed(ActionEvent evt) {
		}
	}

	/*金種枚数計算ボタンの定義*/
	public class MoneyButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public MoneyButton() {
			super("金種枚数計算");
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			this.setFont(new Font("ＭＳ　ゴシック",Font.PLAIN,25));
			gbc.gridwidth = 3;
			gbl.setConstraints(this, gbc);
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent evt) {
			int[] MoneyType={10000,5000,1000,500,100,50,10,5,1};
			int MoneyCount[]=new int[MoneyType.length];
			for(int i=0;i<MoneyType.length;i++){
				while(stackedValue>=MoneyType[i]){
				stackedValue-=MoneyType[i];
				MoneyCount[i]++;
				}
			}
			for(int x=0;x<MoneyType.length;x++){
				label[x].setText("×"+MoneyCount[x]);
			}

			DentakuFrame.this.pack();
			afterCalc = false;
			isStacked = false;
		}
	}
}