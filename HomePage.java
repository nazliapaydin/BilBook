
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Formatter.BigDecimalLayoutForm;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class HomePage extends JPanel{
    
    BilBook bilbook;
    ArrayList<Product> products;
    ScrollPane scrollPane;

    HomePage(BilBook bilbook){
        this.bilbook = bilbook;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JPanel searchMenu = bilbook.createSortPanel(false);
        add(searchMenu);
        products = bilbook.getProducts();
        scrollPane = new ScrollPane();
    }

    public void sortBooks(boolean showBooks, boolean showNotes, String courseDepartment, int courseCode, String searchBar, boolean showOnlyFavourites, boolean dontShowSold, String sortMethod){
        ArrayList<Product> productsToBeShown = new ArrayList<>();
        for(Product product : products) {
            if(product.willBeShown(showBooks, showNotes, courseDepartment, courseCode, searchBar, showOnlyFavourites, dontShowSold, bilbook.getLoggedIn())){
                productsToBeShown.add(product);
            }
        }
        if(sortMethod.equals("Price ▲")) {
            Product.sort(productsToBeShown, Product.PRICE_ASCENDING);
        } else if(sortMethod.equals("Price ▼")) {
            Product.sort(productsToBeShown, Product.PRICE_DESCENDING);
        } else if(sortMethod.equals("Date ▲")) {
            Product.sort(productsToBeShown, Product.DATE_ASCENDING);
        } else if(sortMethod.equals("Date ▼")) {
            Product.sort(productsToBeShown, Product.DATE_DESCENDING);
        } else if(sortMethod.equals("Profit %")) {
            Product.sort(productsToBeShown, Product.INTERNETPRICE_PRICE_RATIO);
        } else if(sortMethod.equals("Random%")) {
            Product.sort(productsToBeShown, Product.RANDOM);
        } 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        for (Product product : productsToBeShown) {
            panel.add(product.createPanel(false, bilbook.getLoggedIn(), bilbook));
        }
        scrollPane.removeAll();
        scrollPane.add(panel);

    }

}
