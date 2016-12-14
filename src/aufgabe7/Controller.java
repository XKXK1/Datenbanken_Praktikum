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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	Button button1, buttonDisconnect, buttonHinzufuegen,buttonResult1,buttonResult2;

	@FXML
	TableView<List<String>> table1;

	@FXML
	TableView<List<String>> tableFin;

	public void showAllPersonsButtonClicked() {
		Table table = db.showAllPersons();
		changeTable(table);
	}

	public void showPersonSolarButtonClicked() {
		Table table = db.showPersonSolarEnergie();
		changeTable(table);
	}
	
	public void showAllEndgeraeteClicked(){
		Table table = db.showAllEndgeraete();
		changeTable(table);
	}

	public void changeTable(Table table) {
		try {
			tableFin = FXMLLoader.load(getClass().getResource("table.fxml"));
			Scene scene = new Scene(tableFin, 550, 250);
			stage.setTitle("Datenbank TechnikVerleih - TableView");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tableFin.getColumns().clear();
		tableFin.getItems().clear();

		for (int i = 0; i < table.getTableCount(); i++) {
			TableColumn<List<String>, String> column = new TableColumn<List<String>, String>();
			column.setText(table.getColumn(i));
			final int j = i;
			column.setCellValueFactory((param) -> {
				List<String> list1 = param.getValue();
				return new SimpleStringProperty(list1.get(j) != null ? list1.get(j) : "null");
			});
			tableFin.getColumns().add(column);
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			tableFin.getItems().add(table.getRow(i));
		}
	}

	public void loginButtonClicked() {
		if ((usernameText.getText().isEmpty() || passwordText.getText().isEmpty())) {
			fehlerText.setText("Bitte Username \nund Password \neingeben!");
		} else {
			db.connect(usernameText.getText(), passwordText.getText());
			if (db.getConnection()) {
				try {
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
	
	public void loginButtonPressed(){
		button1.setStyle("-fx-border-color: black" + ";-fx-background-color: white");
		button1.setTextFill(Color.BLACK);		
	}
	
	public void loginButtonReleased(){
		button1.setStyle("-fx-border-color: white" + ";-fx-background-color: transparent");
		button1.setTextFill(Color.WHITE);
	}

	public void disconnectButtonClicked() {
		try {
			db.disconnect();
			try {
				root = FXMLLoader.load(getClass().getResource("main.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Scene scene = new Scene(root, 600, 400);
			Stage appStage = (Stage) end1.getScene().getWindow();
			appStage.setTitle("Datenbank TechnikVerleih");
			appStage.setScene(scene);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void endgeraetHinzufuegen() {
		if (end1.getText().isEmpty() | end2.getText().isEmpty() | end3.getText().isEmpty()) {
			textMid.setText("*BITTE ALLE FELDER AUSFUELLEN!!!");

		} else {			
			int InventarisierungsNummer = Integer.parseInt(end1.getText());
			String Modellbezeichnung = end2.getText();
			String AnschaffungsDatum = end3.getText();
			
			if (!db.bezeichnerExist(Modellbezeichnung)){	
				
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
			
			db.endgeraetAdd(InventarisierungsNummer, Modellbezeichnung, AnschaffungsDatum);
			textMid.setText("Engeraet hinzugefuegt!");

			end1.clear();
			end2.clear();
			end3.clear();
			}
		}
	}
	
	public void modellbezeichnungHinzufuegen(){
		if (mod1.getText().isEmpty() | mod2.getText().isEmpty()) {
			textMod.setText("*BITTE ALLE FELDER AUSFUELLEN!!!");
		} else {			
			String Modellbezeichnung = mod1.getText();
			String EndgeraetName = mod2.getText();

			db.modellbezeichnungAdd(Modellbezeichnung, EndgeraetName);
			textMod.setText("Engeraet hinzugefuegt!");

			mod1.clear();
			mod2.clear();
			}		
	}
	
	
	public void modellbezClose(){
		Stage appStage = (Stage) mod1.getScene().getWindow();
		appStage.close();
		
	}
}
