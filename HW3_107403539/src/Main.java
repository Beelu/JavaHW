import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements Serializable{
	private JLabel title,edittime;
	private String content1="";
	private JTextArea text,textarea = new JTextArea(content1,13,75);
	private JButton likebutton, edit,newpost,save,newsave,impo,cancel;
	private Date edittimecontext = new Date();
	private JPanel mainpanel, toppanel, underpanel;
	private Dimension d = new Dimension(550,50);
	private JFileChooser fileChooser = new JFileChooser();
	private static Scanner input;
	private Path path = Paths.get("src\\post");
	private boolean islike = false;
	
	public Main(int response) throws IOException
	{	
		super("公告欄");		

		//將post讀入後使用PostSerializable序列化轉成post後寫入JLable和JTextArea
		try
	      {
			//將時間顯示方式轉換格式
			edittimecontext.setTime(Files.getLastModifiedTime(path).toMillis());
			
			//將post的內容讀入公告區和編輯時間和愛心
			ObjectInputStream input = new ObjectInputStream(Files.newInputStream(Paths.get("src\\post"))); 
			PostSerializable ps = (PostSerializable)input.readObject();
    		edittime = new JLabel(ps.getEditTime().toString()); 
    		islike = ps.getIsLike();
    		text = new JTextArea(ps.getContent(),13,60);
    		content1 = ps.getContent();
    		
            input.close(); 
	      }
		catch(IOException i)
	      {
			System.err.println("IOException error");
	      }
		catch(ClassNotFoundException cnfe)
		{
			System.err.println("ClassNotFoundException error");
		}
	
		/****************************實作主面板****************************/
		mainpanel = new JPanel();
		mainpanel.setBackground(Color.GREEN);
		
		text.setForeground(Color.YELLOW);
		text.setFont(new Font("Serif", Font.BOLD, 14));
		text.setOpaque(false);
		text.setBorder(BorderFactory.createEmptyBorder());
		text.setEditable(false);
		
		textarea.setOpaque(false);
		textarea.setBorder(BorderFactory.createEmptyBorder());
		
		mainpanel.add(text);
		mainpanel.add(textarea);
		add(mainpanel);
		
		/*****************************實作上行列**************************/
		toppanel = new JPanel();
		toppanel.setBackground(Color.GREEN);
		toppanel.setLayout(new GridLayout(2,1));
		
		title = new JLabel("進JA");
		title.setFont(new Font("Serif", Font.BOLD, 28));
		title.setForeground(Color.WHITE);
		edittime.setForeground(Color.WHITE);
		
		toppanel.add(title);
		toppanel.add(edittime);
		add(toppanel, BorderLayout.NORTH);
		
		/****************************實作下行列***************************/
		underpanel = new JPanel();
		underpanel.setBackground(Color.ORANGE);
		underpanel.setLayout(new BoxLayout(underpanel, BoxLayout.X_AXIS));	
		
		//愛心按鈕判斷是like還是unlike
		if(islike)
			likebutton = new JButton(getlike());
		else
			likebutton = new JButton(getunlike());
		likebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(islike)
				{
					likebutton.setIcon(getunlike());
					islike = false;
				}
				else 
				{
					likebutton.setIcon(getlike());
					islike = true;
				}
				
				//將這次的愛心值儲存到post檔案下次拿
				try 
				{
					ObjectOutputStream ouput = new ObjectOutputStream(Files.newOutputStream(Paths.get("src\\post"))); 
					PostSerializable ps = new PostSerializable(content1, islike, edittimecontext);
					ouput.writeObject(ps);
					ouput.flush();
					ouput.close();
				}
				catch(IOException i)
			    {
			         i.printStackTrace();
			         return;
			    }
			}
		});
		likebutton.setContentAreaFilled(false);
		likebutton.setFocusPainted(false);
		likebutton.setBorderPainted(false);	
		
		//判斷是否為編輯者，若是則disable
		if(response == 0)
			likebutton.setEnabled(false);
		else
			likebutton.setEnabled(true);
		
		underpanel.add(likebutton);	
		
		//下行列排版間隔
		underpanel.add(Box.createRigidArea(d));
		
		//編輯按鈕
		edit = new JButton("編輯");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pressEdit(content1);
			}
		});
		
		//新貼文按鈕
		newpost = new JButton("全新貼文");
		newpost.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pressEdit("");
			}
		});
		
		//若是發佈者才顯示編輯按鈕和全新貼文按鈕
		if(response==0)
			{
			underpanel.add(edit);
			underpanel.add(newpost);
			}
	
		//儲存按鈕
		save = new JButton("儲存");
		save.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){						
						try 
						{
							//愛心變回unlike
							likebutton.setIcon(getunlike());
							islike = false;
							
							//將編輯區文字讀出序列化到post內
							content1 = textarea.getText();
							ObjectOutputStream ouput = new ObjectOutputStream(Files.newOutputStream(Paths.get("src\\post"))); 
							PostSerializable ps = new PostSerializable(content1, islike, edittimecontext);
							ouput.writeObject(ps);
							ouput.flush();
							ouput.close();
					
				            text.setText(ps.getContent());
				            
				            //將post.txt的資料一並修改，其實可不用
				            FileWriter fw = new FileWriter("src\\post.txt");
				            Scanner s = new Scanner(textarea.getText());
				            String str = "";
				            while(s.hasNext()) {
				            	str += s.nextLine() + "\r\n";
				            }
							fw.write(str);
							fw.close();
							s.close();
						}						
						catch(IOException ioex) {
							System.err.println("Error IOException.");
						}
			            
						//按完儲存後的修改時間重設
			            try
			            {
			            	edittimecontext.setTime(Files.getLastModifiedTime(path).toMillis());
			            	edittime.setText(edittimecontext.toString());
			            }
			            catch(IOException ioexception)
						{
							System.err.println("Error IOException.");
						}
						
			            back();
					}
		});
		underpanel.add(save);
		save.setVisible(false);
		
		//另存內容按鈕
		newsave = new JButton("另存內容");
		newsave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.CANCEL_OPTION)
					System.exit(1);
				
				if (fileChooser.getSelectedFile() != null) {
					try 
					{
						FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
						fw.write(textarea.getText());
						fw.close();
					}				
					catch(IOException ioexception)
					{
						System.err.println("Error IOException.");
					}
				}
			}
		});
		underpanel.add(newsave);
		newsave.setVisible(false);
		
		//匯入內容按鈕
		impo = new JButton("匯入內容");
		impo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.CANCEL_OPTION)
					System.exit(1);
				Path choosepath = fileChooser.getSelectedFile().toPath();
				
				//確認有選&檔案存在
				if (choosepath != null && Files.exists(choosepath)) {
					try
				      {
				         input = new Scanner(choosepath); 
				      } 
				      catch (IOException ioException)
				      {
				         System.err.println("Error opening file. Terminating.");
				         System.exit(1);
				      } 
				}
				
				String importstring = "";
				try
				{
					while (input.hasNext()) 
			        {
						importstring+=input.nextLine() + "\n";
			            textarea.setText(importstring);
			        }
				}
				catch (NoSuchElementException elementException)
			    {
			       System.err.println("File improperly formed. Terminating.");
			    } 
			    catch (IllegalStateException stateException)
			    {
			       System.err.println("Error reading from file. Terminating.");
			    } 
			}
			
		});
		underpanel.add(impo);
		impo.setVisible(false);
		
		//取消按鈕
		cancel = new JButton("取消");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		underpanel.add(cancel);
		cancel.setVisible(false);
		
		add(underpanel, BorderLayout.SOUTH);
	}
	
	/**************************************按編輯跟編輯完的切換***********************************/
	public void pressEdit(String s) {
		likebutton.setVisible(false);
		edit.setVisible(false);
		newpost.setVisible(false);
		text.setVisible(false);
		save.setVisible(true);
		newsave.setVisible(true);
		impo.setVisible(true);
		cancel.setVisible(true);
		d.setSize(50, 50);

		//編輯文字版
		textarea.setVisible(true);
		mainpanel.setBackground(Color.WHITE);
		textarea.setEditable(true);
		textarea.setText(s);
	}
	
	//儲存或取消時返回
	public void back() {
		likebutton.setVisible(true);
		edit.setVisible(true);
		newpost.setVisible(true);
		text.setVisible(true);
		save.setVisible(false);
		newsave.setVisible(false);
		impo.setVisible(false);
		cancel.setVisible(false);
		d.setSize(550, 50);
		
		//文字顯示區
		textarea.setVisible(false);
		mainpanel.setBackground(Color.GREEN);
	}
	
	//給你愛心(調整大小)
	public static ImageIcon getlike() {
		try
		{
			ImageIcon like = new ImageIcon(ImageIO.read(new File("src\\like.png")));  
			Image image = like.getImage(); 
			Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);  
			like = new ImageIcon(newimg);
			
			return like;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
	
	public static ImageIcon getunlike() {
		try
		{
			ImageIcon unlike = new ImageIcon(ImageIO.read(new File("src\\unlike.png")));  
			Image image = unlike.getImage(); 
			Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_AREA_AVERAGING);  
			unlike = new ImageIcon(newimg);
			
			return unlike;
		}
		catch (IOException e)
		{
		      e.printStackTrace();
		}
		return null;
	}
}
