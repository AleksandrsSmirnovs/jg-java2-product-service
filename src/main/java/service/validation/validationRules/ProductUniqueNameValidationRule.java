package service.validation.validationRules;

import dto.ProductDto;
import repository.InMemoryProductRepository;
import service.validation.ProductValidationException;

public class ProductUniqueNameValidationRule implements ProductValidationRule {

    InMemoryProductRepository repository;

    public ProductUniqueNameValidationRule(InMemoryProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(ProductDto productDto) {
        if (repository.getNameList().contains(productDto.getName())) {
            throw new ProductValidationException("Product name must be unique.");
        }
    }
}
