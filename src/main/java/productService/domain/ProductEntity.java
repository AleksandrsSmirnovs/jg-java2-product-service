package productService.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductEntity {

    private Long id;
    private String category;
    private String name;
    private BigDecimal price;
    private BigDecimal discount;
    private String description;

    public ProductEntity() {

    }

    public static class ProductBuilder {

        private Long id;
        private final String name;
        private final BigDecimal price;
        private String category;
        private BigDecimal discount;
        private String description;

        public ProductBuilder(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        public ProductBuilder buildId(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder buildCategory(String category) {
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
        category = builder.category;
        discount = builder.discount;
        description = builder.description;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
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


    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity entity = (ProductEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(category, entity.category) &&
                Objects.equals(name, entity.name) &&
                Objects.equals(price, entity.price) &&
                Objects.equals(discount, entity.discount) &&
                Objects.equals(description, entity.description);
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
