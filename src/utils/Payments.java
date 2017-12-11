package utils;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;

public class Payments {

    public static void main(String[] args) {
        Token token = null;
        try {
            token = createToken(4242424242424242L, 12, 2018, 131);
            createCharge(token, 100);

        } catch (CardException | APIException | InvalidRequestException | AuthenticationException | APIConnectionException e) {
            e.printStackTrace();
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
    private static Token createCharge(Token token, int price) {
        String privateApiKey = "sk_test_EgItpWbz0JklAlzlo4zpQSsn";

        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey(privateApiKey).build();


        //        charge the card
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", price);
        chargeMap.put("currency", "usd");
        chargeMap.put("source", token.getId());
        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            System.out.println(charge);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }


}
