package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

import java.math.BigDecimal;

public class ProductDiscountValidationRule implements ProductValidationRule {


    @Override
    public void validate(ProductDto productDto) {
        if (productDto.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductValidationException("Product discount must be a positive number.");
        }
        if (productDto.getDiscount().compareTo(BigDecimal.valueOf(100)) >= 0) {
            throw new ProductValidationException("Product discount can't be more than 100%.");
        }
        if (productDto.getPrice().compareTo(BigDecimal.valueOf(20)) < 0) {
            productDto.setDiscount(BigDecimal.ZERO);
        }
    }
}
