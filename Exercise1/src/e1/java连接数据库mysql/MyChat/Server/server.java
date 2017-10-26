/**/
import java.net.*;
import java.io.*;
import java.util.*;

interface DAOloginn 
{
	public String getLogin(String s);

}

class server
{
	public static void main(String[] args) 
	{
		new beginning();
		//System.out.println("hello world");
	}
}

class beginning implements DAOloginn{
	private ServerSocket ss=null;
	private String[] zhanghao;
	private Socket[] sockets;
	private static int i=0;
	
	
	public beginning() {
		try 
		{
			ss=new ServerSocket(8989);
//			hs=new HashSet();
			zhanghao=new String[100];
			sockets=new Socket[100];

			while(true)
			{
				Socket s;
			    try {
					s = ss.accept();
					new MyThread(s).start();//xian cheng kai shi
				}
			    catch (IOException e){e.printStackTrace();}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String getLogin(String s) //diao yong jie kou
	{
		FileInputStream fis=null;
		BufferedReader br=null;
		InputStreamReader isr=null;
		try {
			String[] sn=new String[3];
			sn=s.split(":");
			
			fis=new FileInputStream("MyChat/info/"+sn[1]+".dat");
			isr=new InputStreamReader(fis);
			Properties pro=new Properties();
			pro.load(isr);
			
			String s1=pro.getProperty("id");
			String s2=pro.getProperty("mima");
			String s3=pro.getProperty("name");
			String s4=pro.getProperty("haoyou");
			
			if(sn[1].equals(s1) && sn[2].equals(s2)){
				return "%true%"+":"+s1+":"+s2+":"+s3+":"+s4;
			}
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "false";
	}

	class MyThread extends Thread{
		Socket s=null;
		PrintWriter pw=null;
		BufferedReader br=null;
		public MyThread(Socket s){
			super();
			this.s=s;
		}
		public void run(){
			String str=null;
			try {
				br=new BufferedReader(new InputStreamReader(s.getInputStream()));
				pw = new PrintWriter(s.getOutputStream());
			
			while(true){	
						str=br.readLine();
						if(str!=null){
							if(str.indexOf("%login%")==0)//deng lu xin xi yanzheng
							{
								DAOloginn ld=new beginning();
								String s2=null;
								s2=ld.getLogin(str);
								
								zhanghao[i]=str.split(":")[1];
								sockets[i]=s;
								i++;
								pw.println(s2);
								pw.flush();
							}
							if(str.indexOf("%send%")==0)//fa song xinxi
							{
								String s=str.split("%send%")[1];
								String[] sms=s.split(":");//get the imformation
								for(int j=0;j<i;j++)
								{
									if(sms[0].equals(zhanghao[j]))
									{
										try
										{
											PrintWriter pw=new PrintWriter(sockets[j].getOutputStream());
						                    pw.println("%receive%"+sms[1]+":"+sms[2]+":"+sms[3]);// send imformation
											pw.flush();
										}
										catch(IOException e){e.printStackTrace();}
									}
								}
							}
						}
						else{
							continue;
						}
					}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}	
		}
	}
}


/*
class CheckImformation implements DAOloginn{

	public String getLogin(String s) 
	{
		FileInputStream fis=null;
		BufferedReader br=null;
		InputStreamReader isr=null;
		try {
			String[] sn=new String[3];
			sn=s.split(":");
			
			fis=new FileInputStream(sn[1]+".dat");
			isr=new InputStreamReader(fis);
			Properties pro=new Properties();
			pro.load(isr);
			
			String s1=pro.getProperty("id");
			String s2=pro.getProperty("mima");
			String s3=pro.getProperty("name");
			String s4=pro.getProperty("haoyou");
			
			if(sn[1].equals(s1) && sn[2].equals(s2)){
				return "%true%"+":"+s1+":"+s2+":"+s3+":"+s4;
			}
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return "false";
	}
}

*/

