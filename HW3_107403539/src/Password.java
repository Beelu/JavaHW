//帳號:userID 密碼:password
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.awt.*;

public class Password extends JFrame{
	private JButton login,dontLogin,quit;
	private JTextField userID,password;
	
	public Password() {
		super("登入");
		
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(3,1));
		JLabel space = new JLabel("");
		userID = new JTextField("Enter userID",20);
		password = new JTextField("Enter password",20);
		toppanel.add(space);
		toppanel.add(userID);
		toppanel.add(password);
		add(toppanel, BorderLayout.NORTH);
		
		JPanel underpanel = new JPanel();
		underpanel.setLayout(new BoxLayout(underpanel, BoxLayout.X_AXIS));
		login = new JButton("發佈者登入");
		dontLogin = new JButton("非發佈者查看");
		quit = new JButton("退出");
		
		//登入
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userID.getText().equals("userID") && password.getText().equals("password"))
				{
					try 
					{
						Main poster = new Main(0);
						poster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						poster.setSize(800, 500);
						poster.setVisible(true);
						setVisible(false);
					}
					catch(IOException ioe)
					{
						System.err.println("IOExpection error");
					}
				}
			}
		});
		
		//查看
		dontLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					Main poster = new Main(1);
					poster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					poster.setSize(800, 500);
					poster.setVisible(true);
					setVisible(false);
				}
				catch(IOException ioe)
				{
					System.err.println("IOExpection error");
				}
			}
		});
		
		//退出
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
		underpanel.add(login);
		underpanel.add(dontLogin);
		underpanel.add(quit);
		add(underpanel, BorderLayout.SOUTH);
	}

}
