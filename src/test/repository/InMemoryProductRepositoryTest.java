package repository;

import domain.ProductCategory;
import domain.ProductEntity;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class InMemoryProductRepositoryTest {

    private InMemoryProductRepository victim;

    @Before
    public void setUp(){
        victim = new InMemoryProductRepository();
    }

    private void setUpSampleMap() {
        victim.index.put(1L, new ProductEntity.ProductBuilder("Lemon", BigDecimal.valueOf(0.5))
                .buildId(1L)
                .buildCategory(ProductCategory.FRUIT)
                .buildDiscount(BigDecimal.valueOf(50))
                .buildDescription("sour lemon")
                .build());
        victim.index.put(2L, new ProductEntity.ProductBuilder("Pelmen", BigDecimal.valueOf(3.8))
                .buildId(2L)
                .buildCategory(ProductCategory.DUMPLINGS)
                .build());
        victim.index.put(3L, new ProductEntity.ProductBuilder("Potato", BigDecimal.valueOf(0.4))
                .buildId(3L)
                .buildCategory(ProductCategory.VEGETABLE)
                .build());
    }

    @Test
    public void should_find_all_when_empty(){
        assertThat(victim.findAll().isEmpty());
    }

    @Test
    public void should_find_all_when_not_empty(){
        setUpSampleMap();
        assertThat(victim.findAll()).containsExactlyInAnyOrder(
                new ProductEntity.ProductBuilder("Lemon", BigDecimal.valueOf(0.5))
                        .buildId(1L)
                        .buildCategory(ProductCategory.FRUIT)
                        .buildDiscount(BigDecimal.valueOf(50))
                        .buildDescription("sour lemon")
                        .build(),
                new ProductEntity.ProductBuilder("Pelmen", BigDecimal.valueOf(3.8))
                        .buildId(2L)
                        .buildCategory(ProductCategory.DUMPLINGS)
                        .build(),
                new ProductEntity.ProductBuilder("Potato", BigDecimal.valueOf(0.4))
                        .buildId(3L)
                        .buildCategory(ProductCategory.VEGETABLE)
                        .build()
        );
    }

    @Test
    public void should_find_by_id(){
        setUpSampleMap();
        ProductEntity expected = new ProductEntity.ProductBuilder("Pelmen", BigDecimal.valueOf(3.8))
                .buildId(2L)
                .buildCategory(ProductCategory.DUMPLINGS)
                .build();
        ProductEntity actual = victim.findByID(2L);
        assertEquals(expected, actual);
    }

    @Test
    public void should_return_null_when_not_found_by_id(){
        setUpSampleMap();
        assertThat(victim.findByID(8L)).isNull();
    }

    @Test
    public void should_remove_by_id(){
        setUpSampleMap();
        victim.delete(1L);
        assertThat(victim.findAll()).doesNotContain(new ProductEntity.ProductBuilder("Lemon", BigDecimal.valueOf(0.5))
                .buildId(1L)
                .buildCategory(ProductCategory.FRUIT)
                .buildDiscount(BigDecimal.valueOf(50))
                .buildDescription("sour lemon")
                .build());
    }

    @Test
    public void should_remove_nothing_when_id_not_found(){
        setUpSampleMap();
        InMemoryProductRepository victimBefore = victim;
        victim.delete(9L);
        assertEquals(victimBefore, victim);
    }

    @Test
    public void should_save_new_products(){
        victim.save(new ProductEntity.ProductBuilder("Beef", BigDecimal.valueOf(6.5))
                .buildCategory(ProductCategory.MEAT)
                .build());
        victim.save(new ProductEntity.ProductBuilder("Lemon", BigDecimal.valueOf(0.5))
                .buildCategory(ProductCategory.FRUIT)
                .buildDiscount(BigDecimal.valueOf(50))
                .buildDescription("sour lemon")
                .build());
        assertThat(victim.index.values()).containsExactlyInAnyOrder(
                new ProductEntity.ProductBuilder("Beef", BigDecimal.valueOf(6.5))
                        .buildId(1L)
                        .buildCategory(ProductCategory.MEAT)
                        .build(),
                new ProductEntity.ProductBuilder("Lemon", BigDecimal.valueOf(0.5))
                        .buildId(2L)
                        .buildCategory(ProductCategory.FRUIT)
                        .buildDiscount(BigDecimal.valueOf(50))
                        .buildDescription("sour lemon")
                        .build()
        );
    }

    @Test
    public void should_overwrite_when_saving_product_when_id_already_present(){
        setUpSampleMap();
        victim.save(new ProductEntity.ProductBuilder("Beef", BigDecimal.valueOf(6.5))
                .buildId(3L)
                .buildCategory(ProductCategory.MEAT)
                .build());
        assertThat(victim.findAll()).contains(
                new ProductEntity.ProductBuilder("Beef", BigDecimal.valueOf(6.5))
                        .buildId(3L)
                        .buildCategory(ProductCategory.MEAT)
                        .build())
                .doesNotContain(new ProductEntity.ProductBuilder("Potato", BigDecimal.valueOf(0.4))
                        .buildId(3L)
                        .buildCategory(ProductCategory.VEGETABLE)
                        .build());
    }

    @Test
    public void should_return_list_of_names() {
        setUpSampleMap();
        assertThat(victim.getNameList()).containsExactlyInAnyOrder("Lemon", "Pelmen", "Potato");
    }

    @Test
    public void should_return_zero_when_category_discount_not_found() {
        setUpSampleMap();
        assertThat(victim.getCategoryDiscount(new ProductEntity.ProductBuilder("Beef", BigDecimal.valueOf(6.5))
                .buildId(3L)
                .buildCategory(ProductCategory.MEAT)
                .build())).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void should_return_category_discount_if_present() {
        setUpSampleMap();
        victim.categoryDiscounts.put(ProductCategory.MEAT, BigDecimal.valueOf(25));
        assertThat(victim.getCategoryDiscount(new ProductEntity.ProductBuilder("Beef", BigDecimal.valueOf(6.5))
                .buildId(3L)
                .buildCategory(ProductCategory.MEAT)
                .build())).isEqualTo(BigDecimal.valueOf(25));
    }

    @Test
    public void should_set_category_discount() {
        victim.setDiscountForCategory(ProductCategory.DUMPLINGS, BigDecimal.valueOf(30));
        assertThat(victim.categoryDiscounts).hasEntrySatisfying(ProductCategory.DUMPLINGS, value -> BigDecimal.valueOf(30));
    }

}