package utils;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import models.PaymentInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom payments handler for stripe! Api keys are only test keys. In production mode you would
 * need to either keep the source code hidden and the jar file inaccessible or have separate employee and customer
 * applications to keep the secret api key safe
 *
 * @author lukeharries kaiklasen
 */
public class PaymentsUtil {

    /**
     * Charge the credit card details using stripe
     *
     * @param paymentInfo The {@link PaymentInfo} object including which card to charge and how much
     * @return whether the charge was successful
     */
    public static boolean chargeCreditCard(PaymentInfo paymentInfo) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        try {
            Token token = createToken(paymentInfo.getCardNumber(), paymentInfo.getExpiryMonth(), paymentInfo.getExpiryYear(), paymentInfo.getCvc());
            createCharge(token, paymentInfo.getPrice());
            return true;

        } catch (CardException | APIException | InvalidRequestException | AuthenticationException | APIConnectionException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Create the token using the credit card information which will then be charged
     * This is normally done from the client side (this JavaFX application).
     * The api key is public and publishable (in this case just a test api key)
     *
     * @param cardNumber  the credit card number
     * @param expiryMonth the month of expiry
     * @param expiryYear  the year of expiry
     * @param cvc         the cvc number
     * @return the token
     * @throws CardException
     * @throws APIException
     * @throws AuthenticationException
     * @throws InvalidRequestException
     * @throws APIConnectionException
     */
    private static Token createToken(long cardNumber, int expiryMonth, int expiryYear, int cvc) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        String publicApiKey = "pk_test_niT9sIktsQx3n0R6btPFVeCB";

        //create card
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expiryMonth);
        cardParams.put("exp_year", expiryYear);
        cardParams.put("cvc", cvc);

        //create token
        Map<String, Object> tokenParams = new HashMap<String, Object>();
        tokenParams.put("card", cardParams);
        Token token;
        try {
            Stripe.apiKey = publicApiKey;
            return Token.create(tokenParams);
        } catch (AuthenticationException | InvalidRequestException | CardException | APIConnectionException | APIException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Charges the token which was made by a credit card
     * Note in a live application this should be moved server side if the application sourcecode is going to be
     * made accessible to the public
     *
     * @param token The token from which the credit card was made
     * @return
     */
    private static void createCharge(Token token, int price) {
        String privateApiKey = "sk_test_EgItpWbz0JklAlzlo4zpQSsn";

        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey(privateApiKey).build();

        //        charge the card
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", price);
        chargeMap.put("currency", "GBP");
        chargeMap.put("source", token.getId());
        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            System.out.println(charge);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }


}
