import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductPage extends JPanel{

    BilBook bilbook;
    Product product;

    ProductPage(BilBook bilBook, Product product){
        this.bilbook = bilBook;
        this.product = product;
        JPanel page = new JPanel();
        setLayout(new BorderLayout());
        add(bilbook.createMenuBar(bilBook.getLoggedIn()), BorderLayout.NORTH);
        page.setLayout(new GridLayout(2,1));

        ImageIcon image = GenericMethods.fileToImage(product.getImageFile(), 100);
        JLabel imageLabel = new JLabel(image);
        JPanel featuresPanel = featuresLabel(product);
        JPanel publisherFeatures = publisherFeatures(product);
        JPanel container1 = new JPanel();
        container1.setLayout(new FlowLayout());
        container1.add(imageLabel);
        container1.add(featuresPanel);
        container1.add(publisherFeatures);
        page.add(container1);

        JPanel description = new JPanel(new GridLayout(2,1));
        JLabel string = new JLabel("Description:");
        JTextField descriptionText = new JTextField(product.getDescription(), 5);
        descriptionText.setEditable(false);
        description.add(string);
        description.add(descriptionText);

        JPanel prices = new JPanel(new GridLayout(1,2));
        JPanel seller = new JPanel(new GridLayout(2, 1));
        JLabel sellerText = new JLabel("Seller's Price");
        JLabel sellerPrice = new JLabel(product.getPrice() + "₺");
        seller.add(sellerText);
        seller.add(sellerPrice);
        JPanel online = new JPanel(new GridLayout(2, 1));
        JLabel onlineText = new JLabel("Online Price");
        JLabel onlinePrice = new JLabel(product.getOnlinePrice() + "$");
        online.add(onlineText);
        online.add(onlinePrice);
        prices.add(seller);
        prices.add(online);

        ImageIcon starImageOff = GenericMethods.NOT_FAVOURITE_STAR;
        ImageIcon starImageOn = GenericMethods.FAVOURITE_STAR;
        JPanel favorite = new JPanel();
        JCheckBox star = new JCheckBox(starImageOff);
        star.setSelectedIcon(starImageOn);
        favorite.add(star);
        favorite.setPreferredSize(new Dimension(50, 50));

        JPanel container2 = new JPanel(new FlowLayout());
        container2.add(description);
        container2.add(prices);
        container2.add(favorite);
        page.add(container2);
        add(page, BorderLayout.CENTER);
    }

    public JPanel featuresLabel(Product product) {
        JLabel label1 = new JLabel("Name of the book: " + product.getName());
        JLabel label2 = new JLabel("Author of the book: " + product.getAuthor());
        JLabel label3 = product.isBook() ? new JLabel("Year published: " + product.getDatePublished()) : new JLabel("Year written: " + product.getDatePublished());
        JLabel label4 = new JLabel("Price: " + product.getPrice());
        JLabel label5 = new JLabel("Lecture: " + product.getCourseDepartment() + " " + product.getCourseCode());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);
        return panel;
    }
    
    public JPanel publisherFeatures(Product product) {
        ImageIcon image = GenericMethods.fileToImage(product.getUser().getImageFile(), 80);
        JLabel imageLabel = new JLabel(image);
        JLabel info1 = new JLabel("Phone Number: " + product.getUser().getPhoneNumber());
        JLabel info2 = new JLabel("Email Address: " + product.getUser().getEmail());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(imageLabel);
        panel.add(info1);
        panel.add(info2);
        if(product.getUserID() == bilbook.getLoggedIn().getID()) {
            JButton edit = new JButton("Edit");
            JButton sold = new JButton("Sold");
            JButton delete = new JButton("Delete");
            panel.add(edit);
            panel.add(sold);
            panel.add(delete);
        }
        return panel;
    }
}
