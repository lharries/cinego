package application;

import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.CustomerDAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerProfileController implements Initializable{

    @FXML
    private Button updateProfileBttn;

    @FXML
    private Button editProfileBttn;

    @FXML
    private ImageView backgroundImg;

    @FXML
    private TextField custFirNameField;

    @FXML
    private TextField custLaNameField;

    @FXML
    private TextField custEmailField;

    @FXML
    private TextField custPhone;

    boolean textFieldEditable = false;





    @FXML
    private void editCustomerProfile() {

        // TODO: Add ability to edit customer data. First click enable editing and change button lable to say "update".



        //Updates the user's information in the database
        Main.user.setFirstName(custFirNameField.getText());
        Main.user.setLastName(custLaNameField.getText());
        Main.user.setEmail(custEmailField.getText());
        try {
            CustomerDAO.updateCustomerDetails(Main.user.getFirstName(),Main.user.getLastName(),Main.user.getEmail(), Main.user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //TODO: display the adjusted customer name in the root
//        CustomerRootController.

        //testing
//        CustomerRootController controller = new CustomerRootController();
//        controller.custFirstName.setText(custFirNameField.getText());
//        CustomerRootController.custLastNameLabel.setText(custLaNameField.getText());
//
//        CustomerRootController.




        //pop-up to inform user about successfully updating data - source: https://stackoverflow.com/questions/39281622/javafx-how-to-show-temporary-popup-osd-when-action-performed
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage popup = (Stage) alert.getDialogPane().getScene().getWindow();
        popup.getIcons().add(new Image(this.getClass().getResource("/resources/cinestar.png").toString()));
        alert.setTitle("Cinego");
        alert.setHeaderText("Profile Update");
        alert.setContentText("Your profile was successfully updated, "+ Main.user.getFirstName());
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> popup.hide());

        popup.show();
        delay.play();
    }

    @FXML
    private void setProfileEditable(){



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

        //initializes customer profile input fields
        editProfileBttn.isDisable();

        custFirNameField.setText(Main.user.getFirstName());
        custFirNameField.setEditable(textFieldEditable);

        custLaNameField.setText(Main.user.getLastName());
        custLaNameField.setEditable(textFieldEditable);

        custEmailField.setText(Main.user.getEmail());
        custEmailField.setEditable(textFieldEditable);
        //TODO: store customer's phone number in database
//        custPhone.setText(Main.user.getPhone());
        custPhone.setEditable(textFieldEditable);


        //render background image
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
