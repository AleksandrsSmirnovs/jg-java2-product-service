package productService.service.validation.validationRules;

import productService.dto.ProductDto;
import org.springframework.stereotype.Component;
import productService.service.validation.ProductValidationException;

@Component
public class ProductNameValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto dto) {
        if (dto.getName() == null) throw new ProductValidationException("Product name must be not null.");
        if (dto.getName().length() < 3) throw new ProductValidationException("Product name is too short.");
        if (dto.getName().length() > 32) throw new ProductValidationException("Product name is too long.");
    }
}
