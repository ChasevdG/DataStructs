package assignment;

public class Token {
	protected String value;
	public String getValue(){
		return value;
	}
}
class AndToken extends Token{
	public AndToken(){
		value = "&";
	}
}
class ImplicitAndToken extends Token{
	public ImplicitAndToken(){
		value = "&";
	}
}
class OrToken extends Token{
	public OrToken(){
		value = "|";
	}
}
class OPToken extends Token{
	public OPToken(){
		value = "(";
	}
}
class CPToken extends Token{
	public CPToken(){
		value = ")";
	}
}
class WordToken extends Token{
	public WordToken(String s){
		value = s;
	}
}
class NegationToken extends Token{
	public NegationToken(){
		value = "!";
	}
}
class NegationWordToken extends Token{
	public NegationWordToken(String s){
		value = s;
	}
	public String getValue(){
		return "!not! " + value;
	}
}
class QuoteToken extends Token{
	public QuoteToken(){
		value = "\"";
	}
}
class PhraseToken extends Token{
	public PhraseToken(String s){
		value = "\"";
	}
}
class SpaceToken extends Token{
	public SpaceToken(){
		value = " ";
	}
}