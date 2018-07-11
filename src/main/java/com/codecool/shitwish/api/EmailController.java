package com.codecool.shitwish.api;

import com.codecool.shitwish.model.Order;
import com.codecool.shitwish.model.Present;
import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EmailController {


    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(value = "/registration")
    public HttpStatus sendRegistrationEmail(@RequestBody User user) {
        try {
            emailService.sendRegistrationMail(user);
            return HttpStatus.OK;
        } catch(Exception e)  {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping(value = "/purchase")
    public HttpStatus sendPurchaseEmail(@RequestBody Map<String, Object> objectMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.convertValue(objectMap.get("user"), User.class);
            Order order = objectMapper.convertValue(objectMap.get("order"), Order.class);
            emailService.sendPurchaseEmail(user, order);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping(value = "/sold")
    public HttpStatus sendSoldEmail(@RequestBody Map<String, Object> objectMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User buyer = objectMapper.convertValue(objectMap.get("buyer"), User.class);
            List sellers = objectMapper.convertValue(objectMap.get("sellers"), List.class);
            List presents = objectMapper.convertValue(objectMap.get("presents"), List.class);
            emailService.sendSoldMail(buyer, sellers, presents);
            return HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
