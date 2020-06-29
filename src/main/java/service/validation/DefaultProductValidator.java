package service.validation;

import domain.ProductEntity;
import dto.ProductDto;
import service.validation.validationRules.*;

import java.util.List;

public class DefaultProductValidator implements ProductValidator {

    private final List<ProductValidationRule> listOfRules;


    public DefaultProductValidator(List<ProductValidationRule> listOfRules) {
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
