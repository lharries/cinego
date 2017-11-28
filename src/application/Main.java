package application;
	
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

	private static BorderPane customerRoot;
	private static AnchorPane customerProgram;
	
	private static Stage primaryStage;
	private static customerProgramController customerProgramController;


	@Override
	public void start(Stage primaryStage) {
		try {	

			//launches the login view upon running program
			Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void startAsCustomer(){
//		showCustomerRoot();
		showCustomerProgram();
	}
 
	private static void showCustomerProgram() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/views/Movies.fxml"));
			customerProgram = loader.load();
			customerProgramController = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void showCustomerRoot() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/views/Movies.fxml"));
		
		try {
			customerRoot = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(customerRoot);
		primaryStage.setScene(scene);
		primaryStage.show();
		}
	}


}
