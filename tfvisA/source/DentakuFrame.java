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
	JTextField result = new JTextField(""); //�v�Z���ʂ�\������e�L�X�g�t�B�[���h
	double stackedValue = 0.0; //���Z�q�{�^���������O�Ƀe�L�X�g�t�B�[���h�ɍ������l
	boolean isStacked = false; //stackedValue�ɐ��l����͂������ǂ���
	boolean afterCalc = false; //���Z�q�{�^�����������ォ�ǂ���
	String currentOp = ""; //�����ꂽ���Z�q�{�^���̖��O

	//���C�A�E�g�p
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();

	//���햇���v�Z�p
	JLabel label[]=new JLabel[9];

	//�t���[���̃r���h
	public DentakuFrame() {
		contentPane.setLayout(borderLayout1);
		//���C���t���[���̒�`
		//this.getContentPane().setPreferredSize(new Dimension(680, 400));//�E�B���h�E�̑傫��
		this.setTitle("�d�q�����v�Z�@");//�E�B���h�E�̃^�C�g��
		this.setContentPane(contentPane);//contentPane��\��

		result.setPreferredSize(new Dimension(200,100));
		result.setFont(new Font("�l�r�@�S�V�b�N",Font.PLAIN,50));
		result.setEditable(false);
		result.setBackground(Color.WHITE);
		contentPane.add(result, BorderLayout.NORTH); //�e�L�X�g�t�B�[���h��z�u

		JPanel keyPanel = new JPanel(gbl); //�{�^����z�u����p�l����p��
		//keyPanel.setLayout(new GridLayout(4, 8)); //4�s4���GridLayout�ɂ���

		contentPane.add(keyPanel, BorderLayout.CENTER);

		setMoneyLabel();

		int x=0;
		int y=0;
		//�R���|�[�l���g�̔z�u
		addConponent(keyPanel, new NumberButton("�V"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("�W"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("�X"), x++, y, 1, 1);
		addConponent(keyPanel, new ClearButton(), x++, y, 2, 1);
		addConponent(keyPanel, new MoneyButton(), ++x, y, 3, 1);

	    x=0;
		y++;
		addConponent(keyPanel, new NumberButton("�S"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("�T"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("�U"), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("��"), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("�~"), x++, y, 1, 1);
		addConponent(keyPanel, label[2], x++, y, 1, 1);
		addConponent(keyPanel, label[1], x++, y, 1, 1);
		addConponent(keyPanel, label[0], x++, y, 1, 1);

	    x=0;
		y++;
		addConponent(keyPanel, new NumberButton("�P"), x++, y, 1, 1);
	    addConponent(keyPanel, new NumberButton("�Q"), x++, y, 1, 1);
	    addConponent(keyPanel, new NumberButton("�R"), x++, y, 1, 1);
	    addConponent(keyPanel, new CalcButton("�{"), x++, y, 1, 1);
	    addConponent(keyPanel, new CalcButton("�|"), x++, y, 1, 1);
	    addConponent(keyPanel, label[5], x++, y, 1, 1);
	    addConponent(keyPanel, label[4], x++, y, 1, 1);
	    addConponent(keyPanel, label[3], x++, y, 1, 1);

	    x=0;
		y++;
		addConponent(keyPanel, new NumberButton("�O"), x++, y, 1, 1);
		addConponent(keyPanel, new NumberButton("00"), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("."), x++, y, 1, 1);
		addConponent(keyPanel, new CalcButton("��"), x++, y, 2, 1);
		addConponent(keyPanel, label[8], ++x, y, 1, 1);
		addConponent(keyPanel, label[7], ++x, y, 1, 1);
		addConponent(keyPanel, label[6], ++x, y, 1, 1);

	    this.pack();//�R���|�[�l���g�ɍ��킹�ăT�C�Y��������������
		this.setVisible(true);
	}

	/*�e�L�X�g�t�B�[���h�Ɉ����̕�������Ȃ���*/
	public void appendResult(String c) {
		if (!afterCalc){ //���Z�q�{�^��������������łȂ��Ȃ�
			result.setText(result.getText() + c); //�������{�^���̖��O���Ȃ���
		}else {
			result.setText(c); //�������{�^���̕����񂾂���ݒ肷��i��������N���A�������Ɍ�����j
			afterCalc = false;
		}
	}

	/*�R���|�[�l���g�ǉ����\�b�h*/
	void addConponent(JComponent parent, JComponent c, int x, int y, int w, int h) {
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.gridx = x;
	    gbc.gridy = y;
	    gbc.gridwidth = w;
	    gbc.gridheight = h;
	    gbl.setConstraints(c, gbc);
	    parent.add(c);
	  }

	/*���탉�x���̒�`*/
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
		label[i]=new JLabel("�~0",icon[i],JLabel.CENTER);
		label[i].setOpaque(true);
		label[i].setBackground(Color.WHITE);
		//label[i].setBorder(new LineBorder(Color.GRAY, 1, false));
		label[i].setFont(new Font("�l�r�@�S�V�b�N",Font.PLAIN,40));
		}
	}

	/*��������͂���{�^���̒�`*/
	public class NumberButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		public NumberButton(String keyTop) {
			super(keyTop); //JButton�N���X�̃R���X�g���N�^���Ăяo��
			this.setFont(new Font("�l�r�@�S�V�b�N",Font.PLAIN,40));
			this.addActionListener(this); //���̃{�^���ɃA�N�V�����C�x���g�̃��X�i��ݒ�
		}

		public void actionPerformed(ActionEvent evt) {
			String[] s={"0","1","2","3","4","5","6","7","8","9"};
			String[] S={"�O","�P","�Q","�R","�S","�T","�U","�V","�W","�X"};
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
			appendResult(keyNumber); //�{�^���̖��O���e�L�X�g�t�B�[���h�ɂȂ���
		}
	}

	/*���Z�q�{�^�����`*/
	public class CalcButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		public CalcButton(String op) {
			super(op);
			this.setFont(new Font("�l�r�@�S�V�b�N",Font.PLAIN,40));
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if (isStacked) { //�ȑO�ɉ��Z�q�{�^���������ꂽ�̂Ȃ�v�Z���ʂ��o���B
				double resultValue = (Double.valueOf(result.getText()))
						.doubleValue();
				if (currentOp.equals("�{")) //���Z�q�ɉ����Čv�Z����
					stackedValue += resultValue;
				else if (currentOp.equals("�|"))
					stackedValue -= resultValue;
				else if (currentOp.equals("�~"))
					stackedValue *= resultValue;
				else if (currentOp.equals("��"))
					stackedValue /= resultValue;
				result.setText(String.valueOf(stackedValue)); //�v�Z���ʂ��e�L�X�g�t�B�[���h�ɐݒ�
			}
			currentOp = this.getText(); //�{�^�������牟���ꂽ�{�^���̉��Z�q�����o��
			stackedValue = (Double.valueOf(result.getText())).doubleValue();
			afterCalc = true;
			if (currentOp.equals("��"))
				isStacked = false;
			else
				isStacked = true;
		}
	}

	/*�N���A�{�^���̒�`*/
	public class ClearButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public ClearButton() {
			super("C");
			this.setFont(new Font("�l�r�@�S�V�b�N",Font.PLAIN,40));
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent evt) {
			result.setFont(new Font("�l�r�@�S�V�b�N",Font.PLAIN,50));
			stackedValue = 0.0;
			result.setText("");
			for(int i=0;i<9;i++){
				label[i].setText("�~0");
				}
			afterCalc = false;
			isStacked = false;
			DentakuFrame.this.pack();
		}
	}

	/*�����{�^���̒�`*/
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

	/*���햇���v�Z�{�^���̒�`*/
	public class MoneyButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public MoneyButton() {
			super("���햇���v�Z");
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			this.setFont(new Font("�l�r�@�S�V�b�N",Font.PLAIN,25));
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
				label[x].setText("�~"+MoneyCount[x]);
			}

			DentakuFrame.this.pack();
			afterCalc = false;
			isStacked = false;
		}
	}
}