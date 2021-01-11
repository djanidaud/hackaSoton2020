package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    Comparator comp = new Comparator("src/RecipeList.txt");
    @FXML
    private Button add;
    @FXML
    private Button find;
    @FXML
    private VBox ingredients;
    @FXML
    private HBox imagePane;
    @FXML
    private VBox recipesPane;

    @FXML
    private VBox scroll;

    @FXML
    public void onButtonClicked() {
        System.out.println("Hello");
    }

    @FXML
    private ScrollPane scrollPane;

    boolean spAdded = false;


    public EventHandler<MouseEvent> mouseEnter = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox)e.getSource();

            hbox.setStyle("-fx-border:1px;-fx-border-color:rgb(255, 102, 102);-fx-border-radius:10px;" +
                    "-fx-background-color:rgb(255, 102, 102);");
            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };
    public EventHandler<MouseEvent> mouseExit = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox)e.getSource();

            hbox.setStyle("-fx-border:1px;-fx-border-color:rgb(0, 153, 51);-fx-border-radius:10px;" +
                    "-fx-background-color:white;");

            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };
    public EventHandler<MouseEvent> mouseClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {

            HBox hbox = (HBox)e.getSource();

            ingredients.getChildren().remove(hbox);
            comp.removeUserIngredient(hbox.getId());
            comp.clearAble();
            comp.compare();
            //System.out.println(hbox.getParent().getChildrenUnmodifiable().toString());
            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };



    @FXML
    private void onHoverAdd() {
        if (add.getText().contentEquals("Add an ingredient")) {
            add.setStyle("-fx-background-color: #0062cc;" +
                    "-fx-padding: 10px;-fx-border-radius:25px;\n" +
                    "-fx-color:rgb(0, 0, 0);-fx-font-weight:400;-fx-font-size:16px;");
        }
    }

    @FXML
    private void onExitAdd() {
        if (add.getText().contentEquals("Add an ingredient")) {
            add.setStyle("-fx-background-color:rgb(0, 123, 255);" +
                    "-fx-padding: 10px;-fx-border-radius:25px;\n" +
                    "-fx-color:rgb(0, 0, 0);-fx-font-weight:400;-fx-font-size:16px;");

        }
    }

    @FXML
    private void onHoverFind() {
        if (find.getText().contentEquals("Find recipes")) {
            find.setStyle("-fx-background-color: #0062cc;" +
                    "-fx-padding: 10px;-fx-border-radius:25px;\n" +
                    "-fx-color:rgb(0, 0, 0);-fx-font-weight:400;-fx-font-size:16px;");
        }
    }

    @FXML
    private void onExitFind() {
        if (find.getText().contentEquals("Find recipes")) {
            find.setStyle("-fx-background-color:rgb(0, 123, 255);" +
                    "-fx-padding: 10px;-fx-border-radius:25px;\n" +
                    "-fx-color:rgb(0, 0, 0);-fx-font-weight:400;-fx-font-size:16px;");
        }
    }

    @FXML
    private void onFindClick() throws FileNotFoundException {
        comp.clearAble();
        comp.compare();
        ArrayList<Recipe> recipes = comp.getAbleToCook();

        recipesPane.getChildren().clear();
        if (!recipes.isEmpty()) {
            imagePane.getChildren().clear();


            for (Recipe n : recipes) {


                TitledPane recipePane = new TitledPane();
                recipePane.setText(n.getName());

                recipePane.setId(n.getName());
                recipePane.setExpanded(false);

                BorderPane recipeDesc = new BorderPane();
                Text text = new Text(n.getDescription());
                text.setWrappingWidth(500);
                recipeDesc.setCenter(text);
                recipeDesc.setStyle("-fx-background-color:white");

                VBox recipeIngredients = new VBox();

                for (String ing : n.getIngredients()) {

                    HBox ingredientBox = new HBox();
                    ingredientBox.getChildren().add(new Label("-" + ing));
                    ingredientBox.getChildren().add(new ImageView(new Image(
                            new FileInputStream("src/Kartinki/" + ing + ".png"))));
                    recipeIngredients.getChildren().add(ingredientBox);
                }


                recipeDesc.setRight(recipeIngredients);
                recipePane.setContent(recipeDesc);
                if (!isInRecipePane(n.getName()))
                    recipesPane.getChildren().add(recipePane);

            }

        }
    }

    @FXML
    private void addIngredient() throws IOException {
        ArrayList<Node> selected = onOpenDialog();

        if(!spAdded) {
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            spAdded=true;
        }

        for (Node ing : selected) {

            String id = ing.getId();


            if (!isInList(id)) {


                comp.addUserIngredient(ing.getId());
                ing.addEventFilter(MouseEvent.MOUSE_ENTERED,mouseEnter);
                ing.addEventFilter(MouseEvent.MOUSE_EXITED,mouseExit);
                ing.addEventFilter(MouseEvent.MOUSE_CLICKED,mouseClick);

                ingredients.getChildren().add(ing);
                scrollPane.setContent(ingredients);

            } else System.out.println("Item is in list");

        }

        //System.out.println(ingredients.getChildren().toString());
    }

    private boolean isInList(String id) {

        for (int i = 0; i < ingredients.getChildren().size(); i++) {
            if (ingredients.getChildren().get(i).getId().equals(id)) return true;
        }
        return false;
    }

    private boolean isInRecipePane(String id) {

        for (int i = 0; i < recipesPane.getChildren().size(); i++) {
            if (recipesPane.getChildren().get(i).getId().equals(id)) return true;
        }
        return false;
    }

    @FXML
    private ArrayList<Node> onOpenDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addIngredientDialog.fxml"));
        Parent parent = fxmlLoader.load();
        AddIngredientDialogController dialogController = fxmlLoader.getController();
        // dialogController.setAppMainObservableList(tvObservableList);


        dialogController.FillIngredients();

        Scene scene = new Scene(parent, 600, 550);
        Stage stage = new Stage();
        stage.setMinWidth(600);
        stage.setMinHeight(600);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        //return parent.getChildrenUnmodifiable().toString();

        EventHandler handler = dialogController.getEventHandler();
        //

        ArrayList<Node> temporary = new ArrayList<>(dialogController.getSelected());
        for (Node ing : temporary) {
            ing.removeEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        }
        return temporary;
    }

}
