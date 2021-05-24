package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    TaskRepository taskRepository;
    @Value("${info.company.name}")
    private String companyName;
    @Value("${info.company.email}")
    private String companyEmail;


    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("preview", "New task appeared in your account");
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://zephirus91.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye_message","Thank you for using our services.");
        context.setVariable("company_name", companyName);
        context.setVariable("company_email", companyEmail);
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyInfoEmail(String message) {
        long tasksNumber = taskRepository.count();

        Context context = new Context();
        context.setVariable("preview", "Daily information about my tasks");
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://zephirus91.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("goodbye_message","Thank you for using our services.");
        context.setVariable("company_name", companyName);
        context.setVariable("company_email", companyEmail);
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);

        if (tasksNumber == 0)
            context.setVariable("zero_tasks",true);

        return templateEngine.process("mail/daily-info-mail", context);
    }
}
