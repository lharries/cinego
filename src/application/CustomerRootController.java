package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerRootController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(CustomerRootController.class.getName());

    @FXML
    private BorderPane customerPane;

//    @FXML
//    private Label userLbl;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView background;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //System.err.println(System.getProperty("java.class.path"));


        //must set the background image within the individual scenes because right now the images will
        //likely be added to the view but are located behind the added scenes

        BufferedImage bufferedLogo = null;
//        BufferedImage bufferedBackground = null;
        try {
            bufferedLogo = ImageIO.read(new File("src/resources/cinestar.png"));
//            bufferedBackground = ImageIO.read(new File("src/resources/cinemaWallpaper.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image logo = SwingFXUtils.toFXImage(bufferedLogo, null);
//        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
        this.logo.setImage(logo);
//        this.background.setImage(background);
    }

    /**
     * Purpose: method to be called by different customer controllers to set their respective views to the
     * center of the parent borderPane: 'empPane' . This Pane is a child of the stage
     *
     * @param node
     */
    public void setCenter(javafx.scene.Node node) {
        customerPane.setCenter(node);
    }


    /**
     * Purpose: opens the customer's ProgramView (scene located within customerRoot) where they can see the list of
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
     * Purpose: opens the customer's Profile (scene located within customerRoot) where they can see their movie history,
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
     * Purpose: logs user out of cinema system
     * @version: logout 1.0
     * @param event
     */
    @FXML
    public void logout(ActionEvent event){

        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main = new Main();
        Stage primaryStage = new Stage();
        main.start(primaryStage);
    }
}

