//�e�O�{��
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
   private MyBoundedShape[] shapes; // �N�Ҧ��w�e�����Ϊ������b�o
   private int shapeCount; // �p��w���X�ӧΪ�����

   private int shapeType; // �P�_��ܪ��u�㬰��(Combobox)
   private MyBoundedShape currentShape; // �N�{�b���b�e���Ϊ���b�o
   private Color currentColor; // �{�b��ܪ��C��
   private boolean filledShape; // �O�_���Ŀ��(checkbox)
   private int linesize=3,x1,y1,eraserSelect=0;//�]�w��l�ʫסA�٦��T���ܼ�
   private JLabel statusLabel; // xy�b���A�C
   
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
      
      //�N�Ҧ����g�e�L���ϧΥ����e�W
      for (int i = 0; i < shapeCount; i++)
         shapes[i].draw(g);
      
      //�e�X�{�b���b�e����
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
   
 //���]�C��ɧ���������
   public void setDrawingColor(Color c)
   {
      currentColor = c;
      eraserSelect=0;
   }

   //�W�@�B
   public void clearLastShape()
   {
      if (shapeCount > 0)
      {
    	 shapeCount--;
         repaint();
      } 
   } 
   
   //�M���e��
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
         		x1=e.getX();      //�b���U�ɥ��]�wxy��l�y�е��e��
         		y1=e.getY();
         		break;
         	case 1:
               currentShape = new MyLine(e.getX(), e.getY(),e.getX(), e.getY(), currentColor,filledShape,linesize); 
               eraserSelect=0;    //����Ϊ��ɧ���������
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

      //���_��s���b�e����
      public void mouseDragged(MouseEvent e)
      {
         if (currentShape != null)
         {
            currentShape.setX2(e.getX());
            currentShape.setY2(e.getY());
            repaint();
         } 
         
         //�����@�A�Y������Q�}�ҫh�]�w���e�զ�A�Y�����h�|�ܦ^�쥻��ܪ��C��e�u
         //�N���o������I�s���åB���_����
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

         mouseMoved(e); // �b�즲�ɤ]��s���A�C
      } 

      public void mouseMoved(MouseEvent e)
      {
         statusLabel.setText(
        		 String.format("���Ц�m(%d,%d)", e.getX(), e.getY()));
      } 
   } 
} 

