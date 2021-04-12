package sample.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.enums.Category;


public class HomeController {
    ObservableList tabItems = FXCollections.observableArrayList();
    ObservableList allItems = FXCollections.observableArrayList();
    ObservableList foodItems = FXCollections.observableArrayList();
    ObservableList electronicItems = FXCollections.observableArrayList();
    ObservableList clothingItems = FXCollections.observableArrayList();
    public ListView<String> tabsListView;
    public ListView<String> itemsListView;

    //For Details
    public VBox detailsMenu = new VBox();
    public Label name = new Label();
    public Label category = new Label();
    public TextField quantityField = new TextField();
    public Button increase = new Button();
    public Button decrease = new Button();
    public Button addToCart = new Button();
    public Button buyNow = new Button();
    int quantity = 1;
    @FXML
    void initialize() {
        setTabsList();
        setAllItemsList();
        setFoodItemsList();
        setClothingItemsList();
        setElectronicItemsList();
        showAllItems();
        detailsMenu.setVisible(false);
        name.setText("Kacchi");
        quantityField.setText(quantity + "");
        category.setText(Category.Food.name());

        increase.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(quantity > 0){
                    decrease.setDisable(false);
                }
                quantity++;
                quantityField.setText(quantity + "");
            }
        });

        decrease.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if(quantity - 2  <= 0){
                    decrease.setDisable(true);
                }
                quantity--;
                quantityField.setText(quantity + "");
            }
        });
        decrease.setDisable(true);
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
    private void setAllItemsList(){
        allItems.removeAll(allItems);
        for(int i=0; i<5; i++){
            allItems.add("All Item " + i);
        }
    }
    private void setFoodItemsList(){
        foodItems.removeAll(allItems);
        for(int i=0; i<5; i++){
            foodItems.add("Food Item " + i);
        }
    }
    private void setElectronicItemsList(){
        electronicItems.removeAll(allItems);
        for(int i=0; i<5; i++){
            electronicItems.add("Electronic Item " + i);
        }
    }
    private void setClothingItemsList(){
        clothingItems.removeAll(allItems);
        for(int i=0; i<5; i++){
            clothingItems.add("Clothing Item " + i);
        }
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        if(tabsListView.getSelectionModel().getSelectedItem().equals(getLabel(Category.All.name()))){
            showAllItems();
        }
        else if(tabsListView.getSelectionModel().getSelectedItem().equals(getLabel(Category.Food.name()))){
            showFoodItems();
        }
        else if(tabsListView.getSelectionModel().getSelectedItem().equals(getLabel(Category.Electronic.name()))){
            showElectronicItems();
        }
        else if(tabsListView.getSelectionModel().getSelectedItem().equals(getLabel(Category.Clothing.name()))){
            showClothingItems();
        }
        else{
            return;
        }
        detailsMenu.setVisible(true);
    }

    private void showAllItems(){
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(allItems);
    }
    private void showFoodItems(){
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(foodItems);
    }
    private void showElectronicItems(){
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(electronicItems);
    }
    private void showClothingItems(){
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(clothingItems);
    }
}
