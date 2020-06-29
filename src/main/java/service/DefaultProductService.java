package service;

import converters.ProductDtoEntityConverter;
import domain.ProductEntity;
import dto.ProductDto;
import service.discount.DiscountService;
import service.validation.ProductValidator;
import domain.ProductCategory;
import repository.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultProductService implements ProductService {

    private final Repository<Long, ProductEntity> repository;
    private final ProductValidator validator;
    private final DiscountService discountService;
    private final ProductDtoEntityConverter converter;

    public DefaultProductService(Repository repository,
                                 ProductValidator validator,
                                 DiscountService discountService,
                                 ProductDtoEntityConverter converter) {
        this.repository = repository;
        this.validator = validator;
        this.discountService = discountService;
        this.converter = converter;
    }

    @Override
    public List<ProductDto> findAll() {

        return repository.findAll().stream().map(converter::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findByID(Long id) {
        return converter.toDto(repository.findByID(id));
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public void save(ProductDto dto) {
        if ((dto.getCategory() != null) && (discountService.getCategoryDiscount(dto).compareTo(BigDecimal.ZERO) != 0)) {
            discountService.setDiscountForProduct(dto, discountService.getCategoryDiscount(dto));
        }
        validator.validateProduct(dto);
        repository.save(converter.toEntity(dto));
    }

    @Override
    public void setDiscountForCategory(ProductCategory category, BigDecimal discount) {
        discountService.setDiscountForCategory(category, discount);
    }

    public void fillSampleData() {
        save(new ProductDto.Builder()
                .buildCategory(ProductCategory.FRUIT)
                .buildName("Lemon")
                .buildPrice(BigDecimal.valueOf(0.5))
                .buildDiscount(BigDecimal.valueOf(50))
                .buildDescription("dasdf")
                .build());
        save(new ProductDto.Builder()
                .buildCategory(ProductCategory.DUMPLINGS)
                .buildName("Pelmen")
                .buildPrice(BigDecimal.valueOf(3.8))
                .build());
        save(new ProductDto.Builder()
                .buildCategory(ProductCategory.VEGETABLE)
                .buildName("Potato")
                .buildPrice(BigDecimal.valueOf(0.4))
                .build());
        save(new ProductDto.Builder()
                .buildCategory(ProductCategory.MEAT)
                .buildName("Beef")
                .buildPrice(BigDecimal.valueOf(6.5))
                .build());
        save(new ProductDto.Builder()
                .buildCategory(ProductCategory.SOFT_DRINKS)
                .buildName("Fanta")
                .buildPrice(BigDecimal.valueOf(1.0))
                .build());
        save(new ProductDto.Builder()
                .buildCategory(ProductCategory.FRUIT)
                .buildName("Orange")
                .buildPrice(BigDecimal.valueOf(0.8))
                .build());
    }


}
