package productService.service.validation.validationRules;

import productService.dto.ProductDto;
import org.springframework.stereotype.Component;
import productService.service.validation.ProductValidationException;

@Component
public class ProductDescriptionValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto dto) {
        if ((dto.getDescription() != null) && (dto.getDescription().length() > 60)) {
            throw new ProductValidationException("Description is too long.");
        }
    }
}
