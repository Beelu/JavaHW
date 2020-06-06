import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Bloodpanel extends JPanel{
	int blooddown = 0;
	
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g); 
	    Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		
		g.drawLine(0, 5, 305-blooddown, 5);
	}
	
	public void blood(boolean b)
	{
		if(305-blooddown>0) {
			if(b)
				blooddown += 15;
			else
				blooddown += 60;
		}
		else
			System.out.println("尼已經死了");
	}
}
