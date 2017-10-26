      //登录界面自己设计的
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.*;

class index
{
	public static void main(String args[])
	{
		new WindowBox();//登陆界面
	}
}

class WindowBox extends JFrame
{
	Box baseBox,boxV1,boxV2,boxV3;
	JTextField userText = new JTextField("",16);
	JPasswordField pwdText = new JPasswordField(16);
	JButton dengButton = new JButton(" enter ");
	JButton zhuce = new JButton(" log in ");
	WindowBox()
	{
		boxV1=Box.createVerticalBox();
		boxV1.add(new JLabel("userid"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV1.add(new JLabel("password"));
		boxV1.add(Box.createVerticalStrut(8));
		boxV2=Box.createVerticalBox();
		boxV2.add(userText);
		boxV2.add(Box.createVerticalStrut(8));
		boxV2.add(pwdText);
		boxV2.add(Box.createVerticalStrut(8));
		boxV3=Box.createVerticalBox();
		boxV3.add(dengButton);
		boxV3.add(zhuce);
		baseBox=Box.createHorizontalBox();
		baseBox.add(boxV1);
		baseBox.add(Box.createHorizontalStrut(10));
		baseBox.add(boxV2);
		baseBox.add(boxV3);
		
		setLayout(new FlowLayout());
		add(baseBox);
		validate();
		dengButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String userid = userText.getText();
				char[] userpwd = pwdText.getPassword();
				denglu(userid,userpwd);
            }
		});
		zhuce.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
                //System.out.println("hello");
				String userid = userText.getText();
				char[] userpwd = pwdText.getPassword();
				logimg(userid,userpwd);
            }
		});
		setBounds(80,80,400,200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	PrintWriter pw=null;
	BufferedReader br=null;
	//JFrame frame;
	public void denglu(String id,char[] pwd) 
	{
		String stpwd = new String(pwd);
		String str;
		if(id.equals("") || stpwd.equals(""))
		{
			JOptionPane.showMessageDialog(this,"\'id\' and \'password\' must not null","imformation error",JOptionPane.YES_NO_OPTION);
			return;
		}
		str="%login%"+":"+id+":"+stpwd;
		try
		{
			Socket s = new Socket("127.0.0.1",8989);
		    pw=new PrintWriter(s.getOutputStream());
			pw.println(str);
		    pw.flush();//发送账号密码
			
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));//接收回送的信息
			String getimf=br.readLine();
			if(getimf.indexOf("%true%")==0)
			{
				//JOptionPane.showMessageDialog(this,"great","imformation",JOptionPane.YES_NO_OPTION);
				new ShowFriends(s,getimf,pw,br);
				dispose();
				return;
			}
			JOptionPane.showMessageDialog(this,"\'id\' or \'password\' is not good","enter error",JOptionPane.YES_NO_OPTION);
			
		}
		catch(UnknownHostException ee)
		{ee.printStackTrace();}
		catch(IOException eee)
		{eee.printStackTrace();}
		System.out.println(id+"  "+stpwd);
	}
	public void logimg(String id,char[] pwd)
	{
		String stpwd = new String(pwd);
		System.out.println(id+"  "+stpwd);
		
	}
}


class Talking extends JFrame
{
	JTextArea ta;
	JLabel label;
	private PrintWriter pw;
	String old;
	String yonghuming;
	private String name;
    private String duixiang;
	
	public Talking(PrintWriter p1w,String str,String aa,String yonghu,String old)
	{
		this.pw=p1w;
		this.old=old;
		this.yonghuming=yonghu;
		this.name=str;
		this.duixiang=aa;
		
		
		JPanel jp=new JPanel(new GridLayout(2,1));	
        label=new JLabel("显示内容");
        jp.add(label);//把label加入了panel面板

        JPanel jp1=new JPanel(new GridLayout(2,1));
        ta=new JTextArea(3,6);//new一个多行输入框，指定 行数和列数分别为3,6
        jp1.add(ta);

        JPanel jp2=new JPanel(new FlowLayout());
        JButton but1=new JButton("发送");
        JButton but2=new JButton("清除");
		JButton but3=new JButton("清空");
        jp2.add(but1);
        jp2.add(but2);
		jp2.add(but3);
    
        jp1.add(jp2);
        jp.add(jp1);
		but1.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try
				{
					pw.println("%send%"+duixiang+":"+name+":"+ta.getText()+":"+yonghuming);
					
					BufferedReader br1=new BufferedReader(new StringReader(label.getText()));
                    String str1=br1.readLine();//读取第一行
					label.setText("<html>"+name+":"+ta.getText()+"<br>"+str1+"</html>");
		            pw.flush();
		            ta.setText("");
				}
				catch(IOException ee)
				{System.out.println(ee);}
				
                //System.out.println("hello");
            }
		});
		but2.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
                System.out.println("goodbye");
				ta.setText("");
            }
		});
		but3.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
                label.setText("");
            }
		});
		
        setContentPane(jp);
        setSize(500, 600);
        setTitle("正在与"+aa+"聊天");
        setVisible(true);
		setLab(old);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void setLab(String str){
		try{
			
			BufferedReader br1=new BufferedReader(new StringReader(label.getText()));
            String str1=br1.readLine();//读取第一行
		
		    label.setText("<html>"+str+"<br>"+str1+"</html>");
		}
		catch(IOException ee){System.out.println(ee);}
		
	}
	public void setVis(){
		setVisible(true);
	}
	
}

class ShowFriends implements ActionListener
{
	private Socket s;
	String str=null;
	String[] inf=new String[5];
	JFrame frame;
	JButton[] button ;
	JPanel panel1,panel2;
	static int i=0;
	PrintWriter pw=null;
	BufferedReader br=null;
	public ShowFriends(Socket s,String str,PrintWriter pw,BufferedReader br)
	{
		this.s=s;
		this.str=str;
		inf=str.split(":");
		this.pw=pw;
		this.br=br;
		
		panel1=new JPanel();
		panel2=new JPanel();
		String[] o=inf[4].split(",");
		frame=new JFrame(inf[3]+"<"+inf[1]+">   please select a friend to chart");
		button =new JButton[o.length];
		for(int i=0;i<o.length;i++)
		{
			button[i]=new JButton(o[i]);
			button[i].setBackground(Color.WHITE);
			button[i].addActionListener(this);
			panel2.add(button[i],BorderLayout.CENTER);
		}
		frame.add(panel1,BorderLayout.NORTH);
		frame.add(panel2,BorderLayout.CENTER);
		
		WriteWordThread one;
		one = new WriteWordThread("haha",1);
		one.start();
		
		frame.setVisible(true);
		frame.setLocation(100,100);
		frame.setSize(500,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	String al=null;
	String old =null;
	private String[] fr=new String[100];
	//private ChatFrame[] obj=new ChatFrame[100];
	private Talking[] obj=new Talking[100];
	
	public void actionPerformed(ActionEvent e) 
	{
		JButton button=(JButton)e.getSource();
		al=button.getText();
		for(int j=0;j<i;j++)
		{
			if(al.equals(fr[j])){
				obj[j].setVis();
				return;
			}
		}
		//ChatFrame cf=new ChatFrame(pw,br,inf[3],al,inf[1],old);
		Talking cf=new Talking(pw,inf[3],al,inf[1],old);
		
		obj[i]=cf;
		fr[i]=al;
		i++;
		button.setBackground(Color.WHITE);
		old=null;
		System.out.println("ok");
	}
	
	class WriteWordThread extends Thread
    {
		String abs = null;
	    int b = 0;
	    WriteWordThread(String s,int b)
	    {
			this.abs = s;
		    this.b = b;
	    }
		public void run()
		{
			loop:
			while(true)
			{
				try
				{
					str=br.readLine();
					if(str!=null)
					{
						String str1=str.split("%receive%")[1];
						String[] msm=new String[3];
						msm=str1.split(":");
						for(int j=0;j<i;j++)
						{
							if(msm[2].equals(fr[j]))
							{
								obj[j].setLab(msm[0]+":"+msm[1]+"\n");
								obj[j].setVis();
								continue loop;
							}
						}
						for(int n=0;n<button.length;n++)
						{
							if(button[n].getText().equals(msm[2]))
							{
								button[n].setBackground(Color.BLUE);break;
							}
						}
						old=msm[0]+":"+msm[1]+"\n";
					}
				}
				catch(IOException eeee)
				{
					eeee.printStackTrace();
				}
			}
		}
	}
}

/////////////////////////////////////////////////////////////////////////////



/*    自己设计的类
class ShowFriends implements ActionListener
{
	private Socket s;
	String str=null;
	String[] inf=new String[5];
	JFrame frame;
	JButton[] button ;
	JPanel panel1,panel2;
	public ShowFriends(Socket s,String str)
	{
		this.s=s;
		this.str=str;
		inf=str.split(":");
		
		panel1=new JPanel();
		panel2=new JPanel();
		String[] o=inf[4].split(",");
		frame=new JFrame(inf[3]+"<"+inf[1]+">");
		button =new JButton[o.length];
		for(int i=0;i<o.length;i++)
		{
			button[i]=new JButton(o[i]);
			button[i].setBackground(Color.WHITE);
			button[i].addActionListener(this);
			panel2.add(button[i],BorderLayout.CENTER);
		}
		frame.add(panel1,BorderLayout.NORTH);
		frame.add(panel2,BorderLayout.CENTER);
		
		frame.setVisible(true);
		frame.setLocation(100,100);
		frame.setSize(150,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("ok");
	}
}*/


///////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////



