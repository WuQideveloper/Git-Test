package mychat;

/**/
import java.net.*;
import java.io.*;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

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
	///////////////////////////////////////////////////////////////////////////
	public void sentTomysql(String s1,String s2)
	{
		Connection con;
        //link
        String driver = "com.mysql.jdbc.Driver";
        //mydata
        String url = "jdbc:mysql://localhost:3306/mychat";
        //
        String user = "ora00186";
        //
        String password = "AN9rxG";
		//String name = "zhouming";
		int id= 2;
		//String info = "123zxc";
        String name = s1;String info = s2;
        try {
 
            Class.forName(driver);
			System.out.println("haha");
            
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            
            Statement statement = con.createStatement();
			///////////////////////////////////////////////////////////////////////select information
            String sql3 = "select * from user_friend";
			ResultSet rs1 = statement.executeQuery(sql3);
			
			int u_id = 0;
			while(rs1.next()){
                u_id = rs1.getInt("user_id");
            }
            rs1.close();
			 id = u_id+1;
			
			
			/////////////////////////////////////////////////////////////////////////
			String sql2="insert into user_friend (user_id,user_name,user_info) values (?,?,?)";//add
			try {    
            PreparedStatement  preStmt= con.prepareStatement(sql2);	
            //String uid = String.valueOf(id);	
           			
            preStmt.setInt(1, id);
            preStmt.setString(2, name);
            preStmt.setString(3, info);
            preStmt.executeUpdate();
            
            preStmt.close();
            //db.close();//close
        } catch (Exception e) {
            e.printStackTrace();
        }

			String sql = "select * from user_friend";
            
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------"); 
            System.out.println("-----------------");  
            System.out.println("| user_id | user_name | user_info |");  
            System.out.println("-----------------");  
             
			int user_id = 0;
			String user_name = null;
			String user_info = null;
			while(rs.next()){
                user_id = rs.getInt("user_id");
                user_name = rs.getString("user_name");
				user_info = rs.getString("user_info");

                //
                System.out.println(user_id + "\t" + user_name + "\t" + user_info);
				System.out.println("A new record");
            }
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {   
            //
            System.out.println("Sorry,can`t find the Driver!");  
            System.out.println("bad");			
            e.printStackTrace();   
            } catch(SQLException e) {
            //
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
	}
	
	///////////////////////////////////////////////////////////
	
	public String getLogin(String s) //diao yong jie kou
	{
		
		Connection con;
        //qu dong ming cheng 
        String driver = "com.mysql.jdbc.Driver";
        //yao fang wen de shu ju ku 
        String url = "jdbc:mysql://localhost:3306/mychat";
        //yonghuming 
        String user = "ora00186";
        //mima 
        String password = "AN9rxG";
		//String name = "zhouming123";
		//int id= 2134;
		//String pwd = "123zxc";
		
		try 
		{
			String[] sn=new String[3];
			sn=s.split(":");
			
           Class.forName(driver);
		   System.out.println("haha");
		   con = DriverManager.getConnection(url,user,password);
           if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
           Statement statement = con.createStatement(); 
		   String sql = "select * from user_info";//select
            //3.ResultSet
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------");
            System.out.println("ok , it is :");  
            System.out.println("-----------------");  
            System.out.println("| user_id | user_name | user_pwd | user_info |");  
            System.out.println("-----------------");  
             
			String user_id = null;
			String user_name = null;
			String user_pwd = null;
			String user_info = null;
			while(rs.next()){
                user_id = rs.getString("user_id");
                user_name = rs.getString("user_name");
				user_pwd = rs.getString("user_pwd");
				user_info = rs.getString("user_info");
				
                //out put
                System.out.println(user_id + "\t" + user_name + "\t" + user_pwd+ "\t" + user_info);
				System.out.println("great "+user_id);
				if(user_id == sn[1]) break;
				//System.out.println("great    "+sn[1]);
            }
            rs.close();
            con.close();
			if (user_id != sn[1])
			{
				FileInputStream fis=null;
		BufferedReader br=null;
		InputStreamReader isr=null;
				String[] sn1=new String[3];
			sn1=s.split(":");
			
			fis=new FileInputStream(sn1[1]+".dat");
			isr=new InputStreamReader(fis);
			Properties pro=new Properties();
			pro.load(isr);
			
			String s1=pro.getProperty("id");
			String s2=pro.getProperty("mima");
			String s3=pro.getProperty("name");
			String s4=pro.getProperty("haoyou");
			
			if(sn1[1].equals(s1) && sn1[2].equals(s2)){
				return "%true%"+":"+s1+":"+s2+":"+s3+":"+s4;
			}
			}
			return "%true%"+":"+user_id+":"+user_pwd+":"+user_name+":"+user_info;
        } 
		catch(ClassNotFoundException e) 
		{   
            //database not good
            System.out.println("Sorry,can`t find the Driver!");  
            System.out.println("database not good ! !");			
            e.printStackTrace();   
		/////////////////////////////////////////////
		
		
		
		
		
		
		
		
		
		
		
		
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
			

			
		} catch (IOException ee) {
			ee.printStackTrace();
		}
		finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException eeee) {
					eeee.printStackTrace();
				}
			}
		}
		
		
		
		}
		catch(SQLException e1) 
		{
            //database is not
            e1.printStackTrace();  
        }catch (Exception e2) 
		{
            // TODO: handle exception
            e2.printStackTrace();
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
								sentTomysql(sms[1],str);
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

