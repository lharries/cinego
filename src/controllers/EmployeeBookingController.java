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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeBookingController implements Initializable {

    public static Screening selectedScreening;

    public GridPane gridPaneSeats;
    public Label remainingSeatsNumber;
    public Label bookedSeatsNumber;

    @FXML
    private ImageView backgroundImg;

    ArrayList<Seat> selectedSeats;


    //TODO: populate the above fxids= 'Time' + 'Date' + 'Title' + 'seatsBookedPieChart' with their respective data based on the route the employee came from (which movie the employee entered the view from)


    @Override
    public void initialize(URL location, ResourceBundle resources) {


//
//        //TODO: Remove this (just for dev)
//        try {
//            selectedScreening = ScreeningDAO.getScreeningObservableList().get(0);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        createSeatingPlan();

    }

    @FXML
    private void openHomeView(ActionEvent event) throws IOException {
        EmployeeRootController controller = new EmployeeRootController();
        controller.openHomeView(event);
    }

    private void createSeatingPlan() {

        int bookedSeatsCount = 0;

        gridPaneSeats.getChildren().clear();

        initGridLines();
        String[] rows = new String[]{"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 9; j++) {
                try {
                    Seat seat = SeatDAO.getSeatByLocation(j, rows[i]);

//                    System.out.println(getClass().getResource("/resources/seat.png"));

                    // normal seat
                    ImageView seatViewImage = new ImageView(getClass().getResource("/resources/seat.png").toString());
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
                        bookedSeatsCount++;
                        btn.setGraphic(takenSeatImage);
                        btn.setOnMouseClicked(event -> showBookingDetails(bookingInSeat));
                    } else {
                        btn.setDisable(true);
                    }

                    System.out.println(BookingDAO.getBooking(seat.getId(), selectedScreening.getId()));

                    //TODO: Set the button color to white
                    gridPaneSeats.add(btn, j, i);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        bookedSeatsNumber.setText(String.valueOf(bookedSeatsCount));
        remainingSeatsNumber.setText(String.valueOf(40 - bookedSeatsCount));
    }

    public void initGridLines() {

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

    public void showBookingDetails(Booking booking) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // Get the Stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        // Add a custom icon.
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
            e.printStackTrace();
        }

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeCancel);

        alert.show();
    }

}
