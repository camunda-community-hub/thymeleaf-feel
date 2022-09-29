package io.camunda.google;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.thymeleaf.context.Context;

import io.camunda.thymeleaf.feel.ICustomTemplateResolver;
import io.camunda.thymeleaf.feel.TemplateEngineFactory;
import io.camunda.thymeleaf.feel.config.ThymeleafConfig;

public class TemplateEngineFactoryTest {

    @Test
    public void populateResourceTemplate() {
        String body = populateTemplate("template", Map.of("username", "blop"));
        Assertions.assertTrue(body.contains("<span>blop</span>"), body);
        Pattern datePatten = Pattern.compile("Date <span>[0-9]{2}/[0-9]{2}/[0-9]{4}");
        Matcher matcherDate = datePatten.matcher(body);
        Assertions.assertTrue(matcherDate.find(), body);
        Pattern dateTimePatten = Pattern.compile("Date time <span>[0-9]{2}/[0-9]{2}/[0-9]{4} - [0-9]{2}:[0-9]{2}:[0-9]{2}");
        Matcher matcherDateTime = dateTimePatten.matcher(body);
        Assertions.assertTrue(matcherDateTime.find(), body);
    }
    
    @Test
    public void customResolver() {
        ThymeleafConfig config = new ThymeleafConfig();
        config.setCustomTemplateResolver(new ICustomTemplateResolver() {
            
            @Override
            public String getTemplateContent(String templateName) {
                return "Test <span th:text='${username}'></span></h4>";
            }
        });
  
        String body = populateTemplate("custom", Map.of("username", "blop"), config);
        Assertions.assertTrue(body.contains("Test <span>blop</span>"), body);
    }
    
    public static String populateTemplate(String template, Map<String, Object> variables) {
        return populateTemplate(template, variables, new ThymeleafConfig());
    }
    
    public static String populateTemplate(String template, Map<String, Object> variables, ThymeleafConfig config) {
        Context context = new Context();

        for(Map.Entry<String, Object> entry : variables.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
       
        return TemplateEngineFactory.getTemplateEngine(template, config).process(template, context);
    }
}
