package productService.UI;

import productService.converters.ProductConvertionException;
import productService.domain.*;
import productService.dto.ProductDto;
import org.springframework.stereotype.Component;
import productService.service.ProductService;
import productService.service.validation.ProductValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;

@Component
public class MainController {

    private ProductService service;

    ObservableList<ProductDto> observableList;

    @FXML
    private TableView<ProductDto> tableProducts;
    @FXML
    private TableColumn<ProductDto, Long> columnID;
    @FXML
    private TableColumn<ProductDto, String> columnName;
    @FXML
    private TableColumn<ProductDto, ProductCategory> columnCategory;
    @FXML
    private TableColumn<ProductDto, BigDecimal> columnPrice;
    @FXML
    private TableColumn<ProductDto, BigDecimal> columnDiscount;
    @FXML
    private TableColumn<ProductDto, BigDecimal> columnActualPrice;
    @FXML
    private TableColumn<ProductDto, String> columnDescription;
    @FXML
    private HBox boxSettings;
    @FXML
    private HBox boxDiscounts;
    @FXML
    private MenuButton menuButton;
    @FXML
    private MenuButton menuButton2;
    @FXML
    private TextField fldName;
    @FXML
    private TextField fldPrice;
    @FXML
    private TextField fldDiscount;
    @FXML
    private TextArea fldDescription;
    @FXML
    private TextArea fldError;
    @FXML
    private TextField fldMultidiscount;
    @FXML
    private TextField fldSearchByID;

    private boolean isNew = false;

    private String invalidInput = "Please fill all mandatory fields.\n Make sure you enter digits into price and discount fields.\n Please use '.', not ','";


    @FXML
    private void initialize() {
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
        actionRefresh();
    }

    public void buttonAction(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;
        ProductDto selected = tableProducts.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()) {
            case "btnSearch":
                searchButtonAction();
                break;
            case "btnAdd":
                addButtonAction();
                break;
            case "btnEdit":
                editButtonAction(selected);
                break;
            case "btnSetDiscounts":
                setDiscountsButtonAction();
                break;
            case "btnRemove":
                removeButtonAction(selected);
                break;
            case "btnSample":
                sampleButtonAction();
                break;
            case "btnClear":
                clearButtonAction();
                break;
            case "btnSave":
                saveButtonAction(selected);
                break;
            case "btnCancel":
                cancelButtonAction();
                break;
            case "btnSaveDiscounts":
                saveDiscountsButtonAction();
                break;
        }
    }

    private void searchButtonAction() {
        try {
            ProductDto dto = service.findByID(Long.parseLong(fldSearchByID.getText()));
            setOutputText(dto.toString());
        } catch (NumberFormatException e) {
            setOutputText("Please enter ID number!");
        } catch (ProductConvertionException e) {
            setOutputText(e.getMessage());
        }
    }

    private void addButtonAction() {
        fldError.setVisible(false);
        clearFields();
        isNew = true;
        activateFields(boxSettings);
    }

    private void setDiscountsButtonAction() {
        activateFields(boxDiscounts);
    }

    private void sampleButtonAction() {
        deactivateUnnecessaryFields();
        service.fillSampleData();
        tableProducts.setItems(FXCollections.observableList(service.findAll()));
    }

    private void clearButtonAction() {
        clearFields();
    }

    private void cancelButtonAction() {
        clearFields();
        deactivateUnnecessaryFields();
    }

    private void editButtonAction(ProductDto selected) {
        fillFields(selected);
        if (selected != null) {
            activateFields(boxSettings);
        }
    }

    private void removeButtonAction(ProductDto selected) {
        deactivateUnnecessaryFields();
        try {
            service.delete(selected.getId());
            actionRefresh();
        } catch (NullPointerException e) {
            setOutputText("Please select item to remove!");
        }
    }

    private void saveButtonAction(ProductDto selected) {
        if (isNew) {
            selected = null;
        } else {
            service.delete(selected.getId());
        }
        try {
            service.save(createProductFromFields(selected));
            clearFields();
            deactivateUnnecessaryFields();
            actionRefresh();
            isNew = false;
        } catch (ProductValidationException e) {
            setOutputText(e.getMessage());
        } catch (IllegalArgumentException e) {
            setOutputText(invalidInput);
        }
    }

    private void saveDiscountsButtonAction() {
        try {
            if (getCategoryFromMenu(menuButton2) == null) {
                setOutputText("Please select category");
            }
            service.setDiscountForCategory(getCategoryFromMenu(menuButton2), new BigDecimal(fldMultidiscount.getText()));
            actionRefresh();
            deactivateUnnecessaryFields();
        } catch (NumberFormatException e) {
            setOutputText("Make sure you enter digits into discount field");
        } catch (ProductValidationException e) {
            setOutputText(e.getMessage());
        }
    }

    private void clearFields() {
        menuButton.setText("Category:");
        fldName.clear();
        fldPrice.clear();
        fldDiscount.clear();
        fldDescription.clear();
        fldError.clear();
    }

    private void actionRefresh() {
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

    private ProductCategory getCategoryFromMenu(MenuButton menuButton) {
        ProductCategory category = null;
        try {
            category = ProductCategory.valueOf(menuButton.getText());
        } catch (IllegalArgumentException e) {
            setOutputText("Please select category!");
        }
        return category;
    }

    private void fillFields(ProductDto selectedProductDto) {
        if (selectedProductDto != null) {
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

    private void setOutputText(String message) {
        fldError.setText(message);
        fldError.setVisible(true);
    }

    private ProductDto createProductFromFields(ProductDto selected) {
        return new ProductDto.Builder()
                .buildCategory(getCategoryFromMenu(menuButton))
                .buildName(fldName.getText())
                .buildPrice(new BigDecimal(fldPrice.getText()))
                .buildDiscount(new BigDecimal(fldDiscount.getText().isBlank() ? "0" : fldDiscount.getText()))
                .buildDescription(fldDescription.getText())
                .build();
    }

    private void deactivateUnnecessaryFields() {
        boxSettings.setOpacity(0.3);
        boxDiscounts.setOpacity(0.3);
        boxSettings.setDisable(true);
        boxDiscounts.setDisable(true);
        fldError.setVisible(false);
    }

    private void activateFields(HBox box) {
        box.setOpacity(1);
        box.setDisable(false);
    }
}
