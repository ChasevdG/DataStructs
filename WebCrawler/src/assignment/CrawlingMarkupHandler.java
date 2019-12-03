package assignment;

import java.util.*;
import java.net.*;
import org.attoparser.simple.*;

public class CrawlingMarkupHandler extends AbstractSimpleMarkupHandler {
	private static WebIndex index;
	private URL currentURL;
	private static HashSet<URL> visitedURLs;
	private ArrayList<URL> nextURLs;
	private int wordCount;
	public CrawlingMarkupHandler() {
		wordCount = 0;
		visitedURLs = new HashSet<URL>();
		index = new WebIndex();
	}
	public CrawlingMarkupHandler(URL url) {
	  currentURL = url;
	  //visitedURLs.add(url);
	  nextURLs = new ArrayList<URL>();
  }

  /**
   * This method should return a completed Index after we've parsed things.
   */
  public Index getIndex() {
    return index;
  }

  /**
   * This method is to communicate any URLs we find back to the Crawler.
   */
  public List<URL> newURLs() {
    return nextURLs;
  }
  public HashSet<URL> visitedURLs() {
	    return visitedURLs;
	  }

  /**
   * These are some of the methods from AbstractSimpleMarkupHandler.
   * All of its method implementations are NoOps, so we've added some things
   * to do; please remove all the extra printing before you turn in your code.
   *
   * Note: each of these methods defines a line and col param, but you probably
   * don't need those values. You can look at the documentation for the
   * superclass to see all of the handler methods.
   */

  /**
   * Called when the parser first starts reading a document.
   * @param startTimeNanos  the current time (in nanoseconds) when parsing starts
   * @param line            the line of the document where parsing starts
   * @param col             the column of the document where parsing starts
   */
  public void handleDocumentStart(long startTimeNanos, int line, int col) {
    ////System.out.println("Start of document");
    /*while(hasNext){
    	String line = nextLine();
    	if('<'){
    		handleOpenElement(startTimeNanos,null, line+1);}
    	else
    		this.handleText(line, startTimeNanos, line.length(), line, col);;
    	}*/
    }
/**
 * Called for tags which are not closed.
 * @param elementName the element name (such as "img")
 * @param attributes  the element attributes map, or null if it has no attributes
 * @param minimized   true if this tag is minimized
 * @param line        the line in the document where this elements appears
 * @param col         the column in the document where this element appears
 */
public void handleStandaloneElement(String elementName,
    Map<String, String> attributes, boolean minimized, int line, int col) {
 ////System.out.println("Standalone element: " + elementName);
}
  /**
   * Called when the parser finishes reading a document.
   * @param endTimeNanos    the current time (in nanoseconds) when parsing ends
   * @param totalTimeNanos  the difference between current times at the start
   *                        and end of parsing
   * @param line            the line of the document where parsing ends
   * @param col             the column of the document where the parsing ends
   */
  public void handleDocumentEnd(long endTimeNanos, long totalTimeNanos, int line, int col) {
      visitedURLs.add(currentURL);
	  //////System.out.println("End of document");
  }

  /**
   * Called at the start of any tag.
   * @param elementName the element name (such as "div")
   * @param attributes  the element attributes map, or null if it has no attributes
   * @param line        the line in the document where this elements appears
   * @param col         the column in the document where this element appears
   */
  public void handleOpenElement(String elementName, Map<String, String> attributes, int line, int col) {
    //////System.out.println("Start element: " + elementName);
    if(attributes != null)
    	
    for(String v: attributes.keySet()){
    	////System.out.println("Start Attributes: key " + v + " value "+ attributes.get(v));
    	if(v.equals("HREF")||v.equals("href")){
    		try{
    			URL toBeAdded = new URL(attributes.get(v));
    			if(!visitedURLs.contains(toBeAdded))
    				nextURLs.add(toBeAdded);
    			return;
    		}catch (MalformedURLException e) {}
    		int z = currentURL.getPath().length();
    		
    		while(currentURL.getPath().charAt(z-1)!='/')
    			z--;
    		String nextFile = attributes.get(v);
    		int temp =nextFile.indexOf("#");
    		if(temp != -1){
    			nextFile = nextFile.substring(0,temp);
    		}
    		/*
    		if(nextFile.length()>3)
    		if(nextFile.substring(0,3).equals("../")){
    			//System.out.print("woohoo");
    			while(nextFile.substring(0,3).equals("../")){
    				z--;
    				while(currentURL.getPath().charAt(z-1)!='/')
    					z--;
    				nextFile = nextFile.substring(3);
    				if(nextFile.length()<3){
    					break;
    				}
    			}
    		*/
    		//////System.out.println(10);
    		//////System.out.println(currentURL.getPath().substring(0, z) +  attributes.get(v));
    		try {
    			URL toBeAdded = new URL(currentURL, nextFile);
    			if(!visitedURLs.contains(toBeAdded))
    				nextURLs.add(toBeAdded);
			} catch (MalformedURLException e) {
				System.err.println("INVALID URL");
			}
    	}
    }
  }
  
  public int getURLCount(){
	  return visitedURLs.size();
  }
  /**
   * Called at the end of any tag.
   * @param elementName the element name (such as "div").
   * @param line        the line in the document where this elements appears.
   * @param col         the column in the document where this element appears.
   */
 
  public void handleCloseElement(String elementName, int line, int col) {
    //////System.out.println("End element:   " + elementName);
  }

  /**
   * Called whenever characters are found inside a tag. Note that the parser is not
   * required to return all characters in the tag in a single chunk. Whitespace is
   * also returned as characters.
   * @param ch      buffer containint characters; do not modify this buffer
   * @param start   location of 1st character in ch
   * @param length  number of characters in ch
   */
  public void handleText(char ch[], int start, int length, int line, int col) {
    ////System.out.println("Word:    ");
    String url = "";
    for(int i = start; i < start + length; i++) {
      // Instead of printing raw whitespace, we're escaping it
      switch(ch[i]) {
        
      	//Gets rid of any punctuation
      	case '\\':{
      		if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
      		url = "";
      		break;}
        case '\'':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '!':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case ',':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '&':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '|':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '/':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }            
        	url = "";
            break;}
        case ':':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case ';':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '@':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '[':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case ']':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '*':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '%':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }            url = "";
            break;}
        case '^':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '{':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '}':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }            url = "";
            break;}
        case '~':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }            url = "";
            break;}
        case '`':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        case '_':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }  
        	url = "";
        	break;}
        case '=':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            } 
        	url = "";
            break;}
        case '+':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
            break;}
        case '>':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            } 
        	url = "";
        	break;}
        case '<':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            } 
        	url = "";
        	break;}
        case '?':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
        	url = "";
        	break;}
        
        case ')':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }  
        	url = "";
            break;
        }
        case '"':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
            url = "";
            break;}
        case '\n':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
            url = "";
            break;
        }
        case '\r':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
            url = "";
        	////System.out.print("\\r");
            break;
        }
        case '\t':{
          //////System.out.print("\\t");
          //url+="\\t";
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
            url = "";
            break;
        }
        case '.':{
            if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
            url = "";
            break;
          }
        case ' ':{
        	if(!url.equals("")){
            	wordCount++;
            	index.add(url.toLowerCase(), currentURL, wordCount);
            }
            url = "";
            break;
          }
        
        
        //If not punctuation 
        default:{
          url += Character.toLowerCase(ch[i]);
          break;
        }
      }
    }
    ////System.out.print("\"\n");
  }
 
}
