package common;

import java.sql.*;

public class JDBCTemplate {
	private final static String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private final static String USERNAME = "MEMBERWEB";
	private final static String PASSWORD = "MEMBERWEB";

	private static JDBCTemplate instance;
	private static Connection conn;

	public JDBCTemplate() {

	}

	public static JDBCTemplate getInstance() {
		if (instance == null) {
			instance = new JDBCTemplate();
		}
		return instance;
	}

	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName(DRIVER_NAME);
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				conn.setAutoCommit(false);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
