import java.security.SecureRandom;

public class TurtleThread implements Runnable{
	private static final SecureRandom generator = new SecureRandom();
	private Turtle turtle;
	private int flag=0;			  //�P�_���k
	private int counttime=0;	  //�p��ɶ� �@�w�ɶ��᭫�s���t��V
	private boolean exe=true;     //�P�_�O�_�w�Q�M�� 
	
	public TurtleThread(Turtle t){
		turtle=t;
	}
	
	public void closeExe() {exe = false;}
	
	public void run() {
		int x=turtle.getX();
		int y=turtle.getY();
		while(exe) {
			if(counttime==60) {
				if(flag==0)
					flag=1;
				else if(flag==1)
					flag=0;
				
				counttime=0;
			}
			
			try 
			{
				Thread.sleep(generator.nextInt(500));
				
				if(y<320)
				{
					y+=7;
					turtle.setY(y);
				}
				else 
				{
					if(x>=750) { 
						flag=1;
						counttime=0;
					}
					else if(x<=0) {
						flag=0;
						counttime=0;
					}
					
					if(flag==0) 
					{
						x+=generator.nextInt(7);
						turtle.setX(x);
						turtle.setFlag(0);
					}
					else if(flag==1)
					{
						x-=generator.nextInt(7);
						turtle.setX(x);
						turtle.setFlag(1);
					}
				}
				
				counttime+=1;
			}
			catch (InterruptedException exception) 
	         {
	            Thread.currentThread().interrupt(); 
	         } 
		}
	}

}
