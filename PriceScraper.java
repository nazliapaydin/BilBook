import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;
//import java.util.stream.Collectors;

public class PriceScraper 
{
    public PriceScraper() {}

    public static float priceScrape(String bookTitle, String bookAuthor)
    {
        String isbn = PriceScraper.findIsbn(bookTitle,bookAuthor);
        System.out.println("ISBN: " + isbn);
        String url = "http://www.amazon.com/gp/search?index=books&linkCode=qs&keywords=" + isbn;

        // User-agent
        String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
        int maxRetries = 5;

        // Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", "en-US");
        headers.put("Accept-Encoding", "gzip,deflate,sdch");

        // Main call, cookies are fetched inside the method
        org.jsoup.nodes.Document doc = PriceScraper.goToPageAndGetDocument(url, userAgent, headers, maxRetries);
        String price = PriceScraper.getText(doc, "span.a-offscreen");

        if(price!=null)
        {
            price = price.substring(1);
            float parsedPrice = Float.parseFloat(price);
            return parsedPrice;
        }

        return -1;

    }

    /**
     * @param bookTitle
     * @param bookAuthor
     * @return isbn as a string
     */
    public static String findIsbn(String bookTitle, String bookAuthor) {
        String url = "https://www.amazon.com/s";
        String query = String.format("%s %s", bookTitle, bookAuthor);

        // User-agent
        String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
        int maxRetries = 5;

        // Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", "en-US");
        headers.put("Accept-Encoding", "gzip,deflate,sdch");

        // Main call, cookies are fetched inside the method
        Document doc = goToPageAndGetDocument(url + "?k=" + query, userAgent, headers, maxRetries);

        // Retrieve the first product link and extract the ISBN from the URL
        Element firstProductLink = doc.selectFirst("a.a-link-normal.a-text-normal");
        if (firstProductLink != null) {
            String productUrl = firstProductLink.attr("href");
            String isbn = extractIsbnFromUrl(productUrl);
            return isbn;
        }

        return null; // ISBN not found
    }

    /**
     * Method to be used in findIsbn
     * @param url
     * @return 
     */
    private static String extractIsbnFromUrl(String url) 
    {
        int start = url.indexOf("/dp/") + 4;
        int end = url.indexOf("/", start);
        if (end == -1) {
            return url.substring(start);
        } else {
            return url.substring(start, end);
        }
    }
    
    public static String getText(Element document, String selector) 
    {
        Elements elements = document.select(selector);
        if(elements.size() > 0)
            return elements.get(0).text().trim();
        else
            return "";
    }


    public static Document goToPageAndGetDocument(String link, String userAgent, Map<String, String> headers, int maxRetries ) {
        int failCounter = 0;
        if(maxRetries <= 0)
            maxRetries = 1;
        while (failCounter < maxRetries) {
            try{
				// first request to get initial cookies
                Connection.Response response = Jsoup.connect(link)
					.userAgent(userAgent) 
					.execute();
				
				// main request for data
                return Jsoup.connect(link)
					.userAgent(userAgent)
					//.header("Accept-Language", "en-US") 
					.headers(headers)
					.cookies(response.cookies())
					.get();
            }
            catch (Exception e) {
                e.printStackTrace();
                failCounter++;
            }
        }
		// return an empty document, an instance of the type Document. 
        return Jsoup.parse("<!DOCTYPE html><html><head><title></title></head><body></body></html>");
    }
}
