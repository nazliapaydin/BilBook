import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.crypto.Data;

public class BilBook extends JFrame
{
    private static String[] sorts=new String[]{"Price ▲", "Price ▼", "Date ▲", "Date ▼", "Profit %", "Random"};

    private User loggedInUser;
    private JPanel currentPanel;
    private ArrayList<User> users;
    private ArrayList<Product> products;

    public BilBook()
    {
        setup();
    }

    /**
     * A method that proparly sets up a functional BilBook object.
     * Author: Ata Uzay Kuzey
     */
    private void setup()
    {
        DatabaseControl.setup();
        datasOfLectures.getDepartments();
        products=DatabaseControl.getProducts();
        users=DatabaseControl.getUsers();
        setSize(1619, 906);
        setTitle("BilBook");
        setIconImage((new ImageIcon("icon.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        changePanel(new HomePage(this));
        setLocationRelativeTo(null);
    }

    /**
     * Changes the panel that's displayed in the BilBook frame.
     * Author: Ata Uzay Kuzey
     * @param panel new panel to be shown
     */
    public void changePanel(JPanel panel)
    {
        getContentPane().removeAll();
        currentPanel=panel;
        getContentPane().add(currentPanel);
        revalidate();
        repaint();
    }

    public void refreshPage()
    {
        if(currentPanel instanceof HomePage)
        {
            changePanel(new HomePage(this));
        }
        else if(currentPanel instanceof ProfilePage)
        {
            changePanel(new ProfilePage(this, ((ProfilePage) currentPanel).getUser()));
        }
    }

    /**
     * Creates the top bar and arranged the buttons on the top menu according to loggedIn user.
     * Author: Nazlı Apaydın
     * @param loggedInUser
     * @return The Top Menu bar to be used in multiple classes
     * 
     */
    public JPanel createMenuBar()
    {
        User loggedInUser = this.getLoggedIn();
        Color background = new Color(47,49,50);
        JPanel menuBar = new JPanel(new BorderLayout());
        menuBar.setBackground(background);
        menuBar.setMaximumSize(new Dimension(1619, 200));
        JPanel topButtons = new JPanel(new BorderLayout());
        topButtons.setBackground(background);
        topButtons.setMaximumSize(new Dimension(800, 200));
        JPanel logoPanel = createLogo();

        if(loggedInUser == null)
        {
            //log in button
            JButton logInButton = new JButton("Log In");
            logInButton.setBackground(GenericMethods.GREAT_COLOR);
            logInButton.setMaximumSize(new Dimension(200, 100));
            logInButton.setFocusable(false);
            class LogInListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel((new logIn(BilBook.this)).getPanel());
                }
                
            }            
            logInButton.addActionListener(new LogInListener());
            topButtons.add(logInButton, BorderLayout.CENTER);

            //sign up button
            JButton signUpButton = new JButton("Sign Up");
            signUpButton.setBackground(GenericMethods.GREAT_COLOR);
            signUpButton.setMaximumSize(new Dimension(200, 100));
            signUpButton.setFocusable(false);
            class SignUpListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel((new signUp(BilBook.this)).getPanel());
                }
                
            }            
            signUpButton.addActionListener(new SignUpListener());
            topButtons.add(signUpButton, BorderLayout.EAST);
        }
        else //if it is loggedIn
        {
            //view profile button
            JButton viewProfile = new JButton("View Profile");
            viewProfile.setBackground(GenericMethods.GREAT_COLOR);
            viewProfile.setMaximumSize(new Dimension(200, 100));
            viewProfile.setFocusable(false);
            class ViewProfileListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel(new ProfilePage(BilBook.this, loggedInUser));
                }
                
            }            
            viewProfile.addActionListener(new ViewProfileListener());
            topButtons.add(viewProfile,BorderLayout.WEST);

            //create advert button
            JButton createAdvertButton = new JButton("Create Advert");
            createAdvertButton.setBackground(GenericMethods.GREAT_COLOR);
            createAdvertButton.setMaximumSize(new Dimension(200, 100));
            createAdvertButton.setFocusable(false);
            class CreateAdvertButtonListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel((new createAdvert(BilBook.this)).getPanel());
                }
                
            }            
            createAdvertButton.addActionListener(new CreateAdvertButtonListener());
            topButtons.add(createAdvertButton, BorderLayout.CENTER);

            //log out button
            JButton logOutButton = new JButton("Log Out");
            logOutButton.setBackground(GenericMethods.GREAT_COLOR);
            logOutButton.setMaximumSize(new Dimension(200, 100));
            logOutButton.setFocusable(false);
            class LogOutButtonListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    PopUpManager.logOutPopup(BilBook.this);
                }
                
            }            
            logOutButton.addActionListener(new LogOutButtonListener());
            topButtons.add(logOutButton, BorderLayout.EAST);

        }

        menuBar.add(logoPanel, BorderLayout.WEST);
        menuBar.add(topButtons, BorderLayout.EAST);
        return menuBar;
    }

    /**
     * Creates a SearchMenu object that is going to be used in HomePage and ProfilePage.
     * Author: Ata Uzay Kuzey
     * @param isProfilePage a boolean to know if the page is a ProfilePage or a HomePage.
     * @return the SearchMenu object created
     */
    public SearchMenu createSortPanel(boolean isProfilePage)
    {
        return new SearchMenu(isProfilePage);
    }

    /**
     * Finds a user object from the users arraylist using their email.
     * Author: Ata Uzay Kuzey
     * @param email given email whose owner is desired.
     * @return the user object if it exists, null otherwise.
     */
    public User findUserFromEmail(String email)
    {
        if(users==null){return null;}
        for(int i=0;i<users.size();i++)
        {
            if(users.get(i).getEmail().equals(email))
            {
                return users.get(i);
            }
        }
        return null;
    }

    /**
     * A method that let's the user log out.
     * Author: Ata Uzay Kuzey
     */
    public void logOut()
    {
        loggedInUser=null;
        changePanel(new HomePage(this));
    }

    public User getLoggedIn()
    {
        return loggedInUser;
    }

    public ArrayList<Product> getProducts()
    {
        return GenericMethods.copyOf(products);
    }



    /**
     * A method that makes the current panel change it's shown products according to the given criteria. It does not do anything unless the current panel
     * is a HomePage or a ProfilePage
     * Author: Ata Uzay Kuzey
     * @param search SearchMenu object from which the criteria will be taken.
     */
    private void changeScrollPane(SearchMenu search)
    {
        if(currentPanel!=null)
        {
            if(currentPanel instanceof HomePage)
            {
                ((HomePage) currentPanel).sortBooks(search.allowBooks(), search.allowNotes(), search.getSelectedDepartment(), search.getSelectedCode(), search.getSearchedName(), search.onlyFavourites(), search.onlyAvailables(), search.getSortMethod());
            }
            else if(currentPanel instanceof ProfilePage)
            {
                ((ProfilePage) currentPanel).sortBooks(search.allowBooks(), search.allowNotes(), search.getSelectedDepartment(), search.getSelectedCode(), search.getSearchedName(), search.onlyFavourites(), search.onlyAvailables(),search.getSortMethod());
            }
        }
    }

    /**
     * A method that logs in the user.
     * @param user user to be loged in.
     */
    public void logIn(User user)
    {
        loggedInUser=user;
    }

    /**
     * An inner class that extends JPanel. It is equipped with the necessary components to search products. This class is used with the HomePage
     * and ProfilePage objects.
     * Author: Ata Uzay Kuzey
     */
    public class SearchMenu extends JPanel
    {
        JCheckBox books;
        JCheckBox notes;
        JComboBox<String> departments;
        JComboBox<String> codes;
        JComboBox<String> sortMethods;
        JTextField searchBar;
        JCheckBox onlyFavourites;
        JCheckBox onlyAvailables;
        boolean isProfilePage;
        boolean changed;

        /**
         * The constructor for the SearchMenu item. It adds the parametres needed.
         * @param isProfilePage A boolean to know if the current panel is a ProfilePage or a HomePage. ProfilePages does not contain the checkbox
         * to only show favourites, and HomePages does not containt the checkbox to show all the items included the sold ones.
         */
        public SearchMenu(boolean isProfilePage)
        {
            super();
            this.isProfilePage=isProfilePage;
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            books=new JCheckBox("Books"); notes=new JCheckBox("Notes"); books.setSelected(true); notes.setSelected(true);
            departments=new JComboBox<>(datasOfLectures.lectures); codes=new JComboBox<>(datasOfLectures.getCodes(0)); codes.setEnabled(false);departments.addItemListener(new DepartmentsAndCodes());
            departments.setSelectedIndex(0); sortMethods=new JComboBox<>(sorts); searchBar=new JTextField();add(Box.createRigidArea(new Dimension(10, 10)));
            add(books); add(Box.createRigidArea(new Dimension(20, 10)));add(notes); add(Box.createHorizontalGlue());add(Box.createRigidArea(new Dimension(20, 10)));
            add(departments); add(Box.createRigidArea(new Dimension(20, 10))); add(codes); add(Box.createRigidArea(new Dimension(20, 10)));add(Box.createHorizontalGlue());
            add(sortMethods); add(Box.createHorizontalGlue());add(Box.createRigidArea(new Dimension(20, 10))); add(searchBar); 
            if(isProfilePage)
            {
                onlyAvailables=new JCheckBox("Only Availables");
                add(onlyAvailables);
                onlyAvailables.addItemListener(new SortChange());
            }
            else
            {
                if(loggedInUser!=null)
                {
                    onlyFavourites=new JCheckBox("Only Favourites");
                    add(onlyFavourites);    
                    onlyFavourites.addItemListener(new SortChange());
                }
            }
            add(Box.createRigidArea(new Dimension(10, 10)));
            books.addItemListener(new SortChange()); notes.addItemListener(new SortChange()); departments.addItemListener(new SortChange());
            codes.addItemListener(new SortChange()); sortMethods.addItemListener(new SortChange()); 
            searchBar.getDocument().addDocumentListener(new TextChange());
            setMaximumSize(new Dimension(1600, 20));
        }

        public boolean allowBooks()
        {
            return books.isSelected();
        }

        public boolean allowNotes()
        {
            return notes.isSelected();
        }

        public String getSelectedDepartment()
        {
            return (String)departments.getSelectedItem();
        }

        public int getSelectedCode()
        {
            if(!codes.isEnabled()){return 0;}
            return codes.getSelectedIndex()==0? 0: Integer.valueOf((String)codes.getSelectedItem());
        }

        public String getSortMethod()
        {
            return (String) sortMethods.getSelectedItem();
        } 

        public String getSearchedName()
        {
            return searchBar.getText();
        }

        public boolean onlyFavourites()
        {
            if(isProfilePage||loggedInUser==null){return false;}
            return onlyFavourites.isSelected();
        }

        public boolean onlyAvailables()
        {
            if(!isProfilePage){return true;}
            return onlyAvailables.isSelected();
        }

        /**
         * An itemlistener that changes the combobox for the course codes according to what is selected for the department.
         */
        public class DepartmentsAndCodes implements ItemListener
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
        }

        /**
         * An item listener that checks if any of the components except the textfield has changed. Calls the changeScrollPane method if that's the case.
         */
        public class SortChange implements ItemListener
        {
            @Override
            public void itemStateChanged(ItemEvent evt) {
                changeScrollPane(SearchMenu.this);
            }
        }

        /**
         * An item listener that checks if the textfield has changed. Calls the changeScrollPane method if that's the case.
         */
        public class TextChange implements DocumentListener
        {
            @Override
            public void changedUpdate(DocumentEvent evt) {}

            @Override
            public void insertUpdate(DocumentEvent e) {
                changeScrollPane(SearchMenu.this);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeScrollPane(SearchMenu.this);
            }
        }
    }

    /**
     * A mouselistener that can open a popup to confirm the deletion of the product
     * Author: Ata Uzay Kuzey
     * @param product the product that is going to be deleted
     * @return the mouselistener
     */
    public MouseListener productRemover(Product product)
    {
        class ProductRemover implements MouseListener
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                PopUpManager.deleteProductPopup(BilBook.this, product);
            }
    
            @Override
            public void mouseEntered(MouseEvent e) {}
    
            @Override
            public void mouseExited(MouseEvent e) {}
    
            @Override
            public void mousePressed(MouseEvent e) {}
    
            @Override
            public void mouseReleased(MouseEvent e) {}
        }
    
        return new ProductRemover();
    }

    /**
     * An actionlistener that can open a popup to confirm the deletion of the product
     * Author: Ata Uzay Kuzey
     * @param product the product that is going to be deleted
     * @return the actionlistener
     */
    public ActionListener productRemoverForJButton(Product product)
    {
        class ProductRemover implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopUpManager.deleteProductPopup(BilBook.this, product);
            }
        }

        return new ProductRemover();
    }

    /**
     * An itemlistener that controls the "favourite star" in our panels. If the star is active, the product is added as a favourite to the user.
     * Author: Ata Uzay Kuzey
     * @param product the product the itemlistener is for.
     * @return an itemlistener that can check a checkbox's state.
     */
    public ItemListener favouriteListener(Product product)
    {
        class FavouriteListener implements ItemListener
        {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox starCheck = (JCheckBox) e.getSource();
                if(loggedInUser!=null)
                {
                    if(starCheck.isSelected())
                    {
                        product.addFavouritedBy(loggedInUser);
                        starCheck.setIcon(GenericMethods.FAVOURITE_STAR);
                    }
                    else
                    {
                        product.removeFavouritedBy(loggedInUser);
                        starCheck.setIcon(GenericMethods.NOT_FAVOURITE_STAR);
                    }
                    DatabaseControl.updateProduct(product);
                }
                else
                {
                    starCheck.setSelected(false);
                    changePanel((new logIn(BilBook.this)).getPanel());
                }

            }
        }
        return new FavouriteListener();
    }

    /**
     * Removes a product object from BilBook and the Database.
     * Author: Ata Uzay Kuzey
     * @param product the product object to be deleted.
     */
    public void removeProduct(Product product)
    {
        products.remove(product);
        loggedInUser.removeProduct(product);
        DatabaseControl.removeProduct(product);
        product.notifyFavouritedUsers();
        if(currentPanel instanceof ProductPage)
        {
            changePanel(new HomePage(this));
        }
        else
        {
            refreshPage();
        }
    }

    /**
     * Removes a user object from BilBook and the Database. Also deletes all the products by that user.
     * Author: Ata Uzay Kuzey
     * @param user the user object to be deleted.
     */
    public void removeUser(User user)
    {
        users.remove(user);
        ArrayList<Product> userProducts=user.getProducts();
        for(int i=0;i<userProducts.size();i++)
        {
            removeProduct(userProducts.get(i));
        }
        DatabaseControl.removeUser(user);
        for(Product current: products)
        {
            current.removeFavouritedBy(user);
            DatabaseControl.updateProduct(current);
        }
        logOut();
        if(currentPanel instanceof ProfilePage)
        {
            changePanel(new HomePage(this));
        }
        else
        {
            refreshPage();
        }
    }

    /**
     * A method that tries to login using the username and password.
     * Author: Ata Uzay Kuzey
     * @param username the username of the user
     * @param password the password of the user
     * @return true if login is successful, false otherwise.
     */
    public boolean logIn(String username, String password)
    {
        for(User user: users)
        {
            if(user.getUsername().equals(username)&&user.getPassword().equals(password))
            {
                logIn(user);
                return true;
            }
        }
        return false;
    }

    /**
     * A method to determine if the username and email are used by a user.
     * @param username the username to be checked
     * @param email the email to be checked
     * @return true if there are no users with the same username or email, false otherwise.
     */
    public boolean allowSignUp(String username, String email)
    {
        for(User user: users)
        {
            if(user.getUsername().equals(username)||user.getEmail().equals(email))
            {
                return false;
            }
        }
        return true;
    }

    public void addProduct(Product product)
    {
        products.add(product);
        DatabaseControl.addToDataBase(product);
    }

    public void addUser(User user)
    {
        users.add(user);
        DatabaseControl.addToDataBase(user);
    }

    public JPanel createLogo()
    {
        JPanel logoPanel=new JPanel();
        logoPanel.setBackground(new Color(47,49,50));
        logoPanel.add(new JLabel(new ImageIcon("logo.png")));
        class GoToHome implements MouseListener
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(new HomePage(BilBook.this));
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
        }
        logoPanel.addMouseListener(new GoToHome());
        return logoPanel;
    }

    public MouseListener panelChanger(JPanel panel)
    {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePanel(panel);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}
        };
    }


    public static void main(String[] args) {
        BilBook bilBook=new BilBook(); 
        bilBook.setVisible(true);
    }
}