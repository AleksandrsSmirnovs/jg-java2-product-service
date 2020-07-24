package productService.service.validation.validationRules;

import productService.dto.ProductDto;
import org.springframework.stereotype.Component;
import productService.service.validation.ProductValidationException;

import java.math.BigDecimal;

@Component
public class ProductPriceValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto dto) {
        if (dto.getPrice() == null) {
            throw new ProductValidationException("Product price must be not null.");
        }
        if (dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException("Product price must be a positive number.");
        }
    }
}
