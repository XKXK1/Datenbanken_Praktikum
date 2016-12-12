package aufgabe7;

import java.sql.*;

public class Datenbank {

	private Connection dbConnection;
	private boolean isConnected;

	public Datenbank() {
		isConnected = false;
	}

	public void connect(String username, String password) {
		try {
			dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14",
					username, password);
			isConnected = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			dbConnection.close();
			isConnected = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Table showPersonSolarEnergie() {
		try {
			Table table = new Table();
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			ResultSet myRs = myStmt.executeQuery(
					"select AUSLEIHER.VNAME from AUSLEIHER join AUSLEIHER_ORGANISATIONSEINHEIT on AUSLEIHER_ORGANISATIONSEINHEIT.AUSLEIHER_ID = AUSLEIHER.AUSLEIHER_ID");
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;
			while (myRs.next()) {
				table.addColumnData(index, myRs.getString(1));
				index++;
			}
			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public Table showAllPersons() {
		try {
			Table table = new Table();
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from AUSLEIHER");
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;
			while (myRs.next()) {
				table.addColumnData(index, myRs.getString(1));
				table.addColumnData(index, myRs.getString(2));
				table.addColumnData(index, myRs.getString(3));
				table.addColumnData(index, myRs.getString(4));
				table.addColumnData(index, myRs.getString(5));
				table.addColumnData(index, myRs.getString(6));
				table.addColumnData(index, myRs.getString(7));
				index++;
			}
			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Statement createStatement() {
		Statement myStmt;
		try {
			myStmt = dbConnection.createStatement();
			return myStmt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean getConnection() {
		return isConnected;
	}

	public Connection getSQLConnection() {
		return dbConnection;
	}

}
