package productService.service.validation.validationRules;

import productService.dto.ProductDto;
import org.junit.Test;
import productService.service.validation.ProductValidationException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductDescriptionValidationRuleTest {

    ProductDescriptionValidationRule victim = new ProductDescriptionValidationRule();

    @Test
    public void should_validate_successfully_when_description_length_is_not_greater_than_60() {
        ProductDto dto = new ProductDto.Builder().buildDescription("Correct Description").build();
        assertThatCode(() -> victim.validate(dto)).doesNotThrowAnyException();
    }

    @Test
    public void should_validate_successfully_when_name_is_null() {
        ProductDto dto = new ProductDto.Builder().buildName(null).build();
        assertThatCode(() -> victim.validate(dto)).doesNotThrowAnyException();
    }

    @Test
    public void should_throw_exception_when_description_is_too_long() {
        ProductDto dto = new ProductDto.Builder().buildDescription("Let me tell you about this product. A long time ago, in a galaxy far, far away...").build();
        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Description is too long.");
    }


}