
public class UnionFind {

	public void makeset(Cell a){
		// Cell as input and creates a new set containing
		//one element, namely the input Cell.
		
		LLAddOnly W = new LLAddOnly();
		W.first= a;
		W.last = a;
		a.head = W;
		

	}
	public LLAddOnly find(Cell a) {
		//returns a LLAddOnly that is
		//the header of the set containing the input Cell
		return a.head;
	}
	public void union(Cell a, Cell b) {
		//joins together the
		//sets containing those Cells
		if(a.head!=b.head) {
			// This next line finds the LLadd(group) and sets the
			// last node in that group to the first node in the joining group
			a.head.last.next=b.head.first;
			// This points group A last to and makes it
			// Group B last
			a.head.last = b.head.last;
			Cell holder = b.head.first;
			holder.head = a.head;
			while(holder.next!= null) {
				holder = holder.next;
				holder.head = a.head;
			}
			
		}
	}
}
