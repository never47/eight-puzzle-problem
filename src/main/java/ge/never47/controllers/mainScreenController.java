package ge.never47.controllers;

import ge.never47.Tools;
import ge.never47.algorithms.InformedSearch;
import ge.never47.algorithms.UnInformedSearch;
import ge.never47.nodes.Node;
import ge.never47.nodes.NodeInformed;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static ge.never47.DataClass.goal_state;
import static ge.never47.DataClass.init_state;

public class mainScreenController {
    @FXML
    private GridPane gridPane;

    List<TextField> textFields;

    int algorithm_number = 0; // 0 - BFS, 1 - A

    static SimpleIntegerProperty[] arrayProperties = new SimpleIntegerProperty[9];

    public void initialize() {
        for (int i = 0; i < 9; i++) {
            arrayProperties[i] = new SimpleIntegerProperty(init_state[i]);
        }

        textFields = gridPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList());

        for(int i = 0;i < 9;i++){
            textFields.get(i).textProperty().bind(arrayProperties[i].asString());
        }
    }

    public static void guiUpdate(int[] array){
        Platform.runLater(() -> {
            for (int i = 0; i < 9; i++) {
                arrayProperties[i].set(array[i]);
            }
        });
    }


    public void randomInitState() {
        Random rand = new Random();

        for (int i = 0; i < init_state.length; i++) {
            int randomIndexToSwap = rand.nextInt(init_state.length);
            int temp = init_state[randomIndexToSwap];
            init_state[randomIndexToSwap] = init_state[i];
            init_state[i] = temp;
        }

        for (int i = 0; i < 9; i++) {
            arrayProperties[i].set(init_state[i]);
        }
    }

    public void selectAlgoBfs(ActionEvent actionEvent) {
        algorithm_number = 0;
    }

    public void selectAlgoA(ActionEvent actionEvent) {
        algorithm_number = 1;
    }

    private boolean validTextFields(){
        for(int i = 0; i<9;i++){
            for(int j=0; j<9;j++){
                if(i == Integer.parseInt(textFields.get(j).getText())) break;
                if(i != Integer.parseInt(textFields.get(j).getText()) && j==8){
                    return false;
                }
            }
        }
        return true;
    }

    public void changeGoalState(ActionEvent actionEvent) {
        if(!validTextFields()) {
            Tools.showAlarm(
                    Alert.AlertType.ERROR,
                    "Goal State Changing",
                    "Can't change goal state...\nValues must be unique!");
            return;
        }

        int i = 0;
        for(TextField textField : textFields){
            goal_state[i++] = Integer.parseInt(textField.getText());
        }

        Tools.showAlarm(Alert.AlertType.INFORMATION,
                "Goal State Changing",
                "Goal state was successfully changed"
                );
    }

    public void startSolving(ActionEvent actionEvent) {
        if(!validTextFields()) {
            Tools.showAlarm(
                    Alert.AlertType.ERROR,
                    "Start Button",
                    "Can't start solving...\nValues must be unique!");
            return;
        }

        NodeInformed root = new NodeInformed(init_state, null, 'n', 0);

        boolean isSolved;

        if(algorithm_number == 1){
            isSolved = InformedSearch.A_star(root);
        }else{
            isSolved = UnInformedSearch.BFS((Node) root);
        }

        if(isSolved){
            Tools.showAlarm(
                    Alert.AlertType.INFORMATION,
                    "",
                    "Solve was found!"
            );
        }else{
            Tools.showAlarm(
                    Alert.AlertType.INFORMATION,
                    "",
                    "Solve wasn't found!"
            );
        }
    }
}
