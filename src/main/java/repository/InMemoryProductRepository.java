package repository;

import domain.Product;
import domain.ProductCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements Repository<Long, Product>, DiscountRepository<ProductCategory, BigDecimal, Product> {

    private static Long SEQ = 0L;

    Map<Long, Product> index = new HashMap<>();
    private Map<ProductCategory, BigDecimal> categoryDiscounts = new HashMap<>();

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(index.values());
    }

    @Override
    public Product findByID(Long id) {
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
    public void save(Product entity) {
        long id;
        if (entity.getId()!=null){
            id = entity.getId();
        } else {
            id = ++SEQ;
        }
        Product product = new Product(id, entity.getCategory(), entity.getName(), entity.getPrice(), entity.getDiscount(), entity.getDescription());
        index.put(product.getId(), product);
    }

    @Override
    public void setDiscountForCategory(ProductCategory category, BigDecimal discount) {
        categoryDiscounts.put(category, discount);
    }

    @Override
    public void checkForCategoryDiscount(Product product){
        if (categoryDiscounts.containsKey(product.getCategory())) {
            product.setDiscount(categoryDiscounts.get(product.getCategory()));
        }
    }

    public List<String> getNameList() {
        List<String> nameList = new ArrayList<>();
        for (Product product : index.values()) {
            nameList.add(product.getName());
        }
        return nameList;
    }

}
