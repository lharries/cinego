package models;

import controllers.EmployeeRootController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentInfo {
    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    private Double price;
    private Long cardNumber;
    private Integer expiryMonth;
    private Integer expiryYear;
    private Integer cvc;

    private Integer cardNumberLength = 0;


    public PaymentInfo() {
        this.price = price;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvc = cvc;
    }

    public PaymentInfo(Double price, Long cardNumber, Integer expiryMonth, Integer expiryYear, Integer cvc) {
        this.price = price;
        this.cardNumber = cardNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvc = cvc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) throws NumberFormatException {
        try {
            Long cardNumberLong = Long.parseLong(cardNumber.replaceAll("\\s", ""));

            cardNumberLength = (int) (Math.log10(cardNumberLong) + 1.0);
            if (cardNumberLength != 16.0) {
                throw new NumberFormatException("Invalid card length");
            }

            this.cardNumber = cardNumberLong;
        } catch (NumberFormatException e) {
            LOGGER.logp(Level.WARNING, "PaymentInfo", "setCardNumber", "Unable to parse card number" + e);
            throw e;
        }
    }

    public Integer getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(Integer expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public Integer getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(Integer expiryYear) {
        this.expiryYear = expiryYear;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    /**
     * does the card meet the basic criteria of being a credit card
     * i.e. Card number length is 16 and details are present
     *
     * @return if the card is valid
     */
    public boolean isValid() {
        return (cardNumberLength == 16.0
                && getPrice() != null
                && getExpiryMonth() != null
                && getExpiryYear() != null
                && getCvc() != null);
    }
}
