import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.util.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends JFrame{
	private JLabel[] mapitem = new JLabel[100];
	String mapstring = "";
	
	public void draw(Graphics g)
	{
		g.drawLine(10, 10, 330, 10);
	}

	public Main(){
		super("地圖");
		
		final Bloodpanel bloodpanel = new Bloodpanel();
		add(bloodpanel,BorderLayout.NORTH);
		
		//讀檔並記錄
		try
	      {
			FileReader fr = new FileReader("src\\map.txt");
			BufferedReader br = new BufferedReader(fr);
			
			while (br.ready()) 
				mapstring += br.readLine() + "\n";
			
			fr.close();
	      } 
	      catch (IOException ioException)
	      {
	         ioException.printStackTrace();
	      }
		
		
		JPanel underpanel = new JPanel();
		underpanel.setLayout(new GridLayout(10,10));
		
		Scanner s = new Scanner(mapstring);
		
		//判斷為數字幾就放入哪個圖片，如果是牆壁則用random函數有1/5的機率會出現愛心
		while(s.hasNext()) {
				int number = Integer.parseInt(s.next());
				int total=0;
				
				if(number == 0) {
					mapitem[total] = new JLabel("");
					//實作扣血
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
					int i = r.nextInt(5);
					if(i==4) 
						mapitem[total] = new JLabel("",getheart(),SwingConstants.LEFT);
					else {
						mapitem[total] = new JLabel("",getbrickwall(),SwingConstants.LEFT);
						//實作扣血
						mapitem[total].addMouseListener(new MouseAdapter() {
							public void mouseEntered(MouseEvent e) {
								bloodpanel.blood(false);
								repaint();
							}
						});
					}
				}
				else if(number == 2)
					mapitem[total] = new JLabel("",getdiamond(),SwingConstants.LEFT);
				
				underpanel.add(mapitem[total]);
				total++;
		}
		s.close();
		add(underpanel, BorderLayout.CENTER);
	}
	
	//三種圖形的調整大小和取得
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
}
