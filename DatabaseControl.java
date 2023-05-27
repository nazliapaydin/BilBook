import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that connects to the bilbook database and manipulates it. All methods are static so they can be used uniformly across all classes. 
 * Author: Ata Uzay Kuzey
 */
public class DatabaseControl 
{
    private static Connection connection;
    private static Statement statement;
    private static ArrayList<Product> products;
    private static ArrayList<User> users;

    /**
     * Creates a connection and statement to the database. This method should be called before using any of the other methods.
     */
    public static void setup()
    {
        String url="jdbc:mysql://localhost:3306/BilBook";
        String userName="BilBook";
        String password="GjMrQiHBsM";
        products=null;
        users=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection(url, userName, password);
            statement=connection.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a product to the database.
     * @param product Product object to be added.
     */
    public static void addToDataBase(Product product)
    {
        try
        {
            File imageFile=product.getImageFile();
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            byte[] imageData = new byte[(int) imageFile.length()];
            fileInputStream.read(imageData);
            fileInputStream.close();

            String sql = "INSERT INTO Products (Name, Author, DatePublished, DateUploaded, Price, CourseDepartment, CourseCode,Description ,UserID, ID,IsBook, IsSold, Image, FavouritedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getAuthor());
            preparedStatement.setString(3, product.getDatePublished().toString());
            preparedStatement.setString(4, product.getDateUploaded().toString());
            preparedStatement.setFloat(5, product.getPrice());
            preparedStatement.setString(6, product.getCourseDepartment());
            preparedStatement.setInt(7, product.getCourseCode());
            preparedStatement.setString(8, product.getDescription());
            preparedStatement.setInt(9, product.getUserID());
            preparedStatement.setInt(10, product.getID());
            preparedStatement.setBoolean(11, product.isBook());
            preparedStatement.setBoolean(12, product.isSold());
            preparedStatement.setBytes(13, imageData);
            preparedStatement.setString(14, convertOperation(product.getFavouritedBy()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * A method that creates a string that consists of user ID's with spaces inbetween.
     * @param users user arraylist
     * @return a string with all the ID's of users. This output is readable by a Scanner.
     */
    private static String convertOperation(ArrayList<User> users)
    {
        String result="";
        for(int i=0;i<users.size();i++)
        {
            result+=users.get(i).getID()+" ";
        }

        return result;
    }

    /**
     * Adds a user to the database
     * @param user User object to be added.
     */
    public static void addToDataBase(User user)
    {
        try
        {
            File imageFile=user.getImageFile();
            FileInputStream fileInputStream = new FileInputStream(imageFile);
            byte[] imageData = new byte[(int) imageFile.length()];
            fileInputStream.read(imageData);
            fileInputStream.close();

            String sql = "INSERT INTO Users (Name, Surname, Username, DateCreated, Password, Email, PhoneNumber, NoOfSoldItems, NoOfTotalItems, ID, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getDateCreated().toString());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setInt(7, user.getNumOfSoldItems());
            preparedStatement.setInt(8, user.getNumOfTotalItems());
            preparedStatement.setInt(9, user.getID());
            preparedStatement.setBytes(10, imageData);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns an arraylist of all the products saved in the database.
     * @return An arraylist containing all the products in the database.
     */
    public static ArrayList<Product> getProducts()
    {
        if(products!=null)
        {
            return GenericMethods.copyOf(products);
        }
        products=new ArrayList<>();
        try
        {
            ResultSet resultSet=statement.executeQuery("select * from Products");
            while(resultSet.next())
            {
                Product current=new Product(resultSet.getInt("ID"), resultSet.getBoolean("IsBook"));
                current.setName(resultSet.getString("Name"));
                current.setAuthor(resultSet.getString("Author"));
                current.setCourseDepartment(resultSet.getString("CourseDepartment"));
                current.setCourseCode(resultSet.getInt("CourseCode"));
                current.setDatePublished(GenericMethods.createDate(resultSet.getString("DatePublished")));
                current.setDateUploaded(GenericMethods.createDate(resultSet.getString("DateUploaded")));
                current.setDescription(resultSet.getString("Description"));
                current.setPrice(resultSet.getFloat("Price"));
                current.setUserID(resultSet.getInt("UserID"));
                current.setFavouritedByArray(resultSet.getString("FavouritedBy"));
                if(resultSet.getBoolean("IsSold")){current.sell();}
                Product.addIDToPool(resultSet.getInt("ID"));
                
                byte[] imageData = resultSet.getBytes("Image");
                InputStream imageStream = new ByteArrayInputStream(imageData);
                Path imagePath = Files.createTempFile("product", ".jpg");
                Files.copy(imageStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
                File image = imagePath.toFile();
                current.setImageFile(image);
                current.updateOnlinePrice();
                products.add(current);
            }
            resultSet.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return GenericMethods.copyOf(products);
    }


    /**
     * Returns an arraylist of all the users saved in the database.
     * @return An arraylist containing all the users in the database.
     */
    public static ArrayList<User> getUsers()
    {
        if(users!=null)
        {
            return GenericMethods.copyOf(users);
        }
        users=new ArrayList<>();
        try
        {
            ResultSet resultSet=statement.executeQuery("select * from Users;");
            ArrayList<Product> products=getProducts();
            while(resultSet.next())
            {
                User user=new User(resultSet.getInt("ID"));
                user.setName(resultSet.getString("Name"));
                user.setSurname(resultSet.getString("Surname"));
                user.setMail(resultSet.getString("Email"));
                user.setDateCreated(GenericMethods.createDate(resultSet.getString("DateCreated")));
                user.setPassword(resultSet.getString("Password"));
                user.setSoldItems(resultSet.getInt("NoOfSoldItems"));
                user.setTotalItems(resultSet.getInt("NoOfTotalItems"));

                byte[] imageData = resultSet.getBytes("Image");
                InputStream imageStream = new ByteArrayInputStream(imageData);
                Path imagePath = Files.createTempFile("user", ".jpg");
                Files.copy(imageStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
                File image = imagePath.toFile();
                user.setProfilePic(image);

                for(int i=0;i<products.size();i++)
                {
                    Product current=products.get(i);
                    if(current.getUserID()==user.getID())
                    {
                        current.setUser(user);
                        user.addProduct(current);
                    }

                    Scanner in=new Scanner(current.getFavouritedByArray());
                    while(in.hasNextInt())
                    {
                        if(user.getID()==in.nextInt())
                        {
                            current.addFavouritedBy(user);
                        }
                    }
                    in.close();
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return GenericMethods.copyOf(users);
    }

    /**
     * Removes a product from the database.
     * @param product the product object to be removed
     */
    public static void removeProduct(Product product)
    {
        try
        {
            statement.execute("DELETE FROM Products WHERE ID = "+product.getID());
            products=null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Removes a user from the database.
     * @param user the user object to be removed
     */
    public static void removeUser(User user)
    {
        try
        {
            statement.execute("DELETE FROM Users WHERE ID = "+user.getID());
            users=null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Updates a product that already exists in the database.
     * @param product product to be updated.
     */
    public static void updateProduct(Product product)
    {
        removeProduct(product);
        addToDataBase(product);
        products=null;
    }

    /**
     * Updates a user that already exists in the database.
     * @param user user to be updated.
     */
    public static void updateUser(User user)
    {
        removeUser(user);
        addToDataBase(user);
        users=null;
    }

    /**
     * Closes the connection and statement. This method should be called after all others.
     */
    public static void closeConnection()
    {
        try
        {
            statement.close();
            connection.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Drops all the tables and creates them again. Essentially removes all the items.
     */
    public static void removeAll()
    {
        try
        {
            statement.execute("DROP TABLE Products;");
        }
        catch(Exception e){e.printStackTrace();}
        try
        {
            statement.execute("DROP TABLE Users;");
        }
        catch(Exception e){e.printStackTrace();}
        finally
        {

            try
            {
                statement.execute("CREATE TABLE Products(Name VARCHAR(100),Author VARCHAR(100), DatePublished VARCHAR(10), DateUploaded VARCHAR(10),Price FLOAT(5), CourseDepartment VARCHAR(5), CourseCode INTEGER, Description VARCHAR(1000),UserID INTEGER, ID INTEGER, IsBook BIT, IsSold BIT, Image LONGBLOB, FavouritedBy TEXT);");
                statement.execute("CREATE TABLE Users(Name VARCHAR(50), Surname VARCHAR(20), Username VARCHAR(20), DateCreated VARCHAR(10), Password VARCHAR(20),Email VARCHAR(100), PhoneNumber VARCHAR(15), NoOfSoldItems INTEGER, NoOfTotalItems INTEGER, ID INTEGER, Image LONGBLOB);");
                users=null;
                products=null;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
}
