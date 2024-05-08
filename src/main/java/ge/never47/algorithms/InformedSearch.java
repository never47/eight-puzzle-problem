package ge.never47.algorithms;

import ge.never47.nodes.NodeInformed;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class InformedSearch {
    /*
            openList - // nodes which children where not studied
            Using priorityQueue that automatically sorts objects inside
            using comparable class property, compareTo function inside NodeInformed
         */
    public static PriorityQueue<NodeInformed> openList = new PriorityQueue<>();

    /*
        closedList - // nodes that were studied
        No need in priorityQueue, i just save nodes that i have studied
     */
    public static List<NodeInformed> closedList = new ArrayList<>();



    public static void A_star(NodeInformed root) {
        // ************ Adding root in openList, STARTING ************
        boolean goalFound = false;
        openList.add(root);

        while(!openList.isEmpty() && !goalFound){
            // ******* Getting first in queue, creating its children list *******
            NodeInformed currNode = openList.remove();
            closedList.add(currNode);

            // ***** Calculating allowable movements => adding children into list *****
            PriorityQueue<NodeInformed> currNode_children = exploreChildren(currNode);

            // ***** Checking each child on goalTest, adding to openList(sort auto) *****
            for (NodeInformed currNode_child : currNode_children) {
                if (currNode_child.goalTest()) {
                    System.out.println("FIND");

                    goalFound = true;
                    break;
                }

                if (!openList.contains(currNode_child) && !closedList.contains(currNode_child)) {
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
    private static PriorityQueue<NodeInformed> exploreChildren(NodeInformed node){
        PriorityQueue<NodeInformed> nodeChildren = new PriorityQueue<>();

        int depth = node.getDepth();
        int empty_cell = node.getEmptyCell();
        int row = Math.floorDiv(empty_cell, 3);
        int column = empty_cell % 3;

        if(node.getAction()!='l' && column < 2){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell + 1];
            temp_state[empty_cell + 1] = 0;

            nodeChildren.add(new NodeInformed(temp_state, node, 'r', depth+1));
        }

        if(node.getAction()!='r' && column > 0){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell -1];
            temp_state[empty_cell - 1] = 0;

            nodeChildren.add(new NodeInformed(temp_state, node, 'l', depth+1));
        }

        if(node.getAction()!='d' && row > 0){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell - 3];
            temp_state[empty_cell - 3] = 0;

            nodeChildren.add(new NodeInformed(temp_state, node, 'u', depth+1));
        }

        if(node.getAction()!='u' && row < 2){
            int[] temp_state = node.getStateClone();

            temp_state[empty_cell] = temp_state[empty_cell + 3];
            temp_state[empty_cell + 3] = 0;

            nodeChildren.add(new NodeInformed(temp_state, node, 'd', depth+1));
        }

        return nodeChildren;
    }
}
