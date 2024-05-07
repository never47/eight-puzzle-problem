package ge.never47;

import ge.never47.algorithms.UnInformedSearch;

public class Main {
    static int[] goal_state =  {
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


        Node root = new Node(init_state, null, 'n', 0);

        UnInformedSearch.BFS(root);
    }
}