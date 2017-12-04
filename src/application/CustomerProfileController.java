package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerProfileController implements Initializable{

    @FXML
    private Button editProfile;

    @FXML
    private ImageView backgroundImg;


    @FXML
    private void editCustomerProfile() {
        // TODO: Add ability to edit customer data. First click enable editing and change button lable to say "update".
        // TODO: Next click: push customer data into data base via "update" sql command
    }


    @FXML
    private void deleteMovieBooking(){
        //TODO General: Delete button located in each movie booking that is still in the future
        //TODO 1: Add delete button only for movies that are in the future
        //TODO 2: Add error popup for movies that are in the past (can't delete them!)
        //TODO 3: delete the actual booking with a JDialogBOx pop-up asking if you're sure to delete (https://www.youtube.com/watch?v=oZUGMpGQxgQ)

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BufferedImage bufferedBackground = null;
        try {
            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
            //background alternative
//         bufferedBackground = ImageIO.read(new File("src/resources/cinBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
        this.backgroundImg.setImage(background);
    }
}
