package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javax.xml.soap.Node;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 *
 */
public class EmployeeRootController implements Initializable {

    @FXML
    private BorderPane employeePane;

    /**
     *Purpose:
     *
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     *Purpose: method to be called by different employee controllers to set their respective views to the
     *center of the parent borderPane: 'empPane' . This Pane is a child of the stage
     *
     * @param node
     *
     */
    public void setCenter(javafx.scene.Node node){
        employeePane.setCenter(node);
    }



}
