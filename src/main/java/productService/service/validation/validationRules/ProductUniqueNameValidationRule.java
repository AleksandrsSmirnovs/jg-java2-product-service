package productService.service.validation.validationRules;

import productService.dto.ProductDto;
import org.springframework.stereotype.Component;
import productService.repository.ProductRepository;
import productService.service.validation.ProductValidationException;

@Component
public class ProductUniqueNameValidationRule implements ProductValidationRule {

    ProductRepository repository;

    public ProductUniqueNameValidationRule(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(ProductDto dto) {
        if (dto.getName() != null && repository.getNameList().contains(dto.getName())) {
            throw new ProductValidationException("Product name must be unique.");
        }
    }
}
