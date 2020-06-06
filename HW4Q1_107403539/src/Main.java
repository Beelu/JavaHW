import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Main extends JFrame{
	private JLabel  message;
	private JTextField enternumber;
	private JButton confirm,cancel;
	
	public Main()
	{
		super("列出所有有3或是3之倍數的數字");
		
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(2,1));
		
		message = new JLabel("請輸入一個正整數");
		toppanel.add(message);
		
		enternumber = new JTextField(10);
		toppanel.add(enternumber);
		
		JPanel underpanel = new JPanel();
		underpanel.setLayout(new BoxLayout(underpanel, BoxLayout.X_AXIS));
		
		confirm = new JButton("確認");
		//實作按鈕功能
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Set<Integer> set = new HashSet<>();
				List<Integer> list = new LinkedList<>();
				
				//將合要求的數字放入hashset中這樣就不會重複
				try
				{
					int number = Integer.valueOf(enternumber.getText());
					
					String str3=Integer.toString(3);
					for(int i=0;i<=number;i++)
					{
						String s=Integer.toString(i);
						boolean b=s.contains(str3);
						if(b)
							set.add(i);
						
						if(i%3==0&&i!=0)
							set.add(i);
					}
					
					for(int i:set)
						list.add(i);
					
					ListIterator<Integer> iterator = list.listIterator();
					Collections.sort(list);
					
					String content = "";
					if(!set.isEmpty()) {
						content += iterator.next();
						while(iterator.hasNext()) {
							content += ", " + iterator.next();
						}
					}
				
					if(!set.isEmpty())
						JOptionPane.showMessageDialog(null, content, enternumber.getText(), JOptionPane.PLAIN_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "我什麼都沒有QQ", enternumber.getText(), JOptionPane.PLAIN_MESSAGE);
				}
				catch (NumberFormatException nfe)
				{
					System.err.println("請輸入正整數");
				}
			}
		});
		underpanel.add(confirm);
		
		cancel = new JButton("取消");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		underpanel.add(cancel);
		
		add(toppanel, BorderLayout.NORTH);
		add(underpanel, BorderLayout.SOUTH);
	}
}
