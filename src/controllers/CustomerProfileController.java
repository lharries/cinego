package controllers;

import application.Main;
import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import models.*;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GUI showing the customers profile info and their upcoming bookings
 * <p>
 * Design Pattern: MVC
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class CustomerProfileController implements Initializable {


    @FXML
    private Button updateProfileBttn, editProfileBttn;
    @FXML
    private TextField custFirstNameField, custLastNameField, custEmailField;
    @FXML
    private Button deleteBooking, cancelUpdatingProfileBttn;
    @FXML
    private TableView<Booking> bookingsTable;
    @FXML
    private TableColumn titleColBookingTable, dateColBookingTable, seatsColBookingTable;

    private boolean textFieldEditable = false;

    private static int bookingID;

    private Screening selectedScreening;

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());


    /**
     * Initializes the customer profile including Textfields and bookingsTable. Disables editing the Textfields
     * unless chosen by customer
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        enableCustProfileFields(textFieldEditable);
        editProfileBttn.requestFocus();
        initCellFactories();
        populateBookingsTable();
    }


    /**
     * Initialises the bookingsTable columns with customer-specific bookings data
     */
    private void initCellFactories() {

        //retrieve title column data
        titleColBookingTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Booking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Booking, String> param) {
                try {
                    Screening screening = ScreeningDAO.getScreeningById(param.getValue().getScreeningId());
                    if (screening == null) {
                        return new ReadOnlyObjectWrapper<String>("Screening not found");
                    }
                    Film film = FilmDAO.getFilmById(screening.getFilmId());
                    return new ReadOnlyObjectWrapper<String>(film.getTitle());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return new ReadOnlyObjectWrapper<String>("");
            }
        });

        //retrieve date column data
        dateColBookingTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Booking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Booking, String> param) {
                try {
                    Screening screening = ScreeningDAO.getScreeningById(param.getValue().getScreeningId());
                    if (screening == null) {
                        return new ReadOnlyObjectWrapper<String>("Screening not found");
                    }
                    return new ReadOnlyObjectWrapper<String>(screening.getDate());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return new ReadOnlyObjectWrapper<String>("");
            }
        });

        //retrieve seat column data
        seatsColBookingTable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Booking, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Booking, String> param) {
                try {
                    Seat seat = SeatDAO.getSeatsById(param.getValue().getSeatId());
                    return new ReadOnlyObjectWrapper<String>(seat.getName());
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return new ReadOnlyObjectWrapper<String>("");
            }
        });

    }

    /**
     * Populates bookingsTable with data related to customer's bookings
     */
    private void populateBookingsTable() {

        try {
            bookingsTable.setItems(BookingDAO.getBookingObservableList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "CustomerProfileController", "populateBookingsTable", "Failed to populate the bookingsTable. See: " + e);
        }
    }

    /**
     * Called by clicking the editProfile button and then calls the enableCustProfileFields
     * method in order to set the fields to be editable and change the prompttext to normal text
     */
    @FXML
    private void setCustProfileEditable() {

        textFieldEditable = true;
        enableCustProfileFields(textFieldEditable);
    }

    /**
     * @param textFieldEditable
     */

    private void enableCustProfileFields(boolean textFieldEditable) {

        if (textFieldEditable) {

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
        }
        if (!textFieldEditable) {

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
        }

    }

    /**
     * Purpose: called upon clicking the update profile button. Updates the database with the user's
     * input data upon click event.
     */
    @FXML
    private void updateCustomerProfile() {

        //Updates the user's information in the database
        Main.user.setFirstName(custFirstNameField.getText());
        Main.user.setLastName(custLastNameField.getText());
        Main.user.setEmail(custEmailField.getText());
        try {
            CustomerDAO.updateCustomerDetails(Main.user.getFirstName(), Main.user.getLastName(), Main.user.getEmail(), Main.user.getId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.logp(Level.WARNING, "CustomerProfileController", "updateCustomerProfile", "Failed to run db UPDATE query. See: " + e);
        }

        //pop-up to inform user about successfully updating data - References: https://stackoverflow.com/questions/39281622/javafx-how-to-show-temporary-popup-osd-when-action-performed
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setTitle("Cinego");
        alert.setHeaderText("Profile Update");
        alert.setContentText("Your profile was successfully updated, " + Main.user.getFirstName());
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> popup.hide());
        popup.show();
        delay.play();

        //sets editability of profile back to disabled
        enableCustProfileFields(false);
    }

    @FXML
    private void cancelUpdating() {

        enableCustProfileFields(false);
    }

    /**
     * Stores the bookingID of a selected movie into int bookingID which can then be used to delete the booking
     */
    @FXML
    private void getSelectedBooking() {

        Booking booking = bookingsTable.getSelectionModel().getSelectedItem();
        if (booking != null) {
            try {
                selectedScreening = ScreeningDAO.getScreeningById(booking.getScreeningId());
                bookingID = bookingsTable.getSelectionModel().getSelectedItem().getId();
                deleteBooking.setDisable(false);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                LOGGER.logp(Level.WARNING, "CustomerProfileController", "getSelectedBooking", "Failed to fetch selected screening object or bookingID. See: " + e);
            }
        }
    }

    /**
     * References:
     * - https://www.youtube.com/watch?v=oZUGMpGQxgQ
     */
    @FXML
    private void deleteMovieBooking() throws ParseException {

        //checks if selected screening instance is in the past.
        if (selectedScreening.isInPast()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
            popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
            alert.setTitle("Cinego");
            alert.setHeaderText("Booking in the past");
            alert.setContentText("You can't cancel a past booking, " + Main.user.getFirstName());
            alert.showAndWait();

        } else {

            //Declares & instantiates alert prompting user to confirm deleting booking
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
            popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
            alert.setTitle("Cinego");
            alert.setHeaderText("Delete Booking");
            alert.setContentText("Are you sure you want to delete your booking, " + Main.user.getFirstName());
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            ButtonType buttonTypeConfirm = new ButtonType("Delete booking");
            alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeConfirm);

            //waiting for user decision: deletes booking from db upon confirmation & cancel deletion if wanted
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeConfirm) {
                try {
                    BookingDAO.deleteBooking(bookingID);
                    populateBookingsTable();
                    deleteBooking.setDisable(true);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.logp(Level.WARNING, "CustomerProfileController", "deleteMovieBooking", "Failed to run db DELETE query. See: " + e);
                }
            }
            alert.close();
        }
    }
}
