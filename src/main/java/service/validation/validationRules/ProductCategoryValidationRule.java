package service.validation.validationRules;

import service.validation.ProductValidationException;
import domain.Product;

public class ProductCategoryValidationRule implements ProductValidationRule {
    @Override
    public void validate(Product product){
        if (product.getCategory() == null) throw new ProductValidationException("Product category must not be null.");
    }
}
