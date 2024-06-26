package ge.never47.nodes;

/*
    Each node represents state of searching algorithm and some additional information
 */

import java.util.Arrays;

import static ge.never47.DataClass.goal_state;

public class Node {
    protected int[] state = new int[9]; // state of a specific node from a set
    protected Node parentNode; // node from which it was derived (for root element value is NULL)
    protected int emptyCell; // index of empty cell in state (calculates in Constructor)
    protected String action; // action by which node was received (for root element value is Null)
    protected int depth; // count of moving from root node (for root element value is 0)

    public Node(int[] state, Node parentNode, String action, int depth){
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

    public int getEmptyCell(){
        return emptyCell;
    }

    public String getAction(){
        return action;
    }

    public int getDepth(){return depth;}

    public int[] getState(){return state;}
    public Node getParentNode(){return parentNode;}
    public int[] getStateClone(){
        return state.clone();
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
        String info = "<=== " + action + " ===>";
        for(int i = 0; i < 9; i++){
            if(i%3==0) info+="\n\t";

            info+= state[i] + " ";
        }

        info+="\n";

        return info;
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
