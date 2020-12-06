import java.util.LinkedList;

public class Node {

    int index;
    Node Parent;
    // to help you keep track of things as you're solving the maze
    boolean visited = false;
    boolean inSolution = false;

    // these are just here to help with the toString method
    static final String PATH = "The End!";
    static final String VISIT = "Visted";
    static final String NOT_VISIT = "Not Visited";

    public String toString() {
	if(visited) {
	    if(inSolution) return PATH;
	    else return VISIT;
	}
	else return NOT_VISIT;
    }

    public Node(int i) {
	index = i;
    }

}
