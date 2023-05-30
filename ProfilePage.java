import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

public class ProfilePage extends JPanel
{
    //constructor parameters
    BilBook bilBook;
    User currUser;
    Font fontBig = new Font(Font.SANS_SERIF, Font.BOLD, 22);
    Font fontSmall = new Font(Font.SANS_SERIF, Font.BOLD, 17);

    boolean isLoggedIn;
    static boolean editable = false; //TODO
    JButton editProfile;
    JButton deleteProfile;
    JLabel image;
    BilBook.SearchMenu searchmenu;
    ScrollPane scrollPane;
    JPanel panel;

    //sub containers
    JPanel menuBar;
    JPanel profilePic;
    JPanel credentials;    
    JPanel profileControl; //edit and delete buttons
    JPanel scrollPanel;
    JPanel page;

    public ProfilePage(BilBook current, User user)
    {
        bilBook = current;
        currUser = user;
        this.setLayout(new BorderLayout());

        if(bilBook.getLoggedIn() != null)
        { isLoggedIn = true; }
        else
        { isLoggedIn = false; }

        page = new JPanel();
        page.setLayout(null);

        menuBar = bilBook.createMenuBar();
        add(menuBar, BorderLayout.NORTH);        
        credentials = createCredentialsPanel();
        credentials.setBounds(390, 15, 600, 250);
        page.add(credentials);

        if(isLoggedIn)
        {
            deleteProfile = createDeleteProfileButton();
            editProfile = createEditProfileButton();
            deleteProfile.setBounds(1230, 135, 200, 50);
            editProfile.setBounds(1230, 205, 200, 50);
            page.add(deleteProfile);
            page.add(editProfile);
        }

        searchmenu = bilBook.createSortPanel(true);
        searchmenu.setBounds(90, 285, 1500, 30);
        page.add(searchmenu);
        
        scrollPane = createScrollPane();
        scrollPane.setBounds(90, 315, 1500, 330);
        page.add(scrollPane);
        add(page, BorderLayout.CENTER);

    }

    /**
     * @return JPanel that has both search menu and the scroll pane with products (default)
     */
    private ScrollPane createScrollPane() 
    {
        scrollPane=new ScrollPane();
        sortBooks(true, true, "ALL", 0, "", false, false, "Price ▲");
        return scrollPane;
    }


    
    /**
     * @return JPanel that has "Edit Profile" and "Delete Profile" buttons
     */
    private JButton createDeleteProfileButton()
    {
        //Delete Profile Button
        JButton deleteProfile = new JButton();
        deleteProfile.setBackground(GenericMethods.GREAT_COLOR);
        deleteProfile.setText("Delete Profile");
        deleteProfile.setFont(fontSmall);

        class deleteProfileListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                PopUpManager.deleteProfilePopup(bilBook, currUser);
            }
            
        }
        deleteProfile.addActionListener(new deleteProfileListener());

        return deleteProfile;
    }

    private JButton createEditProfileButton()
    {
        //Edit Profile Button
        JButton editProfile = new JButton();
        editProfile.setBackground(GenericMethods.GREAT_COLOR);
        editProfile.setText("Edit Profile");
        editProfile.setFont(fontSmall);
        
        class editProfileListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                editable = true;
                bilBook.refreshPage();             
            }            
        }
        
        editProfile.addActionListener(new editProfileListener());

        return editProfile;
    }

  
    /**
     * @return Panel that opens the filechooser when clicked and that includes the JLabel for the image
     */
    private JPanel createProfilePic()
    {
        profilePic = new JPanel();
        image = new JLabel();
        image.setIcon(currUser.getImageFile()==null ? GenericMethods.fileToImage(new File("profile_default.png"), 200) :GenericMethods.fileToImage(currUser.getImageFile(), 200));
        image.setName("");
        profilePic.add(image);
        
        return profilePic;

    }

    public void sortBooks(boolean showBooks, boolean showNotes, String courseDepartment, int courseCode, String searchBar, boolean showOnlyFavourites, boolean dontShowSold, String sortMethod)
    {
        ArrayList<Product> productsToBeShown = new ArrayList<>();
        ArrayList <Product> userProducts = currUser.getProducts();

        for(Product product : userProducts) 
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
        credentials.setMaximumSize(new Dimension(300, 200));
        
        if(!editable)
        {
            profilePic = createProfilePic();
            profilePic.setBounds(90, 15, 200, 200);
            page.add(profilePic);
            credentials.setLayout(new GridLayout(8,1));
            JLabel header = new JLabel("Account Information");
            header.setFont(fontBig);
            JLabel usernameLabel = new JLabel("Username: " + currUser.getUsername());
            usernameLabel.setFont(fontSmall);
            JLabel nameLabel = new JLabel("Name: " + currUser.getName());
            nameLabel.setFont(fontSmall);
            JLabel surnameLabel = new JLabel("Surname: " + currUser.getSurname());
            surnameLabel.setFont(fontSmall);
            JLabel phoneNumberLabel = new JLabel("Phone Number: " + currUser.getPhoneNumber());
            phoneNumberLabel.setFont(fontSmall);
            JLabel emailLabel = new JLabel("Email: " + currUser.getEmail());
            emailLabel.setFont(fontSmall);
            JLabel totalItemsLabel = new JLabel("Total Number of Items: " + currUser.getNumOfTotalItems());
            totalItemsLabel.setFont(fontSmall);
            JLabel soldItemsLabel = new JLabel("Number of Sold Items: " + currUser.getNumOfSoldItems());
            soldItemsLabel.setFont(fontSmall);
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
            GenericMethods.ChangeableImage profilePic = GenericMethods.createChangeableImage(200);
            profilePic.setBounds(90, 15, 200, 200);
            page.add(profilePic);
            credentials.setLayout(new GridLayout(8,2));
            JLabel header = new JLabel("Edit Account Information");
            header.setFont(fontBig);
            JLabel usernameLabel = new JLabel("Username: ");
            usernameLabel.setFont(fontSmall);
            JTextField usernameField = new JTextField(currUser.getUsername()); usernameField.setEditable(true);
            JLabel nameLabel = new JLabel("Name: ");
            nameLabel.setFont(fontSmall);
            JTextField nameField = new JTextField(currUser.getName()); nameField.setEditable(true);
            JLabel surnameLabel = new JLabel("Surname: ");
            surnameLabel.setFont(fontSmall);
            JTextField surnameField = new JTextField(currUser.getSurname()); surnameField.setEditable(true);;
            JLabel phoneNumberLabel = new JLabel("Phone Number: ");
            phoneNumberLabel.setFont(fontSmall);
            JTextField phoneNumberField = new JTextField(currUser.getPhoneNumber()); phoneNumberField.setEditable(true);
            JLabel emailLabel = new JLabel("Email: ");
            emailLabel.setFont(fontSmall);
            JTextField emailField = new JTextField(currUser.getEmail()); emailField.setEditable(true);
            credentials.add(header); credentials.add(new JLabel());
            credentials.add(usernameLabel); credentials.add(usernameField);
            credentials.add(nameLabel); credentials.add(nameField);
            credentials.add(surnameLabel); credentials.add(surnameField);
            credentials.add(phoneNumberLabel); credentials.add(phoneNumberField);
            credentials.add(emailLabel); credentials.add(emailField);
            JLabel passwordLabel = new JLabel("New Password: ");
            passwordLabel.setFont(fontSmall);
            JPasswordField passwordField = new JPasswordField(); passwordField.setText(currUser.getPassword());
            JButton saveButton = new JButton("Save Profile");
            saveButton.setFont(fontBig);
            saveButton.setBackground(GenericMethods.GREAT_COLOR);
            credentials.add(passwordLabel); credentials.add(passwordField);
            credentials.add(new JLabel()); credentials.add(saveButton);

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
                    currUser.setProfilePic(profilePic.getImage());
                    DatabaseControl.updateUser(currUser);
                    editable = false;
                    bilBook.refreshPage();
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

