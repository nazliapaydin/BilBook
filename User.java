package src;

import java.io.File;
import java.util.ArrayList;

import src.GenericMethods.Date;

/**
 * A class to represent a User object.
 * Author: Nazlı Apaydın
 */
public class User
{
    private int ID;
    private String password;
    private String name; 
    private String surname;
    private int numOfSoldItems;
    private int numOfTotalItems;
    private String username;
    private Date dateCreated;
    private String phoneNumber;
    private String mail;
    private File profilePic;
    private static ArrayList<Integer> uniqueIDList = new ArrayList<Integer>();
    private ArrayList<Product> books;

    /**
     * @param username
     * @param dateCreated
     * @param phoneNumber
     * @param mail
     * @param image
     */
    public User(String name, String surname, String username, String password, Date dateCreated, String phoneNumber, String mail, File image)
    {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
        this.dateCreated = dateCreated; //cannot be changed later
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.profilePic = image;

        if(image==null)
        {
            this.profilePic= new File("profile_default.png");
        }
        else
        {
            this.profilePic=image;
        }

        do
        {
            this.ID=GenericMethods.rand.nextInt(Integer.MAX_VALUE); //cannot be changed later
        }
        while(uniqueIDList.contains(this.ID));
        
    }

    public User(int ID)
    {
        this.ID = ID;
        uniqueIDList.add(this.ID);
    }

    //getters
    public int getID()
    {
        return this.ID;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getName()
    {
        return this.name;
    }

    public String getSurname()
    {
        return this.surname;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public String getEmail()
    {
        return this.mail;
    }

    public File getImageFile()
    {
        return  this.profilePic;
    }

    public Date getDateCreated()
    {
        return this.dateCreated;
    }

    public int getNumOfSoldItems()
    {
        return this.numOfSoldItems;
    }

    public int getNumOfTotalItems()
    {
        return this.numOfTotalItems;
    }

    //setters
    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setProfilePic(File image)
    {
        this.profilePic = image;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public void changeSoldItems(int change)
    {
        numOfSoldItems+=change;
    }

    public void changeTotalItems(int change)
    {
        numOfTotalItems+=change;
    }

    public void addBook(Product product)
    {
        books.add(product);
    }

    public void removeBook( Product product)
    {
        books.remove(product);
    }

    public ArrayList<Product> getBooks()
    {
        return GenericMethods.copyOf(books);
    }

    public void removeUser(User user)
    {
        uniqueIDList.remove(user.ID);
    }


    @Override
    public String toString() 
    {
        return "Name: " + getName() + " | Surname: " + getSurname() + " |  ID: " + getID() + " | Username: " + getUsername() + " | Email: " + getEmail() + " | Phone Number: " + getPhoneNumber(); //TODO 
    }

}
