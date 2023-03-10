package com.chibuisi.dailyinsightservice.template;

public class TemplateUtil {
    public static String getTemplateByTopicName(String topicName){
        switch (topicName){
            case "word":
                return "word.ftl";
            case "company":
                return "company.ftl";
            case "technology":
                return "technology.ftl";
            case "motivation":
                return "motivation.ftl";
            default:
                return "default.ftl";
        }
    }
}
