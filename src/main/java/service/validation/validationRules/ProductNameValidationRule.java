package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

public class ProductNameValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto dto) {
        if (dto.getName() == null) throw new ProductValidationException("Product name must be not null.");
        if (dto.getName().length() < 3) throw new ProductValidationException("Product name is too short.");
        if (dto.getName().length() > 32) throw new ProductValidationException("Product name is too long.");
    }
}
