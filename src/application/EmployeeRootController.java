package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import javax.imageio.ImageIO;
import javax.xml.soap.Node;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Purpose: This class controls all the employee views and allows user to switch between
 * the respective scenes.
 *
 */
public class EmployeeRootController implements Initializable {

    @FXML
    private BorderPane employeePane;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView background;

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    /**
     *Purpose:
     *
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BufferedImage bufferedLogo = null;
        BufferedImage bufferedBackground = null;
        try {
            bufferedLogo = ImageIO.read(new File("src/resources/Cinestar.png"));
            bufferedBackground = ImageIO.read(new File("src/resources/cinemaWallpaper.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image logo = SwingFXUtils.toFXImage(bufferedLogo, null);
        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
        this.logo.setImage(logo);
        this.background.setImage(background);
    }

    /**
     *Purpose: method to be called by different employee controllers to set their respective views to the
     *center of the parent borderPane: 'empPane' . This Pane is a child of the stage
     *
     * @param node
     *
     */
    public void setCenter(javafx.scene.Node node){
        employeePane.setCenter(node);
    }

    @FXML
    public void openMovieFormView(ActionEvent event) {
        try {
            Navigation.loadEmplFxml(Navigation.EMPL_MOVIE_FORM);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "EmployeeRootController", "openMovieFormView", "Failed to load Employee MovieForm View. See: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Purpose: opens the employee's Home View (scene located within employeeRoot) where they can see the movies
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
}