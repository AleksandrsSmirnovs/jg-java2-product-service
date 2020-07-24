package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

public class ProductDescriptionValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto dto) {
        if ((dto.getDescription() != null) && (dto.getDescription().length() > 60)) {
            throw new ProductValidationException("Description is too long.");
        }
    }
}
