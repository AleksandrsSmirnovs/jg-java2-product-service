package service.validation.validationRules;

import dto.ProductDto;
import service.validation.ProductValidationException;

import java.math.BigDecimal;

public class ProductDiscountValidationRule implements ProductValidationRule {

    @Override
    public void validate(ProductDto dto) {
        if (dto.getDiscount() != null && dto.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductValidationException("Product discount must be a positive number.");
        }
        if (dto.getDiscount() != null && dto.getDiscount().compareTo(BigDecimal.valueOf(100)) >= 0) {
            throw new ProductValidationException("Product discount can't be more than 100%.");
        }
        if (dto.getDiscount() != null && dto.getPrice() != null && dto.getPrice().compareTo(BigDecimal.valueOf(20)) < 0) {
            dto.setDiscount(BigDecimal.ZERO);
        }
    }
}
