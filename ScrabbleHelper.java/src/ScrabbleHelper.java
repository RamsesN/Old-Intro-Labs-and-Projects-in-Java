import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.util.HashMap;

public class ScrabbleHelper {
	private HashMap<String, Vector<String>> hash = new HashMap<String, Vector<String>>();

	public void add(String word) {
		String alphaO = AO(word);
		if (hash.get(alphaO) == null) {
			Vector<String> temp =  new Vector<String>();
			temp.add(word);
			hash.put(alphaO,temp);
		}
		else {
			Vector<String> temp = hash.get(alphaO);
			temp.add(word);
		}
	}
	public String AO(String Key) {
		
		char[] Carr = Key.toCharArray();
		Arrays.sort(Carr);
		return Carr.toString();	
	}
	
	public Vector<String> hash(String Key){
		return hash.get(Key);
	}

    public static void main(String [] args) {
    
    ScrabbleHelper theHelp = new ScrabbleHelper();
    
    	try {
    		File dict = new File("myDictionary.txt");
			Scanner sc = new Scanner(dict);
			String dicWord;
	    	
	    	while(sc.hasNext()) {
	    		dicWord = sc.next();
	    		theHelp.add(dicWord);
	    	}
	    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Boolean cont = true;
        String word;
        String t = "no";
    	while(cont) {
        static Scanner kb = new Scanner(System.in);
    	System.out.println("Enter your letters:");
    	word = kb.next();
    	System.out.println(theHelp.hash(theHelp.AO(word)).toString());
    	// entered to the dictionary within the hash map and create anagrams
    	// Then ask the user if that would like to continue
    	System.out.println("Another?");

    	if(t.equals(kb.next())){
    		cont = true;
    	}else {
    		cont = false;
    	}
    	} 

    	}
}
