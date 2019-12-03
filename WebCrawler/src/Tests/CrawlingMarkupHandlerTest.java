package assignment.Tests;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.Test;

import assignment.WebIndex;
import assignment.WebQueryEngine;

public class CrawlingMarkupHandlerTest {

	@Test
	public void QueryTest() {
		GameDictionary dict = new GameDictionary("words.txt");
		WebIndex index = new WebIndex();
		
		try {
			index.load("index.db");
			WebQueryEngine wqe = new WebQueryEngine(index);
			DictionaryIterator it = (DictionaryIterator)dict.iterator();
			String AndString = "a";
			String OrString = "a";
			String PhraseString = "";
			String PhraseString2 = "";
			String previous = "a";
			int all = index.getAllURLS().size();
			while(it.hasNext()){
				String a = it.next();
				System.out.println(a);
				AndString = "(" + AndString + "&" + a + ")";
				OrString = "(" + OrString + "|" + a + ")";
				String AndString2 = "(" + previous + "&" + a + ")";
				String OrString2 = "(" + previous + "|" + a + ")";
				String PhraseString3 = previous + " " + a;
				PhraseString2 = PhraseString + " " + a;
				PhraseString = "\"" + PhraseString2 + "\"";
				wqe.query(PhraseString);
				
				assertEquals(wqe.query(PhraseString), wqe.query(AndString));
				assertEquals(wqe.query(PhraseString3), wqe.query(AndString2));
				assertTrue(wqe.query(OrString).size()>=wqe.query(AndString).size());
				assertTrue(wqe.query(OrString2).size()>=wqe.query(AndString2).size());
				assertEquals(0, wqe.query(a + " !" + a).size());
				assertEquals(all, (wqe.query("("+a + "|!" + a + ")").size()));
				assertEquals(0, (wqe.query("("+a + "&!" + a + ")").size()));
				previous = a;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
