package service.discount;

import domain.ProductCategory;
import dto.ProductDto;
import repository.DiscountRepository;

import java.math.BigDecimal;

public class ProductDiscountService implements DiscountService {

    private DiscountRepository repository;

    public ProductDiscountService(DiscountRepository repository){
        this.repository = repository;
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
        repository.checkCategoryDiscount(productDto);
    }
}
