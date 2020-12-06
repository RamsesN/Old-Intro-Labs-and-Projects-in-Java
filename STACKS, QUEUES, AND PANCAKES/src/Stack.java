import java.util.Vector;

public class Stack<E> {

	private int end = 0;
	private Vector<E> Pedro = new Vector<E>();
	
	public void push(E a) {
		Pedro.add(a);
		end++;
		}
	public E pop() {
		if(!Pedro.isEmpty()) {
		end --;
		return Pedro.remove(end);	
		}
		else return null;
		}
	public E peek() {
		return Pedro.get(end-1);
	}
	public int size() {
		return end;
	}
	public boolean isEmpty() {
		if(end == 0) {
			return true;
		}
		else{
			return false;
		}
	}
	public void print() {	
		for(int i = 0; i < this.end; i++) {
			System.out.println(Pedro.get(i));
		}
		//System.out.println(Pedro.toString());
			
	}
}
