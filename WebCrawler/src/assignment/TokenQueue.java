package assignment;

import java.util.ArrayList;

public class TokenQueue extends TokenStack {
	ArrayList<Token> queue;
	public TokenQueue(){
		queue = new ArrayList<Token>();
		value = "stack";
	}
	public void push(Token t){
	queue.add(t);
	}
	public ArrayList getList(){
		return queue;
		}
	public Token dequeue(){
		if(queue.isEmpty())
			return null;
		Token t =queue.get(0);
		queue.remove(0);
		return t;
		}
	public boolean isEmpty(){
		if(queue.isEmpty())
			return true;
		else return false;
	}
}
