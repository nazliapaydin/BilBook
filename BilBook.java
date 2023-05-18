import javax.swing.JFrame;
import javax.swing.JPanel;

public class BilBook extends JFrame
{

    private User loggedInUser;

    public BilBook()
    {
        DatabaseControl.setup();
        setSize(1619, 906);
        setTitle("BilBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Changes the panel that's displayed in the BilBook frame.
     * Author: Ata Uzay Kuzey
     * @param panel new panel to be shown
     */
    public void changePanel(JPanel panel)
    {
        removeAll();
        add(panel);
        panel.updateUI();
    }

    public JPanel createMenuBar()
    {
        //TODO
        return new JPanel();
    }

    public JPanel createSortPanel()
    {
        //TODO
        return new JPanel();
    }

    public JPanel bilBookSlogan()
    {
        //TODO
        return new JPanel();
    }
    
}