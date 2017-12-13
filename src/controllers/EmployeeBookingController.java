package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * View controller allowing employee to see which seats have been booked for a particular screening
 * clicking on a booked seat shows the customers booking information
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class EmployeeBookingController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    public static Screening selectedScreening;

    public GridPane gridPaneSeats;
    public Label remainingSeatsNumber;
    public Label bookedSeatsNumber;


    //TODO: populate the above fxids= 'Time' + 'Date' + 'Title' + 'seatsBookedPieChart' with their respective data based on the route the employee came from (which movie the employee entered the view from)


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSeatingPlan();

    }

    /**
     * Loads the Employee Home screen where they can perform their tasks
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void openHomeView(ActionEvent event) throws IOException {
        EmployeeRootController controller = new EmployeeRootController();
        controller.openHomeView(event);
    }

    /**
     * Create the graphical layout of the seating plan. The booked seats can be click to show customer information
     */
    private void initSeatingPlan() {

        int bookedSeatsCount = 0;

        gridPaneSeats.getChildren().clear();

        // adds the labels for the columns and rows
        initGridLabels();

        // adds the seats with their bookings to the grid
        String[] rows = new String[]{"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 9; j++) {
                try {
                    Seat seat = SeatDAO.getSeatByLocation(j, rows[i]);

                    if (seat == null) {
                        LOGGER.logp(Level.WARNING, "EmployeeBookingController", "initSeatingPlan", "Unable to find seat");
                        return;
                    }

                    // normal seat
                    ImageView seatViewImage = new ImageView(getClass().getResource("/resources/avaliable-seat.png").toString());
                    seatViewImage.setFitWidth(60.0);
                    seatViewImage.setFitHeight(60.0);

                    // taken seat
                    ImageView takenSeatImage = new ImageView(getClass().getResource("/resources/taken-seat.png").toString());
                    takenSeatImage.setFitWidth(60.0);
                    takenSeatImage.setFitHeight(60.0);

                    // taken seat
                    ImageView selectedSeatImage = new ImageView(getClass().getResource("/resources/selected-seat.png").toString());
                    selectedSeatImage.setFitWidth(60.0);
                    selectedSeatImage.setFitHeight(60.0);

                    Button btn = new Button();
                    btn.setGraphic(seatViewImage);

                    Booking bookingInSeat = BookingDAO.getBooking(seat.getId(), selectedScreening.getId());

                    if (bookingInSeat != null) {
                        // the seat has been booked
                        bookedSeatsCount++;

                        btn.setGraphic(takenSeatImage);

                        btn.setOnMouseClicked(event -> showBookingDetails(bookingInSeat));
                    } else {
                        btn.setDisable(true);
                    }

                    //TODO: Set the button color to white
                    gridPaneSeats.add(btn, j, i);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.logp(Level.WARNING, "EmployeeBookingController", "initSeatingPlan", "Unable to create seat" + e);
                }
            }
        }

        // show booking stats
        bookedSeatsNumber.setText(String.valueOf(bookedSeatsCount));
        remainingSeatsNumber.setText(String.valueOf(40 - bookedSeatsCount));
    }

    /**
     * Add the cinema seats labels i.e. row letters A - E and column numbers 1 - 8
     */
    public void initGridLabels() {

        // Rows
        for (int row = 0; row < 5; row++) {
            Label text = new Label(Character.toString((char) (65 + row)));
            System.out.println(text);
            text.setFont(new Font(30.0));
            text.setLayoutX(-10.0);
            text.setLayoutY(0);
            text.toFront();
            gridPaneSeats.add(text, 0, row);
        }

        // columns
        for (int column = 1; column < 9; column++) {
            Label text = new Label(String.valueOf(column));
            System.out.println(text);
            text.setFont(new Font(30.0));
            text.setLayoutX(-10.0);
            text.setLayoutY(0);
            text.toFront();
            gridPaneSeats.add(text, column, 5);
        }
    }

    /**
     * Show the seats booking details in a dialog box
     *
     * @param booking the booking of this seat
     */
    public void showBookingDetails(Booking booking) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setTitle("Booking Details");

        try {
            Customer customer = booking.getCustomer();
            if (customer != null) {
                alert.setContentText("Customer: " + customer.getFirstName() + " " + customer.getLastName() + "\n");
            } else {
                alert.setContentText("Error: Unable to find customer");
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.logp(Level.WARNING, "EmployeeBookingController", "showBookingDetails", "unable to find customer" + e);
            e.printStackTrace();
        }

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeCancel);
        alert.show();
    }

}
