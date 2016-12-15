package aufgabe7;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * Diese Klasse beinhaltet Methoden zum Bedienen der Benutzeroberflaeche
 * Alle Benutzbaren Elemente der Oberflaeche werden mit den Methoden dieser Klasse verknuepft.
 * 
 * @author Derya Uyargil
 *
 */
public class Controller {
	private static Datenbank db = new Datenbank();
	private Pane root;
	private Stage stage = new Stage();

	@FXML
	Text textMid, fehlerText, textMod;

	@FXML
	TextField usernameText;

	@FXML
	TextField end1, end2, end3, mod1, mod2;

	@FXML
	PasswordField passwordText;

	@FXML
	Button button1, buttonDisconnect, buttonHinzufuegen, buttonResult1, buttonResult2;

	@FXML
	TableView<List<String>> table1;

	@FXML
	TableView<List<String>> tableFin;

	/**
	 * Bei Click zeige alle Personen
	 */
	public void showAllPersonsButtonClicked() {
		Table table = db.showAllPersons();
		changeTable(table);
	}

	/**
	 * Bei Click zeige alle Personen in OE Solarenergie
	 */
	public void showPersonSolarButtonClicked() {
		Table table = db.showPersonSolarEnergie();
		changeTable(table);
	}

	/**
	 * Bei Click zeige alle Endgeraete
	 */
	public void showAllEndgeraeteClicked() {
		Table table = db.showAllEndgeraete();
		changeTable(table);
	}

	/**
	 * Ein neues Fenster "table.fxml" wird erstellt um eine als argument
	 * uebergebene Tabelle anzuzeigen.
	 * 
	 * @param table
	 */
	public void changeTable(Table table) {
		try {
			tableFin = FXMLLoader.load(getClass().getResource("table.fxml"));
			Scene scene = new Scene(tableFin, 650, 250);
			stage.setTitle("Datenbank TechnikVerleih - TableView");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Die tabelle wird geleert
		tableFin.getColumns().clear();
		tableFin.getItems().clear();

		// For schleife geht jede Spalte der Tabelle ab
		for (int i = 0; i < table.getTableCount(); i++) {
			// Eine Spalte wird erstellt
			TableColumn<List<String>, String> column = new TableColumn<List<String>, String>();
			column.setText(table.getColumn(i));
			final int j = i;
			// Jede Zelle wird befuellt
			column.setCellValueFactory((param) -> {
				List<String> list1 = param.getValue();
				return new SimpleStringProperty(list1.get(j) != null ? list1.get(j) : "null");
			});
			// Die gefuellte Spalte wird hinzugefuegt
			tableFin.getColumns().add(column);
		}
		// Daten werden hinzugefuegt
		for (int i = 0; i < table.getRowCount(); i++) {
			tableFin.getItems().add(table.getRow(i));
		}
	}

	/**
	 * Bei Click wird der User eingelogged
	 */
	public void loginButtonClicked() {
		// Abfrage ob alle Felder ausgefuellt sind
		if ((usernameText.getText().isEmpty() || passwordText.getText().isEmpty())) {
			fehlerText.setText("Bitte Username \nund Password \neingeben!");
		} else {
			// Falls alle Felder ausgefuellt sind werden beide Inhalte der
			// Textfenster gegettet
			db.connect(usernameText.getText(), passwordText.getText());
			if (db.getConnection()) {
				try {
					// Bei erfolgreichem Login wird das naechste Fenster
					// geoeffnet
					root = FXMLLoader.load(getClass().getResource("control.fxml"));
					Scene scene = new Scene(root, 600, 400);
					Stage appStage = (Stage) button1.getScene().getWindow();
					appStage.setScene(scene);
					appStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Bei Click wird der User ausgelogged
	 */
	public void disconnectButtonClicked() {
		try {
			db.disconnect();
			try {
				root = FXMLLoader.load(getClass().getResource("main.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Anzeigen des LoginScreens
			Scene scene = new Scene(root, 600, 400);
			Stage appStage = (Stage) end1.getScene().getWindow();
			appStage.setTitle("Datenbank TechnikVerleih");
			appStage.setScene(scene);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Methode zum Hinzufuegen eines Endgeraets in die DatenbankTabelle
	 * Endgeraet Die Tabelle besteht aus 3 Spalten "InventarisierungsNummer",
	 * "Modellbezeichnung" und "AnschaffungsDatum". Ein Endgeraet kann nur dann
	 * hinzugefuegt werden, wenn die "Modellbezeichnung" schon vorhanden ist.
	 * Falls sie nicht vorhanden ist, wird ein neues Fenster zum hinzufuegen
	 * dieser "Modellbezeichnung" geoeffnet.
	 */
	public void endgeraetHinzufuegen() {
		// Abfrage ob alle Textfelder ausgefuellt sind
		if (end1.getText().isEmpty() | end2.getText().isEmpty() | end3.getText().isEmpty()) {
			textMid.setText("*BITTE ALLE FELDER AUSFUELLEN!!!");

		} else {
			// Wenn alle Textfelder ausgefuellt sind, getten des Inputs
			int InventarisierungsNummer = Integer.parseInt(end1.getText());
			String Modellbezeichnung = end2.getText();
			String AnschaffungsDatum = end3.getText();

			// Abfrage ob die eingegebene Modellbezeichnung vorhanden ist, falls
			// nicht oeffnet sich ein Fenster zum Hinzufuegen
			if (!db.bezeichnerExist(Modellbezeichnung)) {
				try {
					root = FXMLLoader.load(getClass().getResource("modellbez.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Scene scene = new Scene(root, 600, 230);
				stage.setTitle("Datenbank TechnikVerleih - Modellbezeichnung Hinzufuegen");
				stage.setScene(scene);
				stage.show();
				HBox box1 = (HBox) root.getChildren().get(0);
				TextField textField1 = (TextField) box1.getChildren().get(0);
				textField1.setText(end2.getText());
			} else {
				// Falls die Modellbezeichnung existiert wird das Endgeraet hinzugefuegt
				db.endgeraetAdd(InventarisierungsNummer, Modellbezeichnung, AnschaffungsDatum);
				textMid.setText("Engeraet hinzugefuegt!");

				// Die Textfelder werden geleert
				end1.clear();
				end2.clear();
				end3.clear();
			}
		}
	}

	/**
	 * Methode zum hinzufuegen einer "Modellbezeichnung" in die DatenbankTabelle "Modellbezeichnung". Hier werden 2 Werte erwartet,
	 * "Modellbezeichnung" und "EndgeraetName".
	 */
	public void modellbezeichnungHinzufuegen() {
		// Abfrage ob die Textfelder ausgefuellt sind
		if (mod1.getText().isEmpty() | mod2.getText().isEmpty()) {
			textMod.setText("*BITTE ALLE FELDER AUSFUELLEN!!!");
		} else {
			// Falls die Textfelder ausgefuellt sind -> getten der Inhalte
			String Modellbezeichnung = mod1.getText();
			String EndgeraetName = mod2.getText();

			// Hinzufuegen der Modellbezeichnung in die Datenbank
			db.modellbezeichnungAdd(Modellbezeichnung, EndgeraetName);
			textMod.setText("Engeraet hinzugefuegt!");

			// Die Textfelder werden geleert
			mod1.clear();
			mod2.clear();
		}
	}

	
	// Schliesst das Fenster zum hinzufuegen der Modellbezeichnung
	public void modellbezClose() {
		Stage appStage = (Stage) mod1.getScene().getWindow();
		appStage.close();

	}
}
