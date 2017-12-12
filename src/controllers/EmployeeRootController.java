package controllers;

import application.Main;
import application.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
 * @version 1.0
 */
public class EmployeeRootController implements Initializable {

    //TODO: set employeeID (Label on Root) to reflect employee's login username so that at all times we know who's logged in
    //TODO: add logout icon to root and make it 'clickable' (possibly by making button see through and laying it on top of image)


    @FXML
    private BorderPane employeePane;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView logoutImg;

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
