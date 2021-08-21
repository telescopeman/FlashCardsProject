import javax.swing.*;

import static javax.swing.SwingUtilities.invokeLater;

public class FlashcardsPlayer extends QuizCardPlayer {
    private JButton wrongButton, correctButton;

    public FlashcardsPlayer(Deck deck, QuizCardBuilder quizCardBuilder) {
        super(deck,  quizCardBuilder,"Flashcards","Show answer");
    }

    protected void buildUniqueButtons(JPanel buttonPanel) {
        correctButton = new JButton("Right");
        correctButton.addActionListener(ev -> {
            deck.incrementNumCorrect();
            invokeLater(this::showNextCardOrResults);
        });

        wrongButton = new JButton("Wrong");
        wrongButton.addActionListener(ev -> {
            deck.incrementNumWrong();
            invokeLater(this::showNextCardOrResults);
        });
        hideFeedback();


        buttonPanel.add(correctButton);
        buttonPanel.add(wrongButton);
    }

    @Override
    protected void showAnswerSpecific() {
        correctButton.setVisible(true);
        correctButton.requestFocusInWindow();
        wrongButton.setVisible(true);
    }



    @Override
    protected void hideFeedback() {
        correctButton.setVisible(false);
        wrongButton.setVisible(false);
    }

}
