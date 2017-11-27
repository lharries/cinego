package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class  LoginController implements Initializable {

    public LoginModel loginModel = new LoginModel();


    @FXML
    private Label isConnected;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }
    public void login(javafx.event.ActionEvent event) {
        try {
            if(loginModel.isLoggedIn(txtUsername.getText(),txtPassword.getText())){
                isConnected.setText("Username & password is correct");

            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();


            //launches the login view upon running program
            Pane root = loader.load(getClass().getResource("/application/Movies.fxml").openStream());
            MoviesController moviesController = (MoviesController) loader.getController();
            moviesController.getMovies(txtUsername.getText());


            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            final ImageView backgroundImage = new ImageView();
            Image image1 = new Image(new FileInputStream("/Users/kai/code/CineGo/src/resources/films.jpg"));
            backgroundImage.setImage(image1);
            //add image to background of Login.fxml
            primaryStage.setScene(scene);
            primaryStage.show();


            }else{
                isConnected.setText("Username & password is not correct");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            isConnected.setText("Username & password is not correct");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goMoviesView(ActionEvent event){
        

    }






}