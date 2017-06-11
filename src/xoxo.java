import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;

public class xoxo extends javax.swing.JFrame implements java.awt.event.ActionListener {

	JButton _newGameButton = new JButton("NEW GAME"), _newPlayerButton = new JButton ("New Player");
	JButton [][]b = new JButton[3][3];
	JPanel p [] = new JPanel[4];
	JLabel l = new JLabel("CHOOSE PLAYER"), l1=new JLabel();
	ArrayList<String> al = new ArrayList<>();
	
	JComboBox<String> _chooseMenue = new JComboBox<>();
	int compscore;
	int myscore;
	int counter=0;
	String win;
	String igrok=null;
	int bb;
	int c;
	String _ScoreSavePath;
	
	public static void main (String args []){
		new xoxo().BuildGUI();
	}

	public void BuildGUI (){
					
		for (int i=0;i<b.length;i++){
			p[i]=new JPanel();
		for (int j=0;j<b[i].length;j++){
			b[i][j] = new JButton();
			b[i][j].addActionListener(this);
			b[i][j].setBackground(Color.ORANGE);
			b[i][j].setFont(new Font ("",Font.BOLD,50));
			p[0].add(b[i][j]);
			b[i][j].setEnabled(false);
		}}
		p[3]=new JPanel();
		
		_chooseMenue.addItemListener(
				new ItemListener(){ public void itemStateChanged(ItemEvent ie){
					if (ie.getStateChange()==ItemEvent.SELECTED){
						igrok =_chooseMenue.getSelectedItem().toString();
						bb = _chooseMenue.getSelectedIndex();
						c = bb+2*(bb-1);
						if(!igrok.equals("")){
						_newGameButton.setEnabled(true);
						l.setText("Push new game button!!!");						
						myscore=Integer.parseInt(al.get(c+1).toString());
						compscore=Integer.parseInt(al.get(c+2).toString()); }
						SetLabel();
				}}} );
				
		l.setHorizontalAlignment(JLabel.CENTER);
		_newGameButton.setEnabled(false);
		_newGameButton.setFont(new Font("",Font.BOLD,20));
		l1.setFont(new Font("",Font.BOLD,15));
		l1.setHorizontalAlignment( JLabel.CENTER);
		l.setFont(new Font("",Font.BOLD,20));
		p[2].setBackground(Color.CYAN);
		_newPlayerButton.addActionListener(this);
		_newGameButton.addActionListener(this);
		p[1].setLayout(new BorderLayout());
		p[2].setLayout(new BorderLayout());
		p[1].add("Center", _chooseMenue);
		p[1].add("South", l1);
		p[1].add("East",_newPlayerButton);
		p[2].add("North", l);
		p[2].add("Center", _newGameButton);
		p[0].setLayout(new GridLayout(3,3));
		p[3].setLayout(new BorderLayout());
		p[3].add("South",p[2]);
		p[3].add("Center",p[0]);
		p[3].add("North",p[1]);
		this.getContentPane().add(p[3]);
		p[1].setBackground(Color.CYAN);
		this.setBounds(500,250,270,330);
		this.setVisible(true);
		this.setTitle("Artem's X.O");
		this.setIconImage(new ImageIcon(new xoxo().getClass().getResource("/xoxo.jpg")).getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		ScoreFileCreator();
		uploader();
		}
	
	public void SetLabel(){
			if(igrok.equals("")|igrok.equals(null)){
			l1.setText(" !!   CHOOSE A PLAYER   !!");
			l.setText("CHOOSE A PLAYER");
			_newGameButton.setEnabled(false);} else{
		l1.setText("  Score |    "+igrok+":  "+myscore+" - "+compscore+"  :Computer");
		}}
	
	public void actionPerformed(ActionEvent evt){
		
		JButton nzh = (JButton) evt.getSource();
		boolean check = true;
		
		if (nzh==_newPlayerButton){
			String igr= JOptionPane.showInputDialog(null,"Player name","New Player",JOptionPane.DEFAULT_OPTION);
			for(int k=1;k<al.size();k+=3){
				if (igr.equals(al.get(k))){
					check=false;
				JOptionPane.showConfirmDialog(null,"Player with this name already exist, please choose another one","WTF...?",JOptionPane.PLAIN_MESSAGE);
				}}
					
			if(!igr.equals(null)&!igr.equals("")&check==true){
			igrok=igr; 
			al.add(igrok);
			al.add("0");
			al.add("0");
			saver(0);
			SetLabel();
			
			_newGameButton.setEnabled(true); 
			_chooseMenue.addItem(igrok);
			_chooseMenue.setSelectedItem(igrok);
			}} 
						
		if (nzh == _newGameButton){
			for(int i=0;i<b.length;i++){
				for(int j=0;j<b[i].length;j++){
					b[i][j].setText("");
					b[i][j].setEnabled(true);
					b[i][j].setBackground(Color.ORANGE);
				}}
			_newPlayerButton.setEnabled(false);
			_chooseMenue.setEnabled(false);
			_newGameButton.setEnabled(false);
			l.setText("Let's GO!");
			counter = 0;
		}
		
		for(int i=0;i<b.length;i++){
			for(int j=0;j<b[i].length;j++){
		if(nzh == b[i][j] && b[i][j].getText().equals("")){
				b[i][j].setText("X");
					counter++;
					
					if (counter>2){
						win=LFW();
						if(win == "X"){
							l.setText("YOU KILLED IT!");
							myscore++;
							endGame();
							break;
						}}
					if (counter==5){
						l.setText("DRAW!");
						endGame();
						break;
					}
					compMove();
		}}}}
	
	public void saver(int x){
	if(x==1){
		al.set(c+1,Integer.toString(myscore));
		al.set(c+2,Integer.toString(compscore));}
		
		FileWriter fw=null;
		BufferedWriter bw = null;
			try{
				fw=new FileWriter(_ScoreSavePath+"/XO_Score.txt");
				bw=new BufferedWriter(fw);
				
					for(int k=1;k<al.size();k++){
				bw.write(al.get(k)+String.format("%n")); }
			}catch(IOException ex2){
				JOptionPane.showConfirmDialog(null,ex2.getMessage().toString(),"Score writing error",JOptionPane.PLAIN_MESSAGE);
			}finally{
				try{
					bw.flush();
					bw.close();
					fw.close();
				}catch (IOException ex3){
					JOptionPane.showConfirmDialog(null,ex3.getMessage().toString(),"Error",JOptionPane.PLAIN_MESSAGE);
				}}}
	public void ScoreFileCreator(){
		_ScoreSavePath = System.getProperty("user.home")+"/Desktop";
		if(!new File(_ScoreSavePath+"/XO_Score.txt").exists()){
			try{
				new File(_ScoreSavePath+"/XO_Score.txt").createNewFile();
			}
			catch(IOException ex){
				JOptionPane.showConfirmDialog(null, ex.getMessage().toString(),"File creating error",JOptionPane.PLAIN_MESSAGE);
			}
			
		}
	}
	
	public void uploader(){
		FileReader fr = null;
		LineNumberReader lnr = null;
		al.add("");						
		_chooseMenue.addItem(al.get(0));
		try{
			fr = new FileReader(_ScoreSavePath+"/XO_Score.txt");
			lnr=new LineNumberReader(fr);
			while(true){
			String lin = lnr.readLine();
			if(lin==null)break;
			else
			al.add(lin);
			}
		}catch(IOException ex){
			JOptionPane.showConfirmDialog(null,ex.getMessage().toString(),"Check File Directory",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}finally{
			try{
				lnr.close();
				fr.close();
			}catch(IOException ex1){
				JOptionPane.showConfirmDialog(null,ex1.getMessage().toString(),"Error",JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}}
		
		for(int k=1;k<al.size();k=k+3){
			_chooseMenue.addItem(al.get(k));
			}}
		
	void compMove() {
		
		int abc =findBest(2);
				
		if (abc==1){
			abc=findBest(-2);
		} 
		if(abc==1 && b[1][1].getText().equals("")){
			b[1][1].setText("O");
			abc=0;
		}
		if(abc==1){
			getRandom();
		}
		if (counter>2){
			win=LFW();
			if(win.equals("O")){
				l.setText("LOL ), YOU LOST!" );
				compscore++;
				endGame();
				}}}
		
	int findBest( int r){
		
		int s [][]=new int[3][3];
		int k = 1;
		for (int i=0;i<b.length;i++){
			for(int j=0;j<b[i].length;j++){
				if (b[i][j].getText().equals("O")){
					s[i][j]=1;
				}
				if (b[i][j].getText().equals("X")){
					s[i][j]=-1;
				}
				if (b[i][j].getText().equals("")){
					s[i][j]=0;
				}}}
		
		for(int i=0;i<s.length;i++){
			if (s[i][0]+s[i][1]+s[i][2]==r){
				for(int j=0;j<s[i].length;j++){
					if (s[i][j]==0){
						b[i][j].setText("O");
						k=0; 
					}}break;}	
		if(s[0][i]+s[1][i]+s[2][i]==r){
				for (int j=0;j<s[i].length;j++){    
					if (s[j][i]==0){
						b[j][i].setText("O");
						k=0; 
					}}break;}	
		if(s[1][1]+s[0][0]+s[2][2]==r){
					if (s[i][i]==0){
						b[i][i].setText("O");
						k=0; break;
					}}
		if(s[1][1]+s[0][2]+s[2][0]==r){
					if(s[i][2-i]==0){
						b[i][2-i].setText("O");
						k=0; break;
			}}}
		return k;
		}
	
	void getRandom (){
		
		int c=0;
		while (c==0){
		int	f=(int)(Math.random()*3);
		int	d=(int)(Math.random()*3);
			if (b[f][d].getText().equals("")){
				b[f][d].setText("O");
				c=1;
			}}}
	
	String LFW(){
	
		String bt [][] = new String [3][3];
				
		for(int i=0;i<bt.length;i++){
		for (int j=0;j<bt[i].length;j++){
			bt[i][j]=b[i][j].getText();
		}}
						
		String w="";
		
		if(!bt[1][1].equals("")){
			if (bt[0][0]==bt[1][1] && bt[1][1]==bt[2][2]){ 
				w = bt[1][1];
				highlight(b[0][0],b[1][1],b[2][2]);
			} 
			if (bt[0][2]==bt[1][1]&&bt[1][1]==bt[2][0]){
				w=bt[1][1];
				highlight(b[0][2],b[1][1],b[2][0]);
			}}
				
		for (int i=0;i<bt.length;i++){
			
			if(bt[i][0]==bt[i][1]&&bt[i][1]==bt[i][2]&&!bt[i][0].equals("")){
				w=bt[i][0];
				highlight(b[i][0],b[i][1],b[i][2]);
			
				}else  
					
			if(bt[0][i].equals(bt[1][i])&&bt[1][i].equals(bt[2][i])&&!bt[0][i].equals("")){
				w=bt[0][i];
				highlight(b[0][i],b[1][i],b[2][i]);
			}}	
		return w;
	}
	
	void endGame(){
	for (int i=0;i<b.length;i++){
		for(int j=0;j<b[i].length;j++){
			b[i][j].setEnabled(false);
		}}
		_newGameButton.setEnabled(true);
		_chooseMenue.setEnabled(true);
		_newPlayerButton.setEnabled(true);
		SetLabel();
		saver(1);
	}
	
	void highlight (JButton x, JButton y, JButton z){
		JButton jb []={x,y,z};
		for (int i=0;i<jb.length;i++){
				jb[i].setBackground(Color.RED);
			
		}}
}

	

