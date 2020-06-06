//畫板程式
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel
{
   private MyBoundedShape[] shapes; // 將所有已畫完的形狀物件放在這
   private int shapeCount; // 計算已有幾個形狀物件

   private int shapeType; // 判斷選擇的工具為何(Combobox)
   private MyBoundedShape currentShape; // 將現在正在畫的形狀放在這
   private Color currentColor; // 現在選擇的顏色
   private boolean filledShape; // 是否有勾選填滿(checkbox)
   private int linesize=3,x1,y1,eraserSelect=0;//設定初始粗度，還有三個變數
   private JLabel statusLabel; // xy軸狀態列
   
   // constructor
   public DrawPanel(JLabel status)
   {
      shapes = new MyBoundedShape[10000]; 
      shapeCount = 0;
      
      setShapeType(0);
      setDrawingColor(Color.BLACK); 
      setFilledShape(false);
      currentShape = null; 
            
      setBackground(Color.WHITE);
           
      MouseHandler mouseHandler = new MouseHandler();
      addMouseListener(mouseHandler);
      addMouseMotionListener(mouseHandler);
      
      statusLabel = status;
   }

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      
      //將所有曾經畫過的圖形全部畫上
      for (int i = 0; i < shapeCount; i++)
         shapes[i].draw(g);
      
      //畫出現在正在畫的圖
      if (currentShape != null)
         currentShape.draw(g);
   }

   public void setShapeType(int shapeType)
   {
      if (shapeType < 0 || shapeType > 4)    
         shapeType = 0;
      
      this.shapeType = shapeType;
   } 
   
   public int getShapeType()
   {
	   return shapeType;
   }
   
 //重設顏色時把橡皮擦關掉
   public void setDrawingColor(Color c)
   {
      currentColor = c;
      eraserSelect=0;
   }

   //上一步
   public void clearLastShape()
   {
      if (shapeCount > 0)
      {
    	 shapeCount--;
         repaint();
      } 
   } 
   
   //清除畫面
   public void clearDrawing()
   {
      shapeCount = 0;
      repaint();
   } 

   public void setFilledShape(boolean isFilled)
   {
      filledShape = isFilled;
   } 
   
   public void setLineSize(int s)
   {
	   linesize = s;
   }
   
   public void setEraserSelect(int s)
   {
	   eraserSelect=s;
   }

   private class MouseHandler extends MouseAdapter
      implements MouseMotionListener
   {
      public void mousePressed(MouseEvent e)
      {
         if (currentShape != null)
            return;

         switch (shapeType)
         {
         	case 0:
         		x1=e.getX();      //在按下時先設定xy初始座標給畫筆
         		y1=e.getY();
         		break;
         	case 1:
               currentShape = new MyLine(e.getX(), e.getY(),e.getX(), e.getY(), currentColor,filledShape,linesize); 
               eraserSelect=0;    //重選形狀時把橡皮擦關掉
               break;
            case 2:
               currentShape = new MyOval(e.getX(), e.getY(),e.getX(), e.getY(), currentColor, filledShape,linesize);
               eraserSelect=0;
               break;
            case 3:
               currentShape = new MyRect(e.getX(), e.getY(),e.getX(), e.getY(), currentColor, filledShape,linesize);  
               eraserSelect=0;
               break;
            case 4:
               currentShape = new MyRoundRect(e.getX(), e.getY(),e.getX(), e.getY(), currentColor, filledShape,linesize);
               eraserSelect=0;
               break;
         } 
      }

      public void mouseReleased(MouseEvent e)
      {
         if (currentShape == null)
            return;
         
         currentShape.setX2(e.getX());
         currentShape.setY2(e.getY());

         if (shapeCount < shapes.length)
         {
            shapes[shapeCount] = currentShape;
            shapeCount++;
         } 
         
         currentShape = null;
         repaint();
      }

      //不斷更新正在畫的圖
      public void mouseDragged(MouseEvent e)
      {
         if (currentShape != null)
         {
            currentShape.setX2(e.getX());
            currentShape.setY2(e.getY());
            repaint();
         } 
         
         //筆刷實作，若橡皮擦被開啟則設定為畫白色，若關掉則會變回原本選擇的顏色畫線
         //將取得的兩個點連接並且不斷執行
         if(shapeType==0)
         {       	 
        	 if(eraserSelect==1)
        	 {
        		 currentShape = new MyLine(x1, y1, e.getX(), e.getY(), Color.WHITE,true,linesize);  
            	 x1=e.getX();
            	 y1=e.getY(); 
        	 }
        	 
        	 else
        	 {
        		 currentShape = new MyLine(x1, y1, e.getX(), e.getY(), currentColor,true,linesize);  
        		 x1=e.getX();
        		 y1=e.getY();
        	 }
        	 
        	 if (shapeCount < shapes.length)
             {
                shapes[shapeCount] = currentShape;
                shapeCount++;
             }
         }

         mouseMoved(e); // 在拖曳時也更新狀態列
      } 

      public void mouseMoved(MouseEvent e)
      {
         statusLabel.setText(
        		 String.format("指標位置(%d,%d)", e.getX(), e.getY()));
      } 
   } 
} 

