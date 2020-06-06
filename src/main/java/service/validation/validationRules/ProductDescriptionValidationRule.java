package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

public class ProductDescriptionValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto productDto) {
        if ((productDto.getDescription()!=null)&&(productDto.getDescription().length()>100)){
            throw new ProductValidationException("Too long description.");
        }
    }
}
