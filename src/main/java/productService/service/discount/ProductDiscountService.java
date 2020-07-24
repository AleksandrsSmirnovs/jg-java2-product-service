package productService.service.discount;

import productService.converters.ProductDtoEntityConverter;
import productService.domain.ProductCategory;
import productService.domain.ProductEntity;
import productService.dto.ProductDto;
import org.springframework.stereotype.Service;
import productService.repository.InMemoryProductRepository;
import productService.service.validation.ProductValidationException;
import productService.service.validation.validationRules.ProductDiscountValidationRule;

import java.math.BigDecimal;

@Service
public class ProductDiscountService implements DiscountService {

    private InMemoryProductRepository repository;
    private ProductDtoEntityConverter converter;
    private ProductDiscountValidationRule validator;

    public ProductDiscountService(InMemoryProductRepository repository, ProductDtoEntityConverter converter, ProductDiscountValidationRule validator) {
        this.repository = repository;
        this.converter = converter;
        this.validator = validator;
    }

    @Override
    public void setDiscountForProduct(ProductDto dto, BigDecimal dicsount) {
        dto.setDiscount(dicsount);
        validator.validate(dto);
    }

    @Override
    public void setDiscountForCategory(ProductCategory category, BigDecimal discount) {
        repository.setDiscountForCategory(category, discount);
        for (ProductEntity entity : repository.findAll()) {
            if (entity.getCategory().equals(category)) {
                ProductDto dto = converter.toDto(entity);
                try {
                    setDiscountForProduct(dto, discount);
                    validator.validate(dto);
                    repository.save(converter.toEntity(dto));
                } catch (ProductValidationException e) {
                }
            }
        }
    }

    @Override
    public BigDecimal getCategoryDiscount(ProductDto dto) {
        return repository.getCategoryDiscount(converter.toEntity(dto));
    }
}