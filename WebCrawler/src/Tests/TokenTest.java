package assignment;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import org.junit.Test;

public class TokenTest {

	@Test
	public void test() {
		Scanner input = new Scanner(System.in);
		Parser2 p = new Parser2("(masturbate | hitler)");
		System.out.println(p.printTree());
		WebIndex index = new WebIndex();
		System.out.println("Loading");
		try {
			index = (WebIndex) index.load("index.db");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(new HashSet().size());
		System.out.println("Load successful");
		WebQueryEngine engine = new WebQueryEngine(index);
		/*System.out.println(index);
		for(String s: index.getAllWords())
			//System.out.println(s);
		for(Page q:engine.query("\"blow job\""))
			//System.out.println(q.getURL());
		try {
			System.out.print(index.getAllURLSWithLocations("blow", new URL("file:/C:/Users/chase/Downloads/prog7/rhf/rhf/www.netfunny.com/rhf/jokes/91q4/stuckpig.html")));
			//System.out.print(index.getAllURLSWithLocations("up", new URL("file:/C:/Users/chase/Downloads/prog7/rhf/rhf/www.netfunny.com/rhf/jokes/91q4/stuckpig.html")));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//assertEquals("(",);
		//t = p.GetToken("acds)(");
		//assertEquals("acds",t.getValue());
		//t = p.GetToken("a");
		//assertEquals("a",t.getValue());
	}

}
