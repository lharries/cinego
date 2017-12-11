package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.CustomerDAO;
import models.Employee;
import models.User;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {


	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static User user;

	private static Parent root;

	public static Employee employee;

	public static Scene scene;

	protected static Stage primaryStage;


	/**
	 * Purpose: runs the Java application and opens the GUI
	 *
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {	

			Main.primaryStage = primaryStage;
			//launches the loginCust view upon running program

			root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
			root.setCache(true);
			root.setCacheHint(CacheHint.SPEED);
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.logp(Level.WARNING, "Main", "start", "Failed to render primaryStage. See: " + e);
		}
	}
	
	public static void main(String[] args) {

		try {
			user = CustomerDAO.login("customer", "customerpassword");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} launch(args);
	}
}

