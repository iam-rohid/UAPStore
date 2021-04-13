package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.ClothingProduct;
import sample.models.FoodProduct;

import java.util.Date;
import java.util.Objects;

public class Main extends Application {
    public static  Store store;
    public static Cart cart;
    public static  Auth auth;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ui/cart.fxml")));
        primaryStage.setTitle("UAP Store");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        store = new Store();
        cart = new Cart();
        auth = new Auth();

        store.addClothingProduct("Men's Pant", 499.0, ClothingProduct.SubCategory.Pant);
        store.addClothingProduct("Women's Pant", 999.0, ClothingProduct.SubCategory.Pant);
        store.addFoodProduct("Kacchi", 149.0, new Date(), FoodProduct.SubCategory.Meal);

        cart.addCartItem(new FoodProduct("Kacchi", 500.0, new Date(), FoodProduct.SubCategory.Meal), 5);
        cart.addCartItem(new FoodProduct("Kacchi 2", 500.0, new Date(), FoodProduct.SubCategory.Meal), 6);
        cart.addCartItem(new FoodProduct("Kacchi 3", 500.0, new Date(), FoodProduct.SubCategory.Meal), 3);
        cart.addCartItem(new FoodProduct("Kacchi 4", 500.0, new Date(), FoodProduct.SubCategory.Meal), 1);
        launch(args);
    }
}
