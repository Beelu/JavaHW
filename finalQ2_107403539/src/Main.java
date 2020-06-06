import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	private JButton newfish,newturtle,removeselected,removeall,cookiebutton,fishingbutton;
	private JLabel status = new JLabel("目前功能:新增魚            魚數量:0  烏龜數量:0 飼料數量:0 釣竿數量:0 已釣到魚數量:0 已釣到烏龜數量:0");
	private Aquarium underpanel = new Aquarium(status);
	private String buttonselected="新增魚";
	private String statustring = "目前功能:%s            魚數量:%d  烏龜數量:%d 飼料數量:%d 釣竿數量:%d 已釣到魚數量: 已釣到烏龜數量:";
	
	public Main() {
		super("水族箱");
		
		//下面板(水族箱)
		underpanel.setBackground(Color.CYAN);
		add(underpanel, BorderLayout.CENTER);
		
		//上面板(按鈕)
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(3,2));
		
		newfish = new JButton("新增魚");
		newfish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(1);
				buttonselected = "新增魚";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		newturtle = new JButton("新增烏龜");
		newturtle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(2);
				buttonselected = "新增烏龜";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		removeselected = new JButton("移除選取");
		removeselected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(3);
				buttonselected = "移除選取";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		removeall = new JButton("移除全部");
		removeall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.clear();
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		cookiebutton = new JButton("餵食");
		cookiebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(4);
				buttonselected = "餵食";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		fishingbutton = new JButton("釣魚");
		fishingbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(5);
				buttonselected = "釣魚";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		toppanel.add(newfish);
		toppanel.add(removeselected);
		toppanel.add(newturtle);
		toppanel.add(removeall);
		toppanel.add(cookiebutton);
		toppanel.add(fishingbutton);
		add(status,BorderLayout.SOUTH);
		
		add(toppanel,BorderLayout.NORTH);
	}
}