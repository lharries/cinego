package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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




public class CustomerRootController implements Initializable {

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
        System.out.println("Test - CustomerRootController");

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
    public void setCenter(Node node){
        custPane.setCenter((javafx.scene.Node) node);
    }






//
//    public void getMovies(String movies){
//        userLbl.setText(movies);
//    }
//





}

