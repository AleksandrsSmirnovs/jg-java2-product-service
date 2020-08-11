package productService.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import productService.dto.ProductDto;
import org.springframework.stereotype.Service;
import productService.service.validation.validationRules.*;

import java.util.List;

@Service
public class DefaultProductValidator implements ProductValidator {

    private final List<ProductValidationRule> listOfRules;


    public DefaultProductValidator(@Autowired @Qualifier("listOfRules") List<ProductValidationRule> listOfRules) {
        this.listOfRules = listOfRules;
    }

    @Override
    public void validateProduct(ProductDto dto) {
        if (dto == null) {
            throw new ProductValidationException("Product must be not null");
        }
        StringBuilder messageList = new StringBuilder();
        listOfRules.forEach(rule -> {
            try {
                rule.validate(dto);
            } catch (ProductValidationException e) {
                messageList.append(e.getMessage()).append("\n");
            }
        });

        if (!(messageList.toString().isEmpty())) {
            throw new ProductValidationException(messageList.toString());
        }
    }
}
