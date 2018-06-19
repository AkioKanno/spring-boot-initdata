package test.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String PROPERTY_DDL_AUTO;

    @Bean
    public InputData inputData() {
        return ("create".equals(this.PROPERTY_DDL_AUTO)) ? new InputData() : null;
    }
}
