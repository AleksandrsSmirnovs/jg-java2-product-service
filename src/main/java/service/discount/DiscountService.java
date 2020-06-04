package service.discount;

import domain.Product;
import domain.ProductCategory;

import java.math.BigDecimal;

public interface DiscountService {

    void setDiscountForProduct(Product product);

    void setDiscountForCategory(ProductCategory category, BigDecimal discount);

    void checkForCategoryDiscount(Product product);

}
