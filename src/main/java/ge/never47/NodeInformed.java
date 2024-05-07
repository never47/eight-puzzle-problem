package ge.never47;
import static ge.never47.Main.goal_state;

public class NodeInformed extends Node implements Comparable<NodeInformed>{
    protected int h = 0;

    public NodeInformed(int[] state, Node parentNode, char action, int depth) {
        super(state, parentNode, action, depth);
    }

    private void manhattan_function(){
        int distance = 0;

        for(int i =0; i<9;i++){
            for(int j=0;j<9;j++){
                if(state[i]!=0 && goal_state[j]!=0){
                    if(state[i]==goal_state[j]){
                        distance+= Math.abs(j%3 - i%3) + Math.abs(j/3 - i/3);
                    }
                }
            }
        }

        h = distance;
    }

    public int f_function(){
        manhattan_function();
        return h + depth;
    }

    @Override
    public int compareTo(NodeInformed o) {
        return Integer.compare(this.f_function(), o.f_function());
    }
}
