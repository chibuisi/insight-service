package com.chibuisi.dailyinsightservice.template;

import com.chibuisi.dailyinsightservice.springsecapp.EmailTemplateIdentifier;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

@Component
public class TemplateUtil {
    @Autowired
    private Configuration configuration;

    public static String getTemplateByTopicName(String topicName){
        switch (topicName){
            case "word":
                return "word.ftlh";
            case "company":
                return "company.ftlh";
            case "technology":
                return "technology.ftlh";
            case "motivation":
                return "motivation.ftlh";
            default:
                return "default.ftlh";
        }
    }

    public String getTemplate(String templateId, Map<String, Object> model) {
        String htmlTemplate = "";
        switch (templateId){
            case "word":
                htmlTemplate = "word.ftlh";
                break;
            case "company":
                htmlTemplate = "company.ftlh";
                break;
            case "technology":
                htmlTemplate = "technology.ftlh";
                break;
            case "motivation":
                htmlTemplate = "motivation.ftlh";
                break;
            case "resetpassword":
                htmlTemplate = "resetpassword.ftlh";
                break;
            default:
                htmlTemplate =  "default.ftlh";
        }
        Template template = null;
        try {
            template = configuration
                    .getTemplate(htmlTemplate);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTemplate(EmailTemplateIdentifier emailTemplateIdentifier, Map<String, Object> model) {
        String htmlTemplate = "";
        switch (emailTemplateIdentifier) {
            case RESET_PASSWORD:
                htmlTemplate = "resetpassword.ftlh";
                break;
            case RESET_PASSWORD_CONFIRMATION:
                htmlTemplate = "resetpasswordconfirmation.ftlh";
                break;
            default:
                htmlTemplate = "default";
        }
        try {
            Template template = configuration
                    .getTemplate(htmlTemplate);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
