package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Customer;
import models.CustomerDAO;
import models.Employee;
import models.EmployeeDAO;


public class LoginController implements Initializable {

    //TODO: FEATURE add password reset functionality via e-Mail sent to user e-Mail client source: https://codereview.stackexchange.com/questions/114005/javafx-email-client

    //public LoginModel loginModel = new LoginModel();
    public Stage primaryStage = new Stage();


    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label loginUnsuccessful;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Purpose: opens a new stage with the user views loaded into the center of the overarching
     * customerRoot view when a system user logs in as a user. Additionally this method hides the
     * login screen.
     */
    @FXML
    public void loginCust(Event event) {

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        try {
            Customer cust = CustomerDAO.login(username, password);

            if (cust != null) {
                Main.user = cust;
                // TODO : Switch to the correct view
                ((Node) event.getSource()).getScene().getWindow().hide();
                try {
                    primaryStage.setScene(createScene(loadCustBorderPane()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setResizable(false);
                primaryStage.show();
            } else{

                loginUnsuccessful.setText("Invalid login - please try again");
                txtUsername.requestFocus();
                //TODO - add timer to set Label back to blank after 3 seconds
//                        Timer t = new Timer();
//                        t.schedule(new TimerTask() {
//                            @Override public void run() {
//                                loginUnsuccessful.setText(" ");
//                            }
//                        }, 0L, 5000L);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//            };
//        });
    }

    /**
     * Purpose: opens a new stage with the user views loaded into the center of the overarching
     * customerRoot view when a system user logs in as a user. Additionally this method hides the
     * login screen.
     *
     * @param event
     */
    @FXML
    public void loginEmpl(Event event) {

        //get user login input
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        //compare against database username-password pair
        try {
            Employee empl = EmployeeDAO.login(username, password);

            if (empl != null) {
                Main.user = empl;
                // TODO : Switch to the correct view
                ((Node) event.getSource()).getScene().getWindow().hide();
                try {
                    primaryStage.setScene(createScene(loadEmpBorderPane()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setResizable(false);
                primaryStage.show();
            } else{
                loginUnsuccessful.setText("Invalid login - please try again");
                txtUsername.requestFocus();
                //TODO - add timer to set Label back to blank after 3 seconds
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Purpose: Static method that sets the scene and adds styling via the CSS stylesheet with a
     * BorderPane as a parameter.
     *
     * @param root
     * @return
     */
    protected Scene createScene(BorderPane root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().setAll(getClass().getResource("/application/application.css").toExternalForm());
        return scene;
    }

    /**
     * Purpose: loads the user view's layout: BorderPane. Fetches BorderPane's controller and stores it in
     * the Navigation class to be used later on.
     *
     * @return BorderPane 'customerRoot'
     * @throws IOException
     */
    private BorderPane loadCustBorderPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();

        // loading the intial root with BoarderPane layout. CustRoot becomes root for other screens to be added into
        System.out.println(getClass().getResourceAsStream(Navigation.CUST_ROOT));
        BorderPane customerRoot = (BorderPane) loader.load(getClass().getResourceAsStream(Navigation.CUST_ROOT));


        //testing rendering speed
//        customerRoot.setCache(true);
//        customerRoot.setCacheHint(CacheHint.SPEED);

        CustomerRootController custRootContr = loader.getController();

        // sets controller for the user root layout
        Navigation.setCustomerController(custRootContr);
        // loads first fxml file with a navigator method
        Navigation.loadCustFxml(Navigation.CUST_PROFILE_VIEW);

        return customerRoot;
    }

    /**
     * Purpose: loads the employee view's layout: BorderPane. Fetches BorderPane's controller and stores it in
     * the Navigation class to be used later on.
     *
     * @return BorderPane 'employeeRoot'
     * @throws IOException
     */
    private BorderPane loadEmpBorderPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        // loading the intial root with BoarderPane layout. CustRoot becomes root for other screens to be added into
        BorderPane employeeRoot = (BorderPane) loader.load(getClass().getResourceAsStream(Navigation.EMPL_ROOT));
        EmployeeRootController emplRootContr = loader.getController();

        // sets controller for the user root layout
        Navigation.setEmployeeController(emplRootContr);
        // loads first fxml file with a navigator method
        Navigation.loadEmplFxml(Navigation.EMPL_HOME_VIEW);

        return employeeRoot;
    }
}