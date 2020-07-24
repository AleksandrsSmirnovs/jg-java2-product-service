package service.validation.validationRules;

import dto.ProductDto;

public interface ProductValidationRule {

    void validate(ProductDto productDto);
}
