package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	// Database Schema
	// CREATE DATABASE loggy DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
	// CREATE TABLE logs (uuid CHAR(40) NOT NULL PRIMARY KEY, title CHAR(128),
	// content TEXT, createTimestamp Date);

	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	private static final String CONN_STRING = "jdbc:mysql://localhost:3306/t1er";
	private static final String CONN_STRING_MYSQL = "jdbc:mysql://";

	public static Connection getConnectionToMySQL() {
		Connection connection = null;
		
		try {

			// load the driver class
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CONN_STRING_MYSQL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.err.println(e);
		}
		return connection;
	}
	
	public static Connection getConnectionToDatabase() {
		Connection connection = null;

		try {

			// load the driver class
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.err.println(e);
		}
		return connection;
	}
	
	//Create database if not exists
	public static void databaseCreateIfNotExists() {
		try {
			
			Connection connMySQL1 = DBConnection.getConnectionToMySQL();
			String createDBSQL1 = "DROP DATABASE IF EXISTS t1er;";			java.sql.PreparedStatement statementDB1 = connMySQL1.prepareStatement(createDBSQL1);
			statementDB1.executeUpdate();
			connMySQL1.close();
//			
			Connection connMySQL = DBConnection.getConnectionToMySQL();
			String createDBSQL = "CREATE DATABASE IF NOT EXISTS t1er;";
			java.sql.PreparedStatement statementDB = connMySQL.prepareStatement(createDBSQL);
			statementDB.executeUpdate();
			connMySQL.close();
			
			Connection connection = DBConnection.getConnectionToDatabase();
			String createTableSQL =  "CREATE TABLE IF NOT EXISTS `user` (\n" + 
					"  `id` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `email` varchar(150) COLLATE utf8_unicode_ci NOT NULL UNIQUE,\n" + 
					"  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `createdDate` datetime DEFAULT NULL,\n" + 
					"  `deletedDate` datetime DEFAULT NULL,\n" +
					"  `isActivated` smallint DEFAULT NULL,\n" +
					"  `activationKey` varchar(12) DEFAULT NULL UNIQUE,\n" +
					"  PRIMARY KEY (`id`)\n" + 
					") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
			java.sql.PreparedStatement statement = connection.prepareStatement(createTableSQL);
			statement.executeUpdate();
			createTableSQL =  "CREATE TABLE IF NOT EXISTS `tier` (\n" + 
					"  `id` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `name` varchar(150) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  PRIMARY KEY (`id`)\n" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
			statement = connection.prepareStatement(createTableSQL);
			statement.executeUpdate();
			createTableSQL =  "CREATE TABLE IF NOT EXISTS `tier_members` (\n" + 
					"  `tierID` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `userID` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" +
					"  `isOwner` tinyint COLLATE utf8_unicode_ci NOT NULL,\n" +
					"  PRIMARY KEY (`tierID`, `userID`),\n" +
					"  FOREIGN KEY (`tierID`) REFERENCES tier(`id`),\n" +
					"  FOREIGN KEY (`userID`) REFERENCES user(`id`)\n" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
			statement = connection.prepareStatement(createTableSQL);
			statement.executeUpdate();
			createTableSQL =  "CREATE TABLE IF NOT EXISTS `expense` (\n" + 
					"  `id` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `name` varchar(150) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `description` varchar(150) COLLATE utf8_unicode_ci NULL,\n" + 
					"  `amount` double COLLATE utf8_unicode_ci NOT NULL,\n" +
					"  `owner` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `createdDate` datetime DEFAULT NULL,\n" + 
					"  `tier` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  PRIMARY KEY (`id`),\n" +
					"  FOREIGN KEY (`owner`) REFERENCES user(`id`),\n" +
					"  FOREIGN KEY (`tier`) REFERENCES tier(`id`)\n" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
			statement = connection.prepareStatement(createTableSQL);
			statement.executeUpdate();
			createTableSQL =  "CREATE TABLE IF NOT EXISTS `expense_subtotal` (\n" + 
					"  `id` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `amount` double COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `isPaid` tinyint COLLATE utf8_unicode_ci NULL,\n" + 
					"  `owner` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `createdDate` datetime DEFAULT NULL,\n" + 
					"  `expense` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  PRIMARY KEY (`id`),\n" +
					"  FOREIGN KEY (`owner`) REFERENCES user(`id`),\n" +
					"  FOREIGN KEY (`expense`) REFERENCES expense(`id`)\n" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
			statement = connection.prepareStatement(createTableSQL);
			statement.executeUpdate();
			createTableSQL =  "CREATE TABLE IF NOT EXISTS `notifications` (\n" + 
					"  `id` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `message` varchar(250) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  `user` char(40) COLLATE utf8_unicode_ci NOT NULL,\n" + 
					"  PRIMARY KEY (`id`),\n" +
					"  FOREIGN KEY (`user`) REFERENCES user(`id`)\n" +
					") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
			statement = connection.prepareStatement(createTableSQL);
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getSQLState());
			System.err.println(e);
		}
	}
}

