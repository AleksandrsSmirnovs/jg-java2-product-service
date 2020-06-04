package service.validation.validationRules;

import service.validation.ProductValidationException;
import domain.Product;

import java.math.BigDecimal;

public class ProductPriceValidationRule implements ProductValidationRule {
    @Override
    public void validate(Product product) {
        if (product.getPrice().compareTo(BigDecimal.ZERO)<=0) {
            throw new ProductValidationException("Product price must be a positive number.");
        }
        if (product.getPrice()==null) {
            throw new ProductValidationException("Product price must be not null.");
        }
    }
}
