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

    public static class ProductBuilder{

        private Long id;
        private final String name;
        private final BigDecimal price;
        private ProductCategory category;
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
        discount = builder.discount == null ? BigDecimal.ZERO : builder.discount;
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

    //    @Override
//    public String toString() {
//        return "Product information:\n" +
//                "Id : " + id +
//                "\nName : '" + name + "'\n" +
//                "Category : " + category +
//                "\nRegular price : " + price.setScale(2) +
//                "\nDiscount : " + discount.setScale(0) + "%" +
//                "\nActual price : " + getActualPrice().setScale(2) + "\n";
//    }




    //    public ProductEntity(Long id, ProductCategory category, String name, BigDecimal price) {
//        this(category, name, price);
//        this.id = id;
//    }
//
//    public ProductEntity(Long id, ProductCategory category, String name, BigDecimal price, BigDecimal discount, String description) {
//        this(category, name, price);
//        this.id = id;
//        this.discount = discount;
//        this.description = description;
//    }
//
//    public ProductEntity(ProductCategory category, String name, BigDecimal price, BigDecimal discount, String description) {
//        this(category, name, price);
//        this.discount = discount;
//        this.description = description;
//    }
//
//    public ProductEntity(ProductCategory category, String name, BigDecimal price) {
//        this.category = category;
//        this.name = name;
//        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public ProductCategory getCategory() {
//        return category;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public BigDecimal getDiscount() {
//        return discount;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDiscount(BigDecimal discount) {
//        if ((discount.compareTo(BigDecimal.valueOf(100))>0)||(discount.compareTo(BigDecimal.valueOf(0))<0)){
//            throw new ProductValidationException("Discount must be between 0 and 100%");
//        }
//        this.discount = discount;
//    }
//
//    public BigDecimal getActualPrice() {
//        return (price.subtract(price.multiply((discount).multiply(BigDecimal.valueOf(0.01))))).setScale(2, RoundingMode.HALF_EVEN);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProductEntity productEntity = (ProductEntity) o;
//        return Objects.equals(id, productEntity.id) &&
//                category == productEntity.category &&
//                Objects.equals(name, productEntity.name) &&
//                Objects.equals(price, productEntity.price) &&
//                Objects.equals(discount, productEntity.discount);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, category, name, price, discount);
//    }
//
//    @Override
//    public String toString() {
//        return "Product information:\n" +
//                "Id : " + id +
//                "\nName : '" + name + "'\n" +
//                "Category : " + category +
//                "\nRegular price : " + price.setScale(2) +
//                "\nDiscount : " + discount.setScale(0) + "%" +
//                "\nActual price : " + getActualPrice().setScale(2) + "\n";
//    }


}
