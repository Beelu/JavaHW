//����������A���޲��ʳt�׸��V
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class FishThread implements Runnable{
	private static final SecureRandom generator = new SecureRandom();
	private Fish fish;
	private int flag=0,flagY=0;
	private int counttime=0,counttimeY=0;
	private boolean exe=true;
	
	//�H�����ͤ@�������V
	public FishThread(Fish f){
		fish=f;
		flag = generator.nextInt(2);
		flagY = generator.nextInt(2);
		fish.setFlag(flag);
	}
	
	public void closeExe() {exe = false;}
	
	public void run() {
		int x=fish.getX();
		int y=fish.getY();
		while(exe) {
			//�p�ɾ� �L�@�q�ɶ�����V
			if(counttime==45) {
				if(flag==0)
					flag=1;
				else if(flag==1)
					flag=0;
				
				counttime=0;
			}
			if(counttimeY==20) {
				if(flagY==0)
					flagY=1;
				else if(flagY==1)
					flagY=0;
				
				counttimeY=0;
			}
			
			try 
			{
				Thread.sleep(generator.nextInt(500));
				
				//x�y�Щw��A(�����)����V�A�H���t��
				if(x>=750) { 
					flag=1;
					counttime=0;
				}
				else if(x<=0) {
					flag=0;
					counttime=0;
				}
				
				if(flag==0) {
					x += generator.nextInt(10);

					fish.setFlag(0);
					fish.setX(x);
				}
				else if(flag==1) {
					x -= generator.nextInt(10);
					
					fish.setFlag(1);				
					fish.setX(x);
				}				
				
				//Y�y�Щw��A(�����)����V�A�H���t��
				if(y>=320) { 
					flagY=1;
					counttimeY=0;
				}
				else if(y<=0) {
					flagY=0;
					counttimeY=0;
				}
				
				if(flagY==0) {
					y += generator.nextInt(5);
					fish.setY(y);
				}
				else if(flagY==1) {
					y -= generator.nextInt(5);							
					fish.setY(y);
				}	
				
				counttime+=1;
				counttimeY+=1;
			}
			catch (InterruptedException exception) 
	         {
	            Thread.currentThread().interrupt(); 
	         } 
		}
	}
}
