package application;

import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import models.*;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class CustomerProfileController implements Initializable{

    @FXML
    private Button updateProfileBttn, editProfileBttn;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private TextField custFirstNameField, custLastNameField, custEmailField, custPhone;

    @FXML
    private Button deleteBooking, cancelUpdatingProfileBttn;

    boolean textFieldEditable = false;

    @FXML
    private TableView<Screening> bookingsTable;

    @FXML
    private TableColumn titleColBookingTable, dateColBookingTable, timeColBookingTable, seatsColBookingTable;

    @FXML
    private ObservableList<Screening> screeningData;

    public static int bookingID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //initializes customer profile input fields and sets them to not editable
        enableCustProfileFields(textFieldEditable);
        editProfileBttn.requestFocus();
        initCellFactories();

        populateBookingsTable();
//        timeColBookingTable.setCellValueFactory(new PropertyValueFactory<Screening, String>("description"));
//        seatsColBookingTable.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("seatId"));

//        populateBookingsTable();

    }

    private void initCellFactories() {

        //TODO: populate the entire BookingsTable with customer's bookings

        titleColBookingTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Screening,String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<Screening,String> param) {
                try {
                    return new ReadOnlyObjectWrapper<String>(FilmDAO.getFilmById(param.getValue().getId()).getTitle());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return new ReadOnlyObjectWrapper<String>("");
            }
        });

        dateColBookingTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Screening,String>, ObservableValue<String>>() {
            @Override public ObservableValue<String> call(TableColumn.CellDataFeatures<Screening,String> param) {
                try {
                    return new ReadOnlyObjectWrapper<String>(param.getValue().getMediumDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return new ReadOnlyObjectWrapper<String>("");
                }
            }
        });

//        seatsColBookingTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Seat,Integer>, ObservableValue<Integer() {
//            @Override public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Seat,Integer> param) {
//                try {
//                    return new ReadOnlyObjectWrapper<Integer>(FilmDAO.getFilmById(param.getValue().getId()).getTitle());
//                } catch (SQLException | ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                return new ReadOnlyObjectWrapper<String>("");
//            }
//        });
    }

    /**
     * Purpose: updates the moviesTable with movie specific data from the database
     */
    private void populateBookingsTable(){
//
//        try {
//            screeningData = ScreeningDAO.get();



        try {
            bookingsTable.setItems(ScreeningDAO.getScreeningObservableList());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    //CURRENT SOLUTION
//        try {
//            bookingsTable.setItems(ScreeningDAO.getScreeningObservableList());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }



//            moviesTable.setItems(moviesData);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * Called by clicking the editProfile button and then calls the enableCustProfileFields
     * method in order to set the fields to be editable and change the prompttext to normal text
     *
     */
    @FXML
    private void setCustProfileEditable() {

        textFieldEditable = true;
        enableCustProfileFields(textFieldEditable);
    }

    /**
     *
     *
     * @param textFieldEditable
     */

    private void enableCustProfileFields(boolean textFieldEditable){

        if(textFieldEditable){

            //buttons
            updateProfileBttn.setDisable(!textFieldEditable);
            cancelUpdatingProfileBttn.setDisable(!textFieldEditable);
            editProfileBttn.setDisable(textFieldEditable);

            //textfields
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

            //buttons
            updateProfileBttn.setDisable(!textFieldEditable);
            cancelUpdatingProfileBttn.setDisable(!textFieldEditable);
            editProfileBttn.setDisable(textFieldEditable);

            //textfields
            custFirstNameField.clear();
            custFirstNameField.setPromptText(Main.user.getFirstName());
            custFirstNameField.setEditable(textFieldEditable);
            custLastNameField.clear();
            custLastNameField.setPromptText(Main.user.getLastName());
            custLastNameField.setEditable(textFieldEditable);
            custEmailField.clear();
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
        enableCustProfileFields(false);
    }

    @FXML
    private void cancelUpdating(){

        enableCustProfileFields(false);
    }

    /**
     * Stores the bookingID of a selected movie into int bookingID which can then be used to delete the booking
     *
     */
    @FXML
    private void getSelectedBooking(){

        bookingID = bookingsTable.getSelectionModel().getSelectedItem().getId();
        System.err.print(bookingID);
        deleteBooking.setDisable(false);

    }

    @FXML
    private void deleteMovieBooking(){

        //TODO 2: Add error popup for movies that are in the past (can't delete them!)
        //TODO 3: delete the actual booking with a JDialogBOx pop-up asking if you're sure to delete (https://www.youtube.com/watch?v=oZUGMpGQxgQ)
        //TODO: add ability to select movies from the list and delete them (see employeeHomeController: getScreeningID() )

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setTitle("Cinego");
        alert.setHeaderText("Delete Booking");
        alert.setContentText("Are you sure you want to delete your booking, "+ Main.user.getFirstName());
        alert.showAndWait();

        try {
            BookingDAO.deleteBooking(bookingID);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        populateBookingsTable();
        deleteBooking.setDisable(true);
    }

}
