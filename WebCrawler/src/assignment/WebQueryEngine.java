package assignment;
import java.net.URL;
import java.util.*;

import assignment.Parser2.Node;

public class WebQueryEngine {
    private WebIndex currentIndex;
	private boolean error;
	private HashSet<String> WordsSearched;
    public WebQueryEngine(){
    currentIndex = new WebIndex();
    }
    public WebQueryEngine(WebIndex i){
    	currentIndex = i;
    }
	/**
     * Returns a WebQueryEngine that uses the given Index to constructed answers to queries.
     *
     * @param index    The WebIndex this WebQueryEngine should use
     * @return         A WebQueryEngine ready to be queried
     */
    public static WebQueryEngine fromIndex(WebIndex index) {
      return new WebQueryEngine(index);
    }
    /**
     * Returns a Collection of URLs (as Strings) of web pages satisfying
     * the query expression.
     *
     * @param query    a query expression
     * @return    a Collection of web pages satisfying the query
     */
    public Collection<Page> query(String query) {
        if(currentIndex == null){
        	return new LinkedList<Page>();
        }
        WordsSearched = new HashSet<String>();
    	//For case uniformity
    	query = query.toLowerCase();
    	//Sends work to parser
    	Parser2 tree = new Parser2(query);
    	//Holds Pages found
        LinkedList<Page> pages = new LinkedList<Page>();
        //URLs that will need to be converted to the Page object
        //Sends work to interpretTree
        Set<URL> turnToPages = interpretTree(tree.getRoot());
        if(error ==true)
        	return new LinkedList<Page>();
        if(turnToPages != null){
        	//Converts to pages
        	for(URL url: turnToPages){
        		int priority = 1;
        		for(String s:WordsSearched){
        			
        			priority *= currentIndex.getAllURLSWithLocations(s, url)
        			.size()+1;
        		}
        		pages.add(new Page(url,priority));
        	}
        }
        //Sorts based on priority
        //Collections.sort(pages);
        //Collections.reverse(pages);
    	return pages;
    }
    //Turns query into logic
    public HashSet<URL> interpretTree(Node n){
    	if(n == null){
    		//System.out.println("Negation Failing");
    		return null;
    	}
    	//System.out.println(n.toString("", 0) + n.ifNegation());
    	if(n.ifNegation()){
    			HashSet<URL> all = currentIndex.getAllURLS();
    			//System.out.println("Negation ");
    			n.setToNotNegation();
    			HashSet<URL> temp = interpretTree(n);
    			if(temp==null)
    				return all;
    			all.removeAll(interpretTree(n));
    			//System.out.println("Success");
    			return all;
    	}
    	//returns intersection of left and right
    	if(n.getToken() instanceof AndToken || n.getToken() instanceof ImplicitAndToken){
    		HashSet<URL> a = interpretTree(n.getRight());
    		HashSet<URL> b = interpretTree(n.getLeft());
    		if(b== null)
    			return a;
    		if(a == null)
    			return b;
    		a.retainAll(b);
    		return a;
    	}
    	//returns union or left and right
    	if(n.getToken() instanceof OrToken){
    		HashSet<URL> a = interpretTree(n.getRight());
    		HashSet<URL> b = interpretTree(n.getLeft());
    		if(b== null)
    			return a;
    		if(a == null)
    			return b;
    		a.addAll(b);
    		return a;
    	}
    	if(n.getToken() instanceof PhraseToken){
    		//System.out.print(" CASH MONEY $$$$$");
    		return handleQuote(n);
    	}
    	//idk what to do
    	if(n.getToken() instanceof QuoteToken){
    		//System.out.print(" CASH MONEY $$$$$");
    		return handleQuote(n);
    	}
    	//may have pointer issues
    	if(n.getToken() instanceof WordToken){
    		//System.out.print("HI:");
    		HashSet<URL> u = currentIndex.getAllURLSWith(n.getToken().getValue());
    		//System.out.print("HI:");
    		WordsSearched.add(n.getToken().getValue());
    		return u;
    	}
    	//only happen if null
    	else {
    		System.err.println("Error: hi");
    		error = true;
    		return null;
    	}
    }
    //Uses the locations of the words to tell whether they are strung together
    public HashSet<URL> handleQuote(Node n){
    	ArrayList<String> a = turnToArrayList(n);
    	HashSet<URL> b = currentIndex.getAllURLSWith(a.get(0));
    	HashSet<URL> c;
    	for(String s: a){
    		c = currentIndex.getAllURLSWith(s);
    		if(c==null){
    			//System.out.print("WHYYYYY " + s + n.toString("", 0));
    			return null;
    		}
    		b.retainAll(c);
    	}
    		c = new HashSet<URL>();
    		//System.out.println("HELLO");
    		for(URL u: b){
    			//Checks every instance
    			for(Integer j: currentIndex.getAllURLSWithLocations(a.get(0),u)){
    				int z=0;
    				boolean fits = true;
    				//sees if for a location of Word0 location of word0 + N is an instance of WordN 
    				while(z<a.size()){
    					if(currentIndex.getAllURLSWithLocations(a.get(z),u).contains(j+z)){
    						z++;
    						continue;
    					}
    					//Otherwise it is not a solution
    					else{
    						fits = false;
    						break; //breaks while loop
    					}
    				}
    				if(fits){
    					c.add(u);
    					break; //breaks for loop to save time
    				}
    			}
    	}
    		return c;
    }
    public ArrayList<String> turnToArrayList(Node n){
    	ArrayList<String> b = new ArrayList<String>();
    	if(n.getRight()!=null){
    		 b = turnToArrayList(n.getRight());
    	}
		b.add(0, n.getLeft().getToken().getValue());
    	return b;
    	}
    	
}