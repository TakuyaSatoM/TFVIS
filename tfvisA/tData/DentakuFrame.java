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
public class DentakuFrame extends JFrame{
private static final long serialVersionUID=1L;
JPanel contentPane=new JPanel ();
BorderLayout borderLayout1=new BorderLayout ();
JTextField result=new JTextField ("");
double stackedValue=0.0;
boolean isStacked=false;
boolean afterCalc=false;
String currentOp="";
GridBagLayout gbl=new GridBagLayout ();
GridBagConstraints gbc=new GridBagConstraints ();
JLabel label[] =new JLabel[9];
  static final int TP_CLASSID = 6;
  static int TP_INSTANCEID = -1;

class NumberButton extends JButton implements ActionListener{
private static final long serialVersionUID=1L;
  public NumberButton(String keyTop){
    super(keyTop);
   final int TP_METHODID = 1;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "keyTop",keyTop,"",false);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "keyTop"  );
    this.setFont(new Font ("ÇlÇrÅ@ÉSÉVÉbÉN",Font.PLAIN,40));
    this.addActionListener(this);
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
    }

    public void actionPerformed(ActionEvent evt){
     final int TP_METHODID = 2;
     int TP_METHODEXE=TProbe.GetExe();	
     TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     boolean isLoop=false;
      String[] s={"0","1","2","3","4","5","6","7","8","9"};
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "s",s,false);
      String[] S={"ÇO","ÇP","ÇQ","ÇR","ÇS","ÇT","ÇU","ÇV","ÇW","ÇX"};
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "S",S,false);
      String keyNumber="";
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "keyNumber",keyNumber,"",false);
      if(this.getText()=="00"){
       TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
        keyNumber="00";
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "keyNumber",keyNumber,"",false);
        }
else{
           TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8);
           boolean tvisLoopInit0=false;
           TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8,"z");
          for(int z=0;z<10;z++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "z",z,"z",false)){
           isLoop=true;
           if(tvisLoopInit0==false){tvisLoopInit0=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8,"z",z,"",false);}
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "S["+z+"],z"  );
            if(this.getText()==S[z]){
             TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9  );
              keyNumber=s[z];
               TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "keyNumber",keyNumber,"s["+z+"],z",false);
              break;
              }
               TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13,8);
               isLoop=false;
              }
               TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8,8);
               TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13);
              }
               TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15);
              appendResult(keyNumber);
               TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16  );
              }

}
class CalcButton extends JButton implements ActionListener{
private static final long serialVersionUID=1L;
  public CalcButton(String op){
    super(op);
   final int TP_METHODID = 3;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
   TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "op",op,"",false);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "op"  );
    this.setFont(new Font ("ÇlÇrÅ@ÉSÉVÉbÉN",Font.PLAIN,40));
    this.addActionListener(this);
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
    }

    public void actionPerformed(ActionEvent e){
     final int TP_METHODID = 4;
     int TP_METHODEXE=TProbe.GetExe();	
     TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     boolean isLoop=false;
      if(isStacked){
       TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
        double resultValue=(Double.valueOf(result.getText())).doubleValue();
         TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "resultValue",resultValue,"",false);
        if(currentOp.equals("Å{")){
         TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3  );
          stackedValue+=resultValue;
           TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "resultValue"  );
          }
          else if(currentOp.equals("Å|")){
           TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6  );
            stackedValue-=resultValue;
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "resultValue"  );
            }
            else if(currentOp.equals("Å~")){
             TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9  );
              stackedValue*=resultValue;
               TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "resultValue"  );
              }
              else if(currentOp.equals("ÅÄ")){
               TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12  );
                stackedValue/=resultValue;
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13, "resultValue"  );
                }
                result.setText(String.valueOf(stackedValue));
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15, "."  );
                }
                currentOp=this.getText();
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,17, "."  );
                stackedValue=(Double.valueOf(result.getText())).doubleValue();
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,18, "."  );
                afterCalc=true;
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,20, "."  );
                if(currentOp.equals("ÅÅ")){
                 TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,20  );
                  isStacked=false;
                  }
else{
                    isStacked=true;
                    }
                     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,26  );
                    }

}
class ClearButton extends JButton implements ActionListener{
private static final long serialVersionUID=1L;
  public ClearButton(){
    super("C");
   final int TP_METHODID = 5;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    this.setFont(new Font ("ÇlÇrÅ@ÉSÉVÉbÉN",Font.PLAIN,40));
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "."  );
    this.addActionListener(this);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "."  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4  );
    }

    public void actionPerformed(ActionEvent evt){
     final int TP_METHODID = 6;
     int TP_METHODEXE=TProbe.GetExe();	
     TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     boolean isLoop=false;
      result.setFont(new Font ("ÇlÇrÅ@ÉSÉVÉbÉN",Font.PLAIN,50));
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "."  );
      stackedValue=0.0;
      result.setText("");
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "."  );
       TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
       boolean tvisLoopInit1=false;
       TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4,"i");
      for(int i=0;i<9;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "i",i,"i",false)){
       isLoop=true;
       if(tvisLoopInit1==false){tvisLoopInit1=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4,"i",i,"",false);}
        label[i].setText("Å~0");
         TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "i,."  );
         TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6,4);
         isLoop=false;
        }
         TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4,4);
         TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6);
        afterCalc=false;
        isStacked=false;
         TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9);
        DentakuFrame.this.pack();
         TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10  );
        }

}
class NoneButton extends JButton implements ActionListener{
private static final long serialVersionUID=1L;
  public NoneButton(){
    super("");
   final int TP_METHODID = 7;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    this.addActionListener(this);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "."  );
    this.setEnabled(false);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "."  );
    this.setBorderPainted(false);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "."  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5  );
    }

    public void actionPerformed(ActionEvent evt){
     final int TP_METHODID = 8;
     int TP_METHODEXE=TProbe.GetExe();	
     TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     boolean isLoop=false;
       TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
      }

}
class MoneyButton extends JButton implements ActionListener{
private static final long serialVersionUID=1L;
  public MoneyButton(){
    super("ã‡éÌñáêîåvéZ");
   final int TP_METHODID = 9;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    GridBagLayout gbl=new GridBagLayout ();
    GridBagConstraints gbc=new GridBagConstraints ();
    this.setFont(new Font ("ÇlÇrÅ@ÉSÉVÉbÉN",Font.PLAIN,25));
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "."  );
    gbc.gridwidth=3;
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "."  );
    gbl.setConstraints(this,gbc);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "."  );
    this.addActionListener(this);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "."  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
    }

    public void actionPerformed(ActionEvent evt){
     final int TP_METHODID = 10;
     int TP_METHODEXE=TProbe.GetExe();	
     TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     boolean isLoop=false;
      int[] MoneyType={10000,5000,1000,500,100,50,10,5,1};
       TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "MoneyType",MoneyType,false);
      int MoneyCount[] =new int[MoneyType.length];
       TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "MoneyType,."  );
       TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3);
       boolean tvisLoopInit2=false;
       TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,"i");
      for(int i=0;i<MoneyType.length;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "i",i,"i",false)){
       isLoop=true;
       if(tvisLoopInit2==false){tvisLoopInit2=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,"i",i,"",false);}
         TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4);
        while(stackedValue>=MoneyType[i]){
         isLoop=true;
          stackedValue-=MoneyType[i];
           TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "MoneyType["+i+"],i"  );
          MoneyCount[i]++;
           TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "MoneyCount["+i+"],i"  );
           TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7,4);
           isLoop=false;
          }
           TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4,4);
           TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7);
           TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8,3);
           isLoop=false;
          }
           TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3,3);
           TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8);
           TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9);
           boolean tvisLoopInit3=false;
           TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,"x");
          for(int x=0;x<MoneyType.length;x++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "x",x,"x",false)){
           isLoop=true;
           if(tvisLoopInit3==false){tvisLoopInit3=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,"x",x,"",false);}
            label[x].setText("Å~"+MoneyCount[x]);
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,10, "x,.,MoneyCount["+x+"]"  );
             TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11,9);
             isLoop=false;
            }
             TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9,9);
             TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11);
             TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12);
            DentakuFrame.this.pack();
            afterCalc=false;
            isStacked=false;
             TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15  );
            }

}
  public DentakuFrame(){
   final int TP_METHODID = 11;
   int TP_METHODEXE=TProbe.GetExe();	
   TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
   boolean isLoop=false;
    contentPane.setLayout(borderLayout1);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "."  );
    this.setTitle("ìdéqéÆëÏè„åvéZã@");
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, "."  );
    this.setContentPane(contentPane);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, "."  );
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, "."  );
    result.setPreferredSize(new Dimension (200,100));
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, "."  );
    result.setFont(new Font ("ÇlÇrÅ@ÉSÉVÉbÉN",Font.PLAIN,50));
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, "."  );
    result.setEditable(false);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, "."  );
    result.setBackground(Color.WHITE);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8, "."  );
    contentPane.add(result,BorderLayout.NORTH);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,9, "."  );
    JPanel keyPanel=new JPanel (gbl);
    contentPane.add(keyPanel,BorderLayout.CENTER);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11, "."  );
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12);
    setMoneyLabel();
    int x=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13, "x",x,"",false);
    int y=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,14, "y",y,"",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15);
    addConponent(keyPanel,new NumberButton ("ÇV"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16);
    addConponent(keyPanel,new NumberButton ("ÇW"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,17);
    addConponent(keyPanel,new NumberButton ("ÇX"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,17, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,18);
    addConponent(keyPanel,new ClearButton (),x++,y,2,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,18, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,19);
    addConponent(keyPanel,new MoneyButton (),++x,y,3,1);
    x=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,20, "x",x,"",false);
    y++;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,21, "y",y,"y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,22);
    addConponent(keyPanel,new NumberButton ("ÇS"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,22, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,23);
    addConponent(keyPanel,new NumberButton ("ÇT"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,23, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,24);
    addConponent(keyPanel,new NumberButton ("ÇU"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,24, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,25);
    addConponent(keyPanel,new CalcButton ("ÅÄ"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,25, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,26);
    addConponent(keyPanel,new CalcButton ("Å~"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,26, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,27);
    addConponent(keyPanel,label[2],x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,27, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,28);
    addConponent(keyPanel,label[1],x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,28, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,29);
    addConponent(keyPanel,label[0],x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,29, "x",x,"x,y",false);
    x=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,30, "x",x,"",false);
    y++;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,31, "y",y,"y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,32);
    addConponent(keyPanel,new NumberButton ("ÇP"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,32, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,33);
    addConponent(keyPanel,new NumberButton ("ÇQ"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,33, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,34);
    addConponent(keyPanel,new NumberButton ("ÇR"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,34, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,35);
    addConponent(keyPanel,new CalcButton ("Å{"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,35, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,36);
    addConponent(keyPanel,new CalcButton ("Å|"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,36, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,37);
    addConponent(keyPanel,label[5],x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,37, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,38);
    addConponent(keyPanel,label[4],x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,38, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,39);
    addConponent(keyPanel,label[3],x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,39, "x",x,"x,y",false);
    x=0;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,40, "x",x,"",false);
    y++;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,41, "y",y,"y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,42);
    addConponent(keyPanel,new NumberButton ("ÇO"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,42, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,43);
    addConponent(keyPanel,new NumberButton ("00"),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,43, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,44);
    addConponent(keyPanel,new CalcButton ("."),x++,y,1,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,44, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,45);
    addConponent(keyPanel,new CalcButton ("ÅÅ"),x++,y,2,1);
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,45, "x",x,"x,y",false);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,46);
    addConponent(keyPanel,label[8],++x,y,1,1);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,47);
    addConponent(keyPanel,label[7],++x,y,1,1);
     TProbe.Input_MethodCall(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,48);
    addConponent(keyPanel,label[6],++x,y,1,1);
    this.pack();
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,49, "."  );
    this.setVisible(true);
     TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,50, "."  );
     TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,51  );
    }

    public void appendResult(String c){
     final int TP_METHODID = 12;
     int TP_METHODEXE=TProbe.GetExe();	
     TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
     boolean isLoop=false;
     TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "c",c,"",false);
      if(!afterCalc){
       TProbe.Input_Route(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1  );
        result.setText(result.getText()+c);
         TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ".,c"  );
        }
else{
          result.setText(c);
           TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, ".,c"  );
          afterCalc=false;
          }
           TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
          }

          void addConponent(JComponent parent,JComponent c,int x,int y,int w,int h){
           final int TP_METHODID = 13;
           int TP_METHODEXE=TProbe.GetExe();	
           TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
           boolean isLoop=false;
           TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "x",x,"",false);
           TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "y",y,"",false);
           TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "w",w,"",false);
           TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0, "h",h,"",false);
            gbc.fill=GridBagConstraints.BOTH;
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,1, "."  );
            gbc.gridx=x;
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,2, ".,x"  );
            gbc.gridy=y;
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,3, ".,y"  );
            gbc.gridwidth=w;
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,4, ".,w"  );
            gbc.gridheight=h;
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,5, ".,h"  );
            gbl.setConstraints(c,gbc);
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,6, ".,c"  );
            parent.add(c);
             TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,7, ".,c"  );
             TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,8  );
            }

            public void setMoneyLabel(){
             final int TP_METHODID = 14;
             int TP_METHODEXE=TProbe.GetExe();	
             TProbe.Input_StartMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,0);
             boolean isLoop=false;
              ImageIcon icon[] =new ImageIcon[9];
              icon[0]=new ImageIcon ("./image/s_money_10000.png");
              icon[1]=new ImageIcon ("./image/s_money_5000.png");
              icon[2]=new ImageIcon ("./image/s_money_1000.png");
              icon[3]=new ImageIcon ("./image/s_money_500.png");
              icon[4]=new ImageIcon ("./image/s_money_100.png");
              icon[5]=new ImageIcon ("./image/s_money_50.png");
              icon[6]=new ImageIcon ("./image/s_money_10.png");
              icon[7]=new ImageIcon ("./image/s_money_5.png");
              icon[8]=new ImageIcon ("./image/s_money_1.png");
               TProbe.Input_Loop(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11);
               boolean tvisLoopInit4=false;
               TProbe.Input_LifeLimit(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11,"i");
              for(int i=0;i<9;i++,TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11, "i",i,"i",false)){
               isLoop=true;
               if(tvisLoopInit4==false){tvisLoopInit4=true;TProbe.Input_Update(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11,"i",i,"",false);}
                label[i]=new JLabel ("Å~0",icon[i],JLabel.CENTER);
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,12, "i,."  );
                label[i].setOpaque(true);
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,13, "i,."  );
                label[i].setBackground(Color.WHITE);
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,14, "i,."  );
                label[i].setFont(new Font ("ÇlÇrÅ@ÉSÉVÉbÉN",Font.PLAIN,40));
                 TProbe.Input_Read(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,15, "i,."  );
                 TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16,11);
                 isLoop=false;
                }
                 TProbe.Input_LoopNext(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,11,11);
                 TProbe.Input_LoopEnd(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,16);
                 TProbe.Input_EndMethod(TP_INSTANCEID,TP_METHODID,TP_METHODEXE,17  );
                }

}
