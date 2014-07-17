
public class Main {
	public static void main(String[] args) {
		
		// sample usage of GoogleScraper object. 
		GoogleScraper gs = new GoogleScraper();	
		String[] input = gs.getInput();
		for(String link : gs.getLinks(input))
			System.out.println(link);
	}
}