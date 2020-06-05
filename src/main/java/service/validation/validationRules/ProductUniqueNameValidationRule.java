package service.validation.validationRules;

import domain.Product;
import repository.Repository;
import service.validation.ProductValidationException;

public class ProductUniqueNameValidationRule implements ProductValidationRule {

    Repository<Long, Product> repository;

    public ProductUniqueNameValidationRule(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(Product product) {
        if (repository.getNameList().contains(product.getName())) {
            throw new ProductValidationException("Product name must be unique.");
        }
    }
}
