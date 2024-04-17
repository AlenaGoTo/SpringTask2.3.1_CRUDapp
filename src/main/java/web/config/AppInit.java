package web.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

// класс инициализатора конфигурации (из WebConfig) Spring Context
// предоставляет возможность настроить 2 контекста: корневой и сервлета
// ApplicationContext обычно содержит все сервисные и инфрастуктурные бины вашего приложения.
// WebServletApplicationContext обычно содержит контекст относящийся к отдельному DispatcherServlet.
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Метод, указывающий на класс конфигурации корневого контекста, который будет предоставлен ContextLoaderListener.
    // (null если корневой контекст нежелателен)
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // Вопрос? Если же у нас только один класс конфигурации, то его нужно передать в метод getRootConfigClasses(),
    // а getServletConfigClasses() должен возвращать null. Ответ: тут без разницы
    // Добавление конфигурации контекста приложения сервлета, который будет предоставлен DispatcherServlet.
    // в которой инициализируем ViewResolver, для корректного отображения jsp.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    /* Данный метод указывает url, на котором будет базироваться приложение */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    // для работы DELETE и PATCH
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new HiddenHttpMethodFilter() };
    }

    /* Еще вариант
    https://stackoverflow.com/questions/72782696/request-method-post-not-supported-error-for-thymeleaf-thmethod-put
    https://stackoverflow.com/questions/18056045/hiddenhttpmethodfilter-configuration-without-xml

    import javax.servlet.ServletContext;
    import javax.servlet.ServletException;
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }*/
}