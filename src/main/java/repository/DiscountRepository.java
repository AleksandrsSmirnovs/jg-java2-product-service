package repository;

import java.math.BigDecimal;

public interface DiscountRepository<CAT, DI, T> {

    void setDiscountForCategory(CAT category, DI discount);

    BigDecimal getCategoryDiscount(T entity);
}
