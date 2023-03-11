package com.chibuisi.dailyinsightservice.template;

public class TemplateUtil {
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
}
