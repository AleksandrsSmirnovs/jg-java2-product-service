package productService.service;

import productService.converters.ProductDtoEntityConverter;
import productService.dto.ProductDto;
import org.springframework.stereotype.Service;
import productService.service.discount.DiscountService;
import productService.service.validation.ProductValidator;
import productService.domain.ProductCategory;
import productService.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator validator;
    private final DiscountService discountService;
    private final ProductDtoEntityConverter converter;

    public DefaultProductService(ProductRepository productRepository,
                                 ProductValidator validator,
                                 DiscountService discountService,
                                 ProductDtoEntityConverter converter) {
        this.productRepository = productRepository;
        this.validator = validator;
        this.discountService = discountService;
        this.converter = converter;
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(converter::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findByID(Long id) {
        return converter.toDto(productRepository.findByID(id));
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    public void save(ProductDto dto) {
        if ((dto.getCategory() != null) && (discountService.getCategoryDiscount(dto).compareTo(BigDecimal.ZERO) != 0)) {
            discountService.setDiscountForProduct(dto, discountService.getCategoryDiscount(dto));
        }
        validator.validateProduct(dto);
        productRepository.save(converter.toEntity(dto));
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
