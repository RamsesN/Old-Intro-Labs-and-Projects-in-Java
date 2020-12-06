import java.lang.Math;
import java.util.Random;
public class MazeGenerator {

    public void run(int n) {

	// creates all cells
	Cell[][] mazeMap = new Cell[n][n];
	initializeCells(mazeMap);

	// create a list of all internal walls, and links the cells and walls
	Wall[] walls = getWalls(mazeMap);

	createMaze(walls, mazeMap);

	printMaze(mazeMap);

    }

    public void createMaze(Wall[] walls, Cell[][] mazeMap) {
	//FILL IN THIS METHOD
    //should not return anything; the Cells and Walls in the input arrays should contain all
    //information needed to display the maze (which you can do by calling my print method).
    	
    // Maze Algorithm - 
    	// Must have 1 exit(Top Left) and 1 entrance(Top Right)
    	Random rand = new Random();
    	
        UnionFind union = new UnionFind();
        
        for(int i = 0; i<mazeMap.length; i++) {
        	for(int j = 0; j< mazeMap[i].length; j++) {
        		union.makeset(mazeMap[i][j]);
        	}
        }
        walls[0].visible = false;
        walls[walls.length - 1].visible = false;
    	 
        while(!oneGroup(mazeMap)) {
        	int rad = (int)(Math.random()*((double)(walls.length-1)));
        	while(walls[rad].first == null || walls[rad].second ==null) {
         		rad = (int)(Math.random()*((double)(walls.length-1)));
         	}
        	if(walls[rad].first != null || walls[rad].second != null) {
        		if(walls[rad].first.head !=  walls[rad].second.head) {
        			walls[rad].visible = false;
        		}
        		union.union(walls[rad].first, walls[rad].second);
        	}
        }
    }
    
    
// General Outline
    //1. while they are not part of the same set
    // write a method that returns a boolean on whether amze 'Works' 
    // Check that it is complete by seeing if all of the cells are part of the 
    // same set by the iterate
    //
    //2. go through the list of walls (the cells on either side are not null)
    // randomly pick a wall from the array - check if wall is vissible - if both cells on sides of wall exist and
    // don't belong to same set, then union
    // while check maze is false
    
    
    public int wallNum(Cell a) {
    	int b = 0;
    	if(a.down.visible == true) {
    		b++;
    	}if(a.up.visible == true) {
    		b++;
    	}if(a.left.visible == true) {
    		b++;
    	}if(a.right.visible == true) {
    		b++;
    	}
    	return b;
    }
    public boolean oneGroup(Cell[][] mazeMap) {
    	Cell a = mazeMap[0][0];
    	boolean isDone = false;
        for(int i = 0; i<mazeMap.length; i++) {
        	for(int j = 0; j< mazeMap[i].length; j++) {
        		if(mazeMap[i][j].head == a.head) {
        			isDone = true;
        		}else {
        			return false;
        		}
        	}
        }
		return isDone;
    }


    // print out the maze in a specific format
    public void printMaze(Cell[][] maze) {
	for(int i = 0; i < maze.length; i++) {
	    // print the up walls for row i
	    for(int j = 0; j < maze.length; j++) {
		Wall up = maze[i][j].up;
		if(up != null && up.visible) System.out.print("+--");
		else System.out.print("+  ");
	    }
	    System.out.println("+");

	    // print the left walls and the cells in row i
	    for(int j = 0; j < maze.length; j++) {
		Wall left = maze[i][j].left;
		if(left != null && left.visible) System.out.print("|  ");
		else System.out.print("   ");
	    }

	    //print the last wall on the far right of row i
	    Wall lastRight = maze[i][maze.length-1].right;
	    if(lastRight != null && lastRight.visible) System.out.println("|");
	    else System.out.println(" ");
	}

	// print the last row's down walls
	for(int i = 0; i < maze.length; i++) {
	    Wall down = maze[maze.length-1][i].down;
	    if(down != null && down.visible) System.out.print("+--");
	    else System.out.print("+  ");
	}
	System.out.println("+");


    }

    // create a new Cell for each position of the maze
    public void initializeCells(Cell[][] maze) {
	for(int i = 0; i < maze.length; i++) {
	    for(int j = 0; j < maze[0].length; j++) {
		maze[i][j] = new Cell();
	    }
	}
    }

    // create all walls and link walls and cells
    public Wall[] getWalls(Cell[][] mazeMap) {

	int n = mazeMap.length;

	Wall[] walls = new Wall[2*n*(n+1)];
	int wallCtr = 0;

	// each "inner" cell adds its right and down walls
	for(int i = 0; i < n; i++) {
	    for(int j = 0; j < n; j++) {
		// add down wall
		if(i < n-1) {
		    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i+1][j]);
		    mazeMap[i][j].down = walls[wallCtr];
		    mazeMap[i+1][j].up = walls[wallCtr];
		    wallCtr++;
		}
		
		// add right wall
		if(j < n-1) {
		    walls[wallCtr] = new Wall(mazeMap[i][j], mazeMap[i][j+1]);
		    mazeMap[i][j].right = walls[wallCtr];
		    mazeMap[i][j+1].left = walls[wallCtr];
		    wallCtr++;
		}
	    }
	}

	// "outer" cells add their outer walls
	for(int i = 0; i < n; i++) {
	    // add left walls for the first column
	    walls[wallCtr] = new Wall(null, mazeMap[i][0]);
	    mazeMap[i][0].left = walls[wallCtr];
	    wallCtr++;

	    // add up walls for the top row
	    walls[wallCtr] = new Wall(null, mazeMap[0][i]);
	    mazeMap[0][i].up = walls[wallCtr];
	    wallCtr++;

	    // add down walls for the bottom row
	    walls[wallCtr] = new Wall(null, mazeMap[n-1][i]);
	    mazeMap[n-1][i].down = walls[wallCtr];
	    wallCtr++;

	    // add right walls for the last column
	    walls[wallCtr] = new Wall(null, mazeMap[i][n-1]);
	    mazeMap[i][n-1].right = walls[wallCtr];
	    wallCtr++;
	}


	return walls;
    }


    public static void main(String [] args) {
	if(args.length > 0) {
	    int n = Integer.parseInt(args[0]);
	    new MazeGenerator().run(n);
	}
	else new MazeGenerator().run(10);
    }

}