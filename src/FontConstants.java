import javax.swing.UIManager;
import java.awt.Font;

/** FontConstants - Class used to set the fonts
 * @since August 21 2021
 * */
public class FontConstants {
    public static final Font labelFont = new Font(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(),
            Font.PLAIN, 14);
    public static final Font textAreaFont = new Font(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(),
            Font.PLAIN, 16);
}