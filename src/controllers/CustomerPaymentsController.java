package controllers;

import application.Main;
import application.Navigation;
import com.stripe.exception.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.*;
import utils.PaymentsUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerPaymentsController implements Initializable {
    @FXML
    private Label filmText;

    @FXML
    private Label screeningText;

    @FXML
    private ListView selectedSeatsList;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateMonthField;

    @FXML
    private TextField cvcField;

    @FXML
    private TextField expiryDateYearField;

    @FXML
    private Label totalCostText;

    @FXML
    private Text errorMessageText;

    private PaymentInfo paymentInfo;

    public static Screening selectedScreening;

    public static ArrayList<Seat> seats;

    public Integer price = 100; // TODO Change this, just for testing

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        price = 5 * seats.size();

        totalCostText.setText("£" + String.valueOf(price));
        try {
            selectedScreening = ScreeningDAO.getScreeningObservableList().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } // TODO: Remove just for testing

        try {
            screeningText.setText(selectedScreening.getMediumDate());
        } catch (ParseException e) {
            LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "initialize", "unable to get screening medium date" + e);
            e.printStackTrace();
        }

        try {
            Film film = selectedScreening.getFilm();
            if (film != null) {
                filmText.setText(film.getTitle());
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "initialize", "unable to get film" + e);
            e.printStackTrace();
        }

        if (seats != null && seats.size() > 0) {
            ObservableList<Seat> seatObservableList = FXCollections.observableList(seats);
            selectedSeatsList.setItems(seatObservableList);
        }


    }

    public void payBtnHandler(ActionEvent actionEvent) {
        errorMessageText.setVisible(false);

        getCardInfo();

        if (paymentInfo.isValid()) {
            try {
                PaymentsUtil.chargeCreditCard(paymentInfo);
                createBooking();
                try {
                    Navigation.loadCustFxml(Navigation.CUST_PROFILE_VIEW);
                } catch (IOException e) {
                    LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "createBooking", "Unable to switch to the profile" + e);
                    e.printStackTrace();
                }
                // TODO: Display success message
            } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e) {
                errorMessageText.setText("Message from stripe API: " + e.getMessage());
                errorMessageText.setVisible(true);
                e.printStackTrace();
            }

        } else {
            errorMessageText.setText("Invalid payment info");
            errorMessageText.setVisible(true);
        }

    }

    public void cancelBtnHandler(ActionEvent actionEvent) {
        try {
            Navigation.loadCustFxml(Navigation.CUST_BOOKING_VIEW);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "createBooking", "Unable to switch to the profile" + e);
            e.printStackTrace();
        }
    }

    private void getCardInfo() {
        paymentInfo = new PaymentInfo();
        paymentInfo.setPrice(price);

        try {
            paymentInfo.setCardNumber(cardNumberField.getText());
        } catch (NumberFormatException e) {
            errorMessageText.setText("Invalid credit card number");
            errorMessageText.setVisible(true);
            return;
        }

        try {
            paymentInfo.setExpiryMonth(Integer.parseInt(expiryDateMonthField.getText()));
        } catch (NumberFormatException e) {
            errorMessageText.setText("Invalid expiry month");
            errorMessageText.setVisible(true);
            return;
        }
        try {
            paymentInfo.setExpiryYear(Integer.parseInt(expiryDateYearField.getText()));
        } catch (NumberFormatException e) {
            errorMessageText.setText("Invalid expiry year");
            errorMessageText.setVisible(true);
            return;
        }

        try {
            paymentInfo.setCvc(Integer.parseInt(cvcField.getText()));
        } catch (NumberFormatException e) {
            errorMessageText.setText("Invalid cvcField");
            errorMessageText.setVisible(true);
            return;
        }

    }

    /**
     * Create the booking object in the database and then refresh the GUI
     */
    private void createBooking() {
        for (Seat seat :
                seats) {

            try {
                BookingDAO.insertBooking(Main.user.getId(), true, seat.getId(), selectedScreening.getId());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "createBooking", "Unable to create the booking" + e);
            }
        }

    }

}
