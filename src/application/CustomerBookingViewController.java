package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import models.Seat;
import models.SeatDAO;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerBookingViewController implements Initializable {

    private Seat selectedSeat;

    @FXML
    private Label Time;

    @FXML
    private Label Date;

    @FXML
    private Label movieTitle;

    @FXML
    private ListView seatListView;

    @FXML
    private ImageView backgroundImg;

    @FXML
    public GridPane gridPaneSeats;


    //TODO: make cinema seats selectable (change colour, store seat identifier, disable booked seats to be chosen
    //TODO: add booking summary at the side: display (push) movie title, date + time, populate seatListView with chosen seats (Row + Column)
    //TODO: fxids= movieTitle, Date, Time, seatListView, bookingConfirmationClickHandler

    //TODO: FEATURE send booking confirmation to customer's E-Mail address via   e-Mail client source: https://codereview.stackexchange.com/questions/114005/javafx-email-client


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
//        setupBackgroundImage();
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
        //TODO: add order to customer profile's history view!


//        //JDialog querying for correct input value: source: http://code.makery.ch/blog/javafx-dialogs-official/
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        // Get the Stage
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//
//        // Add a custom icon.
//        stage.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
//
//        alert.setTitle("Booking Confirmation");
//        alert.setHeaderText("THIS IS WHERE YOUR BOOKING DETAILS WOULD GO VIA VARIABLES, Luke!");
//        alert.setContentText("Luke, I'm awesome");
//
//        ButtonType buttonTypeOne = new ButtonType("Pay now");
//        ButtonType buttonTypeTwo = new ButtonType("Two");
//        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
//
//        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == buttonTypeOne) {
//            //jumps back to customer's profile view after having clicked the Confirm booking button (think about triggering the order with a JDialogPane)
//            CustomerRootController controller = new CustomerRootController();
//            controller.openProfileView(event);
//        } else if (result.get() == buttonTypeTwo) {
//            //test
//            System.err.println("haha it works");
//        }

    }

    private void createSeatingPlan() {
        String[] rows = new String[]{"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 9; j++) {
                try {
                    Seat seat = SeatDAO.getSeatByLocation(rows[i], j);

                    ImageView seatView = new ImageView("/resources/seat.png");
                    seatView.setFitWidth(60.0);
                    seatView.setFitHeight(60.0);

                    Button btn = new Button();
                    btn.setGraphic(seatView);
                    btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            selectedSeat = seat;
                        }
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

    private void displaySelectedSeat() {

    }


}


