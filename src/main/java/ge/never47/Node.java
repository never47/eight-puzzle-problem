package ge.never47;

public class Node {
    private int[] puzzle = new int[9];
    private int empty_cell;

    public Node(int[] p){
        for(int i = 0; i < p.length; i++) {
            this.puzzle[i] = p[i];

            if(p[i]==0){
                empty_cell = i;
            }
        }
    }

    int getEmptyCell(){
        return empty_cell;
    }

    public boolean goalTest(int[] goalState){
        for(int i =0; i<goalState.length;i++){
            if(goalState[i]!=puzzle[i]) return false;
        }
        return true;
    }

    public void printPuzzle(){
        for(int i = 0; i < 9; i++){
            if(i%3==0) System.out.println();
            System.out.print(puzzle[i] + " ");
        }
    }

    public void copyPuzzle(int[] temp_puzzle){
        for(int i = 0; i < puzzle.length; i++) {
            temp_puzzle[i] = this.puzzle[i];
        }
    }

    public Node moveToRight(){
            int[] temp_puzzle = new int[9];
            copyPuzzle(temp_puzzle);

            temp_puzzle[empty_cell] = temp_puzzle[empty_cell+1];
            temp_puzzle[empty_cell + 1] = 0;

            return new Node(temp_puzzle);
    }

    public Node moveToLeft(){
        int[] temp_puzzle = new int[9];
        copyPuzzle(temp_puzzle);

        temp_puzzle[empty_cell] = temp_puzzle[empty_cell-1];
        temp_puzzle[empty_cell - 1] = 0;

        return new Node(temp_puzzle);
    }

    public Node moveToUp(){
        int[] temp_puzzle = new int[9];
        copyPuzzle(temp_puzzle);


        temp_puzzle[empty_cell] = temp_puzzle[empty_cell-3];
        temp_puzzle[empty_cell - 3] = 0;

        return new Node(temp_puzzle);
    }

    public Node moveToDown(){
        int[] temp_puzzle = new int[9];
        copyPuzzle(temp_puzzle);


        temp_puzzle[empty_cell] = temp_puzzle[empty_cell + 3];
        temp_puzzle[empty_cell + 3] = 0;

        return new Node(temp_puzzle);
    }
}
