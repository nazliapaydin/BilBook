import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import java.awt.Font;
import java.awt.Color;
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
    JLabel warning;
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
    GenericMethods.ChangeableImage buttonForImage;
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

    private BilBook bilbook;

    public createAdvert(BilBook bilbook)
    {
        this.bilbook=bilbook;
        frameOfCreateAdvert();
        panel.setBackground(new Color(238, 238, 238));
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
        JPanel menuBar=bilbook.createMenuBar();
        bg= new ButtonGroup();
        departmentsList= new JComboBox<>();
        codesList = new JComboBox<>();
        buttonForImage = GenericMethods.createChangeableImage();
        warning=new JLabel();

        buttonForImage.setBounds(450, 350, 265, 271);
        
        menuBar.setBounds(0, 0, bilbook.getWidth(), 200);

        textOfNameOfItem.setBounds(210,330,150,30);
        textOfNameOfItem.setFont(new Font("Comic Sans",Font.ITALIC,15));

        textOfAuthorOfItem.setBounds(210,390,150,30);
        textOfAuthorOfItem.setFont(new Font("Comic Sans",Font.ITALIC,15));

        textOfYear.setBounds(210,450,150,30);
        textOfYear.setFont(new Font("Comic Sans",Font.BOLD,15));

        textOfPrice.setBounds(210,510,150,30);
        textOfPrice.setFont(new Font("Comic Sans",Font.BOLD,15));



        warning.setBounds(130, 650, 160, 30);
        buttonForCreateAdvert.setBounds(100, 700, 250, 60);
        buttonForCreateAdvert.setText("Create Advert");
        buttonForCreateAdvert.setFocusable(false);
        buttonForCreateAdvert.setHorizontalTextPosition(JButton.CENTER);
        buttonForCreateAdvert.setVerticalTextPosition(JButton.BOTTOM);
        buttonForCreateAdvert.setFont(new Font("New York Times",Font.ITALIC,30));
        buttonForCreateAdvert.setIconTextGap(-15);
        buttonForCreateAdvert.setForeground(Color.black);
        buttonForCreateAdvert.setBackground(Color.cyan);
        buttonForCreateAdvert.setBorder(BorderFactory.createEtchedBorder());
        radioButtonBook = new javax.swing.JRadioButton("Book");
        radioButtonNotes = new javax.swing.JRadioButton("Notes");
        bg.add(radioButtonNotes);
        bg.add(radioButtonBook);
        //radioButtonBook.setText("Book");
        //radioButtonNotes.setText("Notes");
        radioButtonBook.setFocusable(false);radioButtonBook.setSelected(true);
        radioButtonBook.setHorizontalTextPosition(JButton.CENTER);
       radioButtonBook.setVerticalTextPosition(JButton.BOTTOM);
        radioButtonBook.setIconTextGap(1);
        radioButtonBook.setForeground(Color.black);
        radioButtonBook.setBorder(BorderFactory.createEtchedBorder());
       radioButtonBook.setBounds(130, 260, 100, 50);
        radioButtonBook.setFont(new Font("New York Times",Font.ITALIC,12));
        radioButtonBook.setVisible(true);


        radioButtonNotes.setFocusable(false);
        radioButtonNotes.setHorizontalTextPosition(JButton.CENTER);
        radioButtonNotes.setVerticalTextPosition(JButton.BOTTOM);
        radioButtonNotes.setIconTextGap(1);
        radioButtonNotes.setForeground(Color.black);
        radioButtonNotes.setBorder(BorderFactory.createEtchedBorder());
        radioButtonNotes.setBounds(250, 260, 100, 50);
        radioButtonNotes.setFont(new Font("New York Times",Font.ITALIC,12));
        radioButtonNotes.setVisible(true);

        
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
        panel.add(menuBar);
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
        buttonForCreateAdvert.addActionListener(this);
        comboBoxforDepartments(datasOfLectures.getDepartments());
    }
    public void comboBoxforDepartments(String [] departments)
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
        String name=textOfNameOfItem.getText();
        String author=textOfAuthorOfItem.getText();
        String price=textOfPrice.getText();
        String year=textOfYear.getText();
        
        if(name==null||author==null||price==null||year==null)
        {
            PopUpManager.faultyCreation(bilbook, "Please enter all the information!");
        }
        else if(GenericMethods.isInappropriate(name+author))
        {
            PopUpManager.faultyCreation(bilbook, "Please do not enter inappropriate words!");
        }
        else
        {
            try
            {
                float pricef=Float.parseFloat(price);
                int yeari=Integer.parseInt(year);
                bilbook.getLoggedIn().changeTotalItems(1);
                Product product=new Product(name, author, GenericMethods.createDate(1, 1, yeari), GenericMethods.getCurrentDate(), pricef, (String)departmentsList.getSelectedItem(), codesList.getSelectedItem().equals("ALL")? 0:Integer.parseInt((String)codesList.getSelectedItem()), bilbook.getLoggedIn(), radioButtonBook.isSelected() ? true: false, false, buttonForImage.getImage());
                bilbook.addProduct(product);
                bilbook.changePanel(new ProductPage(bilbook, product));
                DatabaseControl.updateUser(bilbook.getLoggedIn());;
            }
            catch(Exception ee)
            {
                PopUpManager.faultyCreation(bilbook, "Please enter numbers!");
            }   
        }

    }

    public JPanel getPanel() {
        return panel;
    }

}
