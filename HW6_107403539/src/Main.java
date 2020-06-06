import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	private JButton newfish,newturtle,removeselected,removeall;
	private JLabel status = new JLabel("�ثe�\��:�s�W��            ���ƶq:0  �Q�t�ƶq:0");
	private Aquarium underpanel = new Aquarium(status);
	private String buttonselected="�s�W��";
	
	public Main() {
		super("���ڽc");
		
		//�U���O(���ڽc)
		underpanel.setBackground(Color.CYAN);
		add(underpanel, BorderLayout.CENTER);
		
		//�W���O(���s)
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(3,2));
		
		newfish = new JButton("�s�W��");
		newfish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(1);
				buttonselected = "�s�W��";
				status.setText(String.format("�ثe�\��:�s�W��            ���ƶq:%d  �Q�t�ƶq:%d",underpanel.gettotalfish(),underpanel.gettotalturtle()));
			}
		});
		
		newturtle = new JButton("�s�W�Q�t");
		newturtle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(2);
				buttonselected = "�s�W�Q�t";
				status.setText(String.format("�ثe�\��:�s�W�Q�t            ���ƶq:%d  �Q�t�ƶq:%d",underpanel.gettotalfish(),underpanel.gettotalturtle()));
			}
		});
		
		removeselected = new JButton("�������");
		removeselected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(3);
				status.setText(String.format("�ثe�\��:�������            ���ƶq:%d  �Q�t�ƶq:%d",underpanel.gettotalfish(),underpanel.gettotalturtle()));
			}
		});
		
		removeall = new JButton("��������");
		removeall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.clear();
				status.setText(String.format("�ثe�\��:%s            ���ƶq:%d  �Q�t�ƶq:%d",buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle()));
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