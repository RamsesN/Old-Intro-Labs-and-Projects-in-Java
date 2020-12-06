public class LLAddOnly {
// This is the group that a cell is in 
    Cell first;
    Cell last;

    public void add(Cell x) {
	if(first == null) {
	    first = x;
	    last = x;
	    x.head = this;
	}
	else {
	    x.next = first;
	    first = x;
	    x.head = this;
	}
    }

}