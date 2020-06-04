package service;

import service.discount.DiscountService;
import service.validation.ProductValidator;
import domain.*;
import repository.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultProductService implements ProductService {

    private final Repository<Long, Product> repository;
    private final ProductValidator validator;
    private final DiscountService discountService;

    public DefaultProductService(Repository repository, ProductValidator validator, DiscountService discountService) {
        this.repository = repository;
        this.validator = validator;
        this.discountService = discountService;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product findByID(Long id) {
        return repository.findByID(id);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public void save(Product product){
        validator.validateProduct(product);
        checkForCategoryDiscount(product);
        repository.save(product);
    }

    @Override
    public void setDiscount(Product product, BigDecimal discount) {
        save(new Product(product.getId(), product.getCategory(), product.getName(), product.getPrice(), discount, product.getDescription()));
    }

    @Override
    public void setDiscountForCategory(ProductCategory category, BigDecimal discount) {
        discountService.setDiscountForCategory(category, discount);
        for (Product product : findAll()){
            if (product.getCategory().equals(category)){
                product.setDiscount(discount);
                save(product);
            }
        }
    }

    private void checkForCategoryDiscount(Product product){
        discountService.checkForCategoryDiscount(product);
    }

    public void fillSampleData() {
            save(new Product(ProductCategory.FRUIT, "Lemon", BigDecimal.valueOf(0.5), new BigDecimal("50"), "dasdf"));
            save(new Product(ProductCategory.DUMPLINGS, "Pelmen", BigDecimal.valueOf(3.8)));
            save(new Product(ProductCategory.VEGETABLE, "Potato", BigDecimal.valueOf(0.4)));
            save(new Product(ProductCategory.MEAT, "Beef", BigDecimal.valueOf(6.5)));
            save(new Product(ProductCategory.SOFT_DRINKS, "Fanta", BigDecimal.valueOf(1.0)));
            save(new Product(ProductCategory.FRUIT, "Orange", BigDecimal.valueOf(0.8)));
    }




}
