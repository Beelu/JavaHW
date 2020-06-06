//主面板程式並放入畫板
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
	private static final String[] names = {"筆刷","直線","橢圓形","矩形","圓角矩形"};
	private JRadioButton small,middle,big;
	private ButtonGroup buttongroup;
	private JCheckBox fill;
	private JButton choosecolor,clear,eraser,undo;
	private Color color = Color.BLACK;
	private JLabel label1,label2,label3,statusLabel;
	private DrawPanel drawPanel;
	
	public Main(){
		super("小畫家");
		JPanel toppanel = new JPanel();
		
		/*******************************Combobox個別*****************************************************/
		JPanel combopanel = new JPanel();
		combopanel.setLayout(new GridLayout(2,1));
		
		combobox = new JComboBox<String>(names);
		label1 = new JLabel("繪圖工具");
		
		combopanel.add(label1);
		combopanel.add(combobox);
		
		//取出並設定索引值，同時判斷如果為畫筆則不可勾選填滿
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
		
		/*********************************Radiobutton個別*********************************************/
		JPanel radiopanel = new JPanel();
		radiopanel.setLayout(new GridLayout(2,3));
		
		small = new JRadioButton("小",true);
		middle = new JRadioButton("中",false);
		big = new JRadioButton("大",false);
		label2 = new JLabel("筆刷大小");
		
		//設定排版
		radiopanel.add(label2);
		radiopanel.add(new JLabel(" "));
		radiopanel.add(new JLabel(" "));	
		radiopanel.add(small);
		radiopanel.add(middle);
		radiopanel.add(big);
		
		//設定小中大的粗度
		small.addItemListener(new Radiobuttonhandler(3));
		middle.addItemListener(new Radiobuttonhandler(6));
		big.addItemListener(new Radiobuttonhandler(9));
		
		buttongroup = new ButtonGroup();
		buttongroup.add(small);
		buttongroup.add(middle);
		buttongroup.add(big);
		
		toppanel.add(radiopanel);

		/**************************************checkbox個別***************************************************/
		JPanel checkpanel = new JPanel();
		checkpanel.setLayout(new GridLayout(2,1));
		
		//設定排版，並且將一開始設定為不可更改(因為一開始是筆刷)
		label3 = new JLabel("填滿");
		fill = new JCheckBox(" ",false);
		fill.setEnabled(false);
		
		checkpanel.add(label3);
		checkpanel.add(fill);
		
		//判斷是否有打勾並回傳值
		fill.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				drawPanel.setFilledShape(fill.isSelected());
			}
		});
		
		toppanel.add(checkpanel);
		
		
		/***********************************button個別********************************************************/
		choosecolor = new JButton("筆刷顏色");
		clear = new JButton("清除畫面");
		eraser = new JButton("橡皮擦");
		undo = new JButton("上一步");
		
		//選擇顏色button
		choosecolor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(Main.this, "Choose the color", color);
				
				if(color == null)
					color = Color.BLACK;
				
				drawPanel.setDrawingColor(color);
			}
		});
		
		//清除畫面button
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == clear)
					drawPanel.clearDrawing();
			}
		});
		
		//橡皮擦button
		eraser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPanel.setEraserSelect(1);
				drawPanel.setShapeType(0);
			}
		});
		
		//上一步button
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
		
		//建立XY座標
		statusLabel = new JLabel("指標位置(0,0)");
		
		//建立畫板
		drawPanel = new DrawPanel(statusLabel);
		
		//設定排版
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
	
