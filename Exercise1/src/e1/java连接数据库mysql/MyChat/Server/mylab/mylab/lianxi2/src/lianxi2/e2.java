package lianxi2;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class e2 {

    public static void main(String[] args) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/food";
        //MySQL配置时的用户名
        String user = "ora00186";
        //MySQL配置时的密码
        String password = "AN9rxG";
        //遍历查询结果集
        try {
            //加载驱动程序
			
            Class.forName(driver);
			System.out.println("haha");
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from user_info";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------");
            System.out.println("执行结果如下所示:");  
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

                //输出结果
                System.out.println(user_id + "\t" + user_name + "\t" + user_pwd);
				System.out.println("数据库数据成功获取！！");
            }
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");  
            System.out.println("数据库数据获取失败！！");			
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

}