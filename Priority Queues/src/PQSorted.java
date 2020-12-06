
public class PQSorted implements PriorityQueue {

    Integer[] data;
    int numElts;

    public int size() {
	return numElts;
    }

    public boolean isEmpty() {
	return numElts == 0;
    }

    public Integer remove() {
	if(numElts == 0) return null;

	numElts--;
	Integer toReturn = data[numElts];
	data[numElts] = null;
	     
	return toReturn;
    }

    public void add(Integer toAdd) {
	if(numElts == data.length) resize();
	
	int i = numElts - 1;
	while(i >= 0 && data[i] > toAdd) {
	    data[i+1] = data[i];
	    i--;
	}
	data[i+1] = toAdd;
	numElts++;
    }

    private void resize() {
	Integer[] temp = new Integer[numElts * 2];

	for(int i = 0; i < numElts; i++) {
	    temp[i] = data[i];
	}
	data = temp;
    }

    public PQSorted() {
	data = new Integer[2];
	numElts = 0;
    }

}