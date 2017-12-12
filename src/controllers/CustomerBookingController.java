package controllers;

import application.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The graphical layout of the cinema allowing booking multiple seats for a particular screening
 * <p>
 * Design Pattern: MVC
 * <p>
 * References:
 * - Dialog boxes http://code.makery.ch/blog/javafx-dialogs-official/
 */
public class CustomerBookingController implements Initializable {

    /**
     * The screening which has been selected
     */
    static Screening selectedScreening;

    /**
     * The seats which have been selected to book
     */
    private ArrayList<Seat> selectedSeats = new ArrayList<>();

    @FXML
    private Text totalCostText;

    @FXML
    private Label Time;

    @FXML
    private Label screeningDate;

    @FXML
    private Label filmTitle;

    @FXML
    private ListView<Seat> seatListView;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private GridPane gridPaneSeats;

    private int totalCost = 0;
    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    //TODO: FEATURE send booking confirmation to user's E-Mail address via   e-Mail client References: https://codereview.stackexchange.com/questions/114005/javafx-email-client


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filmTitle.setText(selectedScreening.getFilmTitle());

        screeningDate.setText(selectedScreening.getMediumDate());

        updateTotalCost();

        initSeatingPlan();
    }

    /**
     * Dialog window showing the booking summary and asking the customer to confirm their booking
     *
     * @param event the
     */
    @FXML
    private void confirmBookingBtnHandler(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Get the Stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        // Add a custom icon to the dialog window box.
        stage.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));

        alert.setTitle("Confirm Booking");
        alert.setHeaderText("Confirm Booking");
        alert.setContentText("Do you wish to pay for " + String.valueOf(selectedSeats.size()) + " seats");

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOne = new ButtonType("Pay Now");

        alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeOne);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonTypeOne) {
            // customer has choosen to book the seats
            alert.close();
            try {
                CustomerPaymentsController.selectedScreening = selectedScreening;
                CustomerPaymentsController.seats = selectedSeats;
                Navigation.loadCustFxml(Navigation.CUST_PAYMENTS_VIEW);
            } catch (IOException e) {
                LOGGER.logp(Level.WARNING, "CustomerBookingController",
                        "confirmBookingBtnHandler", "Unable to switch to payments view" + e);
                e.printStackTrace();
            }
        }

    }

    // TODO: Display booking summary

    /**
     * Create the graphical layout of the seating plan with different images
     * for available seats, taken seats and selected seats
     */
    private void initSeatingPlan() {

        gridPaneSeats.getChildren().clear();

        // add the labels to the grid i.e. Column numbers 1-8 and row letters A to E
        initGridLabels();

        String[] rowsLabels = new String[]{"A", "B", "C", "D", "E"};

        // create the 5 by 8 cinema GUI
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 9; j++) {
                try {
                    Seat seat = SeatDAO.getSeatByLocation(j, rowsLabels[i]);

                    // check the seat has been found
                    if (seat == null) {
                        LOGGER.logp(Level.WARNING, "CustomerBookingController", "initSeatingPlan", "Unable to find seat");
                        return;
                    }

                    // normal seat
                    ImageView seatViewImage = new ImageView("/resources/avaliable-seat.png");
                    seatViewImage.setFitWidth(60.0);
                    seatViewImage.setFitHeight(60.0);

                    // taken seat
                    ImageView takenSeatImage = new ImageView("/resources/taken-seat.png");
                    takenSeatImage.setFitWidth(60.0);
                    takenSeatImage.setFitHeight(60.0);

                    // selected seat
                    ImageView selectedSeatImage = new ImageView("/resources/selected-seat.png");
                    //
                    selectedSeatImage.setFitWidth(60.0);
                    selectedSeatImage.setFitHeight(60.0);

                    Button btn = new Button();
                    btn.setGraphic(seatViewImage);

                    // see if the seat has already been booked by seatId and screeningId
                    Booking bookingInSeat = BookingDAO.getBooking(seat.getId(), selectedScreening.getId());

                    if (bookingInSeat == null) {
                        // the seat has not been booked

                        // handle the seat btn being clicked
                        btn.setOnAction((ActionEvent e) -> {
                            if (!selectedSeats.contains(seat)) {
                                // the seat has not yet been selected so add it to the list of selected seats
                                selectedSeats.add(seat);
                                btn.setGraphic(selectedSeatImage);
                            } else {
                                // the seat has already been selected so now remove it
                                selectedSeats.remove(seat);
                                btn.setGraphic(seatViewImage);
                            }
                            // refresh the list of selected seats
                            updateTotalCost();
                            updateListOfSelectedSeats();
                        });
                    } else {
                        // the seat has been booked so show an error message

                        btn.setGraphic(takenSeatImage);
                        btn.setOnAction((ActionEvent e) -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);

                            alert.setTitle("Seat Booked");
                            alert.setHeaderText("Error - Seat already booked");
                            alert.setContentText("Please select a seat which has not yet been booked");

                            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            alert.getButtonTypes().setAll(buttonTypeCancel);

                            alert.show();
                        });
                    }

                    //TODO: Set the button color to white
                    gridPaneSeats.add(btn, j, i);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.logp(Level.WARNING, "CustomerBookingController", "createSeatingPlan", "Failed to add button to 'gridPaneSeats'. See: " + e);
                }
            }
        }
    }

    /**
     * Shows which seats have been selected for booking
     */
    private void updateListOfSelectedSeats() {
        ObservableList<Seat> seatObservableList = FXCollections.observableList(selectedSeats);
        seatListView.setItems(seatObservableList);
    }

    /**
     * Displays the labels of the grid i.e Columns 1 to 8 and Rows A to E
     */
    public void initGridLabels() {

        // Rows
        for (int row = 0; row < 5; row++) {
            Label text = new Label(Character.toString((char) (65 + row)));
            text.setFont(new Font(30.0));
            text.setLayoutX(-10.0);
            text.setLayoutY(0);
            text.toFront();
            gridPaneSeats.add(text, 0, row);
        }

        // columns
        for (int column = 1; column < 9; column++) {
            Label text = new Label(String.valueOf(column));
            text.setFont(new Font(30.0));
            text.setLayoutX(-10.0);
            text.setLayoutY(0);
            text.toFront();
            gridPaneSeats.add(text, column, 5);
        }
    }

    public void updateTotalCost() {
        totalCost = selectedSeats.size() * 5;

        totalCostText.setText("£" + String.valueOf(totalCost));
    }


}


