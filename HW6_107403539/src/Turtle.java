import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Turtle {
	int x,y;
	Image turtle;
	Image otherturtle;
	int flag=0;
	static int i;
	static int j;
	boolean needDraw = true;    //判斷是否被清除
	
	public Turtle(int x,int y) {
		this.x=x;
		this.y=y;
		turtle=getturtle();
		otherturtle=getOtherturtle();
	}
	
	public void closeNeedDraw() {needDraw = false;}
	public boolean getNeedDraw() {return needDraw;}
	
	public int getSize() {return 80;}
	
	public void setX(int x) {this.x=x;}	
	public int getX() {return x;}
	
	public void setY(int y) {this.y=y;}
	public int getY() {return y;}
	
	public void setFlag(int f) {flag = f;}
	
	public void draw(Graphics g) {
		if(flag==0) 
			g.drawImage(turtle, x, y, null);
		else if(flag==1) 
			g.drawImage(otherturtle, x, y, null);
	}
	
	//圖片
	public static Image getturtle() {
		try
		{			
			ImageIcon turtle = new ImageIcon(ImageIO.read(new File("src\\w.png")));  
			Image image = turtle.getImage(); 
			Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_AREA_AVERAGING);
			
			return newimg;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	
	public static Image getOtherturtle() {
		try
		{
			ImageIcon turtle = new ImageIcon(ImageIO.read(new File("src\\w2.png")));  
			Image image = turtle.getImage(); 
			Image newimg = image.getScaledInstance(80, 80, java.awt.Image.SCALE_AREA_AVERAGING);
			
			return newimg;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
}
