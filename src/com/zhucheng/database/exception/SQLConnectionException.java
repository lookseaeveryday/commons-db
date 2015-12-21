package com.zhucheng.database.exception;

import java.sql.SQLException;

public class SQLConnectionException extends SQLException {

	private static final long serialVersionUID = -7869099982963536085L;


	public SQLConnectionException() {
		printStackTrace();
	}
	
	public void printStackTrace() {
		System.out.println("SQL param error please check!");
	};
}
