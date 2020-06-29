package service.validation;

import dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import service.validation.validationRules.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProductValidatorTest {

    @Mock
    ProductDescriptionValidationRule productDescriptionValidationRule;
    @Mock
    ProductDiscountValidationRule productDiscountValidationRule;
    @Mock
    ProductNameValidationRule productNameValidationRule;
    @Mock
    ProductPriceValidationRule productPriceValidationRule;
    @Mock
    ProductUniqueNameValidationRule productUniqueNameValidationRule;

    DefaultProductValidator victim;

    @Before
    public void setUp() {
        victim = new DefaultProductValidator(List.of(
                productDescriptionValidationRule,
                productDiscountValidationRule,
                productNameValidationRule,
                productUniqueNameValidationRule,
                productPriceValidationRule));
    }

    @Test
    public void should_throw_exception_when_dto_is_null() {
        ProductDto dto = null;
        assertThatThrownBy(() -> victim.validateProduct(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product must be not null");
    }

    @Test
    public void should_throw_exception_with_all_messages() {
        ProductDto dto = new ProductDto.Builder().build();
        doThrow(new ProductValidationException("Discount_exception_message"))
                .when(productDiscountValidationRule).validate(any());
        doThrow(new ProductValidationException("Description_exception_message"))
                .when(productDescriptionValidationRule).validate(any());
        doThrow(new ProductValidationException("Name_exception_message"))
                .when(productNameValidationRule).validate(any());
        doThrow(new ProductValidationException("Price_exception_message"))
                .when(productPriceValidationRule).validate(any());
        doThrow(new ProductValidationException("Unique_name_exception_message"))
                .when(productUniqueNameValidationRule).validate(any());

        assertThatThrownBy(() -> victim.validateProduct(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessageContaining("Discount_exception_message")
                .hasMessageContaining("Description_exception_message")
                .hasMessageContaining("Name_exception_message")
                .hasMessageContaining("Price_exception_message")
                .hasMessageContaining("Unique_name_exception_message");
        verify(productPriceValidationRule).validate(dto);
        verify(productDiscountValidationRule).validate(dto);
        verify(productDescriptionValidationRule).validate(dto);
        verify(productUniqueNameValidationRule).validate(dto);
        verify(productNameValidationRule).validate(dto);
    }

    @Test
    public void should_validate_successfully_when_all_validation_rules_passed() {
        ProductDto dto = new ProductDto.Builder().build();
        doNothing().when(productDiscountValidationRule).validate(any());
        doNothing().when(productDescriptionValidationRule).validate(any());
        doNothing().when(productNameValidationRule).validate(any());
        doNothing().when(productPriceValidationRule).validate(any());
        doNothing().when(productUniqueNameValidationRule).validate(any());

        assertThatCode(() -> victim.validateProduct(dto)).doesNotThrowAnyException();

        verify(productPriceValidationRule).validate(dto);
        verify(productDiscountValidationRule).validate(dto);
        verify(productDescriptionValidationRule).validate(dto);
        verify(productUniqueNameValidationRule).validate(dto);
        verify(productNameValidationRule).validate(dto);
    }

}