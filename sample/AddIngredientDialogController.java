package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AddIngredientDialogController {

    @FXML
    private FlowPane allIngredients;

    private ArrayList<Node> selected = new ArrayList<>();

    @FXML
    private Button done;

    public EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            Node hbox = (Node)e.getSource();
            if (selected.contains(hbox)) {
                //System.out.println(e.getSource());
                hbox.setStyle("-fx-border:1px;-fx-border-color:lightgray;-fx-border-radius:10px;" +
                        "-fx-background-color:white;");
                selected.remove(hbox);
            } else {
                hbox.setStyle("-fx-border:1px;-fx-border-color:rgb(0, 153, 51);-fx-border-radius:10px;" +
                        "-fx-background-color:white;");
                selected.add(hbox);
            }


            // hbox.removeEventFilter(MouseEvent.MOUSE_CLICKED, this);
        }
    };
    public EventHandler getEventHandler(){
        return eventHandler;
    }
    @FXML
    public void FillIngredients() throws FileNotFoundException {

        Comparator comp = new Comparator("src/RecipeList.txt");

        ArrayList<String> ingredients = new ArrayList(comp.getAllUniqueIngredients());
        Collections.sort(ingredients);



        for (String ing : ingredients) {
            HBox hbox = new HBox();
            hbox.setStyle("-fx-border:1px;-fx-border-color:lightgray;-fx-border-radius:10px;" +
                    "-fx-background-color:white;");
            hbox.setId(ing);

            Label label = new Label(ing);
            label.setStyle("-fx-padding: 5px;-fx-font-size: 16px;");



            Image image = new Image(new FileInputStream("src/Kartinki/"+ing+".png"));
            ImageView imageView = new ImageView(image);

            imageView.setFitHeight(24);
            imageView.setFitWidth(24);


            hbox.setAlignment(Pos.CENTER);



            //Registering the event filter
            hbox.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


            hbox.getChildren().addAll(imageView, label);

            allIngredients.getChildren().add(hbox);
        }

    }


    @FXML
    private void onHoverDone() {
        if (done.getText().contentEquals("Done")) {
            done.setStyle("-fx-background-color: #0062cc;" +
                    "-fx-padding: 10px;-fx-border-radius:25px;\n" +
                    "-fx-color:rgb(0, 0, 0);-fx-font-weight:400;-fx-font-size:16px;");
        }
    }

    @FXML
    private void onExitDone() {
        if (done.getText().contentEquals("Done")) {
            done.setStyle("-fx-background-color:rgb(0, 123, 255);" +
                    "-fx-padding: 10px;-fx-border-radius:25px;\n" +
                    "-fx-color:rgb(0, 0, 0);-fx-font-weight:400;-fx-font-size:16px;");

        }
    }

    @FXML
    private void onDoneClicked(ActionEvent event) {
        if (done.getText().contentEquals("Done")) {

            closeStage(event);
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public ArrayList<Node> getSelected() {
        return selected;
    }

}
