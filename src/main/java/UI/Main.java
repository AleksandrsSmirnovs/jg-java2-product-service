package UI;

import repository.InMemoryProductRepository;
import service.DefaultProductService;
import service.discount.ProductDiscountService;
import service.validation.DefaultProductValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainStage.fxml"));
        Parent root = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.injectService(new DefaultProductService(new InMemoryProductRepository(new HashMap<>()), new DefaultProductValidator(), new ProductDiscountService()));
        primaryStage.setTitle("Product service Application");
        primaryStage.setScene(new Scene(root, 910, 410));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
