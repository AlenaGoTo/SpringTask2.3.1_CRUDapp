package web.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

// класс конфигурирования Спринг MVC-приложения (для DispatcherServlet)
@Configuration
@EnableWebMvc // регистрирует бины из Спринг MVC и адаптирует их к нашим бинам
@ComponentScan("web")
public class WebConfig implements WebMvcConfigurer {

    //  главный интерфейс в Spring-приложении, предоставляет информацию о конфигурации приложения
    private final ApplicationContext applicationContext;

    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // ниже настройки интеграции Thymeleaf со Spring MVC
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // определение доступа и чтения ресурсов (преобразователь шаблонов)
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/"); // путь до шаблонов
        templateResolver.setSuffix(".html"); // формат
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        // Создание и настройка объекта TemplateEngine
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver()); // Устанавливает один преобразователь шаблонов для этого механизма шаблонов
        templateEngine.setEnableSpringELCompiler(true); // компилятор SpEL
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // ViewResolver сопоставляет имена представлений, возвращаемые контроллером, с фактическими объектами представлений
        // ThymeleafViewResolver используется для определения того, какие представления Thymeleaf отображать, учитывая имя представления
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }
}