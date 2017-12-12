package controllers;

import application.Main;
import application.Navigation;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Customer;
import models.CustomerDAO;
import models.Employee;
import models.EmployeeDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    /**
     * The login stage
     */
    private Stage primaryStage = new Stage();


    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label loginUnsuccessfulMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Checks if the login details are corrected and if so opens the relevant new stage
     *
     * @param event The login button being clicked event - not used
     */
    @FXML
    public void customerLoginBtnHandler(Event event) {

        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        try {
            Customer customer = CustomerDAO.login(username, password);

            // check that the login details are correct
            if (customer != null) {
                // sets the logged in user as
                Main.user = customer;

                ((Node) event.getSource()).getScene().getWindow().hide();
                try {
                    primaryStage.setScene(createScene(loadCustBorderPane()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setResizable(false);
                primaryStage.show();
            } else {
                loginUnsuccessfulMessage.setText("Invalid login - please try again");
                usernameTextField.requestFocus();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

//            };
//        });
    }

    /**
     * opens a new stage with the user views loaded into the center of the overarching
     * customerRoot view when a system user logs in as a user. Additionally this method hides the
     * login screen.
     *
     * @param event
     */
    @FXML
    public void employeeLoginBtnHandler(Event event) {

        //get user login input
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        //compare against database username-password pair
        try {
            Employee empl = EmployeeDAO.login(username, password);

            if (empl != null) {
                Main.user = empl;

                ((Node) event.getSource()).getScene().getWindow().hide();
                try {
                    primaryStage.setScene(createScene(loadEmpBorderPane()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setResizable(false);
                primaryStage.show();
            } else {
                loginUnsuccessfulMessage.setText("Invalid login - please try again");
                usernameTextField.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method that sets the scene and adds styling via the CSS stylesheet with a
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
     * loads the user view's layout: BorderPane. Fetches BorderPane's controller and stores it in
     * the Navigation class to be used later on.
     *
     * @return BorderPane 'customerRoot'
     * @throws IOException
     */
    private BorderPane loadCustBorderPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();

        // loading the intial root with BoarderPane layout. CustRoot becomes root for other screens to be added into
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
     * loads the employee view's layout: BorderPane. Fetches BorderPane's controller and stores it in
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