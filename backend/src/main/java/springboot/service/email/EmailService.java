package springboot.service.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;

@Service
public class EmailService {

    private static final Logger LOGGER = LogManager.getLogger(EmailService.class);

    public boolean sendRegistrationConfirmLink(String clientEmailAddress, String clientPassword) {
        LOGGER.info("Attempt to send confirmation email.");
        String encodedEmail = Base64.getEncoder().encodeToString(clientEmailAddress.getBytes());
        String encodedPassword = Base64.getEncoder().encodeToString(clientPassword.getBytes());
        long time = LocalDateTime.now()
                .plusHours(1)
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        String encodedLifeTime = Base64.getEncoder().encodeToString(String.valueOf(time).getBytes());
        String confirmationLink = "http://localhost:8081/registration-confirm/" + encodedEmail + ":" + encodedPassword + ":" + encodedLifeTime;
        String subject = "Registration confirmation";
        String message = "To complete your sign up, please, paste the following link into your browser: " +
                "\n" +
                "\n" +
                confirmationLink +
                "\n" +
                "\n" +
                " You’re receiving this email because you recently created a new account. If this wasn’t you, please ignore this email.";
        return sendEmail(subject, message, clientEmailAddress);
    }

    public boolean sendPasswordUpdateLink(String clientEmailAddress) {
        LOGGER.info("Attempt to send password update link.");
        String encodedEmail = Base64.getEncoder().encodeToString(clientEmailAddress.getBytes());
        long time = LocalDateTime.now()
                .plusHours(1)
                .atZone(ZoneId.systemDefault())
                .toInstant().toEpochMilli();
        String encodedLifeTime = Base64.getEncoder().encodeToString(String.valueOf(time).getBytes());
        String passwordUpdateLink = "http://localhost:8081/password-update/" + encodedEmail + ":" + encodedLifeTime;
        String subject = "Please, change your password.";
        String message = "To change your password you can use the following link: " +
                "\n" +
                "\n" +
                passwordUpdateLink +
                "\n" +
                "\n" +
                " The link is available for 1 hour.";
        return sendEmail(subject, message, clientEmailAddress);
    }

    public boolean sendNewCalendarEntry(String name, String mobileNr, String mail, String eventType, Long id) {
        String subject = "New Event.";
        String message =
                "<html><body>New event is pending. " +
                "\n" +
                "\n" +
                "From " + name + ", number: " + mobileNr + "\n" +
                "\n" + "Event type: " + eventType + "\n" +
                "\n" +
                "<a href=\"http://localhost:8080/api/aprove/" + id + "\"> Click here to approve! </a>" + "\n" +
                "\n" +
                "Videography. </html></body>";
        return sendEmail(subject, message, mail);
    }

    private boolean sendEmail(String subject, String message, String clientEmailAddress) {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("vali.testeaza@gmail.com", "Valitesteaza1"));
        email.setSSLOnConnect(true);
        try {
            email.setFrom("vali.testeaza@gmail.com");
            email.setSubject(subject);
            email.setContent(message, "text/html");
//            email.setMsg(message);
            email.addTo(clientEmailAddress);
            email.send();
            LOGGER.info("Email was send successfully.");
            return true;
        } catch (EmailException e) {
            LOGGER.fatal("Failed to send email", e);
            return false;
        }
    }
}
