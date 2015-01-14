package com.bdcyclists.bdcbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/14/15.
 */
//TODO we will change this emailService to serve html formatted email
@Service
public class EmailService {
    private static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendUserResetPasswordEmail(final String recipientName, final String recipientEmail, String resetHashUrl)
            throws MessagingException {
        LOGGER.debug("sendUserResetPasswordEmail() recipientName = {}, recipientEmail = {}, resetHashUrl = {}", recipientName, recipientEmail, resetHashUrl);


        String htmlContent = "<html>\n" +
                "<head>\n" +
                "    <title>BDCyclists Password Recovery</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>BDCyclists Password Recovery</h3>\n" +
                "\n" +
                "<p> Hello " + recipientName + ", </p>\n" +
                "\n" +
                "<p>You recently notified us that you forgot your BDCylist account's Password. Your email Address:<br/>\n" +
                "    " + recipientEmail + " <br/><br/>\n" +
                "    Please click below to reset your password:<br/>\n" +
                "\n" +
                "    <a href=\"" + resetHashUrl + "\">Reset\n" +
                "        my password</a>\n" +
                "    <br/><br/>\n" +
                "    Cheers,<br/>\n" +
                "\n" +
                "    BDCyclists Team\n" +
                "</p>\n" +
                "\n" +
                "<p>\n" +
                "    P.S. If you didn't request, it's likely that someone accidentally entered your email address, you can just ignore\n" +
                "    this email. As long as you don't click the link contained in the email, no action will be taken and your account\n" +
                "    will remain secure and password will not be changed.\n" +
                "\n" +
                "</p>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart

        mimeMessageHelper.setSubject("BDCyclists Password Recovery");
        mimeMessageHelper.setTo(recipientEmail);
        mimeMessageHelper.setText(htmlContent, true); // true = isHtml
        mimeMessageHelper.setFrom("no-reply@bdcyclists.com");

        try {
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("Can't send message", e);
        }
    }
}
