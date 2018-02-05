package utils;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import controllers.EmployeeRootController;
import models.PaymentInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom payments handler for stripe! Api keys are only test keys. In production mode you would
 * need to either keep the source code hidden and the jar file inaccessible or have separate employee and customer
 * applications to keep the secret api key safe
 *
 * @author lukeharries kaiklasen
 */
public class PaymentsUtil {

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    /**
     * Charge the credit card details using stripe
     *
     * @param paymentInfo The {@link PaymentInfo} object including which card to charge and how much
     * @return whether the charge was successful
     * @throws CardException           CardException There is a problem with the card
     * @throws APIException            APIException there is a problem with the api
     * @throws AuthenticationException AuthenticationException there is a problem authenticating
     * @throws InvalidRequestException InvalidRequestException invalid request
     * @throws APIConnectionException  APIConnectionException unable to connect to the api
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
     * @throws CardException           CardException There is a problem with the card
     * @throws APIException            APIException there is a problem with the api
     * @throws AuthenticationException AuthenticationException there is a problem authenticating
     * @throws InvalidRequestException InvalidRequestException invalid request
     * @throws APIConnectionException  APIConnectionException unable to connect to the api
     */
    private static Token createToken(long cardNumber, int expiryMonth, int expiryYear, int cvc) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        String publicApiKey = "API_KEY";

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
            LOGGER.logp(Level.INFO, "PaymentsUtil", "createCharge", "charge created" + charge);
        } catch (StripeException e) {
            LOGGER.logp(Level.WARNING, "PaymentsUtil", "createCharge", "Unable to create the charge" + e);
            e.printStackTrace();
        }
    }


}
