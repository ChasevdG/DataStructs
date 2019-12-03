package assignment;
import java.net.URL;

/**
 * The Page class holds anything that the QueryEngine returns to the server.
 * The field and method we provided here is the bare minimum requirement
 * to be a Page, feel free to add anything you want as long as you don't
 * break the getURL method.
 */
public class Page implements Comparable<Page>{
  private URL url;
  private int priority;

  public Page(URL url, int priority) {
    this.url = url;
    this.priority = priority;
  }

  public URL getURL() { return url; }
  public int getPriority() { return priority; }

@Override
public int compareTo(Page o) {
	if(priority < o.getPriority())
		return -1;
	if(priority > o.getPriority())
		return 1;
	return 0;
}
}
