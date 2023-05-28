import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
public class createAdvert extends JPanel implements ActionListener{
    JPanel panel;
    JPanel panelForRadioButtons;
    JTextField textOfNameOfItem;
    JTextField textOfAuthorOfItem;
    JTextField textOfYear;
    JTextField textOfPrice;
    JTextField textOfImageLink;
    JLabel labelForNameOfItem;
    JLabel labelForAuthorOfItem;
    JLabel labelForYearOfItem;
    JLabel labelForPriceOfItem;
    JLabel labelForDepartment;
    JLabel labelForCode;
    JLabel labelForCreateAdvert;
    JLabel labelForImageLink;
    JButton buttonForCreateAdvert;
    JRadioButton radioButtonBook;
    JRadioButton radioButtonNotes;
    JButton buttonForAddingImage;
    ButtonGroup bg;
    JButton buttonForImage;
    String typeOfItem;
    String name;
    String author;
    String yearPublished;
    String priceOfTheItem;
    String departmentOfItem;
    String codeOfItem;
    JComboBox<String>departmentsList;
    JComboBox<String>codesList;
    Box box1;
    int index = 0;
    String selectedItem = datasOfLectures.getDepartments()[index];

    // private Bilbook bilbook;

    public createAdvert(/*Bilbook bilbook*/)
    {
        //frameOfCreateAdvert();
        //this.bilbook=bilbook;
    }
    public void frameOfCreateAdvert()
    {
        panel= new JPanel();
        textOfNameOfItem= new JTextField();
        textOfAuthorOfItem= new JTextField();
        textOfYear= new JTextField();
        textOfPrice= new JTextField();
        textOfImageLink = new JTextField();
        labelForNameOfItem= new JLabel();
        labelForAuthorOfItem= new JLabel();
        labelForYearOfItem= new JLabel();
        labelForPriceOfItem= new JLabel();
        labelForDepartment= new JLabel();
        labelForCreateAdvert = new JLabel();
        labelForCode= new JLabel();
        labelForImageLink= new JLabel();
        buttonForCreateAdvert= new JButton();
        buttonForAddingImage = new JButton();
        panelForRadioButtons= new JPanel();
        radioButtonBook= new JRadioButton("Book");
        radioButtonNotes= new JRadioButton("Notes");
        bg= new ButtonGroup();
        departmentsList= new JComboBox<>();
        codesList = new JComboBox<>();
        buttonForImage = new JButton();

        ImageIcon photoImage = new ImageIcon("Photo.png");
        buttonForImage.setBounds(450, 350, 265, 271);
        buttonForImage.setIcon(photoImage);
        buttonForImage.addActionListener(this);


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


       

        JRadioButton radioButtonBook = new javax.swing.JRadioButton("Book");
        JRadioButton radioButtonNotes = new javax.swing.JRadioButton("Notes");
        bg.add(radioButtonNotes);
        bg.add(radioButtonBook);
        //radioButtonBook.setText("Book");
        //radioButtonNotes.setText("Notes");
        radioButtonBook.addActionListener(this);
        radioButtonBook.setFocusable(false);
        radioButtonBook.setHorizontalTextPosition(JButton.CENTER);
       radioButtonBook.setVerticalTextPosition(JButton.BOTTOM);
        radioButtonBook.setIconTextGap(-15);
        radioButtonBook.setForeground(Color.black);
        radioButtonBook.setBorder(BorderFactory.createEtchedBorder());
       radioButtonBook.setBounds(130, 260, 100, 50);
        radioButtonBook.setFont(new Font("New York Times",Font.ITALIC,12));
        radioButtonBook.setVisible(true);


        radioButtonNotes.addActionListener(this);
        radioButtonNotes.setFocusable(false);
        radioButtonNotes.setHorizontalTextPosition(JButton.CENTER);
        radioButtonNotes.setVerticalTextPosition(JButton.BOTTOM);
        radioButtonNotes.setIconTextGap(-15);
        radioButtonNotes.setForeground(Color.black);
        radioButtonNotes.setBorder(BorderFactory.createEtchedBorder());
        radioButtonNotes.setBounds(250, 260, 100, 50);
        radioButtonNotes.setFont(new Font("New York Times",Font.ITALIC,12));
        radioButtonNotes.setVisible(true);
        radioButtonNotes.setSelected(true);

        
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



       // panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);
        panel.setBounds(0,0,1500,1000);
        panel.setBackground(Color.gray);
        panel.setVisible(true);
        panel.add(textOfNameOfItem);
        panel.add(textOfAuthorOfItem);
        panel.add(textOfYear);
        panel.add(textOfPrice);
        panel.add(labelForAuthorOfItem);
        panel.add(labelForDepartment);
        panel.add(buttonForImage);
        panel.add(labelForNameOfItem);
        panel.add(labelForYearOfItem);
        panel.add(labelForCreateAdvert);
        panel.add(labelForCode);
        panel.add(labelForPriceOfItem);
        panel.add(buttonForCreateAdvert);
        panel.add(panelForRadioButtons);
        panel.add(radioButtonBook);
        panel.add(radioButtonNotes);
      
        comboBoxforDepartments(datasOfLectures.getDepartments());
    }
    public void comboBoxforDepartments(String []departments)
    {
        departmentsList = new JComboBox<String>(departments);
        //int index = (int)departmentsList.getSelectedIndex();
        departmentsList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Selected: " + departmentsList.getSelectedItem());
               // System.out.println("Selected: " + departmentsList.getSelectedItem().toString());
                //System.out.println(", Position: " + departmentsList.getSelectedIndex());
                index = departmentsList.getSelectedIndex();
                String[] newCodeList = datasOfLectures.getCodes(index);
                codesList.removeAllItems();
                for (String code: newCodeList) {
                    codesList.addItem(code);
                }
            }
        });
        codesList = new JComboBox<String>(datasOfLectures.getCodes(index));
        codesList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Impelement Here

            }
        });

        departmentsList.setBounds(210, 560, 150, 40);
        codesList.setBounds(210, 620, 150, 40);
        codesList.setMaximumRowCount(5);
        codesList.setForeground(Color.black);
        codesList.setFont(new Font("Arial", Font.ITALIC, 14));
        departmentsList.setMaximumRowCount(5);
        departmentsList.setForeground(Color.black);
        departmentsList.setFont(new Font("Arial", Font.ITALIC, 14));
        panel.add(departmentsList);
        panel.add(codesList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==buttonForImage)
        {
            labelForImageLink.setText("Link of Image: ");
            labelForImageLink.setBounds(450,450,150,50);
            labelForImageLink.setVisible(true);

            textOfImageLink.addActionListener(this);
            textOfImageLink.setBounds(550,450,150,20);
            textOfImageLink.setFont(new Font("Comic Sans",Font.BOLD,15));

            buttonForAddingImage.setBounds(710, 450, 250, 60);
            buttonForAddingImage.addActionListener(this);
            buttonForAddingImage.setText("Okey");
            buttonForAddingImage.setFocusable(false);
            buttonForAddingImage.setHorizontalTextPosition(JButton.CENTER);
            buttonForAddingImage.setVerticalTextPosition(JButton.BOTTOM);
            buttonForAddingImage.setFont(new Font("New York Times",Font.ITALIC,12));
            buttonForAddingImage.setIconTextGap(-15);
            buttonForAddingImage.setForeground(Color.black);
            buttonForAddingImage.setBackground(Color.cyan);
            buttonForAddingImage.setBorder(BorderFactory.createEtchedBorder());

            panel.add(labelForImageLink);
            panel.add(textOfImageLink);
            panel.add(buttonForAddingImage);
            ImageIcon photoOfSource = new ImageIcon(textOfImageLink.getText());
            /*
             * ADD EXCEPTIONS
             */
            if(e.getSource()==buttonForAddingImage)
            {
            labelForImageLink.setVisible(false);
            textOfImageLink.setVisible(false);
            buttonForAddingImage.setVisible(false);
            JLabel labelForPhotoOfSource= new JLabel(); 
            labelForPhotoOfSource.setBounds(450, 350, 265, 271);
            labelForPhotoOfSource.setIcon(photoOfSource);
            panel.add(labelForPhotoOfSource);
            }
        }

        if(radioButtonBook.isSelected()==true)
        {
            typeOfItem="Book";
            System.out.println(typeOfItem);
        }
        if(radioButtonNotes.isSelected()==true)
        {
            typeOfItem="Notes";
            System.out.println(typeOfItem);
        }
       
        if(e.getSource()==buttonForCreateAdvert)
        {
            name=textOfNameOfItem.getText();
            author=textOfAuthorOfItem.getText();
            yearPublished=textOfYear.getText();
            priceOfTheItem=textOfPrice.getText();
            System.out.println(name);
        }
        if(radioButtonNotes.isSelected()==true)
        {
            typeOfItem="Note";
            System.out.println(typeOfItem);
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