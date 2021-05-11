package db;

import dao.DBConnection;

public class CreateDB {

	public static void main(String[] args) {
		DBConnection.databaseCreateIfNotExists();
		System.out.println("Database made");
	}

}
