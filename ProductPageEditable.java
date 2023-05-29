import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductPageEditable extends JPanel{

    BilBook bilbook;
    Product product;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    JComboBox<String> departments;
    JComboBox<String> codes;


    ProductPageEditable(BilBook bilBook, Product product){
        this.bilbook = bilBook;
        this.product = product;
        JPanel page = new JPanel();
        setLayout(new BorderLayout());
        add(bilbook.createMenuBar(), BorderLayout.NORTH);
        page.setLayout(new GridLayout(2,1));

        GenericMethods.ChangeableImage image = GenericMethods.createChangeableImage();
        image.loadImage(product.getImageFile());
        JPanel featuresPanel = featuresLabel(product);
        JPanel publisherFeatures = publisherFeatures(product);
        JPanel container1 = new JPanel();
        container1.setLayout(new FlowLayout());
        container1.add(image);
        container1.add(featuresPanel);
        container1.add(publisherFeatures);
        page.add(container1);

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
        star.addItemListener(bilbook.favouriteListener(product));
        //star.setIcon(product.isFavouritedBy(bilBook.getLoggedIn()) ? GenericMethods.FAVOURITE_STAR: GenericMethods.NOT_FAVOURITE_STAR);
        if(product.isFavouritedBy(bilBook.getLoggedIn())) {
            star.setSelected(true);
        }
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
        JLabel label1 = new JLabel("Name of the book: ");
        JLabel label2 = new JLabel("Author of the book: ");
        JLabel label3 = product.isBook() ? new JLabel("Year published: ") : new JLabel("Year written: ");
        JLabel label4 = new JLabel("Price: ");
        JLabel label5 = new JLabel("Lecture: ");
        textField1 = new JTextField(product.getName(), 10);
        textField2 = new JTextField(product.getAuthor(),10);
        textField3 = new JTextField(product.getDatePublished().getYear()+"",10);
        textField4 = new JTextField(product.getPrice()+"", 10);
        departments=new JComboBox<>(datasOfLectures.lectures); departments.setSelectedItem(product.getCourseDepartment());
        codes=new JComboBox<>(datasOfLectures.getCodes(departments.getSelectedIndex())); codes.setSelectedItem(product.getCourseCode());
        departments.addItemListener(new  ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int department=departments.getSelectedIndex();
                if(department==0)
                {
                    codes.setEnabled(false);
                }
                else
                {
                    DefaultComboBoxModel<String> model= new DefaultComboBoxModel<>(datasOfLectures.getCodes(department));
                    codes.setModel(model);
                    codes.setEnabled(true);
                }
            }
        });

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
        JPanel combo = new JPanel(new GridLayout(1,2));
        combo.add(departments);
        combo.add(codes);
        panel.add(combo);
        return panel;
    }
    
    public JPanel publisherFeatures(Product product) {
        GenericMethods.ChangeableImage image = GenericMethods.createChangeableImage();
        image.loadImage(product.getUser().getImageFile());
        JLabel info1 = new JLabel("Phone Number: " + product.getUser().getPhoneNumber());
        JLabel info2 = new JLabel("Email Address: " + product.getUser().getEmail());
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(image);
        panel.add(info1);
        panel.add(info2);
        
        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.setName(textField1.getText());
                product.setAuthor(textField2.getText());
                product.setDatePublished(GenericMethods.createDate("1/1/" + textField3.getText()));
                product.setPrice(Float.parseFloat(textField4.getText()));
                product.setCourseDepartment((String)departments.getSelectedItem());
                product.setCourseCode(codes.getSelectedItem().equals("ALL")? 0 :Integer.parseInt((String)codes.getSelectedItem()));
                bilbook.changePanel(new ProductPage(bilbook, product));
                DatabaseControl.updateProduct(product);
                product.notifyFavouritedUsers();
            }
        });
        JButton sold = new JButton("Sold");
        sold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(product.isSold() == false) {
                    product.sell();
                } else {
                    product.reverseSell();
                }
                DatabaseControl.updateProduct(product);
                product.notifyFavouritedUsers();
             }
        });
        JButton delete = new JButton("Delete");
        delete.addActionListener(bilbook.productRemoverForJButton(product));
        panel.add(save);
        panel.add(sold);
        panel.add(delete);
        
        return panel;
    }
}
