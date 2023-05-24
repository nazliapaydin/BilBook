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

public class ProductPageEditable extends JPanel{

    BilBook bilbook;
    Product product;

    ProductPageEditable(BilBook bilBook, Product product){
        this.bilbook = bilBook;
        this.product = product;
        setLayout(new GridLayout(2,1));

        ImageIcon image = GenericMethods.fileToImage(product.getImageFile(), 100);
        JLabel imageLabel = new JLabel(image);
        JPanel featuresPanel = featuresLabel(product);
        JPanel publisherFeatures = publisherFeatures(product);
        JPanel container1 = new JPanel();
        container1.setLayout(new FlowLayout());
        container1.add(imageLabel);
        container1.add(featuresPanel);
        container1.add(publisherFeatures);
        add(container1);

        JPanel description = new JPanel(new GridLayout(2,1));
        JLabel string = new JLabel("Description:");
        JTextField descriptionText = new JTextField(product.getDescription(), 5);
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
        add(container2);
    }

    public JPanel featuresLabel(Product product) {
        JLabel label1 = new JLabel("Name of the book: ");
        JLabel label2 = new JLabel("Author of the book: ");
        JLabel label3 = product.isBook() ? new JLabel("Year published: ") : new JLabel("Year written: ");
        JLabel label4 = new JLabel("Price: ");
        JLabel label5 = new JLabel("Lecture: ");
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JTextField textField3 = new JTextField();
        JTextField textField4 = new JTextField(" tl");
        //combo box eklencek

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,2));
        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(textField3);
        panel.add(label4);
        panel.add(textField4);
        panel.add(label5);
        //combo box eklencek
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
        
        JButton save = new JButton("Save");
        JButton sold = new JButton("Sold");
        JButton delete = new JButton("Delete");
        panel.add(save);
        panel.add(sold);
        panel.add(delete);
        
        return panel;
    }
}
