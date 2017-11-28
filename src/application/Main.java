package application;//package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

//	Stage window;
//	Scene login, movies, seating;
//
//	@Override
//	public void start(Stage primaryStage) {
//
//
//		try {
//
//			//launches the login view upon running program
//			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//
//			final ImageView backgroundImage = new ImageView();
//			Image image1 = new Image(new FileInputStream("/Users/kai/code/CineGo/src/resources/films.jpg"));
//			backgroundImage.setImage(image1);
//			//add image to background of Login.fxml
//			primaryStage.setScene(scene);
//			primaryStage.show();
//
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//}


	@Override
	public void start(Stage stage) throws Exception{
		stage.setTitle("CineGo - Cinema Booking System");

		stage.setScene(createScene(loadMainPane()));
		stage.show();
	}

	/**
	 * Loads the main fxml layout.
	 * Sets up the vista switching VistaNavigator.
	 * Loads the first vista into the fxml layout.
	 *
	 * @return the loaded pane.
	 * @throws IOException if the pane could not be loaded.
	 */
	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();

		Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(VistaNavigator.MAIN));

		MainController mainController = loader.getController();

		VistaNavigator.setMainController(mainController);
		VistaNavigator.loadVista(VistaNavigator.VISTA_1);

		return mainPane;
	}

	/**
	 * Creates the main application scene.
	 *
	 * @param mainPane the main application layout.
	 *
	 * @return the created scene.
	 */
	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(mainPane);
		scene.getStylesheets().setAll(getClass().getResource("vista.css").toExternalForm());
		return scene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}




