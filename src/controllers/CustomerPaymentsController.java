package controllers;

import com.stripe.exception.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.*;
import utils.PaymentsUtil;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerPaymentsController implements Initializable {
    public Label filmText;
    public Label screeningText;
    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateMonthField;

    @FXML
    private TextField cvcField;

    @FXML
    private TextField expiryDateYearField;

    @FXML
    private TableView selectedSeatsTable;

    @FXML
    private Label totalCostText;

    @FXML
    private Text errorMessageText;

    private PaymentInfo paymentInfo;

    public Screening selectedScreening;

    public ArrayList<Seat> seats;

    public Integer price = 100; // TODO Change this, just for testing

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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


    }

    public void payBtnHandler(ActionEvent actionEvent) {
        errorMessageText.setVisible(false);

        getCardInfo();

        if (paymentInfo.isValid()) {
            try {
                PaymentsUtil.chargeCreditCard(paymentInfo);
                // TODO: Display success message
                // Your payment has been successfully handled by Stripe
                // we look forward to seeing you on ...
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

}
