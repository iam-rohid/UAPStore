package sample.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sample.models.ClothingProduct;
import sample.models.ElectronicProduct;
import sample.models.FoodProduct;
import sample.models.Product;

public class AddProductForm {

    @FXML
    private VBox form;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productQuantity;

    @FXML
    private ComboBox<Product.Category> productCategory;

    @FXML
    private TextField productDiscount;

    @FXML
    private TextField productSalePrice;

    @FXML
    private GridPane fpGroup;

    @FXML
    private TextField fpExpirationDate;

    @FXML
    private ComboBox<FoodProduct.SubCategory> fpCategory;

    @FXML
    private GridPane epGroup;

    @FXML
    private ComboBox<ElectronicProduct.SubCategory> epCategory;

    @FXML
    private GridPane cpGroup;

    @FXML
    private ComboBox<ClothingProduct.SubCategory> cpCategory;

    @FXML
    private Button addProduct;

    ObservableList<String> categorys = FXCollections.observableArrayList();
    ObservableList<String> fpCategorys = FXCollections.observableArrayList();
    ObservableList<String> epCategorys = FXCollections.observableArrayList();
    ObservableList<String> cpCategorys = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        for (Product.Category value : Product.Category.values()) {
            categorys.add(value.name());
        }
        for (FoodProduct.SubCategory value : FoodProduct.SubCategory.values()) {
            fpCategorys.add(value.name());
        }
        for (ElectronicProduct.SubCategory value : ElectronicProduct.SubCategory.values()) {
            epCategorys.add(value.name());
        }
        for (ClothingProduct.SubCategory value : ClothingProduct.SubCategory.values()) {
            cpCategorys.add(value.name());
        }
    }
}
