package com.zhucheng.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.zhucheng.database.exception.SQLCloseException;

/**
 * 数据管理类
 * @author apple
 *
 */
public final class DBUtils {

	/**
	 * 空的构造
	 */
	public DBUtils() {
		// do nothing
	}
	
	/**
	 * Connection method
	 * @throws SQLException 
	 */
//	public static Connection getConn(String url,String user, String password) throws SQLException {
//		if(url == null && user == null && password  == null) {
//			throw new SQLConnectionException();
//		} else {
//			Connection conn;
//			conn = DriverManager.getConnection(url, user, password);
//			return conn;
//		}
//	}

	/**
	 * 关闭Connection
	 * 
	 * @param conn
	 * @throws SQLException
	 * @throws SQLCloseException 
	 */
	public static void close(Connection conn) throws SQLException, SQLCloseException {
		if (conn != null) {
			close(conn, null, null);
		}
	}

	/**
	 * 关闭ResultSet
	 * 
	 * @param rs
	 * @throws SQLException
	 * @throws SQLCloseException 
	 */
	public static void close(ResultSet rs) throws SQLException, SQLCloseException {
		if (rs != null) {
			close(null, rs, null);
		}
	}

	/**
	 * 关闭Statement
	 * 
	 * @param stmt
	 * @throws SQLException
	 * @throws SQLCloseException 
	 */
	public static void close(Statement stmt) throws SQLException, SQLCloseException {
		if (stmt != null) {
			close(null, null, stmt);
		}
	}

	public static void close(Connection conn, ResultSet rs, Statement stmt)
			throws SQLException, SQLCloseException {
		if (conn != null && rs != null && stmt != null) {
			stmt.close();
			rs.close();
			conn.close();
		} else if (conn != null) {
			conn.close();
		} else if (rs != null) {
			rs.close();
		} else if (stmt != null) {
			stmt.close();
		} else {
			throw new SQLCloseException();
		}
	}

	/**
	 * 事务的提交和连接的关闭
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static void commitAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.commit();
			} finally {
				conn.close();
			}
		}
	}

	/**
	 * 事务的回滚和连接的关闭
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public static void rollbackAndClose(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.rollback();
			} finally {
				conn.close();
			}
		}
	}

	public static boolean loadDriver(String driverClassName) {
		return loadDriver(DBUtils.class.getClassLoader(), driverClassName);
	}

	public static boolean loadDriver(ClassLoader classLoader,
			String driverClassName) {
		try {
			classLoader.loadClass(driverClassName).newInstance();
			return true;

		} catch (IllegalAccessException e) {
			// Constructor is private, OK for DriverManager contract
			return true;

		} catch (Exception e) {
			return false;

		}
	}
}
