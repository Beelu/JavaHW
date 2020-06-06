import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class ChangeFrame extends JFrame{
	private static final String URL = "jdbc:mysql://localhost:3306/member?serverTimezone=UTC";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";
	private Connection connection;
	private PreparedStatement addpeople,peoplestate;
	
	private static final String[] types = {"cell","company","home"};
	private static final String[] groups = {"classmate","family","friend","undefined" };
	private JLabel typelabel,phonelabel,namelabel,grouplabel;
	private JComboBox<String> type,group;
	private JTextField phone,name;
	private JButton yes,no;

	public ChangeFrame(final ResultSetTableModel tableModel,int ID) {
		super("修改聯絡人");
		
		try 
	      {
	         connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	         addpeople = connection.prepareStatement("UPDATE people SET type=?,name=?,phone=?,Group1=? WHERE MemberID='" + ID + "'");
	         peoplestate = connection.prepareStatement("SELECT * FROM people WHERE MemberID ='"+ID+"'");
	         ResultSet resultset = peoplestate.executeQuery();
	         resultset.next();
	         phone = new JTextField(resultset.getString("phone"),10);
	         name = new JTextField(resultset.getString("name"),20);
	      }
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();
	         System.exit(1);
	      }
		
		typelabel = new JLabel("類型");
		namelabel = new JLabel("名字");
		phonelabel = new JLabel("電話號碼");
		phone = new JTextField(10);
		name = new JTextField(20);
		type = new JComboBox<String>(types);
		grouplabel = new JLabel("群組");
		group = new JComboBox<String>(groups);
		yes = new JButton("確定");
		no = new JButton("取消");
		
		setLayout(new GridLayout(5,2,4,4));
		add(typelabel);
		add(type);
		add(namelabel);
		add(name);
		add(phonelabel);
		add(phone);
		add(grouplabel);
		add(group);
		add(yes);
		add(no);
		
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typestr = (String)type.getSelectedItem();
				String namestr = name.getText();
				String phonestr = phone.getText();
				String groupstr = (String)group.getSelectedItem();
				
				try
				{
					addpeople.setString(1, typestr);
					addpeople.setString(2, namestr);
					addpeople.setString(3, phonestr);
					addpeople.setString(4, groupstr);
					
					addpeople.executeUpdate(); 
					
					tableModel.setQuery("SELECT name FROM people");
				}
				catch (SQLException sqlException)
			    {
			       sqlException.printStackTrace();
			    }
				catch (IllegalStateException e1) 
				{
					e1.printStackTrace();
				}
				
				ChangeFrame.this.dispose();
			}
		});
		
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeFrame.this.dispose();
			}
		});
	}
}
