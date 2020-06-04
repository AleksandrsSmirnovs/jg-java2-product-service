package service.discount;

import domain.Product;
import domain.ProductCategory;
import repository.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductDiscountService implements DiscountService {

    private Map<ProductCategory, BigDecimal> categoryDiscounts = new HashMap<>();

    @Override
    public void setDiscountForProduct(Product product) {


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
}
