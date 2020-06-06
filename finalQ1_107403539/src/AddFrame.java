import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class AddFrame extends JFrame{
	private static final String URL = "jdbc:mysql://localhost:3306/member?serverTimezone=UTC";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";
	private List<String> namelist;
	private Connection connection;
	private PreparedStatement addpeople,getAll;
	
	private static final String[] types = {"cell","company","home"};
	private static final String[] groups = {"classmate","family","friend","undefined" };
	private JLabel typelabel,phonelabel,namelabel,grouplabel;
	private JComboBox<String> type,group;
	private JTextField phone,name;
	private JButton yes,no;

	public AddFrame(final ResultSetTableModel tableModel) {
		super("增加聯絡人");
		
		try 
	      {
	         connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	         addpeople = connection.prepareStatement("INSERT INTO people (type,name,phone,Group1) VALUES(?,?,?,?)");
	         getAll = connection.prepareStatement("SELECT * FROM people");
	         namelist = getAllname();
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
				
				boolean b = true;
				
				//電話號碼防呆
				if(type.getSelectedIndex()==0)
				{
					if(phonestr.charAt(0)!='0' || phonestr.charAt(1)!='9' || phonestr.length()!=10) {
						JOptionPane.showMessageDialog(null, "輸入不符合規定!請輸入09開頭 且 10個數字", "格式不符", JOptionPane.WARNING_MESSAGE);
						b = false;
					}
					else
						b = true;
				}
				else
				{
					if(phonestr.charAt(0)!='0' || phonestr.length()>10 || phonestr.length()<9) {
						JOptionPane.showMessageDialog(null, "輸入不符合規定!請輸入0開頭 且 10或9個數字", "格式不符", JOptionPane.WARNING_MESSAGE);
						b = false;
					}
					else
						b = true;
				}
				
				if(namelist.contains(namestr))
				{
					JOptionPane.showMessageDialog(null, "請勿輸入重複名稱，可改用小名或匿名", "名稱重複", JOptionPane.WARNING_MESSAGE);
					b = false;
				}
				
				if(b)
				{
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
					
					AddFrame.this.dispose();
				}
			}
		});
		
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFrame.this.dispose();
			}
		});
	}
	
	public List<String> getAllname()
	   {
	      List<String> results = null;
	      ResultSet resultSet = null;
	      
	      try 
	      {
	         // executeQuery returns ResultSet containing matching entries
	         resultSet = getAll.executeQuery(); 
	         results = new ArrayList<String>();
	         
	         while (resultSet.next())
	         {
	            results.add(resultSet.getString("name"));
	         } 
	      } 
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();         
	      }
	      
	      return results;
	   }
}
