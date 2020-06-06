import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Cookie {
	int x,y;
	Image cookie;
	int flag=0;
	static int i,j,size;
	boolean needDraw = true;
	
	public Cookie(int x,int y) {
		this.x=x;
		this.y=y;
		cookie=getcookie();
	}
	
	public void closeNeedDraw() {needDraw = false;}
	public boolean getNeedDraw() {return needDraw;}
	
	public void setX(int x) {this.x=x;}	
	public int getX() {return x;}
	
	public void setY(int y) {this.y=y;}
	public int getY() {return y;}
	public int getSize() {return size;}
	
	public void setFlag(int f) {flag = f;}
	
	public void draw(Graphics g) {
		g.drawImage(cookie, x, y, null);
	}
	
	//¹Ï¤ù
		public static Image getcookie() {
			try
			{
				String s = "src\\cookie.png";
				
				ImageIcon cookie = new ImageIcon(ImageIO.read(new File(s)));  
				Image image = cookie.getImage(); 
				Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);
				
				return newimg;
			}
			catch (IOException e)
			{
			      e.printStackTrace();
			}
			
			return null;
		}
}