package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * A class to scrape available online prices / web scraping.
 * Author: Nazlı Apaydın
 */

public class PriceScraper {
    /**
     * @param bookTitle
     * @param bookAuthor
     * @param yearPublished
     * @return isbn as a String if it is found, null otherwise
     * @throws IOException
     */
    private static String findISBN(String bookTitle, String bookAuthor, int yearPublished) throws IOException 
    {
        String title = bookTitle;
        String author = bookAuthor;
        int year = yearPublished;

        String encodedTitle = URLEncoder.encode(title, "UTF-8");
        String encodedAuthor = URLEncoder.encode(author, "UTF-8");
        String query = "intitle:" + encodedTitle + "+inauthor:" + encodedAuthor + "+year:" + year;

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=" + query);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) 
        {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) 
            {
                content.append(inputLine);
            }

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
                            return identifier.getString("identifier");
                        }
                    }
                }
            }
        } 
        catch (JSONException e) {}

        return null;
    }

    /**
     * @param bookTitle
     * @param bookAuthor
     * @param yearPublished
     * @return price as a float if successful, -1 if unsuccessful
     * @throws IOException
     */
    public static float priceScrape(String bookTitle, String bookAuthor, int yearPublished)
    {
        String isbn;
        try 
        {
            isbn = PriceScraper.findISBN(bookTitle, bookAuthor, yearPublished);
        } catch (IOException e) 
        {
            isbn = null;
        }

        if (isbn != null) 
        {
            String url = "https://www.amazon.com/s?i=stripbooks&rh=p_66%3A" + isbn;
            try 
            {
                Document document = Jsoup.connect(url).timeout(5000).get();
                Elements priceElements = document.select("span.a-offscreen");

                if (!priceElements.isEmpty()) 
                {
                    Element priceElement = priceElements.first();
                    String price = priceElement.text();
                    float parsedPrice = Float.parseFloat(price.replaceAll("[^\\d.]", ""));
                    return parsedPrice;
                } 
                else 
                {
                    return -1;
                }
            } 
            catch (IOException e) 
            {
                return -1;
            }
        }

        return -1;
    }
}

    

    /**
     * Finds the price of a book using the Google Books API based on the ISBN.
     * 
     * @param isbn the ISBN of the book
     * @return the price of the book as a float if found, or -1 otherwise
     * @throws Exception if an error occurs during the API request or JSON parsing
    public static float findPriceFromAPI(String bookTitle, String bookAuthor, int yearPublished) throws Exception 
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
                        //String currency = listPrice.getString("currencyCode");
                        return (float) price;
                    }
                }
            }
        }        

        return -1;
    }*/