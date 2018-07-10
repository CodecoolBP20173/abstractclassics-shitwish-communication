package com.codecool.shitwish.api;

import com.codecool.shitwish.model.User;
import com.codecool.shitwish.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (MailException e) {
            e.printStackTrace();
            return HttpStatus.I_AM_A_TEAPOT;
        }
    }

}
