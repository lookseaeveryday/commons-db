package com.zhucheng.database;

import javax.sql.DataSource;


/**
 * @author 贾志新
 * 2015/12/21 扩展的新功能
 */
public class WebSources {

	protected static DataSource dataSource;

	protected static void setDataSource(DataSource dataSource) {
		WebSources.dataSource = dataSource;
	}
	
	public static DataSource getSource() {
		return dataSource;
	}
	
	public void init() {
	}
	
}
