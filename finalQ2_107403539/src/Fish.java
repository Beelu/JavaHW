//產生一隻魚
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Fish {
	int x,y;
	Image fish;
	Image otherfish;
	int flag=0;
	static int i,j,size;
	int size2;
	boolean needDraw = true;
	
	public Fish(int x,int y) {
		this.x=x;
		this.y=y;
		fish=getfish();
		otherfish=getOtherfish();
		size2=size;
	}
	
	public void closeNeedDraw() {needDraw = false;}
	public boolean getNeedDraw() {return needDraw;}
	
	public void setX(int x) {this.x=x;}	
	public int getX() {return x;}
	
	public void setY(int y) {this.y=y;}
	public int getY() {return y;}
	public int getSize() {return size2;}
	public void setSize(int s) {
		if(size2<=100)
			size2+=s;
		}
	
	public void setFlag(int f) {flag = f;}
	
	public void draw(Graphics g) {
		if(flag==0) 
			g.drawImage(fish, x, y, size2, size2, null);
		else if(flag==1) 
			g.drawImage(otherfish, x, y, size2, size2, null);
	}
	
	//圖片
	public static Image getfish() {
		try
		{
			Random r = new Random();
			size = r.nextInt(60)+25;
			i = 2*r.nextInt(3)+1;
			j = i+1;
			String s = "src\\" + i + ".png";
			
			ImageIcon fish = new ImageIcon(ImageIO.read(new File(s)));  
			Image image = fish.getImage(); 
			Image newimg = image.getScaledInstance(size, size, java.awt.Image.SCALE_AREA_AVERAGING);
			
			return newimg;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	
	public static Image getOtherfish() {
		try
		{
			String s = "src\\" + j + ".png";
			
			ImageIcon fish = new ImageIcon(ImageIO.read(new File(s)));  
			Image image = fish.getImage(); 
			Image newimg = image.getScaledInstance(size, size, java.awt.Image.SCALE_AREA_AVERAGING);
			
			return newimg;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
}
