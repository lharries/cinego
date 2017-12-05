package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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


    //testing colour changing rectangles for selectable cinema chairs;
    @FXML
    private Rectangle seat10ColorRect, seat11ColorRect, seat12ColorRect, seat13ColorRect, seat14ColorRect;
    @FXML
    private Rectangle seat20ColorRect, seat21ColorRect, seat22ColorRect, seat23ColorRect, seat24ColorRect;
    @FXML
    private Rectangle seat30ColorRect, seat31ColorRect, seat32ColorRect, seat33ColorRect, seat34ColorRect;
    @FXML
    private Rectangle seat40ColorRect, seat41ColorRect, seat42ColorRect, seat43ColorRect, seat44ColorRect;

    private boolean isSeat10Selected, isSeat11Selected, isSeat12Selected, isSeat13Selected, isSeat14Selected;
    private boolean isSeat20Selected, isSeat21Selected, isSeat22Selected, isSeat23Selected, isSeat24Selected;
    private boolean isSeat30Selected, isSeat31Selected, isSeat32Selected, isSeat33Selected, isSeat34Selected;
    private boolean isSeat40Selected, isSeat41Selected, isSeat42Selected, isSeat43Selected, isSeat44Selected;


    //TODO: make cinema seats selectable (change colour, store seat identifier, disable booked seats to be chosen
    //TODO: add booking summary at the side: display (push) movie title, date + time, populate seatListView with chosen seats (Row + Column)
    //TODO: fxids= movieTitle, Date, Time, seatListView, bookingConfirmationClickHandler

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
        alert.setHeaderText("THIS IS WHERE YOUR BOOKING DETAILS WOULD GO VIA VARIABLES, Luke!");
        alert.setContentText("Luke, I'm awesome");

        ButtonType buttonTypeOne = new ButtonType("Pay now");
        ButtonType buttonTypeTwo = new ButtonType("Two");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            //jumps back to user's profile view after having clicked the Confirm booking button (think about triggering the order with a JDialogPane)
            CustomerRootController controller = new CustomerRootController();
            controller.openProfileView(event);
        } else if (result.get() == buttonTypeTwo) {
            //test
            System.err.println("haha it works");
        }

    }

    /**
     * Purpose: changes colour of selected seats and thus allows customers to select their preferred seats
     * for a respective movie screening.
     *
     * @param event
     */

    @FXML
    private void selectSeat(MouseEvent event) {

        //fetches triggering seat's fx:id which is used to check against the cases
        ImageView seat = (ImageView) event.getSource();
        String seatID = seat.getId();

        switch (seatID) {
            //column 1 (first column filled with chairs)
            case "seat10ClickHandler":
                if (isSeat10Selected) {
                    this.seat10ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat10Selected = false;
                } else if (!isSeat10Selected) {
                    this.seat10ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat10Selected = true;
                }
                break;

            case "seat11ClickHandler":
                if (isSeat11Selected) {
                    this.seat11ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat11Selected = false;
                } else if (!isSeat11Selected) {
                    this.seat11ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat11Selected = true;
                }
                break;

            case "seat12ClickHandler":
                if (isSeat12Selected) {
                    this.seat12ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat12Selected = false;
                } else if (!isSeat12Selected) {
                    this.seat12ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat12Selected = true;
                }
                break;

            case "seat13ClickHandler":
                if (isSeat13Selected) {
                    this.seat13ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat13Selected = false;
                } else if (!isSeat13Selected) {
                    this.seat13ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat13Selected = true;
                }
                break;

            case "seat14ClickHandler":
                if (isSeat14Selected) {
                    this.seat14ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat14Selected = false;
                } else if (!isSeat14Selected) {
                    this.seat14ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat14Selected = true;
                }
                break;

            //column 2
            case "seat20ClickHandler":
                if (isSeat20Selected) {
                    this.seat20ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat20Selected = false;
                } else if (!isSeat20Selected) {
                    this.seat20ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat20Selected = true;
                }
                break;

            case "seat21ClickHandler":
                if (isSeat21Selected) {
                    this.seat21ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat21Selected = false;
                } else if (!isSeat21Selected) {
                    this.seat21ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat21Selected = true;
                }
                break;

            case "seat22ClickHandler":
                if (isSeat22Selected) {
                    this.seat22ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat22Selected = false;
                } else if (!isSeat22Selected) {
                    this.seat22ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat22Selected = true;
                }
                break;

            case "seat23ClickHandler":
                if (isSeat23Selected) {
                    this.seat23ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat23Selected = false;
                } else if (!isSeat23Selected) {
                    this.seat23ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat23Selected = true;
                }
                break;

            case "seat24ClickHandler":
                if (isSeat24Selected) {
                    this.seat24ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat24Selected = false;
                } else if (!isSeat24Selected) {
                    this.seat24ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat24Selected = true;
                }
                break;

            //column 3
            case "seat30ClickHandler":
                if (isSeat30Selected) {
                    this.seat30ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat30Selected = false;
                } else if (!isSeat30Selected) {
                    this.seat30ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat30Selected = true;
                }
                break;

            case "seat31ClickHandler":
                if (isSeat31Selected) {
                    this.seat31ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat31Selected = false;
                } else if (!isSeat31Selected) {
                    this.seat31ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat31Selected = true;
                }
                break;

            case "seat32ClickHandler":
                if (isSeat32Selected) {
                    this.seat32ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat32Selected = false;
                } else if (!isSeat32Selected) {
                    this.seat32ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat32Selected = true;
                }
                break;

            case "seat33ClickHandler":
                if (isSeat33Selected) {
                    this.seat33ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat33Selected = false;
                } else if (!isSeat33Selected) {
                    this.seat33ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat33Selected = true;
                }
                break;

            case "seat34ClickHandler":
                if (isSeat34Selected) {
                    this.seat34ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat34Selected = false;
                } else if (!isSeat34Selected) {
                    this.seat34ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat34Selected = true;
                }
                break;

            //column 4
            case "seat40ClickHandler":
                if (isSeat40Selected) {
                    this.seat40ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat40Selected = false;
                } else if (!isSeat40Selected) {
                    this.seat40ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat40Selected = true;
                }
                break;

            case "seat41ClickHandler":
                if (isSeat41Selected) {
                    this.seat41ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat41Selected = false;
                } else if (!isSeat41Selected) {
                    this.seat41ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat41Selected = true;
                }
                break;

            case "seat42ClickHandler":
                if (isSeat42Selected) {
                    this.seat42ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat42Selected = false;
                } else if (!isSeat42Selected) {
                    this.seat42ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat42Selected = true;
                }
                break;

            case "seat43ClickHandler":
                if (isSeat43Selected) {
                    this.seat43ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat43Selected = false;
                } else if (!isSeat43Selected) {
                    this.seat43ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat43Selected = true;
                }
                break;

            case "seat44ClickHandler":
                if (isSeat44Selected) {
                    this.seat44ColorRect.setStyle("-fx-fill: #ffffff");
                    this.isSeat44Selected = false;
                } else if (!isSeat44Selected) {
                    this.seat44ColorRect.setStyle("-fx-fill: #4bd841");
                    this.isSeat44Selected = true;
                }
                break;


            default:
                System.err.println("Switch statement is faulty");
        }
    }
}


