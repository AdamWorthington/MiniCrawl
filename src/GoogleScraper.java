import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// getLinks() is the main function of GoogleScraper-> flow of it is : 
// 1. users passes some input as String[]
// 2. make appropriate google search url with input
// 3. connect to the search url and get links. 

// sample usage ...
// GoogleScraper gs = new GoogleScraper();	
// String[] input = new Scanner(System.in).nextLine().split(" ");
// for (String link : gs.getLinks(input)) System.out.println(link);

public class GoogleScraper extends Scraper{
	// GoogleScraper object could be refactored to have input member (String[]) within constructor
	// .getInput() is redundant and .getSearchUrl + .getLinks will just access this.input etc.
	
	public String[] getInput() {
		return new Scanner(System.in).nextLine().split(" ");
	}
	
	// User input will be made to a proper google search url, helper to getLinks();
		// multiple search paramters are seperated by %20
		// %20 is url encoding escape sequence for spaces (" ")
	public String getSearchUrl(String[] input) {

		String query = input[0];
		if(input.length > 1) {
			for(int i = 1; i < input.length; i++)
				query = query + "%20" + input[i];
		}
		System.out.println("query = " + query);
		// TODO: make url for results past page 1
			//  https://www.google.com/search?as_q=dog&start=10 (example link for second page)
			// "https://www.google.com/search?as_q=" + query + "&start=" + (pageNum - 1) * 10
		return "https://www.google.com/search?as_q=" + query;
	}
	
	// Returns the actual url link from each search result. 
	public ArrayList<String> getLinks(String[] input) {
		
		// get the appropriate search url from user input... 
		String searchUrl = getSearchUrl(input); 		
		// connect....
		Document doc = connectToUrl(searchUrl);
		// now parse the search results page...
		ArrayList<String> links = new ArrayList<String>();
		Elements results = doc.select(".r a");
		for(Element result : results) {
			
			String base = result.toString();
			//<a href="/url?q=http://www.dogparkinlafayette.org/&amp;sa=U&amp;ei=tuzFU-3-FNKryATC8ILwDQ&amp;ved=0CEIQoAIwCg&amp;usg=AFQjCNEKnqeVHggoQYKr-0yjHqx9uipPVg">Shamrock Dog Park</a>
				// cut away unnecessary stuff. 
			if(base.indexOf("http") == -1) //links important to us start with http.
				continue;
			
			String encodedUrl = base.substring(base.indexOf("http"), base.indexOf('&'));
			// the received url is encoded with the escape sequences such as :
				// http://www.w3schools.com/tags/ref_urlencode.asp
			// To actually connect to these urls, we must decode them.
			String decodedUrl = "";
			try {
				decodedUrl = java.net.URLDecoder.decode(encodedUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) { e.printStackTrace();}
			links.add(decodedUrl);
		}
		return links;
	}
}
