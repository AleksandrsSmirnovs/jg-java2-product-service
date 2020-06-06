package service.validation;

import domain.ProductEntity;
import dto.ProductDto;
import service.validation.validationRules.*;

import java.util.List;

public class DefaultProductValidator implements ProductValidator {

    private final List<ProductValidationRule> listOfRules;
    private StringBuilder messageList = new StringBuilder();

    public DefaultProductValidator(List<ProductValidationRule> listOfRules){
        this.listOfRules = listOfRules;
    }

    @Override
    public void validateProduct(ProductDto productDto){
        listOfRules.forEach(rule -> {
            try {
                rule.validate(productDto);
            } catch (ProductValidationException e) {
                messageList.append(e.getMessage()).append("\n");
            }
        });

        if (!(messageList.toString().isEmpty())) {
            throw new ProductValidationException(messageList.toString());
        }
    }
}
