package repository;

public interface DiscountRepository<CAT, DI, T> {

    void setDiscountForCategory(CAT category, DI discount);

    void checkForCategoryDiscount(T entity);
}
