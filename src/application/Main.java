package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	//trying to import Glyphicons into the app
//	static {
//		Font.loadFont(Main.class.getResource("/font-awesome-4.7.0/fonts/fontawesome-webfont.ttf").toExternalForm(), 10);
//	}

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
}

