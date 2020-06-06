package service.validation;

import domain.ProductEntity;
import dto.ProductDto;

public interface ProductValidator {

    void validateProduct(ProductDto productDto);

}
