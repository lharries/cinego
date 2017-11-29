package application;
	
import com.sun.tools.hat.internal.model.Root;
import javafx.application.Application;
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
	
	private static Stage loginStage, primaryStage;
	private static Parent root;
	private static CustomerProgramController CustomerProgramController;


	@Override
	public void start(Stage primaryStage) {
		try {	

			//launches the login view upon running program
			root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
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
		showCustomerRoot();
		//showCustomerProgram();
	}

//	/**
//	 * shows customer
//	 *
//	 */
//	private static void showCustomerProgram() {
//
//		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(Main.class.getResource("/views/Movies.fxml"));
//			customerProgram = loader.load();
//			CustomerProgramController = loader.getController();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * loads the customerRoot
	 *
	 */

	private static void showCustomerRoot() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/views/customerRoot.fxml"));
		
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

