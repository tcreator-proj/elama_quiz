package quiz_chat.elama_quiz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableAsync(proxyTargetClass = true)
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/content/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/content/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/content/script/**")
                .addResourceLocations("classpath:/static/script/");
    }
}
