package assignment;

import java.util.ArrayList;

public class TokenStack extends Token{
	ArrayList<Token> stack;
	public TokenStack(){
		stack = new ArrayList<Token>();
		value = "stack";
	}
	public void push(Token t){
	stack.add(0,t);
	}
	public Token pop(){
		if(stack.isEmpty()){
			return null;
		}
		Token t = stack.get(0);
		stack.remove(0);
		return t;
		}
	public boolean isEmpty(){
		if(stack.isEmpty())
			return true;
		else return false;
	}
}
