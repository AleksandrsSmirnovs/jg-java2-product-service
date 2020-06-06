package service.discount;

import converters.ProductDtoEntityConverter;
import domain.ProductCategory;
import dto.ProductDto;
import repository.DiscountRepository;

import java.math.BigDecimal;

public class ProductDiscountService implements DiscountService {

    private DiscountRepository repository;
    private ProductDtoEntityConverter converter;

    public ProductDiscountService(DiscountRepository repository, ProductDtoEntityConverter converter){

        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void setDiscountForProduct(ProductDto productDto) {


    }

    @Override
    public void setDiscountForCategory(ProductCategory category, BigDecimal discount) {
        repository.setDiscountForCategory(category, discount);
    }

    @Override
    public void checkForCategoryDiscount(ProductDto productDto){
        repository.checkCategoryDiscount(converter.toEntity(productDto));
    }
}
