package productService.UI;

import productService.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import productService.service.ProductService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainStage.fxml"));
        Parent root = fxmlLoader.load();
        MainController controller = fxmlLoader.getController();

        controller.injectService(applicationContext.getBean(ProductService.class));
        primaryStage.setTitle("Product service application");
        primaryStage.setScene(new Scene(root, 910, 410));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
