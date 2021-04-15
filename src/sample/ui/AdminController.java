package sample.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import sample.Main;
import sample.enums.Category;
import sample.models.ClothingProduct;
import sample.models.ElectronicProduct;
import sample.models.FoodProduct;
import sample.models.Product;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AdminController {
    @FXML
    private ListView<String> tabsListView;
    @FXML
    private Button viewCart = new Button();
    @FXML
    private Button logOut = new Button();
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, String> productId;
    @FXML
    private TableColumn<Product, Product.Category> productCategory;
    @FXML
    private TableColumn<Product, Double> productPrice;
    @FXML
    private VBox detailsMenu;
    @FXML
    private Label totalPrice;
    @FXML
    private TextField quantityField;
    @FXML
    private Button increase;
    @FXML
    private Button decrease;
    @FXML
    private Button addToCart;
    @FXML
    private Button buyNow;
    @FXML
    private ListView<String> detailsListView;

    int quantity = 1;
    Product selectedProduct;

    ObservableList tabItems = FXCollections.observableArrayList();
    ObservableList detailsList = FXCollections.observableArrayList();
    ObservableList<Product> productList;

    @FXML
    void initialize() {
        initializeTabs();
        handleSearch();
        detailsMenu.setVisible(false);
        decrease.setDisable(true);
        quantityField.setDisable(true);
        increase.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
                if(quantity > 0){
                    decrease.setDisable(false);
                }
                quantity++;
                updateTotalPrice();
            }
        });
        decrease.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
                if(quantity - 2  <= 0){
                    decrease.setDisable(true);
                }
                quantity--;
                updateTotalPrice();
            }
        });
        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.cart.addCartItem(selectedProduct, quantity);
                quantity = 1;
                updateTotalPrice();
            }
        });
        viewCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.screenController.activate("cart");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.auth.logOut();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                loadDetailsView(newSelection);
            }else{
                detailsMenu.setVisible(false);
                selectedProduct = null;
            }
        });
    }

    void initializeTabs(){
        tabItems.removeAll(tabItems);
        for(int i = 0; i< Category.values().length; i++){
            tabItems.add(getLabel(Category.values()[i].name()));
        }
        tabsListView.getItems().clear();
        tabsListView.getItems().addAll(tabItems);
        tabsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !newSelection.equals(oldSelection)){
                if(newSelection.equals(getLabel(Category.All.name()))){
                    showAllItems();
                }
                else if(newSelection.equals(getLabel(Category.Food.name()))){
                    showFoodItems();
                }
                else if(newSelection.equals(getLabel(Category.Electronic.name()))){
                    showElectronicItems();
                }
                else if(newSelection.equals(getLabel(Category.Clothing.name()))){
                    showClothingItems();
                }
            }
        });
        tabsListView.getSelectionModel().selectFirst();
    }
    String getLabel(String name){
        return name ;
    }
    void handleSearch(){
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                tabsListView.getSelectionModel().selectFirst();
                if(t1.isEmpty() || t1.isBlank()){
                    showAllItems();
                }else{
                    ArrayList<Product> searchProducts = new ArrayList<>();
                    for(Product product: Main.store.getProducts()){
                        if(product.getName().toLowerCase().contains(t1.toLowerCase())){
                            searchProducts.add(product);
                            continue;
                        }
                        if(product.getCategory().name().toLowerCase().contains(t1.toLowerCase())){
                            searchProducts.add(product);
                            continue;
                        }
                        if(product.getId().contains(t1)){
                            searchProducts.add(product);
                            continue;
                        }
                    }
                    showSearchItems(searchProducts);
                }
            }
        });
    }
    private void showAllItems(){
        this.productList = FXCollections.observableArrayList(Main.store.getProducts());
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productId.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        productCategory.setCellValueFactory(new PropertyValueFactory<Product, Product.Category>("category"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productTable.setItems(this.productList);
    }
    private void showFoodItems(){
        this.productList = FXCollections.observableArrayList(Main.store.getAllFoodProducts());
        productTable.setItems(this.productList);
    }
    private void showElectronicItems(){
        this.productList = FXCollections.observableArrayList(Main.store.getAllElectronicProducts());
        productTable.setItems(this.productList);
    }
    private void showClothingItems(){
        this.productList = FXCollections.observableArrayList(Main.store.getAllClothingProducts());
        productTable.setItems(this.productList);
    }

    private void showSearchItems(ArrayList<Product> products){
        this.productList = FXCollections.observableArrayList(products);
        productTable.setItems(this.productList);
    }
    void loadDetailsView(Product product){
        if(selectedProduct == null || product != selectedProduct){
            this.quantity = 1;
            selectedProduct = product;
        }
        updateTotalPrice();
        detailsList.removeAll(detailsList);
        detailsList.add("Id: " + product.getId());
        detailsList.add("Name: " + product.getName());
        detailsList.add("Category: " + product.getCategory());
        if(product.getCategory() == Product.Category.Food){
            FoodProduct foodProduct = (FoodProduct) product;
            String pattern = "dd MMM yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(foodProduct.getExpirationDate());
            detailsList.add("Sub Category: " + foodProduct.getSubCategory());
            detailsList.add("Expiration Date: "+ date);
        }
        if(product.getCategory() == Product.Category.Electronic){
            ElectronicProduct electronicProduct = (ElectronicProduct)product;
            detailsList.add("Sub Category: " + electronicProduct.getSubCategory().name());
        }
        if(product.getCategory() == Product.Category.Clothing){
            ClothingProduct clothingProduct = (ClothingProduct) product;
            detailsList.add("Sub Category: " + clothingProduct.getSubCategory().name());
        }
        detailsList.add("Price: " + product.getPrice() + " Tk");
        detailsListView.getItems().clear();
        detailsListView.getItems().addAll(detailsList);
        detailsMenu.setVisible(true);
    }
    void updateTotalPrice(){
        quantityField.setText(quantity + "");
        totalPrice.setText((selectedProduct.getPrice() * quantity) + " Tk");
    }
}
