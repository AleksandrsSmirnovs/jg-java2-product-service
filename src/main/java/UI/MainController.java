package UI;

import domain.*;
import dto.ProductDto;
import service.ProductService;
import service.validation.ProductValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;

public class MainController {

    private ProductService service;

    ObservableList<ProductDto> observableList;

    @FXML private TableView<ProductDto> tableProducts;
    @FXML private TableColumn<ProductDto, Long> columnID;
    @FXML private TableColumn<ProductDto, String> columnName;
    @FXML private TableColumn<ProductDto, ProductCategory> columnCategory;
    @FXML private TableColumn<ProductDto, BigDecimal> columnPrice;
    @FXML private TableColumn<ProductDto, BigDecimal> columnDiscount;
    @FXML private TableColumn<ProductDto, BigDecimal> columnActualPrice;
    @FXML private TableColumn<ProductDto, String> columnDescription;
    @FXML private Button btnSearch;
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnSetDiscounts;
    @FXML private Button btnRemove;
    @FXML private Button btnSample;
    @FXML private Button btnSave;
    @FXML private Button btnClear;
    @FXML private Button btnCancel;
    @FXML private Button btnSaveDiscounts;
    @FXML private HBox boxSettings;
    @FXML private HBox boxSearch;
    @FXML private HBox boxDiscounts;
    @FXML private MenuButton menuButton;
    @FXML private MenuButton menuButton2;
    @FXML private TextField fldName;
    @FXML private TextField fldPrice;
    @FXML private TextField fldDiscount;
    @FXML private TextArea fldDescription;
    @FXML private TextArea fldError;
    @FXML private TextField fldMultidiscount;
    @FXML private TextField fldSearchByID;

    private boolean isNew = false;

    private String invalidInput = "Please fill all mandatory fields.\n Make sure you enter digits into price and discount fields.\n Please use '.', not ','";


    @FXML private void initialize(){
        columnID.setCellValueFactory(new PropertyValueFactory<ProductDto, Long>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<ProductDto, String>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<ProductDto, BigDecimal>("price"));
        columnCategory.setCellValueFactory(new PropertyValueFactory<ProductDto, ProductCategory>("category"));
        columnDiscount.setCellValueFactory(new PropertyValueFactory<ProductDto, BigDecimal>("discount"));
        columnActualPrice.setCellValueFactory(new PropertyValueFactory<ProductDto, BigDecimal>("actualPrice"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<ProductDto, String>("description"));
    }

    public void injectService(ProductService service) {
        this.service = service;
        observableList = FXCollections.observableList(service.findAll());
    }

    public void buttonAction(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {return;}

        Button clickedButton = (Button)source;
        ProductDto selectedProductDto = tableProducts.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()){
            case "btnSearch":
                try {
                    ProductDto productDto = service.findByID(Long.parseLong(fldSearchByID.getText()));
                    setOutputText(productDto.toString());
                    break;
                } catch (NullPointerException e) {
                    setOutputText("Item not found!");
                    break;
                } catch (NumberFormatException e){
                    setOutputText("Please enter ID number!");
                    break;
                }
            case "btnAdd":
                fldError.setVisible(false);
                clearFields();
                isNew = true;
                activateFields(boxSettings);
                break;
            case "btnEdit":
                fillFields(selectedProductDto);
                if (selectedProductDto !=null) {
                    activateFields(boxSettings);
                }
                break;
            case "btnSetDiscounts":
                activateFields(boxDiscounts);
                break;
            case "btnRemove":
                deactivateUnnecessaryFields();
                try {
                    service.delete(selectedProductDto.getId());
                     actionRefresh();
                     break;
                } catch (NullPointerException e){
                    setOutputText("Please select item to remove!");
                    break;
                }
            case "btnSample":
                deactivateUnnecessaryFields();
                service.fillSampleData();
                tableProducts.setItems(FXCollections.observableList(service.findAll()));
                break;
            case "btnClear":
                clearFields();
                break;
            case "btnSave":
                if (isNew) {
                    selectedProductDto = null;}
                try {
                    service.save(createProductFromFields(selectedProductDto));
                    clearFields();
                    deactivateUnnecessaryFields();
                    actionRefresh();
                    isNew = false;
                } catch (ProductValidationException e){
                    setOutputText(e.getMessage());
                    break;
                } catch (NumberFormatException e){
                    setOutputText(invalidInput);
                    break;
                } catch (IllegalArgumentException e){
                    setOutputText(invalidInput);
                }
                break;
            case "btnCancel":
                clearFields();
                deactivateUnnecessaryFields();
                break;
            case "btnSaveDiscounts":
                try {
                    if (getCategoryFromMenu(menuButton2)==null){
                        setOutputText("Please select category");
                        break;
                    }
                    service.setDiscountForCategory(getCategoryFromMenu(menuButton2), new BigDecimal(fldMultidiscount.getText()));
                    actionRefresh();
                    deactivateUnnecessaryFields();
                } catch (NumberFormatException e){
                    setOutputText("Make sure you enter digits into discount field");
                    break;
                } catch (ProductValidationException e){
                    setOutputText(e.getMessage());
                    break;
                }
                break;
        }
    }

    public void clearFields(){
        menuButton.setText("Category:");
        fldName.clear();
        fldPrice.clear();
        fldDiscount.clear();
        fldDescription.clear();
        fldError.clear();
    }

    public void actionRefresh(){
        tableProducts.setItems(FXCollections.observableList(service.findAll()));
        tableProducts.refresh();
    }

    public void menuItemReaction(javafx.event.ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof MenuItem)) {
            return;
        }
        MenuItem choice = (MenuItem) source;
        menuButton.setText(choice.getText());
    }

    public void menuItemReaction2(javafx.event.ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof MenuItem)) {
            return;
        }
        MenuItem choice = (MenuItem) source;
        menuButton2.setText(choice.getText());
    }


    public ProductCategory getCategoryFromMenu(MenuButton menuButton){
        ProductCategory category = null;
        try {
            category = ProductCategory.valueOf(menuButton.getText());
        } catch (IllegalArgumentException e){
            setOutputText("Please select category!");
        }
        return category;
    }

    public void fillFields(ProductDto selectedProductDto){
        if (selectedProductDto !=null) {
            menuButton.setText(selectedProductDto.getCategory().toString());
            fldName.setText(selectedProductDto.getName());
            fldPrice.setText(selectedProductDto.getPrice().toString());
            fldDiscount.setText(selectedProductDto.getDiscount().toString());
            fldDescription.setText(selectedProductDto.getDescription());
            boxSettings.setVisible(true);
        } else {
            setOutputText("Please select product!");
        }
    }

    public void setOutputText(String message){
        fldError.setText(message);
        fldError.setVisible(true);
    }


    public ProductDto createProductFromFields(ProductDto selectedProductDto){
        Long id;
        if (selectedProductDto != null){
           id = selectedProductDto.getId();
        } else {
            id = null;
        }
        ProductCategory category = getCategoryFromMenu(menuButton);
        String name = fldName.getText();
        BigDecimal price = new BigDecimal(fldPrice.getText());
        BigDecimal discount;
        try {
            discount = new BigDecimal(fldDiscount.getText());
        } catch (NumberFormatException e){
            discount = BigDecimal.ZERO;
        }
        String description = fldDescription.getText();

        ProductDto productDto = new ProductDto.Builder()
                .buildId(id)
                .buildCategory(category)
                .buildName(name)
                .buildPrice(price)
                .buildDiscount(discount)
                .buildDescription(description)
                .build();
        return productDto;
    }

    public void deactivateUnnecessaryFields(){
        boxSettings.setOpacity(0.3);
        boxDiscounts.setOpacity(0.3);
        boxSettings.setDisable(true);
        boxDiscounts.setDisable(true);
        fldError.setVisible(false);
    }

    public void activateFields(HBox box){
        box.setOpacity(1);
        box.setDisable(false);
    }
}
