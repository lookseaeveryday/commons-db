package com.zhucheng.database.exception;

public class SQLCloseException extends Exception {


	private static final long serialVersionUID = 7029871814976607558L;

	public SQLCloseException () {
		printStackTrace();
	}
	
	public void printStackTrace() {
		System.out.println("SQL close error please check!");
	};
}
