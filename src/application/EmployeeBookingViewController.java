package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.Seat;
import models.SeatDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeBookingViewController implements Initializable{

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //TODO: @Luke display selected seats in this view
        //initialize data
        try {
            Seat seat = SeatDAO.getSeatsByScreening(EmployeeHomeController.screenID);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void openHomeView(ActionEvent event) throws IOException {
        EmployeeRootController controller = new EmployeeRootController();
        controller.openHomeView(event);
    }

}
