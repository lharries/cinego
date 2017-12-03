package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class EmployeeHomeController {



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
