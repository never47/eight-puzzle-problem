package ge.never47;

import java.util.ArrayList;
import java.util.List;

public class UniformedSearch {
    public static List<Node> pathToSolution = new ArrayList<Node>();
    public static List<Node> openList = new ArrayList<Node>();

    private static int col_count = 3;


    public static List<Node> BFS(Node root){
        boolean goalFound = false;
        openList.add(root);

        while(!openList.isEmpty() && !goalFound){
            Node currNode = openList.get(0);
            openList.remove(0);

            List<Node> currNode_children = new ArrayList<Node>();

            int empty_cell = currNode.getEmptyCell();

            if(empty_cell % col_count < col_count - 1){
                currNode_children.add(currNode.moveToRight());
            }

            if(empty_cell % col_count > 0){
                currNode_children.add(currNode.moveToLeft());
            }

            if(empty_cell - col_count >= 0){
                currNode_children.add(currNode.moveToUp());
            }

            if(empty_cell + col_count < 9){
                currNode_children.add(currNode.moveToDown());
            }

            for(int i = 0; i < currNode_children.size();i++){
                Node currNode_child = currNode_children.get(i);
                currNode_child.printPuzzle();
                System.out.println();

                if(currNode_child.goalTest(new int[]
                        {1,2,3,
                         0,8,4,
                         7,6,5}

                )){
                    System.out.println("FIND");
                    goalFound = true;
                    break;
                }else{
                    openList.add(currNode_child);
                }
            }
        }

        return pathToSolution;
    }
}
