package domain;

import service.validation.ProductValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Product {

    private Long id;
    private final ProductCategory category;
    private final String name;
    private final BigDecimal price;
    private BigDecimal discount = BigDecimal.ZERO;
    private String description = "";

    public Product(Long id, ProductCategory category, String name, BigDecimal price) {
        this(category, name, price);
        this.id = id;
    }

    public Product(Long id, ProductCategory category, String name, BigDecimal price, BigDecimal discount, String description) {
        this(category, name, price);
        this.id = id;
        this.discount = discount;
        this.description = description;
    }

    public Product(ProductCategory category, String name, BigDecimal price, BigDecimal discount, String description) {
        this(category, name, price);
        this.discount = discount;
        this.description = description;
    }

    public Product(ProductCategory category, String name, BigDecimal price) {
        this.category = category;
        this.name = name;
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Long getId() {
        return id;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDiscount(BigDecimal discount) {
        if ((discount.compareTo(BigDecimal.valueOf(100))>0)||(discount.compareTo(BigDecimal.valueOf(0))<0)){
            throw new ProductValidationException("Discount must be between 0 and 100%");
        }
        this.discount = discount;
    }

    public BigDecimal getActualPrice() {
        return (price.subtract(price.multiply((discount).multiply(BigDecimal.valueOf(0.01))))).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                category == product.category &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(discount, product.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, price, discount);
    }

    @Override
    public String toString() {
        return "Product information:\n" +
                "Id : " + id +
                "\nName : '" + name + "'\n" +
                "Category : " + category +
                "\nRegular price : " + price.setScale(2) +
                "\nDiscount : " + discount.setScale(0) + "%" +
                "\nActual price : " + getActualPrice().setScale(2) + "\n";
    }


}
