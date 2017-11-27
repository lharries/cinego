package application;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class MoviesController implements Initializable{

    @FXML
    private Label userLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void getMovies(String movies){
        userLbl.setText(movies);
    }


}
