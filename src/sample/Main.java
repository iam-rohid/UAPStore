package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.ClothingProduct;
import sample.models.FoodProduct;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Main extends Application {
    public static  Store store;
    public static Cart cart;
    public static  Auth auth;
    public static ScreenController screenController;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ui/home.fxml")));
        Scene scene = new Scene(root);
        screenController = new ScreenController(scene);
        screenController.addScreen("login", "ui/login.fxml");
        screenController.addScreen("home", "ui/home.fxml");
        screenController.addScreen("cart", "ui/cart.fxml");
        screenController.addScreen("admin", "ui/admin.fxml");
        primaryStage.setTitle("UAP Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        store = new Store();
        cart = new Cart();
        auth = new Auth();
//
//        store.addClothingProduct("Men's Pant", 499.0, ClothingProduct.SubCategory.Pant, 5, 15);
//        store.addClothingProduct("Women's Pant", 999.0, ClothingProduct.SubCategory.Pant, 100, 50);
        store.addFoodProduct("Kacchi", 149.0, LocalDate.now(), FoodProduct.SubCategory.Meal, 120, 35);
        launch(args);
    }
}
