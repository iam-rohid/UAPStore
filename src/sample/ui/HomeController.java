package sample.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.Main;
import sample.enums.Category;
import sample.models.ClothingProduct;
import sample.models.ElectronicProduct;
import sample.models.FoodProduct;
import sample.models.Product;
import java.text.SimpleDateFormat;


public class HomeController {
    // Sidebar
    ObservableList tabItems = FXCollections.observableArrayList();
    public ListView<String> tabsListView;
    public Button viewCart = new Button();
    public Button logOut = new Button();

    @FXML
    void initialize() {
        setTabsList();
        showAllItems();
        tabsListView.getSelectionModel().selectFirst();
        detailsMenu.setVisible(false);
        quantityField.setText(quantity + "");
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
                Main.cart.viewCart();
            }
        });
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.auth.logOut();
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
    }

    String getLabel(String name){
        return name + " Items";
    }
    private void setTabsList(){
        tabItems.removeAll(tabItems);
        for(int i = 0; i< Category.values().length; i++){
            tabItems.add(getLabel(Category.values()[i].name()));
        }
        tabsListView.getItems().addAll(tabItems);
    }

    ObservableList<Product> productList;
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

    //For Details
    public VBox detailsMenu = new VBox();
    public Label totalPrice = new Label();
    public TextField quantityField = new TextField();
    public Button increase = new Button();
    public Button decrease = new Button();
    public Button addToCart = new Button();
    public Button buyNow = new Button();
    int quantity = 1;
    Product selectedProduct;


    public ListView<String> detailsListView;
    ObservableList detailsList = FXCollections.observableArrayList();

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
            detailsList.add("Expiration Date: "+ date);
        }
        if(product.getCategory() == Product.Category.Electronic){
            ElectronicProduct electronicProduct = (ElectronicProduct)product;
            detailsList.add("Type: " + electronicProduct.getSubCategory().name());
        }
        if(product.getCategory() == Product.Category.Clothing){
            ClothingProduct clothingProduct = (ClothingProduct) product;
            detailsList.add("Type: " + clothingProduct.getSubCategory().name());
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
