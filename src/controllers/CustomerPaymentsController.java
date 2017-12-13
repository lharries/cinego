package controllers;

import application.Main;
import application.Navigation;
import com.stripe.exception.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.*;
import utils.EmailsUtil;
import utils.PaymentsUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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

    public Integer price;

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        price = 5 * seats.size() * 100;

        totalCostText.setText("£" + String.valueOf(price / 100));

        initSummaryData();
    }

    private void initSummaryData() {
        screeningText.setText(selectedScreening.getMediumDate());

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

            chargeCreditCard();

        } else {
            errorMessageText.setText("Invalid payment info");
            errorMessageText.setVisible(true);
        }

    }

    private void chargeCreditCard() {

        try {
            PaymentsUtil.chargeCreditCard(paymentInfo);
            createBooking();

            showSuccessModal();

            //send ticket with QR code
            String email = confirmEmail();
            String emailContent = EmailsUtil.createBookingEmailContent(selectedScreening.getMediumDate(), selectedScreening.getFilmTitle(), seats);
            EmailsUtil.sendEmail(email, "Ticket for Cinego", emailContent);

            try {
                Navigation.loadCustFxml(Navigation.CUST_PROFILE_VIEW);
            } catch (IOException e) {
                LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "createBooking", "Unable to switch to the profile" + e);
                e.printStackTrace();
            }
        } catch (CardException | APIException | AuthenticationException | APIConnectionException | InvalidRequestException e) {
            errorMessageText.setText("Message from stripe API: " + e.getMessage());
            errorMessageText.setVisible(true);
            e.printStackTrace();
        }
    }

    private void showSuccessModal() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Payment successful");
        alert.setHeaderText("Payment successful");
        alert.setContentText("Thank you for booking!\n" +
                "We'll see you at " + selectedScreening.getMediumDate() + " to see " +
                selectedScreening.getFilmTitle());

        ButtonType buttonTypeDone = new ButtonType("Done", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeDone);

        alert.show();
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
            LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "getCardInfo", "invalid credit card number. See" + e);
            return;
        }

        try {
            paymentInfo.setExpiryMonth(Integer.parseInt(expiryDateMonthField.getText()));
        } catch (NumberFormatException e) {
            errorMessageText.setText("Invalid expiry month");
            errorMessageText.setVisible(true);
            LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "getCardInfo", "invalid expiry date. See" + e);
            return;
        }
        try {
            paymentInfo.setExpiryYear(Integer.parseInt(expiryDateYearField.getText()));
        } catch (NumberFormatException e) {
            errorMessageText.setText("Invalid expiry year");
            errorMessageText.setVisible(true);
            LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "getCardInfo", "invalid expiry year. See" + e);
            return;
        }

        try {
            paymentInfo.setCvc(Integer.parseInt(cvcField.getText()));
        } catch (NumberFormatException e) {
            errorMessageText.setText("Invalid cvcField");
            errorMessageText.setVisible(true);
            LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "getCardInfo", "invalid cvc number. See" + e);
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
                BookingDAO.insertBooking(Main.user.getId(), seat.getId(), selectedScreening.getId());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                LOGGER.logp(Level.WARNING, "CustomerPaymentsController", "createBooking", "Unable to create the booking" + e);
            }
        }

    }

    /**
     * Reference: http://code.makery.ch/blog/javafx-dialogs-official/
     *
     * @return the email
     */
    private String confirmEmail() {
        TextInputDialog dialog = new TextInputDialog(Main.user.getEmail());
        dialog.setTitle("Payment Success");
        dialog.setHeaderText("Payment Success!");
        dialog.setContentText("Payment success! Please confirm your email to which we will send you tickets:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result.get();
        } else {
            System.err.println("Unable to get email");
            return Main.user.getEmail();
        }
    }

}
