import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
public class signUp extends JPanel implements ActionListener{
	private BilBook bilbook;
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
	JPanel panelForImage;
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


    public signUp(BilBook bilbook)
    {
		this.bilbook=bilbook;
		frameOfSignUp();
		panel.setBackground(new Color(234, 234, 234));
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
        buttonForForgottenPassword= new JButton();


		panelForImage=bilbook.createLogo();
		panelForImage.setBounds(150, 220, 570, 200);

        textOfName.addActionListener(this);
		textOfName.setBounds(860,142,150,20);
		textOfName.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfSurname.addActionListener(this);
		textOfSurname.setBounds(860,190,150,20);
		textOfSurname.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfUsername.addActionListener(this);
		textOfUsername.setBounds(860,240,150,20);
		textOfUsername.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfEmail.addActionListener(this);
		textOfEmail.setBounds(860,290,150,20);
		textOfEmail.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfPhoneNumber.addActionListener(this);
		textOfPhoneNumber.setBounds(860,340,150,20);
		textOfPhoneNumber.setFont(new Font("Comic Sans",Font.BOLD,12));


        textOfPassword.addActionListener(this);
		textOfPassword.setBounds(860,390,150,20);
		textOfPassword.setFont(new Font("Comic Sans",Font.BOLD,12));

        textOfPasswordAgain.addActionListener(this);
		textOfPasswordAgain.setBounds(860,440,150,20);
		textOfPasswordAgain.setFont(new Font("Comic Sans",Font.BOLD,12));

        buttonForSignUp.setBounds(750, 490, 300, 50);
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

        buttonForLogIn.setBounds(905, 555, 150, 20);
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
		labelForSignUp.setBounds(750,50,150,100);
		labelForSignUp.setVisible(true);
		labelForSignUp.setFont(new Font("New York Times",Font.BOLD,35));

        labelForName.setText("Name: ");
		labelForName.setBounds(750,100,100,100);
		labelForName.setVisible(true);

        labelForSurName.setText("Surname: ");
		labelForSurName.setBounds(750,150,100,100);
		labelForSurName.setVisible(true);

        labelForUserName.setText("Username: ");
		labelForUserName.setBounds(750,200,100,100);
		labelForUserName.setVisible(true);

        labelForEmailName.setText("E-Mail: ");
		labelForEmailName.setBounds(750,250,100,100);
		labelForEmailName.setVisible(true);

        labelForPhoneNumber.setText("Phone Number: ");
		labelForPhoneNumber.setBounds(750,300,150,100);
		labelForPhoneNumber.setVisible(true);

        labelForPassword.setText("Password: ");
		labelForPassword.setBounds(750,350,100,100);
		labelForPassword.setVisible(true);

        labelForPasswordAgain.setText("Password Again: ");
		labelForPasswordAgain.setBounds(750,400,150,100);
		labelForPasswordAgain.setVisible(true);

        labelForAlreadyAccount.setText("Already have an account?");
		labelForAlreadyAccount.setBounds(750,540,160,50);
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
        panel.add(panelForImage);
        panel.add(buttonForForgottenPassword);
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

            password=GenericMethods.passwordFieldToString(textOfPassword);

            passwordAgain=GenericMethods.passwordFieldToString(textOfPasswordAgain);
			/*
			 * TO DO 
			 * IMPLEMENT THESE PASSWORD FIELD TO GENERIC METHODS
			 */
			if(name==null||name.equals(""))
			{
				PopUpManager.faultyCreation(bilbook, "Enter all of the information.");
			}
            else if(surname==null||surname.equals(""))
			{
				PopUpManager.faultyCreation(bilbook, "Enter all of the information.");
			}
            else if(username==null||username.equals(""))
			{
				PopUpManager.faultyCreation(bilbook, "Enter all of the information.");
			}
            else if(email==null||email.equals(""))
			{
				PopUpManager.faultyCreation(bilbook, "Enter all of the information.");
			}
            else if(phoneNumber==null||phoneNumber.equals(""))
			{
				PopUpManager.faultyCreation(bilbook, "Enter all of the information.");
			}
			else if(password==null||password.equals(""))
			{
				PopUpManager.faultyCreation(bilbook, "Enter all of the information.");
			}
            else if(passwordAgain==null||passwordAgain.equals(""))
			{
				PopUpManager.faultyCreation(bilbook, "Passwords are different.");
			}
			else if(validation)
			{
				if(bilbook.allowSignUp(username, email))
				{
					User user=new User(name, surname, username, password, GenericMethods.getCurrentDate(), phoneNumber, email, null);
					bilbook.addUser(user);
					bilbook.changePanel((new logIn(bilbook)).getPanel());
				}
				else
				{
					PopUpManager.faultyCreation(bilbook, "There is someone with identical username or email.");
				}
			}
        }
        if(e.getSource()==buttonForLogIn)
        {
			bilbook.changePanel((new logIn(bilbook)).getPanel());
        }
        if(e.getSource()==buttonForForgottenPassword)
        {
			PopUpManager.emailConfirmationPopup(bilbook);
        }
        
    }

	public JPanel getPanel() {
		return panel;
	}
}
