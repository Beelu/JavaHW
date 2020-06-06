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
		super("���i��");		

		//�NpostŪ�J��ϥ�PostSerializable�ǦC���নpost��g�JJLable�MJTextArea
		try
	      {
			//�N�ɶ���ܤ覡�ഫ�榡
			edittimecontext.setTime(Files.getLastModifiedTime(path).toMillis());
			
			//�Npost�����eŪ�J���i�ϩM�s��ɶ��M�R��
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
	
		/****************************��@�D���O****************************/
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
		
		/*****************************��@�W��C**************************/
		toppanel = new JPanel();
		toppanel.setBackground(Color.GREEN);
		toppanel.setLayout(new GridLayout(2,1));
		
		title = new JLabel("�iJA");
		title.setFont(new Font("Serif", Font.BOLD, 28));
		title.setForeground(Color.WHITE);
		edittime.setForeground(Color.WHITE);
		
		toppanel.add(title);
		toppanel.add(edittime);
		add(toppanel, BorderLayout.NORTH);
		
		/****************************��@�U��C***************************/
		underpanel = new JPanel();
		underpanel.setBackground(Color.ORANGE);
		underpanel.setLayout(new BoxLayout(underpanel, BoxLayout.X_AXIS));	
		
		//�R�߫��s�P�_�Olike�٬Ounlike
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
				
				//�N�o�����R�߭��x�s��post�ɮפU����
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
		
		//�P�_�O�_���s��̡A�Y�O�hdisable
		if(response == 0)
			likebutton.setEnabled(false);
		else
			likebutton.setEnabled(true);
		
		underpanel.add(likebutton);	
		
		//�U��C�ƪ����j
		underpanel.add(Box.createRigidArea(d));
		
		//�s����s
		edit = new JButton("�s��");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pressEdit(content1);
			}
		});
		
		//�s�K����s
		newpost = new JButton("���s�K��");
		newpost.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pressEdit("");
			}
		});
		
		//�Y�O�o�G�̤~��ܽs����s�M���s�K����s
		if(response==0)
			{
			underpanel.add(edit);
			underpanel.add(newpost);
			}
	
		//�x�s���s
		save = new JButton("�x�s");
		save.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){						
						try 
						{
							//�R���ܦ^unlike
							likebutton.setIcon(getunlike());
							islike = false;
							
							//�N�s��Ϥ�rŪ�X�ǦC�ƨ�post��
							content1 = textarea.getText();
							ObjectOutputStream ouput = new ObjectOutputStream(Files.newOutputStream(Paths.get("src\\post"))); 
							PostSerializable ps = new PostSerializable(content1, islike, edittimecontext);
							ouput.writeObject(ps);
							ouput.flush();
							ouput.close();
					
				            text.setText(ps.getContent());
				            
				            //�Npost.txt����Ƥ@�íק�A���i����
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
			            
						//�����x�s�᪺�ק�ɶ����]
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
		
		//�t�s���e���s
		newsave = new JButton("�t�s���e");
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
		
		//�פJ���e���s
		impo = new JButton("�פJ���e");
		impo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.CANCEL_OPTION)
					System.exit(1);
				Path choosepath = fileChooser.getSelectedFile().toPath();
				
				//�T�{����&�ɮצs�b
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
		
		//�������s
		cancel = new JButton("����");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
		underpanel.add(cancel);
		cancel.setVisible(false);
		
		add(underpanel, BorderLayout.SOUTH);
	}
	
	/**************************************���s���s�觹������***********************************/
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

		//�s���r��
		textarea.setVisible(true);
		mainpanel.setBackground(Color.WHITE);
		textarea.setEditable(true);
		textarea.setText(s);
	}
	
	//�x�s�Ψ����ɪ�^
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
		
		//��r��ܰ�
		textarea.setVisible(false);
		mainpanel.setBackground(Color.GREEN);
	}
	
	//���A�R��(�վ�j�p)
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
