import javax.swing.*;
import java.awt.*;

public class CardsViewer extends DisplayUI {

    JScrollPane scrollPane = new JScrollPane(this);
    public CardsViewer(MainFrame menu) {
        super(menu, "StudyTool", new Dimension(400,800));
    }

    public CardsViewer(DisplayUI menu) {
        super(menu.getMenu(), "StudyTool", new Dimension(400,800));
    }

    private class CardDisplay extends JPanel
    {
        public CardDisplay(QuizCard card)
        {
            add(buildTextArea(card.getQuestion(),true));
            add(buildTextArea(card.getAnswer(),true));
        }
    }

    @Override
    protected void buildContent() {
        JScrollPane scrollPane = new JScrollPane();
        add(BorderLayout.CENTER, scrollPane);
        for (QuizCard card : getDeck().getQuizCardList())
        {
            scrollPane.add(new CardDisplay(card));
        }
    }
}
