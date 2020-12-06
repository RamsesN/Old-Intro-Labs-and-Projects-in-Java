import OrderedCollection.OrderedCollection;

public class MyDS implements OrderedCollection{
	Node end;

	class Node{
		Node next;
		String[] lvl;
		public Node(String[] toAppend){
			this.lvl = toAppend;
		}
	}

	public MyDS(){
		end = null;
	}	
	
    public void append(String[] toAppend){
    	Node toAdd = new Node(toAppend);
    	toAdd.next = end;
    	end = toAdd;
    }
   public String[] peek() {
	   return end.lvl;
   }
    public int length(){
    	Node n = end;
    	int count = 0;
    	while (n != null){
     		count++;
     		n = n.next;
     	}
    	return count;
    }
} 