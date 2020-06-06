package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

public class ProductNameValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto productDto) {
        if (productDto.getName() == null) throw new ProductValidationException("Product name must not be null.");
        if (productDto.getName().length() < 3) throw new ProductValidationException("Product name is too short.");
        if (productDto.getName().length() > 32) throw new ProductValidationException("Product name is too long.");
    }
}
