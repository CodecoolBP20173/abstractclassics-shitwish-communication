package com.codecool.shitwish.service;

import com.codecool.shitwish.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
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
}
