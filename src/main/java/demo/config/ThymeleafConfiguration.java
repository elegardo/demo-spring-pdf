package demo.config;

import static org.thymeleaf.templatemode.TemplateMode.HTML;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver emplateResolver = new ClassLoaderTemplateResolver();
        emplateResolver.setPrefix("templates/");
        emplateResolver.setSuffix(".html");
        emplateResolver.setTemplateMode(HTML);
        emplateResolver.setCharacterEncoding("UTF-8");
        emplateResolver.setOrder(1);
        return emplateResolver;
    }

}