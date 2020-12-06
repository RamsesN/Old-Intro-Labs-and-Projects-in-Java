
public class PQHeap implements PriorityQueue{
    private Integer[] data;
    private Integer arrLe = data.length;
    private int numElts;
	
    public void add(Integer toAdd) {
    	if(numElts == data.length) {
    		resize();
    	}
    	int addInd = data.length - 1;
    	data[addInd] = toAdd; 
    	numElts ++;
    	while(data[addInd]> data[findPar(addInd)]) {
    		int temp = findPar(addInd);
    		siftUp(addInd,findPar(addInd));
    		addInd = temp;
    	}
    	
    }
    public void siftUp(int i, int t) {
    	//takes 2 array indexes and switches their values
    	int temp = data[i];
    	data[i] = data[t];
    	data[t] = temp;
    }
    public int findPar(int i) {
    		return (i-1)/2;
    }

    public Integer remove() {
    	numElts--;
    	return data[0];
    	// I need to find which child is larger and then
    	// run a siftUp algorithm to move
    }
    public Integer siftDown() {
    	//since the top is always the one being removed, we do not
    	// need to take in an array index... it is returning the value that will be moved up
    	// I am choosing to make this recursive because I think that
    	// will add to simplicity and may optimize run time
    	int temp = siftDown();
    	
    }

    public int size() {
    	return numElts;
    }
    
    public boolean isEmpty() {
    	return numElts==0;
    }
    
    public int p(int r) {
    	return ((r-1)/2);
    }
    private void resize() {
	Integer[] temp = new Integer[numElts * 2];
	for(int i = 0; i < numElts; i++) {
	    temp[i] = data[i];
	}
	data = temp;
    }


}
