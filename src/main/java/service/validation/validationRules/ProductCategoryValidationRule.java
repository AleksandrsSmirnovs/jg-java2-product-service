package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

public class ProductCategoryValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto productDto){
        if (productDto.getCategory() == null) throw new ProductValidationException("Product category must be not null.");
    }
}
