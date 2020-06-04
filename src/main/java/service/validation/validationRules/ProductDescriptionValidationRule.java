package service.validation.validationRules;

import service.validation.ProductValidationException;
import domain.Product;

public class ProductDescriptionValidationRule implements ProductValidationRule {
    @Override
    public void validate(Product product) {
        if ((product.getDescription()!=null)&&(product.getDescription().length()>100)){
            throw new ProductValidationException("Too long description.");
        }
    }
}
