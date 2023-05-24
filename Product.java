import java.io.File;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * A class to represent a product object.
 * Author: Ata Uzay Kuzey
 */
public class Product implements Comparable<Product>
{
    private static ArrayList<Integer> IDList=new ArrayList<>();

    private String name;
    private String author;
    private GenericMethods.Date datePublished;
    private GenericMethods.Date dateUploaded;
    private float price;
    private String courseDepartment;
    private int courseCode;
    private File image;
    private String description;
    private float onlinePrice;
    private User user;
    private int userID;
    private int ID;
    private boolean isBook;
    private boolean isSold;
    private ArrayList<User> favouritedBy;
    private String favouritedByArray;

    public static final int PRICE_ASCENDING=6;
    public static final int PRICE_DESCENDING=3;
    public static final int DATE_ASCENDING=8;
    public static final int DATE_DESCENDING=11;
    public static final int INTERNETPRICE_PRICE_RATIO=14;
    public static final int RANDOM=31;
    private static int currentSort=11;


    /**
     * Constructs a product object.
     * @param name name of the product
     * @param author author of the product
     * @param datePublished year published
     * @param price seller's price of the product
     * @param courseDepartment department of the course the product belongs to
     * @param courseCode code of the course the product belongs to
     * @param user user of the seller
     */
    public Product(String name ,String author,GenericMethods.Date datePublished,GenericMethods.Date dateUploaded,float price,String courseDepartment,int courseCode,User user,boolean isBook,boolean isSold, File image)
    {
        this.name=name;
        this.author=author;
        this.datePublished=datePublished;
        this.dateUploaded=dateUploaded;
        this.price=price;
        this.courseDepartment=courseDepartment;
        this.courseCode=courseCode;
        this.user=user;
        this.userID=user.getID();
        this.onlinePrice=isBook ? PriceScraper.priceScrape(name, author, datePublished.getYear()): 0;
        this.isBook=isBook;
        this.isSold=isSold;
        this.description="";
        this.favouritedBy=new ArrayList<>();
        if(image==null)
        {
            if(isBook)
            {
                this.image=new File("default_book.png");
            }
            else
            {
                this.image=new File("default_notes.png");
            }
        }
        else
        {
            this.image=image;
        }
        do
        {
            this.ID=GenericMethods.rand.nextInt(Integer.MAX_VALUE);
        }while(IDList.contains(this.ID));
    }

    /**
     * This method is used to create a product object that is going to be altered using the data from the database.
     * @param ID ID of the product
     * @param isBook true if the product is a book, false if it is notes.
     */
    protected Product(int ID, boolean isBook)
    {
        this.ID=ID;
        this.isBook=isBook;
        this.onlinePrice=isBook ? PriceScraper.priceScrape(name, author, datePublished.getYear()): 0;
        this.favouritedBy=new ArrayList<>();
    }

    //setters and getters
    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseDepartment(String courseDepartment) {
        this.courseDepartment = courseDepartment;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageFile(File image) {
        this.image = image;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    public void setDatePublished(GenericMethods.Date datePublished) {
        this.datePublished = datePublished;
    }

    public void setDateUploaded(GenericMethods.Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public void setFavouritedByArray(String favouritedByArray) {
        this.favouritedByArray = favouritedByArray;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void updateOnlinePrice()
    {
        this.onlinePrice=PriceScraper.priceScrape(name, author, datePublished.getYear());
    }

    public void addFavouritedBy(User user)
    {
        if(!favouritedBy.contains(user))
        {
            favouritedBy.add(user);
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getFavouritedBy() {
        return GenericMethods.copyOf(favouritedBy);
    }

    public String getFavouritedByArray() {
        return favouritedByArray;
    }

    public String getAuthor() {
        return author;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public String getDescription() {
        return description;
    }

    public File getImageFile() {
        return image;
    }

    public String getName() {
        return name;
    }

    public double getOnlinePrice() {
        return onlinePrice;
    }

    public float getPrice() {
        return price;
    }

    public User getProfile() {
        return user;
    }

    public User getUser() {
        return user;
    }

    public GenericMethods.Date getDatePublished() {
        return datePublished;
    }

    public GenericMethods.Date getDateUploaded() {
        return dateUploaded;
    }

    public static void setUniversalSort(int code)
    {
        currentSort=code;
    }

    public int getUserID() {
        return userID;
    }

    public boolean isBook()
    {
        return isBook;
    }

    public boolean isSold()
    {
        return isSold;
    }

    public static void addIDToPool(int ID)
    {
        if(!IDList.contains(ID))
        {
            IDList.add(ID);
        }
    }
    
    public int getID() {
        return ID;
    }

    /**
     * Marks the product as sold.
     */
    public void sell()
    {
        isSold=true;
    }

    /**
     * Sends an email notification to all the users who favourited this product.
     */
    public void notifyFavouritedUsers()
    {
        for(int i=0;i<favouritedBy.size();i++)
        {
            EmailSender.sendNotification(favouritedBy.get(i).getEmail(), "One of the items you favourited, "+name+", has changed.");
        }
    }

    /**
     * A method to determine if a product will be shown according to the criteria given by the user.
     * @param showBooks true if books checkbox is ticked, false otherwise
     * @param showNotes true if notes checkbox is ticked, false otherwise
     * @param courseDepartment for the product to be shown, it must either be "ALL" or the department of the product
     * @param courseCode for the product to be shown, it must either be 0 or the code of the product, alternatively the department can be "ALL" or "OTHER"
     * @param searchBar the string from the search bar, for the product to be shown the similarity calculated by GenericMethods must be greater than 0.75
     * @param showOnlyFavourites true if the Only favourites checkbox is ticked, false otherwise
     * @param dontShowSold true if the Only Show Available checkbox is ticked, false otherwise
     * @param loggedInUser the user who's currently logged in
     * @return true if all criteria is matched, false otherwise.
     */
    public boolean willBeShown(boolean showBooks, boolean showNotes, String courseDepartment, int courseCode, String searchBar, boolean showOnlyFavourites, boolean dontShowSold, User loggedInUser)
    {
        if(!showBooks&&isBook){return false;}
        if(!showNotes&&!isBook){return false;}
        if(!courseDepartment.equals("ALL")&&!courseDepartment.equals(this.courseDepartment)){return false;}
        if(courseCode!=0&&courseCode!=this.courseCode&&!courseDepartment.equals("OTHER")&&!courseDepartment.equals("ALL")){return false;}
        if(GenericMethods.stringSimilarity(this.name, searchBar)<0.75){return false;}
        if(showOnlyFavourites&&!favouritedBy.contains(loggedInUser)){return false;}
        if(dontShowSold&&isSold){return false;}
        return true;
    }

    /**
     * Creates a JPanel that shows the basic information about a product object. To be used in scrollpanes.
     * @param isProfilePage a boolean that is true if the panel is to be used in a profile page, false otherwise.
     * @param loggedInUser the object for the user that's currently logged in
     * @return 
     */
    public JPanel createPanel(boolean isProfilePage, User loggedInUser, BilBook bilBook)
    {
        JPanel panel=new JPanel();
        panel.setPreferredSize(new Dimension(1000, 200));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        JPanel bookImage=GenericMethods.imageIntoPanel(GenericMethods.fileToImage(image,150));
        JPanel leftPanel=new JPanel();leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS)); leftPanel.add(Box.createRigidArea(new Dimension(20, 1))); leftPanel.add(bookImage);
        JPanel bookInformation=new JPanel(new GridLayout(9,1));
        JLabel name=new JLabel(this.name+" ("+datePublished+")"); JLabel course=new JLabel(courseDepartment+(courseCode==0 ? "" : courseCode)); 
        JLabel date=new JLabel(dateUploaded.toString()); JLabel type=new JLabel("Type: "+ (isBook ? "Book" : "Notes"));
        bookInformation.add(new JLabel()); bookInformation.add(name); bookInformation.add(new JLabel());bookInformation.add(course);bookInformation.add(new JLabel()); bookInformation.add(date); bookInformation.add(new JLabel());bookInformation.add(type);bookInformation.add(new JLabel());
        leftPanel.add(Box.createRigidArea(new Dimension(20, 1)));
        leftPanel.add(bookInformation);
        panel.add(leftPanel, BorderLayout.WEST);
        JPanel rightPanel=new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel pricesIn=new JPanel();
        JPanel pricesOut=new JPanel(); pricesOut.setLayout(new BoxLayout(pricesOut, BoxLayout.X_AXIS));
        JLabel price=new JLabel("Price: "+this.price+" ₺");
        if(isBook)
        {   
            JLabel onlinePrice=new JLabel("Online Price: "+this.onlinePrice+ " ₺");
            JLabel information=new JLabel("Contact Information: "+user.getPhoneNumber());
            pricesIn.setLayout(new GridLayout(7, 1));
            pricesIn.add(new JLabel());pricesIn.add(price); pricesIn.add(new JLabel()); pricesIn.add(onlinePrice); pricesIn.add(new JLabel());pricesIn.add(information);pricesIn.add(new JLabel());
        }
        else
        {
            JLabel information=new JLabel("Contact Information: "+user.getPhoneNumber());
            pricesIn.setLayout(new GridLayout(5, 1));
            pricesIn.add(new JLabel());pricesIn.add(price); pricesIn.add(new JLabel());pricesIn.add(information);pricesIn.add(new JLabel());
        }
        pricesOut.add(pricesIn);
        pricesOut.add(Box.createRigidArea(new Dimension(20,1)));
        rightPanel.add(pricesOut, BorderLayout.WEST);

        if(!isProfilePage)
        {
            JPanel profilePic=GenericMethods.imageIntoPanel(GenericMethods.fileToImage(user.getImageFile(), 140));
            rightPanel.add(profilePic,BorderLayout.CENTER);
        }

        JPanel rightMostIn=new JPanel();
        JPanel rightMostOut=new JPanel();
        rightMostOut.setLayout(new BoxLayout(rightMostOut, BoxLayout.X_AXIS));
        rightMostOut.add(Box.createRigidArea(new Dimension(20, 1)));
        if(loggedInUser!=null&&user.getID()==loggedInUser.getID())
        {
            rightMostIn.setLayout(new BorderLayout());
            JPanel thrashCan=GenericMethods.imageIntoPanel(GenericMethods.THRASH_CAN);
            JPanel star;
            if(isSold)
            {
                star=GenericMethods.imageIntoPanel(GenericMethods.SOLD);
            }
            else
            {
                star=GenericMethods.imageIntoPanel(favouritedBy.contains(loggedInUser) ? GenericMethods.FAVOURITE_STAR : GenericMethods.NOT_FAVOURITE_STAR);
            }
            rightMostIn.add(thrashCan,BorderLayout.NORTH); rightMostIn.add(star,BorderLayout.CENTER); rightMostIn.add(Box.createRigidArea(new Dimension(GenericMethods.FAVOURITE_STAR.getIconWidth()-10, GenericMethods.FAVOURITE_STAR.getIconWidth()-10)),BorderLayout.SOUTH);
        }
        else
        {
            rightMostIn.setLayout(new GridLayout(1, 1));
            JPanel star;
            if(isSold)
            {
                star=GenericMethods.imageIntoPanel(GenericMethods.SOLD);
            }
            else
            {
                star=GenericMethods.imageIntoPanel(loggedInUser==null ? GenericMethods.NOT_FAVOURITE_STAR:(favouritedBy.contains(loggedInUser) ? GenericMethods.FAVOURITE_STAR : GenericMethods.NOT_FAVOURITE_STAR));
            }
            rightMostIn.add(star);
        }
        rightMostOut.add(rightMostIn);
        rightMostOut.add(Box.createRigidArea(new Dimension(20, 1)));
        rightPanel.add(rightMostOut, BorderLayout.EAST);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }


    /**
     * Sorts an arraylist of product objects according to a parameter.
     * @param arrayList arraylist to be sorted.
     * @param code code for the parameter, codes are static final integers found in this class.
     */
    public static void sort(ArrayList<Product> arrayList, int code)
    {
        setUniversalSort(code);
        GenericMethods.sort(arrayList);
        if(code==RANDOM)
        {
            ArrayList<Product> temp=GenericMethods.copyOf(arrayList);
            arrayList.removeAll(arrayList);
            while(temp.size()>0)
            {
                int i=GenericMethods.rand.nextInt(temp.size());
                arrayList.add(temp.get(i));
                temp.remove(i);
            }
        }
    }

    @Override
    public int compareTo(Product o) {
        if(currentSort==PRICE_ASCENDING)
        {
            return (int) Math.signum(price-o.price);
        }
        else if(currentSort==PRICE_DESCENDING)
        {
            return (int) Math.signum(o.price-price);
        }
        else if(currentSort==DATE_ASCENDING)
        {
            return dateUploaded.compareTo(o.dateUploaded);
        }
        else if(currentSort==DATE_DESCENDING)
        {
            return o.dateUploaded.compareTo(dateUploaded);
        }
        else if(currentSort==RANDOM)
        {
            return 0;
        }
        else if(currentSort==INTERNETPRICE_PRICE_RATIO)
        {
            if(!isBook&&o.isBook)
            {
                return 1;
            }
            else if(isBook&&!o.isBook)
            {
                return -1;
            }
            else if(!isBook&&!o.isBook)
            {
                return 0;
            }
            return (int)Math.signum(-(((onlinePrice/price)-o.onlinePrice/o.price)));
        }
        return 0;
    }

}
