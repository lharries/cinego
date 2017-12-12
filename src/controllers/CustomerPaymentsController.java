package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.PaymentInfo;
import models.Seat;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerPaymentsController implements Initializable {
    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField expiryDateMonthField;

    @FXML
    private TextField cvcField;

    @FXML
    private ChoiceBox cardTypeChoiceBox;

    @FXML
    private TextField expiryDateYearField;

    @FXML
    private TableView selectedSeatsTable;

    @FXML
    private Label totalCostText;

    @FXML
    private Text errorMessageText;

    private PaymentInfo paymentInfo;

    public ArrayList<Seat> seats;

    public Double price = 100.0; // TODO Change this, just for testing


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(errorMessageText);

    }

    public void payBtnHandler(ActionEvent actionEvent) {
        errorMessageText.setVisible(false);
//        // charge the card
//        try {
//            PaymentsUtil.chargeCreditCard(100, 4242424242424242L, 12, 17, 131);
//            System.out.println("success");
//        } catch (CardException e) {
//            e.printStackTrace();
//        } catch (APIException e) {
//            e.printStackTrace();
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//        } catch (InvalidRequestException e) {
//            e.printStackTrace();
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        }

        getCardInfo();
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


        System.out.println(paymentInfo.isValid());

    }

}
