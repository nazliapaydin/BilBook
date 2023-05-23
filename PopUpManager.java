import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.SwingConstants;

import com.mysql.cj.x.protobuf.MysqlxNotice.Warning;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PopUpManager extends JFrame
{
    private BilBook bilBook;
    private JButton confirm;
    private JButton notConfirm;
    private JTextField email;
    private JTextField codeFromEmail;
    private JPasswordField password;
    private JPasswordField passwordConfirmation;

    private static final int DELETE_BOOK= 12;
    private static final int DELETE_PROFILE=23;
    private static final int LOG_OUT=15;
    private static final int SEND_EMAIL=16;
    
    /**
     * A general constructor for popups. It uses codes to create confirmation popups as well as a popup that takes an email.
     * @param code code of the popup found as final integers in the class
     * @param bilBook the bilbook object
     * @param product this parametre is for the delete product popup. It's the product that the user wants to delete
     * @param user this parametre is for the delete user popup. It's the user that the user wants to delete
     */
    private PopUpManager(int code, BilBook bilBook, Product product, User user)
    {
        this.bilBook=bilBook;
        setSize(400,200);
        setLocationRelativeTo(null);
        setTitle("Confirmation");
        JLabel label=new JLabel();
        JPanel generalPanel=new JPanel();
        JPanel buttonPanel=new JPanel();
        confirm=new JButton("Yes"); confirm.setBackground(GenericMethods.GREAT_COLOR);
        notConfirm=new JButton("No"); notConfirm.setBackground(GenericMethods.GREAT_COLOR); notConfirm.addActionListener(new NotConfirmButton());
        buttonPanel.add(confirm); buttonPanel.add(notConfirm);
        generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
        if(code==DELETE_BOOK){
            label.setText("Are you sure you want to delete this book?");
            generalPanel.add(Box.createRigidArea(new Dimension(1, 40)));generalPanel.add(label); 
            generalPanel.add(Box.createRigidArea(new Dimension(1, 10))); generalPanel.add(buttonPanel);
        }
        if(code==DELETE_PROFILE){
            label.setText("Are you sure you want to delete your profile?");
            JLabel warning=new JLabel("                        This action is irreversable."); warning.setFont(new Font(getName(), Font.PLAIN, 10));
            generalPanel.add(Box.createRigidArea(new Dimension(1, 40))); generalPanel.add(label); generalPanel.add(warning);
            generalPanel.add(buttonPanel);
        }
        if(code==LOG_OUT){
            confirm.addActionListener(new LogOutConfirm());
            label.setText("Are you sure you want to log out?");
            generalPanel.add(Box.createRigidArea(new Dimension(1, 40)));generalPanel.add(label); 
            generalPanel.add(Box.createRigidArea(new Dimension(1, 10))); generalPanel.add(buttonPanel);
        }
        if(code==SEND_EMAIL){
            JPanel panel=new JPanel(); panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            label.setText("Enter your Email:");
            email=new JTextField(19); panel.add(Box.createRigidArea(new Dimension(100, 30)));panel.add(email);panel.add(Box.createRigidArea(new Dimension(100, 1)));
            confirm.setText("Enter"); confirm.addActionListener(new EmailSendingButton());
            generalPanel=new JPanel();
            generalPanel.add(Box.createRigidArea(new Dimension(1, 50))); generalPanel.add(label); generalPanel.add(panel); generalPanel.add(confirm);
            generalPanel.add(Box.createRigidArea(new Dimension(1, 40)));
        }
        add(generalPanel);
        setVisible(true);
    }

    /**
     * Constructs a popup that confirms if the user really wants to delete a product
     * @param bilBook the bilbook object
     * @param product product that's going to be erased.
     * @return the popup as a frame.
     */
    public static void deleteProductPopup(BilBook bilBook, Product product)
    {
        new PopUpManager(DELETE_BOOK, bilBook, product, null);
    }

    /**
     * Constructs a popup that confirms if the user really wants to delete their profile
     * @param bilBook the bilbook object
     * @param user user that's going to be erased.
     * @return the popup as a frame.
     */
    public static void deleteProfilePopup(BilBook bilBook, User user)
    {
        new PopUpManager(DELETE_PROFILE, bilBook, null, user);
    }

    /**
     * Constructs a popup that confirms if the user wants to log out.
     * @param bilBook the bilbook object
     * @return the popup as a frame
     */
    public static void logOutPopup(BilBook bilBook)
    {
        new PopUpManager(LOG_OUT, bilBook, null, null);
    }

    /**
     * Constructs a popup that lets the user send a code to their email. The code can be used to change their password.
     * @param bilBook the bilbook object
     * @return the popup as a frame
     */
    public static void emailConfirmationPopup(BilBook bilBook)
    {
        new PopUpManager(SEND_EMAIL, bilBook, null, null);
    }

    /**
     * A specified constructor for password changing.
     * @param user the user that wants their password changed
     * @param code code that was sent to the given email
     * @param bilBook the bilbook object
     */
    private PopUpManager(User user, String code, BilBook bilBook)
    {
        setSize(400,200);
        setLocationRelativeTo(null);
        setTitle("Password Changer");
        JPanel generalPanel=new JPanel(); generalPanel.setLayout(new BorderLayout());
        JPanel upperPanel=new JPanel(); upperPanel.setLayout(new GridLayout(3, 2));
        codeFromEmail=new JTextField(); password=new JPasswordField(); passwordConfirmation=new JPasswordField();
        confirm=new JButton("Change Password"); confirm.setBackground(GenericMethods.GREAT_COLOR);
        upperPanel.add(new JLabel("Code from your Email: ")); upperPanel.add(codeFromEmail);
        upperPanel.add(new JLabel("New Password: ")); upperPanel.add(password);
        upperPanel.add(new JLabel("New Password Again: ")); upperPanel.add(passwordConfirmation);
        generalPanel.add(upperPanel, BorderLayout.CENTER); 
        generalPanel.add(confirm, BorderLayout.SOUTH);
        JPanel evenMoreGeneralPanel=new JPanel(); evenMoreGeneralPanel.setLayout(new BoxLayout(evenMoreGeneralPanel, 0));
        evenMoreGeneralPanel.add(Box.createRigidArea(new Dimension(50, 1)));evenMoreGeneralPanel.add(generalPanel);evenMoreGeneralPanel.add(Box.createRigidArea(new Dimension(50, 1)));
        JPanel somehowEvenMoreGeneralPanel=new JPanel(); somehowEvenMoreGeneralPanel.setLayout(new BoxLayout(somehowEvenMoreGeneralPanel, 1)); somehowEvenMoreGeneralPanel.add(Box.createRigidArea(new Dimension(1, 30)));
        somehowEvenMoreGeneralPanel.add(evenMoreGeneralPanel); somehowEvenMoreGeneralPanel.add(Box.createRigidArea(new Dimension(1, 20)));
        add(somehowEvenMoreGeneralPanel);
        EmailSender.sendVerificationCode(user.getEmail(), code);
    }

    private class EmailSendingButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String givenEmail=email.getText().replaceAll(" ", "");
            String code=GenericMethods.createCode(6);
            User user=bilBook.findUserFromEmail(givenEmail);
            if(user!=null)
            {
                PopUpManager popup=new PopUpManager(user, code, bilBook);
                popup.setVisible(true);
            }
            dispose();
        }
    }

    private class NotConfirmButton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class LogOutConfirm implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            bilBook.logOut();
            dispose();
        }
    }

    public static void main(String[] args) {
        logOutPopup(new BilBook());
    }
    
}
