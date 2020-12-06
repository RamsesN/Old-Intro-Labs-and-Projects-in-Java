
	class Node<T>{
		T elem;
		Node<T> next;
		public Node(T elem) {
			this.elem = elem;
		}
	}
public class MyGenericDS<E> implements GenericOrderedCollection<E>{
	Node<E>end = null;
	int numNodes = 0;
	public void append(E toAppend) {
		Node<E> toAdd = new Node<E>(toAppend);
		toAdd.next = end;
		end = toAdd;
		this.numNodes ++ ;
	}
	public E peek() {
		return end.elem;
	}
	public E pop() {
		E toReturn = end.elem;
		end = end.next;
		this.numNodes --;
		return toReturn;
	}
	public void remove(int index) {
		Node<E> temp = end;
		for( int i = this.numNodes ; i > index+2 ; i--) {
			temp = temp.next;
		}
		temp.next = temp.next.next;
	}
	public int length() {
		return this.numNodes;
	}
  // your code here
 }
