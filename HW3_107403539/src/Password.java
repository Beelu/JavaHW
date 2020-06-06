//�b��:userID �K�X:password
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
		super("�n�J");
		
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
		login = new JButton("�o�G�̵n�J");
		dontLogin = new JButton("�D�o�G�̬d��");
		quit = new JButton("�h�X");
		
		//�n�J
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
		
		//�d��
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
		
		//�h�X
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
