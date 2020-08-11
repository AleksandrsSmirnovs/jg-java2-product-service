package productService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import productService.service.validation.validationRules.*;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "productService")
public class AppConfig {

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
