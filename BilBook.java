import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BilBook extends JFrame
{
    private static String[] departments=new String[]{"ALL","MATH", "CS"};//update
    private static Integer[][] codes=new Integer[][]{new Integer[]{0},new Integer[]{101,102,132}, new Integer[]{101,102,201}};//update
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
        setSize(1619, 906);
        setTitle("BilBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        products=DatabaseControl.getProducts();
        users=DatabaseControl.getUsers();
        currentPanel=new HomePage(this);
    }

    /**
     * Changes the panel that's displayed in the BilBook frame.
     * Author: Ata Uzay Kuzey
     * @param panel new panel to be shown
     */
    public void changePanel(JPanel panel)
    {
        removeAll();
        add(panel);
        panel.updateUI();
    }

    public JPanel createMenuBar()
    {
        //TODO
        return new JPanel();
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
                ((HomePage) currentPanel).sortBooks(search.allowBooks(), search.allowNotes(), search.getSelectedDepartment(), search.getSelectedCode(), search.getSearchedName(), search.onlyFavourites(), search.onlyAvailables());
            }
            else if(currentPanel instanceof ProfilePage)
            {
                ((ProfilePage) currentPanel).sortBooks(search.allowBooks(), search.allowNotes(), search.getSelectedDepartment(), search.getSelectedCode(), search.getSearchedName(), search.onlyFavourites(), search.onlyAvailables());
            }
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
        JComboBox<Integer> codes;
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
            departments=new JComboBox<>(BilBook.departments); codes=new JComboBox<>(); codes.setEnabled(false);departments.addItemListener(new DepartmentsAndCodes());
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
            return (int)codes.getSelectedItem();
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
                    DefaultComboBoxModel<Integer> model= new DefaultComboBoxModel<>(BilBook.codes[department]);
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
}
