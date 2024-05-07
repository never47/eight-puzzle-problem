package ge.never47;

/*
    Each node represents state of searching algorithm and some additional information
 */

import java.util.Arrays;

import static ge.never47.Main.goal_state;

public class Node {
    protected int[] state = new int[9]; // state of a specific node from a set
    protected Node parentNode; // node from which it was derived (for root element value is NULL)
    protected int emptyCell; // index of empty cell in state (calculates in Constructor)
    protected char action; // action by which node was received (for root element value is Null)
    protected int depth; // count of moving from root node (for root element value is 0)

    public Node(int[] state, Node parentNode, char action, int depth){
        this.parentNode = parentNode;
        this.action = action;
        this.depth = depth;

        // copying state and searching for empty cell

        for(int i = 0; i < state.length; i++) {
            this.state[i] = state[i];

            if(state[i]==0){
                emptyCell = i;
            }
        }
    }

    int getEmptyCell(){
        return emptyCell;
    }

    char getAction(){
        return action;
    }

    /*
        Function compares object state to goalState,
        if their value is equal => returns true, else => false

        goalState matrix can be different
     */
    public boolean goalTest(){
        for(int i =0; i<goal_state.length;i++){
            if(goal_state[i]!= state[i]) {
                return false;
            }
        }
        return true;
    }

    /*
        Prints array like matrix 3x3.
        returns string
     */
    public String printPuzzle(){
        String info = "";
        for(int i = 0; i < 9; i++){
            if(i%3==0) info+="\n";

            info+= state[i] + " ";
        }

        info+="\n";
        return info;
    }

    //********************** MOVING FUNCTIONS **************************
    /*
        All of them have same logic:
            1) Function creates new state
            2) Based on movement, makes changes
            3) Creates new Node, returns it
     */

    public Node moveToRight(){
            int[] temp_state = state.clone();

            temp_state[emptyCell] = temp_state[emptyCell +1];
            temp_state[emptyCell + 1] = 0;

            return new Node(temp_state, this, 'r', this.depth+1);
    }

    public Node moveToLeft(){
        int[] temp_state = state.clone();

        temp_state[emptyCell] = temp_state[emptyCell -1];
        temp_state[emptyCell - 1] = 0;

        return new Node(temp_state, this, 'l', this.depth+1);
    }

    public Node moveToUp(){
        int[] temp_state = state.clone();

        temp_state[emptyCell] = temp_state[emptyCell -3];
        temp_state[emptyCell - 3] = 0;

        return new Node(temp_state, this, 'u', this.depth+1);
    }

    public Node moveToDown(){
        int[] temp_state = state.clone();

        temp_state[emptyCell] = temp_state[emptyCell + 3];
        temp_state[emptyCell + 3] = 0;

        return new Node(temp_state, this, 'd', this.depth+1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Arrays.equals(state, node.state);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(state);
    }
}
