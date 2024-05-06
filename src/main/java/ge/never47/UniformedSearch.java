package ge.never47;

import java.util.ArrayList;
import java.util.List;

public class UniformedSearch {
    public static List<Node> openList = new ArrayList<>(); // nodes which children where not studied
    public static List<Node> closedList = new ArrayList<>(); // nodes that were studied
    private static int col_count = 3; // just for code design <3

    /*
        Breadth First Search:
            1) Algorithm adds root node in openList
                and works until openList is not empty, or goal is find
            2) Removes node from openList and starts its studying
            3) Creates list of nodes children, that were created
                by using allowable movements(right, left, up, down)
            4) Checks each child on reaching goal state
                Success: ends searching
                Fail: adds child in the end of the openList in condition that
                        its unique, no such element in openList/closedList
            5) After checking all the children, adds node to closedList
     */
    public static void BFS(Node root) {
        boolean goalFound = false;
        openList.add(root);

        while(!openList.isEmpty() && !goalFound){
            Node currNode = openList.remove(0);
            
            closedList.add(currNode);


            List<Node> currNode_children = new ArrayList<>();

            int empty_cell = currNode.getEmptyCell();
            int row = Math.floorDiv(empty_cell, 3);
            int column = empty_cell % 3;

            if(currNode.getAction()!='l' && column < 2){
                currNode_children.add(currNode.moveToRight());
            }

            if(currNode.getAction()!='r' && column > 0){
                currNode_children.add(currNode.moveToLeft());
            }

            if(currNode.getAction()!='d' && row > 0){
                currNode_children.add(currNode.moveToUp());
            }

            if(currNode.getAction()!='u' && row < 2){
                currNode_children.add(currNode.moveToDown());
            }

            for (Node currNode_child : currNode_children) {
                if (currNode_child.goalTest(new int[]
                        {1, 2, 3,
                                8, 0, 4,
                                7, 6, 5}

                )) {
                    System.out.println("FIND");

                    goalFound = true;
                    break;
                }

                if (!openList.contains(currNode_child) && !closedList.contains(currNode_child)) {
                    System.out.println(currNode_child.printPuzzle());
                    openList.add(currNode_child);
                }
            }
        }
    }

}
