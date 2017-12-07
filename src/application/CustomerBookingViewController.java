package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerBookingViewController implements Initializable {

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
        setupBackgroundImage();
        createSeatingPlan();

    }

    private void setupBackgroundImage() {
        //renders background image of the view
        BufferedImage bufferedBackground = null;
        try {
            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
        this.backgroundImg.setImage(background);
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
        ImageView seat = new ImageView("/resources/seat.png");
        seat.setFitWidth(60.0);
        seat.setFitHeight(60.0);
        seat.setX(100.0);
        seat.setY(5.0);


        ImageView seat2 = new ImageView("/resources/seat.png");
        seat2.setFitWidth(60.0);
        seat2.setFitHeight(60.0);
        seat2.setX(100.0);
        seat2.setY(5.0);

        ImageView seat3 = new ImageView("/resources/seat.png");
        seat3.setFitWidth(60.0);
        seat3.setFitHeight(60.0);
        seat3.setX(100.0);
        seat3.setY(5.0);

        ImageView seat4 = new ImageView("/resources/seat.png");
        seat4.setFitWidth(60.0);
        seat4.setFitHeight(60.0);
        seat4.setX(100.0);
        seat4.setY(5.0);


        gridPaneSeats.add(seat, 1, 0);
//        GridPane.setHalignment(gridPaneSeats, HPos.CENTER);
//        gridPaneSeats.setValignment(gridPaneSeats, VPos.CENTER);
        gridPaneSeats.add(seat2, 2, 0);
        gridPaneSeats.add(seat3, 2, 1);
        gridPaneSeats.add(seat4, 1, 1);
    }


}


