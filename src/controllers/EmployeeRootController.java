package controllers;

import application.Main;
import application.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class controls all the employee views and allows user to switch between
 * the respective scenes.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class EmployeeRootController implements Initializable {

    @FXML
    private BorderPane employeePane;

    @FXML
    private ImageView logoutImg, logo;

    @FXML
    private Label emplFirstName;

    @FXML
    private Label emplLastName;

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //sets user's name onto root
        emplFirstName.setText(Main.user.getFirstName());
        emplLastName.setText(Main.user.getLastName());
        File file = new File("src/resources/logout.png");
        Image image = new Image(file.toURI().toString());
        logoutImg.setImage(image);

        file = new File("src/resources/cinestar.png");
        image = new Image(file.toURI().toString());
        logo.setImage(image);


    }

    /**
     * method to be called by different employee controllers to set their respective views to the
     * center of the parent borderPane: 'empPane' . This Pane is a child of the stage
     *
     * @param node
     */
    public void setCenter(javafx.scene.Node node) {
        employeePane.setCenter(node);
    }

    /**
     * opens the employee's Home View (scene located within employeeRoot) where they can see the movies
     *
     * @param event
     */

    @FXML
    public void openHomeView(ActionEvent event) {
        try {
            Navigation.loadEmplFxml(Navigation.EMPL_HOME_VIEW);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "EmployeeRootController", "openHomeView", "Failed to load Employee HomeView View. See: " + e);
            e.printStackTrace();
        }
    }

    /**
     * @param event
     */
    @FXML
    public void openBookingView(ActionEvent event) {
        try {
            Navigation.loadEmplFxml(Navigation.EMPL_BOOKING_VIEW);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "EmployeeRootController", "openBookingView", "Failed to load Employee Booking View. See: " + e);
            e.printStackTrace();
        }
    }

    /**
     * logs user out of cinema system
     *
     * @param event
     */
    @FXML
    public void logout(ActionEvent event) {

        ((javafx.scene.Node) event.getSource()).getScene().getWindow().hide();
        Main main = new Main();
        Stage primaryStage = new Stage();
        main.start(primaryStage);
    }

}
