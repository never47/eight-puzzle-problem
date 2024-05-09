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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static ge.never47.DataClass.goal_state;
import static ge.never47.DataClass.init_state;


/*
    GUI CONTROLLER CLASS

    Functions are connected to buttons, they are called, when buttons were clicked.
 */
public class mainScreenController {
    public VBox buttonsLayer;
    @FXML
    private GridPane gridPane;

    List<TextField> textFields;
    List<Button> buttons = new ArrayList<>();

    private int algorithm_number = 0; // 0 - BFS, 1 - A

    /*
        App is printing info about algorithm, which one it was using

        The variable last_worked_algorithm_number is used to
        save last used algorithm(just for printing)

        P.S can't use algorithm_number variable, becase
            user can change algorithm_number before clicking on SHOW RESULTS
     */
    private int last_worked_algorithm_number = 0;

    /*
        arrayProperties => automatically updates GUI textfields (there is written info about states)
            when it gets new values

        Quite easy: change arrayProperties and it will occur on display
     */
    static SimpleIntegerProperty[] arrayProperties = new SimpleIntegerProperty[9];

    /*
        searchedNode => last node(if it was find) that equals to goal_state

        using this one to print pathTrace
     */
    private Node searchedNode = null;

    private double duration = 0; // for time counting in millisecs

    /*
        This functions is called automatically when class creates
     */
    public void initialize() {
        // creating and initializing arrayProperties with init_state
        for (int i = 0; i < 9; i++) {
            arrayProperties[i] = new SimpleIntegerProperty(init_state[i]);
        }

        // gets all textFields from the grid, used to occur states info
        textFields = gridPane.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> (TextField) node)
                .collect(Collectors.toList());

        // binding textFields with arrayProperties
        for(int i = 0;i < 9;i++){
            textFields.get(i).textProperty().bind(arrayProperties[i].asString());
        }

        // getting all buttons from the layout, used to disable some buttons
            // while other one is pressed
        for (javafx.scene.Node node : buttonsLayer.getChildren()) {
            if (node instanceof Button) {
                buttons.add((Button) node);
            }
        }
    }

    /*
        Showing state information on display

        Function gets some array and copies its values
        to arrayProperties, which is binded with textFields (initialize func)
     */
    public static void guiUpdate(int[] array){
        Platform.runLater(() -> {
            for (int i = 0; i < 9; i++) {
                arrayProperties[i].set(array[i]);
            }
        });
    }

    /*
        Disable all layout buttons except active button
     */
    private void disableOtherButtons(Button button){
        for(Button curButton : buttons){
            if(!curButton.equals(button)){
                curButton.setDisable(true);
            }
        }
    }

    /*
        Enables all layout buttons
    */
    private void enableAllButtons(){
        for(Button curButton : buttons){
            curButton.setDisable(false);
        }
    }

    /*
        Shuffles init_state,  copies info in arrayProperties
     */
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

    /*
        Checks array values, in must have each value from [0...8], if not -> error
     */
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

    /*
        Checks on valid and changes goal_state, shows some alarm
     */
    public void changeGoalState(ActionEvent actionEvent) {
        if(!validTextFields()) {
            Tools.showAlarm(
                    Alert.AlertType.ERROR,
                    "Goal State Changing",
                    "Can't change goal state...\nValues must be unique!");
            return;
        }

        for (int i = 0; i < 9; i++) {
            goal_state[i] = arrayProperties[i].get();
        }

        Tools.showAlarm(Alert.AlertType.INFORMATION,
                "Goal State Changing",
                "Goal state was successfully changed"
        );
    }

    /*
        Checks validText, if its okay => gets new info from textFields, starts solving the problem
     */
    public void startSolving(ActionEvent actionEvent) {
        if(!validTextFields()) {
            Tools.showAlarm(
                    Alert.AlertType.ERROR,
                    "Start Button",
                    "Can't start solving...\nValues must be unique!");
            return;
        }

        // copying process from textFields to arrayProperties
        for(int i=0;i<textFields.size();i++){
            arrayProperties[i].set(Integer.parseInt(textFields.get(i).getText()));
        }

        disableOtherButtons((Button) actionEvent.getSource());

        // if someting will gone wrong -> searchedNode will always be null
        searchedNode = null;

        NodeInformed root = new NodeInformed(init_state, null, "n", 0);


        long startTime = System.nanoTime();

        if(algorithm_number == 1){
            last_worked_algorithm_number = 1;
            searchedNode = InformedSearch.A_star(root);
        }else{
            last_worked_algorithm_number = 0;
            searchedNode = UnInformedSearch.BFS((Node) root);
        }

        long endTime = System.nanoTime();
        duration = (endTime - startTime)/ 1_000_000_000.0;

        if(searchedNode!=null){
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

        enableAllButtons();
    }

    /*
        Creates .txt file with algorithm working result
     */
    public void showResult(ActionEvent actionEvent) {
        if(searchedNode!=null){
            List<Node> pathTrace = new ArrayList<>();

            Node currNode = searchedNode;
            pathTrace.add(currNode);

            while(currNode.getParentNode()!=null){
                currNode = currNode.getParentNode();
                pathTrace.add(currNode);
            }

            try {
                String jarPath = new File(mainScreenController.class.getProtectionDomain().getCodeSource().
                        getLocation().toURI()).getParentFile().getPath();
                String fileName = jarPath + File.separator + Arrays.toString(init_state);

                File file = new File(fileName);
                FileWriter fw = new FileWriter(fileName);
                BufferedWriter bw = new BufferedWriter(fw);

                Collections.reverse(pathTrace);
                bw.write("\tAlgorithm used: " + (last_worked_algorithm_number==0?"BFS":"A*") + "\n");
                bw.write("\tDepth level: " + searchedNode.getDepth() + "\n");
                bw.write("\tRequired time in millisecs: " + duration + "\n\n");

                bw.write("\tInit State \tGoal State\n");
                bw.write("\t"+init_state[0] + " " + init_state[1] + " " + init_state[2]);
                bw.write("\t\t"+goal_state[0] + " " + goal_state[1] + " " + goal_state[2]+"\n");
                bw.write("\t"+init_state[3] + " " + init_state[4] + " " + init_state[5]);
                bw.write("\t\t"+goal_state[3] + " " + goal_state[4] + " " + goal_state[5]+"\n");
                bw.write("\t"+init_state[6] + " " + init_state[7] + " " + init_state[8]);
                bw.write("\t\t"+goal_state[6] + " " + goal_state[7] + " " + goal_state[8]+"\n\n");


                bw.write("\t Path Trace:\n\n");

                for(Node node : pathTrace){
                    bw.write(node.printPuzzle());
                }

                bw.close();
                fw.close();
            } catch (Exception e) {
                Tools.showAlarm(
                        Alert.AlertType.ERROR,
                        " ",
                        "Something went wrong....\nCan't create file.");
                e.printStackTrace();
            }
        }else{
            Tools.showAlarm(Alert.AlertType.ERROR, "", "Firt start solving, no info saved yet");
        }
    }
}
