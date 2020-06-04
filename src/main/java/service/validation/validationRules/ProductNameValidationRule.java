package service.validation.validationRules;

import service.validation.ProductValidationException;
import domain.Product;

public class ProductNameValidationRule implements ProductValidationRule {
    @Override
    public void validate(Product product) {
        if (product.getName() == null) throw new ProductValidationException("Product name must not be null.");
        if (product.getName().length() < 3) throw new ProductValidationException("Product name is too short.");
        if (product.getName().length() > 32) throw new ProductValidationException("Product name is too long.");
    }
}
