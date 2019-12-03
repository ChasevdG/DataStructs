package assignment;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WebIndex extends Index{
    private static final long serialVersionUID = 1L;
    private HashMap<String, HashMap<URL,HashSet<Integer>>> a;
    private HashSet<URL> allURLs;
    public WebIndex(){
    	a = new HashMap<String, HashMap<URL,HashSet<Integer>>>();
    	allURLs = new HashSet<URL>();
    }
    public void add(String s, URL url, int wordNum){
    	s = s.toLowerCase();
    	
       	//Make new set if null
    	HashMap<URL,HashSet<Integer>> temp = a.get(s);
    	if(temp == null){
    		HashSet<Integer> b = new HashSet<Integer>();
    		b.add(wordNum);
    		HashMap<URL,HashSet<Integer>> c = new HashMap<URL,HashSet<Integer>>();
    		c.put(url, b);
    		a.put(s, c);
    	}
    	//adds to old set
    	else{
    		HashSet<Integer> temp2 = a.get(s).get(url);
        	if(temp2 == null){
        		HashSet<Integer> b = new HashSet<Integer>();
        		b.add(wordNum);
        		temp.put(url, b);
        	}
    		temp.get(url).add(wordNum);
    		a.put(s,temp);
    	}
    	allURLs.add(url);
    }
    public boolean contains(String s){
    	return a.containsKey(s);
    }
    
    public Set<String> getAllWords(){
    	if(!a.keySet().isEmpty()){
    		return a.keySet();
    	}
    	return null;
    }
    public HashSet<URL> getAllURLSWith(String s){
    	if(a.containsKey(s)){
    		//HashSet<URL> array = sort(a.get(s),s);
    		HashSet<URL> b = new HashSet<URL>();
    		b.addAll(a.get(s).keySet());
    		return b;
    	}
    	return null;
    }
    /*private HashSet<URL> sort(HashMap<URL,HashSet<Integer>> b, String s) {
    	HashSet<URL> array = new HashSet<URL>();
    	HashMap<Integer,HashSet<URL>> temp = new HashMap<Integer,HashSet<URL>>();
    	for(URL url: b.keySet()){
    		if(!temp.containsKey(b.get(url).size()))
    			temp.put(b.get(url).size(),new HashSet<URL>());
    		temp.get(b.get(url).size()).add(url);
		}
    	TreeMap<Integer, HashSet<URL>> map = new TreeMap<Integer,HashSet<URL>>(temp);
    	for(int i = 0; i<map.keySet().size();i++)
    		array.addAll(temp.get(map.get(map.keySet().size() - i)));
    	
    	return array;
	}
    */
	public HashSet<Integer> getAllURLSWithLocations(String s, URL url){
    	if(a.containsKey(s)){
    		HashSet<Integer> b = new HashSet<Integer>();
    		b = (a.get(s).get(url));
    		if(b == null)
    			b = new HashSet<Integer>();
    		return b;
    	}
    	return new HashSet<Integer>();
    }
    public HashSet<URL> getAllURLS(){
    	HashSet<URL> b = new HashSet<URL>();
		b.addAll(allURLs);
    	return b;
    }
}
