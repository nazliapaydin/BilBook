import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ProductPageEditable extends JPanel{

    BilBook bilbook;
    Product product;
    JTextField textField1;
    JTextField textField2;
    JTextField textField3;
    JTextField textField4;
    JTextArea descriptionText;
    JComboBox<String> departments;
    JComboBox<String> codes;
    Font font = new Font(Font.SANS_SERIF, Font.BOLD, 26);
    Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 15);
    Font font3 = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

    ProductPageEditable(BilBook bilBook, Product product){
        this.bilbook = bilBook;
        this.product = product;
        JPanel page = new JPanel();
        setLayout(new BorderLayout());
        add(bilbook.createMenuBar(), BorderLayout.NORTH);
        page.setLayout(null);

        GenericMethods.ChangeableImage image = GenericMethods.createChangeableImage();
        image.resizeChangeableImage(260);
        image.loadImage(product.getImageFile());
        JPanel featuresPanel = featuresLabel(product);
        JPanel publisherFeatures = publisherFeatures(product);
        page.add(image);
        image.setBounds(40,40,260,260);
        page.add(featuresPanel);
        featuresPanel.setBounds(350, 50, 500,240 );
        page.add(publisherFeatures);
        publisherFeatures.setBounds(1200, 40, 400, 400);

        JPanel description = new JPanel();
        description.setLayout(null);
        JLabel string = new JLabel("Description:");
        string.setFont(font);
        string.setBounds(0, 10, 300, 26);
        descriptionText = new JTextArea(product.getDescription());
        descriptionText.setLineWrap(true);
        descriptionText.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        descriptionText.setBounds(0, 40, 700, 280);
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
        description.setBounds(40, 300, 700, 330);
        page.add(prices);
        prices.setBounds(750, 330, 400, 200);
        page.add(favorite);
        add(page, BorderLayout.CENTER);
    }

    public JPanel featuresLabel(Product product) {
        JLabel label1 = new JLabel("Name of the book: ");
        label1.setFont(font);
        JLabel label2 = new JLabel("Author of the book: ");
        label2.setFont(font);
        JLabel label3 = product.isBook() ? new JLabel("Year published: ") : new JLabel("Year written: ");
        label3.setFont(font);
        JLabel label4 = new JLabel("Price: ");
        label4.setFont(font);
        JLabel label5 = new JLabel("Lecture: ");
        label5.setFont(font);
        textField1 = new JTextField(product.getName(), 10);
        textField1.setFont(font3);
        textField2 = new JTextField(product.getAuthor(),10);
        textField2.setFont(font3);
        textField3 = new JTextField(product.getDatePublished().getYear()+"",10);
        textField3.setFont(font3);
        textField4 = new JTextField(product.getPrice()+"", 10);
        textField4.setFont(font3);
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
        image.resizeChangeableImage(150);
        image.loadImage(product.getUser().getImageFile());
        JLabel info1 = new JLabel("Phone Number: " + product.getUser().getPhoneNumber());
        info1.setFont(font2);
        JLabel info2 = new JLabel("Email Address: " + product.getUser().getEmail());
        info2.setFont(font2);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(image);
        image.setBounds(125, 0, 150, 150);
        panel.add(info1);
        info1.setBounds(0, 165, 400, 15);
        panel.add(info2);
        info2.setBounds(0, 183, 400, 15);
        
        JButton save = new JButton("Save");
        save.setFocusable(false);
        save.setBounds(125, 210, 150, 35);
        save.setBackground(GenericMethods.GREAT_COLOR);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                product.setName(textField1.getText());
                product.setAuthor(textField2.getText());
                product.setDatePublished(GenericMethods.createDate("1/1/" + textField3.getText()));
                product.setPrice(Float.parseFloat(textField4.getText()));
                product.setCourseDepartment((String)departments.getSelectedItem());
                product.setCourseCode(codes.getSelectedItem().equals("ALL")? 0 :Integer.parseInt((String)codes.getSelectedItem()));
                product.setDescription(descriptionText.getText());
                bilbook.changePanel(new ProductPage(bilbook, product));
                DatabaseControl.updateProduct(product);
                product.notifyFavouritedUsers();
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
                } else {
                    product.reverseSell();
                }
                DatabaseControl.updateProduct(product);
                product.notifyFavouritedUsers();
             }
        });
        JButton delete = new JButton("Delete");
        delete.setFocusable(false);
        delete.setBackground(GenericMethods.GREAT_COLOR);
        delete.setBounds(125, 300, 150, 35);
        delete.addActionListener(bilbook.productRemoverForJButton(product));
        panel.add(save);
        panel.add(sold);
        panel.add(delete);
        
        return panel;
    }
}
