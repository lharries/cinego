package utils;

import com.sendgrid.*;

import java.io.IOException;

/**
 * Send emails using the sendGridApi
 * <p>
 * Source:
 * - https://app.sendgrid.com/guide/integrate/langs/java
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class EmailsUtil {

    private static String apiKey = "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";

    public static void main(String[] args) {
        sendEmail("luke.harries.17@ucl.ac.uk", "Header", "Content",
                "Luke Harries. 21st December. The hills have eyes too");
    }

    public static void sendEmail(String toEmail, String headerText, String contentText, String bookingInfo) {
        Email from = new Email("luke.harries.17@ucl.ac.uk");
        String subject = headerText;
        Email to = new Email("luke.harries.17@ucl.ac.uk");
        Content content = new Content("text/html", contentText +
                "<br><br><img src=\"https://api.qrserver.com/v1/create-qr-code/?data=+"
                + bookingInfo +
                "+&amp;size=400x400\" alt=\"Your ticket\" title=\"Your ticket\" />");

        Mail mail = new Mail(from, subject, to, content);


        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            try {
                throw ex;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
