package service.discount;

import domain.ProductCategory;
import dto.ProductDto;

import java.math.BigDecimal;

public interface DiscountService {

    void setDiscountForProduct(ProductDto productDto);

    void setDiscountForCategory(ProductCategory category, BigDecimal discount);

    void checkForCategoryDiscount(ProductDto productDto);

}
