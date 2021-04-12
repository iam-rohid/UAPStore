package sample.ui;

import javafx.beans.Observable;
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
import sample.Main;
import sample.enums.Category;
import sample.models.Product;


public class HomeController {
    ObservableList tabItems = FXCollections.observableArrayList();
    ObservableList itemsList = FXCollections.observableArrayList();
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
        showAllItems();
        tabsListView.getSelectionModel().selectFirst();
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

    @FXML
    public void tabListViewMouseClick(MouseEvent arg0) {
        if(tabsListView.getSelectionModel().getSelectedItem() != null){
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
        }
    }

    @FXML
    public void itemListViewMouseClick(MouseEvent arg){
        if(itemsListView.getSelectionModel().getSelectedItem() != null){
            LoadDetailsView(itemsListView.getSelectionModel().getSelectedIndices());
        }else{
            detailsMenu.setVisible(false);
        }
    }

    private void showAllItems(){
        itemsList.removeAll(itemsList);
        for(Product product: Main.store.getProducts()){
            itemsList.add(product.getName());
        }
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(itemsList);
    }
    private void showFoodItems(){
        itemsList.removeAll(itemsList);
        for(Product product: Main.store.getAllFoodProducts()){
            itemsList.add(product.getName());
        }
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(itemsList);
    }
    private void showElectronicItems(){
        itemsList.removeAll(itemsList);
        for(Product product: Main.store.getAllElectronicProducts()){
            itemsList.add(product.getName());
        }
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(itemsList);
    }
    private void showClothingItems(){
        itemsList.removeAll(itemsList);
        for(Product product: Main.store.getAllClothingProducts()){
            itemsList.add(product.getName());
        }
        itemsListView.getItems().clear();
        itemsListView.getItems().addAll(itemsList);
    }

    void LoadDetailsView(ObservableList<Integer> index){
        detailsMenu.setVisible(true);
        var name = itemsList.get(index.get(0));
    }
}
