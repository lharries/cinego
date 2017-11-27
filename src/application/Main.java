package application;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Main extends Application {

	Stage window;
	Scene login, movies, seating;

	@Override
	public void start(Stage primaryStage) {

		window = primaryStage;
		Button button1 = new Button("Go to 'Movies' ");
		button1.setOnAction(event -> window.setScene(movies));

		try {

			//launches the login view upon running program
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			final ImageView backgroundImage = new ImageView();
			Image image1 = new Image(new FileInputStream("/Users/kai/code/CineGo/src/resources/films.jpg"));
			backgroundImage.setImage(image1);
			//add image to background of Login.fxml
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
