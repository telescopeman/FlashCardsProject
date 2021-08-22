import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Caleb Copeland, Calculus5000
 * @since August 22, 2021
 */
public abstract class DisplayUI extends JPanel {
    private final String windowTitle;
    private final Dimension MINIMUM_FRAME_SIZE;
    private final HomeMenu menu;

    protected class EasyButton extends JButton  {
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
                switchPage(destination);
            }
        }

        public Portal(String str, DisplayUI destination) {
            super(str, CENTER_ALIGNMENT);
            addActionListener(new PortalListener(destination));
        }
    }

    public HomeMenu getMenu()
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

    /**
     * What to do when the window closes.
     */
    public abstract void close();

    protected static class EasyLabel extends JLabel {

        protected EasyLabel(String text)
        {
            super(text);
            setFont(FontConstants.labelFont);
            setAlignmentX(Component.LEFT_ALIGNMENT);
        }
    }

    public DisplayUI(HomeMenu menu, String title, Dimension min_size)
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
