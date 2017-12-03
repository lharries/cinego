package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CustomerProfileController {

@FXML
    private Button editProfile;



@FXML
private void editCustomerProfile() {
    // TODO: Add ability to edit customer data. First click enable editing and change button lable to say "update".
    // TODO: Next click: push customer data into data base via "update" sql command
}


@FXML
private void deleteMovieBooking(){
    //TODO General: Delete button located in each movie booking that is still in the future
    //TODO 1: Add delete button only for movies that are in the future
    //TODO 2: Add error popup for movies that are in the past (can't delete them!)
    //TODO 3: delete the actual booking with a JDialogBOx pop-up asking if you're sure to delete (https://www.youtube.com/watch?v=oZUGMpGQxgQ)

}





}
