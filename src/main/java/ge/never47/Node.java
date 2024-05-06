package ge.never47;

/*
    Each node represents state of searching algorithm and some additional information
 */

import java.util.Arrays;

public class Node {
    private int[] state = new int[9];
    private Node parentNode;
    private int emptyCell;
    private char action;
    private int depth;

    public Node(int[] state, Node parentNode, char action, int depth){
        this.parentNode = parentNode;
        this.action = action;
        this.depth = depth;


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


    public boolean goalTest(int[] goalState){
        for(int i =0; i<goalState.length;i++){
            if(goalState[i]!= state[i]) {
                return false;
            }
        }
        return true;
    }


    public String printPuzzle(){
        String info = "";
        for(int i = 0; i < 9; i++){
            if(i%3==0) info+="\n";

            info+= state[i] + " ";
        }

        info+="Depth: " + depth;
        info+="Parent: " + parentNode;
        info+="\n";
        return info;
    }



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
