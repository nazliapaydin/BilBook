import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class ProductPage extends JPanel{

    BilBook bilbook;
    Product product;
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
    Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 15);

    ProductPage(BilBook bilBook, Product product){
        this.bilbook = bilBook;
        this.product = product;
        JPanel page = new JPanel();
        setLayout(new BorderLayout());
        add(bilbook.createMenuBar(), BorderLayout.NORTH);
        page.setLayout(null);

        ImageIcon image = GenericMethods.fileToImage(product.getImageFile(), 260);
        JLabel imageLabel = new JLabel(image);
        JPanel featuresPanel = featuresLabel(product);
        JPanel publisherFeatures = publisherFeatures(product);
        
        page.add(imageLabel);
        imageLabel.setBounds(40,40,260,260);
        page.add(featuresPanel);
        featuresPanel.setBounds(350, 50, 800,240 );
        page.add(publisherFeatures);
        publisherFeatures.setBounds(1200, 40, 400, 400);
        
        

        JPanel description = new JPanel();
        description.setLayout(null);
        JLabel string = new JLabel("Description:");
        string.setFont(font);
        string.setBounds(0, 10, 300, 26);
        JTextArea descriptionText = new JTextArea(product.getDescription());
        descriptionText.setLineWrap(true);
        descriptionText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        descriptionText.setBounds(0, 40, 700, 280);
        descriptionText.setEditable(false);
        description.add(string);
        description.add(descriptionText);

        JPanel prices = new JPanel(new GridLayout(1,2));
        JPanel seller = new JPanel(new GridLayout(2, 1));
        JLabel sellerText = new JLabel("Seller's Price");
        JLabel sellerPrice = new JLabel(product.getPrice() + "₺");
        sellerText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        sellerPrice.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        seller.add(sellerText);
        seller.add(sellerPrice);
        Border sellerBorder = BorderFactory.createTitledBorder("Cheap");
        seller.setBorder(sellerBorder);
        JPanel online = new JPanel(new GridLayout(2, 1));
        JLabel onlineText = new JLabel("Online Price");
        JLabel onlinePrice = new JLabel(product.getOnlinePrice() + "$");
        onlineText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        onlinePrice.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        online.add(onlineText);
        online.add(onlinePrice);
        Border onlineBorder = BorderFactory.createTitledBorder("Expensive");
        online.setBorder(onlineBorder);
        prices.add(seller);
        prices.add(online);

        ImageIcon starImageOff = GenericMethods.NOT_FAVOURITE_STAR;
        ImageIcon starImageOn = GenericMethods.FAVOURITE_STAR;
        JPanel favorite = new JPanel();
        JCheckBox star = new JCheckBox(starImageOff);
        star.setSelectedIcon(starImageOn);
        star.addItemListener(bilbook.favouriteListener(product));
        //star.setIcon(product.isFavouritedBy(bilBook.getLoggedIn()) ? GenericMethods.FAVOURITE_STAR: GenericMethods.NOT_FAVOURITE_STAR);
        if(product.isFavouritedBy(bilBook.getLoggedIn())) {
            star.setSelected(true);
        }
        favorite.add(star);
        favorite.setBounds(1350, 450, 100, 100);

        
        page.add(description);
        description.setBounds(40, 300, 700, 500);
        page.add(prices);
        prices.setBounds(750, 325, 400, 200);
        page.add(favorite);
        add(page, BorderLayout.CENTER);
    }

    public JPanel featuresLabel(Product product) {
        
        JLabel label1 = new JLabel("Name of the book: " + product.getName());
        label1.setFont(font);
        JLabel label2 = new JLabel("Author of the book: " + product.getAuthor());
        label2.setFont(font);
        JLabel label3 = product.isBook() ? new JLabel("Year published: " + product.getDatePublished()) : new JLabel("Year written: " + product.getDatePublished());
        label3.setFont(font);
        JLabel label4 = new JLabel("Price: " + (int)product.getPrice() + "₺");
        label4.setFont(font);
        JLabel label5 = new JLabel("Lecture: " + product.getCourseDepartment() + " " + product.getCourseCode());
        label5.setFont(font);
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
        ImageIcon image = GenericMethods.fileToImage(product.getUser().getImageFile(), 150);
        JLabel imageLabel = new JLabel(image);
        JLabel info1 = new JLabel("Phone Number: " + product.getUser().getPhoneNumber());
        info1.setFont(font2);
        JLabel info2 = new JLabel("Email Address: " + product.getUser().getEmail());
        info2.setFont(font2);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(imageLabel);
        imageLabel.setBounds(125, 0, 150, 150);
        panel.add(info1);
        info1.setBounds(0, 165, 400, 15);
        panel.add(info2);
        info2.setBounds(0, 183, 400, 15);
        JButton edit = new JButton("Edit");
        edit.setFocusable(false);
        edit.setBounds(125, 210, 150, 35);
        edit.setBackground(GenericMethods.GREAT_COLOR);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bilbook.changePanel(new ProductPageEditable(bilbook, product));
                
            }
        });
        JButton sold = new JButton("Sold");
        sold.setFocusable(false);
        sold.setBackground(GenericMethods.GREAT_COLOR);
        sold.setBounds(125, 255, 150, 35);
        sold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(product.isSold() == false) {
                    product.sell();
                    sold.setText("Unsell");
                } else {
                    product.reverseSell();
                    sold.setText("Sold");
                }
                DatabaseControl.updateProduct(product);
            }
        });
        JButton delete = new JButton("Delete");
        delete.setFocusable(false);
        delete.setBackground(GenericMethods.GREAT_COLOR);
        delete.setBounds(125, 300, 150, 35);
        delete.addActionListener(bilbook.productRemoverForJButton(product));
        panel.add(edit);
        panel.add(sold);
        panel.add(delete);
        if(bilbook.getLoggedIn()!=null && product.getUserID() != bilbook.getLoggedIn().getID()) {
            edit.setEnabled(false);
            sold.setEnabled(false);
            delete.setEnabled(false);
        }
        return panel;
    }
}
