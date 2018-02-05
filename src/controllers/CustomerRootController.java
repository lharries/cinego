package controllers;

import application.Main;
import application.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
 * This class controls all the customer views and allows user to switch between
 * the respective scenes.
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class CustomerRootController implements Initializable {

    @FXML
    private BorderPane customerPane;

    @FXML
    protected Label custLastNameLabel;

    @FXML
    protected Label custFirstNameLabel;

    @FXML
    private ImageView logoutImg, logo;

    private static final Logger LOGGER = Logger.getLogger(CustomerRootController.class.getName());

    /**
     * Initializes the customer root layer with logo, logout image and user credentials
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set user first + last name
        custFirstNameLabel.setText(Main.user.getFirstName());
        custLastNameLabel.setText(Main.user.getLastName());

        //set user's first + lastname onto root
        Main.user.firstNameProperty().addListener((observable, oldValue, newValue) -> custFirstNameLabel.setText(newValue));
        Main.user.lastNameProperty().addListener((observable, oldValue, newValue) -> custLastNameLabel.setText(newValue));
    }

    /**
     * Called by different user controllers to set their respective views to the
     * center of the parent borderPane: 'empPane' . This Pane is a child of the stage
     *
     * @param node node
     */
    public void setCenter(javafx.scene.Node node) {
        customerPane.setCenter(node);
    }


    /**
     * Opens the user's ProgramView (scene located within customerRoot) where they can see the list of
     * available movies
     *
     * @param event ActionEvent
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
     * Opens the user's Profile (scene located within customerRoot) where they can see their movie history,
     * booked movies and edit their own profile details
     * available movies
     *
     * @param event ActionEvent
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
     * logs user out of cinema system
     *
     * @param event ActionEvent
     */
    @FXML
    public void logout(ActionEvent event) {

        ((Node) event.getSource()).getScene().getWindow().hide();
        Main main = new Main();
        Stage primaryStage = new Stage();
        main.start(primaryStage);
    }
}

