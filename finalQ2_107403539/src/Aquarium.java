//���ڽc���O(����a���a��)
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
	private Fish[] totalfish = new Fish[50];
	private Cookie[] totalcookie = new Cookie[10];
	private int cookiecount=0,cookiecount2=0;
	private Fishing[] totalfishing = new Fishing[10];
	private int fishingcount=0,fishingcount2=0;
	private FishingThread fishingThread[] = new FishingThread[10];
	private int fishcount=0,fishcount2=0;
	private Turtle[] totalturtle = new Turtle[50];
	private int turtlecount=0,turtlecount2=0;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private JLabel statusLabel;
	private FishThread[] fishThread = new FishThread[50];
	private TurtleThread[] turtleThread = new TurtleThread[50];
	private CookieThread[] cookieThread = new CookieThread[10];
	private String statustring = "�ثe�\��:%s            ���ƶq:%d  �Q�t�ƶq:%d �}�Ƽƶq:%d ����ƶq:%d �w���쳽�ƶq: �w����Q�t�ƶq:";
	private String buttonselectstr="�s�W��";
	
	public Aquarium(JLabel status) {
		MouseHandler mouseHandler = new MouseHandler();
	    addMouseListener(mouseHandler);
	    statusLabel = status;
	    statusLabel.setForeground(Color.BLUE);
	}
	
	//�D�e�O
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
			for(int i = 0;i<cookiecount; i++)
			{
				if(cookieThread[i].getExe()) {
					totalcookie[i].draw(g);
					cookiecount2+=1;
				}
			}
			for(int i = 0;i<fishingcount; i++)
			{
				if(totalfishing[i].getNeedDraw())
					totalfishing[i].draw(g);
			}
			
			statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
			cookiecount2=0;
			int fishX[] = new int[50];
			int fishY[] = new int[50];
			int fishsize[] = new int[50];
			int turtleX[] = new int[50];
			int turtleY[] = new int[50];
			int turtlesize[] = new int[50];
			for(int j=0;j<fishcount;j++) {
				if(totalfish[j].getNeedDraw()) {
					fishX[j]=totalfish[j].getX();
					fishY[j]=totalfish[j].getY();
					fishsize[j]=totalfish[j].getSize();
				}
			}
			for(int k=0;k<turtlecount;k++) {
				if(totalturtle[k].getNeedDraw()) {
					turtleX[k]=totalturtle[k].getX();
					turtleY[k]=totalturtle[k].getY();
					turtlesize[k]=totalturtle[k].getSize();
				}
			}
			for(int i=0;i<cookiecount;i++) {
				cookieThread[i].setfishX(fishX);
				cookieThread[i].setfishY(fishY);
				cookieThread[i].setfishsize(fishsize);
				cookieThread[i].setfishcount(fishcount);
				cookieThread[i].setturtleX(turtleX);
				cookieThread[i].setturtleY(turtleY);
				cookieThread[i].setturtlesize(turtlesize);
				cookieThread[i].setturtlecount(turtlecount);
				if(cookieThread[i].fishhaveeat)
					totalfish[cookieThread[i].getfisheat()].setSize(10);
				if(cookieThread[i].turtlehaveeat)
					totalturtle[cookieThread[i].getturtleeat()].setSize(10);
			}
			
			repaint();
		}
	
	//�T�{�ҿ���s��k
	public void setButtonselected(int i) {buttonselected = i;} 
	
	//�^�ǳ� �Q�t�`�Ƥ�k
	public int gettotalfish() {return fishcount2;}
	public int gettotalturtle() {return turtlecount2;}
	public int gettotalcookie() {return cookiecount2;}
	public int gettotalfishing() {return fishingcount2;}
	
	//�M���e����k
	public void clear() {
		fishcount=0;
		turtlecount=0;
		fishcount2=0;
		turtlecount2=0;
		cookiecount=0;
		cookiecount2=0;
		fishingcount=0;
		fishingcount2=0;
		
		repaint();
	}
	
	//�ƹ��I���ɧP�_��ܫ��s�ð��X����
	private class MouseHandler extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			switch(buttonselected){
				case 1:
					if(fishcount<totalfish.length) {
						buttonselectstr = "�s�W��";
						totalfish[fishcount] = new Fish(e.getX(),e.getY());
						fishThread[fishcount] = new FishThread(totalfish[fishcount]);
						executorService.execute(fishThread[fishcount]);
						fishcount+=1;
						fishcount2+=1;
						statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
					}
					break;
				case 2:
					if(turtlecount<totalturtle.length) {
						buttonselectstr = "�s�W�Q�t";
						totalturtle[turtlecount] = new Turtle(e.getX(),e.getY());
						turtleThread[turtlecount] = new TurtleThread(totalturtle[turtlecount]);
						executorService.execute(turtleThread[turtlecount]);
						turtlecount+=1;
						turtlecount2+=1;
						statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
					}
					break;
				case 3:
					buttonselectstr = "�������";
					statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
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
									statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
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
									statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
									turtleThread[i].closeExe();
									totalturtle[i].closeNeedDraw();
									repaint();
									break;
								}
							}
						}
					}	
					break;
				
				case 4:
					if(cookiecount<totalcookie.length) {
						buttonselectstr = "����";
						totalcookie[cookiecount] = new Cookie(e.getX(),e.getY());
						cookieThread[cookiecount] = new CookieThread(totalcookie[cookiecount]);
						executorService.execute(cookieThread[cookiecount]);
						cookiecount+=1;
						cookiecount2+=1;
						statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
					}
					break;
				
				case 5:
					if(fishingcount<totalfishing.length) {
						buttonselectstr = "����";
						totalfishing[fishingcount] = new Fishing(e.getX(),e.getY());
						fishingThread[fishingcount] = new FishingThread(totalfishing[fishingcount]);
						executorService.execute(fishingThread[fishingcount]);
						fishingcount+=1;
						fishingcount2+=1;
						statusLabel.setText(String.format(statustring,buttonselectstr,fishcount2,turtlecount2,cookiecount2,fishingcount2));
						}
					break;
		}
	}
}
}