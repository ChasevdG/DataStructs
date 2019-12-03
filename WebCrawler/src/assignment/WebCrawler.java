package assignment;

import java.io.*;
import java.net.*;
import java.util.*;

import org.attoparser.simple.*;
import org.attoparser.ParseException;
import org.attoparser.config.ParseConfiguration;

public class WebCrawler {

  /**
   * The WebCrawler's main method starts crawling a set of pages.
   * You can change this method as you see fit, as long as it takes
   * URLs as inputs and saves an Index at "index.db".
   */
  public static void main(String[] args) {
    // Basic usage information
    if (args.length == 0) {
      System.out.println("No URLs specified.");
      System.exit(1);
    }
    // We'll throw all of the args into a list
    List<URL> remaining = new LinkedList<>();
    HashSet<URL> addedURLs = new HashSet<URL>();
    for (String url : args) {
      try {
        if(!addedURLs.contains(url))
        	remaining.add(new URL(url));
        addedURLs.add(new URL(url));
      } catch (MalformedURLException e) {
        // Throw this one out
      }
    }

    // Create a parser from the attoparser library
    ISimpleMarkupParser parser = new SimpleMarkupParser(
        ParseConfiguration.htmlConfiguration());

    // We're using the handler we've defined
    CrawlingMarkupHandler handler = new CrawlingMarkupHandler();
    
    	int z=0;
    	int x=0;
      while (!remaining.isEmpty()) {
    	  // Parse the next URL's page
    	  URL next = remaining.remove(0);
    	  handler = new CrawlingMarkupHandler(next);
    	  //System.out.println(next);
    	  try {
			parser.parse(new InputStreamReader(
			  next.openStream()), handler);
			System.out.println(next);
			//System.out.println(x++);
		} catch (ParseException e) {
			//System.err.println("Invalid URL");
			//e.printStackTrace();
			} catch (IOException e) {
			//System.err.println("Invalid URL");
			//e.printStackTrace();
		}
    catch (IllegalArgumentException e) {
			//System.err.println("Invalid URL");
		}
    	  WebIndex index2 = (WebIndex)handler.getIndex();
    	  //System.out.println(index2.getAllURLS().size());
        // Add any new URLs
        for(URL u:handler.newURLs())
        	if(!addedURLs.contains(u)){
        		addedURLs.add(u);
        		remaining.add(u);
        	}
      }
      WebIndex index2 = (WebIndex)handler.getIndex();
      //System.out.println(index2.getAllURLS());
      //for(URL url:handler.visitedURLs())
    	  //System.out.println(url);
      HashSet<URL> URLdif = index2.getAllURLS();
      URLdif.removeAll(handler.visitedURLs());
      //System.out.println(URLdif);
      //System.out.print("#Valid URLs" + " " + index2.getAllURLS().size());
      WebIndex index = (WebIndex)handler.getIndex();
     
      try {
		handler.getIndex().save("index.db");
	} catch (IOException e) {
		//System.err.println(e.getStackTrace());
	}
  }
}
