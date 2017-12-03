package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.util.ResourceBundle;


public class EmployeeHomeController implements Initializable {

    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality
    //TODO: source: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx

    @FXML
    private Button CreateMovieButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.CreateMovieButton.setTooltip(
                new Tooltip("Button of doom")
        );
    }


    /**
     *
     * Purpose: allows user to also change to movie creation view from within his scene
     * @param event
     */
    @FXML
    private void createMovie(ActionEvent event){

        //ToDo: re-direct to "create-new-movie" view

        EmployeeRootController emplRootController = new EmployeeRootController();
        emplRootController.openMovieFormView(event);
    }

    @FXML
    private void exportToCSV(){
        //TODO: add exporting to CSV functionality (Button triggers downloading all current movies including titles, seats booked etc.)

    }

    @FXML
    private void openSeatsBooked(){
        //TODO: open a movie's specific "seats booked overview" +
    }

}
