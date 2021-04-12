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
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ui/home.fxml")));
        primaryStage.setTitle("UAP Store");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        store = new Store();
        store.addClothingProduct("Men's Pant", 499.0, ClothingProduct.SubCategory.Pant);
        store.addClothingProduct("Women's Pant", 999.0, ClothingProduct.SubCategory.Pant);
        store.addFoodProduct("Kacchi", 149.0, new Date(), FoodProduct.SubCategory.Meal);
        cart = new Cart();
        launch(args);
    }
}
