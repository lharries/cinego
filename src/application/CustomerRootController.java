package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;




public class CustomerRootController implements Initializable {

    @FXML
    private Label userLbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Test - CustomerRootController");
    }

    public void getMovies(String movies){
        userLbl.setText(movies);
    }
}

