package application;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import javax.imageio.ImageIO;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.soap.Node;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerRootController implements Initializable {

    private static final Logger logger = Logger.getLogger(CustomerRootController.class.getName());

    @FXML
    private BorderPane custPane;

    @FXML
    private Label userLbl;

    @FXML
    private ImageView logo;

    @FXML
    private ImageView background;


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
     *Purpose: method to be called by different customer controllers to set their respective views to the
     *center of the parent borderPane: 'empPane' . This Pane is a child of the stage
     *
     * @param node
     *
     */
    public void setCenter(javafx.scene.Node node){
        custPane.setCenter(node);
    }

    @FXML
    public void openProgramView(ActionEvent event) {
        try {
            Navigation.loadCustFxml(Navigation.CUST_PROG_VIEW);
        } catch (IOException e) {
            logger.logp(Level.WARNING, "CustomerRootController", "openProgramView", "Failed to load CustomerProgram View. See: " + e);
            e.printStackTrace();
        }
    }

    @FXML
    public void openProfileView(ActionEvent event) {
        try {
            Navigation.loadCustFxml(Navigation.CUST_ACC_VIEW);
        } catch (IOException e) {
            logger.logp(Level.WARNING, "CustomerRootController", "openProfileView", "Failed to load CustomerProfile View. See: " + e);
            e.printStackTrace();
        }
    }



//
//    public void getMovies(String movies){
//        userLbl.setText(movies);
//    }
//



}

