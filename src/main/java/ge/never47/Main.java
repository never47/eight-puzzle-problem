package ge.never47;

import ge.never47.algorithms.UnInformedSearch;
import ge.never47.nodes.Node;
import ge.never47.nodes.NodeInformed;
import ge.never47.algorithms.InformedSearch;

/*
    There is two algorithms for searching goal_state.
        1) BFS (UnInformed Search)
        2) A*   (Informed Search)

     Difference between them:
        1) BFS checks all possible states in its queue(doesnt sort).
        2) A* uses f_function to sort states in priority queue and select the best one.

 */
public class Main {
    public static int[] goal_state =  {
            1, 2, 3,
            8, 0, 4,
            7, 6, 5
    };

    public static void main(String[] args){
        int[] init_state = {
                8,3,2,
                1,0,4,
                7,6,5
        };

        NodeInformed root = new NodeInformed(init_state, null, 'n', 0);

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