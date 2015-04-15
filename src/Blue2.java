import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Blue2 extends JFrame implements ActionListener

{
	JPanel jp1,jp2,jp3,jp4,jp5;
	JLabel jl1,jl2,jl3,jl4,jl5,jl6;
	JButton jb1,jb2,jb3,jb4;
	JTextField jt1, jt2,jt3;
	JTextField jtf;
	JTable jtb;
	
	
	static List<String> mtname = new ArrayList<String>();
	static List<String> rfname = new ArrayList<String>();
	static int n;
	static File[] mtFile = null;
	static File[] rfFile = null;
	static ArrayList[] mtContext = null;
	static ArrayList[] rfContext = null;
	static int[] correct;
	static int[] len;
	static int lens,lenr;
	static double bp;
	static double blue;
	public static void main(String[] args) {
		Blue2 de = new Blue2();
	}
	public void saveMT(List<String> mtName) throws IOException{
		if(!mtName.isEmpty()){
			mtFile = new File[mtName.size()];
			mtContext = new ArrayList[mtName.size()];
			for(int i=0;i<mtName.size();i++){
				mtContext[i] = new ArrayList<String>();
				 mtFile[i] = new File(mtName.get(i));
				 if(!mtFile[i].exists() || !mtFile[i].isFile()){  
				        javax.swing.JOptionPane.showMessageDialog(null, "Desole,La path est fault！");  
				    } 
				 	String temp = null;
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mtFile[i])));
					while((temp = br.readLine())!=null){
						mtContext[i].add(temp);	
					}
					br.close();
				} 
			}	
	}
	public void saveRF(List<String> rfName) throws IOException{
		if(!rfName.isEmpty()){
			rfFile = new File[rfName.size()];
			rfContext = new ArrayList[rfName.size()];
			for(int i=0;i<rfName.size();i++){
				rfContext[i] = new ArrayList<String>();
				 rfFile[i] = new File(rfName.get(i));
				 if(!rfFile[i].exists() || !rfFile[i].isFile()){  
				        javax.swing.JOptionPane.showMessageDialog(null, "Desole,La path est fault！");  
				    } 
				 	String temp = null;
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rfFile[i])));
					while((temp = br.readLine())!=null){
						rfContext[i].add(temp);	
					}
					br.close();
				} 	
			}		
	}
	
	public void calculeUnePhase(int n,String mtline,String[] rfline){
			
		tempMt temp = new tempMt();
		String[] mtli = mtline.split(" ");	
		
		lens +=mtli.length;
		for(int i=0;i<n;i++){
			len[i] +=mtli.length-i;
		}
		
		String mt = "";
		String rf = "";
		for(int i=0;i<n;i++){
			for(int j=0;j<mtli.length-i;j++){
				for(int m=0;m<=i;m++){
					mt += mtli[j+m];
				}
				temp.setMt(mt);
				temp.isExist = false;
				for(int c=0;c<rfline.length;c++){
					String[] rfli = rfline[c].split(" ");
					for(int d=0;d<rfli.length-i;d++){
						for(int e=0;e<=i;e++){
							rf +=rfli[d+e];
						}
//						System.out.println(rf);
						if(mt.equalsIgnoreCase(rf)){
							 correct[i] += 1;
							temp.isExist = true;
						}
						rf="";
					}
					if(temp.isExist) break;
				}
//				System.out.println(mt+"   "+correct);
//				System.out.println("_________________");
				mt="";
			}
//			System.out.println("totale est : "+i+" _"+correct[i]);
		}
	}
	public void calculeBlue(int n,ArrayList[] mtCon,ArrayList[] rfCon){
			String[] rfline = new String[rfCon.length];
			
			for(int i=0;i<mtCon.length;i++){ 			
				for(int j=0;j<mtCon[i].size();j++){
					for(int m=0;m<rfCon.length;m++){
						rfline[m] = rfCon[m].get(j).toString();
					}	
					for(int q=0;q<rfline.length;q++){
						String[] s = rfline[q].split(" ");
						lenr += s.length; 
					}
					calculeUnePhase(n, mtCon[i].get(j).toString(),rfline);
				}
			}
			System.out.println("Total_length : "+ lens+"  "+lenr);
			for(int i=0;i<n;i++){
				System.out.println(i+"-gram : "+correct[i]+" :"+len[i]);
				
			}
			if((2*lens)>lenr){
				bp = 1;
			}else{
				bp = Math.exp(1-((double)lenr/(2*lens)));
			}
			double total = 0 ;
			for(int i=0;i<len.length;i++){
				if(((double)correct[i]/len[i])!=0){
					total += Math.log((double)correct[i]/len[i]);
				}
			}
			blue = bp * Math.exp((double)total/n);
			System.out.println(total+"  "+bp+"  "+blue);
	}
	public Blue2(){
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3  = new JPanel();
		jp4  = new JPanel();
		jp5  = new JPanel();
		
		jl1 = new JLabel("Traduction:");
		jl2 = new JLabel("Reference:");
		jl3 = new JLabel("");
		jl4 = new JLabel("N-gram : ");
		jl5 = new JLabel("");
		jl6 = new JLabel("");
		jtf = new JTextField(10);
		jtf.setText("4");
		
		jb1 = new JButton("Choix");
		jb1.addActionListener(this);
		jb1.setActionCommand("choix-mt");
		
		
		jb2 = new JButton("Choix");
		jb2.addActionListener(this);
		jb2.setActionCommand("choix-rf");
		
		jb3 = new JButton("Calculer");
		jb3.addActionListener(this);
		jb3.setActionCommand("calculer");
		jb4 = new JButton("Clear");
		jb4.addActionListener(this);
		jb4.setActionCommand("clear");
		
		jt1 = new JTextField(30);
		jt2 = new JTextField(30);
		jt3 = new JTextField(10);
		
		this.setLayout(new GridLayout(5,1));
		jp1.add(jl1);
		jp1.add(jt1);
		jp1.add(jb1);
		
		jp2.add(jl2);
		jp2.add(jt2);
		jp2.add(jb2);
		
		jp3.add(jl4);
		jp3.add(jtf);
		jp3.add(jb3);
		
		jp4.setLayout(new GridLayout(4,1));
		jp4.add(jl6);
		jp4.add(jl3);
		jp4.add(jl5);
		jp4.add(jb4);
		
		jp5.setLayout(new GridLayout(1,1));
		
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp5);
		this.add(jp4);
		
		this.setTitle("Blue");
		this.setSize(500,600);
		this.setResizable(false);
		this.setLocation(300, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("choix-mt")){
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Choix votre MT....");
			jfc.showOpenDialog(null);
			jfc.setVisible(true);		
				
			String filename1 = jfc.getSelectedFile().getAbsolutePath();
			mtname.add(filename1);
			String namelist = "";
			for(int i=0;i<mtname.size();i++){
				namelist +=mtname.get(i)+"  ";
			}
			jt1.setText(namelist);			
		}
		if(e.getActionCommand().equals("choix-rf")){
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Choix votre Reference....");
			jfc.showOpenDialog(null);
			jfc.setVisible(true);			
			String filename1 = jfc.getSelectedFile().getAbsolutePath();
			rfname.add(filename1);
			String namelist = "";
			for(int i=0;i<rfname.size();i++){
				namelist +=rfname.get(i)+"  ";
			}
			jt2.setText(namelist);	
		}
		if(e.getActionCommand().equals("calculer")){
			n =Integer.parseInt(jtf.getText());
			try {
				saveMT(mtname);
				saveRF(rfname);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			len = new int[n];
			correct = new int[n];
			calculeBlue(n, mtContext, rfContext);
			
			Object[][] tableData = new Object[n+1][3];
			for(int i=0;i<n;i++){
				tableData[i][0]= i+"gram";
				tableData[i][1]= correct[i];
				tableData[i][2]=len[i];
			} 
		    Object[] columnTitle = {"N-gram" , "Count_Cor" , "Count_mots"}; 
		    jtb = new JTable(tableData,columnTitle);
		    jp5.add((new JScrollPane(jtb)));
			jl6.setText("MT_length : "+ lens+"    "+" RF_length : "+lenr);
			jl3.setText("bp : "+bp);
			jl5.setText("Blue : "+blue);
			
		}
		if(e.getActionCommand().equals("clear")){
			mtname = new ArrayList<String>();
			rfname = new ArrayList<String>();
			n=4;
			mtFile = null;
			rfFile = null;
			mtContext = null;
			rfContext = null;
			correct = null;
			len=null;
			lens=0;
			lenr=0;
			bp = 0;
			blue = 0;
		}
			
	}

	
	
}
