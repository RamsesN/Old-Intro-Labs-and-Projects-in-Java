public class Graph {

    int numNodes;
    Node[] nodes;
    int[][] graph;
    public void addEdge(int i, int j) {
    	graph[i][j] = 1;
    	graph[j][i] = 1; 
    }

    public Graph(int num) {
	numNodes = num;
	nodes = new Node[numNodes];
	for(int i = 0; i < numNodes; i++) {
	    nodes[i] = new Node(i);
	}
	graph = new int[numNodes][numNodes];
	// you might also want to do other things here
    }

}