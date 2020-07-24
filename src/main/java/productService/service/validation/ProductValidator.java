package productService.service.validation;

import productService.dto.ProductDto;

public interface ProductValidator {

    void validateProduct(ProductDto productDto);

}
