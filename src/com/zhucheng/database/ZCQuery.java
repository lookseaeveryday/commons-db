package com.zhucheng.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zhucheng.database.exception.SQLCloseException;

/**
 * 操作管理类
 * @author 贾志鑫
 */
public class ZCQuery extends AbstractQueryRunner {
	
	public ZCQuery() {
		super(WebSources.getSource());
	}

	public ZCQuery(DataSource ds) {
		super(ds);
	}
	
	
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params)
			throws SQLException, SQLCloseException {
		Connection conn = this.prepareConnection();

		return this.<T> query(conn, true, sql, rsh, params);
	}

	public <T> T query(String sql, ResultSetHandler<T> rsh)
			throws SQLException, SQLCloseException {
		Connection conn = this.prepareConnection();

		return this.<T> query(conn, true, sql, rsh, (Object[]) null);
	}

	public int update(String sql) throws SQLException, SQLCloseException {
		Connection conn = this.prepareConnection();

		return this.update(conn, true, sql, (Object[]) null);
	}

	public int update(String sql, Object param) throws SQLException,
			SQLCloseException {
		Connection conn = this.prepareConnection();

		return this.update(conn, true, sql, new Object[] { param });
	}

	public int update(String sql, Object... params) throws SQLException,
			SQLCloseException {
		Connection conn = this.prepareConnection();

		return this.update(conn, true, sql, params);
	}

	private int update(Connection conn, boolean closeConn, String sql,
			Object... params) throws SQLException, SQLCloseException {
		if (conn == null) {
			throw new SQLException("Null connection");
		}

		if (sql == null) {
			if (closeConn) {
				close(conn);
			}
			throw new SQLException("Null SQL statement");
		}

		PreparedStatement stmt = null;
		int rows = 0;

		try {
			stmt = this.prepareStatement(conn, sql);
			this.fillStatement(stmt, params);
			rows = stmt.executeUpdate();

		} catch (SQLException e) {
			this.rethrow(e, sql, params);

		} finally {
			close(stmt);
			if (closeConn) {
				close(conn);
			}
		}

		return rows;
	}

	private <T> T query(Connection conn, boolean closeConn, String sql,
			ResultSetHandler<T> rsh, Object... params) throws SQLException,
			SQLCloseException {
		if (conn == null) {
			throw new SQLException("Null connection");
		}

		if (sql == null) {
			if (closeConn) {
				close(conn);
			}
			throw new SQLException("Null SQL statement");
		}

		if (rsh == null) {
			if (closeConn) {
				close(conn);
			}
			throw new SQLException("Null ResultSetHandler");
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		T result = null;

		try {
			stmt = this.prepareStatement(conn, sql);
			this.fillStatement(stmt, params);
			rs = this.wrap(stmt.executeQuery());
			result = rsh.handle(rs);

		} catch (SQLException e) {
			this.rethrow(e, sql, params);

		} finally {
			try {
				close(rs);
			} finally {
				close(stmt);
				if (closeConn) {
					close(conn);
				}
			}
		}

		return result;
	}

}
