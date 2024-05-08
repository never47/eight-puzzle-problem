package ge.never47.controllers;

import ge.never47.Tools;
import ge.never47.algorithms.InformedSearch;
import ge.never47.algorithms.UnInformedSearch;
import ge.never47.nodes.Node;
import ge.never47.nodes.NodeInformed;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static ge.never47.DataClass.goal_state;
import static ge.never47.DataClass.init_state;

public class mainScreenController {
    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane innerAnchorPane;

    List<TextField> textFields;

    int algorithm_number = 0; // 0 - BFS, 1 - A


    public void initialize() {
        setGridFields();

    }

    private void setGridFields(){
        textFields = gridPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList());
    }

    public void randomInitState() {
        if(textFields == null) setGridFields();
        Random rand = new Random();

        for (int i = 0; i < init_state.length; i++) {
            int randomIndexToSwap = rand.nextInt(init_state.length);
            int temp = init_state[randomIndexToSwap];
            init_state[randomIndexToSwap] = init_state[i];
            init_state[i] = temp;
        }

        int i = 0;
        for (TextField textField : textFields){
            textField.setText(String.valueOf(init_state[i++]));
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
        if(textFields == null) setGridFields();
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
        if(textFields == null) setGridFields();
        if(!validTextFields()) {
            Tools.showAlarm(
                    Alert.AlertType.ERROR,
                    "Start Button",
                    "Can't start solving...\nValues must be unique!");
            return;
        }

        NodeInformed root = new NodeInformed(init_state, null, 'n', 0);

        if(algorithm_number == 0){
            InformedSearch.A_star(root);
        }else{
            UnInformedSearch.BFS((Node) root);
        }
    }
}
