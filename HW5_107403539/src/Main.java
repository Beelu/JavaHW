import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends JFrame{
	private JLabel[] mapitem = new JLabel[100];
	int[] mapint = new int[100];
	int total=0;
	Bloodpanel bloodpanel = new Bloodpanel();
	JPanel underpanel = new JPanel();

	public Main(){
		super("�a��");
		
		add(bloodpanel,BorderLayout.NORTH);
		underpanel.setLayout(new GridLayout(10,10));
		
		//Ū�ɨðO��
		try
	      {
			File f = new File("src\\map.txt");
			Scanner s = new Scanner(f);
			
			//Functional Programing
			Arrays.stream(mapint)
			      .map(mapint -> Integer.parseInt(s.next()))
			      .forEach(mapint -> decide(mapint));
			
			s.close();
	      } 
	      catch (IOException ioException)
	      {
	         ioException.printStackTrace();
	      }

		add(underpanel, BorderLayout.CENTER);
	}
	
	//�T�عϧΪ��վ�j�p�M���o
	public static ImageIcon getheart() {
		try
		{
			ImageIcon heart = new ImageIcon(ImageIO.read(new File("src\\heart.png")));  
			Image image = heart.getImage(); 
			Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);  
			heart = new ImageIcon(newimg);
			
			return heart;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	
	public static ImageIcon getbrickwall() {
		try
		{
			ImageIcon brickwall = new ImageIcon(ImageIO.read(new File("src\\brickwall.png")));  
			Image image = brickwall.getImage(); 
			Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);  
			brickwall = new ImageIcon(newimg);
			
			return brickwall;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	
	public static ImageIcon getdiamond() {
		try
		{
			ImageIcon diamond = new ImageIcon(ImageIO.read(new File("src\\diamond.png")));  
			Image image = diamond.getImage(); 
			Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);  
			diamond = new ImageIcon(newimg);
			
			return diamond;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	
	public void decide(int number) {
		//�P�_���Ʀr�X�N��J���ӹϤ��A�p�G�O����h��random��Ʀ�1/5�����v�|�X�{�R��
		if(number == 0) {
				mapitem[total] = new JLabel("");
				//��@����
				mapitem[total].addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
						bloodpanel.blood(true);
						repaint();
					}
				});
			}
			else if(number == 1)
			{
				Random r = new Random();
				int i=0;
				
				//Functional Programing�����H���Ʀr�A�ͦ�����ηR��
				IntStream.of(i)
						 .map(value -> value = r.nextInt(6))
						 .forEach(value -> randomSelect(value));
			}
			else if(number == 2)
				mapitem[total] = new JLabel("",getdiamond(),SwingConstants.LEFT);
			
			underpanel.add(mapitem[total]);
			total+=1;
	}
	
	public void randomSelect(int i){
		if(i==4) 
			mapitem[total] = new JLabel("",getheart(),SwingConstants.LEFT);
		else {
			mapitem[total] = new JLabel("",getbrickwall(),SwingConstants.LEFT);
			//��@����
			mapitem[total].addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					bloodpanel.blood(false);
					repaint();
				}
			});
		}
	}
}
