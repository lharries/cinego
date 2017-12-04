package application;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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



    //testing colour changing rectangles for selectable cinema chairs;
    @FXML
    private Button buttonTest;
    @FXML
    private Rectangle testRectangle;
    int count = 0;


    //TODO: make cinema seats selectable (change colour, store seat identifier, disable booked seats to be chosen
    //TODO: add booking summary at the side: display (push) movie title, date + time, populate seatListView with chosen seats (Row + Column)
    //TODO: fxids= movieTitle, Date, Time, seatListView, bookingConfirmationClickHandler

    //TODO: FEATURE send booking confirmation to customer's E-Mail address via   e-Mail client source: https://codereview.stackexchange.com/questions/114005/javafx-email-client


    //TODO: @Kai test button behind chair image if it makes it clickable and colours whether that's enough to make the chair turn green / red



    @FXML
    private void confirmMovieBooking(ActionEvent event){
    //TODO: trigger a booking summary to be displayed (should we do an additional summary or is the one above the button enough?)
    //TODO: add order to customer profile's history view!


        //jumps back to customer's profile view after having clicked the Confirm booking button (think about triggering the order with a JDialogPane)
        CustomerRootController controller = new CustomerRootController();
        controller.openProfileView(event);
    }






    @FXML
    private void selectSeat(){

//        buttonTest.setOnAction(new EventHandler<ActionEvent>() {
//            Color colour = (Color)buttonTest.getBackground().getFills().get(0).getFill();
//
//            if(color == Color.RED){
//
//            }
//            String colour = buttonTest.getStyle("-fx-fill");
//            @Override public void handle(ActionEvent e) {
//                buttonTest.setText("Accepted");
//            }
//        });

        //selected means modulo 1
        count++;
        if(count%2 == 1){
            this.testRectangle.setStyle("-fx-fill: #ffffff");
            this.buttonTest.setStyle("-fx-background-color: #ffffff");
        }

        else{
            this.testRectangle.setStyle("-fx-fill: #d34a19");
            this.buttonTest.setStyle("-fx-background-color: #d34a19");
        }

    }

    @FXML
    private void unselectSeat(){
        this.testRectangle.setStyle("-fx-fill: #cccccc");
    }



    //testing the idea of having clickable seats / with a colour changes depending on whether they were selected
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //renders background image of the view
        BufferedImage bufferedBackground = null;
        try {
            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
//         bufferedBackground = ImageIO.read(new File("src/resources/cinBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
        this.backgroundImg.setImage(background);




    //works!
        Image imageDecline = new Image(Main.class.getResource("/resources/seat.png").toExternalForm(), 80, 60, true, true);
//                getClass().getResourceAsStream("/resources/cinestar.png"));
        this.buttonTest.setGraphic(new ImageView(imageDecline));

//        buttonTest.setStyle("-fx-background-image: url('/resources/cinestar.png')");

    }
}
