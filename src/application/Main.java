package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.CustomerDAO;
import models.User;

import java.sql.SQLException;

public class Main extends Application {

	//trying to import Glyphicons into the app
//	static {
//		Font.loadFont(Main.class.getResource("/font-awesome-4.7.0/fonts/fontawesome-webfont.ttf").toExternalForm(), 10);
//	}

	public static User user;

	private static Parent root;

//	private static AnchorPane customerProgram;
//	private static BorderPane customerRoot;

	private static Stage loginStage, primaryStage;
//	private static CustomerMovieProgramController CustomerMovieProgramController;

	/**
	 * Purpose: runs the Java application and opens the GUI
	 *
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {	

			//launches the loginCust view upon running program

			root = FXMLLoader.load(getClass().getResource("/views/CustomerBookingView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		try {
			user = CustomerDAO.login("customer", "customerpassword");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		launch(args);
	}
}

