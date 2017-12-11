package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeMovieFormController implements Initializable {


    @FXML
    private ImageView backgroundImg;

    @FXML
    private void returnToMoviesView(){

        //ToDo: add ability to change back to movies view
    }

    @FXML
    private void addMovie(){
        //TODO: add ability to add a new movie to database based on the input data from each field
        //TODO: question: how do we add an image? Via internet URL or do we actually have to create an upload so the image is stored in the project's directory / the database?

        //TODO: FEATURE add ability to select multiple dates and screening times to create multiple movies at once!
        //TODO: FEATURE add ability to delete movies accidentally created / created with errors (would have to ensure that no seats booked yet if allow employee to delete the movies)
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        BufferedImage bufferedBackground = null;
//        try {
//            bufferedBackground = ImageIO.read(new File("src/resources/cinWallpaper.png"));
//            //background alternative
////         bufferedBackground = ImageIO.read(new File("src/resources/cinBackground.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Image background = SwingFXUtils.toFXImage(bufferedBackground, null);
//        this.backgroundImg.setImage(background);
    }
}
