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
				table.addRow();
				table.addColumnData(index, myRs.getString(1), myRs);
				index++;
			}
			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public Table showAllEndgeraete(){
		try {
			Table table = new Table();
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from modellbezeichnung");
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;
			while (myRs.next()) {
				table.addRow();
				table.addColumnData(index, myRs.getString(1), myRs);
				table.addColumnData(index, myRs.getString(2), myRs);			
				index++;			
			}
			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public boolean bezeichnerExist(String modellbezeichner){
		try {
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from modellbezeichnung where modellbezeichnung = '"+modellbezeichner+"'");
			if(myRs.isBeforeFirst()){
			return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void endgeraetAdd(int invNr, String modellbezeichnung, String datum){
		try {
			String befehl = "INSERT INTO ENDGERAETE VALUES(?,?,?)";
			PreparedStatement stmn = dbConnection.prepareStatement(befehl);
			stmn.setString(3, datum);
			stmn.setString(2, modellbezeichnung);
			stmn.setInt(1, invNr);
			stmn.execute();
			stmn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void modellbezeichnungAdd(String modellbezeichnung, String geraetName){
		try {
			String befehl = "INSERT INTO modellbezeichnung VALUES(?,?)";
			PreparedStatement stmn = dbConnection.prepareStatement(befehl);
			stmn.setString(2, geraetName);
			stmn.setString(1, modellbezeichnung);
			stmn.execute();
			stmn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Table showAllPersons() {
		try {
			Table table = new Table();
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			ResultSet myRs = myStmt.executeQuery("select * from ausleiher");
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;
			while (myRs.next()) {
				table.addRow();
				table.addColumnData(index, myRs.getString(1), myRs);
				table.addColumnData(index, myRs.getString(2), myRs);
				table.addColumnData(index, myRs.getString(3), myRs);
				table.addColumnData(index, myRs.getString(4), myRs);
				table.addColumnData(index, myRs.getString(5), myRs);
				table.addColumnData(index, myRs.getString(6), myRs);
				table.addColumnData(index, myRs.getString(7), myRs);
				table.addColumnData(index, myRs.getString(8), myRs);
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
