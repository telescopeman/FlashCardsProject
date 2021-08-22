import java.awt.*;

public class StartMenu extends DisplayUI {
    public StartMenu(HomeMenu menu) {
        super(menu,"StudyTool", new Dimension(400,800));
    }



    @Override
    protected void buildContent() {
        System.out.println("yyyy");
        add(new Portal("Add Flashcards", new QuizCardBuilder(this)));
        add(new Portal("View Flashcards", new CardsViewer(this)));
        setBackground(Color.BLACK);
    }
}
