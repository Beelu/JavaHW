import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	private JButton newfish,newturtle,removeselected,removeall;
	private JLabel status = new JLabel("目前功能:新增魚            魚數量:0  烏龜數量:0");
	private Aquarium underpanel = new Aquarium(status);
	private String buttonselected="新增魚";
	
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
				status.setText(String.format("目前功能:新增魚            魚數量:%d  烏龜數量:%d",underpanel.gettotalfish(),underpanel.gettotalturtle()));
			}
		});
		
		newturtle = new JButton("新增烏龜");
		newturtle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(2);
				buttonselected = "新增烏龜";
				status.setText(String.format("目前功能:新增烏龜            魚數量:%d  烏龜數量:%d",underpanel.gettotalfish(),underpanel.gettotalturtle()));
			}
		});
		
		removeselected = new JButton("移除選取");
		removeselected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(3);
				status.setText(String.format("目前功能:移除選取            魚數量:%d  烏龜數量:%d",underpanel.gettotalfish(),underpanel.gettotalturtle()));
			}
		});
		
		removeall = new JButton("移除全部");
		removeall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.clear();
				status.setText(String.format("目前功能:%s            魚數量:%d  烏龜數量:%d",buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle()));
			}
		});
		
		toppanel.add(newfish);
		toppanel.add(removeselected);
		toppanel.add(newturtle);
		toppanel.add(removeall);
		toppanel.add(status);
		
		add(toppanel,BorderLayout.NORTH);
	}
}