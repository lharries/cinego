package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.*;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerBookingViewController implements Initializable {

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


    //TODO: make cinema seats selectable (change colour, store seat identifier, disable booked seats to be chosen
    //TODO: add booking summary at the side: display (push) movie title, date + time, populate seatListView with chosen seats (Row + Column)
    //TODO: fxids= movieTitle, screeningDate, Time, seatListView, bookingConfirmationClickHandler

    //TODO: FEATURE send booking confirmation to user's E-Mail address via   e-Mail client source: https://codereview.stackexchange.com/questions/114005/javafx-email-client


    //TODO: @Kai test button behind chair image if it makes it clickable and colours whether that's enough to make the chair turn green / red


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

        System.out.println(selectedScreening);
        System.out.println(selectedScreening.getDate());

        createSeatingPlan();


    }

    private void setupBackgroundImage() {
        //renders background image of the view
//        BufferedImage bufferedBackground = null;
//        try {
//            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
//        this.backgroundImg.setImage(background);
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
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displaySelectedSeats() {
        ObservableList<Seat> seatObservableList = FXCollections.observableList(selectedSeats);
        seatListView.setItems(seatObservableList);
    }


}


