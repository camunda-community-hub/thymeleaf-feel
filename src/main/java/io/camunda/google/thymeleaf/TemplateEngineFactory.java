package io.camunda.google.thymeleaf;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import io.camunda.google.config.ThymeleafConfig;
import io.camunda.google.feel.FeelExpressionEvaluator;


public class TemplateEngineFactory {
    
    private static final String DEFAULT = "defaultEngine";
    
    private static Map<String, TemplateEngine> enginesMap = new HashMap<>();
    
    private static TemplateEngine templateEngine;
    
    /**
     * Returns the default template engine and build it if it doesn't exist.
     * @return the default template engine
     */
    public static TemplateEngine getTemplateEngine() {
        if (enginesMap.containsKey(DEFAULT)) {
            return enginesMap.get(DEFAULT);
        }
        return getTemplateEngine(DEFAULT, new ThymeleafConfig());
    }
    
    /**
     * Returns the default template engine. Build the engine with the provided config if default engine doesn't preexist.
     * @param config the custom config
     * @return the default template engine
     */
    public static TemplateEngine getTemplateEngine(ThymeleafConfig config) {
        if (enginesMap.containsKey(DEFAULT)) {
            return enginesMap.get(DEFAULT);
        }
        return getTemplateEngine(DEFAULT, config);
    }
    
    /**
     * Returns the engine identified by name. Build the engine with default config if named engine doesn't preexist.
     * @param name the name that identifies the engine
     * @return the engine identified by the name
     */
    public static TemplateEngine getTemplateEngine(String name) {
        if (enginesMap.containsKey(name)) {
            return enginesMap.get(name);
        }
        return getTemplateEngine(name, new ThymeleafConfig());
    }
    
    /**
     * Returns the engine identified by name. Build the engine with the provided config if named engine doesn't preexist.
     * @param name the name that identifies the engine
     * @param config the custom config
     * @return the engine identified by the name
     */
    public static synchronized TemplateEngine getTemplateEngine(String name, ThymeleafConfig config) {
        if (enginesMap.containsKey(name)) {
            return enginesMap.get(name);
        }
        templateEngine = new TemplateEngine();
        if (config.getCustomTemplateResolver()==null) {
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setTemplateMode(config.getMode());
            resolver.setCharacterEncoding(config.getEncoding());
            resolver.setPrefix(config.getPrefix());
            resolver.setSuffix(config.getSuffix());
            
            templateEngine.setTemplateResolver(resolver);
        } else {
            ThymeleafCustomResourceResolver resolver = new ThymeleafCustomResourceResolver(config);
            templateEngine.setTemplateResolver(resolver);
        }
        if (config.isFeelExpressions()) {
            for(IDialect dialect : templateEngine.getDialects()) {
                if (dialect instanceof StandardDialect) {
                    ((StandardDialect)dialect).setVariableExpressionEvaluator(new FeelExpressionEvaluator(config));
                }
            }
        }
        enginesMap.put(name, templateEngine);
        return templateEngine;
    }
}
