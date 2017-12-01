package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class EmployeeHomeController {



    @FXML
    private void createMovie(ActionEvent event){
        //ToDo: re-direct to "create-new-movie" view

        EmployeeRootController emplRootController = new EmployeeRootController();
        emplRootController.openMovieFormView(event);
    }


}
