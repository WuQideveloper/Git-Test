package lianxi2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class e2 {

    public static void main(String[] args) {
        //����Connection����
        Connection con;
        //����������
        String driver = "com.mysql.jdbc.Driver";
        //URLָ��Ҫ���ʵ����ݿ���mydata
        String url = "jdbc:mysql://localhost:3306/food";
        //MySQL����ʱ���û���
        String user = "ora00186";
        //MySQL����ʱ������
        String password = "AN9rxG";
        //������ѯ�����
        try {
            //������������
			
            Class.forName(driver);
			System.out.println("haha");
            //1.getConnection()����������MySQL���ݿ⣡��
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.����statement���������ִ��SQL��䣡��
            Statement statement = con.createStatement();
            //Ҫִ�е�SQL���
            String sql = "select * from user_info";
            //3.ResultSet�࣬������Ż�ȡ�Ľ��������
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------");
            System.out.println("ִ�н��������ʾ:");  
            System.out.println("-----------------");  
            System.out.println("| user_id | user_name | user_pwd | user_info |");  
            System.out.println("-----------------");  
             
			String user_id = null;
			String user_name = null;
			String user_pwd = null;
			while(rs.next()){
                user_id = rs.getString("user_id");
                user_name = rs.getString("user_name");
				user_pwd = rs.getString("user_pwd");

                //������
                System.out.println(user_id + "\t" + user_name + "\t" + user_pwd);
				System.out.println("���ݿ����ݳɹ���ȡ����");
            }
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {   
            //���ݿ��������쳣����
            System.out.println("Sorry,can`t find the Driver!");  
            System.out.println("���ݿ����ݻ�ȡʧ�ܣ���");			
            e.printStackTrace();   
            } catch(SQLException e) {
            //���ݿ�����ʧ���쳣����
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}