# Thymeleaf FEEL
[![Community Extension](https://img.shields.io/badge/Community%20Extension-An%20open%20source%20community%20maintained%20project-FF4700)](https://github.com/camunda-community-hub/community)
![Compatible with: Camunda Platform 8](https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%208-0072Ce)
[![](https://img.shields.io/badge/Lifecycle-Incubating-blue)](https://github.com/Camunda-Community-Hub/community/blob/main/extension-lifecycle.md#incubating-)

The goal of this project is to provide a simple way to evaluate [Thymeleaf](https://www.thymeleaf.org/) templates with [FEEL](https://docs.camunda.io/docs/components/modeler/feel/what-is-feel/) expressions.

# How to use this library

The templates expressions are evaluated with [FEEL](https://docs.camunda.io/docs/components/modeler/feel/what-is-feel/) so you can use expressions like ${now()+duration("P3M")}. For a good overview of available expressions, you can refer to [Camunda's FEEL expression introduction](https://docs.camunda.io/docs/components/modeler/feel/language-guide/feel-expressions-introduction/) 

This can be changed by modifying the ThymeleafConfig encoding, prefix, expression language, date formatting patterns, etc.

The default behavior is to read templates from the "template" resource folder. They should be suffixed by ".html". You can override this behaviour by providing a customTemplateResolver. You can use this mechanism to load templates from a database or from a file system.

```java
ThymeleafConfig config = new ThymeleafConfig();
config.setDatePattern("dd/MM/yyyy");
config.setPrefix("/somewhere/");
config.setSuffix(".xhtml");
config.setFeelExpressions(true);
config.setCustomTemplateResolver(new ICustomTemplateResolver() {
  @Override
  public String getTemplateContent(String templateName) {
    return "Test <span th:text='${username}'></span></h4>";
  }
});
Context context = new Context();
[...]
TemplateEngineFactory.getTemplateEngine("engineName", config).process("templateName", context)
```

You can use as many TemplateEngine as you need with different configs. If you don't give the engine a name, you will get the default one. 


# use it in your project
You can import it to your maven or gradle project as a dependency

```xml
<dependency>
	<groupId>io.camunda</groupId>
	<artifactId>thymeleaf-feel</artifactId>
	<version>1.1.1</version>
</dependency>
```

# Note
Issues and PRs are welcome.
