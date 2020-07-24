package productService.converters;

import productService.domain.ProductCategory;
import productService.domain.ProductEntity;
import productService.dto.ProductDto;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class ProductDtoEntityConverterTest {

    ProductDtoEntityConverter victim = new ProductDtoEntityConverter();

    @Test
    public void should_convert_entity_to_dto() {
        ProductEntity entity = new ProductEntity.ProductBuilder("Product_name", BigDecimal.valueOf(25.5))
                .buildId(2L)
                .buildDescription("")
                .buildCategory(ProductCategory.FRUIT)
                .buildDiscount(BigDecimal.valueOf(32))
                .build();

        assertThat(victim.toDto(entity)).isEqualTo(
                new ProductDto.Builder()
                        .buildId(2L)
                        .buildName("Product_name")
                        .buildPrice(BigDecimal.valueOf(25.5))
                        .buildCategory(ProductCategory.FRUIT)
                        .buildDiscount(BigDecimal.valueOf(32))
                        .buildDescription("")
                        .build()
        );
    }

    @Test
    public void should_convert_dto_to_entity() {
        ProductDto dto = new ProductDto.Builder()
                .buildId(2L)
                .buildName("Product_name")
                .buildPrice(BigDecimal.valueOf(25.5))
                .buildCategory(ProductCategory.FRUIT)
                .buildDiscount(BigDecimal.valueOf(32))
                .buildDescription("Description")
                .build();

        assertThat(victim.toEntity(dto)).isEqualTo(
                new ProductEntity.ProductBuilder("Product_name", BigDecimal.valueOf(25.5))
                        .buildId(2L)
                        .buildDescription("Description")
                        .buildCategory(ProductCategory.FRUIT)
                        .buildDiscount(BigDecimal.valueOf(32))
                        .build());
    }


    @Test
    public void should_convert_dto_to_entity_when_only_name_and_price_present() {
        ProductDto dto = new ProductDto.Builder()
                .buildName("Product_name")
                .buildPrice(BigDecimal.valueOf(25.5))
                .build();

        assertThat(victim.toEntity(dto)).isEqualTo(
                new ProductEntity.ProductBuilder("Product_name", BigDecimal.valueOf(25.5))
                        .build());
    }

    @Test
    public void should_throw_exception_when_dto_is_null() {
        ProductDto dto = null;
        assertThatThrownBy(() -> victim.toEntity(dto))
                .isInstanceOf(ProductConvertionException.class).hasMessage("Can't convert null.");
    }

    @Test
    public void should_throw_exception_when_entity_is_null() {
        ProductEntity entity = null;
        assertThatThrownBy(() -> victim.toDto(entity))
                .isInstanceOf(ProductConvertionException.class).hasMessage("Item not found.");
    }

    @Test
    public void should_throw_exception_when_dto_name_is_null() {
        ProductDto dto = new ProductDto.Builder()
                .buildPrice(BigDecimal.valueOf(25.5))
                .build();
        assertThatThrownBy(() -> victim.toEntity(dto))
                .isInstanceOf(ProductConvertionException.class).hasMessage("Name must be not null.");
    }

    @Test
    public void should_throw_exception_when_dto_price_is_null() {
        ProductDto dto = new ProductDto.Builder()
                .buildName("ProductName")
                .build();
        assertThatThrownBy(() -> victim.toEntity(dto))
                .isInstanceOf(ProductConvertionException.class).hasMessage("Price must be not null.");
    }

}