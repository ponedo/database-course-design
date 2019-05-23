package Database;

import java.sql.*;

public class DBUtil {

    private static Connection conn=null;
    private static String user = "root"; //MySQL配置时的用户名
    private static String password = "101112131415tuzi"; //MySQL配置时的密码

    public static void setUser(String user) {
        DBUtil.user = user;
    }

    public static void setPassword(String password) {
        DBUtil.password = password;
    }

    public static Connection getConn() {
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名yggl
        String url = "jdbc:mysql://localhost:3306/Vocabulary";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //getConnection()方法，连接MySQL数据库
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (SQLException e) {
            return null;
        }

    }

    public static void myClose(Connection conn, Statement statement, ResultSet rs) {
        if(rs!=null) {
            try {
                rs.close();
                rs=null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(statement!=null) {
            try {
                statement.close();
                statement=null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null) {
            try {
                conn.close();
                conn=null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

/**另一种Statement范例
public void test() {
        try {
            if (!conn.isClosed())
                System.out.println("Successfully connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句
            Statement statement = conn.createStatement();
            //要执行的SQL语句
            String sql = "select count(*) from ranking where [time] <= 1000";
            //3.ResultSet类，用来存放获取的结果集
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-------------------------------------");
            System.out.println("执行结果如下所示：");
            System.out.println("-------------------------------------");
            String count = null;
            while(rs.next()) {
                //获取这几列数据
                count = rs.getString("count(*)");
                System.out.println(count);
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 */