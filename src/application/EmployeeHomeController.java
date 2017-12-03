package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class EmployeeHomeController {

    //TODO: add tooltips to buttons in order to convey additional information w.r.t. their functionality
    //TODO: source: https://stackoverflow.com/questions/25338873/is-there-a-simple-way-to-display-hint-texts-in-javafx


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
}
