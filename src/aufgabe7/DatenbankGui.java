package aufgabe7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DatenbankGui extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Pane root = FXMLLoader.load(getClass().getResource("main.fxml"));

		Scene scene = new Scene(root, 600, 400);
		

		primaryStage.setTitle("Datenbank TechnikVerleih");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.sizeToScene();
		//primaryStage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
