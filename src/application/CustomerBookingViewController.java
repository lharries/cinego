package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Rectangle seat10ColorRect, seat11ColorRect, seat12ColorRect, seat13ColorRect, seat14ColorRect;

    private boolean isSeat10Selected, isSeat11Selected, isSeat12Selected, isSeat13Selected, isSeat14Selected;



    //TODO: make cinema seats selectable (change colour, store seat identifier, disable booked seats to be chosen
    //TODO: add booking summary at the side: display (push) movie title, date + time, populate seatListView with chosen seats (Row + Column)
    //TODO: fxids= movieTitle, Date, Time, seatListView, bookingConfirmationClickHandler

    //TODO: FEATURE send booking confirmation to customer's E-Mail address via   e-Mail client source: https://codereview.stackexchange.com/questions/114005/javafx-email-client


    //TODO: @Kai test button behind chair image if it makes it clickable and colours whether that's enough to make the chair turn green / red


    //renders the background image for the scene + commented out code to load chair image into button's background
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

        //setting background image into button
//        Image imageDecline = new Image(Main.class.getResource("/resources/seat.png").toExternalForm(), 80, 60, true, true);
//        getClass().getResourceAsStream("/resources/cinestar.png"));
//        this.buttonTest.setGraphic(new ImageView(imageDecline));
    }



    @FXML
    private void confirmMovieBooking(ActionEvent event){
    //TODO: trigger a booking summary to be displayed (should we do an additional summary or is the one above the button enough?)
    //TODO: add order to customer profile's history view!


        //jumps back to customer's profile view after having clicked the Confirm booking button (think about triggering the order with a JDialogPane)
        CustomerRootController controller = new CustomerRootController();
        controller.openProfileView(event);
    }


    @FXML
    private void selectSeat(MouseEvent event){

        //fetches triggering seat's fx:id which is used to check against the cases
        ImageView seat = (ImageView) event.getSource();
        String seatID = seat.getId();

        switch(seatID){
            case "seat10ClickHandler":
                if(isSeat10Selected){
                    this.seat10ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat10Selected = false;
                }
                else if(!isSeat10Selected){
                    this.seat10ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat10Selected = true;
                }break;

            case "seat11ClickHandler":
                if(isSeat11Selected){
                    this.seat11ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat11Selected = false;
                }
                else if(!isSeat11Selected){
                    this.seat11ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat11Selected = true;
                }break;

            case "seat12ClickHandler":
                if(isSeat12Selected){
                    this.seat12ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat12Selected = false;
                }
                else if(!isSeat12Selected){
                    this.seat12ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat12Selected = true;
                }break;

            case "seat13ClickHandler":
                if(isSeat13Selected){
                    this.seat13ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat13Selected = false;
                }
                else if(!isSeat13Selected){
                    this.seat13ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat13Selected = true;
                }break;

            case "seat14ClickHandler":
                if(isSeat14Selected){
                    this.seat14ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat14Selected = false;
                }
                else if(!isSeat14Selected){
                    this.seat14ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat14Selected = true;
                }break;

            default: System.err.println("Switch statement is faulty");
        }



//        if(seatID.equalsIgnoreCase(seat10ColorRect.getId())){
//           if(isSeat10Selected){
//               this.seat10ColorRect.setStyle("-fx-fill: #ffffff");
//               this.isSeat10Selected = false;
//           }
//           else if(!isSeat10Selected){
//               this.seat10ColorRect.setStyle("-fx-fill: #4bd841");
//               this.isSeat10Selected = true;
//           }
//        }
//
//        else if(seatID.equalsIgnoreCase(seat11ColorRect.getId())){
//            if(isSeat11Selected){
//                this.seat11ColorRect.setStyle("-fx-fill: #ffffff");
//                isSeat11Selected = false;
////                this.isSeat11Selected = false;
//            }
//            else if(!isSeat11Selected){
//                this.seat11ColorRect.setStyle("-fx-fill: #4bd841");
//                isSeat11Selected = true;
////                this.isSeat11Selected = true;
//            }
//        }


    }
}
