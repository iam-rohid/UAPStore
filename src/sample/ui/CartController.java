package sample.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sample.models.CartItem;
import sample.models.Product;

public class CartController {

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private VBox detailsMenu;

    @FXML
    private ListView<?> detailsListView;

    @FXML
    private Button decreaseButton;

    @FXML
    private TextField quantityField;

    @FXML
    private Button increaseButton;

    @FXML
    private Button removeItemButton;

    @FXML
    private TableView<CartItem> cartListTable;

    @FXML
    private TableColumn<Product, String> productId;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Product.Category> productCategory;

    @FXML
    private TableColumn<CartItem, Integer> productQuantity;

    @FXML
    private TableColumn<CartItem, Double> productPrice;
}
