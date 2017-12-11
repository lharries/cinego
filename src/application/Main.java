package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main application built using a MVC design pattern
 */
public class Main extends Application {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * The user which is logged in
     */
    public static User user;

    /**
     * Purpose: runs the Java application and opens the GUI
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage primaryStage) {
        try {

            //launches the login view upon running program
            Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));

            // TODO: Kai please state what this is for
            root.setCache(true);
            root.setCacheHint(CacheHint.SPEED);

            // create the root scene
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "Main", "start", "Failed to render primaryStage. See: " + e);
        }
    }

    /**
     * Launches the main application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

