package assignment;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashSet;

public class Parser2 {
	private int queryLength;
	private int current;
	private TokenQueue sections;
	private int parens;
	private boolean openQuote;
	private Node rootNode;
	private boolean error;
	HashSet<String> SkipWords;
	//Creates a tree of operations out of the query
	public Parser2(String query){
		SkipWords = new HashSet<String>();
		openQuote = false;
		error = false;
		loadSkipWords();
		queryLength = query.length();
		current = 0;
		parens = 0;
		sections = new TokenQueue();
		//loops for every word
		while(current<queryLength){
			Token tok = GetToken(query.substring(current));
			sections.push(tok);
			}
		//Odd number of Quotes is invalid
		if(openQuote == true){
			error = true;
		}
		//loops for every token
		while(!sections.isEmpty())
			parseTree();
		}
	public TokenQueue getList(){
		return sections;
	}
	public Node getRoot(){
		if(error == true)
			return null;
		return rootNode;
	}
	public void loadSkipWords(){
		SkipWords.add("<");
		SkipWords.add(">");
		SkipWords.add(",");
		SkipWords.add(".");
		SkipWords.add("/");
		SkipWords.add(";");
		SkipWords.add(":");
		SkipWords.add("{");
		SkipWords.add("}");
		SkipWords.add("[");
		SkipWords.add("]");
		SkipWords.add("\\");
		SkipWords.add("?");
		SkipWords.add("+");
		SkipWords.add("=");
		SkipWords.add("_");
		SkipWords.add("*");
		SkipWords.add("&");
		SkipWords.add("^");
		SkipWords.add("%");
		SkipWords.add("#");
		SkipWords.add("@");
		SkipWords.add("!");
		SkipWords.add("~");
		SkipWords.add("`");
	}
	public String toString()
	{
		String s = "";
		ArrayList<Token> y = sections.getList();
		for(Token t: y){
			s += "["+ t.getValue() + "] ";
		}
		return s;
	}
	public Token GetToken(String stream)
	{
		StringCharacterIterator iterator = new StringCharacterIterator(stream);
		String c = ""+stream.charAt(0);
		char next;
		current++;
		String wordToken = "";
		if (c.equals("&")){
			if(parens == 0){
				System.err.println("Invalid And In Query");
				error = true;
				sections = new TokenQueue();
				return null;
			}
			return new AndToken();
		}
		else if (c.equals("|")){
			if(parens == 0){
				System.err.println("Invalid Or In Query");
				error = true;
				sections = new TokenQueue();
				return null;
			}
			return  new OrToken();
	}
		else if (c.equals("(")){
			parens++;
			return new OPToken();
		}
		else if (c.equals(")")){
			parens--;
			if(parens<0)
				error = true;
			return new CPToken();
		}
		else if (c.equals("!")){
			return new NegationToken();
			
		}
		else if (c.equals("\"" )){
			openQuote= !openQuote;
			//System.out.print("hi");
			return new QuoteToken();
		}
		else if (c.equals(" ")){
			if(parens == 0 && !openQuote)
				return new ImplicitAndToken();
			return new SpaceToken();
		}
		else if (SkipWords.contains(c)){
			return new SpaceToken();
		}
		else{
			wordToken+=c;
			next = iterator.next();
			//builds a String
			while(current<queryLength){
				String nextString = ""+ next;
				if ((nextString).equals("&")){
					break;
				}
				else if (nextString.equals("|")){
					break;
				}
				else if ((nextString ).equals("(")){
					break;
				}
				else if ((nextString ).equals(")")){
					break;
				}
				else if ((nextString ).equals(" ")){
					break;
				}
				else if ((nextString).equals("\"")){
					break;
				}
				else if ((nextString).equals("!")){
					break;
				}
				if(SkipWords.contains(nextString))
					break;
				
				wordToken += next;
				next = iterator.next();
				current++;
		}
			return new WordToken(wordToken);
			
		}
	}
	//Prints a string representation
	public String printTree(){
		if(rootNode == null)
			return "Empty Tree";
		String s = rootNode.toString("", 0);
		return s;
	}
	//Creates a tree from the query
	//Uses a queue to get info
	public Node parseTree()
	{
		Token t = sections.dequeue();
		if (t instanceof SpaceToken){
			return parseTree();
		}
		if (t instanceof OPToken){
			Node left = parseTree(); // recursively build the left subtree
			Token op = sections.dequeue();// get the binary operator: AND or OR
			if(op instanceof SpaceToken) // skips Spaces
			{
				op = sections.dequeue();
			}
			//If an op is not in the second space
			if(!(op instanceof AndToken || op instanceof OrToken))
			{
				System.err.println("No Op in Parenthesis");
				error= true;
				sections = new TokenQueue();
				return null;
			}
			Node right = parseTree(); // recursively build the right subtree
			//Error if there is no closed parens
			Token tok = sections.dequeue();
			while(tok instanceof SpaceToken){
				tok = sections.dequeue();
			}
			if(!(tok instanceof CPToken)){
				System.err.println("Closed Parenthesis Expected");
				error = true;
				sections = new TokenQueue();
				return null;
			}
			rootNode = new Node(op, left, right);
			return new Node(op, left, right);
		}
		else if (t instanceof NegationToken){
			
			Node nextNode = parseTree(); //Gets next query
			//Case: ! is the last character 
			if(nextNode == null){
				error = true;
				sections = new TokenQueue();
				return null;
			}
			nextNode.setToNegation();
				rootNode = nextNode;
			return nextNode; // Negation Node
			
		}
		else if (t instanceof QuoteToken){
				Node n = handlePhrase();
				return n;
			}
		else if(t instanceof WordToken){
			if(rootNode == null)
				rootNode = new Node(t);
			return new Node(t);
		}
		else if(t instanceof ImplicitAndToken){
			if(rootNode == null)
				return parseTree(); 
			Node impAnd= new Node(t,rootNode,parseTree());
			rootNode = impAnd;
			return impAnd;
		}	
		else{
			System.err.println("Invalid Query");
			error = true;
			sections = new TokenQueue();
			return null;
		}
	}
	
	public Node handlePhrase(){
		Token t = sections.dequeue();
		if(t instanceof QuoteToken){
			return null;
		}
		Node left;
		if(t instanceof WordToken){
			left = new Node(t);
		}
		else if(t instanceof SpaceToken){
			return handlePhrase();
			}
		else{
			System.err.println("Invalid Phrase1 Query");
			error = true;
			sections = new TokenQueue();
			return null;
		}
		Node right = handlePhrase(); // recursively build the right subtree
		PhraseToken token;
		if(right != null){
			token = new PhraseToken("Phrase: " + left.toString() + " " + right.toString());
		}
		else
			token = new PhraseToken("");
		rootNode = new Node(token, left, right);
		return new Node(token, left, right);
	}
class Node{
	private Node left;
	private Node right;
	private Token t;
	private boolean negation;
	public Node(Token tok){
		t = tok;
	}
	public Node(Token tok, Node l,Node r){
		t = tok;
		left = l;
		right = r;
	}
	public Token getToken(){
		return t;
	}
	public Node getRight(){
		return right;
	}
	public Node getLeft(){
		return left;
	}
	public void setToNegation(){
		negation = !negation;
	}
	public void setToNotNegation(){
		negation = false;
	}
	public boolean ifNegation(){
		return negation;
	}
	public String toString(String y,int x){
		for(int i=0; i<x; i++){
			y+= "	";
		}
		y+= t.getValue();
		if( left != null ){
			y = left.toString(y+"\nL",x+1);
		}
		if( right != null ){
			y = right.toString(y+"\nR",x+1);
		}
		return y;
	}
}
}

