package productService.service.discount;

import productService.domain.ProductCategory;
import productService.dto.ProductDto;

import java.math.BigDecimal;

public interface DiscountService {

    void setDiscountForProduct(ProductDto dto, BigDecimal discount);

    BigDecimal getCategoryDiscount(ProductDto dto);

    void setDiscountForCategory(ProductCategory category, BigDecimal discount);

}
