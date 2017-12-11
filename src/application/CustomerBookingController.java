package application;

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

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerBookingController implements Initializable {

    public static Screening selectedScreening;

    private ArrayList<Seat> selectedSeats = new ArrayList<>();

    @FXML
    private Text totalCost;

    @FXML
    private Label Time;

    @FXML
    private Label screeningDate;

    @FXML
    private Label movieTitle;

    @FXML
    private ListView<Seat> seatListView;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private GridPane gridPaneSeats;

    //TODO: FEATURE send booking confirmation to user's E-Mail address via   e-Mail client source: https://codereview.stackexchange.com/questions/114005/javafx-email-client
    // TODO: Changes the colors of the buttons?


    //renders the background image for the scene + commented out code to load chair image into button's background

    /**
     * Purpose: renders the background image upon opening of the scene
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        movieTitle.setText(selectedScreening.getFilmTitle());
        try {
            screeningDate.setText(selectedScreening.getMediumDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initSeatingPlan();

    }

    @FXML
    private void confirmMovieBooking(ActionEvent event) {
        //TODO: trigger a booking summary to be displayed (should we do an additional summary or is the one above the button enough?)
        //TODO: add order to user profile's history view!


        //JDialog querying for correct input value: source: http://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Get the Stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        // Add a custom icon.
        stage.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));

        alert.setTitle("Booking Confirmation");
        alert.setHeaderText("Confirm Booking");
        alert.setContentText("Do you wish to book " + String.valueOf(selectedSeats.size()) + " seats");

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonTypeOne = new ButtonType("Pay now");

        alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeOne);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            System.out.println("Clicked confirm");
            createBooking();
            alert.close();
            initSeatingPlan();
        }

    }

    private void initSeatingPlan() {

        gridPaneSeats.getChildren().clear();

        initGridLines();
        String[] rows = new String[]{"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 9; j++) {
                try {
                    Seat seat = SeatDAO.getSeatByLocation(j, rows[i]);

                    // normal seat
                    ImageView seatViewImage = new ImageView("/resources/seat.png");
                    seatViewImage.setFitWidth(60.0);
                    seatViewImage.setFitHeight(60.0);

                    // taken seat
                    ImageView takenSeatImage = new ImageView("/resources/taken-seat.png");
                    takenSeatImage.setFitWidth(60.0);
                    takenSeatImage.setFitHeight(60.0);

                    // taken seat
                    ImageView selectedSeatImage = new ImageView("/resources/selected-seat.png");
                    selectedSeatImage.setFitWidth(60.0);
                    selectedSeatImage.setFitHeight(60.0);

                    Button btn = new Button();
                    btn.setGraphic(seatViewImage);

                    Booking bookingInSeat = BookingDAO.getBooking(seat.getId(), selectedScreening.getId());

                    if (bookingInSeat != null) {
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
                    } else {
                        btn.setOnAction((ActionEvent e) -> {
                            if (selectedSeats.contains(seat)) {
                                selectedSeats.remove(seat);
                                btn.setGraphic(seatViewImage);
                            } else {
                                selectedSeats.add(seat);
                                btn.setGraphic(selectedSeatImage);
                            }
                            displaySelectedSeats();
                        });
                    }

                    System.out.println(BookingDAO.getBooking(seat.getId(), selectedScreening.getId()));

                    //TODO: Set the button color to white
                    gridPaneSeats.add(btn, j, i);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displaySelectedSeats() {
        ObservableList<Seat> seatObservableList = FXCollections.observableList(selectedSeats);
        seatListView.setItems(seatObservableList);
    }

    public void createBooking() {
        for (Seat selectedSeat :
                selectedSeats) {

            try {
                BookingDAO.insertBooking(Main.user.getId(), true, selectedSeat.getId(), selectedScreening.getId());
                System.out.println("Booked!");
            } catch (SQLException | ClassNotFoundException e) {

                // TODO: Handle this better;
                System.out.println("Booking failed");
                e.printStackTrace();
            }
        }
        initSeatingPlan();
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


}


