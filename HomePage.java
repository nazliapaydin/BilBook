import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.ArrayList;
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
        scrollPane = new ScrollPane();
        JPanel searchMenu = bilbook.createSortPanel(false);
        add(bilbook.createMenuBar());
        add(searchMenu);
        add(scrollPane);
        products = bilbook.getProducts();
        scrollPane.setMinimumSize(new Dimension(1500, 600));
        sortBooks(true, true, "ALL", 0, "", false, true, "Price ▲");
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
        } else if(sortMethod.equals("Random")) {
            Product.sort(productsToBeShown, Product.RANDOM);
        } 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(1, 20)));
        ArrayList<JPanel> panels=new ArrayList<>();
        for (Product product : productsToBeShown) {
            JPanel current=product.createPanel(false, bilbook.getLoggedIn(), bilbook);
            panels.add(current);
            panel.add(current);
        }
        scrollPane.removeAll();
        scrollPane.validate();
        panel.setMinimumSize(new Dimension(1500, 600));
        scrollPane.add(panel);
        for(JPanel current: panels)
        {
            current.updateUI();
        }
    }

}

