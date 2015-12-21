package com.zhucheng.database;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 这个主要是给spring 集成的时候使用
 * @author 贾志鑫
 *
 */
public class DBTemplate {

	protected static DataSource dataSource;

	public static void setDataSource(DruidDataSource dataSource) {
		DBTemplate.dataSource = dataSource;
	}
	
}
