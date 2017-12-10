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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.BookingDAO;
import models.Screening;
import models.Seat;
import models.SeatDAO;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());


    //TODO: make cinema seats selectable (change colour, store seat identifier, disable booked seats to be chosen
    //TODO: add booking summary / total cost to 'checkout cart' side:

    //TODO: FEATURE send booking confirmation to user's E-Mail address via   e-Mail client source: https://codereview.stackexchange.com/questions/114005/javafx-email-client



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        movieTitle.setText(selectedScreening.getFilmTitle());
        try {
            screeningDate.setText(selectedScreening.getMediumDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        System.out.println(selectedScreening);
//        System.out.println(selectedScreening.getDate());

        createSeatingPlan();
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
        }

    }

    private void createSeatingPlan() {
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
                    btn.setOnAction((ActionEvent e) -> {
                        if (selectedSeats.contains(seat)) {
                            selectedSeats.remove(seat);
                            btn.setGraphic(seatViewImage);
                        } else {
                            selectedSeats.add(seat);
                            btn.setGraphic(selectedSeatImage);
                        }
                        System.out.println(seat.getId());
                        displaySelectedSeats();
                    });

                    //TODO: Set the button color to white
                    gridPaneSeats.add(btn, j, i);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.logp(Level.WARNING, "CustomerBookingController", "createSeatingPlan", "Failed to add button to 'gridPaneSeats'. See: " + e);
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
                e.printStackTrace();
                LOGGER.logp(Level.WARNING, "CustomerBookingController", "createBooking", "Failed to create a customer booking. See: " + e);
            }
        }
    }


}


