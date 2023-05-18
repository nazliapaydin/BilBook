import java.awt.Dimension;
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

import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 * A class that connects to the bilbook database and manipulates it. All methods are static so they can be used uniformly across all classes. 
 * Author: Ata Uzay Kuzey
 */
public class DatabaseControl 
{
    private static Connection connection;
    private static Statement statement;

    /**
     * Creates a connection and statement to the database. This method should be called before using any of the other methods.
     */
    public static void setup()
    {
        String url="jdbc:mysql://localhost:3306/BilBook";
        String userName="BilBook";
        String password="GjMrQiHBsM";
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

            String sql = "INSERT INTO Products (Name, Author, DatePublished, DateUploaded, Price, CourseDepartment, CourseCode,Description ,UserID, IsBook, IsSold, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            preparedStatement.setBoolean(10, product.isBook());
            preparedStatement.setBoolean(11, product.isSold());
            preparedStatement.setBytes(12, imageData);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Adds a user to the database
     * @param user User object to be added.
     */
    public static void addToDataBase(User user)
    {
        //TODO
    }

    /**
     * Returns an arraylist of all the products saved in the database.
     * @return An arraylist containing all the products in the database.
     */
    public static ArrayList<Product> getProducts()
    {
        ArrayList<Product> products=new ArrayList<>();
        try
        {
            ResultSet resultSet=statement.executeQuery("select * from Products;");
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
                if(resultSet.getBoolean("IsSold")){current.sell();}
                Product.addIDToPool(resultSet.getInt("ID"));
                
                byte[] imageData = resultSet.getBytes("Image");
                InputStream imageStream = new ByteArrayInputStream(imageData);
                Path imagePath = Files.createTempFile("product", ".jpg");
                Files.copy(imageStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
                File image = imagePath.toFile();
                current.setImageFile(image);

                products.add(current);
            }
            resultSet.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }


    /**
     * Returns an arraylist of all the users saved in the database.
     * @return An arraylist containing all the users in the database.
     */
    public static ArrayList<User> getUsers()
    {
        //TODO
        return null;
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
        //TODO
    }

    /**
     * Updates a product that already exists in the database.
     * @param product product to be updated.
     */
    public static void updateProduct(Product product)
    {
        removeProduct(product);
        addToDataBase(product);
    }

    /**
     * Updates a user that already exists in the database.
     * @param user user to be updated.
     */
    public static void updateUser(User user)
    {
        //TODO
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
        catch(Exception e){
            e.printStackTrace();
        }
        finally
        {
            try
            {
                statement.execute("CREATE TABLE Products(Name VARCHAR(100),Author VARCHAR(100), DatePublished VARCHAR(10), DateUploaded VARCHAR(10),Price FLOAT(5), CourseDepartment VARCHAR(5), CourseCode INTEGER, Description VARCHAR(1000),UserID INTEGER, ID INTEGER, IsBook BIT, IsSold BIT, Image LONGBLOB);");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        setup();
        /*removeAll();
        Product p1=new Product("Big Java: Late Objects", "Cay S. Horstmann", GenericMethods.createDate("26/09/2014"), GenericMethods.createDate("16/05/2023"), 1000, "CS", 101, new User(), true, false,null);
        Product p2=new Product("Calculus", "James Stewart", GenericMethods.createDate("1/1/1987"), GenericMethods.createDate("10/05/2023"), 700, "MATH", 101, new User(), true, false,null);
        Product p3=new Product("Awesome Turkish Writings", "Sealandball", GenericMethods.createDate("15/4/1987"), GenericMethods.createDate("1/05/2023"), 200, "TURK", 102, new User(), false, false,null);
        p1.setDescription("Just like first hand!");
        p2.setDescription("I really like this book hope you do too ^^");
        p3.setDescription("These are my awesome turkish finals who all got 10!!!");
        p3.sell();
        addToDataBase(p1);
        addToDataBase(p2);
        addToDataBase(p3);
        
        Product p4=new Product("Architecture: Form, Space, and Order", "Francis D.K. Ching", GenericMethods.createDate("1/1/1943"), GenericMethods.createDate("17/5/2023"), 500, "FA", 101, new User(), true, false, GenericMethods.chooseFile());
        addToDataBase(p4);
        Product p=new Product("BilBook Homepage", "Bil-Team", GenericMethods.createDate("5/04/2023"), GenericMethods.createDate("18/05/2023"), 10000, "CS", 102, new User(), false, false, GenericMethods.chooseFile());
        addToDataBase(p);*/
        ArrayList<Product> products=getProducts();
        Product.sort(products, Product.INTERNETPRICE_PRICE_RATIO);
        javax.swing.JFrame frame=new javax.swing.JFrame();
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        javax.swing.JPanel panel=new javax.swing.JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(products.get(0).createPanel(false, null).getWidth()+100, 1000));
        javax.swing.JScrollPane scroolpanel=new javax.swing.JScrollPane(panel);
        for(int i=0;i<products.size();i++)
        {
            panel.add(products.get(i).createPanel(false, new User()));
        }
        frame.add(scroolpanel);
        frame.setVisible(true);
        closeConnection();
    }

}
