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
//            Pane root = loader.load(getClass().getResource("/views/customerRoot.fxml").openStream());
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
     * Purpose: Static method that sets the scene and the stylesheet with a
     * BorderPane as a paramter.
     *
     * @param root
     * @return
     */
    private static Scene createScene(BorderPane root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().setAll(LoginController.class.getResource("/application/application.css").toExternalForm());
        return scene;
    }

    /**
     *
     * @return BorderPane custRoot
     * @throws IOException
     */
    private static BorderPane loadCustBorderPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        // loading the intial root with BoarderPane layout. CustRoot becomes root for other screens to be added into
        BorderPane custRoot = (BorderPane) loader.load(LoginController.class.getResourceAsStream(Navigation.CUST_ROOT));
        CustomerRootController custRootContr = loader.getController();

        // sets controller for the customer root layout
        Navigation.setCustController(custRootContr);
        // loads first fxml file with a navigator method
        Navigation.loadCustFxml(Navigation.CUST_ACC_VIEW);

        return custRoot;
    }




//    private static BorderPane loadEmpBorderPane() {
//
//        return
//    }



}