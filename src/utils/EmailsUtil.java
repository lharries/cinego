package utils;

import application.Main;
import com.sendgrid.*;
import controllers.EmployeeRootController;
import models.Seat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Send an email with the booking details and a QR code to the customer
 * <p>
 * References:
 * - https://app.sendgrid.com/guide/integrate/langs/java
 *
 * @author lukeharries kaiklasen
 * @version 1.0.0
 */
public class EmailsUtil {

    private static final Logger LOGGER = Logger.getLogger(EmployeeRootController.class.getName());

    /**
     * The apiKey for sendgrid. In production mode this would be kept in a system environment file and out of
     * access from the public.
     */
    private static String apiKey = "API_KEY";

    /**
     * Sends an email to the customer
     * <p>
     * References:
     * - https://app.sendgrid.com/guide/integrate/langs/java
     *
     * @param toEmail     who you are sending the email to
     * @param headerText  the message header
     * @param contentText the message content
     * @throws IOException IOException
     */
    public static void sendEmail(String toEmail, String headerText, String contentText) throws IOException {
        Email from = new Email("luke.harries.17@ucl.ac.uk");
        String subject = headerText;
        Email to = new Email(toEmail);
        Content content = new Content("text/html", contentText);

        Mail mail = new Mail(from, subject, to, content);


        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException e) {
            LOGGER.logp(Level.WARNING, "EmailsUtil", "sendEmail", "unable to send email" + e);
            throw e;
        }

    }

    /**
     * Creates the booking email content containing the qr code and the screening information
     *
     * @param screeningDate the date and time of the screening
     * @param filmName      the name of the film
     * @param seats         the seats which have been booked
     * @return the email content containing the names, dates and seats bookedg
     */
    public static String createBookingEmailContent(String screeningDate, String filmName, ArrayList<Seat> seats) {
        StringBuilder emailContent = new StringBuilder();

        // intro text
        emailContent.append("Hi ").append(Main.user.getFirstName()).append(" ").append(Main.user.getLastName());
        emailContent.append("<br>");
        emailContent.append("Thanks for booking with Cinego! <br>");
        emailContent.append("<br>");
        emailContent.append("We look forward to see you at: ");
        emailContent.append(screeningDate);
        emailContent.append("<br>");
        emailContent.append("For: ");
        emailContent.append(filmName);
        emailContent.append("<br>");

        // seat info
        emailContent.append("You have booked the following seats:<br>");
        for (Seat seat :
                seats) {
            emailContent.append(" - ").append(seat.getName());
        }

        emailContent.append("<br>Please find your ticket attached below.<br>");
        emailContent.append("We look forward to seeing you on the night<br>");

        // qr code
        emailContent.append("<br><br><img src=\"https://api.qrserver.com/v1/create-qr-code/?data=+");
        emailContent.append(Main.user.getFirstName()).append(" ").append(Main.user.getLastName());
        emailContent.append(screeningDate);
        emailContent.append(" ");
        emailContent.append(filmName);
        emailContent.append(" ");
        for (Seat seat :
                seats) {
            emailContent.append(seat.getName()).append(" ");
        }
        emailContent.append(".");
        emailContent.append("+&amp;size=400x400\" alt=\"Your ticket\" title=\"Your ticket\" />");

        return emailContent.toString();
    }
}
