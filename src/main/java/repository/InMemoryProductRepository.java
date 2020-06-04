package repository;

import domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements Repository<Long, Product> {

    private static Long SEQ = 0L;

    Map<Long, Product> index;

    public InMemoryProductRepository(Map<Long, Product> index) {
        this.index = index;
    }


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


}
