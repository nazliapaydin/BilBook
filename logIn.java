import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;

public class logIn extends JPanel implements ActionListener{
	JTextField textOfUsername;
	JTextField textOfPassword;
	JButton buttonForLogIn;
	JButton buttonForSignUp;
	JFrame frame;
	JLabel labelForLogIn;
	JLabel labelForUserName;
	JLabel labelForPassword;
	JLabel labelForNewAccount;
	JLabel labelForImage;
	JButton buttonForForgottenPassword;
	String username;
	String password;
	JLabel labelForInvalidUsername;
	JLabel labelForInvalidPassword;
	public logIn()
	{
	}
	public  void frameOfLogIn()
	{
		frame = new JFrame();
        textOfUsername=new JTextField();
        textOfPassword=new JTextField();
        buttonForLogIn=new JButton();
        buttonForSignUp=new JButton();
        labelForLogIn=new JLabel();
        labelForUserName=new JLabel();
        labelForPassword=new JLabel();
        labelForNewAccount=new JLabel();
		labelForImage=new JLabel();
        buttonForForgottenPassword=new JButton();

		ImageIcon BilBookImage = new ImageIcon("BilBookImage.png");
		labelForImage.setBounds(280, 400, 346, 147);
		labelForImage.setIcon(BilBookImage);

        textOfUsername.addActionListener(this);
		textOfUsername.setBounds(720,420,200,30);
		textOfUsername.setFont(new Font("Comic Sans",Font.BOLD,20));

        textOfPassword.addActionListener(this);
		textOfPassword.setBounds(720,470,200,30);
		textOfPassword.setFont(new Font("Comic Sans",Font.BOLD,20));

        buttonForLogIn = new JButton();
		buttonForLogIn.setBounds(640, 550, 300, 40);
		buttonForLogIn.addActionListener(this);
		buttonForLogIn.setText("Log In!");
		buttonForLogIn.setFocusable(false);
		buttonForLogIn.setHorizontalTextPosition(JButton.CENTER);
		buttonForLogIn.setVerticalTextPosition(JButton.BOTTOM);
		buttonForLogIn.setFont(new Font("New York Times",Font.BOLD,15));
		buttonForLogIn.setIconTextGap(-15);
		buttonForLogIn.setForeground(Color.black);
		buttonForLogIn.setBackground(Color.cyan);
		buttonForLogIn.setBorder(BorderFactory.createEtchedBorder());

        buttonForSignUp = new JButton();
		buttonForSignUp.setBounds(820, 595, 100, 25);
		buttonForLogIn.addActionListener(this);
		buttonForSignUp.setText("Sign up");
		buttonForSignUp.setFocusable(false);
	//	buttonForSignUp.setHorizontalTextPosition(JButton.LEFT);
		buttonForSignUp.setVerticalTextPosition(JButton.BOTTOM);
		buttonForSignUp.setFont(new Font("New York Times",Font.BOLD,15));
		buttonForSignUp.setIconTextGap(-15);
		buttonForSignUp.setForeground(Color.black);
		buttonForSignUp.setBorder(BorderFactory.createEtchedBorder());

        buttonForForgottenPassword = new JButton();
		buttonForForgottenPassword.setBounds(650, 630, 300, 30);
		buttonForForgottenPassword.addActionListener(this);
		buttonForForgottenPassword.setText("Forgotten Your Password");
		buttonForForgottenPassword.setFocusable(false);
		buttonForForgottenPassword.setHorizontalTextPosition(JButton.CENTER);
		buttonForForgottenPassword.setVerticalTextPosition(JButton.BOTTOM);
		buttonForForgottenPassword.setFont(new Font("New York Times",Font.BOLD,12));
		buttonForForgottenPassword.setIconTextGap(-15);
		buttonForForgottenPassword.setForeground(Color.black);
		

	
		buttonForForgottenPassword.setBorder(BorderFactory.createEtchedBorder());

		labelForLogIn.setText("Log In");
		labelForLogIn.setBounds(640,300,250,100);
		labelForLogIn.setVisible(true);
		labelForLogIn.setForeground(Color.cyan);
		labelForLogIn.setFont(new Font("New York Times",Font.BOLD,20));

		labelForUserName.setText("Username: ");
		labelForUserName.setBounds(640,380,150,100);
		labelForUserName.setVisible(true);

		labelForPassword.setText("Password: ");
		labelForPassword.setBounds(640,430,150,100);
		labelForPassword.setVisible(true);

		labelForNewAccount.setText("Do not have an account? ");
		labelForNewAccount.setBounds(660,580,150,50);
		labelForNewAccount.setVisible(true);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setBounds(300,250,700,600);
        frame.setBackground(Color.gray);
		frame.setVisible(true);
		frame.add(textOfUsername);
		frame.add(textOfPassword);
		frame.add(buttonForLogIn);
		frame.add(buttonForSignUp);
		frame.add(labelForLogIn);
		frame.add(labelForUserName);
        frame.add(labelForPassword);
        frame.add(labelForNewAccount);
        frame.add(buttonForForgottenPassword);
		frame.add(labelForImage);
	}
    
    public static void main(String[] args) {
		logIn login = new logIn();
		login.frameOfLogIn();


    }

    @Override
    public void actionPerformed(ActionEvent e) 
	{
        if(e.getSource()==buttonForLogIn)
		{
			username=textOfUsername.getText();
			password=textOfPassword.getText();
			if(username==null)
			{
				labelForInvalidUsername = new JLabel();
				labelForInvalidUsername.setText("Invalid username ");
				labelForInvalidUsername.setBounds(0,100,150,100);
				labelForInvalidUsername.setVisible(true);
				frame.add(labelForInvalidUsername);

			}
			if(password==null)
			{
				labelForInvalidPassword = new JLabel();
				labelForInvalidPassword.setText("Invalid password ");
				labelForInvalidPassword.setBounds(0,100,150,100);
				labelForInvalidPassword.setVisible(true);
				frame.add(labelForInvalidPassword);
			}
		}
		if(e.getSource()==buttonForSignUp)
		{

		}
		if(e.getSource()==buttonForForgottenPassword)
		{

		}
    }
	

   
}
