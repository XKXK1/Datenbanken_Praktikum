package aufgabe7;

import java.sql.*;

/**
 * Diese Klasse verwaltet die Verbindung der Datenbank und die Befehle zum
 * Bedienen der Datenbank.
 * 
 * @author Derya Uyargil
 *
 */
public class Datenbank {

	private Connection dbConnection;
	private boolean isConnected;

	public Datenbank() {
		isConnected = false;
	}

	/**
	 * Diese Methode stellt eine Verbindung zur Datenbank der HAW her. Erwartet
	 * werden Benutzername und Passwort.
	 * 
	 * @param username
	 * @param password
	 */
	public void connect(String username, String password) {
		try {
			dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14",
					username, password);
			isConnected = true;
		} catch (SQLException e) {
			System.out.println("No connection");
			//e.printStackTrace();
		}
	}

	/**
	 * Beenden der Verbindung mit der Datenbank
	 */
	public void disconnect() {
		try {
			dbConnection.close();
			isConnected = false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Eine Tabelle mit allen Personen, welche in der OE "Solarenergie" sind
	 * wird erstellt.
	 * 
	 * @return
	 */
	public Table showPersonSolarEnergie() {
		try {

			Table table = new Table();
			// Erstellen eines SQL Statements
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			// Definieren des SQL Statements
			ResultSet myRs = myStmt.executeQuery(
					"select AUSLEIHER.VNAME from AUSLEIHER join AUSLEIHER_ORGANISATIONSEINHEIT on AUSLEIHER_ORGANISATIONSEINHEIT.AUSLEIHER_ID = AUSLEIHER.AUSLEIHER_ID");
			// Fuer Jede gefundene Spalte wird eine Spalte in der Tabelle
			// angelegt
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;

			// Jede Reihe der Tabelle wird befuellt
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

	/**
	 * Eine Tabelle mit allen Endgeraeten wird erstellt.
	 * 
	 * @return
	 */
	public Table showAllModellbezeichnung() {
		try {
			Table table = new Table();
			// Erstellen eines SQL Statements
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			// Definieren des SQL Statements
			ResultSet myRs = myStmt.executeQuery("select * from modellbezeichnung");
			// Fuer Jede gefundene Spalte wird eine Spalte in der Tabelle
			// angelegt
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;
			// Jede Reihe der Tabelle wird befuellt
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

	/**
	 * Methode zur Abfrage ob ein, als Argument uebergebener, Modellbezeichner
	 * in der Datenbank exisitert.
	 * 
	 * @param modellbezeichner
	 * @return
	 */
	public boolean bezeichnerExist(String modellbezeichner) {
		try {
			// Erstellen eines SQL Statements
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			// Definieren des SQL Statements
			ResultSet myRs = myStmt.executeQuery(
					"select * from modellbezeichnung where modellbezeichnung = '" + modellbezeichner + "'");
			if (myRs.isBeforeFirst()) {
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

	/**
	 * Methode zum Hinzufuegen eines Endgeraets in die DatenbankTabelle
	 * Endgeraete
	 * 
	 * Es werden folgende 3 Argumente erwartet:
	 * 
	 * @param invNr
	 * @param modellbezeichnung
	 * @param datum
	 */
	public void endgeraetAdd(int invNr, String modellbezeichnung, String datum) {
		try {
			// Definieren des SQL Befehls
			String befehl = "INSERT INTO ENDGERAETE VALUES(?,?,?)";
			// Erstellen eines PreparedStatements damit die uebergebenen
			// Argumente
			// an das Statement uebergeben werden koennen
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

	/**
	 * Methode zum Hinzufuegen einer Modellbezeichnung in die DatenbankTabelle
	 * Modellbezeichnung
	 * 
	 * Es werden folgende 2 Argumente erwartet:
	 * 
	 * @param modellbezeichnung
	 * @param geraetName
	 */
	public void modellbezeichnungAdd(String modellbezeichnung, String geraetName) {
		try {
			// Definieren des SQL Befehls
			String befehl = "INSERT INTO modellbezeichnung VALUES(?,?)";
			// Erstellen eines PreparedStatements damit die uebergebenen
			// Argumente
			// an das Statement uebergeben werden koennen
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
	
	/**
	 * Eine Tabelle mit allen Modellbezeichnungen der DatenbankTabelle "Modellbezeichnungen" wird
	 * erstellt
	 * 
	 * @return
	 */
	public Table showAllEndgeraete(){
		try {
			Table table = new Table();
			// Erstellen eines SQL Statements
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			// Definieren des SQL Statements
			ResultSet myRs = myStmt.executeQuery("select * from endgeraete");
			// Fuer Jede gefundene Spalte wird eine Spalte in der Tabelle
			// angelegt
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;
			// Jede Reihe der Tabelle wird befuellt
			while (myRs.next()) {
				table.addRow();
				table.addColumnData(index, myRs.getString(1), myRs);
				table.addColumnData(index, myRs.getString(2), myRs);
				table.addColumnData(index, myRs.getString(3), myRs);
				index++;
			}
			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		
	}

	/**
	 * Eine Tabelle mit allen Personen der DatenbankTabelle "Ausleiher" wird
	 * erstellt
	 * 
	 * @return
	 */
	public Table showAllPersons() {
		try {
			Table table = new Table();
			// Erstellen eines SQL Statements
			Statement myStmt;
			myStmt = dbConnection.createStatement();
			// Definieren des SQL Befehls
			ResultSet myRs = myStmt.executeQuery("select * from ausleiher");
			// Fuer Jede gefundene Spalte wird eine Spalte in der Tabelle
			// angelegt
			for (int i = 0; i < myRs.getMetaData().getColumnCount(); i++) {
				table.addColumn(myRs.getMetaData().getColumnLabel(i + 1));
			}
			int index = 0;
			// Jede Reihe der Tabelle wird befuellt
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

	public boolean getConnection() {
		return isConnected;
	}

}
