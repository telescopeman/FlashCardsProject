import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Caleb Copeland, Calculus5000
 * @since August 22, 2021
 */
public abstract class DisplayUI extends JPanel {
    private final String windowTitle;
    private final Dimension MINIMUM_FRAME_SIZE;
    private final MainFrame menu;

    protected static class EasyButton extends JButton  {
        public EasyButton(String str, float alignment) {
            super(str);
            setAlignmentY(alignment);
            setOpaque(true);
            setBorderPainted(true);
        }
    }

    protected class Portal extends EasyButton
    {
        private class PortalListener implements ActionListener {
            private final DisplayUI destination;

            private PortalListener(DisplayUI destination) {
                this.destination = destination;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.print(e.toString());
                switchPage(destination);
            }
        }

        public Portal(String str, DisplayUI destination) {
            super(str, CENTER_ALIGNMENT);
            addActionListener(new PortalListener(destination));
        }
    }

    public MainFrame getMenu()
    {
        return menu;
    }

    protected Deck getDeck()
    {
        return getMenu().deck;
    }

    public String getWindowTitle()
    {
        return windowTitle;
    }

    public Dimension getMinimumFrameSize()
    {
        return MINIMUM_FRAME_SIZE;
    }

    protected JTextArea buildTextArea(String text,boolean isEditable) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(isEditable);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(text);
        textArea.setFont(FontConstants.textAreaFont);
        return textArea;
    }

    /**
     * What to do when the window closes.
     */
    public void close() {
        if (getDeck().getIsModified()) {
            // Automatically closes the program if there's nothing to be saved.
            if (getDeck().getQuizCardList().size() == 0) {
                System.exit(0);
            }
            else {
                int optionChosen = JOptionPane.showConfirmDialog(this, "Do you want to save this deck?", "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (optionChosen == JOptionPane.YES_OPTION) {
                    FileManager.save(getDeck(),getMenu());
                }
                if (optionChosen != JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }
            }
        }
        else {
            System.exit(0);
        }
    }

    protected static class EasyLabel extends JLabel {

        protected EasyLabel(String text)
        {
            super(text);
            setFont(FontConstants.labelFont);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }

    public DisplayUI(MainFrame menu, String title, Dimension min_size)
    {
        this.menu = menu;
        windowTitle = title;
        MINIMUM_FRAME_SIZE = min_size;
    }

    public void load()
    {
        removeAll();
        buildContent();
    }

    protected abstract void buildContent();

    protected void switchPage(DisplayUI destination)
    {
        menu.loadDisplay(destination);
    }
}
