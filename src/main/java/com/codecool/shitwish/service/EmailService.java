package com.codecool.shitwish.service;

import com.codecool.shitwish.model.Order;
import com.codecool.shitwish.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    private MailContentBuilder mailContentBuilder;

    @Autowired
    public EmailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    private void prepareAndSend(String recipient, String subject, String content) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };

        mailSender.send(messagePreparator);
    }

    public void sendRegistrationMail(User user) {
        String recipient = user.getEmail();
        String subject = "Welcome to Shitwish.com";
        String content = mailContentBuilder.buildRegistrationMail(user);
        prepareAndSend(recipient, subject, content);
    }

    public void sendPurchaseEmail(User user, Order order) {
        String recipient = user.getEmail();
        String subject = "Successful purchase, Order Id: " + order.getId();
        String content = mailContentBuilder.buildPurchaseMail(user, order);
        prepareAndSend(recipient, subject, content);
    }
}
