package productService.service.validation.validationRules;

import productService.dto.ProductDto;

public interface ProductValidationRule {

    void validate(ProductDto productDto);
}
