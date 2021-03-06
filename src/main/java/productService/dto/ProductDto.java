package productService.dto;

import productService.domain.ProductCategory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class ProductDto {

    private final Long id;
    private final ProductCategory category;
    private final String name;
    private final BigDecimal price;
    private BigDecimal discount;
    private final String description;

    public static class Builder {
        private Long id;
        private ProductCategory category;
        private String name;
        private BigDecimal price;
        private BigDecimal discount;
        private String description;

        public Builder buildId(Long id) {
            this.id = id;
            return this;
        }

        public Builder buildName(String name) {
            this.name = name;
            return this;
        }

        public Builder buildPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder buildCategory(ProductCategory category) {
            this.category = category;
            return this;
        }

        public Builder buildDiscount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public Builder buildDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this);
        }
    }

    private ProductDto(Builder builder) {
        id = builder.id;
        name = builder.name;
        price = builder.price;
        category = builder.category;
        discount = builder.discount;
        description = builder.description;
    }

    public BigDecimal getActualPrice() {
        if (price == null) return BigDecimal.ZERO;
        return discount == null ? price : price.subtract(price.multiply(discount).multiply(BigDecimal.valueOf(0.01))).setScale(2, RoundingMode.HALF_EVEN);
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
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
                category == that.category &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, price, discount, description);
    }

    @Override
    public String toString() {
        return "Product id :" + id +
                "\nCategory: " + category +
                "\nName: " + name +
                "\nRegular price: " + price.setScale(2, RoundingMode.HALF_UP) +
                "\nDiscount: " + discount.setScale(2, RoundingMode.HALF_UP) + "%" +
                "\nActual price: " + getActualPrice().setScale(2, RoundingMode.HALF_UP) +
                "\nDescription: " + description;
    }
}
