package ge.never47;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


/*
    !!!!!!!!!!!!!!!!USE THIS CLASS FOR GUI SOLVING!!!!!!!!!!!!!!

    P.S uncomment this line ->
                mainScreenController.guiUpdate(currNode_child.getState());
                in InformedSearch/UnIndormedSearched classes(algorithms package)

    There is two algorithms for searching goal_state.
        1) BFS (UnInformed Search)
        2) A*   (Informed Search)

     Difference between them:
        1) BFS checks all possible states in its queue(doesnt sort).
        2) A* uses f_function to sort states in priority queue and select the best one.
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("mainScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setResizable(false);
        stage.setTitle("Eight Puzzle Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
