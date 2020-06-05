package service.discount;

import domain.Product;
import domain.ProductCategory;
import repository.DiscountRepository;

import java.math.BigDecimal;

public class ProductDiscountService implements DiscountService {

    private DiscountRepository repository;

    public ProductDiscountService(DiscountRepository repository){
        this.repository = repository;
    }

    @Override
    public void setDiscountForProduct(Product product) {


    }

    @Override
    public void setDiscountForCategory(ProductCategory category, BigDecimal discount) {
        repository.setDiscountForCategory(category, discount);
    }

    @Override
    public void checkForCategoryDiscount(Product product){
        repository.checkForCategoryDiscount(product);
    }
}
