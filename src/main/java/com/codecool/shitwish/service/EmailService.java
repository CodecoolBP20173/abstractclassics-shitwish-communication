package com.codecool.shitwish.service;

import com.codecool.shitwish.model.Order;
import com.codecool.shitwish.model.Present;
import com.codecool.shitwish.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void sendSoldMail(User buyer, List sellersUncasted, List presentsUncasted) {
        List<User> sellers = castListElementsToUserObject(sellersUncasted);
        List<Present> presents = castListElementsToPresentObject(presentsUncasted);
        for (User seller: sellers) {
            List<Present> presentsSoldBySeller = getPresentsSoldBySeller(seller, presents);
            sendMailToSeller(buyer, seller, presentsSoldBySeller);
        }
    }

    private List<User> castListElementsToUserObject(List elements) {
        List<User> users = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object element: elements) {
            User user = objectMapper.convertValue(element, User.class);
            users.add(user);
        }

        return users;
    }

    private List<Present> castListElementsToPresentObject(List elements) {
        List<Present> presents = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object element: elements) {
            Present present = objectMapper.convertValue(element, Present.class);
            presents.add(present);
        }

        return presents;
    }

    private void sendMailToSeller(User buyer, User seller, List<Present> presents) {
        String recipient = seller.getEmail();
        String presentIds = getPresentIdsAsString(presents);
        String subject = "Successful sale, Present id(s): " + presentIds;
        String content = mailContentBuilder.buildSoldMail(buyer, seller, presents);
        prepareAndSend(recipient, subject, content);
    }

    private String getPresentIdsAsString(List<Present> presents) {
        List<String> presentIds = new ArrayList<>();
        for (Present present: presents) {
            presentIds.add(present.getId().toString());
        }
        return String.join(", ", presentIds);
    }

    private List<Present> getPresentsSoldBySeller(User seller, List<Present> presents) {
        List<Present> presentsSoldBySeller= new ArrayList<>();
        for (Present present: presents) {
            if (present.getUserId().equals(seller.getId())) {
                presentsSoldBySeller.add(present);
            }
        }

        return presentsSoldBySeller;
    }
}
