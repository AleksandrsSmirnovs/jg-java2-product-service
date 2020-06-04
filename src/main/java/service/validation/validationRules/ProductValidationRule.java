package service.validation.validationRules;

import domain.Product;

public interface ProductValidationRule {

    void validate(Product product);
}
