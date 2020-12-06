public class PQUnsorted implements PriorityQueue {

    private Integer[] data;
    private int numElts;

    public void add(Integer toAdd) {
	if(numElts == data.length) resize();

	data[numElts] = toAdd;
	numElts++;
    }

    public Integer remove() {
	if(numElts == 0) return null;

	Integer highestPrioLoc = 0;
	for(int i = 1; i < numElts; i++) {
	    if(data[i] > data[highestPrioLoc]) {
		highestPrioLoc = i;
	    }
	}
	Integer toReturn = data[highestPrioLoc];
	numElts--;
	data[highestPrioLoc] = data[numElts];
	data[numElts] = null;

	return toReturn;
    }

    public int size() {
	return numElts;
    }

    public boolean isEmpty() {
	return numElts == 0;
    }

    private void resize() {
	Integer[] temp = new Integer[numElts * 2];
	for(int i = 0; i < numElts; i++) {
	    temp[i] = data[i];
	}
	data = temp;
    }

    public PQUnsorted() {
	data = new Integer[2];
	numElts = 0;
    }

}
