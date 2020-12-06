/*-----------------------------------------------------------------------------------------
ANSWER THE QUESTIONS FROM THE DOCUMENT HERE

(1) Which graph representation did you choose, and why?

Although I chose to use an adjacency matrix for my implementation simply due to it's greater 
simplicty in implementation, I do believe that an adjacency list would have been far more
space efficiency because the each vertex has at most 3 edges. Adding an edge is however faster
with an Adjacency matrix, so in terms of speed, the maze should be solved faster with a 

(2) Which search algorithm did you choose, and why?

Although I chose to use Breadth First Search because of the simplicity of implementation, I do
believe that DFS would have been a better choice in terms of speed. This could simply be due
to the fact that the solution is always the farthest vertex from the start so choosing a method
that goes farther faster rather than one that checks all routes incrementally would've been 
wiser. This logic does however shift to favor BFS as the maze grows larger or if the maze's 
end varies in distance from the start. 

  ----------------------------------------------------------------------------------------*/

import java.io.*;
import java.util.ArrayList;
import java.lang.Math;

public class MazeSolver {

    public void run(String filename) throws IOException {

	// read the input file to extract relevant information about the maze
	String[] readFile = parse(filename);
	int mazeSize = Integer.parseInt(readFile[0]);
	int numNodes = mazeSize*mazeSize;
	String mazeData = readFile[1];

	// construct a maze based on the information read from the file
	Graph mazeGraph = buildGraph(mazeData, numNodes);

	// do something here to solve the maze

	// print out the final maze with the solution path
	printMaze(mazeGraph.nodes, mazeData, mazeSize);
    }

    // prints out the maze in the format used for HW8
    // includes the final path from entrance to exit, if one has been recorded,
    // and which cells have been visited, if this has been recorded
    public void printMaze(Node[] mazeCells, String mazeData, int mazeSize) {
	
	int ind = 0;
	int inputCtr = 0;

	System.out.print("+");
	for(int i = 0; i < mazeSize; i++) {
	    System.out.print("--+");
	}
	System.out.println();

	for(int i = 0; i < mazeSize; i++) {
	    if(i == 0) System.out.print(" ");
	    else System.out.print("|");

	    for(int j = 0; j < mazeSize; j++) {
		System.out.print(mazeCells[ind] + "" + mazeCells[ind] +  mazeData.charAt(inputCtr));
		inputCtr++;
		ind++;
	    }
	    System.out.println();

	    System.out.print("+");
	    for(int j = 0; j < mazeSize; j++) {
		System.out.print(mazeData.charAt(inputCtr) + "" +  mazeData.charAt(inputCtr) + "+");
		inputCtr++;
	    }
	    System.out.println();
	}
	
    }

    // reads in a maze from an appropriately formatted file (this matches the format of the 
    // mazes you generated in HW8)
    // returns an array of Strings, where position 0 stores the size of the maze grid (i.e., the
    // length/width of the grid) and position 1 minimal information about which walls exist
    public String[] parse(String filename) throws IOException {
	FileReader fr = new FileReader(filename);

	// determine size of maze
	int size = 0;
	int nextChar = fr.read();
	while(nextChar >= 48 && nextChar <= 57) {
	    size = 10*size + nextChar - 48;
	    nextChar = fr.read();
	}

	String[] result = new String[2];
	result[0] = size + "";
	result[1] = "";


	// skip over up walls on first row
	for(int j = 0; j < size; j++) {
	    fr.read();
	    fr.read();
	    fr.read();
	}
	fr.read();
	fr.read();

	for(int i = 0; i < size; i++) {
	    // skip over left wall on each row
	    fr.read();
	    
	    for(int j = 0; j < size; j++) {
		// skip over two spaces for the cell
		fr.read();
		fr.read();

		// read wall character
		nextChar = fr.read();
		result[1] = result[1] + (char)nextChar;

	    }
	    // clear newline character at the end of the row
	    fr.read();
	    
	    // read down walls on next row of input file
	    for(int j = 0; j < size; j++)  {
		// skip over corner
		fr.read();
		
		//skip over next space, then handle wall
		fr.read();
		nextChar = fr.read();
		result[1] = result[1] + (char)nextChar;
	    }

	    // clear last wall and newline character at the end of the row
	    fr.read();
	    fr.read();
	    
	}

	return result;
    }
    
    public Graph buildGraph(String maze, int numNodes) {

	Graph mazeGraph = new Graph(numNodes);
	int size = (int)Math.sqrt(numNodes);

	int mazeInd = 0;
	for(int i = 0; i < size; i++) {
	    // create edges for right walls in row i
	    for(int j = 0; j < size; j++) {
		char nextChar = maze.charAt(mazeInd);
		mazeInd++;
		if(nextChar == ' ') {
		    // add an edge corresponding to a right wall, using the indexing convention 
		    // for nodes
		    mazeGraph.addEdge(size*i + j, size*i + j + 1);
		}
	    }

	    // create edges for down walls below row i
	    for(int j = 0; j < size; j++)  {
		char nextChar = maze.charAt(mazeInd);
		mazeInd++;
		if(nextChar == ' ') {
		    // add an edge corresponding to a down wall, using the indexing convention
		    // for nodes
		    mazeGraph.addEdge(size*i + j, size*(i+1) + j);
		}
	    }    
	}

	return mazeGraph;
    }
    
    public Node[] solve (Graph myG) {
	// This array of nodes should include in order the list of 
    // nodes included on the path from start to end
	// This will use BFS
    	Queue q = new Queue();
    	Node current = myG.nodes[0];
    	q.enqueue(current.index);
    	while (!q.isEmpty()) {
    	int holder = q.dequeue();
    	for(int i = 0; i< myG.nodes.length; i++) {
    		if (myG.graph[current.index][i] == 1) {
    			q.enqueue(myG.nodes[i].index);
    			myG.nodes[i].Parent = current;
    			
    		}
    	}
    	current.index = holder;
    	}
    	Node temp;
    	ArrayList<Node> list = new ArrayList<Node>();
    	temp = myG.nodes[myG.nodes.length - 1];
    	while(temp.Parent != null) {
    	list.add(temp);
    	temp = temp.Parent;
    	}
    	Node[] toReturn = new Node[list.size()];
    	for(int i = 0; i<list.size(); i++) {
    		toReturn[i]=list.get(i);
    	}
    	return toReturn;
    }
    public static void main(String [] args) {
	if(args.length < 1) {
	    System.out.println("USAGE: java MazeSolver <filename>");
	}
	else{
	    try{
		new MazeSolver().run(args[0]);
	    }
	    catch(IOException e) {
		e.printStackTrace();
	    }
	}
    }

}