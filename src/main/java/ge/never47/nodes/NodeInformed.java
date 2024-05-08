package ge.never47.nodes;

import static ge.never47.DataClass.goal_state;

public class NodeInformed extends Node implements Comparable<NodeInformed>{
    protected int h = 0; // value of heuristic function

    public NodeInformed(int[] state, Node parentNode, char action, int depth) {
        super(state, parentNode, action, depth);
    }

    /*
        Calculating value of h using manhattan function
        Value is equal to sum of distances,
            Each distance is sum of the cells(horizontally and vertically)
            of how far the cell is from the goal
     */
    private void manhattan_function(){
        int distance = 0;

        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                if(state[i]!=0 && goal_state[j]!=0){
                    if(state[i]==goal_state[j]){
                        distance+= (Math.abs(j%3 - i%3) + Math.abs(j/3 - i/3));
                    }
                }
            }
        }

        h = distance;
    }

    /*
        f(s) = h(s) + g(s)

        I use depth value, because cost per operation is 1
     */
    public int f_function(){
        manhattan_function();
        return h + depth;
    }

    /*
        This is a property that is used to sort queue automatically
     */
    @Override
    public int compareTo(NodeInformed o) {
        return Integer.compare(this.f_function(), o.f_function());
    }
}
