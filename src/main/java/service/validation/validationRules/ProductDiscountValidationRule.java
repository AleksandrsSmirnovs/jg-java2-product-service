package service.validation.validationRules;

import domain.Product;
import service.validation.ProductValidationException;

import java.math.BigDecimal;

public class ProductDiscountValidationRule implements ProductValidationRule {


    @Override
    public void validate(Product product) {
        if (product.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductValidationException("Product discount must be a positive number.");
        }
        if (product.getDiscount().compareTo(BigDecimal.valueOf(100)) >= 0) {
            throw new ProductValidationException("Product discount can't be more than 100%.");
        }
        if (product.getPrice().compareTo(BigDecimal.valueOf(20)) < 0) {
            product.setDiscount(BigDecimal.ZERO);
        }
    }
}
