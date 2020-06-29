package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductEntity {

    private Long id;
    private final ProductCategory category;
    private final String name;
    private final BigDecimal price;
    private BigDecimal discount;
    private final String description;

    public static class ProductBuilder {

        private Long id;
        private final String name;
        private final BigDecimal price;
        private ProductCategory category;
        private BigDecimal discount;
        private String description;

        public ProductBuilder(String name, BigDecimal price) {
            this.name = name;
            this.price = price.setScale(2);
        }

        public ProductBuilder buildId(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder buildCategory(ProductCategory category) {
            this.category = category;
            return this;
        }

        public ProductBuilder buildDiscount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public ProductBuilder buildDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductEntity build() {
            return new ProductEntity(this);
        }
    }

    private ProductEntity(ProductBuilder builder) {
        id = builder.id;
        name = builder.name;
        price = builder.price;
        category = builder.category == null ? ProductCategory.UNDEFINED : builder.category;
        discount = builder.discount == null ? BigDecimal.ZERO.setScale(2) : builder.discount.setScale(2);
        description = builder.description == null ? "" : builder.description;
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
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id) &&
                category == that.category &&
                name.equals(that.name) &&
                price.equals(that.price) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, price, discount, description);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                '}';
    }
}
