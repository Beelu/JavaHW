import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class MyLine extends MyBoundedShape
{
   public MyLine()
   {
      super();
   } 

   public MyLine(int x1, int y1, int x2, int y2, Color color,boolean isFilled,int size)
   {
	   super(x1, y1, x2, y2, color, isFilled, size);     
   } 

   //畫線
   public void draw(Graphics g)
   {
	  Graphics2D g2d = (Graphics2D) g;
	  g.setColor(getColor());
	  g2d.setStroke(new BasicStroke(getLineSize()));
	  
	  if (isFilled()) 
	      g.drawLine(getX1(), getY1(), getX2(), getY2());
	  
	  else {
		  float[] dashes = {10};
		  g2d.setStroke(new BasicStroke(getLineSize(), BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND, 10, dashes, 0));
		  g.drawLine(getX1(), getY1(), getX2(), getY2());
	  	}
	  g2d.setStroke(new BasicStroke(getLineSize()));          //避免其他圖形變成用虛線畫
   } 
}
