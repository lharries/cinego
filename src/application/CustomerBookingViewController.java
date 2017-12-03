package application;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CustomerBookingViewController {


    //TODO: make cinema seats selectable (change colour, store seat identifier, disable booked seats to be chosen
    //TODO: add booking summary at the side: display (push) movie title, date + time, populate seatListView with chosen seats (Row + Column)
    //TODO: fxids= movieTitle, Date, Time, seatListView, bookingConfirmationClickHandler




    @FXML
    private void confirmMovieBooking(ActionEvent event){
    //TODO: trigger a booking summary to be displayed (should we do an additional summary or is the one above the button enough?)
    //TODO: add order to customer profile's history view!


        //jumps back to customer's profile view after having clicked the Confirm booking button (think about triggering the order with a JDialogPane)
        CustomerRootController controller = new CustomerRootController();
        controller.openProfileView(event);
    }
}
