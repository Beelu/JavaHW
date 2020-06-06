import java.security.SecureRandom;

public class CookieThread implements Runnable{
	private static final SecureRandom generator = new SecureRandom();
	private Cookie cookie;
	private boolean exe=true;     //判斷是否已被清除 
	private int counttime=0;
	private int[] fishX=new int[50];
	private int[] fishY=new int[50];
	private int[] fishsize=new int[50];
	private int fishcount=0,turtlecount=0;
	private int[] turtleX=new int[50];
	private int[] turtleY=new int[50];
	private int[] turtlesize=new int[50];
	private int fisheat;
	private int turtleeat;
	boolean fishhaveeat=false,turtlehaveeat=false;
	
	public CookieThread(Cookie t){
		cookie=t;
	}
	
	public void closeExe() {exe = false;}
	public boolean getExe() {return exe;}
	public void setfishX(int[] x) {fishX=x;}
	public void setfishY(int[] y) {fishY=y;}
	public void setfishsize(int[] s) {fishsize=s;}
	public void setfishcount(int f) {fishcount=f;}
	public void setturtleX(int[] x) {turtleX=x;}
	public void setturtleY(int[] y) {turtleY=y;}
	public void setturtlesize(int[] s) {turtlesize=s;}
	public void setturtlecount(int f) {turtlecount=f;}
	public int getfisheat() {
		fishhaveeat=false;
		return fisheat;}
	public int getturtleeat() {
		turtlehaveeat=false;
		return turtleeat;}
	
	public void run() {
		int y=cookie.getY();
		while(exe) {
			if(counttime==55)
				exe=false;
			
			try 
			{
				Thread.sleep(generator.nextInt(500));
				
				if(y<320)
				{
					y+=5;
					cookie.setY(y);
				}
				counttime+=1;
			}
			catch (InterruptedException exception) 
	         {
	            Thread.currentThread().interrupt(); 
	         } 
			
			for(int i=0;i<fishcount;i++) {
				if(fishX[i]<=cookie.getX() && cookie.getX()<=fishX[i]+fishsize[i])
				{
					if(fishY[i]<=cookie.getY() && cookie.getY()<=fishY[i]+fishsize[i])
					{
						fisheat=i;
						fishhaveeat=true;
						closeExe();
					}
				}
			}
			
			for(int j=0;j<turtlecount;j++) {
				if(turtleX[j]<=cookie.getX() && cookie.getX()<=turtleX[j]+turtlesize[j])
				{
					if(turtleY[j]<=cookie.getY() && cookie.getY()<=turtleY[j]+turtlesize[j])
					{
						turtleeat=j;
						turtlehaveeat=true;
						closeExe();
					}
				}
			}
	}
}
}
