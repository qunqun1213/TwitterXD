package bean;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConn {
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/demo" + "?useUnicode=true&characterEncoding=UTF-8", 
					"root", 
					"123456"); 
		} catch (Exception e) {
			System.out.printf("数据库连接失败");
		}

		return con;
	}

}
