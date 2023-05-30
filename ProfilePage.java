import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ProfilePage extends JPanel
{
    //constructor parameters
    BilBook bilBook;
    User currUser;

    boolean isLoggedIn;
    static boolean editable = false; //TODO
    JButton editProfile;
    JButton deleteProfile;
    JLabel image;
    BilBook.SearchMenu searchmenu;
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
        this.setLayout(new BorderLayout());

        if(bilBook.getLoggedIn() != null)
        { isLoggedIn = true; }
        else
        { isLoggedIn = false; }

        menuBar = bilBook.createMenuBar();        
        profilePic = createProfilePic();
        credentials = createCredentialsPanel();
        searchPanel = createSearchPanel();
        
        add(menuBar, BorderLayout.NORTH);
        JPanel page = new JPanel(new GridLayout(2,1));

        JPanel container1 = new JPanel(new FlowLayout());
        container1.add(profilePic);
        container1.add(credentials);
        if(isLoggedIn)
        {
            profileControl = createProfileControlButtons();
            container1.add(profileControl);
        }

        page.add(container1);
        page.add(searchPanel);

        add(page, BorderLayout.CENTER);

    }

    /**
     * @return JPanel that has both search menu and the scroll pane with products (default)
     */
    private JPanel createSearchPanel() 
    {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 1));
        searchmenu = bilBook.createSortPanel(true);
        searchPanel.add(searchmenu);
        scrollPane=new JScrollPane();
        sortBooks(true, true, "ALL", 0, "", false, false, "Price ▲");
        scrollPane.setMinimumSize(new Dimension(1500, 600));
        searchPanel.add(scrollPane);
        return searchPanel;
    }


    
    /**
     * @return JPanel that has "Edit Profile" and "Delete Profile" buttons
     */
    private JPanel createProfileControlButtons()
    {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,1));

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
        image.setIcon(GenericMethods.fileToImage(currUser.getImageFile(), 200));
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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(1, 20)));
        ArrayList<JPanel> panels=new ArrayList<>();
        for (Product product : productsToBeShown) 
        {
            JPanel current = product.createPanel(true, bilBook.getLoggedIn(), bilBook);
            panels.add(current);
            panel.add(current);

        }

        scrollPane.removeAll();
        panel.setMinimumSize(new Dimension(1500, 600));
        scrollPane.add(panel);

        for(JPanel current: panels)
        {
            current.updateUI();
        }
    }

    
    /**
     * @return  JPanel that has user information
     */
    private JPanel createCredentialsPanel()
    {
        JPanel credentials = new JPanel();
        JLabel filler = new JLabel();
        credentials.setMaximumSize(new Dimension(300, 200));
        
        if(!editable)
        {
            credentials.setLayout(new GridLayout(8,1));
            JLabel header = new JLabel("Account Information");
            JLabel usernameLabel = new JLabel("Username: " + currUser.getUsername());
            JLabel nameLabel = new JLabel("Name: " + currUser.getName());
            JLabel surnameLabel = new JLabel("Surname: " + currUser.getSurname());
            JLabel phoneNumberLabel = new JLabel("Phone Number: " + currUser.getPhoneNumber());
            JLabel emailLabel = new JLabel("Email: " + currUser.getEmail());
            JLabel totalItemsLabel = new JLabel("Total Number of Items: " + currUser.getNumOfTotalItems());
            JLabel soldItemsLabel = new JLabel("Number of Sold Items: " + currUser.getNumOfSoldItems());
            credentials.add(header);
            credentials.add(usernameLabel);
            credentials.add(nameLabel);
            credentials.add(surnameLabel);
            credentials.add(phoneNumberLabel);
            credentials.add(emailLabel);
            credentials.add(totalItemsLabel);
            credentials.add(soldItemsLabel);
        }
        else
        {
            credentials.setLayout(new GridLayout(8,2));
            JLabel header = new JLabel("Edit Account Information");
            JLabel usernameLabel = new JLabel("Username: ");
            JTextField usernameField = new JTextField(currUser.getUsername()); usernameField.setEditable(true);
            JLabel nameLabel = new JLabel("Name: ");
            JTextField nameField = new JTextField(currUser.getName()); nameField.setEditable(true);
            JLabel surnameLabel = new JLabel("Surname: ");
            JTextField surnameField = new JTextField(currUser.getSurname()); surnameField.setEditable(true);;
            JLabel phoneNumberLabel = new JLabel("Phone Number: ");
            JTextField phoneNumberField = new JTextField(currUser.getPhoneNumber()); phoneNumberField.setEditable(true);
            JLabel emailLabel = new JLabel("Email: ");
            JTextField emailField = new JTextField(currUser.getEmail()); emailField.setEditable(true);
            credentials.add(header); credentials.add(filler);
            credentials.add(usernameLabel); credentials.add(usernameField);
            credentials.add(nameLabel); credentials.add(nameField);
            credentials.add(surnameLabel); credentials.add(surnameField);
            credentials.add(phoneNumberLabel); credentials.add(phoneNumberField);
            credentials.add(emailLabel); credentials.add(emailField);
            JLabel passwordLabel = new JLabel("New Password: ");
            JPasswordField passwordField = new JPasswordField();
            JButton saveButton = new JButton("Save Profile");
            saveButton.setBackground(GenericMethods.GREAT_COLOR);
            credentials.add(passwordLabel); credentials.add(passwordField);
            credentials.add(filler); credentials.add(saveButton);

            class SaveListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    currUser.setUsername(usernameField.getText());
                    currUser.setName(nameField.getText());
                    currUser.setSurname(surnameField.getText());
                    currUser.setPhoneNumber(phoneNumberField.getText());
                    currUser.setMail(emailField.getText());
                    currUser.setPassword(GenericMethods.passwordFieldToString(passwordField));
                    DatabaseControl.updateUser(currUser);
                    editable = false;
                }
                
            }
            saveButton.addActionListener(new SaveListener());            
        }   
        return credentials;
    }
    
    public User getUser() {
        return currUser;
    }
    
}

