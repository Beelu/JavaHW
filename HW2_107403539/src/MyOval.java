import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MyOval extends MyBoundedShape
{
   public MyOval()
   {
      super();
   } 

   public MyOval(int x1, int y1, int x2, int y2, Color color, boolean isFilled, int size)
   {
      super(x1, y1, x2, y2, color, isFilled, size);
   } 

   public void draw(Graphics g)
   {
	  Graphics2D g2d = (Graphics2D) g;
      g.setColor(getColor());
      g2d.setStroke(new BasicStroke(getLineSize()));
      
      if (isFilled())
         g.fillOval(getUpperLeftX(), getUpperLeftY(),getWidth(), getHeight());
      else
         g.drawOval(getUpperLeftX(), getUpperLeftY(),getWidth(), getHeight());
   } 
} 