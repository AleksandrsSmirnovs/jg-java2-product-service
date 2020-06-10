package service.discount;

import converters.ProductDtoEntityConverter;
import domain.ProductCategory;
import domain.ProductEntity;
import dto.ProductDto;
import repository.DiscountRepository;
import repository.InMemoryProductRepository;
import service.validation.ProductValidationException;
import service.validation.validationRules.ProductDiscountValidationRule;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class ProductDiscountService implements DiscountService {

    private InMemoryProductRepository repository;
    private ProductDtoEntityConverter converter;
    private ProductDiscountValidationRule validator;

    public ProductDiscountService(InMemoryProductRepository repository, ProductDtoEntityConverter converter, ProductDiscountValidationRule validator){
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
    public BigDecimal getCategoryDiscount(ProductDto dto){
        return repository.getCategoryDiscount(converter.toEntity(dto));
    }
}
