//水族箱面板(魚游泳的地方)
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;


public class Aquarium extends JPanel{
	private int buttonselected=1;
	private Fish[] totalfish = new Fish[100];
	private int fishcount=0,fishcount2=0;
	private Turtle[] totalturtle = new Turtle[50];
	private int turtlecount=0,turtlecount2=0;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private JLabel statusLabel;
	private FishThread[] fishThread = new FishThread[100];
	private TurtleThread[] turtleThread = new TurtleThread[50];
	
	public Aquarium(JLabel status) {
		MouseHandler mouseHandler = new MouseHandler();
	    addMouseListener(mouseHandler);
	    statusLabel = status;
	    statusLabel.setForeground(Color.BLUE);
	}
	
	//主畫板
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < fishcount; i++)
		{
			if(totalfish[i].getNeedDraw())
				totalfish[i].draw(g);
		}
		for(int i = 0;i<turtlecount; i++)
		{
			if(totalturtle[i].getNeedDraw())
				totalturtle[i].draw(g);
		}
		
		repaint();
	}
	
	//確認所選按鈕方法
	public void setButtonselected(int i) {buttonselected = i;} 
	
	//回傳魚 烏龜總數方法
	public int gettotalfish() {return fishcount2;}
	public int gettotalturtle() {return turtlecount2;}
	
	//清除畫面方法
	public void clear() {
		fishcount=0;
		turtlecount=0;
		fishcount2=0;
		turtlecount2=0;
		repaint();
	}
	
	//滑鼠點擊時判斷選擇按鈕並做出反應
	private class MouseHandler extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			switch(buttonselected){
				case 1:
					if(fishcount<totalfish.length) {
						totalfish[fishcount] = new Fish(e.getX(),e.getY());
						fishThread[fishcount] = new FishThread(totalfish[fishcount]);
						executorService.execute(fishThread[fishcount]);
						fishcount+=1;
						fishcount2+=1;
						statusLabel.setText(String.format("目前功能:新增魚            魚數量:%d  烏龜數量:%d",fishcount2,turtlecount2));
					}
					break;
				case 2:
					if(turtlecount<totalturtle.length) {
						totalturtle[turtlecount] = new Turtle(e.getX(),e.getY());
						turtleThread[turtlecount] = new TurtleThread(totalturtle[turtlecount]);
						executorService.execute(turtleThread[turtlecount]);
						turtlecount+=1;
						turtlecount2+=1;
						statusLabel.setText(String.format("目前功能:新增烏龜            魚數量:%d  烏龜數量:%d",fishcount2,turtlecount2));
					}
					break;
				case 3:
					statusLabel.setText(String.format("目前功能:移除選取            魚數量:%d  烏龜數量:%d",fishcount2,turtlecount2));
					int clickX = e.getX();
					int clickY = e.getY();
					for(int i=0;i<fishcount;i++)
					{
						int fishX = totalfish[i].getX();
						int fishY = totalfish[i].getY();
						
						if(totalfish[i].getNeedDraw())
						{
							if(fishX<=clickX && clickX<=fishX+totalfish[i].getSize())
							{
								if(fishY<=clickY && clickY<=fishY+totalfish[i].getSize())
								{
									if(totalfish[i].getNeedDraw())
										fishcount2-=1;
									statusLabel.setText(String.format("目前功能:移除選取            魚數量:%d  烏龜數量:%d",fishcount2,turtlecount2));
									fishThread[i].closeExe();
									totalfish[i].closeNeedDraw();
									repaint();
									break;
								}
							}
						}
					}
					
					for(int i=0;i<turtlecount;i++)
					{
						int turtleX = totalturtle[i].getX();
						int turtleY = totalturtle[i].getY();
						
						if(totalturtle[i].getNeedDraw())
						{
							if(turtleX<=clickX && clickX<=turtleX+totalturtle[i].getSize())
							{
								if(turtleY<=clickY && clickY<=turtleY+totalturtle[i].getSize())
								{
									if(totalturtle[i].getNeedDraw())
										turtlecount2-=1;
									statusLabel.setText(String.format("目前功能:移除選取            魚數量:%d  烏龜數量:%d",fishcount2,turtlecount2));
									turtleThread[i].closeExe();
									totalturtle[i].closeNeedDraw();
									repaint();
									break;
								}
							}
						}
					}
					
					break;
			}
		}
	}
}