package com.zhucheng.database;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;

import javax.sql.DataSource;

import com.zhucheng.database.exception.SQLCloseException;

/**
 * 
 * @author apple
 *
 */
public abstract class AbstractQueryRunner {

	protected final DataSource ds;

	public AbstractQueryRunner(DataSource ds) {
		this.ds = ds;
	}

	public DataSource getDataSource() {
		return this.ds;
	}

	protected Connection prepareConnection() throws SQLException {
		if (this.getDataSource() == null) {
			throw new SQLException(
					"QueryRunner requires a DataSource to be "
							+ "invoked in this way, or a Connection should be passed in");
		}
		return this.getDataSource().getConnection();
	}

	private volatile boolean pmdKnownBroken = false;

	protected void close(Connection conn) throws SQLException,
			SQLCloseException {
		DBUtils.close(conn);
	}

	protected void close(Statement stmt) throws SQLException, SQLCloseException {
		DBUtils.close(stmt);
	}

	protected void close(ResultSet rs) throws SQLException, SQLCloseException {
		DBUtils.close(rs);
	}

	protected PreparedStatement prepareStatement(Connection conn, String sql)
			throws SQLException {
		return conn.prepareStatement(sql);
	}

	protected ResultSet wrap(ResultSet rs) {
		return rs;
	}

	protected void rethrow(SQLException cause, String sql, Object... params)
			throws SQLException {

		String causeMessage = cause.getMessage();
		if (causeMessage == null) {
			causeMessage = "";
		}
		StringBuffer msg = new StringBuffer(causeMessage);

		msg.append(" Query: ");
		msg.append(sql);
		msg.append(" Parameters: ");

		if (params == null) {
			msg.append("[]");
		} else {
			msg.append(Arrays.deepToString(params));
		}

		SQLException e = new SQLException(msg.toString(), cause.getSQLState(),
				cause.getErrorCode());
		e.setNextException(cause);

		throw e;
	}

	public void fillStatement(PreparedStatement stmt, Object... params)
			throws SQLException {

		// check the parameter count, if we can
		ParameterMetaData pmd = null;
		if (!pmdKnownBroken) {
			pmd = stmt.getParameterMetaData();
			int stmtCount = pmd.getParameterCount();
			int paramsCount = params == null ? 0 : params.length;

			if (stmtCount != paramsCount) {
				throw new SQLException("Wrong number of parameters: expected "
						+ stmtCount + ", was given " + paramsCount);
			}
		}

		// nothing to do here
		if (params == null) {
			return;
		}

		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				stmt.setObject(i + 1, params[i]);
			} else {
				// VARCHAR works with many drivers regardless
				// of the actual column type. Oddly, NULL and
				// OTHER don't work with Oracle's drivers.
				int sqlType = Types.VARCHAR;
				if (!pmdKnownBroken) {
					try {
						/*
						 * It's not possible for pmdKnownBroken to change from
						 * true to false, (once true, always true) so pmd cannot
						 * be null here.
						 */
						sqlType = pmd.getParameterType(i + 1);
					} catch (SQLException e) {
						pmdKnownBroken = true;
					}
				}
				stmt.setNull(i + 1, sqlType);
			}
		}
	}

}
