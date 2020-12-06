
public class Queue {
	
	private static int start = 0;// this will be the index for the next item to exit the queue
	private static int end = 0;// this will be the index for the next open spot in the queue
	private static int[]ram = new int[5]; // this is the initial array in which the queue will be he
	
	public void enqueue(int a) {
		/*if(end => ram.length) {
		resize();
		}*/
		if(end+1 == ram.length) {
			resize();
		}
		ram[end] = a;
		end++;
	}
	public int dequeue() {
		if (size() == 0) {
			return 888888888;
		}
		int temp = ram[start];
		ram[start] = Integer.MAX_VALUE;
		if (start+1 >= ram.length) {
			start = 0;
		}
		else{
			start ++;
		}
		return temp;	
	}
	public static int peek() {
		return ram[start];
	}
	public static int size() {
	return end - start;
	}

	public static boolean isEmpty() {
		if(size() == 0) {
			return true;
		}
		else return false;
	}
	public static void resize() {
		int t = size();
		int[]temp = new int[ram.length*2];
		for(int i = 0; i<= t; i++) {
			temp[i] = ram[i+ start];
		}
		start = 0;
		ram = temp;
		end = t;
	}
	
	public static void main(String[]args) {
		
	}
}


