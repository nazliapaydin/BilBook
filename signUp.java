import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
public class signUp extends JPanel implements ActionListener{
	//private Bilbook bilbook;
    JTextField textOfName;
	JTextField textOfSurname;
    JTextField textOfUsername;
    JTextField textOfEmail;
    JTextField textOfPhoneNumber;
    JPasswordField textOfPassword;
    JPasswordField textOfPasswordAgain;
	JButton buttonForSignUp;
    JButton buttonForLogIn;
	JPanel panel;
	JLabel labelForLogIn;
    JLabel labelForSignUp;
    JLabel labelForName;
    JLabel labelForSurName;
    JLabel labelForUserName;
    JLabel labelForEmailName;
    JLabel labelForPhoneNumber;
    JLabel labelForPassword;
	JLabel labelForPasswordAgain;
	JLabel labelForAlreadyAccount;
	JLabel labelForImage;
	JButton buttonForForgottenPassword;
    String name;
    String surname;
	String username;
	String email;
    String phoneNumber;
    String password;
    String passwordAgain;
    JLabel labelForInvalidName;
    JLabel labelForInvalidSurname;
    JLabel labelForInvalidUsername;
    JLabel labelForInvalidEmail;
    JLabel labelForInvalidPhoneNumber;
    JLabel labelForInvalidPassword;
    JLabel labelForInvalidPasswordAgain;


    public signUp(/*Bilbook bilbook*/)
    {
		//this.bilbook=bilbook
    }
    public void frameOfSignUp()
    {
        panel= new JPanel();
        textOfName= new JTextField();
    	textOfSurname= new JTextField();
        textOfUsername= new JTextField();
        textOfEmail= new JTextField();
        textOfPhoneNumber= new JTextField();
        textOfPassword= new JPasswordField();
        textOfPasswordAgain= new JPasswordField();
        buttonForSignUp= new JButton();
        buttonForLogIn= new JButton();
        labelForLogIn= new JLabel();
        labelForSignUp= new JLabel();
        labelForName= new JLabel();
        labelForSurName= new JLabel();
        labelForUserName= new JLabel();
        labelForEmailName= new JLabel();
        labelForPhoneNumber= new JLabel();
        labelForPassword= new JLabel();
        labelForPasswordAgain= new JLabel();
        labelForAlreadyAccount= new JLabel();
        labelForImage= new JLabel();
        buttonForForgottenPassword= new JButton();


        ImageIcon BilBookImage = new ImageIcon("BilBookImage.png");
		labelForImage.setBounds(150, 220, 346, 147);
		labelForImage.setIcon(BilBookImage);

        textOfName.addActionListener(this);
		textOfName.setBounds(660,142,150,20);
		textOfName.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfSurname.addActionListener(this);
		textOfSurname.setBounds(660,190,150,20);
		textOfSurname.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfUsername.addActionListener(this);
		textOfUsername.setBounds(660,240,150,20);
		textOfUsername.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfEmail.addActionListener(this);
		textOfEmail.setBounds(660,290,150,20);
		textOfEmail.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfPhoneNumber.addActionListener(this);
		textOfPhoneNumber.setBounds(660,340,150,20);
		textOfPhoneNumber.setFont(new Font("Comic Sans",Font.BOLD,12));


        textOfPassword.addActionListener(this);
		textOfPassword.setBounds(660,390,150,20);
		textOfPassword.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfPasswordAgain.addActionListener(this);
		textOfPasswordAgain.setBounds(660,440,150,20);
		textOfPasswordAgain.setFont(new Font("Comic Sans",Font.BOLD,12));

        buttonForSignUp.setBounds(550, 490, 300, 50);
		buttonForSignUp.addActionListener(this);
		buttonForSignUp.setText("Sign Up!");
		buttonForSignUp.setFocusable(false);
		buttonForSignUp.setHorizontalTextPosition(JButton.CENTER);
		buttonForSignUp.setVerticalTextPosition(JButton.BOTTOM);
		buttonForSignUp.setFont(new Font("New York Times",Font.BOLD,30));
		buttonForSignUp.setIconTextGap(-15);
		buttonForSignUp.setForeground(Color.black);
		buttonForSignUp.setBackground(Color.cyan);
		buttonForSignUp.setBorder(BorderFactory.createEtchedBorder());

        buttonForLogIn.setBounds(725, 555, 150, 20);
		buttonForLogIn.addActionListener(this);
		buttonForLogIn.setText("Log In");
		buttonForLogIn.setFocusable(false);
		buttonForLogIn.setHorizontalTextPosition(JButton.CENTER);
		buttonForLogIn.setVerticalTextPosition(JButton.BOTTOM);
		buttonForLogIn.setFont(new Font("New York Times",Font.BOLD,13));
		buttonForLogIn.setIconTextGap(-15);
		buttonForLogIn.setForeground(Color.black);
		//buttonForLogIn.setBackground(Color.cyan);
		buttonForLogIn.setBorder(BorderFactory.createEtchedBorder());

      

        labelForSignUp.setText("Sign up ");
        labelForSignUp.setForeground(Color.cyan);
		labelForSignUp.setBounds(550,50,150,100);
		labelForSignUp.setVisible(true);
		labelForSignUp.setFont(new Font("New York Times",Font.BOLD,35));

        labelForName.setText("Name: ");
		labelForName.setBounds(550,100,100,100);
		labelForName.setVisible(true);

        labelForSurName.setText("Surname: ");
		labelForSurName.setBounds(550,150,100,100);
		labelForSurName.setVisible(true);

        labelForUserName.setText("Username: ");
		labelForUserName.setBounds(550,200,100,100);
		labelForUserName.setVisible(true);

        labelForEmailName.setText("E-Mail: ");
		labelForEmailName.setBounds(550,250,100,100);
		labelForEmailName.setVisible(true);

        labelForPhoneNumber.setText("Phone Number: ");
		labelForPhoneNumber.setBounds(550,300,150,100);
		labelForPhoneNumber.setVisible(true);

        labelForPassword.setText("Password: ");
		labelForPassword.setBounds(550,350,100,100);
		labelForPassword.setVisible(true);

        labelForPasswordAgain.setText("Password Again: ");
		labelForPasswordAgain.setBounds(550,400,150,100);
		labelForPasswordAgain.setVisible(true);

        labelForAlreadyAccount.setText("Already have an account?");
		labelForAlreadyAccount.setBounds(570,540,160,50);
		labelForAlreadyAccount.setVisible(true);


    

       // panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
		panel.setBounds(0,0,1500,1000);
        panel.setBackground(Color.gray);
		panel.setVisible(true);
		panel.add(textOfName);
        panel.add(textOfUsername);
        panel.add(textOfSurname);
        panel.add(textOfEmail);
        panel.add(textOfPhoneNumber);
        panel.add(textOfPassword);
        panel.add(textOfPasswordAgain);
        panel.add(buttonForSignUp);
        panel.add(buttonForLogIn);
        panel.add(labelForLogIn);
        panel.add(labelForSignUp);
        panel.add(labelForName);
        panel.add(labelForSurName);
        panel.add(labelForUserName);
        panel.add(labelForEmailName);
        panel.add(labelForPhoneNumber);
        panel.add(labelForPassword);
        panel.add(labelForPasswordAgain);
        panel.add(labelForAlreadyAccount);
        panel.add(labelForImage);
        panel.add(buttonForForgottenPassword);
    }
	public static void main(String[] args) {
		signUp signnn= new signUp();
		signnn.frameOfSignUp();
	}
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==buttonForSignUp)
        {
			boolean validation =true;
            name=textOfName.getText();
            surname=textOfSurname.getText();
            username=textOfUsername.getText();
            email=textOfEmail.getText();
            phoneNumber=textOfPhoneNumber.getText();
			//passwordFieldToString();
            password=textOfPassword.getText();
			//passwordFieldToString();
            passwordAgain=textOfPasswordAgain.getText();
			/*
			 * TO DO 
			 * IMPLEMENT THESE PASSWORD FIELD TO GENERIC METHODS
			 */
			if(name==null)
			{
				labelForInvalidName = new JLabel();
				labelForInvalidName.setText("Invalid name ");
				labelForInvalidName.setBounds(660,142,150,20);
				labelForInvalidName.setVisible(true);
				panel.add(labelForInvalidName);
				validation =false;

			}
            if(surname==null)
			{
				labelForInvalidSurname = new JLabel();
				labelForInvalidSurname.setText("Invalid surname ");
				labelForInvalidSurname.setBounds(660,190,150,20);
				labelForInvalidSurname.setVisible(true);
				panel.add(labelForInvalidSurname);
				validation =false;
			}
            if(username==null)
			{
				labelForInvalidUsername = new JLabel();
				labelForInvalidUsername.setText("Invalid username ");
				labelForInvalidUsername.setBounds(660,240,150,20);
				labelForInvalidUsername.setVisible(true);
				panel.add(labelForInvalidUsername);
				validation =false;
			}
            if(email==null)
			{
				labelForInvalidEmail = new JLabel();
				labelForInvalidEmail.setText("Invalid E-Mail ");
				labelForInvalidEmail.setBounds(660,290,150,20);
				labelForInvalidEmail.setVisible(true);
				panel.add(labelForInvalidEmail);
				validation =false;
			}
            if(phoneNumber==null)
			{
				labelForInvalidPhoneNumber = new JLabel();
				labelForInvalidPhoneNumber.setText("Invalid Phone number ");
				labelForInvalidPhoneNumber.setBounds(660,340,150,20);
				labelForInvalidPhoneNumber.setVisible(true);
				panel.add(labelForInvalidPhoneNumber);
				validation =false;
			}
			if(password==null)
			{
				labelForInvalidPassword = new JLabel();
				labelForInvalidPassword.setText("Invalid password ");
				labelForInvalidPassword.setBounds(660,390,150,20);
				labelForInvalidPassword.setVisible(true);
				panel.add(labelForInvalidPassword);
				validation =false;
			}
            if(passwordAgain==null)
			{
				labelForInvalidPasswordAgain = new JLabel();
				labelForInvalidPasswordAgain.setText("Invalid password again ");
				labelForInvalidPasswordAgain.setBounds(660,440,150,20);
				labelForInvalidPasswordAgain.setVisible(true);
				panel.add(labelForInvalidPasswordAgain);
				validation =false;
			}
				if(validation&&password.equals(passwordAgain))
				{

				}
        }
        if(e.getSource()==buttonForLogIn)
        {
            //Find the code of frame finisher
            logIn newLogIn = new logIn();
            newLogIn.frameOfLogIn();
        }
        if(e.getSource()==buttonForForgottenPassword)
        {

        }
        
    }
}