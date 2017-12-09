package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeBookingController implements Initializable{

    public static Screening selectedScreening;

    public GridPane gridPaneSeats;
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

    ArrayList<Seat> selectedSeats;



    //TODO: populate the above fxids= 'Time' + 'Date' + 'Title' + 'seatsBookedPieChart' with their respective data based on the route the employee came from (which movie the employee entered the view from)


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createSeatingPlan();

    }

    @FXML
    private void openHomeView(ActionEvent event) throws IOException {
        EmployeeRootController controller = new EmployeeRootController();
        controller.openHomeView(event);
    }

    private void createSeatingPlan() {

        gridPaneSeats.getChildren().clear();

        initGridLines();
        String[] rows = new String[]{"A", "B", "C", "D", "E"};
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 9; j++) {
                try {
                    Seat seat = SeatDAO.getSeatByLocation(j, rows[i]);

                    // normal seat
                    ImageView seatViewImage = new ImageView("/resources/seat.png");
                    seatViewImage.setFitWidth(60.0);
                    seatViewImage.setFitHeight(60.0);

                    // taken seat
                    ImageView takenSeatImage = new ImageView("/resources/taken-seat.png");
                    takenSeatImage.setFitWidth(60.0);
                    takenSeatImage.setFitHeight(60.0);

                    // taken seat
                    ImageView selectedSeatImage = new ImageView("/resources/selected-seat.png");
                    selectedSeatImage.setFitWidth(60.0);
                    selectedSeatImage.setFitHeight(60.0);

                    Button btn = new Button();
                    btn.setGraphic(seatViewImage);

                    Booking bookingInSeat = BookingDAO.getBooking(seat.getId(), selectedScreening.getId());

                    if (bookingInSeat != null) {
                        if (bookingInSeat.getCustomerId() == Main.user.getId()) {
                            btn.setGraphic(selectedSeatImage);
                        } else {
                            btn.setGraphic(takenSeatImage);
                        }
                        btn.setDisable(true);
                    }

                    System.out.println(BookingDAO.getBooking(seat.getId(), selectedScreening.getId()));

                    //TODO: Set the button color to white
                    gridPaneSeats.add(btn, j, i);
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initGridLines() {

        // Rows
        for (int row = 0; row < 5; row++) {
            Label text = new Label(Character.toString((char) (65 + row)));
            System.out.println(text);
            text.setFont(new Font(30.0));
            text.setLayoutX(-10.0);
            text.setLayoutY(0);
            text.toFront();
            gridPaneSeats.add(text, 0, row);
        }

        // columns
        for (int column = 1; column < 9; column++) {
            Label text = new Label(String.valueOf(column));
            System.out.println(text);
            text.setFont(new Font(30.0));
            text.setLayoutX(-10.0);
            text.setLayoutY(0);
            text.toFront();
            gridPaneSeats.add(text, column, 5);
        }
    }

}
