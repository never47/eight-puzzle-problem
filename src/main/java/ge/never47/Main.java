package ge.never47;

import ge.never47.algorithms.UnInformedSearch;
import ge.never47.nodes.Node;
import ge.never47.nodes.NodeInformed;
import ge.never47.algorithms.InformedSearch;

import static ge.never47.DataClass.init_state;

/*
    !!!!!!!!!!!!!!!!USE THIS CLASS FOR CONSOLE SOLVING!!!!!!!!!!!!!!

    P.S comment this line ->
                mainScreenController.guiUpdate(currNode_child.getState());
                in InformedSearch/UnIndormedSearched classes(algorithms package)

    There is two algorithms for searching goal_state.
        1) BFS (UnInformed Search)
        2) A*   (Informed Search)

     Difference between them:
        1) BFS checks all possible states in its queue(doesnt sort).
        2) A* uses f_function to sort states in priority queue and select the best one.
 */
public class Main {
    public static void main(String[] args){

        // you can change init_state/goal_state in DataClass
        NodeInformed root = new NodeInformed(init_state, null, "n", 0);

        // ************************** A* search **************************
        System.out.println("Staring A* search");
        long startTime = System.nanoTime();

        InformedSearch.A_star(root);

        long endTime = System.nanoTime();
        double duration = (endTime - startTime)/ 1_000_000_000.0;
        System.out.println("Duration for A* " + duration + " nanosec");

        System.out.println();

        // ************************** BFS search **************************
        System.out.println("Staring BFS search");
        startTime = System.nanoTime();

        UnInformedSearch.BFS((Node) root);

        endTime = System.nanoTime();
        duration = (endTime - startTime)/1_000_000_000.0;
        System.out.println("Duration for BFS " + duration + " nanosec");
    }
}