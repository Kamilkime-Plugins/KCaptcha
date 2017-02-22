package io.github.kamilkime.kcaptcha.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.github.kamilkime.kcaptcha.Main;

public class DatabaseManager {

	private static final File DB_FILE = new File(Main.getInst().getDataFolder(), "data.sqlite");
	private static Connection dbConnection;
	
	public static void checkDatabaseFile() {
		if(!DB_FILE.exists()) {
			try {
				DB_FILE.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			openConnection();
			executeUpdate("CREATE TABLE IF NOT EXISTS users (uuid varchar(36) PRIMARY KEY not null, name varchar(32) not null, ip varchar(16) not null,"
					+ "date bigint not null);");
			closeConnection();
		}
	}
	
	public static void openConnection() {
		try {
			if(dbConnection !=null && !dbConnection.isClosed()) return;
			dbConnection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE.getAbsolutePath());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection() {
		try {
			if(dbConnection == null || (dbConnection !=null && dbConnection.isClosed())) return;
			dbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet executeQuery(String query) {
		try {
			return dbConnection.prepareStatement(query).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int executeUpdate(String update) {
		try {
			return dbConnection.prepareStatement(update).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static long executeLargeUpdate(String update) {
		try {
			return dbConnection.prepareStatement(update).executeLargeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0L;
		}
	}
}