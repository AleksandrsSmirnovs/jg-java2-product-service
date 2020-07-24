package productService.service.validation.validationRules;

import productService.dto.ProductDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import productService.repository.InMemoryProductRepository;
import productService.service.validation.ProductValidationException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductUniqueNameValidationRuleTest {

    @Mock
    private InMemoryProductRepository repository;

    @InjectMocks
    private ProductUniqueNameValidationRule victim;

    @Test
    public void should_validate_successfully_when_name_is_unique() {
        ProductDto dto = new ProductDto.Builder().buildName("UNIQUE_NAME").build();
        when(repository.getNameList()).thenReturn(List.of("NAME_1", "NAME_2", "NAME_3"));
        assertThatCode(() -> victim.validate(dto)).doesNotThrowAnyException();
        verify(repository).getNameList();
    }

    @Test
    public void should_throw_exception_when_name_is_not_unique() {
        ProductDto dto = new ProductDto.Builder().buildName("UNIQUE_NAME").build();
        when(repository.getNameList()).thenReturn(List.of("NAME_1", "NAME_2", "NAME_3", "UNIQUE_NAME"));
        assertThatThrownBy(() -> victim.validate(dto))
                .isInstanceOf(ProductValidationException.class)
                .hasMessage("Product name must be unique.");
        verify(repository).getNameList();
    }
}