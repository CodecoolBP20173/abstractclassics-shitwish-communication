package com.codecool.shitwish.service;

import com.codecool.shitwish.model.Order;
import com.codecool.shitwish.model.Present;
import com.codecool.shitwish.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildRegistrationMail(@ModelAttribute User user) {
        Context context = new Context();
        context.setVariable("user", user);
        return templateEngine.process("registrationMail", context);
    }

    public String buildPurchaseMail(User user, Order order) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("order", order);
        return templateEngine.process("purchaseMail", context);
    }

    public String buildSoldMail(User buyer, User seller, List<Present> presents) {
        Context context = new Context();
        context.setVariable("buyer", buyer);
        context.setVariable("seller", seller);
        context.setVariable("presents", presents);
        return templateEngine.process("soldMail", context);
    }
}
