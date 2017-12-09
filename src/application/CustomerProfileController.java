package application;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerProfileController implements Initializable{

    @FXML
    private Button updateProfileBttn, editProfileBttn;
    
    @FXML
    private ImageView backgroundImg;

    @FXML
    private TextField custFirstNameField, custLastNameField, custEmailField, custPhone;


    @FXML
    private Button deleteBooking;

    boolean textFieldEditable = false;

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn titleColBookingTable, dateColBookingTable, timeColBookingTable, seatsColBookingTable;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //initializes customer profile input fields and sets them to not editable
        setCustProfileFieldsEnabled(textFieldEditable);

        titleColBookingTable.setCellValueFactory(new PropertyValueFactory<Screening, String>("filmTitle"));
        dateColBookingTable.setCellValueFactory(new PropertyValueFactory<Screening, String>("date"));
//        timeColBookingTable.setCellValueFactory(new PropertyValueFactory<Screening, String>("description"));
        seatsColBookingTable.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("seatId"));

//        populateBookingsTable();

    }

    /**
     * Purpose: updates the moviesTable with movie specific data from the database
     */
    private void populateBookingsTable(){


        //        try {
//            moviesData = FilmDAO.getFilmObservableList();
//            moviesTable.setItems(moviesData);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }



    /**
     * Purpose: is called by clicking the edit Profile button and then calls the setCustProfileFieldsEnabled
     * method in order to set the fields to be editable and change the prompttext to normal text
     *
     */
    @FXML
    private void setCustProfileEditable() {

        textFieldEditable = true;
        setCustProfileFieldsEnabled(textFieldEditable);
    }

    private void setCustProfileFieldsEnabled(boolean textFieldEditable){


        if(textFieldEditable){
            updateProfileBttn.setDisable(!textFieldEditable);
            editProfileBttn.setDisable(textFieldEditable);

            custFirstNameField.setText(Main.user.getFirstName());
            custFirstNameField.setEditable(textFieldEditable);

            custLastNameField.setText(Main.user.getLastName());
            custLastNameField.setEditable(textFieldEditable);

            custEmailField.setText(Main.user.getEmail());
            custEmailField.setEditable(textFieldEditable);
            //TODO: store customer's phone number in database
//        custPhone.setText(Main.user.getPhone());
            custPhone.setEditable(textFieldEditable);
        } if(!textFieldEditable){
            updateProfileBttn.setDisable(!textFieldEditable);
            editProfileBttn.setDisable(textFieldEditable);

            custFirstNameField.setPromptText(Main.user.getFirstName());
            custFirstNameField.setEditable(textFieldEditable);

            custLastNameField.setPromptText(Main.user.getLastName());
            custLastNameField.setEditable(textFieldEditable);

            custEmailField.setPromptText(Main.user.getEmail());
            custEmailField.setEditable(textFieldEditable);
            //TODO: store customer's phone number in database
//        custPhone.setText(Main.user.getPhone());
            custPhone.setEditable(textFieldEditable);
        }
    }

    /**
     * Purpose: called upon clicking the update profile button. Updates the database with the user's
     * input data upon click event.
     *
     */
    @FXML
    private void updateCustomerProfile(){

        //Updates the user's information in the database
        Main.user.setFirstName(custFirstNameField.getText());
        Main.user.setLastName(custLastNameField.getText());
        Main.user.setEmail(custEmailField.getText());
        try {
            CustomerDAO.updateCustomerDetails(Main.user.getFirstName(),Main.user.getLastName(),Main.user.getEmail(), Main.user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //pop-up to inform user about successfully updating data - source: https://stackoverflow.com/questions/39281622/javafx-how-to-show-temporary-popup-osd-when-action-performed
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setTitle("Cinego");
        alert.setHeaderText("Profile Update");
        alert.setContentText("Your profile was successfully updated, "+ Main.user.getFirstName());
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> popup.hide());
        popup.show();
        delay.play();

        //sets editability of profile back to disabled
        setCustProfileFieldsEnabled(false);
    }

    @FXML
    private void getSelectedBooking(){


        deleteBooking.setDisable(false);

    }

    @FXML
    private void deleteMovieBooking(){

        //TODO 2: Add error popup for movies that are in the past (can't delete them!)
        //TODO 3: delete the actual booking with a JDialogBOx pop-up asking if you're sure to delete (https://www.youtube.com/watch?v=oZUGMpGQxgQ)




    }


    //TODO: //TODO: add ability to select movies from the list and delete them (see employeeHomeController: getScreeningID() )


}
