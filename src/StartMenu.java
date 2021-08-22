import javax.swing.*;
import java.awt.*;

public class StartMenu extends DisplayUI {
    public StartMenu(HomeMenu menu) {
        super(menu,"StudyTool", new Dimension(400,800));
    }

    @Override
    public void close() {
        if (getDeck().getIsModified()) {
            // Automatically closes the program if there's nothing to be saved.
            if (getDeck().getQuizCardList().size() == 0) {
            }
            else {
                int optionChosen = JOptionPane.showConfirmDialog(this, "Do you want to save this deck?", "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (optionChosen == JOptionPane.YES_OPTION) {
                    getMenu().save();
                }
                if (optionChosen != JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }
            }
            System.exit(0);
        }
        else {
            // Automatically closes the program if there's no changes to be saved.
            System.exit(0);
        }
    }

    @Override
    protected void buildContent() {
        System.out.println("yyyy");
        add(new Portal("Add Flashcards", new QuizCardBuilder(this)));
        setBackground(Color.BLACK);
    }
}
