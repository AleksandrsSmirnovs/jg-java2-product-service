package UI;

import converters.ProductDtoEntityConverter;
import repository.InMemoryProductRepository;
import repository.Repository;
import service.DefaultProductService;
import service.ProductService;
import service.discount.DiscountService;
import service.discount.ProductDiscountService;
import service.validation.DefaultProductValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.validation.ProductValidator;
import service.validation.validationRules.*;

import java.math.BigDecimal;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainStage.fxml"));
        Parent root = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        InMemoryProductRepository repository = new InMemoryProductRepository();
        List<ProductValidationRule> listOfRules = List.of(
                new ProductNameValidationRule(),
                new ProductUniqueNameValidationRule(repository),
                new ProductPriceValidationRule(),
                new ProductDiscountValidationRule(),
                new ProductDescriptionValidationRule()
        );
        ProductValidator validator = new DefaultProductValidator(listOfRules);
        ProductDtoEntityConverter converter = new ProductDtoEntityConverter();
        ProductDiscountValidationRule discountValidationRule = new ProductDiscountValidationRule();
        DiscountService discountService = new ProductDiscountService(repository, converter, discountValidationRule);
        ProductService productService = new DefaultProductService(repository, validator, discountService, converter);
        controller.injectService(productService);
        primaryStage.setTitle("Product service Application");
        primaryStage.setScene(new Scene(root, 910, 410));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
