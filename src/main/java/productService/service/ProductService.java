package productService.service;

import productService.domain.*;
import productService.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductDto> findAll();

    ProductDto findByID(Long id);

    void save(ProductDto productDto);

    void delete(Long id);

    void setDiscountForCategory(ProductCategory category, BigDecimal discount);

    void fillSampleData();
}
