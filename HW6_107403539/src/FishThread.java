//魚的執行緒，控管移動速度跟方向
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
	
	//隨機產生一隻魚跟方向
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
			//計時器 過一段時間換方向
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
				
				//x座標定位，(撞牆時)換方向，隨機速度
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
				
				//Y座標定位，(撞牆時)換方向，隨機速度
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
