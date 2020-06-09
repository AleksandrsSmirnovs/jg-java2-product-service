package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

import java.math.BigDecimal;

public class ProductPriceValidationRule implements ProductValidationRule {
    @Override
    public void validate(ProductDto productDto) {
        if (productDto.getPrice()==null) {
            throw new ProductValidationException("Product price must be not null.");
        }
        if (productDto.getPrice().compareTo(BigDecimal.ZERO)<=0) {
            throw new ProductValidationException("Product price must be a positive number.");
        }
    }
}
