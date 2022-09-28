package io.camunda.google.thymeleaf;

public interface ICustomTemplateResolver {

    String getTemplateContent(String templateName);
    
}
