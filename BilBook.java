import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
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
        setSize(1619, 906);
        setTitle("BilBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        products=DatabaseControl.getProducts();
        users=DatabaseControl.getUsers();
        currentPanel=new HomePage(this);
        add(currentPanel);
    }

    /**
     * Changes the panel that's displayed in the BilBook frame.
     * Author: Ata Uzay Kuzey
     * @param panel new panel to be shown
     */
    public void changePanel(JPanel panel)
    {
        remove(currentPanel);
        currentPanel=panel;
        add(panel);
    }

    public void refreshPage()
    {
        //TODO
    }

    /**
     * Creates the top bar and arranged the buttons on the top menu according to loggedIn user.
     * Author: Nazlı Apaydın
     * @param loggedInUser
     * @return The Top Menu bar to be used in multiple classes
     * 
     */
    public JPanel createMenuBar(User loggedInUser)
    {
        Color background = new Color(47,49,50);
        JPanel menuBar = new JPanel();
        menuBar.setBackground(background);
        menuBar.setPreferredSize(new Dimension(1619, 100)); //CHANGE TODO
        JPanel topButtons = new JPanel();
        topButtons.setBackground(background);
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(background);
        ImageIcon logoIcon = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);

        if(loggedInUser == null)
        {
            //log in button
            JButton logInButton = new JButton();
            logInButton.setBackground(GenericMethods.GREAT_COLOR);
            class LogInListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel(new logIn());
                }
                
            }            
            logInButton.addActionListener(new LogInListener());
            topButtons.add(logInButton);

            //sign up button
            JButton signUpButton = new JButton();
            signUpButton.setBackground(GenericMethods.GREAT_COLOR);
            class SignUpListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel(new signUp());
                }
                
            }            
            signUpButton.addActionListener(new SignUpListener());
            topButtons.add(signUpButton);
        }
        else //if it is loggedIn
        {
            //view profile button
            JButton viewProfile = new JButton("View Profile");
            viewProfile.setBackground(GenericMethods.GREAT_COLOR);
            class ViewProfileListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel(new ProfilePage(BilBook.this, loggedInUser));
                }
                
            }            
            viewProfile.addActionListener(new ViewProfileListener());
            topButtons.add(viewProfile);

            //create advert button
            JButton createAdvertButton = new JButton("Create Advert");
            createAdvertButton.setBackground(GenericMethods.GREAT_COLOR);
            class CreateAdvertButtonListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    changePanel(new createAdvert());
                }
                
            }            
            createAdvertButton.addActionListener(new CreateAdvertButtonListener());
            topButtons.add(createAdvertButton);

            //log out button
            JButton logOutButton = new JButton("Log Out");
            logOutButton.setBackground(GenericMethods.GREAT_COLOR);
            class LogOutButtonListener implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    PopUpManager.logOutPopup(BilBook.this);
                }
                
            }            
            logOutButton.addActionListener(new LogOutButtonListener());
            topButtons.add(logOutButton);

        }

        menuBar.add(logoPanel);
        menuBar.add(topButtons);
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

    @Override
    protected void processWindowEvent(WindowEvent e) {
        DatabaseControl.closeConnection();
        super.processWindowEvent(e);
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
        if(loggedInUser==null)
        {
            loggedInUser=user;
        }
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
            add(books); add(Box.createRigidArea(new Dimension(20, 10)));add(notes); add(Box.createHorizontalGlue());
            add(departments); add(Box.createRigidArea(new Dimension(20, 10))); add(codes); add(Box.createHorizontalGlue());
            add(sortMethods); add(Box.createHorizontalGlue()); add(searchBar); 
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
            changeScrollPane(this);
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
            return Integer.valueOf((String)codes.getSelectedItem());
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
                    }
                    else
                    {
                        product.removeFavouritedBy(loggedInUser);
                    }
                }
                else
                {
                    starCheck.setSelected(false);
                    changePanel(new logIn());
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


    public static void main(String[] args) {
        BilBook bilBook=new BilBook();
        bilBook.setVisible(true);
    }
}
