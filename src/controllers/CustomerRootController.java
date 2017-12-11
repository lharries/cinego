package controllers;

import application.Main;
import application.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerRootController implements Initializable {


    //TODO: make the logout button hoverable so the mouse changes / the colour changes so the user knows there is something clickable
    //TODO: add a 'Goodbye, xx' message to login screen that disappears after some seconds

    private static final Logger LOGGER = Logger.getLogger(CustomerRootController.class.getName());

    @FXML
    private BorderPane customerPane;

    @FXML
    protected Label custLastNameLabel;

    @FXML
    protected Label custFirstNameLabel;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView logoutImg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        custFirstNameLabel.setText(Main.user.getFirstName());
        custLastNameLabel.setText(Main.user.getLastName());

        //set user's first + lastname onto root
        Main.user.firstNameProperty().addListener((observable, oldValue, newValue) -> custFirstNameLabel.setText(newValue));

        Main.user.lastNameProperty().addListener((observable, oldValue, newValue) -> custFirstNameLabel.setText(newValue));

        //sets the logos for the roots
//        BufferedImage bufferedLogo = null;
//        BufferedImage bufferedLogout = null;
//        BufferedImage bufferedBackground = null;
//        try {
//            bufferedLogo = ImageIO.read(new File("src/resources/cinestar.png"));
//            bufferedLogout = ImageIO.read(new File("src/resources/logout.png"));
////            bufferedBackground = ImageIO.read(new File("src/resources/cinemaWallpaper.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image logo = SwingFXUtils.toFXImage(bufferedLogo, null);
//        Image logout = SwingFXUtils.toFXImage(bufferedLogout, null);
////        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
//        this.logo.setImage(logo);
//        this.logoutImg.setImage(logout);
//        this.backgroundImg.setImage(background);
    }

    /**
     * Purpose: method to be called by different user controllers to set their respective views to the
     * center of the parent borderPane: 'empPane' . This Pane is a child of the stage
     *
     * @param node
     */
    public void setCenter(javafx.scene.Node node) {
        customerPane.setCenter(node);
    }


    /**
     * Purpose: opens the user's ProgramView (scene located within customerRoot) where they can see the list of
     * available movies
     *
     * @param event
     */

    @FXML
    public void openProgramView(ActionEvent event) {
        try {
            Navigation.loadCustFxml(Navigation.CUST_PROGRAM_VIEW);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "CustomerRootController", "openProgramView", "Failed to load CustomerProgram View. See: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Purpose: opens the user's Profile (scene located within customerRoot) where they can see their movie history,
     * booked movies and edit their own profile details
     * available movies
     *
     * @param event
     */

    @FXML
    public void openProfileView(ActionEvent event) {
        try {
            Navigation.loadCustFxml(Navigation.CUST_PROFILE_VIEW);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "CustomerRootController", "openProfileView", "Failed to load CustomerProfile View. See: " + e);
            e.printStackTrace();
        }
    }


    /**
     * Purpose: opens the cinema room's seat selection view based on the movie selected in the customerProgramView.
     * Customers can book a movie in this view
     * <p>
     * available movies
     */
    @FXML
    public void openBookingView() {
        try {
            Navigation.loadCustFxml(Navigation.CUST_BOOKING_VIEW);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "CustomerRootController", "openBookingView", "Failed to load CustomerBooking View. See: " + e);
            e.printStackTrace();
        }
    }


    /**
     * Purpose: logs user out of cinema system
     *
     * @param event
     * @version: logout 1.0
     */
    @FXML
    public void logout(ActionEvent event) {

        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main = new Main();
        Stage primaryStage = new Stage();
        main.start(primaryStage);
    }
}
