package repository;

import domain.ProductEntity;
import domain.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements Repository<Long, ProductEntity>, DiscountRepository<ProductCategory, BigDecimal, ProductEntity> {

    private static Long SEQ = 0L;

    Map<Long, ProductEntity> index = new HashMap<>();
    Map<ProductCategory, BigDecimal> categoryDiscounts = new HashMap<>();

    @Override
    public List<ProductEntity> findAll() {
        return new ArrayList<>(index.values());
    }

    @Override
    public ProductEntity findByID(Long id) {
        if (index.containsKey(id)) {
            return index.get(id);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        index.remove(id);
    }

    @Override
    public void save(ProductEntity entity) {
        long id = entity.getId() == null ? ++SEQ : entity.getId();

        ProductEntity newEntity = new ProductEntity.ProductBuilder(entity.getName(), entity.getPrice())
                .buildId(id)
                .buildCategory(entity.getCategory())
                .buildDiscount(entity.getDiscount())
                .buildDescription(entity.getDescription())
                .build();
        index.put(newEntity.getId(), newEntity);
    }

    @Override
    public void setDiscountForCategory(ProductCategory category, BigDecimal discount) {
        categoryDiscounts.put(category, discount);
    }

    @Override
    public BigDecimal getCategoryDiscount(ProductEntity entity){
        return categoryDiscounts.get(entity.getCategory()) == null ? BigDecimal.ZERO : categoryDiscounts.get(entity.getCategory());
    }

    public List<String> getNameList() {
        return index.values().stream().map(ProductEntity::getName).collect(Collectors.toList());
    }
}
