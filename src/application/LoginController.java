package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class LoginController implements Initializable {

    //public LoginModel loginModel = new LoginModel();
    public static Stage primaryStage = new Stage();


    @FXML
    private Label isConnected;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        if (loginModel.isDbConnected()) {
//            isConnected.setText("Connected");
//        } else {
//            isConnected.setText("Not Connected");
//        }
    }

    /**
     * Purpose: opens a new stage with the customer views loaded into the center of the overarching
     * customerRoot view when a system user logs in as a customer. Additionally this method hides the
     * login screen.
     *
     * @param event
     */

    @FXML
    public void loginCust(javafx.event.ActionEvent event) {

//        try {
//            if(loginModel.isLoggedIn(txtUsername.getText(),txtPassword.getText())){
//                isConnected.setText("Username & password is correct");

        ((Node) event.getSource()).getScene().getWindow().hide();
        try {
            primaryStage.setScene(createScene(loadCustBorderPane()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setResizable(false);
        primaryStage.show();

//            //launches the loginCust view upon running program
//            Pane root = loader.load(getClass().getResource("/views/CustomerRoot.fxml").openStream());
//            CustomerRootController customerController = (CustomerRootController) loader.getController();

//            }else{
//                isConnected.setText("Username & password is not correct");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            isConnected.setText("Username & password is not correct");

//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException ev) {
//            ev.printStackTrace();
//        }
    }

    /**
     * Purpose: opens a new stage with the customer views loaded into the center of the overarching
     * customerRoot view when a system user logs in as a customer. Additionally this method hides the
     * login screen.
     *
     * @param event
     */
    @FXML
    public void loginEmpl(javafx.event.ActionEvent event) {

//        try {
//            if(loginModel.isLoggedIn(txtUsername.getText(),txtPassword.getText())){
//                isConnected.setText("Username & password is correct");

        ((Node) event.getSource()).getScene().getWindow().hide();
        try {
            primaryStage.setScene(createScene(loadEmpBorderPane()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setResizable(false);
        primaryStage.show();

//            //launches the loginCust view upon running program
//            Pane root = loader.load(getClass().getResource("/views/CustomerRoot.fxml").openStream());
//            CustomerRootController customerController = (CustomerRootController) loader.getController();

//            }else{
//                isConnected.setText("Username & password is not correct");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            isConnected.setText("Username & password is not correct");

//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException ev) {
//            ev.printStackTrace();
//        }
    }

    /**
     * Purpose: Static method that sets the scene and adds styling via the CSS stylesheet with a
     * BorderPane as a parameter.
     *
     * @param root
     * @return
     */
    protected static Scene createScene(BorderPane root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().setAll(LoginController.class.getResource("/application/application.css").toExternalForm());
        return scene;
    }

    /**
     * Purpose: loads the customer view's layout: BorderPane. Fetches BorderPane's controller and stores it in
     * the Navigation class to be used later on.
     *
     * @return BorderPane 'customerRoot'
     * @throws IOException
     */
    private static BorderPane loadCustBorderPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        // loading the intial root with BoarderPane layout. CustRoot becomes root for other screens to be added into
        BorderPane customerRoot = (BorderPane) loader.load(LoginController.class.getResourceAsStream(Navigation.CUST_ROOT));
        CustomerRootController custRootContr = loader.getController();

        // sets controller for the customer root layout
        Navigation.setCustomerController(custRootContr);
        // loads first fxml file with a navigator method
        Navigation.loadCustFxml(Navigation.CUST_PROG_VIEW);

        return customerRoot;
    }

    /**
     * Purpose: loads the employee view's layout: BorderPane. Fetches BorderPane's controller and stores it in
     * the Navigation class to be used later on.
     *
     * @return BorderPane 'employeeRoot'
     * @throws IOException
     */
    private static BorderPane loadEmpBorderPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        // loading the intial root with BoarderPane layout. CustRoot becomes root for other screens to be added into
        BorderPane employeeRoot = (BorderPane) loader.load(LoginController.class.getResourceAsStream(Navigation.EMPL_ROOT));
        EmployeeRootController emplRootContr = loader.getController();

        // sets controller for the customer root layout
        Navigation.setEmployeeController(emplRootContr);
        // loads first fxml file with a navigator method
        Navigation.loadEmplFxml(Navigation.EMPL_HOME_VIEW);

        return employeeRoot;
    }
}