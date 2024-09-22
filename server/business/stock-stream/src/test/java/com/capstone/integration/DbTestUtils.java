package com.capstone.integration;
/*
 * DbTestUtils.java - utility functions for database integration tests.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

public class DbTestUtils
{

	public static int countRowsInTable(Connection connection, String table) throws SQLException {
		int count = 0;
		String sql = "Select Count(0) from " + table;
		ResultSetHandler<BigDecimal> rsHandler = new ScalarHandler<>("count(0)");
		QueryRunner queryRunner = new QueryRunner();
		BigDecimal dcount = queryRunner.query(connection, sql, rsHandler);
		count = dcount.intValue();
		
		return count;
	}
	
	public static int countRowsInTableWhere(Connection connection, String table, String where) throws SQLException {
		int count = 0;
		String sql = "Select Count(0) from " + table + " where " + where;
		ResultSetHandler<BigDecimal> rsHandler = new ScalarHandler<>("count(0)");
		QueryRunner queryRunner = new QueryRunner();
		BigDecimal dcount = queryRunner.query(connection, sql, rsHandler);
		count = dcount.intValue();
		
		return count;
	}			
	
}
