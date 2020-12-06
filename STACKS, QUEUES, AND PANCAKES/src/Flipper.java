
import java.lang.Math;

public class Flipper {

    // input: the number of pancakes
    public void run(int numPancakes) {
	Stack<Integer> s = new Stack<Integer>();
	initialize(s, numPancakes);
	//int pos = 0;
	flipAllPancakes(s);
    } 

    public void flipAllPancakes(Stack<Integer> pancakes) {
	// FILL IN THIS METHOD
    	if(isOrder(pancakes)) {
    		System.out.println("this2");
    		return;
    	}else {
    		System.out.println("this3");
    		flip(pancakes,findPos(pancakes));
    		System.out.println("this4");
    		int top = pancakes.peek();
    		flip(pancakes, top);
    		
    	}

    }

    public void flip(Stack<Integer> pancakes, int pos) {
	// FILL IN THIS METHOD
    	int t = 0;
    	Queue s = new Queue();
    	for( int i = 0; i>= pos; i++) {
    		System.out.println("this7");
    		t = (int) pancakes.pop();
    		s.enqueue(t);
    	}
    	for(int i = 0; i>= pos; i++) {
    		// use a queue at or before this point in doing the flipping
    		pancakes.push(s.dequeue());
    	}
    }
    public static int findPos(Stack<Integer> pancakes) {
    	//Stack<Integer> fill = new Stack<Integer>();
    	//fill = pancakes;
    	int t = pancakes.size();
    	int max = 0;
    	int pos = 0;
    	for(int i = 0; i>=t; i++) {
    		int a = (int) pancakes.pop();
    		if(max < a && (t-a != i)) {
    			max = a;
    			pos = i;
    			System.out.println("this");
    		}		
    	}
    	//pancakes = fill;
    	return pos;
    }
    public static boolean isOrder(Stack<Integer> pancakes){
    			//int t = pancakes.size();
    			for(int i = 0; i<pancakes.size(); i++) {
    				if(pancakes.pop() - i != 1) {
    					return false;
    				}
    			}
    			return true;
    }
    // fill in the Stack with the specified number of pancakes in a random order
    public void initialize(Stack<Integer> s, int n) {
	// create an array with the desired number of elements,
	// then initialize it with pancake sizes 1...n
	int[] order = new int[n];
	for(int i = 0; i < order.length; i++) {
	    order[i] = i + 1;
	}
	// randomly permute the order of the pancakes
	shuffle(order);

	// add all of the pancakes to the stack
	for(int i = 0; i < n; i++) {
	    s.push(order[i]);
	}
    }

    // randomly permute the elements in the input array
    public void shuffle(int[] toShuffle) {
	for(int i = toShuffle.length - 1; i > 0; i--) {
	    int which = (int)(Math.random() * (i+1));
	    int temp = toShuffle[i];
	    toShuffle[i] = toShuffle[which];
	    toShuffle[which] = temp;
	}
    }

    // command line input: number of pancakes in the original pile
    public static void main(String [] args) {
	if(args.length > 0) new Flipper().run(Integer.parseInt(args[0]));
	else new Flipper().run(5); // default is 5 pancakes
    }
    

}