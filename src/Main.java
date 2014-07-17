
public class Main {
	public static void main(String[] args) {
		
		// sample usage of GoogleScraper object.
		int pageNum = 10;
		GoogleScraper gs = new GoogleScraper(pageNum);	
		String[] input = gs.getInput();
		for(String link : gs.getLinks(input))
			System.out.println(link);
	}
}