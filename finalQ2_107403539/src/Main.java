import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends JFrame{
	private JButton newfish,newturtle,removeselected,removeall,cookiebutton,fishingbutton;
	private JLabel status = new JLabel("�ثe�\��:�s�W��            ���ƶq:0  �Q�t�ƶq:0 �}�Ƽƶq:0 ����ƶq:0 �w���쳽�ƶq:0 �w����Q�t�ƶq:0");
	private Aquarium underpanel = new Aquarium(status);
	private String buttonselected="�s�W��";
	private String statustring = "�ثe�\��:%s            ���ƶq:%d  �Q�t�ƶq:%d �}�Ƽƶq:%d ����ƶq:%d �w���쳽�ƶq: �w����Q�t�ƶq:";
	
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
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		newturtle = new JButton("�s�W�Q�t");
		newturtle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(2);
				buttonselected = "�s�W�Q�t";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		removeselected = new JButton("�������");
		removeselected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(3);
				buttonselected = "�������";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		removeall = new JButton("��������");
		removeall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.clear();
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		cookiebutton = new JButton("����");
		cookiebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(4);
				buttonselected = "����";
				status.setText(String.format(statustring,buttonselected,underpanel.gettotalfish(),underpanel.gettotalturtle(),underpanel.gettotalcookie(),underpanel.gettotalfishing()));
			}
		});
		
		fishingbutton = new JButton("����");
		fishingbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				underpanel.setButtonselected(5);
				buttonselected = "����";
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