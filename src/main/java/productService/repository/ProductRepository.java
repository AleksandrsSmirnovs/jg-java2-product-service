package productService.repository;

import productService.domain.ProductCategory;
import productService.domain.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository {

    List<ProductEntity> findAll();

    ProductEntity findByID(Long id);

    void save(ProductEntity entity);

    void delete(Long id);

    List<String> getNameList();
}
