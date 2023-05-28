package src;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import src.BilBook.SearchMenu;

public class ProfilePage extends JPanel
{
    //constructor parameters
    BilBook bilBook;
    User currUser;

    boolean isLoggedIn;
    boolean editable;
    JButton editProfile;
    JButton deleteProfile;
    JLabel image;
    SearchMenu searchmenu;
    JScrollPane scrollPane;
    JPanel panel;

    //sub containers
    JPanel menuBar;
    JPanel profilePic;
    JPanel credentials;    
    JPanel profileControl; //edit and delete buttons
    JPanel searchPanel;

    public ProfilePage(BilBook current, User user)
    {
        bilBook = current;
        currUser = user;

        if(bilBook.getLoggedIn() != null)
        { isLoggedIn = false; }
        else
        { isLoggedIn = false; }

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS)); //for the scrollPane

        menuBar = bilBook.createMenuBar(bilBook.getLoggedIn());
        searchPanel = createSearchPanel();
        credentials = createCredentialsPanel();
        profilePic = createProfilePic();

        if(isLoggedIn)
        {
            profileControl = createProfileControlButtons();
            add(profileControl); //Don't forget to arrange layout
        }
        

        //MANAGE LAYOUTS
        add(menuBar);
        add(credentials); //Edit layout of the panel itself
        add(profilePic);
        add(searchPanel);

    }

    /**
     * @return JPanel that has both search menu and the scroll pane with products (default)
     */
    private JPanel createSearchPanel() 
    {
        JPanel searchPanel = new JPanel();
        searchmenu = bilBook.createSortPanel(true);
        for (Product product : currUser.getProducts()) 
        {
            panel.add(product.createPanel(true, bilBook.getLoggedIn(), bilBook));
        }

        searchPanel.add(searchmenu);
        scrollPane.add(panel);
        searchPanel.add(scrollPane);

        return searchPanel;
    }


    
    /**
     * @return JPanel that has "Edit Profile" and "Delete Profile" buttons
     */
    private JPanel createProfileControlButtons()
    {
        JPanel buttons = new JPanel();

        //Delete Profile Button
        JButton deleteProfile = new JButton();
        deleteProfile.setBackground(GenericMethods.GREAT_COLOR);
        deleteProfile.setText("Delete Profile");

        class deleteProfileListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                PopUpManager.deleteProfilePopup(bilBook, currUser);
            }
            
        }
        deleteProfile.addActionListener(new deleteProfileListener());

        //Edit Profile Button
        JButton editProfile = new JButton();
        editProfile.setBackground(GenericMethods.GREAT_COLOR);
        editProfile.setText("Edit Profile");
        
        class editProfileListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                editable = true;              
            }            
        }
        
        editProfile.addActionListener(new editProfileListener());
        buttons.add(editProfile);
        buttons.add(deleteProfile);
        return buttons;
    }

  
    /**
     * @return Panel that opens the filechooser when clicked and that includes the JLabel for the image
     */
    private JPanel createProfilePic()
    {
        profilePic = new JPanel();
        image = new JLabel();
        image.setIcon(GenericMethods.fileToImage(currUser.getImageFile(), 100)); //CHANGE SIZE TODO
        image.setName("");
        profilePic.add(image);

        class ImageListener implements MouseListener
        {

            @Override
            public void mouseClicked(MouseEvent e) 
            {
                currUser.setProfilePic(GenericMethods.chooseFile());
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            
        }

        if(isLoggedIn)
        {
            profilePic.addMouseListener(new ImageListener());
        }
        
        return profilePic;

    }

    public void sortBooks(boolean showBooks, boolean showNotes, String courseDepartment, int courseCode, String searchBar, boolean showOnlyFavourites, boolean dontShowSold, String sortMethod)
    {
        ArrayList<Product> productsToBeShown = new ArrayList<>();

        for(Product product : currUser.getProducts()) 
        {
            if(product.willBeShown(showBooks, showNotes, courseDepartment, courseCode, searchBar, showOnlyFavourites, dontShowSold, bilBook.getLoggedIn()))
            {
                productsToBeShown.add(product);
            }
        }
        if(sortMethod.equals("Price ▲")) 
        {
            Product.sort(productsToBeShown, Product.PRICE_ASCENDING);
        } 
        else if(sortMethod.equals("Price ▼")) 
        {
            Product.sort(productsToBeShown, Product.PRICE_DESCENDING);
        } 
        else if(sortMethod.equals("Date ▲")) 
        {
            Product.sort(productsToBeShown, Product.DATE_ASCENDING);
        }
        else if(sortMethod.equals("Date ▼"))
        {
            Product.sort(productsToBeShown, Product.DATE_DESCENDING);
        }
        else if(sortMethod.equals("Profit %"))
        {
            Product.sort(productsToBeShown, Product.INTERNETPRICE_PRICE_RATIO);
        } 
        else if(sortMethod.equals("Random%")) 
        {
            Product.sort(productsToBeShown, Product.RANDOM);
        } 

        for (Product product : productsToBeShown) 
        {
            panel.add(product.createPanel(false, bilBook.getLoggedIn(), bilBook));
        }

        scrollPane.removeAll();
        scrollPane.add(panel);
    }

    
    /**
     * @return  JPanel that has user information
     */
    private JPanel createCredentialsPanel()
    {
        JPanel credentials = new JPanel();
        credentials.setLayout(new GridLayout(9,2));

        JLabel header = new JLabel("Account Information");
        JLabel filler = new JLabel();
        JLabel usernameLabel = new JLabel("Username: ");
        JTextField usernameField = new JTextField(currUser.getUsername());
        JLabel nameLabel = new JLabel("Name: " + currUser.getName());
        JTextField nameField = new JTextField(currUser.getName());
        JLabel surnameLabel = new JLabel("Surname: ");
        JTextField surnameField = new JTextField(currUser.getSurname());
        JLabel phoneNumberLabel = new JLabel("Phone Number: ");
        JTextField phoneNumberField = new JTextField(currUser.getPhoneNumber());
        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField(currUser.getEmail());
        JLabel totalItemsLabel = new JLabel("Total Number of Items: ");
        JTextField totalItemsField = new JTextField(currUser.getNumOfTotalItems());
        JLabel soldItemsLabel = new JLabel("Number of Sold Items: ");
        JTextField soldItemsField = new JTextField(currUser.getNumOfSoldItems());
        credentials.add(header); credentials.add(filler);
        credentials.add(usernameLabel); credentials.add(usernameField);
        credentials.add(nameLabel); credentials.add(nameField);
        credentials.add(surnameLabel); credentials.add(surnameField);
        credentials.add(phoneNumberLabel); credentials.add(phoneNumberField);
        credentials.add(emailLabel); credentials.add(emailField);
        credentials.add(totalItemsLabel); credentials.add(totalItemsField);
        credentials.add(soldItemsLabel); credentials.add(soldItemsField);

        if(editable==true)
        {
            JLabel passwordLabel = new JLabel("New Password: ");
            JPasswordField passwordField = new JPasswordField();
            credentials.add(passwordLabel); credentials.add(passwordField);
            usernameField.setEditable(editable);
            nameField.setEditable(editable);
            surnameField.setEditable(editable);
            phoneNumberField.setEditable(editable);
            emailField.setEditable(editable);
            //TODO change variabled by getText()

        }
        return credentials;
    }
    
    
}
