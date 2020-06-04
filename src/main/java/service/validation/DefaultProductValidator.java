package service.validation;

import service.validation.validationRules.*;
import domain.Product;

import java.util.List;

public class DefaultProductValidator implements ProductValidator {

    private final List<ProductValidationRule> listOfRules;
    private StringBuilder messageList = new StringBuilder();

    public DefaultProductValidator(){
        listOfRules = List.of(
                new ProductCategoryValidationRule(),
                new ProductNameValidationRule(),
                new ProductPriceValidationRule(),
                new ProductDescriptionValidationRule()
        );
    }

    @Override
    public void validateProduct(Product product){
        listOfRules.forEach(rule -> {
            try {
                rule.validate(product);
            } catch (ProductValidationException e) {
                messageList.append(e.getMessage()).append("\n");
            }
        });

        if (!(messageList.toString().isEmpty())) {
            throw new ProductValidationException(messageList.toString());
        }
    }
}
