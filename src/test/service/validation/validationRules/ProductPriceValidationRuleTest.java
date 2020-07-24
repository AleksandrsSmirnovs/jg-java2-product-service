package service.validation.validationRules;

import dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import service.validation.ProductValidationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class ProductPriceValidationRuleTest {

    private ProductPriceValidationRule victim = new ProductPriceValidationRule();

    @Test
    public void should_validate_successfully_when_price_is_positive() {
        ProductDto dto = new ProductDto.Builder().buildPrice(BigDecimal.valueOf(35)).build();
        assertThatCode(() -> victim.validate(dto)).doesNotThrowAnyException();
    }

    @Test
    public void should_throw_exception_when_price_is_negative() {
        ProductDto dto = new ProductDto.Builder().buildPrice(BigDecimal.valueOf(-10)).build();
        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product price must be a positive number.");
    }

    @Test
    public void should_throw_exception_when_price_is_null() {
        ProductDto dto = new ProductDto.Builder().buildPrice(null).build();
        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product price must be not null.");
    }

}