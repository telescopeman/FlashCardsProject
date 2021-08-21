import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * QuizCardPlayer - This class allows the user to test themselves using a specific Deck of QuizCards.
 *
 * @author Calculus5000, abuzittin gillifirca, Caleb Copeland
 */
public class QuizCardPlayer extends ElementUI{

    private int deckIndex;
    private boolean isAnswerShown;
    private JButton correctButton, showAnswerButton, wrongButton;
    private JTextArea textArea;
    private final Dimension FRAME_SIZE = new Dimension(300, 300);
    private final QuizCardBuilder quizCardBuilder;

    public QuizCardPlayer(Deck deck, QuizCardBuilder quizCardBuilder) {
        super("Quiz Card Player", new Dimension(200, 200));
        this.deck = deck;
        this.quizCardBuilder = quizCardBuilder; // registers the callback
        //build();
    }

    public void build() {
        SwingUtilities.invokeLater(
                () -> {
                    buildFrame();
                    buildContentPane();
                    buildLabel("Question:");
                    buildTextArea();
                    buildButtonPanel();
                    displayFrame();
                    showAnswerButton.requestFocusInWindow();
                }
        );
    }

    private void buildButtonPanel() {
        showAnswerButton = new JButton("Show answer");
        showAnswerButton.addActionListener(ev -> invokeLater(getAction()));

        correctButton = new JButton("Right");
        correctButton.addActionListener(ev -> {
            deck.incrementNumCorrect();
            invokeLater(this::showNextCardOrResults);
        });
        correctButton.setVisible(false);
        wrongButton = new JButton("Wrong");
        wrongButton.addActionListener(ev -> {
            deck.incrementNumWrong();
            invokeLater(this::showNextCardOrResults);
        });
        wrongButton.setVisible(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showAnswerButton);
        buttonPanel.add(correctButton);
        buttonPanel.add(wrongButton);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(BorderLayout.SOUTH, buttonPanel);
    }

    private void buildContentPane() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        frame.setContentPane(contentPane);
    }



    private void buildTextArea() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        String question;
        try {
            question = deck.getQuizCardList().get(0).getQuestion();
        }
        catch (IndexOutOfBoundsException ex)
        {
            question = "";
        }
        textArea.setText(question);
        textArea.setFont(FontConstants.textAreaFont);
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(BorderLayout.CENTER, jsp);

    }

    protected void close() {
        SwingUtilities.invokeLater(frame::dispose);
        deck.setIsTestRunning(false);
        deck.resetNumCorrect();
        deck.resetNumWrong();
        quizCardBuilder.setTextAreaEditability(true);
        quizCardBuilder.getQuestionText().requestFocusInWindow();
    }

    private void displayFrame() {
        frame.setSize(FRAME_SIZE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * toFront - brings this frame in the JVM to the front.
     */
    void toFront() {
        SwingUtilities.invokeLater(frame::toFront);
    }

    public void showResults() {
        label.setText("Results:");
        textArea.setText("You got " + deck.getNumCorrect() + " correct and " + deck.getNumWrong() + " wrong.");
        showAnswerButton.setText("End");
        showAnswerButton.setVisible(true);
        showAnswerButton.requestFocusInWindow();
        correctButton.setVisible(false);
        wrongButton.setVisible(false);
        deckIndex++;
    }

    private Runnable getAction() {
        if (deckIndex > deck.getQuizCardList().size())
            return QuizCardPlayer.this::close;

        if (deckIndex == deck.getQuizCardList().size())
            return this::showResults;

        return isAnswerShown ? this::showNextCard : this::showAnswer;
    }


    //to be used as `invokeLater(this::showAnswer)` etc.
    private void showAnswer() {
        label.setText("Answer:");
        textArea.setText(deck.getQuizCardList().get(deckIndex).getAnswer());
        isAnswerShown = true;
        showAnswerButton.setVisible(false);
        correctButton.setVisible(true);
        correctButton.requestFocusInWindow();
        wrongButton.setVisible(true);

        deckIndex++;

    }

    private void showNextCardOrResults() {
        if (deckIndex < deck.getQuizCardList().size()) {
            this.showNextCard();
        } else {
            this.showResults();
        }
    }

    private void showNextCard() {
        label.setText("Question:");
        textArea.setText(deck.getQuizCardList().get(deckIndex).getQuestion());
        isAnswerShown = false;
        showAnswerButton.setText("Show answer");
        showAnswerButton.setVisible(true);
        showAnswerButton.requestFocusInWindow();
        correctButton.setVisible(false);
        wrongButton.setVisible(false);
    }
}
