

/**
 * QuizCardPlayer - This class allows the user to test themselves using a specific Deck of QuizCards.
 *
 * @author Calculus5000, abuzittin gillifirca, Caleb Copeland
 * @since August 21 2021
 */
public abstract class QuizCardPlayer {
//
//    protected JTextArea textArea;
//    private int deckIndex;
//    protected boolean isAnswerShown;
//    protected JButton submitButton;
//
//    private final Dimension FRAME_SIZE = new Dimension(300, 300);
//    private final ElementUI quizCardBuilder;
//    private final String submit_text;
//
//    public QuizCardPlayer(Deck deck, ElementUI quizCardBuilder,String text, String submit_text) {
//        super(text, new Dimension(200, 200));
//        this.deck = deck;
//        this.quizCardBuilder = quizCardBuilder; // registers the callback
//        this.submit_text = submit_text;
//    }
//
//    /**
//     * Sets up and shows.
//     */
//    public void build() {
//        SwingUtilities.invokeLater(
//                () -> {
//                    buildContentPane();
//                    add(new EasyLabel("Question:"));
//                    buildTextArea();
//                    buildButtonPanel();
//                    displayFrame();
//                    submitButton.requestFocusInWindow();
//                }
//        );
//    }
//
//    private void buildButtonPanel()
//    {
//        JPanel buttonPanel = new JPanel();
//        submitButton = new JButton(submit_text);
//        submitButton.addActionListener(ev -> invokeLater(getAction()));
//        buttonPanel.add(submitButton);
//        buildUniqueButtons(buttonPanel);
//        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        add(BorderLayout.SOUTH, buttonPanel);
//    }
//
//    protected abstract void buildUniqueButtons(JPanel buttonPanel);
//
//    private void buildContentPane() {
//        removeAll();
//        setLayout(new BorderLayout());
//        setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
//    }
//
//
//
//    protected void buildTextArea() {
//        textArea = new JTextArea();
//        textArea.setEditable(false);
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
//        String question;
//        try {
//            question = deck.getQuizCardList().get(0).getQuestion();
//        }
//        catch (IndexOutOfBoundsException ex)
//        {
//            question = "";
//        }
//        textArea.setText(question);
//        textArea.setFont(FontConstants.textAreaFont);
//        JScrollPane jsp = new JScrollPane(textArea);
//        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
//        contentPane.add(BorderLayout.CENTER, jsp);
//    }
//
//    protected void close() {
//        SwingUtilities.invokeLater(frame::dispose);
//        deck.setIsTestRunning(false);
//        deck.resetNumCorrect();
//        deck.resetNumWrong();
//        //quizCardBuilder.setTextAreaEditability(true);
//        //quizCardBuilder.getQuestionText().requestFocusInWindow();
//    }
//
//    private void displayFrame() {
//        frame.setSize(FRAME_SIZE);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//
//    /**
//     * toFront - brings this frame in the JVM to the front.
//     */
//    public void toFront() {
//        SwingUtilities.invokeLater(frame::toFront);
//    }
//
//
//
//    protected Runnable getAction() {
//        if (deckIndex > deck.getQuizCardList().size())
//            return QuizCardPlayer.this::close;
//
//        if (deckIndex == deck.getQuizCardList().size())
//            return this::showResults;
//
//        return isAnswerShown ? this::showNextCard : this::submit;
//    }
//
//
//    //to be used as `invokeLater(this::showAnswer)` etc.
//    protected void submit() {
//        label.setText("Answer:");
//        textArea.setText(deck.getQuizCardList().get(deckIndex).getAnswer());
//        isAnswerShown = true;
//        submitButton.setVisible(false);
//        showAnswerSpecific();
//        deckIndex++;
//    }
//
//    protected abstract void showAnswerSpecific();
//
//    protected void showNextCardOrResults() {
//        if (deckIndex < deck.getQuizCardList().size()) {
//            this.showNextCard();
//        } else {
//            this.showResults();
//        }
//    }
//
//    protected void showNextCard() {
//        label.setText("Question:");
//        textArea.setText(deck.getQuizCardList().get(deckIndex).getQuestion());
//        isAnswerShown = false;
//        submitButton.setText(submit_text);
//        submitButton.setVisible(true);
//        submitButton.requestFocusInWindow();
//        hideFeedback();
//    }
//
//    public void showResults() {
//        label.setText("Results:");
//        textArea.setText("You got " + deck.getNumCorrect() + " correct and " + deck.getNumWrong() + " wrong.");
//        submitButton.setText("End");
//        submitButton.setVisible(true);
//        submitButton.requestFocusInWindow();
//
//        deckIndex++;
//    }
//
//    protected abstract void hideFeedback();
}
