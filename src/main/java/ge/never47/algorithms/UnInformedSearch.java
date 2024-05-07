package ge.never47.algorithms;

import ge.never47.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class UnInformedSearch {
    public static List<Node> openList = new ArrayList<>(); // nodes which children where not studied
    public static List<Node> closedList = new ArrayList<>(); // nodes that were studied

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
        // ************ Adding root in openList, STARTING ************
        boolean goalFound = false;
        openList.add(root);

        while(!openList.isEmpty() && !goalFound){
            // ******* Getting first in queue, creating its children list *******
            Node currNode = openList.remove(0);
            closedList.add(currNode);

            // ***** Calculating allowable movements => adding children into list *****
            List<Node> currNode_children = exploreChildren(currNode);

            // *** Checking each child on goalTest, adding to openList ***
            for (Node currNode_child : currNode_children) {
                if (currNode_child.goalTest()) {
                    System.out.println("FIND");

                    goalFound = true;
                    break;
                }

                if (!openList.contains(currNode_child) && !closedList.contains(currNode_child)) {
                    //System.out.println(currNode_child.printPuzzle());
                    openList.add(currNode_child);
                }
            }
        }
    }

    /*
        This function explores all allowable children of the node.

        1) Calculates which movements are possible
        2) Clones state list of node, makes changes
        3) Then creates new object with new state
        4) Adds all objects in nodeChildren list, then returns
     */
    private static List<Node> exploreChildren(Node node){
        List<Node> nodeChildren = new ArrayList<>();

        int depth = node.getDepth();
        int empty_cell = node.getEmptyCell();
        int row = Math.floorDiv(empty_cell, 3);
        int column = empty_cell % 3;

        if(node.getAction()!='l' && column < 2){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell + 1];
            temp_state[empty_cell + 1] = 0;

            nodeChildren.add(new Node(temp_state, node, 'r', depth+1));
        }

        if(node.getAction()!='r' && column > 0){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell -1];
            temp_state[empty_cell - 1] = 0;

            nodeChildren.add(new Node(temp_state, node, 'l', depth+1));
        }

        if(node.getAction()!='d' && row > 0){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell - 3];
            temp_state[empty_cell - 3] = 0;

            nodeChildren.add(new Node(temp_state, node, 'u', depth+1));
        }

        if(node.getAction()!='u' && row < 2){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell + 3];
            temp_state[empty_cell + 3] = 0;

            nodeChildren.add(new Node(temp_state, node, 'd', depth+1));
        }

        return nodeChildren;
    }
}
