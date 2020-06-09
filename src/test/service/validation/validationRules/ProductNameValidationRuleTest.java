package service.validation.validationRules;

import dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import service.validation.ProductValidationException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class ProductNameValidationRuleTest {

    ProductNameValidationRule victim = new ProductNameValidationRule();

    @Test
    public void should_validate_successfully_when_name_length_is_between_3_and_32() {
        ProductDto dto = new ProductDto.Builder().buildName("Correct Name").build();
        assertThatCode(() -> victim.validate(dto)).doesNotThrowAnyException();
    }

    @Test
    public void should_throw_exception_when_name_is_null() {
        ProductDto dto = new ProductDto.Builder().buildName(null).build();
        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product name must be not null.");
    }

    @Test
    public void should_throw_exception_when_name_is_too_short() {
        ProductDto dto = new ProductDto.Builder().buildName("Pn").build();
        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product name is too short.");
    }

    @Test
    public void should_throw_exception_when_name_is_too_long() {
        ProductDto dto = new ProductDto.Builder().buildName("Very long and funny product name that I like very much").build();
        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product name is too long.");
    }

}