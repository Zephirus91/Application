package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Value("${info.company.name}")
    private String companyName;
    @Value("${info.company.email}")
    private String companyEmail;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("preview", "New task appeared in your account");
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://github.com/Zephirus91");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message","Thank you for using our services.");
        context.setVariable("company_name", companyName);
        context.setVariable("company_email", companyEmail);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
