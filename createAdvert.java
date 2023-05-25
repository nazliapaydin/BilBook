import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
public class createAdvert extends JPanel implements ActionListener{
    JFrame frame;
    JTextField textOfNameOfItem;
    JTextField textOfAuthorOfItem;
    JTextField textOfYear;
    JTextField textOfPrice;
    JLabel labelForNameOfItem;
    JLabel labelForAuthorOfItem;
    JLabel labelForYearOfItem;
    JLabel labelForPriceOfItem;
    JLabel labelForDepartment;
    JLabel labelForCode;
    JLabel labelForCreateAdvert;
    JButton buttonForCreateAdvert;
    JButton buttonForBook;
    JButton buttonForNotes;
    JLabel labelForImage;
    String typeOfItem;
    String name;
    String author;
    String yearPublished;
    String priceOfTheItem;
    String departmentOfItem;
    String codeOfItem;
    JComboBox<String>departmentsList;
    JComboBox<String>codesList;
    
    





   // private Bilbook bilbook;

    public createAdvert(/*Bilbook bilbook*/)
    {
       frameOfCreateAdvert();
    }
    public void frameOfCreateAdvert()
    {
     frame= new JFrame();
     textOfNameOfItem= new JTextField();
    textOfAuthorOfItem= new JTextField();
     textOfYear= new JTextField();
     textOfPrice= new JTextField();
     labelForNameOfItem= new JLabel();
     labelForAuthorOfItem= new JLabel();
     labelForYearOfItem= new JLabel();
     labelForPriceOfItem= new JLabel();
     labelForDepartment= new JLabel();
     labelForCreateAdvert = new JLabel();
     labelForCode= new JLabel();
     buttonForCreateAdvert= new JButton();
     buttonForBook= new JButton();
     buttonForNotes= new JButton();
     departmentsList= new JComboBox<>();
     codesList = new JComboBox<>();
     labelForImage = new JLabel();

        ImageIcon photoImage = new ImageIcon("Photo.png");
        labelForImage.setBounds(450, 350, 265, 271);
        labelForImage.setIcon(photoImage);


        textOfNameOfItem.addActionListener(this);
		textOfNameOfItem.setBounds(210,330,150,30);
		textOfNameOfItem.setFont(new Font("Comic Sans",Font.ITALIC,15));

        textOfAuthorOfItem.addActionListener(this);
		textOfAuthorOfItem.setBounds(210,390,150,30);
		textOfAuthorOfItem.setFont(new Font("Comic Sans",Font.ITALIC,15));

        textOfYear.addActionListener(this);
		textOfYear.setBounds(210,450,150,30);
		textOfYear.setFont(new Font("Comic Sans",Font.BOLD,15));

        textOfPrice.addActionListener(this);
		textOfPrice.setBounds(210,510,150,30);
		textOfPrice.setFont(new Font("Comic Sans",Font.BOLD,15));

        buttonForCreateAdvert = new JButton();
		buttonForCreateAdvert.setBounds(100, 700, 250, 60);
		buttonForCreateAdvert.addActionListener(this);
		buttonForCreateAdvert.setText("Create Advert");
		buttonForCreateAdvert.setFocusable(false);
		buttonForCreateAdvert.setHorizontalTextPosition(JButton.CENTER);
		buttonForCreateAdvert.setVerticalTextPosition(JButton.BOTTOM);
		buttonForCreateAdvert.setFont(new Font("New York Times",Font.ITALIC,30));
		buttonForCreateAdvert.setIconTextGap(-15);
		buttonForCreateAdvert.setForeground(Color.black);
		buttonForCreateAdvert.setBackground(Color.cyan);
		buttonForCreateAdvert.setBorder(BorderFactory.createEtchedBorder());

        buttonForBook = new JButton();
		buttonForBook.setBounds(100, 260, 100, 50);
		buttonForBook.addActionListener(this);
		buttonForBook.setText("Book");
        buttonForBook.setFont(new Font("New York Times",Font.BOLD,20));
		buttonForBook.setFocusable(false);
		buttonForBook.setHorizontalTextPosition(JButton.CENTER);
		buttonForBook.setVerticalTextPosition(JButton.BOTTOM);
		buttonForBook.setIconTextGap(-15);
		buttonForBook.setForeground(Color.black);
		buttonForBook.setBackground(Color.lightGray);
		buttonForBook.setBorder(BorderFactory.createEtchedBorder());

        buttonForNotes = new JButton();
		buttonForNotes.setBounds(220, 260, 100, 50);
		buttonForNotes.addActionListener(this);
		buttonForNotes.setText("Notes");
		buttonForNotes.setFocusable(false);
		buttonForNotes.setHorizontalTextPosition(JButton.CENTER);
		buttonForNotes.setVerticalTextPosition(JButton.BOTTOM);
		buttonForNotes.setFont(new Font("New York Times",Font.BOLD,20));
		buttonForNotes.setIconTextGap(-15);
		buttonForNotes.setForeground(Color.black);
		buttonForNotes.setBackground(Color.lightGray);
		buttonForNotes.setBorder(BorderFactory.createEtchedBorder());

        labelForCreateAdvert.setText("Create Advert ");
        labelForCreateAdvert.setFont(new Font("New York Times",Font.BOLD,30));
		labelForCreateAdvert.setBounds(100,200,250,50);
        labelForCreateAdvert.setBackground(Color.cyan);
        labelForCreateAdvert.setForeground(Color.BLACK);
		labelForCreateAdvert.setVisible(true);
        labelForCreateAdvert.setHorizontalTextPosition(JLabel.CENTER);

        labelForNameOfItem.setText("Name of the Item: ");
		labelForNameOfItem.setBounds(100,320,150,50);
		labelForNameOfItem.setVisible(true);

        labelForAuthorOfItem.setText("Author of the Item: ");
		labelForAuthorOfItem.setBounds(100,380,150,50);
		labelForAuthorOfItem.setVisible(true);

        labelForYearOfItem.setText("Year Created: ");
		labelForYearOfItem.setBounds(100,440,150,50);
		labelForYearOfItem.setVisible(true);

        labelForPriceOfItem.setText("Price: ");
		labelForPriceOfItem.setBounds(100,500,150,50);
		labelForPriceOfItem.setVisible(true);

        labelForDepartment.setText("Department: ");
		labelForDepartment.setBounds(100,560,150,50);
		labelForDepartment.setVisible(true);

        labelForCode.setText("Code: ");
		labelForCode.setBounds(100,620,150,50);
		labelForCode.setVisible(true);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setBounds(0,0,1500,1000);
        frame.setBackground(Color.gray);
		frame.setVisible(true);
		frame.add(textOfNameOfItem);
		frame.add(textOfAuthorOfItem);
		frame.add(textOfYear);
		frame.add(textOfPrice);
		frame.add(labelForAuthorOfItem);
		frame.add(labelForDepartment);
        frame.add(labelForImage);
        frame.add(labelForNameOfItem);
        frame.add(labelForYearOfItem);
        frame.add(labelForCreateAdvert);
        frame.add(labelForCode);
        frame.add(labelForPriceOfItem);
        frame.add(buttonForCreateAdvert);
        frame.add(buttonForBook);
        frame.add(buttonForNotes);
        comboBoxforDepartments(datasOfLectures.getDepartments());
    } 
    public void comboBoxforDepartments(String []departments)
    {
        departmentsList = new JComboBox<String>(departments);
        //index hep 0 geliyor comboboxla buton kombinlemek mi lazım??
        int index = (int)departmentsList.getSelectedIndex();
        codesList = new JComboBox<String>(datasOfLectures.getCodes(index));
        departmentsList.addActionListener(this);
        codesList.addActionListener(this);
        departmentsList.setBounds(210, 560, 150, 40);
        codesList.setBounds(210, 620, 150, 40);
        codesList.setMaximumRowCount(5);
        codesList.setForeground(Color.black);
        codesList.setFont(new Font("Arial", Font.ITALIC, 14));
        departmentsList.setMaximumRowCount(5);
        departmentsList.setForeground(Color.black);
        departmentsList.setFont(new Font("Arial", Font.ITALIC, 14));
        frame.add(departmentsList);
        frame.add(codesList);
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
     if(e.getSource()==buttonForBook)
     {
        typeOfItem="Book";
     }
     if(e.getSource()==buttonForCreateAdvert)
     {
        name=textOfNameOfItem.getText();
        author=textOfAuthorOfItem.getText();
        yearPublished=textOfYear.getText();
        priceOfTheItem=textOfPrice.getText();
        System.out.println(name);
        //departmentOfItem;
        //codeOfItem;
     }
     if(e.getSource()==buttonForNotes)
     {
        typeOfItem="Note";
     }
     if(e.getSource()==departmentsList)
     {
        departmentOfItem = (String) departmentsList.getSelectedItem();
     }
     if(e.getSource()==codesList)
     {
        codeOfItem= (String) codesList.getSelectedItem();
     }
    }

    public static void main(String[] args) {
        createAdvert advert=new createAdvert();
        advert.frameOfCreateAdvert();
    }
}
