package productService.service.validation;

public class ProductValidationException extends RuntimeException {

    public ProductValidationException(String message) {
        super(message);
    }
}
