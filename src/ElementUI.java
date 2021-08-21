import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Base class for stuff
 * @author Calculus5000, Caleb Copeland
 * @since August 21 2021
 */
public abstract class ElementUI {
    protected JLabel label;
    protected JPanel contentPane;
    protected Deck deck;
    protected JFrame frame;
    private final String windowTitle;

    private final Dimension MINIMUM_FRAME_SIZE;

    public ElementUI(String title, Dimension min_size)
    {
        windowTitle = title;
        MINIMUM_FRAME_SIZE = min_size;
    }

    protected void buildLabel(String text) {
        label = new JLabel();
        label.setFont(FontConstants.labelFont);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(BorderLayout.NORTH, label);
    }

    private void buildContentPane(LayoutManager layout) {
        contentPane = new JPanel();
        contentPane.setLayout(layout);
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        frame.setContentPane(contentPane);
    }

    protected abstract void close();

    protected void buildFrame() {
        frame = new JFrame(windowTitle + " - " + deck.getFileName());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setMinimumSize(MINIMUM_FRAME_SIZE);
        frame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        close();
                    }
                }
        );
    }
}
