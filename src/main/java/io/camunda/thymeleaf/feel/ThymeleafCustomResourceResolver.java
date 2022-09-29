package io.camunda.thymeleaf.feel;

import java.util.Map;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import io.camunda.thymeleaf.feel.config.ThymeleafConfig;

public class ThymeleafCustomResourceResolver extends StringTemplateResolver {
    
    private ICustomTemplateResolver templateResolver;
    
    public ThymeleafCustomResourceResolver(ThymeleafConfig config) {
        this.templateResolver  = config.getCustomTemplateResolver();
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, Map<String, Object> templateResolutionAttributes) {

        String templateContent = templateResolver.getTemplateContent(template);
        if (templateContent != null) {
            return super.computeTemplateResource(configuration, ownerTemplate, templateContent, templateResolutionAttributes);
        }
        return null;
    }

}
