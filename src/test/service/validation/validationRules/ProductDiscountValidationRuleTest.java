package service.validation.validationRules;

import dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import service.validation.ProductValidationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class ProductDiscountValidationRuleTest {

    ProductDiscountValidationRule victim = new ProductDiscountValidationRule();

    @Test
    public void should_throw_exception_when_discount_is_negative() {
        ProductDto dto = new ProductDto.Builder().buildDiscount(BigDecimal.valueOf(-10)).build();

        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product discount must be a positive number.");
    }

    @Test
    public void should_throw_exception_when_discount_is_greater_than_100() {
        ProductDto dto = new ProductDto.Builder().buildDiscount(BigDecimal.valueOf(120)).build();

        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product discount can't be more than 100%.");
    }

    @Test
    public void should_not_throw_exception_when_discount_is_null_and_set_it_to_zero() {
        ProductDto dto = new ProductDto.Builder().buildDiscount(null).build();

        assertThatCode(() -> victim.validate(dto)).doesNotThrowAnyException();
        assertThat(dto.getDiscount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void should_set_discount_to_zero_if_price_is_less_than_20() {
        ProductDto dto = new ProductDto.Builder()
                .buildPrice(BigDecimal.valueOf(15))
                .buildDiscount(BigDecimal.valueOf(10))
                .build();

        victim.validate(dto);
        assertThat(dto.getDiscount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void should_validate_successfully_if_discount_and_price_match_rules() {
        ProductDto dto = new ProductDto.Builder()
                .buildPrice(BigDecimal.valueOf(100))
                .buildDiscount(BigDecimal.valueOf(10))
                .build();

        assertThatCode(() -> victim.validate(dto)).doesNotThrowAnyException();
        assertThat(dto.getDiscount()).isEqualTo(BigDecimal.valueOf(10).setScale(2));
    }

}