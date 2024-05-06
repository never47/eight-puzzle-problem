package ge.never47;

public class Main {
    public static void main(String[] args) {
        int[] init_puzzle = {
                1,2,3,
                0,8,4,
                7,6,5
        };

        Node root = new Node(init_puzzle);
        UniformedSearch.BFS(root);
    }
}