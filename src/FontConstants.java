import java.awt.Font;
import javax.swing.UIManager;

/** FontConstants - Class used to set the fonts */
public class FontConstants {
    public static final Font labelFont = new Font(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(),
            Font.PLAIN, 14);
    public static final Font textAreaFont = new Font(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(),
            Font.PLAIN, 16);
}