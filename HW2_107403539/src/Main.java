//�D���O�{���é�J�e�O
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Point;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Main extends JFrame{
	private JComboBox<String> combobox;
	private static final String[] names = {"����","���u","����","�x��","�ꨤ�x��"};
	private JRadioButton small,middle,big;
	private ButtonGroup buttongroup;
	private JCheckBox fill;
	private JButton choosecolor,clear,eraser,undo;
	private Color color = Color.BLACK;
	private JLabel label1,label2,label3,statusLabel;
	private DrawPanel drawPanel;
	
	public Main(){
		super("�p�e�a");
		JPanel toppanel = new JPanel();
		
		/*******************************Combobox�ӧO*****************************************************/
		JPanel combopanel = new JPanel();
		combopanel.setLayout(new GridLayout(2,1));
		
		combobox = new JComboBox<String>(names);
		label1 = new JLabel("ø�Ϥu��");
		
		combopanel.add(label1);
		combopanel.add(combobox);
		
		//���X�ó]�w���ޭȡA�P�ɧP�_�p�G���e���h���i�Ŀ��
		combobox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
					drawPanel.setShapeType(combobox.getSelectedIndex());
				if(combobox.getSelectedIndex() == 0)
					fill.setEnabled(false);
				else
					fill.setEnabled(true);
			}
		});
		
		toppanel.add(combopanel);
		
		/*********************************Radiobutton�ӧO*********************************************/
		JPanel radiopanel = new JPanel();
		radiopanel.setLayout(new GridLayout(2,3));
		
		small = new JRadioButton("�p",true);
		middle = new JRadioButton("��",false);
		big = new JRadioButton("�j",false);
		label2 = new JLabel("����j�p");
		
		//�]�w�ƪ�
		radiopanel.add(label2);
		radiopanel.add(new JLabel(" "));
		radiopanel.add(new JLabel(" "));	
		radiopanel.add(small);
		radiopanel.add(middle);
		radiopanel.add(big);
		
		//�]�w�p���j���ʫ�
		small.addItemListener(new Radiobuttonhandler(3));
		middle.addItemListener(new Radiobuttonhandler(6));
		big.addItemListener(new Radiobuttonhandler(9));
		
		buttongroup = new ButtonGroup();
		buttongroup.add(small);
		buttongroup.add(middle);
		buttongroup.add(big);
		
		toppanel.add(radiopanel);

		/**************************************checkbox�ӧO***************************************************/
		JPanel checkpanel = new JPanel();
		checkpanel.setLayout(new GridLayout(2,1));
		
		//�]�w�ƪ��A�åB�N�@�}�l�]�w�����i���(�]���@�}�l�O����)
		label3 = new JLabel("��");
		fill = new JCheckBox(" ",false);
		fill.setEnabled(false);
		
		checkpanel.add(label3);
		checkpanel.add(fill);
		
		//�P�_�O�_�����Ĩæ^�ǭ�
		fill.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				drawPanel.setFilledShape(fill.isSelected());
			}
		});
		
		toppanel.add(checkpanel);
		
		
		/***********************************button�ӧO********************************************************/
		choosecolor = new JButton("�����C��");
		clear = new JButton("�M���e��");
		eraser = new JButton("�����");
		undo = new JButton("�W�@�B");
		
		//����C��button
		choosecolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(Main.this, "Choose the color", color);
				
				if(color == null)
					color = Color.BLACK;
				
				drawPanel.setDrawingColor(color);
			}
		});
		
		//�M���e��button
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == clear)
					drawPanel.clearDrawing();
			}
		});
		
		//�����button
		eraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPanel.setEraserSelect(1);
				drawPanel.setShapeType(0);
			}
		});
		
		//�W�@�Bbutton
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == undo)
			         drawPanel.clearLastShape();
			}
		});
		
		toppanel.add(choosecolor);
		toppanel.add(clear);
		toppanel.add(eraser);
		toppanel.add(undo);
		
		//�إ�XY�y��
		statusLabel = new JLabel("���Ц�m(0,0)");
		
		//�إߵe�O
		drawPanel = new DrawPanel(statusLabel);
		
		//�]�w�ƪ�
		add(toppanel, BorderLayout.NORTH);
		add(statusLabel, BorderLayout.SOUTH);
		add(drawPanel, BorderLayout.CENTER);
		
	}
		

	private class Radiobuttonhandler implements ItemListener {
		private int size;
	
		public Radiobuttonhandler(int s) {
			size = s;
		}
	
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				drawPanel.setLineSize(size);
			}
		}
	}
}
	
