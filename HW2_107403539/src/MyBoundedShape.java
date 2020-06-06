import java.awt.Color;
import java.awt.Graphics;

public abstract class MyBoundedShape
{
   private Color myColor;
   private boolean filled; 
   private int linesize,x1,x2,y1,y2;

   public MyBoundedShape()
   {
      super();
      setFilled(false);
   }

   public MyBoundedShape(int x1, int y1, int x2, int y2, Color color, boolean isFilled, int size)
   {
	  setX1(x1); 
	  setY1(y1); 
	  setX2(x2); 
	  setY2(y2); 
	  setColor(color);
      setFilled(isFilled);
      setLineSize(size);
   }
   
   //xy座標的方法
   public void setX1(int x1){this.x1 = (x1 >= 0 ? x1 : 0);} 
 
   public int getX1(){return x1;} 
 
   public void setX2(int x2){this.x2 = (x2 >= 0 ? x2 : 0);}
 
   public int getX2(){return x2;}

   public void setY1(int y1){this.y1 = (y1 >= 0 ? y1 : 0);} 

   public int getY1(){return y1;} 

   public void setY2(int y2){this.y2 = (y2 >= 0 ? y2 : 0);}

   public int getY2(){return y2;} 

   //顏色的方法
   public void setColor(Color color){myColor = color;} 
   
   public Color getColor(){return myColor;}

   //取得最左上角點的方法
   public int getUpperLeftX(){return Math.min(getX1(), getX2());} 

   public int getUpperLeftY(){return Math.min(getY1(), getY2());} 

   //設定長寬的方法
   public int getWidth(){return Math.abs(getX2() - getX1());} 

   public int getHeight(){return Math.abs(getY2() - getY1());} 
   
   //填滿和粗度的方法
   public boolean isFilled(){return filled;} 
 
   public void setFilled(boolean isFilled){filled = isFilled;} 
   
   public void setLineSize(int size){linesize = size;}
   
   public int getLineSize(){return linesize;}
   
   public abstract void draw(Graphics g);
} 