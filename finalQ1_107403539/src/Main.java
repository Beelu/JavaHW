//主要顯示版面 實作各功能方法並呼叫其他class
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Main extends JFrame{
	private JLabel title = new JLabel("Contacts");
	private JButton add,searchbutton;
	private JTextField search = new JTextField(10);
	private JComboBox<String> group;
	private static final String[] groups = {"undefined","classmate","family","friend"};
	
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/member?serverTimezone=UTC";
	private static final String USERNAME = "java";
	private static final String PASSWORD = "java";
	private static final String DEFAULT_QUERY = "SELECT name FROM people";  
	private Connection connection;
	private Statement statement;
	private PreparedStatement delectpeople,getInformation,changepeople;
	private static ResultSetTableModel tableModel;
	private JTable peopletable;
	
	public Main() {
		super("通訊錄");
		
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new GridLayout(2,3,4,4));
		
		group = new JComboBox<String>(groups);
		add = new JButton(getadd());
		add.setContentAreaFilled(false);
		add.setFocusPainted(false);
		add.setBorderPainted(false);	
		searchbutton = new JButton("search");
		title.setFont(new Font("Serif", Font.BOLD, 20));
		
		try 
		{
			connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			statement = connection.createStatement();
			delectpeople = connection.prepareStatement("DELETE FROM people WHERE MemberID=?");
			getInformation = connection.prepareStatement("SELECT * FROM people WHERE name=?");
			changepeople = connection.prepareStatement("UPDATE people SET Group1='undefined' WHERE MemberID=?");
			setGroup();
			tableModel = new ResultSetTableModel(DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		peopletable = new JTable(tableModel);
		peopletable.setRowHeight(30);
		
		//選擇群組事件
		group.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					String s = (String)group.getSelectedItem();
					String searchtext = "SELECT name FROM people WHERE Group1='" + s + "'";
					
					try 
	            	  {
						tableModel.setQuery(searchtext);
	            	  } 
	            	  catch (SQLException sqlException)
	                  {
	                     sqlException.printStackTrace();
	                  }
				}
			}
		});
		
		//雙擊cell事件
		peopletable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					//先取得點擊欄位中的名字
					int row = peopletable.rowAtPoint(e.getPoint());
					int col = peopletable.columnAtPoint(e.getPoint());
					String s = (String)peopletable.getValueAt(row, col);
					int ID = 0;
					String name="";
					String type="";
					String phone="";
					String group="";
					
					//取得資料
					try 
					{
						getInformation.setString(1, s);
						ResultSet resultSet = getInformation.executeQuery();
						resultSet.next();
						ID = resultSet.getInt("MemberID");
						name = resultSet.getString("name");
						type = resultSet.getString("type");
						phone = resultSet.getString("phone");
						group = resultSet.getString("Group1");
					}
					catch (SQLException sqlException)
				    {
				       sqlException.printStackTrace();
				    } 
					
					//詢問要刪除還是修改
					String[] option = {"修改","刪除","取消"};
					String information = "用戶名稱:"+name+"\n用戶類型:"+type+"\n用戶電話號碼:"+phone+"\n用戶群組:"+group+"\n\n要修改還是刪除?";
					int response = JOptionPane.showOptionDialog(null, information, "詳細資訊", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
					if(response==0)
					{
						ChangeFrame cf = new ChangeFrame(tableModel,ID);
						cf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						cf.setSize(400, 300);
						cf.setVisible(true);
					}
					else if(response==1)
					{
						try 
						{
							delectpeople.setInt(1, ID);
							delectpeople.executeUpdate();
							tableModel.setQuery("SELECT name FROM people");
						} 
						catch (SQLException e1) 
						{
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		//新增按鈕功能實作
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddFrame addframe = new AddFrame(tableModel);
				addframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addframe.setSize(400, 300);
				addframe.setVisible(true);			
			}
		});
		
		//搜尋按鈕功能實作
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
	    peopletable.setRowSorter(sorter);
	    searchbutton.addActionListener(           
	            new ActionListener() 
	            {
	               public void actionPerformed(ActionEvent e) 
	               {
	            	  String searchtext = "SELECT name FROM people WHERE phone LIKE '%" + search.getText() + "%' OR name LIKE '%" + search.getText() + "%' AND Group1='"+ (String)group.getSelectedItem()+"'";
	            	  
	            	  try 
	            	  {
						tableModel.setQuery(searchtext);
	            	  } 
	            	  catch (SQLException sqlException)
	                  {
	                     sqlException.printStackTrace();
	                  }
	               } 
	            } 
	         ); 
		
		toppanel.add(title);
		toppanel.add(group);
		toppanel.add(add);
		
		toppanel.add(search);
		toppanel.add(searchbutton);
		add(toppanel, BorderLayout.NORTH);
		
		JPanel underpanel = new JPanel();
	
		underpanel.add(new JScrollPane(peopletable));
		add(underpanel, BorderLayout.CENTER);
	}
	
	public static ImageIcon getadd() {
		try
		{
			ImageIcon add = new ImageIcon(ImageIO.read(new File("src\\1.jpg")));  
			Image image = add.getImage(); 
			Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_AREA_AVERAGING);  
			add = new ImageIcon(newimg);
			
			return add;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	
	public void setGroup() {
		List< Person > results = null;
	    ResultSet resultSet = null;
	    
	    try 
	      {
	         resultSet = statement.executeQuery("SELECT * FROM people"); 
	         results = new ArrayList< Person >();
	         
	         while (resultSet.next())
	         {
	            results.add(new Person(
	               resultSet.getInt("MemberID"),
	               resultSet.getString("name"),
	               resultSet.getString("type"),
	               resultSet.getString("phone"),
	               resultSet.getString("Group1")));
	         } 
	      } 
	      catch (SQLException sqlException)
	      {
	         sqlException.printStackTrace();         
	      } 
	    
	    //FP
	    results.stream()
	    	   .forEach(person -> {
				try {
					changepeople.setInt(1, person.getMemberID());
					
					changepeople.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
	}
}
