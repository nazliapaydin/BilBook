package src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A class to scrape available online prices / web scraping.
 * Author: Nazlı Apaydın
 */

public class PriceScraper 
{
    /**
     * @param bookTitle
     * @param bookAuthor
     * @param yearPublished
     * @return isbn as a String if it is found
     * @throws Exception
     */
    private static String findISBN (String bookTitle, String bookAuthor, int yearPublished) throws Exception
    {
        String title = bookTitle;
        String author = bookAuthor;
        int year = yearPublished;

        String encodedTitle = URLEncoder.encode(title, "UTF-8");
        String encodedAuthor = URLEncoder.encode(author, "UTF-8");
        String query = "intitle:" + encodedTitle + "+inauthor:" + encodedAuthor + "+year:" + year;

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + query);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
        {
            content.append(inputLine);
        }            
        in.close();

        JSONObject json = new JSONObject(content.toString());

        if (json.has("items")) 
        {
            JSONArray items = json.getJSONArray("items");
            if (items.length() > 0) 
            {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
                if (volumeInfo.has("industryIdentifiers")) 
                {
                    JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
                    if (industryIdentifiers.length() > 0) 
                    {
                        JSONObject identifier = industryIdentifiers.getJSONObject(0);
                        String isbn = identifier.getString("identifier");
                        System.out.print(isbn);
                        return isbn;
                    }
                }
            }
        }
        System.out.println("ISBN not found!");
        return "Isbn not found";
    }


    /**
     * @param bookTitle
     * @param bookAuthor
     * @param yearPublished
     * @return return null if unsuccessful, returns price as a String if successful
     * @throws Exception
     */
    public static String priceScrape(String bookTitle, String bookAuthor, int yearPublished) throws Exception
    {
        String isbn = PriceScraper.findISBN(bookTitle, bookAuthor, yearPublished);
        String url = "https://www.amazon.com/s?i=stripbooks&rh=p_66%3A" + isbn;
        boolean success = false;
        Document document = null;
        try 
        {
            document = Jsoup.parse(new URL(url), 5000);
            success = true;
        } 
        catch (IOException e) {}

        if (success && document != null) 
        {
            Elements priceElements = document.select("span.a-offscreen");

            if (!priceElements.isEmpty()) 
            {
                Element priceElement = priceElements.first();
                String price = priceElement.text();
                return price;
            } 
            else 
            {
                return null;
            }
        } 
        else 
        {
            return null;
        }

    }

    /**
     * Finds the price of a book using the Google Books API based on the ISBN.
     * 
     * @param isbn the ISBN of the book
     * @return the price of the book as a String if found, or null otherwise
     * @throws Exception if an error occurs during the API request or JSON parsing
     */
    public static String findPriceFromAPI(String bookTitle, String bookAuthor, int yearPublished) throws Exception 
    {
        if(PriceScraper.findISBN(bookTitle, bookAuthor, yearPublished) != null)
        {
            String isbn = PriceScraper.findISBN(bookTitle, bookAuthor, yearPublished);
            URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) 
            {
                content.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(content.toString());

            if (json.has("items")) 
            {
                JSONArray items = json.getJSONArray("items");
                if (items.length() > 0) {
                    JSONObject saleInfo = items.getJSONObject(0).getJSONObject("saleInfo");
                    if (saleInfo.has("listPrice")) {
                        JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                        double price = listPrice.getDouble("amount");
                        String currency = listPrice.getString("currencyCode");
                        return currency + price;
                    }
                }
            }
        }
        

        return null;
    }

}