package productService.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import productService.service.validation.validationRules.*;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "productService")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${database.url}")
    private String url;
    @Value("${database.driver}")
    private String dbDriverName;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public List<ProductValidationRule> listOfRules(
            ProductNameValidationRule productNameValidationRule,
            ProductUniqueNameValidationRule productUniqueNameValidationRule,
            ProductPriceValidationRule productPriceValidationRule,
            ProductDiscountValidationRule productDiscountValidationRule,
            ProductDescriptionValidationRule productDescriptionValidationRule
    ) {
        return List.of(
                productNameValidationRule,
                productUniqueNameValidationRule,
                productPriceValidationRule,
                productDiscountValidationRule,
                productDescriptionValidationRule
        );
    }


}
