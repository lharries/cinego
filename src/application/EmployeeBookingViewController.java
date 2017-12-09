package application;


import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Screening;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// TODO: Can we delete this controller?
public class EmployeeBookingViewController implements Initializable {

    @FXML
    private Label Time;

    @FXML
    private Label Date;

    @FXML
    private Label movieTitle;

    @FXML
    private PieChart seatsBookedPieChart;

    @FXML
    private ImageView backgroundImg;

//TODO: populate the above fxids= 'Time' + 'Date' + 'Title' + 'seatsBookedPieChart' with their respective data based on the route the employee came from (which movie the employee entered the view from)

    @FXML
    private void openHomeView(ActionEvent event) throws IOException {
        EmployeeRootController controller = new EmployeeRootController();
        controller.openHomeView(event);
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
