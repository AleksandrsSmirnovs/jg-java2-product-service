package service;

import domain.*;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findByID(Long id);

    void save(Product product);

    void delete(Long id);

    void setDiscount(Product product, BigDecimal discount);

    void setDiscountForCategory(ProductCategory category, BigDecimal discount);

    void fillSampleData();
}
