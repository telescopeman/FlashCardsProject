import java.awt.Dimension;

public class HomeMenu extends ElementUI {
    public HomeMenu() {
        super("Home", new Dimension(200,200));
    }

    @Override
    protected void close() {
        System.exit(0);
    }
}
